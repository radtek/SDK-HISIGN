package com.hisign.sdk.common.util.datacompress;

import com.hisign.sdk.common.util.StringUtils;

/**
 * @Title:
 *   数据压缩器工厂类
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年1月13日  下午10:07:15
 * @version 1.0
 */
public class DataCompressorFactory {
    
    //GZip类型
    public static final String TYPE_GZIP = "gzip";
    
    //Zip类型
    public static final String TYPE_ZIP = "zip";
    
    //压缩类型
    public static String defaultType = TYPE_GZIP;
    
    /**
     * 构建数据压缩器
     * @return
     */
    public static IDataCompressor createDataCompressor(){
        return createDataCompressor(defaultType);
    }
    
    /**
     * 按类型构建压缩器
     * @param type
     * @return
     */
    public static IDataCompressor createDataCompressor(String type){
        IDataCompressor dataCompressor = null;
        if(StringUtils.isEmpty(type)){
            dataCompressor = new GZipDataCompressor();
            return dataCompressor;
        }
        
        if(type.equalsIgnoreCase(TYPE_GZIP)){
            dataCompressor = new GZipDataCompressor();
        }else if(type.equalsIgnoreCase(TYPE_ZIP)){
            dataCompressor = new ZipDataCompressor();
        }else{ //默认GZip
            dataCompressor = new GZipDataCompressor();
        }
        
        return dataCompressor;
    } 

}
