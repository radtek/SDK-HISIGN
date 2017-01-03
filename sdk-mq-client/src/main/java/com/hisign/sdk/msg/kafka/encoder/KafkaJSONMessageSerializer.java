package com.hisign.sdk.msg.kafka.encoder;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.StringUtils;
import com.hisign.sdk.msg.Message;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import net.sf.json.JSONObject;

/**
 * @Title:
 *   Kafka的消息json序列化器
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年7月1日  上午11:37:32
 * @version 1.0
 */
public class KafkaJSONMessageSerializer implements Encoder<Message> {
    
    private static Logger log = LoggerFactory.getLogger(KafkaMessageSerializer.class);
    
    public String charSet = "utf-8";
    
    public static final String KEY_USEROBJECTCLASS = "userObjectClass";
    
    public KafkaJSONMessageSerializer(VerifiableProperties props){
    }
    
    public KafkaJSONMessageSerializer(){
    }

    @Override
    public byte[] toBytes(Message msg) {
		byte[] bytes = null;
		try {
			JSONObject jsonObj = JSONObject.fromObject(msg);
			String jsonString = jsonObj.toString();
			log.debug("toBytes jsonString="+jsonString);
			bytes = jsonString.getBytes(charSet);
		} catch (Exception e) {
			log.error("serialize catch an exception",e);
		}
		return bytes;
    }
    
    
    public Message toMessage(byte[] bytes){
    	try{
	    	String jsonString =  new String(bytes,charSet);
	    	log.debug("toMessage jsonString="+jsonString);
	    	JSONObject jsonObj = JSONObject.fromObject(jsonString);
	    	String userObjectClass = null;
	    	if(jsonObj.has(KEY_USEROBJECTCLASS)){
	    		userObjectClass = jsonObj.getString(KEY_USEROBJECTCLASS);
	    	}
	    	
	    	Message message = null;
	    	Map classMap = new HashMap();
	    	if(!StringUtils.isEmpty(userObjectClass)){ 
	    		Class classT = Class.forName(userObjectClass); 
	    		classMap.put("userObject", classT);
	    	}else{ //如果没有设置，则使用HashMap对象
	    		classMap.put("userObject", HashMap.class);
	    	}
	    	
	    	message = (Message)JSONObject.toBean(jsonObj, Message.class, classMap);
	    	
	    	return message;
    	}catch(Throwable tr){
    		log.error("toMessage catch an exception",tr);
    	}
    	
    	return null;
    }
}
