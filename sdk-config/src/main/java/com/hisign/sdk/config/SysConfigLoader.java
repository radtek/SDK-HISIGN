package com.hisign.sdk.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.config.service.SysParamService;
import com.hisign.sdk.config.service.impl.SysParamServiceImpl;

/**
 * @Title:
 *   将配置参数load到System.Properties中
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月28日  上午9:32:02
 * @version 1.0
 */
public class SysConfigLoader {
	
	private static Logger log = LoggerFactory.getLogger(SysConfigLoader.class);
	
	private static SysConfigLoader instance = new SysConfigLoader();
	
	public static SysConfigLoader getInstance(){
		return instance;
	}
	
	private SysConfigLoader(){
	}
	
	/**
	 * 加载系统参数到System.Properties中
	 * @param systemID
	 */
	public void loadSysConfig(String systemID){
		SysParamService service = new SysParamServiceImpl();
		Map<String,Map<String,String>> map = service.getProperties(systemID);
		if(map == null){
			return;
		}
		
		Iterator<Map<String,String>> it = map.values().iterator();
		while(it.hasNext()){
			Map<String,String> paramMap = it.next();
			System.getProperties().putAll(paramMap);
		}
		
		log.info("loadSysConfig "+map.toString()+" to System.properties completely!");
		
		Properties props = LocalSystemProperties.getInstance().getProps();
        System.getProperties().putAll(props);
        
        log.info("loadSysConfig rewrite "+props.toString()+" from local.properties to System.properties completely!");
	}
	
	
	/**
	 * 加载系统参数到System.Properties中
	 * @param systemID
	 * @param paramType
	 */
	public void loadSysConfig(String systemID,String paramType){
		SysParamService service = new SysParamServiceImpl();
		Map<String,String> map = service.getProperties(systemID,paramType);
		if(map == null){
			return;
		}
		
		System.getProperties().putAll(map);
		log.info("loadSysConfig systemID="+systemID+",paramType="+paramType+" params="+map.toString()+" to System.properties completely!");
		
		Properties props = LocalSystemProperties.getInstance().getProps();
        System.getProperties().putAll(props);
        
        log.info("loadSysConfig rewrite "+props.toString()+" from local.properties to System.properties completely!");
	}

}
