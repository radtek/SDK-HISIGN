package com.hisign.sdk.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 消息客户端抽象类
 */
public abstract class MessageClient{
	
	/**
	 * 日志打印变量
	 */
	protected final static Logger log = LoggerFactory.getLogger(MessageClient.class);
	
	/**
	 * 单实例消息客户端
	 */
	private static MessageClient _instance = null;
	
	/**
	 * 获得单实例消息客户端
	 * 此处的实例化类，需要做成可配置的。
	 * @return
	 */
	public static synchronized MessageClient getInstance() {
        if (_instance == null) {
        	_instance = MessageClientFactory.getInstance().getMessageClient();
        }
        
        return _instance;
    }
	
	/**
	 * 添加一个消息处理器到处理队列
	 * @param topicName TOPIC名称
	 * @param handler 消息处理器
	 */
	public abstract void addMessageHandler(String topicName, MessageHandler handler) throws Exception;
	
	/**
	 * 添加一个消息处理器到处理队列
	 * @param topicName  topic名称
	 * @param group  group名称
	 * @param handler  消息处理器
	 * @throws Exception
	 */
	public abstract void addMessageHandler(String topicName,String group,MessageHandler handler) throws Exception;
	
	/**
     * 添加一个队列消息处理器到处理队列
     * @param queueName 队列名称
     * @param handler 消息处理器
     */
    public abstract void addQueueMessageHandler(String queueName, MessageHandler handler) throws Exception;
    
	/**
	 * 添加TOPIC到TOPIC队列
	 * <br><i>此方法在ActiveMQ中放弃使用，TOPIC在连接初始化时完成。</i></br>
	 * @param dest
	 * @return
	 * @throws NamingException
	 */
	public abstract boolean addTopic(String topicName) throws Exception ;
    
	/**
	 * 接收消息，在将处理消息的Handler添加处理器队列后调用此方法
	 * <br><i>此方法在ActiveMQ中放弃使用，消息处理器添加到队列后，自动接收消息并处理。</i></br>
	 * @throws JMSException
	 * @throws NamingException
	 */
    public abstract void receive() throws Exception;
    
    /**
     * 将消息处理器从队列中删除，当某一个Handler已经不在使用的时候，请及时调用此方法，将Handler删除。
     * @param dest
     * @param handler
     */
    public abstract void removeMessageHandler(String topicName,MessageHandler handler) throws Exception;
    
    /**
     * 发送消息到服务器，如果服务器连接失败，消息对象放入缓存中，待连接正常后再补发消息，消息缓存队列要求有先后顺序。
     * @param message
     * @throws JMSException
     * @throws NamingException
     */
    public abstract void send(Message message) throws Exception;
    
    /**
     * 发送消息到服务器，如果服务器连接失败，消息对象放入缓存中，待连接正常后再补发消息，消息缓存队列要求有先后顺序。
     * @param 
     * @param message
     * @throws JMSException
     * @throws NamingException
     */
    public abstract void send(String topicName, Message message) throws Exception;
    
    /**
     * 发送消息到服务器
     * @param topicName topic名称
     * @param msgBean 消息对象
     * @param isOrdered 是否顺序发送  true:是顺序发送，false:不保证顺序
     */
    public abstract void send(Message message,boolean isOrdered) throws Exception; 
    
}
