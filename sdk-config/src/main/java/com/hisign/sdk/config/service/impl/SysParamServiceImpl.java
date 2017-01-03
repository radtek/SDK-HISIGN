package com.hisign.sdk.config.service.impl;

import java.util.List;
import java.util.Map;

import com.hisign.sdk.config.dao.SysParamDAO;
import com.hisign.sdk.config.entity.SysParam;
import com.hisign.sdk.config.service.SysParamService;

/**
 * @Title:
 *   系统参数服务
 * @description:
 *   1.提供统一系统参数读取
 *   2.提供统一系统参数配置
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月23日  下午4:42:37
 * @version 1.0
 */
public class SysParamServiceImpl implements SysParamService {

	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @param paramType 参数类型
	 * @return 系统参数 key=属性名 value=属性值
	 */
	public Map<String,String>  getProperties(String systemID,String paramType){
		SysParamDAO dao = new SysParamDAO();
		return dao.getProperties(systemID, paramType);
	}
	
	
	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @return Map<paramType，Map<参数名，参数值>>
	 */
	public Map<String,Map<String,String>>  getProperties(String systemID){
		SysParamDAO dao = new SysParamDAO();
		return dao.getProperties(systemID);
	}
	
	
	/**
	 * 保存系统参数
	 * @param sysParamList
	 * @return
	 */
	public boolean saveParams(List<SysParam> sysParamList){
		SysParamDAO dao = new SysParamDAO();
		return dao.saveParams(sysParamList);
	}
	
	
	/**
	 * 保存系统参数
	 * @param sysParamList
	 * @return
	 */
	public boolean saveParam(SysParam param){
		SysParamDAO dao = new SysParamDAO();
		return dao.saveParam(param);
	}
}
