package com.hisign.sdk.msg.demo;


import com.hisign.sdk.msg.Message;
import com.hisign.sdk.msg.MessageClient;
import com.hisign.sdk.msg.MessageHandler;

public class MessageRecieveDemo {
	
	public void receiveMsg(){
		try{
			MessageClient.getInstance().addMessageHandler("YWXT_topic1", new MessageHandler(){
				@Override
				public void onMessage(Message msg) {
					if(msg.getType() == 600001){ //只处理新建Person消息类型
						Object obj = msg.getUserObject();
						if(obj instanceof Person){
							Person p1 = (Person)obj;
						    System.out.println("receive msg userObject="+p1.toString());
						}else{
							System.out.println("receive msg userObject type="+obj.getClass().getName()+" userObject="+obj.toString());
						}
						
					}
					
				}
			});
			System.out.println("start receiveMsg completey!");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("SYSTEMID", "YWXT");
		MessageRecieveDemo demo = new MessageRecieveDemo();
		demo.receiveMsg();
		System.in.read(); //等待，防止直接进程退出
	}

}
