package com.hisign.sdk.config.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;
import com.hisign.sdk.common.util.security.InvertibleDES;

/**
 * @Title:
 *   数据库连接
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 15, 2013  5:21:59 PM
 * @version 1.0
 */
public class SQLUtil {
	
	private static Logger log = LoggerFactory.getLogger(SQLUtil.class);
	
	//本地数据源
	private DruidDataSource localDS = null;
	
	public boolean isSyabe =false;
	public boolean isSaveByProbe = false ;
	public DataSourceParam dataSourceParam = null;
	public String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	
	/** boolean型数据的数据库值 */
	public static int FALSE = 0;
	public static int TRUE = 1;
	
    public static final String ORACLE = "oracle";
    public static final String SQLSERVER = "sqlserver";
    public static final String MYSQL = "mysql";
    public static final String SYBASE = "sybase";
	
	//连接数据打印
	public static ConnectionNumPrintThread connNumPrintThread = null;
	
	private static SQLUtil instance = null;
	
	public synchronized static SQLUtil getInstance(){
		if(instance == null){
			instance = new SQLUtil();
		}
		
		return instance;
	}
	
	
	private SQLUtil(){
		init();
	}
	
	private void init(){
		try {
			localDS = setupDataSource();
		} catch (Exception ex) {
			log.error("setupDataSource catch an exception",ex);
		}
	}
	
	/**
	 * 建立DataSource
	 * @throws Exception
	 */
	private DruidDataSource setupDataSource() throws Exception{
		dataSourceParam = DataSourceParam.getInstance();
		DruidDataSource dataSource = new DruidDataSource();
		jdbcDriver = dataSourceParam.getDriverClassName();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(dataSourceParam.getUrl());
		log.info("----------db_url="+dataSourceParam.getUrl());
		dataSource.setUsername(dataSourceParam.getUser());
		String password = dataSourceParam.getPassword();
		if(dataSourceParam.getIsDbPwdSecurity().equalsIgnoreCase("true")){
			log.info("Database password  decrypt:"+ password);
			InvertibleDES des = new InvertibleDES(InvertibleDES.DEFAULT_KEY);// 自定义密钥
			password = des.decrypt(password);
		}
		dataSource.setPassword(password);
		
		//这里设置连接参数
		dataSource.setMaxActive(dataSourceParam.getMaxActive());
		dataSource.setMinIdle(dataSourceParam.getMinIdle());
		dataSource.setMaxWait(dataSourceParam.getMaxWait());
		dataSource.setLogAbandoned(true);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(1800);
		  
		//设置重连机制
		dataSource.setTestWhileIdle(dataSourceParam.isTestWhileIdle());
		log.info("validationQuery="+dataSourceParam.getValidationQuery());
		dataSource.setValidationQuery(dataSourceParam.getValidationQuery());
		dataSource.setValidationQueryTimeout(dataSourceParam.getValidationQueryTimeout());
		dataSource.setTimeBetweenEvictionRunsMillis(dataSourceParam.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(dataSourceParam.getTimeBetweenEvictionRunsMillis());
		
		if(dataSourceParam.isPrintStatus()){ //启动定时打印连接池状态线程
			if(connNumPrintThread == null){
			    connNumPrintThread = new ConnectionNumPrintThread();
			    connNumPrintThread.start();
			}
		}
		
	    return dataSource;
	}
	
	/**
     * 当数据库连接配置发生变化的时候，重新初始化数据库连接池
     */
    public void resetupDataSource(){
        try{
            log.warn("********begin resetupDataSource ...*********");
            if(localDS != null){
                localDS.close();
                localDS = null;
            }
            
            log.warn("********closed old DataSource*********");
            localDS = setupDataSource();
            log.warn("********resetupDataSource completely!*********");
        }catch(Throwable tr){
            log.error("resetupDataSource catch an exception",tr);
        }
    }
	
	/**
	 * 获取数据库连接
	 */
	public Connection getConnection() {
		try {
			printConnectionNum();
			Connection conn = localDS.getConnection();
			return conn;
		} catch (SQLException e) {
			log.error("get Connection error ==",e);
			printConnectionNum();
		}

		return null;
	}
    
//    
//    public Connection getConnection(){
//    	try{
//	    	Class.forName(this.dataSourceParam.getDriverClassName());
//	    	Connection conn = DriverManager.getConnection(this.dataSourceParam.getUrl(), this.dataSourceParam.getUser(), this.dataSourceParam.getPassword());
//	    	return conn;
//    	}catch(Throwable ex){
//    		log.error("getConnection catch an exception",ex);
//    	}
//    	
//    	return null;
//    }
	
	/**
	 * 重置数据库连接
	 * @return
	 */
	public Connection resetConnection() {
		Connection conn = null;

		try {
			localDS = setupDataSource();
			conn = localDS.getConnection();
		} catch (Exception e) {
			log.error("resetConnection",e);
			printConnectionNum();
		}

		return conn;
	}

    public DataSourceParam getDataSourceParam() {
        return dataSourceParam;
    }
    
    public boolean isOrale(){
        if (jdbcDriver.toLowerCase().indexOf(ORACLE) >= 0){
            return true;
        }
        else return false ;
    }
    
    public boolean isSqlServer(){
        if (jdbcDriver.toLowerCase().indexOf(SQLSERVER) >= 0){
            return true;
        }
        else return false ;
    }
    
    public boolean isSybase(){
        if (jdbcDriver.toLowerCase().indexOf(SYBASE) >= 0){
            return true;
        }
        else return false ;
    }

    
    public boolean isMySql(){
        if (jdbcDriver.toLowerCase().indexOf(MYSQL) >= 0){
            return true;
        }
        else return false ;
    }
    
    /**
     * 打印日志
     */
    private void printConnectionNum(){
        try{
            if(localDS != null){
                int numActive = localDS.getActiveCount();
                int maxActive = localDS.getMaxActive();
                int maxIdle = localDS.getMaxIdle();
                long maxWait = localDS.getMaxWait();
                int numTest = localDS.getNumTestsPerEvictionRun();
                StringBuffer sb = new StringBuffer();
                sb.append("numActive=").append(numActive).append(",");
                sb.append("maxActive=").append(maxActive).append(",");
                sb.append("maxIdle=").append(maxIdle).append(",");
                sb.append("maxWait=").append(maxWait).append(",");
                sb.append("numTest=").append(numTest).append(",");
                log.info("[printConnectionNum] "+sb.toString());
            }else{
                log.info("[printConnectionNum] localDS is null");
            }
        }catch(Throwable tr){
            log.error("printConnectionNum catch an exception",tr);
        }
    }
    
    /**
     * 当前连接数打印线程
     * @author lnj2050
     *
     */
    public class ConnectionNumPrintThread extends Thread{
        private boolean cancel = false;

        @Override
        public void run() {
            while(!cancel){
                printConnectionNum();
                
                try {  
                    Thread.sleep( 5 * 60 * 1000);  
                } catch (InterruptedException e) {
                }
            }
        }

        public boolean isCancel() {
            return cancel;
        }

        public void setCancel(boolean cancel) {
            this.cancel = cancel;
        }
    }
}
