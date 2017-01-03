package com.hisign.sdk.config.service;

import java.util.List;
import java.util.Map;

import com.hisign.sdk.config.entity.SysParam;

/**
 * @Title:
 *   系统参数服务
 * @description:
 *   1.提供统一系统参数读取
 *   2.提供统一系统参数配置
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月23日  下午4:40:45
 * @version 1.0
 */
public interface SysParamService {
	
	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @param paramType 参数类型
	 * @return 系统参数 key=属性名 value=属性值
	 */
	public Map<String,String>  getProperties(String systemID,String paramType);
	
	
	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @return Map<paramType，Map<参数名，参数值>>
	 */
	public Map<String,Map<String,String>>  getProperties(String systemID);
	
	
	/**
	 * 保存系统参数
	 * @param sysParamList
	 * @return
	 */
	public boolean saveParams(List<SysParam> sysParamList);
	
	/**
	 * 保存系统参数
	 * @param param
	 * @return
	 */
	public boolean saveParam(SysParam param);

}
