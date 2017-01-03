package com.hisign.sdk.msg.kafka;

import java.util.List;

import org.apache.kafka.common.security.JaasUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.StringUtils;
import com.hisign.sdk.msg.MessageClientFactory;
import com.hisign.sdk.msg.util.zookeeper.ZookeeperClient;
import com.hisign.sdk.msg.util.zookeeper.ZookeeperClientFactory;

import kafka.admin.TopicCommand;
import kafka.admin.TopicCommand.TopicCommandOptions;
import kafka.utils.ZkUtils;


/**
 * @Title:
 *  Topic管理器
 * @description:
 *  管理topic的查看，创建，删除
 * @author lnj 
 * @create time：2016年7月13日  上午10:01:55
 */
public class TopicManager {
	
	private static Logger log = LoggerFactory.getLogger(TopicManager.class);
	
	private String zkAddress;  //zookeeper连接地址
	
	private String namespace = "kafka";
	
	private ZkUtils zkUtils = null;
	
	private boolean autocreatetopic = false; //默认创建topic为true
	
	private int partitions = 5;
	
	private int replications = 1;
	
	private ZookeeperClient zkClient = null;
	
	
	private static TopicManager instance = null;
	
	public synchronized static TopicManager getInstance(){
		if(instance == null){
			return new TopicManager();
		}
		
		return instance;
	}
	
	private TopicManager(){
		this.init();
	}
	
	private void init(){
		String zkConnect = System.getProperty("zkconnect");
		if(StringUtils.isEmpty(zkConnect)){
			throw new RuntimeException("can't get zkconnect from system properties");
		}
		
		String zkRoot = System.getProperty("kafka_zkroot");
		if(StringUtils.isEmpty(zkRoot)){
			throw new RuntimeException("can't get kafka_zkroot from system properties");
		}
		
		this.zkAddress = zkConnect+"/"+this.namespace;
		
		String kafka_autocreatetopic = System.getProperty("kafka_autocreatetopic");
		String kafka_partitions = System.getProperty("kafka_partitions");
		String kafka_replications = System.getProperty("kafka_replications");

		if(!StringUtils.isEmpty(kafka_partitions)){
			try{
				partitions = Integer.parseInt(kafka_partitions);
			}catch(Exception ex){
				partitions = 5;
			}
		}
		
		if(!StringUtils.isEmpty(kafka_replications)){
			try{
				replications = Integer.parseInt(kafka_replications);
			}catch(Exception ex){
				replications = 5;
			}
		}
		
		if(!StringUtils.isEmpty(kafka_autocreatetopic) && kafka_autocreatetopic.equalsIgnoreCase("true")){
			autocreatetopic = true;
		}
		
		log.info("TopicManager autocreatetopic="+autocreatetopic+" partitions="+partitions+",replications="+replications);
		
		this.namespace = zkRoot;
		this.zkClient = ZookeeperClientFactory.getInstace().getZKClient(this.namespace);
		
		this.zkUtils = ZkUtils.apply(zkAddress,30000,30000,JaasUtils.isZkSecurityEnabled());
	}
	
	
	/**
	 * 添加topic
	 * @param topicName topic名称
	 * @param partitions  分区数
	 * @param replications 复制数
	 * @return
	 */
	public void createTopicIfNotExist(String topicName){
		if(autocreatetopic == false){
			return;
		}
		this.createTopicIfNotExist(topicName, partitions, replications);
	}
			
			
	/**
	 * 添加topic
	 * @param topicName topic名称
	 * @param partitions  分区数
	 * @param replications 复制数
	 * @return
	 */
	public void createTopicIfNotExist(String topicName,int partitions,int replications){
		try{
			List<String> allTopicNames = this.getAllTopicNames();
			if(allTopicNames.contains(topicName)){
				log.warn("topic="+topicName+" already exist in kafka");
			    return;
			}
			
			String[] options = new String[]{  
				    "--create",  
				    "--zookeeper",  
				    this.zkAddress,  
				    "--partitions",  
				    ""+partitions, 
				    "--topic",  
				    topicName, 
				    "--replication-factor",  
				    ""+replications
				 };  
			TopicCommandOptions commandOptions = new TopicCommandOptions(options);
		    TopicCommand.createTopic(this.zkUtils, commandOptions);
		    log.info("create topic="+topicName+" partitions="+partitions+",replications="+replications+" completely!");
		}catch(Exception ex){
			log.error("create topic="+topicName+" partitions="+partitions+",replications="+replications+" catch an exception",ex);
		}
	}
	
	/**
	 * 删除topic
	 * @param topicName topic名称
	 * @return
	 */
	public void deleteTopic(String topicName){
		try{
			List<String> allTopicNames = this.getAllTopicNames();
			if(!allTopicNames.contains(topicName)){
				log.warn("topic="+topicName+" not exist in kafka");
			    return;
			}
			String zkConnect = System.getProperty("zkconnect");
			String zkAddress = zkConnect+"/"+this.namespace;
			String[] options = new String[]{  
				    "--delete",  
				    "--zookeeper",  
				    zkAddress,  
				    "--topic",  
				    topicName
				 };  
			
			TopicCommandOptions commandOptions = new TopicCommandOptions(options);
		    TopicCommand.deleteTopic(this.zkUtils, commandOptions);
		    log.info("delete topic="+topicName+" completely!");
		}catch(Exception ex){
			log.error("delete topic="+topicName+" catch an exception",ex);
		}
	}
	
	
	/**
	 * 获取所有的topic名称
	 * @return
	 */
	public List<String> getAllTopicNames() throws Exception{
		List<String> allTopicNames = this.zkClient.getChildren("/config/topics");
		return allTopicNames;
	}
	
	
	public static void main(String []args){
		MessageClientFactory.getInstance();
		TopicManager.getInstance().createTopicIfNotExist("YWXT_topic17");
	}

}
