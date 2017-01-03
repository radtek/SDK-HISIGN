package com.hisign.sdk.common.util.datacompress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisign.sdk.common.util.serializer.ISerializer;
import com.hisign.sdk.common.util.serializer.SerializerFactory;

/**
 * @Title:
 *
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年1月13日  下午9:57:14
 * @version 1.0
 */
public abstract class AbstractDataCompressor implements IDataCompressor {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(AbstractDataCompressor.class);
    
    //序列化工具
    private ISerializer serializer = null;
    
    //字符集
    public static final String charSet = "utf-8"; 
    
    public AbstractDataCompressor(){
        this.serializer = SerializerFactory.createSerializer();
    }

    /**
     * 数据压缩
     * @param data 待压缩数据
     * @return  压缩后数据
     */
    @Override
	public abstract byte[] compress(byte[] data);

    /**
     * 数据压缩
     * @param data 待压缩字符串
     * @return  压缩后字符串
     */
    @Override
	public abstract byte[] decompress(byte[] data);
    
    /**
     * 数据压缩
     * @param data 待压缩字符串
     * @return  压缩后字符串
     */
    @Override
	public String compress(String str){
        if(str == null || str.length() == 0){
            return str;
        }
        
        try {
            byte[] data = str.getBytes(charSet);
            byte[] bytes = this.compress(data);
            String result = new String(bytes,charSet);
            return result;
        } catch (Exception ex) {
            log.error("compress str catch an exception",ex);
        }
        
        return null;
    }

    /**
     * 数据解压
     * @param data 待解压字符串
     * @return 解压后的字符串
     */
    @Override
	public String decompress(String str){
        if(str == null || str.length() == 0){
            return str;
        }
        
        try {
            byte[] data = str.getBytes(charSet);
            byte[] bytes = this.decompress(data);
            String result = new String(bytes,charSet);
            return result;
        } catch (Exception ex) {
            log.error("decompress str catch an exception",ex);
        }
        
        return null;
    }

    /**
     * 对象数据压缩
     * @param data 待压缩数据
     * @return  压缩后数据
     */
    @Override
	public byte[] objcompress(Object obj) {
        if(obj == null){
            return null;
        }
        
        byte[] data = null;
        try {
			data = this.serializer.serialize(obj);
		} catch (Throwable e) {
			log.error("objcompress catch an exception",e);
		}
        return this.compress(data);
    }

    /**
     * 对象数据解压
     * @param data 待解压数据
     * @return 解压后的数据
     */
    @Override
	public Object objdecompress(byte[] data) {
        if(data == null){
            return null;
        }
        
        byte[] bytes = this.decompress(data);
        Object obj = null;
        try {
			obj = this.serializer.unserialize(bytes);
		} catch (Throwable e) {
			log.error("objcompress catch an exception",e);
		}
        return obj;
    }

}
