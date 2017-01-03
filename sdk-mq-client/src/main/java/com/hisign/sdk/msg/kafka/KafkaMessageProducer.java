package com.hisign.sdk.msg.kafka;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.StringUtils;
import com.hisign.sdk.msg.Message;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @Title:
 *  kafka消息发送者
 * @description:
 *  负责Kafka消息发送
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2016年5月19日  下午5:35:36
 * @version 1.0
 */
public class KafkaMessageProducer {
    
    //日志
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageProducer.class);
    
    //配置参数
    private Properties props = null;
    
    //broker地址列表
    private String brokerlist = null;
    
    private Producer<Long, Message> producer = null;
    
    /**
     * 配置属性
     * @param props
     * @throws Exception
     */
    public KafkaMessageProducer(Properties props) throws Exception{
        if(props == null || props.size() <= 0){
            throw new RuntimeException("props can't be null");
        }
        
        String brokerlistStr = props.getProperty("metadata.broker.list");
        if(StringUtils.isEmpty(brokerlistStr)){
            throw new RuntimeException("brokerlistStr can't be null");
        }
        
        this.props = props;
        this.brokerlist = brokerlistStr;
        
        this.createProducer();
    }

    
    /**
     * 构建生产者
     */
    private synchronized void createProducer() throws Exception{
        try{
            log.info("begining kafka createProducer for brokerlist="+this.brokerlist);
            ProducerConfig config = new ProducerConfig(this.props);
            this.producer = new Producer<Long, Message>(config);
            
            log.info("kafka createProducer for brokerlist="+this.brokerlist+" successfully!");
        }catch(Throwable tr){
            log.error("kafka createProducer for brokerlist="+this.brokerlist+" catch an exception",tr);
        }
    }
    
    
    
    /**
     * 发送消息到Metaq服务器
     * @param msgBean 消息对象
     * @throws Exception
     */
    public void send(Message msgBean) throws Exception{
        if(msgBean == null){
            return;
        }
        String topicName = msgBean.getTopic();
        this.send(topicName, msgBean);
    }
    
    /**
     * 发送消息到服务器
     * @param topicName topic名称
     * @param msgBean 消息对象
     * @throws Exception
     */
    public void send(String topicName, Message msgBean) throws Exception{
        this.send(topicName, msgBean, false);
    }
    
    
    
    /**
     * 发送消息到Metaq服务器
     * @param msgBean 消息对象
     * @param isOrdered 是否顺序发送  true:是顺序发送，false:不保证顺序
     * @throws Exception
     */
    public void send(Message msgBean,boolean isOrdered) throws Exception{
        if(msgBean == null){
            return;
        }
        String topicName = msgBean.getTopic();
        this.send(topicName, msgBean,isOrdered);
    }
    
    /**
     * 发送消息到服务器
     * @param topicName topic名称
     * @param msgBean 消息对象
     * @param isOrdered 是否顺序发送  true:是顺序发送，false:不保证顺序
     * @throws Exception
     */
    public void send(String topicName, Message msgBean,boolean isOrdered) throws Exception{
        if(topicName == null || topicName.trim().equals("")){
            throw new Exception("topicName can't be null");
        }
        
        KeyedMessage<Long, Message> keyedMsg = new KeyedMessage<Long, Message>(topicName, msgBean); 
        producer.send(keyedMsg);
    }
    
    /**
     * 获取顺序发送的key
     * @param topicName
     * @return
     */
    private String getOrderedKey(String topicName){
        return "ordered_"+topicName;
    }
}

