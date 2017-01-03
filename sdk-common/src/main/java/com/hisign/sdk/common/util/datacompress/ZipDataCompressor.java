package com.hisign.sdk.common.util.datacompress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
public class ZipDataCompressor extends AbstractDataCompressor implements IDataCompressor {
    
    //日志
    private static Logger log = LoggerFactory.getLogger(ZipDataCompressor.class);
    
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
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(bos);
            ZipEntry entry = new ZipEntry("zip");
            entry.setSize(data.length);
            zip.putNextEntry(entry);
            zip.write(data);
            zip.closeEntry();
            zip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            log.error("compress catch an exception",ex);
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
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ZipInputStream zip = new ZipInputStream(bis);
            while (zip.getNextEntry() != null) {
                byte[] buf = new byte[1024];
                int num = -1;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((num = zip.read(buf, 0, buf.length)) != -1) {
                    baos.write(buf, 0, num);
                }
                b = baos.toByteArray();
                baos.flush();
                baos.close();
            }
            zip.close();
            bis.close();
        } catch (Exception ex) {
            log.error("decompress catch an exception",ex);
        }
        return b;
    }
}
