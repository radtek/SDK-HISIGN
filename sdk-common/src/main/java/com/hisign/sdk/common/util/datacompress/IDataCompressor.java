package com.hisign.sdk.common.util.datacompress;


/**
 * @Title:
 *    数据压缩接口
 * @description:
 *   1.byte[]压缩
 *   2.byte[]解压
 *   3.对象压缩
 *   4.对象解压
 * @Company: hisign.com.cn
 * @author lnj2050 
 * @create time：2014年1月13日  下午6:08:09
 * @version 1.0
 */
public interface IDataCompressor {
    
    /**
     * 数据压缩
     * @param data 待压缩数据
     * @return  压缩后数据
     */
    public byte[] compress(byte[] data);
    
    /**
     * 数据解压
     * @param data 待解压数据
     * @return 解压后的数据
     */
    public byte[] decompress(byte[] data);
    
    /**
     * 数据压缩
     * @param data 待压缩字符串
     * @return  压缩后字符串
     */
    public String compress(String str);
    
    /**
     * 数据解压
     * @param data 待解压字符串
     * @return 解压后的字符串
     */
    public String decompress(String str);
    
    /**
     * 对象数据压缩
     * @param data 待压缩数据
     * @return  压缩后数据
     */
    public byte[] objcompress(Object data);
    
    /**
     * 对象数据解压
     * @param data 待解压数据
     * @return 解压后的数据
     */
    public Object objdecompress(byte[] data);

}
