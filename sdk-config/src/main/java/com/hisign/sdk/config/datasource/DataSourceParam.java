package com.hisign.sdk.config.datasource;


import java.io.InputStream;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.CommonIOUtils;


/**
 * @Title:
 *  数据库连接相关参数
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：Aug 15, 2013  6:20:43 PM
 * @version 1.0
 */
public class DataSourceParam {
	
	private static Logger log = LoggerFactory.getLogger(DataSourceParam.class);
	
	private static final String CONFIG_FILE = "uaop_config.properties"; //db参数配置文件
	
	private String url;

	private String user;

	private String password;
	
	//driver名称
	private String driverClassName;
	
    private String ora_vertion = "9i"; //数据库版本
    
    private  String isdbpwdsecurity = "false"; //数据库密码是否加密
	
	//最大活动连接数
	private int maxActive = 8;
	
	//最大空闲连接
	private int maxIdle = 8;

	//最小空闲连接
	private int minIdle = 2;
	
	//最大等待时间
	private int maxWait = 120000;
	
	//是否自动回收超时连接
	private boolean removeAbandoned = true;
	
	//超时时间(以秒数为单位)
	private int removeAbandonedTimeout  = 300;
	
	//检查sql语句
	private String validationQuery = "select sysdate from dual";
	
	//起了一个Evict的TimerTask定时线程进行控制(可通过设置参数timeBetweenEvictionRunsMillis>0),
	//定时对线程池中的链接进行validateObject校验，对无效的链接进行关闭后，会调用ensureMinIdle，适当建立链接保证最小的minIdle连接数。 
	private boolean testWhileIdle = true;
	
	//设置的Evict线程的时间，单位ms，大于0才会开启evict检查线程
	private int timeBetweenEvictionRunsMillis = 30000;

	private int validationQueryTimeout = 1;
	
	//代表每次检查链接的数量，建议设置和maxActive一样大，这样每次可以有效检查所有的链接.
	private int numTestsPerEvictionRun = maxActive;
	
	//是否定时打印数据库连接状态
	private boolean isPrintStatus = false;
	
	//数据库参数
	private Properties dbParams = null;
	
    //当前是否正在执行数据库地址变更
    private boolean isupdating = false;
	
	private static DataSourceParam instance = null;
	
	/**
	 * 单例模式
	 * @return
	 */
	public synchronized static DataSourceParam getInstance(){
	    if(instance == null){
	        instance = new DataSourceParam();
	    }
	    
	    return instance;
	}
	
	public DataSourceParam(){
		this.init();
	}
	
	private void init(){
		try {
            dbParams = this.loadDBParamProperties();
            if(dbParams == null){
                dbParams = new Properties();
            }
            
			//以下参数是必须配置的
			driverClassName= dbParams.getProperty("db_driverclassname");
			url= dbParams.getProperty("db_url");
			user= dbParams.getProperty("db_username");
			password= dbParams.getProperty("db_password");
            isdbpwdsecurity = dbParams.getProperty("db_isdbpwdsecurity");
            if(isdbpwdsecurity != null && isdbpwdsecurity.trim().equalsIgnoreCase("true")){
                isdbpwdsecurity = "true";
            }else{
                isdbpwdsecurity = "false";
            }
            
			try{
			    String sMaxActive = dbParams.getProperty("db_maxactive");
				maxActive = Integer.parseInt(sMaxActive);
			}catch(Exception ex){
				maxActive = 8;
			}
			
			try{
			    String sMaxIdle = dbParams.getProperty("db_maxidle");
				maxIdle=Integer.parseInt(sMaxIdle);
			}catch(Exception ex){
				maxIdle = 8;
			}
			
			try{
			    String sMinIdle = dbParams.getProperty("db_minidle");
				minIdle=Integer.parseInt(sMinIdle);
			}catch(Exception ex){
				minIdle = 0;
			}
			
			try{
			    String sMaxWait = dbParams.getProperty("db_maxwait");
				maxWait=Integer.parseInt(sMaxWait);
			}catch(Exception ex){
				maxWait = 120000;
			}
			
            try{
                String sRemoveAbandoned = dbParams.getProperty("db_removeabandoned");
                removeAbandoned= sRemoveAbandoned.equalsIgnoreCase("false")?false:true;
            }catch(Exception ex){
                removeAbandoned = true;
            }
        
            try{
                String sRemoveAbandonedTimeout= dbParams.getProperty("db_removeabandonedtimeout");
                removeAbandonedTimeout=Integer.parseInt(sRemoveAbandonedTimeout);
            }catch(Exception ex){
                removeAbandonedTimeout = 300;
            }
        
            try{
                if(driverClassName.toLowerCase().indexOf("oracle") != -1){ //oracle
                    validationQuery = "select sysdate from dual";
                }else if(driverClassName.toLowerCase().indexOf("sybase") != -1){ //sybase
                    validationQuery = "select 1";
                }else if(driverClassName.toLowerCase().indexOf("sqlserver") != -1){ //sqlserver
                    validationQuery = "select 1";
                }else if(driverClassName.toLowerCase().indexOf("mysql") != -1){ //sqlserver
                    validationQuery = "select 1";
                }else{
                    validationQuery = "select 1";
                }
            }catch(Exception ex){
                log.error("driverClassName is empty",ex);
                validationQuery = "select 1";
            }
        
            try{
                String sTestWhileIdle = dbParams.getProperty("db_testwhileidle");
                testWhileIdle= sTestWhileIdle.equalsIgnoreCase("false")?false:true;
            }catch(Exception ex){
                testWhileIdle = true;
            }
        
            try{
                String sTimeBetweenEvictionRunsMillis = dbParams.getProperty("db_timebetweenevictionrunsmillis");
                timeBetweenEvictionRunsMillis=Integer.parseInt(sTimeBetweenEvictionRunsMillis);
            }catch(Exception ex){
                timeBetweenEvictionRunsMillis = 30000;
            }
        
            try{
                String sValidationQueryTimeout = dbParams.getProperty("db_validationquerytimeout");
                validationQueryTimeout=Integer.parseInt(sValidationQueryTimeout);
            }catch(Exception ex){
                validationQueryTimeout = 1;
            }
        
            try{
                String sNumTestsPerEvictionRun = dbParams.getProperty("db_numtestsperevictionrun");
                numTestsPerEvictionRun=Integer.parseInt(sNumTestsPerEvictionRun);
            }catch(Exception ex){
                numTestsPerEvictionRun = maxActive;
            }
            
            try{
                String sIsPrintStatus = dbParams.getProperty("db_isprintstatus");
                if(sIsPrintStatus != null && sIsPrintStatus.trim().equalsIgnoreCase("true")){
                	this.isPrintStatus = true;
                }
            }catch(Exception ex){
            	this.isPrintStatus = false;
            }
            
		} catch (Throwable e) {
			log.error("Initialize catch an exception",e);
		}
	}
	
    /**
     * 从配置文件中加载Properties
     * @return
     * @throws Exception
     */
    private Properties loadDBParamProperties(){
        InputStream is = null;
        try {
            is = DataSourceParam.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            
            if(is == null){
                log.warn("can't get "+CONFIG_FILE+" in classpath");
                return null;
            }

            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (Exception ex) {
            log.warn("load "+CONFIG_FILE+" catch an exception",ex);
            return null;
        }finally{
            CommonIOUtils.close(is);
        }
        
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public boolean isRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public int getRemoveAbandonedTimeout() {
		return removeAbandonedTimeout;
	}

	public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
		this.removeAbandonedTimeout = removeAbandonedTimeout;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getValidationQueryTimeout() {
		return validationQueryTimeout;
	}

	public void setValidationQueryTimeout(int validationQueryTimeout) {
		this.validationQueryTimeout = validationQueryTimeout;
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public String getOra_vertion() {
		return ora_vertion;
	}

	public String getIsDbPwdSecurity() {
		return isdbpwdsecurity;
	}

	public void setIsDbPwdSecurity(String isDbPwdSecurity) {
		this.isdbpwdsecurity = isDbPwdSecurity;
	}

	public boolean isPrintStatus() {
		return isPrintStatus;
	}

}
