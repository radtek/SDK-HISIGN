package com.hisign.sdk.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hisign.sdk.config.entity.SysParam;
import com.hisign.sdk.config.service.SysParamService;
import com.hisign.sdk.config.service.impl.SysParamServiceImpl;

/**
 * @Title:
 *
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月27日  上午9:39:55
 * @version 1.0
 */
public class TestSysParamService {
	
	public void testgetSysParams(){
		SysParamService service = new SysParamServiceImpl();
		Map<String,Map<String,String>> result = service.getProperties("UAOP");
		System.out.println("result="+result);
	}
	
	public void testgetSysParams(String systemID,String paramType){
		SysParamService service = new SysParamServiceImpl();
		Map<String,String> result = service.getProperties(systemID,paramType);
		System.out.println("result="+result);
	}
	
	public void testSaveParam(){
		SysParamService service = new SysParamServiceImpl();
		SysParam param = new SysParam();
		param.setSystemID("UAOP");
		param.setParamType("REDIS");
		param.setParamName("param_1");
		param.setParamValue("172.16.0.113:56379");
		param.setCreateUser("Test");
		param.setCreateTime(System.currentTimeMillis());
		boolean result = service.saveParam(param);
		System.out.println("result="+result);
	}
	
	
	public void testSaveMqParam(){
		SysParamService service = new SysParamServiceImpl();
		List<SysParam> list = new ArrayList<SysParam>();
		SysParam zkconnect = new SysParam();
		zkconnect.setSystemID("UAOP");
		zkconnect.setParamType("ZOOKEEPER");
		zkconnect.setParamName("zkconnect");
		zkconnect.setParamValue("172.16.0.114:52181");
		zkconnect.setCreateUser("Test");
		zkconnect.setCreateTime(System.currentTimeMillis());
		list.add(zkconnect);
		
		
		SysParam msgservertype = new SysParam();
		msgservertype.setSystemID("UAOP");
		msgservertype.setParamType("MQCLIENT");
		msgservertype.setParamName("msgservertype");
		msgservertype.setParamValue("kafka");
		msgservertype.setCreateUser("Test");
		msgservertype.setCreateTime(System.currentTimeMillis());
		list.add(msgservertype);
		
		SysParam kafka_brokerlist = new SysParam();
		kafka_brokerlist.setSystemID("UAOP");
		kafka_brokerlist.setParamType("MQCLIENT");
		kafka_brokerlist.setParamName("kafka_brokerlist");
		kafka_brokerlist.setParamValue("172.16.0.114:59092");
		kafka_brokerlist.setCreateUser("Test");
		kafka_brokerlist.setCreateTime(System.currentTimeMillis());
		list.add(kafka_brokerlist);
		
		SysParam kafka_zkroot = new SysParam();
		kafka_zkroot.setSystemID("UAOP");
		kafka_zkroot.setParamType("MQCLIENT");
		kafka_zkroot.setParamName("kafka_zkroot");
		kafka_zkroot.setParamValue("kafka");
		kafka_zkroot.setCreateUser("Test");
		kafka_zkroot.setCreateTime(System.currentTimeMillis());
		list.add(kafka_zkroot);
		
		boolean result = service.saveParams(list);
		System.out.println("result="+result);
	}
	
	
	public void testSaveParamList(){
		SysParamService service = new SysParamServiceImpl();
		List<SysParam> list = new ArrayList<SysParam>();
		boolean result = false;
		int count = 100;
		for(int i = 0;i < count; i++){
			SysParam param = new SysParam();
			param.setSystemID("UAOP");
			param.setParamType("REDIS");
			param.setParamName("aaa_"+(i+1));
			param.setParamValue("aaa_value"+(i+1));
			param.setCreateUser("Test");
			param.setCreateTime(System.currentTimeMillis());
			
			list.add(param);
//			result = service.saveParam(param);
		}
		
		result = service.saveParams(list);
		System.out.println("result="+result);
	}

	public static void main(String[] args) {
		TestSysParamService test = new TestSysParamService();
//		test.testSaveParam();
//		test.testSaveParamList();
		test.testSaveMqParam();
		test.testgetSysParams("UAOP","MQCLIENT");

	}

}
