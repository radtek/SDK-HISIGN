/*
 * PingUtil.java
 *
 * Created on 2003年2月28日, 上午9:27
 */

package com.hisign.sdk.common.util;


import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author  Administrator
 */
public class PingUtil {
//	日志文件处理
    static Logger log = LoggerFactory.getLogger(PingUtil.class);
    /** Creates a new instance of MyPing */
    public PingUtil() {
    }
    /**
     *ping一个IP地址，看是否通
     *@retry -- 重试次数
     *@timeout -- 超时，单位：milleseconds
     *return true if alive, false if not alive
     */
    public static boolean pings(String s, int retry, int timeout,int packet)
    {
        //temporarily use execPing as ping utility
        boolean result = false;
        try{
        	if(packet==0)
        		result = execPing(s,retry,timeout);
        	else
              result = execPing(s,retry,timeout,packet);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return result;
    }

    /**
     *ping一个IP地址，看是否通
     *@retry -- 重试次数
     *@timeout -- 超时，单位：milleseconds
     *return true if alive, false if not alive
     */
    public static boolean ping(String s, int retry, int timeout)
    {
        //temporarily use execPing as ping utility
        boolean result = false;
        try{
            result = execPing(s,retry,timeout);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return result;
    }
    
    /**
     *ping一个IP地址，看是否通
     *@retry -- 重试次数
     *@timeout -- 超时，单位：milleseconds
     *return true if alive, false if not alive
     */
    public static String pingResult(String s, int retry, int timeout)
    {
        //temporarily use execPing as ping utility
        String s1;
        String PING_CMD = "ping ";
        String osname = System.getProperty("os.name");
        if(osname.startsWith("SunOS") || osname.startsWith("Solaris"))
        {
            int sun_timeout = timeout/1000;
            if ( sun_timeout <=0 )
                sun_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " " + s + " " + sun_timeout);
        } else
        if(osname.startsWith("AIX"))
        {
            int aix_timeout = timeout/1000;
            if ( aix_timeout <=0 )
                aix_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " -c " + retry + " -w "+ aix_timeout + " "  +s);
        } else
        if(osname.startsWith("HP-UX"))
        {
            int hp_timeout = timeout/1000;
            if ( hp_timeout <=0 )
                hp_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            //s1 = new String(PING_CMD + " " + s + " -n "+ retry + " -m " + hp_timeout);
            s1 = new String(PING_CMD + " " + s + " -n "+ retry);
        }else
        if(osname.startsWith("Linux"))
        {
            PING_CMD = "/bin/ping -c 1 -w 1 ";
            s1 = new String(PING_CMD + " " + s);
        } else
        if(osname.startsWith("FreeBSD"))
        {
            PING_CMD = "/sbin/ping -c 1";
            s1 = new String(PING_CMD + " " + s);
        } else
        if(osname.startsWith("Windows"))
        {
            PING_CMD = "ping -n " + retry + " -w " + timeout;
            s1 = new String(PING_CMD + " " + s);
        } else
        {
            s1 = new String(PING_CMD + " " + s);
        }
        RunCmd runcmd = new RunCmd(s1);
        runcmd.start();
        do
            try
            {
                Thread.sleep(100L);
            }
            catch(Exception exception) { 
               // throw exception;
            }
        while(!runcmd.finished);
        
        if (runcmd.exceptionOccured != null && ! runcmd.exceptionOccured.trim().equalsIgnoreCase("")) {
            return runcmd.exceptionOccured.trim();
        }
            
      return runcmd.stdout.toString();
        
    }
    
    /**
     *增加一些额外的逻辑，0为不通，1为通，-1为发生了异常
     */
    public static int nPing(String s, int retry, int timeout){
        int result = -1;
        try{
            boolean temp = execPing(s,retry,timeout);
            if ( temp ){
                result = 1;
            }
            else{
                result = 0;
            }
        }catch(Exception ex){
            //System.out.println(ex.getMessage());
            result = -1;
        }
        return result;
    }

    /**
     *增加一些额外的逻辑，0为不通，1为通，-1为发生了异常
     */
    public static int nPing(String s, int retry, int timeout,int packet){
        int result = -1;
        try{
        	 boolean temp = false ;
        	if(packet==0)
        		temp = execPing(s,retry,timeout);
        	else
                temp = execPing(s,retry,timeout,packet);
            if ( temp ){
                result = 1;
            }
            else{
                result = 0;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            result = -1;
        }
        return result;
    }

    private static boolean execPing(String s, int retry ,int timeout,int packet) throws Exception
    {
        String s1;
        String PING_CMD = "ping ";
        String osname = System.getProperty("os.name");
        if(osname.startsWith("SunOS") || osname.startsWith("Solaris"))
        {
            int sun_timeout = timeout/1000;
            if ( sun_timeout <=0 )
                sun_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " " + s + " " + sun_timeout);
        } else
        if(osname.startsWith("AIX"))
        {
            int aix_timeout = timeout/1000;
            if ( aix_timeout <=0 )
                aix_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " -c " + retry + " -w "+ aix_timeout +   " -s " + packet +" " +s);
        } else
        if(osname.startsWith("HP-UX"))
        {
            int hp_timeout = timeout/1000;
            if ( hp_timeout <=0 )
                hp_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            //s1 = new String(PING_CMD + " " + s + " -n "+ retry + " -m " + hp_timeout);
            s1 = new String(PING_CMD + " " + s  + " " + packet  + " -n "+ retry  );
        }else
        if(osname.startsWith("Linux"))
        {   PING_CMD = "/bin/ping -c 1 -w 1 ";
            s1 = new String(PING_CMD +  " " + s); 
        } else
        if(osname.startsWith("FreeBSD"))
        {
            PING_CMD = "/sbin/ping -c 1";
            s1 = new String(PING_CMD + " -s " +packet + " " + s);
        } else
        if(osname.startsWith("Windows"))
        {
            PING_CMD = "ping -n " + retry + " -w " + timeout + " -l " + packet;
            s1 = new String(PING_CMD + " " + s);
        } else
        {
            s1 = new String(PING_CMD + " " + s);
        }
        //log.info("s1=="+s1);
        RunCmd runcmd = new RunCmd(s1);
        runcmd.start();
        do
            try
            {
                Thread.sleep(100L);  
            }
            catch(Exception exception) { 
                throw exception;
            }
        while(!runcmd.finished);
        
        if (runcmd.exceptionOccured != null && ! runcmd.exceptionOccured.trim().equalsIgnoreCase("")) {
        	//log.error("exceptionOccured==="+runcmd.exceptionOccured.trim());
        	throw new Exception(runcmd.exceptionOccured);
        }
            
            
        if(osname.startsWith("Windows"))
        {
            if(!runcmd.result || runcmd.exitValue != 0)
                return false;
            String s2  = runcmd.stdout.toString();
            //log.info(""+s2);
            boolean flag = ((s2.indexOf("Reply from") != -1 && s2.indexOf("bytes=") != -1 )||(s2.indexOf("来自") != -1 && s2.indexOf("字节=") != -1));
            return flag ;
//            String s2;
//            return (s2 = runcmd.stdout.toString()).indexOf("Reply from") != -1 && s2.indexOf("bytes=") != -1;
        }
        if(osname.startsWith("Linux") || osname.startsWith("FreeBSD") || osname.startsWith("AIX"))
        {   //log.info("result==="+runcmd.result + ",exitValue=" +runcmd.exitValue);
            if(!runcmd.result || runcmd.exitValue != 0)
                return false;
            if(runcmd.result && runcmd.exitValue == 0)
            	return true;
            String s3=runcmd.stdout.toString();
          // log.info(s +"==="+ s3);
            boolean flag = (s3.indexOf("bytes from") != -1)||((s3.indexOf("来自") != -1) && (s3.indexOf("字节") != -1));
            //log.info(s +"==="+ flag);
            return  flag ;
        }
        return runcmd.result && runcmd.exitValue == 0;
    }
   
    
    
    private static boolean execPing(String s, int retry ,int timeout) throws Exception
    {
        String s1;
        String PING_CMD = "ping ";
        String osname = System.getProperty("os.name");
        if(osname.startsWith("SunOS") || osname.startsWith("Solaris"))
        {
            int sun_timeout = timeout/1000;
            if ( sun_timeout <=0 )
                sun_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " " + s + " " + sun_timeout);
        } else
        if(osname.startsWith("AIX"))
        {
            int aix_timeout = timeout/1000;
            if ( aix_timeout <=0 )
                aix_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            s1 = new String(PING_CMD + " -c " + retry + " -w "+ aix_timeout +  " " +s);
        } else
        if(osname.startsWith("HP-UX"))
        {
            int hp_timeout = timeout/1000;
            if ( hp_timeout <=0 )
                hp_timeout = 1;
            PING_CMD = "/usr/sbin/ping ";
            //s1 = new String(PING_CMD + " " + s + " -n "+ retry + " -m " + hp_timeout);
            s1 = new String(PING_CMD + " " + s + " -n "+ retry  );
        }else
        if(osname.startsWith("Linux"))
        {   PING_CMD = "/bin/ping -c 1 -w 1 ";
            s1 = new String(PING_CMD + " " + s);
        } else
        if(osname.startsWith("FreeBSD"))
        {
            PING_CMD = "/sbin/ping -c 1";
            s1 = new String(PING_CMD + " " + s);
        } else
        if(osname.startsWith("Windows"))
        {
            PING_CMD = "ping -n " + retry + " -w " + timeout;
            s1 = new String(PING_CMD + " " + s);
        } else
        {
            s1 = new String(PING_CMD + " " + s);
        }
        RunCmd runcmd = new RunCmd(s1);
        runcmd.start();
        do
            try
            {
                Thread.sleep(100L);
            }
            catch(Exception exception) { 
                throw exception;
            }
        while(!runcmd.finished);
        
        if (runcmd.exceptionOccured != null && ! runcmd.exceptionOccured.trim().equalsIgnoreCase("")) {
            throw new Exception(runcmd.exceptionOccured);
        }
            
            
        if(osname.startsWith("Windows"))
        {
            if(!runcmd.result || runcmd.exitValue != 0)
                return false;
            String s2  = runcmd.stdout.toString();
            //log.info(""+s2);
            boolean flag = ((s2.indexOf("Reply from") != -1 && s2.indexOf("bytes=") != -1 )||(s2.indexOf("来自") != -1 && s2.indexOf("字节=") != -1));
            return flag;
        }
        if(osname.startsWith("Linux") || osname.startsWith("FreeBSD")||  osname.startsWith("AIX"))
        {
            if(!runcmd.result || runcmd.exitValue != 0)
                return false;
            if(runcmd.result && runcmd.exitValue == 0)
            	return true;
            String s3=runcmd.stdout.toString();
           // log.info(s +"==="+ s3);
            boolean flag = (s3.indexOf("bytes from") != -1)||(s3.indexOf("来自") != -1 && s3.indexOf("字节") != -1);
           // log.info(s +"==="+ flag);
            return  flag ;
        }
        return runcmd.result && runcmd.exitValue == 0;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if ( args.length != 3 )
        {
            System.out.println("Usage: java com.ultrapower.configuration.util.PingUtil [host] [retry] [timeout]");
            return;
        }
       // System.out.println("ping:" + PingUtil.ping(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2])));
       // System.out.println("nPing:" + PingUtil.nPing(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2])));
    }
  
    /** 检测一个主机的端口,只能用于TCP检测
     *常用端口如下: DNS 53 | SMTP 25 | POP3 110 | WWW 80
     * @param host 主机IP和名称
     * @param port 主机端口
     * @return 是否开启
     */    
    public static boolean pingPort(String host, int port){
        if ( host == null )
            return false;
        Socket socket = null;
        boolean result = false;
        try{
            // open a socket connection
            socket = new Socket(host, port);
            result = true;
            // open I/O streams for objects
        }catch(Exception ex){
            result = false;
        }finally{
            try{
                socket.close();
            }catch(Exception ex2){
            }
        }
        return result;
    }
    
}
