package com.hisign.sdk.common.util.serializer;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @Title:
 *  Kryo对象序列化工具
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin
 * @E-mail:lnj2050@hotmail.com
 * @create time：Jul 31, 2013 9:06:03 PM
 * @version 1.0
 */
public class KryoSerializer<T> implements ISerializer<T>,Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // 日志
    private static Logger log = LoggerFactory.getLogger(KryoSerializer.class);

    
    private Kryo kryo = null;

	public KryoSerializer() {
		kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
	}

	@Override
	public byte[] serialize(T obj){
	    if(obj == null){
	        return null;
	    }
		try{
			return serialize(obj, -1);
		}catch(Throwable tr){
		    log.error("serialize catch an exception",tr);
		}
		
		return null;
	}

	public synchronized byte[] serialize(T obj, int maxBufferSize) {
	    if(obj == null){
	        return null;
	    }
		Output output = new Output(1024, maxBufferSize);
		kryo.writeClassAndObject(output, obj);
		return output.toBytes();
	}

	@Override
	public synchronized T unserialize(byte[] bytes){
	    if(bytes == null || bytes.length <= 0){
	        return null;
	    }
	    
		try{
			Input input = new Input(bytes);
			return (T)kryo.readClassAndObject(input);
		}catch(Throwable tr){
		    log.error("unserialize catch an exception",tr);
		}
		
		return null;
	}

}
