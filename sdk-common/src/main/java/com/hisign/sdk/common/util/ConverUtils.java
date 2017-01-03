package com.hisign.sdk.common.util;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/****
 * 转换工具类
 * 
 * 1. 将颜色值不同进制进行相互转换
 * 2. 将object转换为相应字符型、整型
 * 
 * @author caijinpeng 
 *
 */
public class ConverUtils {
	
	//日志
	private static Logger log = LoggerFactory.getLogger(ConverUtils.class);
	
	/*** =================================================================  
	 * 将颜色值不同进制进行相互转换 
	 * 
	 * ================================================================== **/
	
	/**
	 * 将rgb的颜色值转换为16进制的数值
	 * @param r
	 * @param g
	 * @param b
	 * @return 格式：0xFFFFFF
	 * @throws Exception
	 */
	public static String converColor(int r , int g , int b) throws Exception{
		StringBuffer buffer = new StringBuffer("");
		buffer.append("0x");
		String rs = Integer.toHexString(r);
		if(rs.length()==1){
			buffer.append("0");
		}
		buffer.append(rs);
		String gs = Integer.toHexString(g);
		if(gs.length()==1){
			buffer.append("0");
		}
		buffer.append(gs);
		String bs = Integer.toHexString(b);
		if(bs.length()==1){
			buffer.append("0");
		}
		buffer.append(bs);
		return buffer.toString();
	}

	/**
	 * 通过16进制的字符串值获取颜色对象
	 * @param str
	 * @return
	 */
	public static Color String2Color(String str) {
		int i = Integer.parseInt(str.substring(1), 16);
		return new Color(i);
	}

	/**
	 * 通过颜色对象获取十六进制字符串值
	 * @param color
	 * @return
	 */
	public static String Color2String(Color color) {
		String R = Integer.toHexString(color.getRed());
		R = R.length() < 2 ? ('0' + R) : R;
		String G = Integer.toHexString(color.getGreen());
		G = G.length() < 2 ? ('0' + G) : G;
		String B = Integer.toHexString(color.getBlue());
		B = B.length() < 2 ? ('0' + B) : B;
		return '#' + R + G + B;
	}
	
	
	/*** =================================================================  
	 * 将object转换为相应字符型、整型
	 * 
	 * ================================================================== **/
	
	/**
	 * 将通用对象s转换为long类型，如果字符穿为空或null，返回r；
	 * @author caijinpeng 
	 * @param s
	 * @param r
	 * @return
	 */
	public static long Obj2long(Object s, long r) {
		long i = r;

		String str = "";
		try {
			str = s.toString();
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = r;
		}
		return i;
	}
	
	
	/**
	 * 将通用对象s转换为long类型，如果字符穿为空或null，返回0；
	 * @param s
	 * @return
	 */
	public static long Obj2long(Object s) {
		long i = 0;
		String str = "";
		try {
			str = s.toString();
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}
	
	
	/**
	 * 将通用对象s转换为int类型，如果字符穿为空或null，返回r；
	 * @author caijinpeng 
	 * @param s
	 * @param r
	 * @return
	 */
	public static int Obj2int(Object s, int r) {
		int i = r;

		String str = "";
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = r;
		}
		return i;
	}
	
	/**
	 * 将通用对象s转换为int类型，如果字符穿为空或null，返回0；
	 * @param s
	 * @return
	 */
	public static int Obj2int(Object s) {
		int i = 0;
		String str = "";
		try {
			str = s.toString();
			i = Integer.parseInt(str);
		} catch (Exception e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 将通用对象s转换为String类型，如果字符穿为空或null，返回r；
	 * @author caijinpeng 
	 * @param s
	 * @param r
	 * @return
	 */
	public static String Obj2Str(Object s, String r) {
		String str = r;
		try {
			str = s.toString();
			if (str.equals("null") || str == null || str.trim().length() == 0) {
				str = r;
			}
		} catch (Exception e) {
			str = r;
		}
		return str;
	}
	
	
	/**
	 * 将long对象s转换为String类型，如果s为0，返回null；
	 * @param s
	 * @return
	 */
	public static String Long2Str(long s){
		String i = null;
		try {
			if(s!=0){
				i = String.valueOf(s);
			}
		} catch (Exception e){
			i = null;
		}
		return i;
	}
	
	
	/***
	 * 将double字符串对象d 转换为int类型，如果s为0，返回0；
	 * @param d
	 * @return
	 */
	public static int DoubleStr2Int(String d){
		int i = 0;
		try{
			if(d!=null && d.trim().length()>0){
				Double D1=new Double(d);    
				i = D1.intValue();
			}
		}catch(Exception e){
			i = 0;
		}
		return i;
	}
	
	/*** =================================================================  
	 * 时间不同格式进行转换
	 * 
	 * ================================================================== **/
	/**
	 * 将毫秒数转换为yyyy-MM-dd HH:mm:ss格式的时间串
	 * @param millis
	 * @return
	 */
	public static String Millis2StrLong(long millis){
		if (millis <= 0)
			return "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		Date date = calendar.getTime();
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = datetimeFormat.format(date);
		return s;
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss类型的字符串转换为毫秒数
	 * @param date
	 * @return
	 */
	public static long StrLong2Millis(String date){
		if (date == null || "".equals(date.trim())) {
			return 0;
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt2;
		try {
			dt2 = datetimeFormat.parse(date);
			//继续转换得到的long型
			long lTime = dt2.getTime();
			return lTime;
		} catch (ParseException e) {
			log.error("formatString Error! ", e);
		}
		return 0;
	}
	
	/**
	 * 将yyyy-MM-dd类型的字符串转换为毫秒数
	 * @param date
	 * @return
	 */
	public static long Str2Millis(String date){
		if (date == null || "".equals(date.trim())) {
			return 0;
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt2;
		try {
			dt2 = datetimeFormat.parse(date);
			//继续转换得到的long型
			long lTime = dt2.getTime();
			return lTime;
		} catch (ParseException e) {
			log.error("formatString Error! ", e);
		}
		return 0;
	}
	

	/**
	 * 将给定类型的时间字符串转换为毫秒数
	 * @param date
	 * @param _dtFormat
	 *     日期格式1  yyyy-MM-dd HH:mm:ss
	 *     日期格式2  yyyy-MM-dd
	 *     日期格式3  yyyyMMddhh
	 * @return
	 */	
    public static long Str2Millis(String date, String _dtFormat){
		if (date == null || "".equals(date.trim())) {
			return 0;
		}
		SimpleDateFormat datetimeFormat = new SimpleDateFormat(_dtFormat);
		Date dt2;
		try {
			dt2 = datetimeFormat.parse(date);
			//继续转换得到的long型
			long lTime = dt2.getTime();
			return lTime;
		} catch (ParseException e) {
			log.error("formatString Error! ", e);
		}
		return 0;   	
    }
 
}
