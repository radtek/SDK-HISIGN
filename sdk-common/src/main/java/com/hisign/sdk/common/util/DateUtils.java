package com.hisign.sdk.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @Title:
 *   时间通用类
 * @description:
 * @Copyright: Copyright (c) 2008
 * @Company: hisign.com.cn
 * @author linengjin 
 * @E-mail:lnj2050@hotmail.com
 * @create time：2012-2-1 上午11:30:21
 * @version 1.0
 */
public class DateUtils {
	
	/**
	 * 获取经历时间
	 * @param elapsedTime
	 * @return
	 */
    public static String getElapsedTimeString(long elapsedTime){
	    long ms = elapsedTime % 1000L;
	    long second = elapsedTime / 1000L;
	    long minute = second / 60L;
	    long hour = minute / 60L;
	    long day = hour / 24L;
	    second %= 60L;
	    minute %= 60L;
	    hour %= 24L;

	    StringBuffer sb = new StringBuffer();
	    if (day != 0L) {
	      sb.append(day);
	      sb.append("d:");
	    }
	    if (hour != 0L) {
	      sb.append(hour);
	      sb.append("h:");
	    }
	    if (minute != 0L) {
	      sb.append(minute);
	      sb.append("m:");
	    }
	    if (second != 0L) {
	      sb.append(second);
	      sb.append("s:");
	    }

	    sb.append(ms);
	    sb.append("ms");

	    return sb.toString();
	}

    
	/**
	 * 获取当前时间，格式yyyy-mm-dd hh:MM:ss
	 * @return
	 */
	public static String getCurrentDateTime(){
		Calendar calendar = Calendar.getInstance();
		long lNow = System.currentTimeMillis();
		calendar.setTimeInMillis(lNow);
		Date nowDate = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String callTime = df.format(nowDate);
		return callTime;
	}
	
	/**
	 * 获取今天的开始时间
	 * @param today
	 * @return 格式:YYYY-MM-DD HH:MM:SS
	 * @throws Exception
	 */
	public static String getTodayFromTime(Calendar today) throws Exception{	
		SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormatDate.format(today.getTime())+" 00:00:00";
	}
	/**
	 * 获取今天的结束时间
	 * @param today
	 * @return 格式:YYYY-MM-DD HH:MM:SS
	 * @throws Exception
	 */
	public static String getTodayToTime(Calendar today) throws Exception{
		SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormatDate.format(today.getTime())+" 23:59:59";
	}
	/**
	 * 获取当前时间
	 * @param today
	 * @return 格式:YYYY-MM-DD HH:MM:SS
	 * @throws Exception
	 */
	public static String getNowTime(Calendar today) throws Exception{
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormatAll.format(today.getTime());
	}
	/**
	 * 获取本周开始时间
	 * @param today
	 * @return
	 */
	public static String getThisWeekFromTime(Calendar today) throws Exception{
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		today.add(Calendar.DATE, 1-dayOfWeek);	
		SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
		String result = simpleDateFormatDate.format(today.getTime())+" 00:00:00";
		today.add(Calendar.DATE , dayOfWeek-1);
		return result;
	}
	/**
	 * 获取本周结束时间
	 * @param today
	 * @return
	 * @throws Exception
	 */
	public static String getThisWeekToTime(Calendar today) throws Exception{
		int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
		today.add(Calendar.DATE, 7-dayOfWeek);
		SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
		String result = simpleDateFormatDate.format(today.getTime())+" 23:59:59" ;
		today.add(Calendar.DATE, dayOfWeek-7);
		return result;
	}
	
	public static String getWeekNameByIndex(int i) throws Exception{
		String name = "";
		switch(i){
			case 1:
					name = "周日";
				break;
			case 2:
					name = "周一";
				break;
			case 3:				
					name = "周二";
				break;
			case 4:				
					name = "周三";
				break;
			case 5:				
					name = "周四";
				break;
			case 6:
					name = "周五";
				break;
			case 7:
					name = "周六";
				break;
		}
		return name;
	}
	
	
	public static void main(String[] args){
		Calendar c = Calendar.getInstance();
		try {
			System.out.println("今天开始时间："+getTodayFromTime(c));
			System.out.println("今天结束时间："+getTodayToTime(c));
			System.out.println("当前时间："+getNowTime(c));
			System.out.println("本周开始时间："+getThisWeekFromTime(c));
			System.out.println("本周结束时间："+getThisWeekToTime(c));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 输入：YYYY-MM-DD 每隔几个月 第几天 持续几天
	 * 输出：YYYY-MM-DD 到当前时间总共有多少天符合
	 * */
	public static List<Date> getLastDays(String StartTime,int numMonth,int numDay,int n,Date date,int days_spen){

		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");
		Calendar ycal = Calendar.getInstance();
		ycal.setTime(date);
		ycal.add(Calendar.DAY_OF_YEAR,-days_spen);
		//SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date eventDate = ycal.getTime();


		List<Date> list = new ArrayList<Date>();
		Date newDate = new Date();
		try {
			Date StartDate = dateFormat.parse(StartTime);
			Date eventStop = StartDate;
			Calendar cal = Calendar.getInstance();
			int newYear = cal.get(Calendar.YEAR);

			int year = 0;
			int month = 0;
			int day = 0;
			int newDay = 0;
			if(numMonth == 1){
				//每隔一个月的处理
				//如果配置开始时间晚于样品开始时间那么就以配置开始时间为计算开始时间，反之以样品开始时间为准
				if(dateCompare(StartDate,eventDate)==0 ||dateCompare(StartDate,eventDate)==2){					
					cal.setTime(eventDate);
					eventStop = eventDate;
				}else{
					cal.setTime(StartDate);
				}


			}else{
				//每隔多个月的处理流程
				cal.setTime(StartDate);
				if(dateCompare(StartDate,eventDate)==0 ||dateCompare(StartDate,eventDate)==2){					
					eventStop = eventDate;
				}
			}
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH)+1;
			day = cal.get(Calendar.DAY_OF_MONTH);
			newDay = getDay(year,month,numDay);
			
			int m = dateDiff("M",cal.getTime(),date);


			for(int i = 0;i<=m;i+=numMonth){

				if(i!=0){
					cal.add(Calendar.MONTH, numMonth);
					year = cal.get(Calendar.YEAR);
					month = cal.get(Calendar.MONTH)+1;
					day = cal.get(Calendar.DAY_OF_MONTH);
					newDay = getDay(year,month,numDay);
					newDate = getDate(year,month,newDay);
					
					if(dateCompare(newDate,eventStop)==1||dateCompare(newDate,eventStop)==0){
						list.add(newDate);
												
					}
					for(int d=1;d<n;d++){
						Calendar calDay = Calendar.getInstance();
						calDay.setTime(newDate);
						calDay.add(Calendar.DAY_OF_MONTH, d);
						if(dateCompare(calDay.getTime(),eventStop)==1||dateCompare(calDay.getTime(),eventStop)==0){
							list.add(calDay.getTime());
						}
					}
				}else{
					
					newDate = getDate(year,month,newDay);				

					if(dateCompare(newDate,eventStop)==1||dateCompare(newDate,eventStop)==0){
						//本月这一天属于范围之内
						list.add(newDate);	
					}
					for(int d=1;d<n;d++){
						Calendar calDay = Calendar.getInstance();
						calDay.setTime(newDate);
						calDay.add(Calendar.DAY_OF_MONTH, d);
					
						if(dateCompare(calDay.getTime(),eventStop)==1||dateCompare(calDay.getTime(),eventStop)==0){
							//本月这一天属于范围之内
							list.add(calDay.getTime());
						}
					}
					
				}
			}

		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 输入：时间 增加天数
	 * 输出：变动后的日期
	 * */
	private static Date getNewDate(Date newDate,int numDay){
		Calendar cd = Calendar.getInstance();
		cd.setTime(newDate);
		cd.add(Calendar.DATE,numDay);

		return cd.getTime();

	}

	/**
	 * 输入：YYYY-MM-DD 每隔几个月 第几天
	 * 输出：YYYY-MM-DD 到当前时间总共有多少天符合
	 * */
	public static List<Date>  getLastDays(String StartTime,int numMonth,int numDay){
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		List<Date> list = new ArrayList<Date>();
		try {
			Date StartDate = dateFormat.parse(StartTime);

			Calendar cal = Calendar.getInstance();
			int newYear = cal.get(Calendar.YEAR);
			cal.setTime(StartDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_YEAR);
			int newDay = getDay(year,month,numDay);
			if(day<=newDay){
				//本月这一天属于范围之内
				list.add(getDate(year,month,newDay));
			}
			for(int y = year;y<=newYear;y++){
				if(y==year){
					for(int m = month;m<=12;m+=numMonth){
						newDay = getDay(year,month,numDay);
						list.add(getDate(y,m,newDay));
					}
				}else{
					for(int m = 1;m<=12;m+=numMonth){
						newDay = getDay(year,month,numDay);
						list.add(getDate(y,m,newDay));
					}			
				}	
			}

		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 输入年 月 日
	 * 输出 DATE型时间
	 * */
	private static Date getDate(int year,int month,int day){
		String date = year+"-"+month+"-"+day;
		try {
			return getStringByDate(date);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			return null;
		}
	}


	/**
	 * 输入：YYYY-MM-DD(配置开始时间) 每隔几个周 和星期几（多个星期的最合）计算开始时间 样品空间天数
	 * 输出：YYYY-MM-DD 到当前时间总共有多少天符合
	 * @throws ParseException 
	 * */
	public static List<Date> getLastWeek(String startTime,int numWeek,String weeks,Date date,int days_spen) throws ParseException{

		Date startDate = getStringByDate(startTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_YEAR,-days_spen);
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date swatchDate = cal.getTime();
		if(numWeek == 1){
			//判断样品开始天和配置开始时间那个时间大
			if(dateCompare(swatchDate,startTime)==1){
				startDate = swatchDate;
			}
		}
		List<Date> weekList = new ArrayList<Date>();
		//找到开始这天是星期几
		int week = getWeek(startDate);
		//找到开始这天所在周的，需要的星期
		String[] weekStr = weeks.split(";");

		for(int i=0;i<weekStr.length;i++){
			int newWeek = Integer.parseInt(weekStr[i]);
			
			if(week == newWeek &&dateCompare(startDate,startTime)==0){
			
				weekList.add(startDate);
			}
			weekList = getWeeks(startDate,numWeek,newWeek,weekList,date,startDate);
		}

		return weekList;
	}

	private static Date getStringByDate(String DateStr) throws ParseException{
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		return dateFormat.parse(DateStr);

	}

	/**
	 * 输入开始时间，每隔几周，星期几，list
	 * 输出list
	 * */
	private static List<Date> getWeeks(Date startDate,int numWeek,int week,List<Date> list,Date stopDate,Date swatchDate){
		int week1 = getWeek(startDate);
		Calendar cal = Calendar.getInstance(); 

		try {

			cal.setTime(startDate);

			//获得本周第一天 星期天的日期是那天
			int   day   =   cal.get(Calendar.DAY_OF_WEEK);   
			cal.add(Calendar.DATE,(day-1)*(-1));   

			//获得本周星期几这天的日期
			cal.add(Calendar.DATE,week);

			Date newDate = cal.getTime();

			int num = getbeApartFromWeek(newDate,stopDate);

			for(int i=0;i<=num;i+=numWeek){
				Date date = getNewDate(newDate,i*7);

				if(dateCompare(date,swatchDate)==1||dateCompare(date,swatchDate)==0){
					list.add(date);
				}

			}

			return list;
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 指定年 月的第几天是多少号
	 * 输入： year 年  month 月 numDay第几天 
	 * 输出：Date
	 * */
	public static int getDay(int year,int month,int numDay) {
		Calendar c = Calendar.getInstance(); 
		if(year ==0){	
			year = c.get(Calendar.YEAR);
		}

		if(month ==0){
			//当前时间所在年和月的第几天是多少号		
			month  = c.get(Calendar.MONTH)+1;
		}
		
		int days = getDays(year,month);
		if(numDay<0){
			days = days + 1 + numDay;
		}if(numDay>0 && numDay<days){
			days = numDay;
		}

		return days;

	}

	/**
	 * 输入 开始时间
	 * 输出 开始时间距离现在相隔周数
	 * */
	private static int getbeApartFromWeek(String startTime){
		Calendar cal = Calendar.getInstance(); 

		try {
			Long endTime = cal.getTimeInMillis();
			Date startDate = getStringByDate(startTime);
			cal.setTime(startDate);
			Long startTimeLong = cal.getTimeInMillis();
			long num = (endTime-startTimeLong)/(7*24*3600000);

			return Integer.parseInt(num+"");
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 输入 开始时间 结束时间
	 * 输出 开始时间距离现在相隔周数
	 * */
	private static int getbeApartFromWeek(Date startTime,Date stopDate){
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date endTime = dateFormat.parse(dateFormat.format(stopDate.getTime()));
			Date startDate = dateFormat.parse(dateFormat.format(startTime.getTime()));
			cal.setTime(startDate);
			Long startTimeLong = cal.getTimeInMillis();
			cal.setTime(endTime);
			Long endTimeLong = cal.getTimeInMillis();
			long num = (endTimeLong-startTimeLong)/(7*24*3600000);

			return Integer.parseInt(num+"");
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 判读2个日期是否相同
	 *输入 d1：日期1    d2：日期2
	 *
	 *输出 0：2个日期相同  1:date1日期>date2日期   2：date1日期<date2日期
	 * */
	public static int dateCompare(String date1,String date2){
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date d1 = dateFormat.parse(date1);
			Date d2 = dateFormat.parse(date2);

			if(d1.compareTo(d2)==0){
				return 0;				
			}else if(d1.compareTo(d2)>0){
				return 1;
			}else{
				return 2;
			}
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} 
		return 3;
	}

	/**
	 * 判读2个日期是否相同
	 *输入 d1：日期1    d2：日期2
	 *
	 *输出 0：2个日期相同  1:date1日期>date2日期   2：date1日期<date2日期
	 * */
	public static int dateCompare(Date date1,String date2){


		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date d1 = dateFormat.parse(dateFormat.format(date1.getTime()));
			Date d2 = dateFormat.parse(date2);

			if(d1.compareTo(d2)==0){
				return 0;				
			}else if(d1.compareTo(d2)>0){
				return 1;
			}else{
				return 2;
			}
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return 3; 
	}

	/**
	 * 	判读2个日期是否相同
	 *	输入 d1：日期1    d2：日期2
	 *	输出 0: 2个日期相同  
	 *	输出 1: date1日期>date2日期   
	 *	输出 2：date1日期<date2日期
	 * */
	public static int dateCompare(Date date1,Date date2){
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date d1 = dateFormat.parse(dateFormat.format(date1.getTime()));
			Date d2 = dateFormat.parse(dateFormat.format(date2.getTime()));

			if(d1.compareTo(d2)==0){
				return 0;				
			}else if(d1.compareTo(d2)>0){
				return 1;
			}else{
				return 2;
			}
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return 3; 
	}

	static int [] days = {0,31,0,31,30,31,30,31,31,30,31,30,31,}; 
	/**
	 * 获得每个月的天数
	 * 参数 mon 对应每一个月,比如一月时 mon=1
	 * */ 
	public static int getDays(int year,int mon){ 

		Calendar c = Calendar.getInstance(); 
		if(year ==0){
			year = c.get(Calendar.YEAR);
		}
		//判断是否为闰年,是 b=true 否 b=false 
		boolean b = (year % 100 != 0 && year % 4 == 0) || year % 400 == 0; 
		if(mon==2)return b?29:28; 
		return days[mon]; 
	} 

	/**
	 * 判断某个日期是星期几
	 * 输入一个yyyy-MM-dd的日期字符串
	 * 输出日期 0:星期日 1:星期一  2:星期二  3:星期三   4:星期四   5:星期五   6:星期六
	 * */
	public static int getWeek(String date){
		int s = -1;
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date d1 = dateFormat.parse(date);
			SimpleDateFormat formatD = new SimpleDateFormat("E");
			s = getWeekNum(formatD.format(d1));
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

		return s;
	}

	/**
	 * 输入一个yyyy-MM-dd的DATE型
	 * 输出日期 0:星期日 1:星期一  2:星期二  3:星期三   4:星期四   5:星期五   6:星期六
	 * */
	public static int getWeek(Date date){

		SimpleDateFormat formatD = new SimpleDateFormat("E");
		int s = getWeekNum(formatD.format(date));
		return s;
	}

	private static int getWeekNum(String weekStr){
		if(weekStr.equals("星期一")){
			return 1;
		}
		if(weekStr.equals("星期二")){
			return 2;
		}
		if(weekStr.equals("星期三")){
			return 3;
		}
		if(weekStr.equals("星期四")){
			return 4;
		}
		if(weekStr.equals("星期五")){
			return 5;
		}
		if(weekStr.equals("星期六")){
			return 6;
		}
		if(weekStr.equals("星期日")){
			return 0;
		}
		return -1;
	} 

	public static Date getData(String date,String format) throws ParseException{
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(format); 

		return dateFormat.parse(date);

	}

	public static String getDateString(Date date,String format){
		SimpleDateFormat   dateFormat   =   new   SimpleDateFormat(format); 

		return dateFormat.format(date);
	}

	public static int dateDiff(String interval,java.util.Date date1, java.util.Date date2) { 
		int intReturn=0 ; 
		if (date1==null || date2==null || interval==null) return intReturn ; 
		try { 
			java.util.Calendar cal1 = java.util.Calendar.getInstance(); 
			java.util.Calendar cal2 = java.util.Calendar.getInstance(); 

//			different date might have different offset 
			cal1.setTime(date1); 
			long ldate1 = date1.getTime() + cal1.get(java.util.Calendar.ZONE_OFFSET) + cal1.get(java.util.Calendar.DST_OFFSET); 

			cal2.setTime(date2); 
			long ldate2 = date2.getTime() + cal2.get(java.util.Calendar.ZONE_OFFSET) + cal2.get(java.util.Calendar.DST_OFFSET); 

//			Use integer calculation, truncate the decimals 
			int hr1 = (int)(ldate1/3600000); 
			int hr2 = (int)(ldate2/3600000); 

			int days1 = hr1/24; 
			int days2 = hr2/24; 

			int yearDiff = cal2.get(java.util.Calendar.YEAR) - cal1.get(java.util.Calendar.YEAR); 
			int monthDiff = yearDiff * 12 + cal2.get(java.util.Calendar.MONTH) - cal1.get(java.util.Calendar.MONTH); 
			int dateDiff = days2 - days1; 
			int hourDiff = hr2 - hr1; 
			int minuteDiff = (int)(ldate2/60000) - (int)(ldate1/60000); 
			int secondDiff = (int)(ldate2/1000) - (int)(ldate1/1000); 

			if (interval.equals("Y")){ 
				intReturn = yearDiff; 
			} 
			else if (interval.equals("M")){ 
				intReturn = monthDiff; 
			} 
			else if (interval.equals("D")){ 
				intReturn = dateDiff; 
			} 
			else if (interval.equals("H")){ 
				intReturn = hourDiff; 
			} 
			else if (interval.equals("m")){ 
				intReturn = minuteDiff; 
			} 

		}catch(Exception e){
			e.printStackTrace();
		}
		return intReturn;
	}
	
	/**
	 * 格式化long型时间
	 * @param date
	 * @return
	 */
	public static String getDateFormat(long date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	/**
	 * 格式化Date型时间
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date.getTime());
	}
	
	/**
	 * 只返回年月日
	 * @param date
	 * @return
	 */
	public static String getDayFormat(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date.getTime());
	}
    
    /**
     * 通过时间串获取毫秒数
     * @param time
     * @return
     */
	public static long getMillsByTimeStr(String timeStr){
		try{
			SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return simpleDateFormatAll.parse(timeStr).getTime();
		}catch(Exception ex){
		}
		
		return -1;
	}
	
    /**
     * 通过时间串获取毫秒数
     * @param time
     * @return
     */
    public static long getMillsByTimeStr(String timeStr,String format){
        try{
            SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat(format);
            return simpleDateFormatAll.parse(timeStr).getTime();
        }catch(Exception ex){
        }
        
        return -1;
    }
	
	/**
	 * 获取整点毫秒数
	 * @param timeStr
	 * @return
	 * @throws Exception
	 */
	public static long getIntegratedHourMills(String timeStr) throws Exception{
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormatAll.parse(timeStr);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis();
	}
	
	/**
	 * 通过时间获取毫秒值
	 * @param millis
	 * @return
	 */
	public static String getTimeStrByMills(long millis,String format){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		Date date = c.getTime();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	   /**
     * 通过时间获取毫秒值
     * @param millis
     * @return
     */
    public static String getTimeStrByMills(long millis){
        SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        Date date = c.getTime();
        String timeStr = simpleDateFormatAll.format(date);
        return timeStr;
    }
	
	/**
	 * 通过时间获取毫秒值
	 * @param millis
	 * @return
	 */
	public static String getMinuteTimeStrByMills(long millis){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		Date date = c.getTime();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	/**
	 * 将时间转换为string类型
	 * @param c
	 * @return
	 */
	public static String getTimeStrByCalendar(Calendar c){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = c.getTime();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	/**
	 * 获取年月日yyyy-MM-dd格式串
	 * @param millis
	 * @return
	 */
	public static String getDateStrByMills(long millis){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		Date date = c.getTime();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	/**
	 * 获取年月日yyyy-MM-dd格式串
	 * @param c
	 * @return
	 */
	public static String getDateStrByCalendar(Calendar c){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd");
		Date date = c.getTime();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	/**
	 * 获取时分秒
	 * @param c
	 * @return
	 */
	public static String getHMS(Calendar c){
		StringBuffer sb = new StringBuffer();
		int h = c.get(Calendar.HOUR_OF_DAY);
		if(h < 10){
			sb.append("0").append(h);
		}else{
			sb.append(h);
		}
		sb.append(":");
		
		int m = c.get(Calendar.MINUTE);
		if(m < 10){
			sb.append("0").append(m);
		}else{
			sb.append(m);
		}
		sb.append(":");
		
		int s = c.get(Calendar.SECOND);
		if(s < 10){
			sb.append("0").append(s);
		}else{
			sb.append(s);
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取时分秒
	 * @param timeMills
	 * @return
	 */
	public static String getHMS(long timeMills){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMills);
		String hms = getHMS(c);
		return hms;
	}
	
	/**
	 * 获取一天开始的时间，也就是yyyy-MM-dd 00:00:00
	 * @param dateMillis
	 * @return
	 */
	public static long getDateStartMills(long dateMillis){
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(dateMillis);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 获取一天开始的时间，也就是yyyy-MM-dd 00:00:00
	 * @param dateMillis
	 * @return
	 */
	public static long getDateEndMills(long dateMillis){
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(dateMillis);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		cal.set(Calendar.MILLISECOND,999);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 在某天的基础上，增加N天
	 * @param dateMillis
	 * @param dayNum
	 * @return
	 */
	public static long addDays(long dateMillis,int dayNum){
		Calendar cal = Calendar.getInstance(); 
		cal.setTimeInMillis(dateMillis);
		cal.add(Calendar.DAY_OF_MONTH,dayNum);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 在某天的基础上，增加N天
	 * @param dateMillis
	 * @param dayNum
	 * @return
	 */
	public static String addMillisToDate(String dateStr,long during) throws Exception{
		String newDateStr = dateStr;
		long dateMills = getMillsByTimeStr(newDateStr);
		long newDateMillis = dateMills + during;
		newDateStr = getTimeStrByMills(newDateMillis);
		return newDateStr;
	}
	
	/**
	 * 获取当前时间串
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentTimeStr(){
		SimpleDateFormat simpleDateFormatAll = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String timeStr = simpleDateFormatAll.format(date);
		return timeStr;
	}
	
	
	/**
	 * 获取时间所在月的日期，比如 1,2,3,4,5,6...30,31
	 * @param mills
	 * @return
	 */
	public static int getDayInMonth(long mills){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}
	
	/**
	 * 获取HH:mm:ss格式的时分秒毫秒数
	 * @param hmsStr
	 * @return
	 */
	public static long getHMSMills(String hmsStr){
		String []arr = hmsStr.split(":");
		long hour = Long.parseLong(arr[0]);
		long minute = Long.parseLong(arr[1]);
		long second = Long.parseLong(arr[2]);
		long hmsMills = hour*60*60*1000 + minute*60*1000 + second * 1000;
		return hmsMills;
	}
	
	/**
	 * 获取完整时间的时分秒数
	 * @param dateMillis
	 * @return
	 */
	public static long getHMSMills(long dateMillis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateMillis);
		long hour = c.get(Calendar.HOUR_OF_DAY);
		long minute = c.get(Calendar.MINUTE);
		long second = c.get(Calendar.SECOND);
		long hmsMills = hour*60*60*1000 + minute*60*1000 + second * 1000;
		return hmsMills;
	}
	
	/**
	 * 获取当前时间是一周中的第几天，周日:0,周一:1  依次类推
	 * @param millis
	 * @return
	 */
	public static int getWeek(long millis){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return dayOfWeek;
	}
	
	/**
	 * 获取前一个月的同一天时间
	 * @param mills
	 * @return
	 */
	public static long getPerMonthSameDateMills(long mills){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		cal.add(Calendar.MONTH, -1);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 将时分秒组织成秒
	 * @param time
	 * @return
	 * @throws Exception
	 */
	public static long toSecondTime(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return (hour * 60 + minute) * 60 + second;

	}
	
	/**
	 * 判断是否为工作日
	 * @param time
	 * @return
	 */
	public static boolean isWorkingDate(long time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if ((day != Calendar.SUNDAY) && (day != Calendar.SATURDAY)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 缺省按照d MMM yyyy HH:mm:ss Z和美国本地化转化日期为字符串
	 * 
	 * @param dateStr
	 * 
	 */
	public static String getString(Date date) {
		return getString("yyyy-MM-dd HH:mm:ss", Locale.US, date);
	}

	/**
	 * 按照日前模式和本地化转化日期为字符串
	 * 
	 * @param pattern
	 * @param locale
	 * @param date
	 * 
	 */
	private static String getString(String pattern, Locale locale, Date date) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, locale);

			return df.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();

			return null;
		}
	}

	/**
	 * 转化long型timestamp为字符串
	 * 
	 */
	public static String getTimestamp(long dcTime) {
		Date date = new Date(dcTime);
		return getString(date);
	}
	
	/**
	 * 取long型时间转为年月日时格式
	 * @param time
	 * @return
	 * @throws Exception
	 */
    public static long getTimeDID(long time) throws Exception {
        if(time == 0l)
            return 0l;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        String s = format.format(new Date(time));
        return Long.parseLong(s);

    }
}
