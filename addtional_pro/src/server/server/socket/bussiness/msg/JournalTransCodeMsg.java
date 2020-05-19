/**
 * 
 */
package server.server.socket.bussiness.msg;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:36:33
 * @Description
 * @version 1.0 Shawn create
 */
public class JournalTransCodeMsg {
	
	/**
	 * ATMVH查阅T+n(n>=0)电子流水报文 10101
	 */
	public final static int DownloadJournalRequestMsg = 10101;

	/**
	 * 分行电子流水文件服务器查阅T+n(n>=0)电子流水响应报文 10102
	 */
	public final static int DownloadJournalResponseMsg = 10102;
	
	/**
	 * ATMVH数据请求报文 10201
	 */
	public final static int DownloadJournalDataRequestMsg = 10201;
	
	/**
	 * ATMVH数据传输应报文 10202
	 */
	public final static int DownloadJournalDataTransMsg = 10202;
	
	/**
	 * 总行向分行请求下载单个文件的请求报文 
	 */
	public final static int DownloadJournalFileRequestMsg = 10203;
	
	/**
	 *  总行向分行请求下载单个文件的响应报文
	 */
	public final static int DownloadJournalFileResponseMsg = 10204;
	
	/**
	 * ATM上传请求报文
	 */
	public final static int UploadJournalRequestMsg = 20101;
	
	/**
	 * 分行电子流水文件服务器ATM上传请求响应报文
	 */
	public final static int UploadJournalResponseMsg = 20102;
	
	/**
	 * ATM数据传输报文（20201）
	 */
	public final static int UploadJournalDataTransMsg = 20201;
	
	/**
	 * ATMVH定时T+n(n>=0)提取报文（30101）
	 */
	public final static int PlanUploadJournalRequestMsg = 30101;

	/**
	 * ATM定时提取电子流水上传状态报文（30201）
	 */
	public final static int PlanUploadJournalStatusReportMsg = 30201;

	
	/**
	 * ATMVH 实时T+n(n>=0)提取报文（40101）
	 */
	public final static int ImmediatelyUploadJournalRequestMsg = 40101;

	/**
	 * ATM实时提取电子流水上传状态报文（40201）
	 */
	public final static int ImmediatelyUploadJournalStatusReportMsg = 40201;
	
	/**
	 * WEB查阅T+n(n>=0)电子流水报文 90101
	 */
	public final static int WebRequestJournalMsg = 90101;

	/**
	 * ATMVH查阅T+n(n>=0)电子流水响应报文 90102
	 */
	public final static int WebRequestJournalResponseMsg = 90102;

	/**
	 * 20110509 xq 添加
	 * ATMP、ATMV实时维护商到场时间报文上送处理报文 请求报文 701002
	 */
	public final static int ManuArriveImmediatelyRequestMsg = 701002;
	
	/**
	 * 20110509 xq 添加
	 * ATMP、ATMV实时维护商到场时间报文上送处理报文 返回报文 701002
	 */
	public final static int ManuArriveImmediatelyResponseMsg = 701002;
	
	/**
	 * 20110517 xq 添加
	 * ATMP2.0 实时状态监控报文上送处理报文  请求报文 701001
	 */
	public final static int AtmpRealTimeLookRequestMsg = 701001;
	
	/**
	 * 20110517 xq 添加
	 * ATMP2.0 实时状态监控报文上送处理报文  响应报文 701001
	 */
	public final static int AtmpRealTimeLookResponseMsg = 701001;
	
	/**
	 * 20120612 renshuliang
	 * 实时发送短信
	 */
	public final static int RealTimeNote = 701003;
	
	/**
	 * 20140123 xq
	 * 403008	设备故障通知
	 */
	public final static int DevFaultNotice = 403008;
	
	public final static int VerificationCodeSend = 701004;
	public final static int VerificationCodeConfirm = 701005;
	
	
	static {
	}

	public static String getImmediatelyUploadJournalRequestMsg() {
		return String.valueOf(ImmediatelyUploadJournalRequestMsg);
	}

	public static String getImmediatelyUploadJournalStatusReportMsg() {
		return String.valueOf(ImmediatelyUploadJournalStatusReportMsg);
	}

	public static String getPlanUploadJournalRequestMsg() {
		return String.valueOf(PlanUploadJournalRequestMsg);
	}

	public static String getPlanUploadJournalStatusReportMsg() {
		return String.valueOf(PlanUploadJournalStatusReportMsg);
	}

	public static String getUploadJournalRequestMsg() {
		return String.valueOf(UploadJournalRequestMsg);
	}

	public static String getUploadJournalResponseMsg() {
		return String.valueOf(UploadJournalResponseMsg);
	}
	
	public static String getUploadJournalDataTransMsg() {
		return String.valueOf(UploadJournalDataTransMsg);
	}

	public static String getDownloadJournalRequestMsg() {
		return String.valueOf(DownloadJournalRequestMsg);
	}

	public static String getDownloadJournalResponseMsg() {
		return String.valueOf(DownloadJournalResponseMsg);
	}
	
	public static String getDownloadJournalDataRequestMsg() {
		return String.valueOf(DownloadJournalDataRequestMsg);
	}

	public static String getDownloadJournalDataTransMsg() {
		return String.valueOf(DownloadJournalDataTransMsg);
	}
	
	public static String getDownloadJournalFileRequestMsg() {
		return String.valueOf(DownloadJournalFileRequestMsg);
	}

	public static String getDownloadJournalFileResponseMsg() {
		return String.valueOf(DownloadJournalFileResponseMsg);
	}

	public static String getWebRequestJournalMsg() {
		return String.valueOf(WebRequestJournalMsg);
	}

	public static String getWebRequestJournalResponseMsg() {
		return String.valueOf(WebRequestJournalResponseMsg);
	}

	
	
	 // 20110517 xq 新增ATMP实时状态报文上送即维护商到场时间上送------//
	public static String getManuArriveImmediatelyRequestMsg() {
		return String.valueOf(ManuArriveImmediatelyRequestMsg);
	}

	public static String getManuArriveImmediatelyResponseMsg() {
		return String.valueOf(ManuArriveImmediatelyResponseMsg);
	}

	public static String getAtmpRealTimeLookRequestMsg() {
		return String.valueOf(AtmpRealTimeLookRequestMsg);
	}

	public static String getAtmpRealTimeLookResponseMsg() {
		return String.valueOf(AtmpRealTimeLookResponseMsg);
	}
	
	public static String getRealTimeNote() {
		return String.valueOf(RealTimeNote);
	}
	
	//20140122 xq add
	public static String getDevFaultNotice() {
		return String.valueOf(DevFaultNotice);
	}
	
	//20140515 tq add
	public static String getVerificationCodeSend() {
		return String.valueOf(VerificationCodeSend);
	}
	
	public static String getVerificationCodeConfirm() {
		return String.valueOf(VerificationCodeConfirm);
	}
	//----------------------------end-------------------------//
	
}
