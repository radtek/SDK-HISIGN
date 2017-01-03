package com.hisign.sdk.common.util.id;

import java.util.Random;

/**
 * @Title:
 *   动态生成ID
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年2月11日  下午2:49:03
 * @version 1.0
 */
public class IDAutoGenerater {

    private static int id = 0; 
    
    private static final int MAX_VALUE=1000;
    
    /**
     * 产生id
     * @return
     */
    private static  int next(){
        if(id >= MAX_VALUE){
            id = 0;
        }
        return id++;
    }
    
    /**
     * 自动产生变量名称
     * @return
     */
    public synchronized  static  String  autoGenerateID(){
        Random  r = new Random();
        int  randomNumber = r.nextInt(100);
        long currentTime = System.currentTimeMillis();
        int sequence =  next();
        String id = randomNumber +"_"+ currentTime+"_"+sequence;
        return id;
    }
    
    public static void main(String []args){
        System.out.println("1=="+autoGenerateID());
        System.out.println("2=="+autoGenerateID());
    }
}
