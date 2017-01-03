package com.hisign.sdk.msg;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 *  消息发送模拟器
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2013年12月11日  上午11:16:16
 * @version 1.0
 */
public class SendMsgSimulator {
    /**  日志文件 */
    private static  Logger log = LoggerFactory.getLogger(SendMsgSimulator.class);
    
    private ExecutorService pool = null;
    
//    private List<Message> list = new ArrayList<Message>();
    
    public SendMsgSimulator(int poolSize){
        MessageClient.getInstance();
        pool = Executors.newFixedThreadPool(poolSize);
    }
    
    
    public void sendMsg(int count){
        try{
            long begin = System.currentTimeMillis();
           
            for(int i = 0;i < count; i++){
                final Message message = new Message();
                Map userObj = this.buildMsgData(i);
                //String userObj = "message mfdalfjdalfdafjda;lfl;dafdafl;djafl;dafdlfdalfda;fl"+i;
                message.setUserObject(userObj);
//                list.add(message);
                Future f = pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //log.info("");
                            MessageClient.getInstance().send("test",message);
                            //log.info("send message "+message.toString()+" sucessfully!");
                        } catch (Exception e) {
                        }
                    }
                    
                });
            }
            
            while(threadStatus() == true){
                break;
            }
            
            long during = System.currentTimeMillis() - begin;
            double speed = (double)count*1000/during;
            log.info("sendMsg count="+count+",during="+during+",speed="+speed+"条/s");
        }catch(Throwable tr){
            tr.printStackTrace();
        }
    }
    
    public void sendOrderedMsg(int count){
        try{
            long begin = System.currentTimeMillis();
           
            for(int i = 0;i < count; i++){
                final Message message = new Message();
                Map userObj = this.buildMsgData(i);
                //String userObj = "message mfdalfjdalfdafjda;lfl;dafdafl;djafl;dafdlfdalfda;fl"+i;
                message.setUserObject(userObj);
                message.setTopic("test");
//                list.add(message);
                Future f = pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //log.info("");
                            MessageClient.getInstance().send(message,true);
                            log.info("send message "+message.toString()+" sucessfully!");
                        } catch (Exception e) {
                        }
                    }
                    
                });
            }
            
            while(threadStatus() == true){
                break;
            }
            
            long during = System.currentTimeMillis() - begin;
            double speed = (double)count*1000/during;
            log.info("sendMsg count="+count+",during="+during+",speed="+speed+"条/s");
        }catch(Throwable tr){
            tr.printStackTrace();
        }
    }
    
    /**
     * 构建消息数据
     * @param i
     * @return
     */
    private Map<String, String> buildMsgData(int i){
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("filed1_"+i, "value1_"+i);
        map.put("filed2_"+i, "value2_"+i);
        map.put("filed3_"+i, "value3_"+i);
        map.put("filed4_"+i, "value4_"+i);
        map.put("filed5_"+i, "value5_"+i);
        return map;
    }
    
    //判断线程池是否结束 
    public boolean threadStatus(){
        pool.shutdown();
        while(true){
            if(pool.isTerminated()){
                return true;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
    }
    
    private Message createMessage(int i){
        Message msg = new Message();
        msg.setType(600001); //新增用户
        msg.setUserObject(this.buildMsgData(i));
        msg.setTopic("test");
        return msg;
    }
    

    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        int poolSize = Integer.parseInt(args[1]);
        SendMsgSimulator test = new SendMsgSimulator(poolSize);
        log.info("begin to send count="+count+",poolSize="+poolSize);
        test.sendMsg(count);
    }

}
