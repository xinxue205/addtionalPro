package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理�?
 * @author Kevin
 *
 */
public class DateUtil {
	
	/**
	 * 把字符串转换成Date对象
	 * @param datestr
	 * @param pattern 日期模式，默认为"yyyy-MM-dd"
	 * @return Date对象
	 */
	public static Date parseDate(String datestr, String pattern){
		if(datestr==null || "".equals(datestr)){ 
				
			return null;
		}
		if(pattern==null || "".equals(pattern)){
			pattern = "yyyy-MM-dd";
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}
	
	/**
	 * 格式化日�?
	 * @param date Date对象或�?是String对象
	 * @param pattern 日期格式，默认为yyyy-MM-dd
	 * @return 日期字符�?
	 */
	public static String formatDate(Object date, String pattern){
		if(date==null){ 
			return null;
		}
		if(pattern==null || "".equals(pattern)){
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 得到time的当�?(-)delta 这天�?�?�?�?毫秒
	 * @param time
	 * @return
	 */
	public static Date getDayBegin(long time, int delta) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.add(Calendar.DAY_OF_YEAR, delta);
		return new Date(calendar.getTimeInMillis());
	}
	
	/**
	 * 得到time的当�?(-)delta 这天�?3�?9�?9�?99毫秒
	 * @param time
	 * @return
	 */
	public static Date getDayEnd(long time, int delta) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.MILLISECOND, 999);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.add(Calendar.DAY_OF_YEAR, delta);
		return new Date(calendar.getTimeInMillis());
	}
	
	/**
	 * 格式化日�?
	 * @param d  日期（Date�?
	 * @param pattern 模式，如: yyyyMMdd HH:MM:SS
	 * @return 日期字符�?
	 */
	public static String formatDate(Date d, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}
	
	/**
	 * 格式化日�?
	 * yyyymmdd change to yyyy-mm-dd
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getFormatDate(String dateStr) throws Exception {
		if(dateStr==null||dateStr.equals("")){
			return "";
		}
		if(dateStr.length()==10){
			return dateStr;
		}
		
		try {
			String year = dateStr.substring(0, 4);
			String month = dateStr.substring(4, 6);
			String day = dateStr.substring(6, 8);
			return year + "-" + month + "-" + day;
		} catch (Exception e) {
			throw new Exception("��ʽ�����ڳ���",e);
		}
	}
	
	/**
	 * 格式化日�?
	 * yyyymmdd change to yyyy-mm-dd
	 * @param dateStr
	 * @return
	 * @throws Exception
	 */
	public static String getFormatDate(Object dateStr) throws Exception {
		String sDate = "";
		if(null == dateStr ){
			return "";
		}else{
			sDate =  dateStr.toString();
		}
		if(8 != sDate.length()){
			return dateStr.toString();
		}
		
		try {
			String year = dateStr.toString().substring(0, 4);
			String month = dateStr.toString().substring(4, 6);
			String day = dateStr.toString().substring(6, 8);
			return year + "-" + month + "-" + day;
		} catch (Exception e) {
			 e.printStackTrace();
			return dateStr.toString();
		}
	}
	
	
	/**
	 * 		获取系统当前日期
	 * 
	 * @return
	 * 		系统当前日期
	 */
	public static String getSystemDateString() {
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(today);
	}
	
	/**
	 * 		获取系统当前时间
	 * 
	 * @return
	 * 		系统当前日期
	 */
	public static String getSystemTimeString() {
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(today);
	}
	
	/**
	 * 		获取指定格式，与当前日期相隔day天的日期字符�?
	 * @param day
	 * 					与当前日期相隔天�?
	 * @param pattern
	 * 					模式，如: yyyyMMdd HH:MM:SS
	 * @return
	 * 			格式化的日期字符�?
	 */
	public static String getDateString(int day,String pattern){
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		Date yesterday = new Date(today.getTime() - day * 24 * 3600 * 1000);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(yesterday);
	}
	
	/**
	 * 格式化数据库中的时间
	 * 
	 * @param sTime  �?  020000 格式化成  02:00:00
	 * @return HH:mm:ss
	 */
	public static String formatTime(String sTime) {
		String sResult = "";
		SimpleDateFormat sf1 = new SimpleDateFormat("HHmmss");
		SimpleDateFormat sf2 = new SimpleDateFormat("HH:mm:ss");
		try {
			sResult = sf2.format(sf1.parse(sTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sResult;
	}
	
	/**
	 * 取两日期间的天数
	 * date1-date2
	 * @param date1
	 * @param date2
	 * @return
	 * @throws Exception
	 */
	public static long getDateDiff(String date1, String date2){
		long day=0;
		SimpleDateFormat formatdate = new SimpleDateFormat("yyyyMMdd");
		try {
			if(date1 != null && !date1.equals("null") && date2 != null && !date2.equals("null")  )
			{
				Date tmpdate1 = formatdate.parse(date1);
				Date tmpdate2 = formatdate.parse(date2);
				day = (tmpdate1.getTime() - tmpdate2.getTime())
						/ (24 * 60 * 60 * 1000);
				//if(day<0) day=-day;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	 * 取两时间间的时间 以秒为单�?
	 * time1-time2
	 * @param  time1
	 * @param  date2
	 * 
	 * @return long
	 * 
	 */
	public static int getTimeDiff(String time1,String time2)
	{
		int datetime=0;
		int myhour=0;
		int myminute=0;
		int mysecond=0;
		int myhourbeg=0;
		int myminutebeg=0;
		int mysecondbeg=0;
		try {
			myhour = Integer.parseInt(time1.substring(0,2));
			myminute = Integer.parseInt(time1.substring(2,4));
			mysecond = Integer.parseInt(time1.substring(4,6));
				
			myhourbeg = Integer.parseInt(time2.substring(0,2));
			myminutebeg = Integer.parseInt(time2.substring(2,4));
			mysecondbeg = Integer.parseInt(time2.substring(4,6));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		int timenum=(myhour-myhourbeg)*60*60 + (myminute-myminutebeg)*60+mysecond-mysecondbeg;
	    datetime=timenum;
		return datetime; 
	}
	
	/**
	 * 取两日期间的天数 以秒为单�?
	 * date1-date2
	 * @param date1 time1
	 * @param date2 date2
	 * 
	 * @return long
	 * 
	 */
	public static long getDatetimeDiff(String date1,String date2,String time1,String time2)
	{
		long datetime=0;
		int myhour=0;
		int myminute=0;
		int mysecond=0;
		int myhourbeg=0;
		int myminutebeg=0;
		int mysecondbeg=0;
		if(time1.length()!=6)
			time1="0"+time1;
		if(time2.length()!=6)
			time2="0"+time2;
		if(time1.length()!=6)
			time1="000000";
		if(time2.length()!=6)
			time2="000000";
		try {
			myhour = Integer.parseInt(time1.substring(0,2));
			myminute = Integer.parseInt(time1.substring(2,4));
			mysecond = Integer.parseInt(time1.substring(4,6));
				
			myhourbeg = Integer.parseInt(time2.substring(0,2));
			myminutebeg = Integer.parseInt(time2.substring(2,4));
			mysecondbeg = Integer.parseInt(time2.substring(4,6));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		}
		
		long timenum=(myhour-myhourbeg)*60*60 + (myminute-myminutebeg)*60+mysecond-mysecondbeg;
		long datenum=0;
		try {
			datenum = getDateDiff(date1,date2)*24*3600;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    datetime=datenum+timenum;
	    if(datetime<0) datetime=-datetime;
		return datetime;
	}
	
	/**
	 * @author lanhui 20131018
	 * 根据起始日期及结束日期返回中间的间隔的日期数�?
	 * @param sStartDate 起始日期 格式 "yyyyMMdd"
	 * @param endDate 结束日期 格式 "yyyyMMdd"
	 * @return 包含起始日期及结束日期的日期数组
	 */
	public static String[] getDateRange(String sStartDate, String endDate) {
		String[] strDateRange = null;
		// 设定时间的格�?从配置文件里获取文件时间格式
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		// 获取时间�?
		Date fileTimeLower = null;
		Date fileTimeUpper = null;
		try {
			fileTimeLower = myFormatter.parse(sStartDate);
			fileTimeUpper = myFormatter.parse(endDate);
		} catch (ParseException e) {
			return null;
		}
		long lDayLongTime = 1000 * 60 * 60 * 24;
		// 获取时间间隔天数
		int iDayCount = (int) ((fileTimeUpper.getTime() - fileTimeLower
				.getTime()) / lDayLongTime) + 1;

		strDateRange = new String[iDayCount];

		for (int iIndex = 0; iIndex < iDayCount; iIndex++) {
			strDateRange[iIndex] = myFormatter.format(new Date(fileTimeLower
					.getTime()
					+ iIndex * 86400000L));
		}
		return strDateRange;
	}
		/**
	 * 格式化日期时�?
	 * @param sDateValue String对象日期时间数字�?
	 * @return yyyy-MM-dd hh:mm:ss
	 */
	public static String formatDateTime(String sDateValue) {
		StringBuffer sDateFormated = new StringBuffer();
		int iStdDateLen = 14;
		if (sDateValue == null || sDateValue.trim().equals("")
				|| sDateValue.trim().equals("0")) {
			sDateFormated.append("0");
		} else if( sDateValue.length() != iStdDateLen ){
			sDateFormated.append(sDateValue);			
		} else {		// yyyy-MM-dd hh:mm:ss
			sDateFormated.append(sDateValue.substring(0, 4));
			sDateFormated.append("-");
			sDateFormated.append(sDateValue.substring(4, 6));
			sDateFormated.append("-");
			sDateFormated.append(sDateValue.substring(6, 8));
			sDateFormated.append(" ");
			sDateFormated.append(sDateValue.substring(8, 10));
			sDateFormated.append(":");
			sDateFormated.append(sDateValue.substring(10, 12));
			sDateFormated.append(":");
			sDateFormated.append(sDateValue.substring(12, 14));
		}
		return sDateFormated.toString();
	}
	
	/**
	 * 格式化日�?
	 * @param sDateValue String对象日期时间数字�?
	 * @return yyyy-MM-dd
	 */
	public static String formatDate(String sDateValue) {
		StringBuffer sDateFormated = new StringBuffer();
		int iStdDateLen = 8;
		if (sDateValue == null || sDateValue.trim().equals("")
				|| sDateValue.trim().equals("0")) {
			sDateFormated.append("0");
		} else if( sDateValue.length() != iStdDateLen ){
			sDateFormated.append(sDateValue);			
		} else {		// yyyy-MM-dd hh:mm:ss
			sDateFormated.append(sDateValue.substring(0, 4));
			sDateFormated.append("-");
			sDateFormated.append(sDateValue.substring(4, 6));
			sDateFormated.append("-");
			sDateFormated.append(sDateValue.substring(6, 8));
		}
		return sDateFormated.toString();
	}
	
	/**
	 * 取指定时刻前后一定数量单位时间的时刻
	 * @param time1 指定时间
	 * @param num 秒数
	 * @return
	 * @throws Exception
	 */
	public static Date getOtherTime(Date time1,long num){
		long time = time1.getTime();
		Date time2 = new Date(time+(num*1000));
		return time2;
	}
}
