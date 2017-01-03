package com.hisign.sdk.common.util;

import java.security.MessageDigest;


/**
 * @Title:
 *  针对密码进行加密工具类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：2012-7-26  下午4:57:38
 * @version 1.0
 */
public class CryptPassword {
	
    /**
     *用来将一个String加工为MD5后的String
     */
    public static String getMD5String(String pass){
        String result = null;
        
        if ( pass != null ){
            try{
                MessageDigest md = null;
                md = MessageDigest.getInstance("MD5");
                md.update(pass.getBytes());
                byte[] dig = md.digest();
                
                result = byte2hex(dig);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        return result;
    }
    
    private static  String byte2hex(byte[] b) //二行制转字符串
    {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) 
                hs=hs+"0"+stmp;
            else 
                hs=hs+stmp;
        }
        return hs.toUpperCase();
    }
    
    
    /**
     *根据密码和已加密过的字符串判断是否相符
     */
    public static boolean isPassRight(String pass, String crypted){
        String cryptPass = getMD5String(pass);
        if ( cryptPass.equals(crypted) )
            return true;
        else
            return false;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if ( args == null || args.length != 1 ){
            //System.out.println("参数为密码，返回经过加密的十六进制的密文");
            System.out.println("Parameters for the password and return to the encrypted ciphertext hexadecimal system");
            return;
        }
        System.out.println(getMD5String(args[0]));
    }

}
