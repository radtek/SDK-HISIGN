package com.hisign.sdk.common.util.datacompress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 *   基于GZip的压缩解压
 * @description:
 * 
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年1月13日  下午6:18:18
 * @version 1.0
 */
public class GZipDataCompressor extends AbstractDataCompressor implements IDataCompressor {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(GZipDataCompressor.class);

    /**
     * 数据压缩
     * @param data 待压缩数据
     * @return  压缩后数据
     */
    @Override
	public byte[] compress(byte[] data) {
        if(data == null || data.length <= 0){
            return data;
        }
        
        byte[] b = null;
        ByteArrayOutputStream bos = null;
        GZIPOutputStream gzip = null;
        try {
            bos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            gzip = null;
            b = bos.toByteArray();
            bos.close();
            bos = null;
        } catch (Throwable ex) {
            log.error("compress bytes catch an exception",ex);
        }finally{
            try{
                if(gzip != null){
                    gzip.close();
                    gzip = null;
                }
            }catch(Exception ex){
            }
            
            try{
                if(bos != null){
                    bos.close();
                    bos = null;
                }
            }catch(Exception ex){
            }
        }
        return b;
    }

    /**
     * 数据解压
     * @param data 待解压数据
     * @return 解压后的数据
     */
    @Override
	public byte[] decompress(byte[] data) {
        if(data == null || data.length <= 0){
            return data;
        }
        
        byte[] b = null;
        ByteArrayInputStream bis = null;
        GZIPInputStream gzip = null;
        ByteArrayOutputStream baos = null;
        try {
            bis = new ByteArrayInputStream(data);
            gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            baos = null;
            gzip.close();
            gzip = null;
            bis.close();
            bis = null;
        } catch (Throwable ex) {
            log.error("decompress bytes catch an exception",ex);
        }finally{
            try{
                if(baos != null){
                    baos.close();
                    baos = null;
                }
            }catch(Exception ex){
            }
            
            try{
                if(gzip != null){
                    gzip.close();
                    gzip = null;
                }
            }catch(Exception ex){
            }
            
            try{
                if(bis != null){
                    bis.close();
                    bis = null;
                }
            }catch(Exception ex){
            }
        }
        return b;
    }

}
