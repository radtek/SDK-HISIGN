package com.hisign.sdk.msg.demo;

import com.hisign.sdk.msg.Message;
import com.hisign.sdk.msg.MessageClient;

public class MsgSendDemo {
	
	public void sendMessage(){
		//构建消息
		Person p1 = new Person(1,"张三",1);
		Message msg1 = new Message();
		msg1.setType(600001); //新建Person
		msg1.setTopic("YWXT_topic1"); //消息topic
		msg1.setUserObject(p1);
		
		try {
			MessageClient.getInstance().send(msg1);
			System.out.println("send msg1+"+msg1.toString()+"+ successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MsgSendDemo demo = new MsgSendDemo();
		demo.sendMessage();
	}

}
