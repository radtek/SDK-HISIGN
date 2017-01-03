package com.hisign.sdk.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:
 *  网络地址公用类
 * @description:
 * 
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：2012-6-7  16:52:19
 * @version 1.0
 */
public class InetAddressUtil {
	
	//日志
    private static Logger log = LoggerFactory.getLogger(InetAddressUtil.class);
	
	private static String localIp = null;
			
	/**
	 * 获取Ip地址
	 * 
	 * @return
	 */
	public static String getLocalIp() {
	    if(StringUtils.isEmpty(localIp)){ //如果localIp为空
	        String osname = System.getProperty("os.name");
	        log.info("osname="+osname);
	        if(osname.toLowerCase().startsWith("linux")){
	            localIp = getLinuxIp();
	        }else{
	            localIp = getWindowsIp();
	        }
	    }
		
	    return localIp;
	}
	
	/**
	 * 获取Windows的Ip地址
	 * @return
	 */
	public static String getWindowsIp(){
		try{
		   String ip = InetAddress.getLocalHost().getHostAddress();
		   return ip;
		}catch(Exception ex){
		   log.error("getWindowsIp catch an exception",ex);
		}
		
		return null;
	}
	
	/**
	 * 获取Linux 的Ip地址
	 * @return
	 */
	public static String getLinuxIp(){
		try{
		    // 根据网卡取本机配置的IP
			log.info("current os is linux");
			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ipAddress = null;
			String ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				if(ni.getInetAddresses() == null){
					continue;
				}

				log.info("NetworkInterface ->"+ni.toString());
                String interfaceName = ni.getName();
                if(interfaceName != null && interfaceName.toLowerCase().indexOf("tunl") != -1){ //tunl接口
                    continue; 
                }
                
				Enumeration<InetAddress> ipEm = ni.getInetAddresses();
				while(ipEm.hasMoreElements()){
					ipAddress = ipEm.nextElement(); 
					log.info("current ipaddress "+ipAddress.toString());
			
					if (!ipAddress.isLoopbackAddress()
							&& ipAddress.getHostAddress().indexOf(":") == -1) {
						ip = ipAddress.getHostAddress();
						log.info("linux get ip="+ip);
						return ip;
					} 
				}
			}   
		}catch(Exception ex){
			log.info("getLinuxIp catch an exception",ex);
		}
		
		return null;
	}

	/**
     * 是否为一个IP地址
     * 
     * @param ipAddress
     * @return
     */
    public static boolean isIpAddress(String ipAddress) {
    	String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipAddress);
        boolean matches = matcher.matches();
        return matches;
    }
    
    //十进制ip地址转化为长整型（59.225.0.0-->1004601344L）
    public static long stringIpToLong(String ip) {  
        String[] ips = ip.split("\\.");  
        long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);  
        return num;  
    }  

    //长整型转化为十进制ip地址（1004601344L-->59.225.0.0）
    public static String longIpToString(long ipLong) {     
        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};  
        long num = 0;  
        StringBuffer ipInfo = new StringBuffer();  
        for(int i=0;i<4;i++){  
            num = (ipLong & mask[i])>>(i*8);  
            if(i>0) ipInfo.insert(0,".");  
                ipInfo.insert(0,Long.toString(num,10));  
        }  
        return ipInfo.toString();  
    }
    
    //根据IP和子网掩码获取IP网段
    public static String getIPStartAndEnd(String ip,String mask){
        long s1 = stringIpToLong(ip);
        long s2 = stringIpToLong(mask);
        String erj = Long.toBinaryString(s2);
        long s3 = s1 & s2;
        long start = stringIpToLong(longIpToString(s3));
        String wl = Long.toBinaryString(s3);
        if(wl.length()<32) {
            int le = 32-wl.length();
            for(int i=0; i<le; i++) {
                wl = "0"+wl;
            }
        }
        String gbl = wl.substring(0,erj.indexOf("0"))+wl.substring(erj.indexOf("0"),wl.length()).replace("0", "1");
        long s4 = Long.parseLong(gbl, 2);
        long end = stringIpToLong(longIpToString(s4));
        return start+"|"+end;
    }
    
    public static void main(String[]arg) {
        System.out.println(getIPStartAndEnd("59.224.192.0", "255.255.192.0"));
        System.out.println(stringIpToLong("1.12.0.0"));
        System.out.println(longIpToString(1000L));
        System.out.println(longIpToString(1000000L));
    }
}
