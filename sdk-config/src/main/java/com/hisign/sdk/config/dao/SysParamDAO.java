package com.hisign.sdk.config.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.config.datasource.SQLUtil;
import com.hisign.sdk.config.entity.SysParam;

/**
 * @Title:
 *   系统参数DAO
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj 
 * @create time：2016年6月24日  下午5:50:32
 * @version 1.0
 */
public class SysParamDAO extends AbstractDAO {
	
	private static Logger log = LoggerFactory.getLogger(SysParamDAO.class);
	
	public SysParamDAO(){
		SQLUtil.getInstance();
	}
	
	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @param paramType 参数类型
	 * @return 系统参数 key=属性名 value=属性值
	 */
	public Map<String,String>  getProperties(String systemID,String paramType){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			String sql = "select PARAM_NAME,PARAM_VALUE from UAOP_SYSPARAM where SYSTEM_ID=? and PARAM_TYPE=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, systemID);
			stmt.setString(2, paramType);
			rs = stmt.executeQuery();
			
			Map<String,String> map = new HashMap<String,String>();
			while(rs.next()){
				String paramName = rs.getString("PARAM_NAME");
				String paramValue = rs.getString("PARAM_VALUE");
				map.put(paramName, paramValue);
			}
			
			return map;
		}catch(Exception ex){
			log.error("getProperties by systemID="+systemID+",paramType="+paramType+" catch an exception",ex);
		}finally{
			this.closeConnection(conn, stmt, rs);
		}
		
		return null;
	}
	
	
	/**
	 * 获取系统参数
	 * @param systemID  系统唯一标识
	 * @return Map<paramType，Map<参数名，参数值>>
	 */
	public Map<String,Map<String,String>>  getProperties(String systemID){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = this.getConnection();
			String sql = "select PARAM_TYPE,PARAM_NAME,PARAM_VALUE from UAOP_SYSPARAM where SYSTEM_ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, systemID);
			rs = stmt.executeQuery();
			
			Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
			while(rs.next()){
				String paramType = rs.getString("PARAM_NAME");
				String paramName = rs.getString("PARAM_NAME");
				String paramValue = rs.getString("PARAM_VALUE");
				Map<String,String> paramMap = null;
				if(map.containsKey(paramType)){
					paramMap = map.get(paramType);
				}else{
					paramMap = new HashMap<String,String>();
					map.put(paramType, paramMap);
				}
				paramMap.put(paramName, paramValue);
			}
			
			return map;
		}catch(Exception ex){
			log.error("getProperties by systemID="+systemID+" catch an exception",ex);
		}finally{
			this.closeConnection(conn, stmt, rs);
		}
		
		return null;
	}

	
	/**
	 * 保存系统参数
	 * @param sysParamList
	 * @return
	 */
	public boolean saveParams(List<SysParam> sysParamList){
		Connection conn = null;
		try{
			conn = this.getConnection();
			conn.setAutoCommit(false);
			for(SysParam param : sysParamList){
				this.saveParam(param, conn);
			}
			
			conn.commit();
			
			return true;
		}catch(Throwable ex){
			log.error("saveParams size="+sysParamList.size()+" catch an exception",ex);
			try {
				conn.rollback();
			} catch (SQLException e) {
			}
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			this.closeConnection(conn, null, null);
		}
		
		return false;
	}
	
	
	/**
	 * 保存系统参数
	 * @param sysParamList
	 * @return
	 */
	public boolean saveParam(SysParam param){
		Connection conn = null;
		try{
			conn = this.getConnection();
			this.saveParam(param, conn);
			return true;
		}catch(Throwable ex){
			log.error("saveParam param="+param.toString()+" catch an exception",ex);
		}finally{
			this.closeConnection(conn, null, null);
		}
		
		return false;
	}
	
	
	
	/**
	 * 保存参数
	 * @param param
	 * @param conn
	 * @return
	 */
	public void saveParam(SysParam param,Connection conn) throws Throwable{
		boolean isExist = this.isParamExist(param, conn);
		if(isExist){
		    this.updateParam(param, conn);
		}else{
			this.insertParam(param, conn);
		}
	}
	
	/**
	 * 新建参数
	 * @param param
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private void insertParam(SysParam param,Connection conn) throws Throwable{
		PreparedStatement stmt = null;
		try{
			log.info("param="+param.toString());
			String sql = "insert into  UAOP_SYSPARAM(SYSTEM_ID,PARAM_TYPE,PARAM_NAME,PARAM_VALUE,CREATE_USER,CREATE_TIME,LAST_MODIFY_TIME,EXT_STR1,EXT_STR2,EXT_STR3) values(?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,param.getSystemID());
			stmt.setString(2,param.getParamType());
			stmt.setString(3,param.getParamName());
			stmt.setString(4,param.getParamValue());
			stmt.setString(5, param.getCreateUser());
			long createTime = param.getCreateTime();
			if(createTime <= 0){
				createTime = System.currentTimeMillis();
			}
			stmt.setLong(6, createTime);
			stmt.setLong(7, createTime);
			stmt.setString(8, param.getExtStr1());
            stmt.setString(9, param.getExtStr2());
            stmt.setString(10, param.getExtStr3());
			
            stmt.executeUpdate();
            
		}catch(Throwable ex){
			log.error("",ex);
		    throw ex;
		}finally{
			this.closeConnection(null, stmt, null);
		}
	}
	
	
	/**
	 * 新建参数
	 * @param param
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private void updateParam(SysParam param,Connection conn) throws Exception{
		PreparedStatement stmt = null;
		try{
			String sql = "update UAOP_SYSPARAM set PARAM_VALUE=?,LAST_MODIFY_TIME=?,EXT_STR1=?,EXT_STR2=?,EXT_STR3=? where SYSTEM_ID=? and PARAM_TYPE=? and PARAM_NAME=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,param.getParamValue());
			long updateTime = -1;
			if(param.getUpdateTime() == null || param.getUpdateTime().longValue() <= 0){
				updateTime = System.currentTimeMillis();
			}else{
				updateTime = param.getUpdateTime();
			}
			stmt.setLong(2, updateTime);
			stmt.setString(3, param.getExtStr1());
            stmt.setString(4, param.getExtStr2());
            stmt.setString(5, param.getExtStr3());
            
            stmt.setString(6,param.getSystemID());
			stmt.setString(7,param.getParamType());
			stmt.setString(8,param.getParamName());
			
            stmt.executeUpdate();
		}catch(Exception ex){
		    throw ex;
		}finally{
			this.closeConnection(null, stmt, null);
		}
	}
	
	
	
	/**
	 * 保存系统参数
	 * @param param
	 * @return
	 */
	private boolean isParamExist(SysParam param,Connection conn) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			String sql = "select SYSTEM_ID from UAOP_SYSPARAM where SYSTEM_ID=? and PARAM_TYPE=? and PARAM_NAME=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,param.getSystemID());
			stmt.setString(2,param.getParamType());
			stmt.setString(3,param.getParamName());
			rs = stmt.executeQuery();
			if(rs.next()){
				return true;
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			this.closeConnection(null, stmt, rs);
		}
		
		return false;
	}
}
