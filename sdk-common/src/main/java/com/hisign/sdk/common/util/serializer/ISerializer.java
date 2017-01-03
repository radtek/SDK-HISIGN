package com.hisign.sdk.common.util.serializer;
/**
 * @Title:
 *   对象序列化接口
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Jul 31, 2013  9:03:44 PM
 * @version 1.0
 */
public interface ISerializer<T> {
	
	/**
	 * 将对象序列化为byte[]
	 * @param obj
	 * @return
	 */
	public byte[] serialize(T obj);
	
	/**
	 * 将byte数组反序列化为对象
	 * @param bytes
	 * @return
	 */
	public T unserialize(byte[] bytes); 

}
