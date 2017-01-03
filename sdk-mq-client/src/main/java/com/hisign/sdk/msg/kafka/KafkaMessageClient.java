package com.hisign.sdk.msg.kafka;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.msg.Message;
import com.hisign.sdk.msg.MessageClient;
import com.hisign.sdk.msg.MessageHandler;


/**
 * @Title:
 *    Kafka消息处理器
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2016年5月19日  下午5:34:08
 * @version 1.0
 */
public class KafkaMessageClient extends MessageClient {
    
    private static final long serialVersionUID = 1L;
    
    //日志
    private static Logger log = LoggerFactory.getLogger(KafkaMessageClient.class);
    
    //zkConnect
    private String zkConnect = null;
    
    private String brokerlist = null;
    
    //默认消息分组
    private String defaultGroup = null;
    
    private String zkRoot = null; //kafka在zookeeper的存根，默认/kafkacluster
    
    private static final String DEFAULT_QUEUE_GROUP = "sigmam-queue-group"; //使用quque时，默认指定分组
    
    //消息发布者
    private KafkaMessageProducer producer = null; 
    
    //消息消费者
    private KafkaMessageConsumer comsumer = null;
    
    
    /**
     * kafka消息客户端
     * @param zkConnect zookeeper地址，例如:192.168.95.111:52181,192.168.95.112:52181,192.168.95.113:52181
     * @param borkerlist kafka集群地址,例如:192.168.95.111:59092,192.168.95.112:59092,192.168.95.113:59092
     * @param defaultGroup 默认分组
     * @param zkRoot 在zookeeper上默认的kafka根节点
     */
    public KafkaMessageClient(String zkConnect,String borkerlist,String defaultGroup,String zkRoot){
        this.zkConnect = zkConnect;
        this.brokerlist = borkerlist;
        this.defaultGroup = defaultGroup;
        this.zkRoot = zkRoot;
        
        this.initProducer();
        this.initConsumer();
    }
    
    /**
     * 初始化消息发送者
     */
    private void initProducer(){
        try{
            log.info("begin to initProducer for "+this.brokerlist+"...");
            Properties props = new Properties();
            props.put("metadata.broker.list", this.brokerlist);
            props.put("serializer.class", "com.hisign.sdk.msg.kafka.encoder.KafkaJSONMessageSerializer");
            this.producer = new KafkaMessageProducer(props);
            log.info("initProducer  for "+this.brokerlist+" successfully!");
        }catch(Throwable tr){
            log.error("initProducer  for "+this.brokerlist+" catch an exception",tr);
        }
    }
    
    /**
     * 初始化消息消费者
     */
    private void initConsumer(){
        try{
            log.info("begin to initConsumer ...");
            Properties props = new Properties();
            String kafkaZkConnect = this.zkConnect + "/" + this.zkRoot;
            props.put("zookeeper.connect", kafkaZkConnect);
            props.put("zookeeper.session.timeout.ms", "5000");
            props.put("zookeeper.connection.timeout.ms", "10000");
            props.put("zookeeper.sync.time.ms", "200000");
            props.put("auto.commit.enable", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("rebalance.max.retries", "10");
            props.put("rebalance.backoff.ms", "2000");
            
            this.comsumer = new KafkaMessageConsumer(props,this.defaultGroup);
            
            log.info("initConsumer  successfully!");
        }catch(Throwable tr){
            log.error("initConsumer catch an exception",tr);
        }
    }
    
    
    /**
     * 添加一个消息处理器到处理队列
     * @param topicName topic名称
     * @param handler 消息处理器
     */
    @Override
    public void addMessageHandler(String topicName, MessageHandler handler) throws Exception{
        try {
            this.comsumer.addMessageHandler(topicName, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 添加一个消息处理器到处理队列
     * @param topicName  topic名称
     * @param group  group名称
     * @param handler  消息处理器
     * @throws Exception
     */
    @Override
    public void addMessageHandler(String topicName,String group,MessageHandler handler) throws Exception{
        this.comsumer.addMessageHandler(topicName, group, handler);
    }
    
    /**
     * 添加一个队列消息处理器到处理队列
     * @param queueName 队列名称
     * @param handler 消息处理器
     */
    @Override
    public void addQueueMessageHandler(String queueName, MessageHandler handler) throws Exception{
        try {
            this.addMessageHandler(queueName, DEFAULT_QUEUE_GROUP, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 将消息处理器从队列中删除
     * 当某一个Handler已经不在使用的时候，请及时调用此方法，将Handler删除。
     * @param topicName topic名称 
     * @param handler
     */
    @Override
    public void removeMessageHandler(String topicName,MessageHandler handler)  throws Exception{
        try {
            this.comsumer.removeMessageHandler(topicName, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 将消息处理器从队列中删除
     * 当某一个Handler已经不在使用的时候，请及时调用此方法，将Handler删除。
     * @param topicName topic名称 
     * @param group 分组名称
     * @param handler
     */
    public void removeMessageHandler(String topicName,String group,MessageHandler handler) throws Exception{
        this.comsumer.removeMessageHandler(topicName, group, handler);
    }
    
    /**
     * 发送消息到服务器
     * @param message
     * @throws Exception
     */
    @Override
    public void send(Message message) throws Exception{
        this.send(message, false);
    }
    
    /**
     * 发送消息到服务器
     * @param topic the specific topic
     * @param message
     * @throws Exception
     */
    @Override
    public void send(String topicName, Message message) throws Exception{
        try {
            this.producer.send(topicName, message);
        }catch (Exception e) {
            log.error("send message catch an exception",e);
            throw e;
        }
    }
    
    /**
     * 发送消息到服务器
     * @param topicName topic名称
     * @param msgBean 消息对象
     * @param isOrdered 是否顺序发送  true:是顺序发送，false:不保证顺序
     */
    public void send(Message message,boolean isOrdered) throws Exception{
        try {
            this.producer.send(message,isOrdered);
        }catch (Exception e) {
            log.error("send message catch an exception",e);
            throw e;
        }
    }
    
    @Override
    public boolean addTopic(String dest) throws NamingException {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	public void receive() throws Exception {
		// TODO Auto-generated method stub
	}

}

