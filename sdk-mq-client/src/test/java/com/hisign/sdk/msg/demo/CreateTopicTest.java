package com.hisign.sdk.msg.demo;

import java.util.Set;

import kafka.admin.TopicCommand;

/**
 * @Title:
 *
 * @description:
 * 
 * @author lnj 
 * @create time：2016年7月13日  上午9:04:06
 */
public class CreateTopicTest {
	
	
	public void createTopic(String zkAddress,String topicName,int partitions,int reps){
		String[] options = new String[]{  
			    "--create",  
			    "--zookeeper",  
			    zkAddress,  
			    "--partitions",  
			    ""+partitions, 
			    "--topic",  
			    topicName, 
			    "--replication-factor",  
			    ""+reps
			 };  
	    TopicCommand.main(options);  
	    System.out.println("create topic "+topicName+" completely!");
	}
	
	
	/**
	 * 列出所有的Topic名
	 * @return
	 */
	public void listTopics(String zkAddress){
		String[] options = new String[]{
				"--list",
				"--zookeeper",
				"localhost:2181"
			};
	}

	public static void main(String[] args) {
		CreateTopicTest test = new CreateTopicTest();
		test.createTopic("172.16.0.114:52181/kafka","YWXT_topic16",1,1);
		
	}

}
