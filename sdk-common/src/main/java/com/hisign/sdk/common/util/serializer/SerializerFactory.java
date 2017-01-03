package com.hisign.sdk.common.util.serializer;
/**
 * @Title:
 *  序列化工厂类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 4, 2013  3:18:26 PM
 * @version 1.0
 */
public class SerializerFactory {
	
	//Jdk序列化类型
	public static final String TYPE_JDK = "jdk";
	
	//kryo序列化类型
	public static final String TYPE_KRYO = "kryo";
	
	//序列化类型
	public static String defaultType = TYPE_KRYO;
	
	
	/**
	 * 创建序列化器
	 * @return
	 */
	public static ISerializer createSerializer(){
		return createSerializer(defaultType);
	}
	
	/**
	 * 创建序列化器
	 * @param type 序列化器类型
	 * @return
	 */
	public static ISerializer createSerializer(String type){
		ISerializer serializer = null;
		if(type == null || type.trim().equals("")){
			serializer = new KryoSerializer();
			return serializer;
		}
		
		if(type.trim().equalsIgnoreCase(TYPE_KRYO)){ //kryo
			serializer = new KryoSerializer();
		}else if(type.trim().equalsIgnoreCase(TYPE_JDK)){ //jdk
			serializer = new JDKSerializer();
		}else{ //默认JDK
			serializer = new KryoSerializer();
		}
		
		return serializer;
	}

}
