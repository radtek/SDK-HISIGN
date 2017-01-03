package com.hisign.sdk.common.util.serializer;
/**
 * @Title:
 *  序列化异常
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 4, 2013  3:31:12 PM
 * @version 1.0
 */
public class SerializationException extends RuntimeException {
	
	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 *
	 * @param msg
	 * @param cause
	 */
	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructs a new <code>SerializationException</code> instance.
	 *
	 * @param msg
	 */
	public SerializationException(String msg) {
		super(msg);
	}

}
