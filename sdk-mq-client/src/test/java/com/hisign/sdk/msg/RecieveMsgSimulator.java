package com.hisign.sdk.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Title:
 *
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 7, 2013  2:40:41 PM
 * @version 1.0
 */
public class RecieveMsgSimulator {
    /**  日志文件 */
    private Logger log = LoggerFactory.getLogger(RecieveMsgSimulator.class);
    
    public static long begin = 0;
    public static long during  =0;
    public static long count = 0;
    
    public RecieveMsgSimulator(){
        MessageClient.getInstance();
    }
    
	public void registMessageHandler(final String topic){
		try{
			MessageClient.getInstance().addMessageHandler(topic,"test",new MessageHandler(){
				@Override
				public void onMessage(Message msg) {
				    try{
				        if(count == 0){
				            begin = System.currentTimeMillis();
				        }
				        count++;
//				        log.info("receive msg "+msg.toString());
				        during = System.currentTimeMillis() - begin;
//				        if(count % 10 == 0){
//				            double speed = (double)count*1000/during;
//	                        log.info("recieved:count="+count+",during="+during+"ms,speed="+speed+"条/s");
//				        }
				        log.info("recieved a message");
				    }catch(Throwable tr){
				        tr.printStackTrace();
				    }
				}
			});
			System.out.println("setup messsage listener topic="+topic+" completely!");
		}catch(Throwable tr){
			tr.printStackTrace();
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    RecieveMsgSimulator test = new RecieveMsgSimulator();
		test.registMessageHandler("test");
	}
}
