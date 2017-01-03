package com.hisign.sdk.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.SafeProperties;


/**
 * @Title:
 *  读取local.properties文件
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2015年2月13日  上午11:15:19
 * @version 1.0
 */
public class LocalSystemProperties {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(LocalSystemProperties.class);
    
    public static final String LOCAL_FILE = "local.properties";
    
    //可以实现写入properries文件时不改变原来properties文件格式
    private SafeProperties props = new SafeProperties();
    
    private static LocalSystemProperties instance = new LocalSystemProperties();
    
    public static LocalSystemProperties getInstance(){
        return instance;
    }
    
    private LocalSystemProperties(){ 
        this.load();
    }

    
    /**
     * 加载本地应用
     * @return
     */
    private void load(){
        try{
            File file = this.getConfigFile();
            if(file == null || !file.exists()){ //如果文件不存在
                log.warn("local.properties is not exist");
                return;
            }
            
            InputStream is = new FileInputStream(file);
            props.load(is);
            if(props == null || props.size() <= 0){
                log.warn("local.properties is empty!");
                return;
            }
        }catch(Throwable tr){
            log.error("load "+LOCAL_FILE+" catch an exception",tr);
        }
    }
    
    
    /**
     * 获取配置文件
     * @return
     * @throws Exception
     */
    private File getConfigFile() throws Exception{
        URL url = this.getClass().getClassLoader().getResource(LOCAL_FILE);
        if(url == null){
            log.warn("local.properties is not exist");
            return null;
        }
        
        String filePath = URLDecoder.decode(url.getFile(),"utf-8");
        File file = new File(filePath);
        
        return file;
    }
    
    
    /**
     * 更新属性文件中的某些属性值
     * @param key  
     * @param value
     * @return
     */
    public void updateProperty(String key,String value){
        OutputStream os = null;
        try{
            props.put(key, value);
            File file = this.getConfigFile();
            os = new FileOutputStream(file);
            props.store(os,null);
        }catch(Throwable tr){
            log.error("updateProperty key="+key+",value="+value+" to "+ LOCAL_FILE + "catch an exception",tr);
        }finally{
            if(os != null){
                try{
                    os.flush();
                    os.close();
                    os = null;
                }catch(Exception ex){    
                }
            }
        }
    }

    /**
     * @return the props
     */
    public SafeProperties getProps() {
        return props;
    }
}
