package com.hisign.sdk.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.InetAddressUtil;
import com.hisign.sdk.common.util.StringUtils;


/**
 * @Title:
 *   默认分组配置
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2015年7月6日  下午5:38:42
 * @version 1.0
 */
public class DefaultGroupConfig {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(DefaultGroupConfig.class);
    
    //默认分组前缀
    public static final String DEFAULT_GROUP_PREFIEX = "hisign"; 
    
    private String defaultGroup = null;
    
    private static DefaultGroupConfig instance = new DefaultGroupConfig();
    
    public static DefaultGroupConfig getInstance(){
        return instance;
    }
    
    private DefaultGroupConfig(){
        this.init();
    }
    
    private void init(){
        this.defaultGroup = this.assembleDefaultGroup();
    }
    
    /**
     * @return the defaultGroup
     */
    public String getDefaultGroup() {
        return defaultGroup;
    }

    /**
     * 获取默认的分组
     * @return
     */
    private String assembleDefaultGroup(){
    	String defaultGroup = System.getProperty("mq-defaultgroup");
        if(StringUtils.isEmpty(defaultGroup)){
            defaultGroup = DEFAULT_GROUP_PREFIEX;
        }

        String ip = InetAddressUtil.getLocalIp();
        String systemID = System.getProperty("systemId");
        if(StringUtils.isEmpty(systemID)){
        	systemID = "unknown";
        }
        
        defaultGroup = defaultGroup + "_" + systemID+"_"+ip;
        return defaultGroup;
    }
}
