package com.hisign.sdk.config.entity;

import java.io.Serializable;

/**
 * @Title:
 *  系统配置参数实体类
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月23日  下午4:22:34
 * @version 1.0
 */
public class SysParam implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//业务系统编码
	private String systemID;
	
	//参数类型
	private String paramType;
	
	//参数名
	private String paramName;
	
	//参数值
	private String paramValue;
	
	//创建用户
	private String createUser;
	
	//创建时间
	private Long  createTime;
	
	//修改时间
	private Long updateTime;
	
	private String extStr1;
	
	private String extStr2;
	
	private String extStr3;

	public String getSystemID() {
		return systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getExtStr1() {
		return extStr1;
	}

	public void setExtStr1(String extStr1) {
		this.extStr1 = extStr1;
	}

	public String getExtStr2() {
		return extStr2;
	}

	public void setExtStr2(String extStr2) {
		this.extStr2 = extStr2;
	}

	public String getExtStr3() {
		return extStr3;
	}

	public void setExtStr3(String extStr3) {
		this.extStr3 = extStr3;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SysParam [systemID=");
		builder.append(systemID);
		builder.append(", paramType=");
		builder.append(paramType);
		builder.append(", paramName=");
		builder.append(paramName);
		builder.append(", paramValue=");
		builder.append(paramValue);
		builder.append(", createUser=");
		builder.append(createUser);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", extStr1=");
		builder.append(extStr1);
		builder.append(", extStr2=");
		builder.append(extStr2);
		builder.append(", extStr3=");
		builder.append(extStr3);
		builder.append("]");
		return builder.toString();
	}
}
