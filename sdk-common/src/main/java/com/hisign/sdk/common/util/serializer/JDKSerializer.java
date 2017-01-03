package com.hisign.sdk.common.util.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 *  JDK序列化工具
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 4, 2013  3:25:37 PM
 * @version 1.0
 */
public class JDKSerializer<T> implements ISerializer<T> {
	
    // 日志
    private static Logger log = LoggerFactory.getLogger(JDKSerializer.class);

	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	@Override
	public byte[] serialize(T object){
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception ex) {
			log.error("serialize catch an exception",ex);
		}finally{
			try{
			    if(oos != null){
			    	oos.close();
			    	oos = null;
			    }
			    
			    if(baos != null){
			    	baos.close();
			    	baos = null;
			    }
			}catch(Exception ex){
				
			}
		}
		
		return null;
	}

	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	@Override
	public T unserialize(byte[] bytes){
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			T obj = (T)ois.readObject();
			return obj;
		} catch (Exception ex) {
			log.error("unserialize catch an exception",ex);
		}finally{
			try{
			    if(ois != null){
			    	ois.close();
			    	ois = null;
			    }
			    
			    if(bais != null){
			    	bais.close();
			    	bais = null;
			    }
			}catch(Exception ex){
				
			}
		}
		
		return null;
	}
}
