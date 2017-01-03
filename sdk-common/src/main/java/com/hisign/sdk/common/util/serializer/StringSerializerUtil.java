package com.hisign.sdk.common.util.serializer;
/**
 * @Title:
 *  字符串序列化工具类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 12, 2013  10:55:22 AM
 * @version 1.0
 */
public class StringSerializerUtil {
	
	public static String charSet = "utf-8";
	
	/**
	 * 字符串转换为byte数组
	 * @param str
	 * @return
	 */
	public static byte[] str2bytes(String str) throws Exception{
		byte[] bytes = str.getBytes(charSet);
		return bytes;		
	}

	/**
	 * 将byte数组转换为字符串
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String bytes2str(byte []bytes) throws Exception{
		String str = new String(bytes,charSet);
		return str;
	}
}
