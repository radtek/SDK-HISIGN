package com.hisign.sdk.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;


/**
 * @Title:
 *   通用IO工具类
 * @description:
 *   提供关闭方法
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年10月27日  下午2:53:10
 * @version 1.0
 */
public class CommonIOUtils {
    
    /**
     * 关闭输入
     * @param reader
     * @param input
     */
    public static void close(Reader reader,InputStream input){
        close(reader);
        close(input);
    }
    
    /**
     * 关闭输出
     * @param writer
     * @param output
     */
    public static void close(Writer writer,OutputStream output){
        close(writer);
        close(output);
    }
    
    public static void close(Reader reader) {
        close((Closeable)reader);
    }
    
    public static void close(InputStream input) {
        close((Closeable)input);
    }
    
    
    public static void close(Writer writer) {
        close((Closeable)writer);
    }
    
    public static void close(OutputStream output) {
        close((Closeable)output);
    }
    
    /**
     * 关闭基础类
     * @param closeable
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
                closeable = null;
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    
    /**
     * 关闭socket连接
     * @param sock
     */
    public static void close(Socket sock){
        if (sock != null){
            try {
                sock.close();
            } catch (IOException ioe) {
                // ignored
            }
        }
    }

}
