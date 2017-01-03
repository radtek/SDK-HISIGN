package com.hisign.sdk.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Title:
 *  Map操作根据类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 22, 2013  2:44:43 PM
 * @version 1.0
 */
public class MapUtils {

	/**
	 * Map去掉为空的Element
	 * @param map
	 * @return
	 */
	public static Map<String, String> trunkNull(Map<String,String> map){
		Iterator<Entry<String, String>> iter=map.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, String> entry=iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			if((key == null || key.trim().equals("") || key.trim().equalsIgnoreCase("null"))
					|| (value == null || value.trim().equals("") || value.trim().equalsIgnoreCase("null"))){
				iter.remove();
			}
		}
		return map;
	}
	
	/**
	 * 获取并删除该主键
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getStringAndRemove(Map<String,String> map,String key){
		String value = map.get(key);
		map.remove(key);
        return value;
	}
	
	public static long getLongAndRemove(Map<String,String> map,String key){
		String value = map.get(key);
		map.remove(key);
		
		long lValue = -1;
		if(StringUtils.isEmpty(value)){
			return -1;
		}
		
		try{
			lValue = Long.parseLong(value);
		}catch(Exception ex){
		}
		
        return lValue;
	}
	
	public static int getIntAndRemove(Map<String,String> map,String key){
		String value = map.get(key);
		map.remove(key);
		
		int iValue = -1;
		if(StringUtils.isEmpty(value)){
			return -1;
		}
		
		try{
			iValue = Integer.parseInt(value);
		}catch(Exception ex){
		}
		
        return iValue;
	}
	   
    public static double getDoubleAndRemove(Map<String,String> map,String key){
        String value = map.get(key);
        map.remove(key);
        
        double dValue = -1;
        if(StringUtils.isEmpty(value)){
            return -1;
        }
        
        try{
            dValue = Double.parseDouble(value);
        }catch(Exception ex){
        }
        
        return dValue;
    }
    
	public static boolean getBooleanAndRemove(Map<String,String> map,String key){
		String value = map.get(key);
		map.remove(key);
		
		if(value == null || value.trim().equals("")){
			return false;
		}
		
		if(value.trim().equalsIgnoreCase("true")){
			return true;
		}
        return false;
	}
	
	public static long getLong(Map<String,String> map,String key){
        String value = map.get(key);
        long lValue = -1;
        if(StringUtils.isEmpty(value)){
            return -1;
        }
        
        try{
            lValue = Long.parseLong(value);
        }catch(Exception ex){
        }
        
        return lValue;
    }
    
    public static int getInt(Map<String,String> map,String key){
        String value = map.get(key);
        int iValue = -1;
        if(StringUtils.isEmpty(value)){
            return -1;
        }
        
        try{
            iValue = Integer.parseInt(value);
        }catch(Exception ex){
        }
        
        return iValue;
    }
    
    public static boolean getBoolean(Map<String,String> map,String key){
        String value = map.get(key);
        if(value == null || value.trim().equals("")){
            return false;
        }
        
        if(value.trim().equalsIgnoreCase("true")){
            return true;
        }
        return false;
    }
    
    public static double getDouble(Map<String,String> map,String key){
        String value = map.get(key);
        double dValue = -1;
        if(StringUtils.isEmpty(value)){
            return -1;
        }
        
        try{
            dValue = Double.parseDouble(value);
        }catch(Exception ex){
        }
        
        return dValue;
    }
}
