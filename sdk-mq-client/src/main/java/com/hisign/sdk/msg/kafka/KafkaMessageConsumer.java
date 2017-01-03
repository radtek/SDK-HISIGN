package com.hisign.sdk.msg.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.StringUtils;
import com.hisign.sdk.common.util.serializer.ISerializer;
import com.hisign.sdk.common.util.serializer.SerializerFactory;
import com.hisign.sdk.msg.Message;
import com.hisign.sdk.msg.MessageHandler;
import com.hisign.sdk.msg.kafka.encoder.KafkaJSONMessageSerializer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * @Title:
 *  Kafka消息消费者
 * @description:
 *  负责Kafka消息的消费
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2016年5月19日  下午5:36:03
 * @version 1.0
 */
public class KafkaMessageConsumer {
    
    //日志
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageConsumer.class);
    
    
    public static final char[] INVALID_GROUP_CHAR = { '~', '!', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '`', '\'',
                                                      '"', ',', ';', '/', '?', '[', ']', '<', '>', '.', ':', ' ' };
    
    //默认消息分组
    private String defaultGroup = null;
    
    //配置参数
    private Properties defaultProps = null;
    
    //默认建立5个分区,也就是对应最多可以用5个线程进行拉取
    private int numThreadsPreTopic = 5;
    
    //保存已经建立的topic_group对应的consumer key=topic_group,value=ConsumerConnector
    private static Map<String,ConsumerConnector> topicGroupConsumerMap = new ConcurrentHashMap<String,ConsumerConnector>();
     
    //保存已经注册的MessageHandler key=topic_group,value=MetaqMessageListenerWrapper
    private static Map<String,CopyOnWriteArraySet<MessageHandler>>  topicGroupHandlerMap = new ConcurrentHashMap<String,CopyOnWriteArraySet<MessageHandler>>();

    
    /**
     * @param defaultProps 配置参数
     * @param defaultGroup 默认分组
     * @throws Exception
     */
    public KafkaMessageConsumer(Properties defaultProps,String defaultGroup) throws Exception{
        if(defaultProps == null || defaultProps.size() <= 0){
            throw new RuntimeException("defaultProps can't be null");
        }
        
        if(StringUtils.isEmpty(defaultGroup)){
            throw new RuntimeException("defaultGroup can't be null");
        }
        
        this.defaultProps = defaultProps;
        this.defaultGroup = defaultGroup;
        this.defaultGroup = this.processInvalidGroupChar(this.defaultGroup);
    }
    
    
    /**
     * 添加一个消息处理器到处理队列
     * @param topicName topic名称
     * @param handler 消息处理器
     */
    public void addMessageHandler(String topicName, MessageHandler handler) throws Exception{
        this.addMessageHandler(topicName,this.defaultGroup, handler);
    }
    
    /**
     * 添加一个消息处理器到处理队列
     * @param topicName  topic名称
     * @param group  group名称
     * @param handler  消息处理器
     * @throws Exception
     */
    public synchronized void addMessageHandler(String topicName,String group,MessageHandler handler) throws Exception{
        String newGroup = this.processInvalidGroupChar(group);
        String key = this.getTopicGroupKey(topicName, newGroup);
        if(!KafkaMessageConsumer.topicGroupConsumerMap.containsKey(key)){ //不包含topic,group,则新建该topic,group的consumer
            this.createComsumer(topicName, newGroup);
        }
        
        CopyOnWriteArraySet<MessageHandler> handlers = topicGroupHandlerMap.get(key);
        handlers.add(handler);
    }
    
    /**
     * 将消息处理器从队列中删除
     * 当某一个Handler已经不在使用的时候，请及时调用此方法，将Handler删除。
     * @param topicName topic名称 
     * @param handler
     */
    public void removeMessageHandler(String topicName,MessageHandler handler) throws Exception{
        this.removeMessageHandler(topicName,this.defaultGroup, handler);
    }
    
    /**
     * 将消息处理器从队列中删除
     * 当某一个Handler已经不在使用的时候，请及时调用此方法，将Handler删除。
     * @param topicName topic名称 
     * @param group 分组名称
     * @param handler
     */
    public void removeMessageHandler(String topicName,String group,MessageHandler handler) throws Exception{
        String newGroup = this.processInvalidGroupChar(group);
        String key = this.getTopicGroupKey(topicName, newGroup);
        if(!topicGroupHandlerMap.containsKey(key)){
            return;
        }
        
        CopyOnWriteArraySet<MessageHandler> handlers = topicGroupHandlerMap.get(key);
        if(handlers.contains(handler)){
            handlers.remove(handler);
        }
    }
    
    /**
     * 构建Consumer
     * @param topic topic名称
     * @param group  topic消费者分组
     */
    public void createComsumer(String topicName,String group){
        log.info("begin kafka createComsumer for topicName="+topicName+",group="+group);
        TopicManager.getInstance().createTopicIfNotExist(topicName);
        
        String key = this.getTopicGroupKey(topicName, group);
        
        Properties props = new Properties();
        props.putAll(this.defaultProps);
        props.put("group.id", group);
        
        log.info("createComsumer props="+props.toString());
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        ConsumerConnector comsumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
        
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put(topicName, numThreadsPreTopic);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = comsumerConnector.createMessageStreams(map);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topicName);
        
        // now launch all the threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreadsPreTopic);

        int threadNumber = 1;
        // now create an object to consume the messages//int threadNumber = 0;
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            executor.submit(new ConsumerMsgTask(topicName,group,stream,threadNumber));
            threadNumber++;
        }
        
        KafkaMessageConsumer.topicGroupConsumerMap.put(key, comsumerConnector);
        CopyOnWriteArraySet<MessageHandler> handlers = new CopyOnWriteArraySet<MessageHandler>();
        KafkaMessageConsumer.topicGroupHandlerMap.put(key, handlers);
    }
    
    
    /**
     * 处理分组中特殊字符
     * @param defaultGroup
     * @return
     */
    private String processInvalidGroupChar(final String defaultGroup){
        StringBuffer sb = new StringBuffer();
        try{   
            for(int i = 0; i < defaultGroup.length(); i++){
                char c = defaultGroup.charAt(i);
                boolean isInvalidChar = false;
                for(char invalid : INVALID_GROUP_CHAR){
                    if(c == invalid){
                        isInvalidChar = true;
                        sb.insert(i, "_");
                        break;
                    }
                }
                
                if(isInvalidChar == false){
                    sb.insert(i, c);
                }
            }
            
        }catch(Exception ex){
            log.error("processInvalidGroupChar "+defaultGroup+" catch an exception",ex);
            return defaultGroup;
        }
        
        return sb.toString();
    }
        
    
    /**
     * 构建topicGroupMap的key
     * @param topicName
     * @param group
     * @return
     */
    private String getTopicGroupKey(String topicName,String group){
        String key = topicName+"_"+group;
        return key;
    }
    
    
    /**
     * 消费者消息任务
     * @author lnj2050
     */
    class ConsumerMsgTask implements Runnable {
        private String topicName;
        private String group;
        private KafkaStream<byte[], byte[]> stream;
        private int threadNumber;
        //序列化器
        private KafkaJSONMessageSerializer serializer = null;

        public ConsumerMsgTask(String topicName,String group,KafkaStream<byte[], byte[]> stream, int threadNumber) {
            this.topicName = topicName;
            this.group = group;
            this.threadNumber = threadNumber;
            this.stream = stream;
            this.serializer = new KafkaJSONMessageSerializer();
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = this.stream.iterator();
            while (it.hasNext()){
                byte [] dataBytes = it.next().message();
                Message msgBean = (Message)this.serializer.toMessage(dataBytes);
                this.dispatchMessageToHandlers(msgBean);
            }
            log.warn("Shutting down  topicName="+this.topicName+",group="+this.group+" Thread: " + this.threadNumber);
        }
        
        
        /**
         * 派发消息到各个MessageHandler中
         * @param msgBean
         */
        public void dispatchMessageToHandlers(Message message){
            String key = getTopicGroupKey(this.topicName,this.group);
            CopyOnWriteArraySet<MessageHandler> handlers = topicGroupHandlerMap.get(key);
            if(handlers == null || handlers.size() <= 0){
                return;
            }
            
            Object[]arr = handlers.toArray();
            for(int i = 0; i < arr.length; i++){
                MessageHandler handler = (MessageHandler)arr[i];
                if(handler == null){
                    continue;
                }
                
                String handlerClassName = handler.getClass().getName();
                try{
                    long begin = System.currentTimeMillis();
                    handler.onMessage(message);
                    long during = System.currentTimeMillis() - begin;
                    
                }catch(Throwable tr){
                    log.error("handler ["+handlerClassName+"] on message catch an exception",tr);
                }
            }
        }
    }

}

