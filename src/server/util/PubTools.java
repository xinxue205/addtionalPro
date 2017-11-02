/**
 * 
 */
package server.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:04:08
 * @Description
 * @version 1.0 Shawn create
 */
public class PubTools {
	private static ThreadLocal calendarCache = new ThreadLocal();

	public static final Logger caslog = Logger.getLogger("getCasReport"); // 日志记录器

	public static final Logger log = Logger.getLogger("getJournal"); // 日志记录器

	/**
	 * 日期字符串转化为YYYYMMDD格式的字符串
	 * 
	 * @param dt
	 *            传入将被格式化为字符串的日期型变量
	 * @return String 返回字符串YYYYMMDD
	 */
	public static String dateToFormatString(java.util.Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(dt);
	}

	/**
	 * 把日期转换成为字符串
	 * 
	 * @param dt
	 *            输入的日期
	 * @param fmtStr：
	 *            格式字符串
	 * @return String 按照指定的格式转换出字符串
	 */
	public static String dateToString(Date dt, String fmtStr) {

		SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		return format.format(dt);
	}

	/**
	 * 日期转化为字符串
	 * 
	 * @param dt
	 *            传入将被格式化为字符串的日期型变量
	 * @return String 返回当前的日期时间yyyy-MM-dd
	 */
	public static String dateToString(java.util.Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(dt);
	}

	/**
	 * 日期字符串转化为YYYYMMDD格式的字符串
	 * 
	 * @param strDate
	 *            传入日期格式的字符串
	 * @return String 返回字符串YYYYMMDD
	 */
	public static String dateToString(String strDate) {
		strDate = strDate + "-";
		String[] strs = doSplit(strDate, '-');
		if (strs[1].length() == 1)
			strs[1] = "0" + strs[1];
		if (strs[2].length() == 1)
			strs[2] = "0" + strs[2];
		return strs[0] + strs[1] + strs[2];
	}

	/**
	 * 计算两个日期的时间差
	 * 
	 * @param sDate
	 *            起始日期
	 * @param fDate
	 *            结束日期
	 * @return int 两个日期的时间差，返回天数
	 */
	public static int diffDate(String sDate, String fDate) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date sdate = myFormatter.parse(sDate);
		java.util.Date fdate = myFormatter.parse(fDate);
		int diffdate = (int) ((fdate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24));
		return Math.abs(diffdate);
	}

	/**
	 * 计算两个日期时间的时间差
	 * 
	 * @param sTime
	 *            起始日期时间
	 * @param fTime
	 *            结束日期时间
	 * @return int 返回时间差，返回秒
	 */
	public static int diffTime(String sTime, String fTime) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		java.util.Date stime = myFormatter.parse(sTime);
		java.util.Date ftime = myFormatter.parse(fTime);
		int second = (int) ((ftime.getTime() - stime.getTime()) / 1000);
		return second;
	}

	/**
	 * 根据chr分割字符串，因为String类自带的split不支持以"|"为分割符， 它把"|"当成或的关系
	 * 
	 * @param str
	 *            将要被分割的串
	 * @param chr
	 *            分割符号
	 * @return String[] 分割后的字符串数组
	 */
	public static String[] doSplit(String str, char chr) {
		return mySplit(str, chr);
	}

	private static Calendar getCalendar() {
		Calendar calendar = (Calendar) calendarCache.get();
		if (calendar == null) {
			calendar = Calendar.getInstance();
			calendarCache.set(calendar);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		return calendar;
	}

	public static String getDay(int day) {
		Date today;
		Calendar cal = getCalendar();
		today = cal.getTime();
		Date yesterday = new Date(today.getTime() - day * 24 * 3600 * 1000);
		return dateToString(yesterday);
	}

	public static String getPreDayString(int day) {
		Date today = new Date();
		Calendar cal = getCalendar();
		today = cal.getTime();
		Date yesterday = new Date(today.getTime() - day * 24 * 3600 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(yesterday);
	}

	public static String getPreDayString(int day, String sSplit) {
		Date today = new Date();
		Calendar cal = getCalendar();
		today = cal.getTime();
		Date yesterday = new Date(today.getTime() - day * 24 * 3600 * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy" + sSplit + "MM"
				+ sSplit + "dd");
		return format.format(yesterday);
	}

	/**
	 * 返回YYYYMMDDHHNNSS 或者 YYYYMMDDHHNNSS.MSEC 14位的时间
	 * 
	 * @param boolena
	 *            hadMSec true表示包含毫秒
	 * @return String
	 */
	public static String getFullNowTime() {
		return getFullNowTime(false);
	}

	public static String getFullNowTime(boolean hadMSec) {
		// Calendar calendar=Calendar.getInstance();
		Calendar calendar = getCalendar();
		StringBuffer timestr = new StringBuffer();
		int date = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		timestr.append(year);
		if (month < 10) {
			timestr.append("0");
		}
		timestr.append(month);
		if (date < 10) {
			timestr.append("0");
		}
		timestr.append(date);
		if (hour < 10) {
			timestr.append("0");
		}
		timestr.append(hour);
		if (minute < 10) {
			timestr.append("0");
		}
		timestr.append(minute);
		if (second < 10) {
			timestr.append("0");
		}
		timestr.append(second);

		if (hadMSec) {
			timestr.append(".");
			timestr.append(calendar.get(Calendar.MILLISECOND));
		}
		return timestr.toString();
	}

	public static String getSystemDate() {
		Date today;
		Calendar cal = getCalendar();
		today = cal.getTime();
		return dateToFormatString(today);
	}

	public static String getYesterday() {
		Date today;
		Calendar cal = getCalendar();
		today = cal.getTime();
		Date yesterday = new Date(today.getTime() - 24 * 3600 * 1000);
		return dateToString(yesterday);
	}

	public static String getSystemDateTimeString() {
		Date today = new Date();
		Calendar cal = getCalendar();
		today = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(today);
	}

	public static void main(String[] args) {
		try {
			System.out.println(PubTools.diffDate("20090814", "20100114"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String[] strDateRange = PubTools.getDateRange("20090205",
		// "20090305");
		// for (int days = 0; days < strDateRange.length; days++) {
		// System.out.println(strDateRange[days]);
		// }
	}

	public static String[] mySplit(String str, char s) {
		return mySplit(str, s, false);
	}

	public static String[] mySplit(String str, char s, boolean repeat) {
		String[] t;
		char[] c;
		// StringBuffer x = new StringBuffer();
		int i, j, n, k;
		t = null;
		if (str == null)
			return null;
		if (str.length() == 0)
			return null;
		c = str.toCharArray();
		n = c.length;

		for (j = 0, i = 0; i < n; i++) {
			if (c[i] == s) {
				if (repeat) {
					for (int kk = i + 1; kk < n; kk++) {
						if (c[kk] == s)
							i++;
						else
							break;
					}
				}
				j++;
			}
		}
		// System.out.println(j);
		t = new String[j + 1];

		for (k = 0, j = 0, i = 0; i < n; i++) {
			if (c[i] == s) {
				if (repeat) {
					for (int kk = i + 1; kk < n; kk++) {
						if (c[kk] == s)
							i++;
						else
							break;
					}
				}
				t[j] = str.substring(k, i);
				j++;
				k = i + 1;
			}
		}
		t[j] = str.substring(k, i);

		return t;
	}

	public static String readBuffer(ByteBuffer buffer) {
		byte[] data = new byte[buffer.remaining()];
		int position = buffer.position();
		buffer.get(data);
		buffer.position(position);
		return new String(data);

	}

	public static InetSocketAddress resolveAddress(String addr)
			throws IllegalArgumentException {
		try {
			String[] tuple = addr.split(":");
			return new InetSocketAddress(tuple[0].substring(0, tuple[0]
					.indexOf("/")), new Integer(tuple[1]).intValue());

		} catch (Exception ex) {
			throw new IllegalArgumentException(addr);
		}
	}

	public static InetSocketAddress resolveAddressParam(String addr, int port)
			throws IllegalArgumentException {

		try {
			return new InetSocketAddress(addr, port);

		} catch (Exception ex) {
			throw new IllegalArgumentException(addr);
		}
	}

	public static void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException ignore) {
		}
	}

	public static String trimNull(String source) {
		String resStr = source;
		if (resStr == null)
			resStr = "";
		return resStr.trim();
	}

	/**
	 * 根据起始日期及结束日期返回中间的间隔的日期
	 * 
	 * @param startDate
	 *            起始日期 格式 "yyyyMMdd"
	 * @param endDate
	 *            结束日期 格式 "yyyyMMdd"
	 * @return 包含起始日期及结束日期的日期数组
	 */
	public static String[] getDateRange(String startDate, String endDate) {
		String[] strDateRange = null;
		// 设定时间的格式 从配置文件里获取文件时间格式
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		// 获取时间值
		Date fileTimeLower = null;
		Date fileTimeUpper = null;
		try {
			fileTimeLower = myFormatter.parse(startDate);
			fileTimeUpper = myFormatter.parse(endDate);
		} catch (ParseException e) {
			log.error("getDateRang Catch Exception:" + e.toString());
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
	 * 在报文前面加上4位的报文长度 报文长度只算报文的真正长度 不加上前面的4位长
	 * 
	 * @param sendMsg
	 *            不带长度的原报文信息
	 * @return 加上4位长度的报文信息
	 * 
	 */
	public static String addStrngLength(String sendMsg) {
		// StringBuffer sb = new StringBuffer();
		// Formatter formatter = new Formatter(sb, Locale.US);
		// Object[] obj = { Integer.valueOf(sendMsg.length()) };
		NumberFormat nf = new DecimalFormat("0000");
		String s = nf.format(sendMsg.length());
		return s + sendMsg;

		// formatter.format("%04d", obj);
		// String sendString = sb.toString();
		// sendString += sendMsg;
		// return sendString;
	}

	/**
	 * 减去报文前面的四位长度
	 * 
	 * @param revMsg
	 *            带四位长度的原报文信息
	 * 
	 * @return 减去四位长度的报文信息
	 */
	public static String removeStringLength(String revMsg) {
		String revString = revMsg.substring(4, revMsg.length());
		return revString;
	}

	/**
	 * 把Byte数组的数据转成Base64编码的字符串
	 * 
	 * @param byteBuf
	 *            byte 的数组
	 * @param byteLen
	 *            数组的长度
	 * @return Base64编码的字符串
	 */
	public static String ConvertByteToBase64String(byte[] byteBuf, int byteLen) {

		byte[] convertBuf = new byte[byteLen];
		System.arraycopy(byteBuf, 0, convertBuf, 0, byteLen);

		BASE64Encoder enc = new BASE64Encoder();
		String convertString = enc.encode(convertBuf);

		return convertString;
	}

	/**
	 * 把Base64编码的字符串转成Byte类型
	 * 
	 * @param strDataBuf
	 *            Base64编码的字符串
	 * @return byte类型的数组
	 */
	public static byte[] ConvertBase64StringToByte(String strDataBuf) {

		try {
			BASE64Decoder dec = new BASE64Decoder();
			byte[] byteBlock = dec.decodeBuffer(strDataBuf);

			return byteBlock;
		} catch (IOException ex) {
			// log.error("Convert Base64String to Byte Catch Exception :" +
			// ex.toString());
			// ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断字符串是否数字
	 * 
	 * @param strMessage
	 * 
	 * @return 是数字 true 否 false
	 */
	public static boolean isNumber(String strMessage) {
		char[] str = strMessage.toCharArray();
		int blength = str.length;
		for (int i = 0; i < blength; i++) {
			if (str[i] == '0' || str[i] == '1' || str[i] == '2'
					|| str[i] == '3' || str[i] == '4' || str[i] == '5'
					|| str[i] == '6' || str[i] == '7' || str[i] == '8'
					|| str[i] == '9')
				continue;
			else
				return false;
		}
		return true;
	}

	/**
	 * 检查远程端口是否打开
	 * 
	 * @param strIP
	 *            远程服务器IP
	 * @param iPort
	 *            远程服务端口
	 * @return true 打开 false 关闭
	 */
	public static boolean checkSocketIsOpen(String strIP, int iPort) {
		try {
			Socket socket = new Socket(strIP, iPort); // 建立连接
			socket.close(); // 关闭连接
			log.debug("IP[" + strIP + "]  Port[" + iPort + "] open"); // Socket打开状态
			return true;
		} catch (IOException e) {
			log.debug("IP[" + strIP + "]  Port[" + iPort + "] close"); // Socket关闭状态
			return false;
		}
	}


}
