package com.hisign.sdk.msg;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.CommonIOUtils;
import com.hisign.sdk.config.SysConfigLoader;
import com.hisign.sdk.msg.kafka.KafkaMessageClient;


/**
 * @Title:
 *  Message客户端工厂类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 5, 2013  11:20:01 AM
 * @version 1.0
 */
public class MessageClientFactory {
    
    private static Logger log = LoggerFactory.getLogger(MessageClientFactory.class);
    
    private static final String CONFIG_FILE = "mq_config.properties"; //db参数配置文件
    
	public static final String TYPE_KAFKA = "kafka";
	
	//使用的消息服务器类型,为以后其他服务器类型预留准备
	private String type = TYPE_KAFKA;
	
	//消息操作客户端
	private MessageClient messageClient = null;
	
	//单例
	public static MessageClientFactory instance = null; 
	
	//默认分组前缀
	public static final String DEFAULT_GROUP_PREFIEX = "hisign"; 
	

	
	public synchronized static MessageClientFactory getInstance(){
		if(instance == null){
			instance = new MessageClientFactory();
		}
		
		return instance;
	}
	
	private MessageClientFactory(){
		try{
			initMessageClient();
		}catch(Throwable tr){
			log.error("initMessageClient catch an exception",tr);
		}
	}
	
	/**
	 * 初始化客户端实例
	 * @throws Exception
	 */
	private void initMessageClient() throws Exception{
		boolean loadFlag = this.loadConfigFromLocalProperties();
		if(loadFlag == false){
			log.info("load properties from sysconfigloader");
			SysConfigLoader.getInstance().loadSysConfig("UAOP");
		}
		
        String msgservertype = System.getProperty("msgservertype");
        log.info("----------msgservertype="+msgservertype+"-------------");
        if(msgservertype != null && msgservertype.trim().equalsIgnoreCase(TYPE_KAFKA)){
            this.type = TYPE_KAFKA;
            messageClient = this.createKafkaMessageClient();
        }else{
        	this.type = TYPE_KAFKA;
            messageClient = this.createKafkaMessageClient();
        }
	}
	
	
	/**
	 * 从本地配置文件中获取sdk-mq-client相关配置
	 * @return
	 */
	private boolean loadConfigFromLocalProperties(){
		InputStream is = null;
        try {
            is = MessageClientFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            
            if(is == null){
                log.warn("can't get "+CONFIG_FILE+" in classpath");
                return false;
            }

            Properties props = new Properties();
            props.load(is);
            
            if(props == null || props.size() <= 0){
            	return false;
            }
            
            System.getProperties().putAll(props);
            log.info("put mq_config.properties "+props.toString()+" to System.properties");
            
            return true;
        } catch (Exception ex) {
            log.warn("load "+CONFIG_FILE+" catch an exception",ex);
            return false;
        }finally{
            CommonIOUtils.close(is);
        }
	}
	
	
	/**
     * 构建kafka消息服务器客户端
     * @return
     */
    private KafkaMessageClient createKafkaMessageClient() throws Exception{
        String zkConnect = System.getProperty("zkconnect");
        String brokerlist = System.getProperty("kafka_brokerlist");
        String zkRoot = System.getProperty("kafka_zkroot");
        String defaultGroup = DefaultGroupConfig.getInstance().getDefaultGroup();
        log.info("-------------kafka address "+brokerlist+",zkRoot="+zkRoot+",defaultGroup="+defaultGroup+"----------------");
        KafkaMessageClient kafkaClient = new KafkaMessageClient(zkConnect,brokerlist,defaultGroup,zkRoot);
        
        return kafkaClient;
    }
    
	
	
	public MessageClient getMessageClient() {
		return messageClient;
	}


}
