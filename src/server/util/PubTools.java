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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:04:08
 * @Description
 * @version 1.0 Shawn create
 */
public class PubTools {
	private static ThreadLocal calendarCache = new ThreadLocal();

	public static final Logger caslog = Logger.getLogger("getCasReport"); // ��־��¼��

	public static final Logger log = Logger.getLogger("getJournal"); // ��־��¼��

	/**
	 * �����ַ���ת��ΪYYYYMMDD��ʽ���ַ���
	 * 
	 * @param dt
	 *            ���뽫����ʽ��Ϊ�ַ����������ͱ���
	 * @return String �����ַ���YYYYMMDD
	 */
	public static String dateToFormatString(java.util.Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(dt);
	}

	/**
	 * ������ת����Ϊ�ַ���
	 * 
	 * @param dt
	 *            ���������
	 * @param fmtStr��
	 *            ��ʽ�ַ���
	 * @return String ����ָ���ĸ�ʽת�����ַ���
	 */
	public static String dateToString(Date dt, String fmtStr) {

		SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		return format.format(dt);
	}

	/**
	 * ����ת��Ϊ�ַ���
	 * 
	 * @param dt
	 *            ���뽫����ʽ��Ϊ�ַ����������ͱ���
	 * @return String ���ص�ǰ������ʱ��yyyy-MM-dd
	 */
	public static String dateToString(java.util.Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(dt);
	}

	/**
	 * �����ַ���ת��ΪYYYYMMDD��ʽ���ַ���
	 * 
	 * @param strDate
	 *            �������ڸ�ʽ���ַ���
	 * @return String �����ַ���YYYYMMDD
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
	 * �����������ڵ�ʱ���
	 * 
	 * @param sDate
	 *            ��ʼ����
	 * @param fDate
	 *            ��������
	 * @return int �������ڵ�ʱ����������
	 */
	public static int diffDate(String sDate, String fDate) throws Exception {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date sdate = myFormatter.parse(sDate);
		java.util.Date fdate = myFormatter.parse(fDate);
		int diffdate = (int) ((fdate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24));
		return Math.abs(diffdate);
	}

	/**
	 * ������������ʱ���ʱ���
	 * 
	 * @param sTime
	 *            ��ʼ����ʱ��
	 * @param fTime
	 *            ��������ʱ��
	 * @return int ����ʱ��������
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
	 * ����chr�ָ��ַ�������ΪString���Դ���split��֧����"|"Ϊ�ָ���� ����"|"���ɻ�Ĺ�ϵ
	 * 
	 * @param str
	 *            ��Ҫ���ָ�Ĵ�
	 * @param chr
	 *            �ָ����
	 * @return String[] �ָ����ַ�������
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
	 * ����YYYYMMDDHHNNSS ���� YYYYMMDDHHNNSS.MSEC 14λ��ʱ��
	 * 
	 * @param boolena
	 *            hadMSec true��ʾ��������
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
	 * ������ʼ���ڼ��������ڷ����м�ļ��������
	 * 
	 * @param startDate
	 *            ��ʼ���� ��ʽ "yyyyMMdd"
	 * @param endDate
	 *            �������� ��ʽ "yyyyMMdd"
	 * @return ������ʼ���ڼ��������ڵ���������
	 */
	public static String[] getDateRange(String startDate, String endDate) {
		String[] strDateRange = null;
		// �趨ʱ��ĸ�ʽ �������ļ����ȡ�ļ�ʱ���ʽ
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");
		// ��ȡʱ��ֵ
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
		// ��ȡʱ��������
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
	 * �ڱ���ǰ�����4λ�ı��ĳ��� ���ĳ���ֻ�㱨�ĵ��������� ������ǰ���4λ��
	 * 
	 * @param sendMsg
	 *            �������ȵ�ԭ������Ϣ
	 * @return ����4λ���ȵı�����Ϣ
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
	 * ��ȥ����ǰ�����λ����
	 * 
	 * @param revMsg
	 *            ����λ���ȵ�ԭ������Ϣ
	 * 
	 * @return ��ȥ��λ���ȵı�����Ϣ
	 */
	public static String removeStringLength(String revMsg) {
		String revString = revMsg.substring(4, revMsg.length());
		return revString;
	}

	/**
	 * ��Byte���������ת��Base64������ַ���
	 * 
	 * @param byteBuf
	 *            byte ������
	 * @param byteLen
	 *            ����ĳ���
	 * @return Base64������ַ���
	 */
	public static String ConvertByteToBase64String(byte[] byteBuf, int byteLen) {

		byte[] convertBuf = new byte[byteLen];
		System.arraycopy(byteBuf, 0, convertBuf, 0, byteLen);

		BASE64Encoder enc = new BASE64Encoder();
		String convertString = enc.encode(convertBuf);

		return convertString;
	}

	/**
	 * ��Base64������ַ���ת��Byte����
	 * 
	 * @param strDataBuf
	 *            Base64������ַ���
	 * @return byte���͵�����
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
	 * �ж��ַ����Ƿ�����
	 * 
	 * @param strMessage
	 * 
	 * @return ������ true �� false
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
	 * ���Զ�̶˿��Ƿ��
	 * 
	 * @param strIP
	 *            Զ�̷�����IP
	 * @param iPort
	 *            Զ�̷���˿�
	 * @return true �� false �ر�
	 */
	public static boolean checkSocketIsOpen(String strIP, int iPort) {
		try {
			Socket socket = new Socket(strIP, iPort); // ��������
			socket.close(); // �ر�����
			log.debug("IP[" + strIP + "]  Port[" + iPort + "] open"); // Socket��״̬
			return true;
		} catch (IOException e) {
			log.debug("IP[" + strIP + "]  Port[" + iPort + "] close"); // Socket�ر�״̬
			return false;
		}
	}


}
