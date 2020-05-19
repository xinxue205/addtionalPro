/**
 * 
 */
package server.server.socket.bussiness.msg;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:36:33
 * @Description
 * @version 1.0 Shawn create
 */
public class JournalTransCodeMsg {
	
	/**
	 * ATMVH����T+n(n>=0)������ˮ���� 10101
	 */
	public final static int DownloadJournalRequestMsg = 10101;

	/**
	 * ���е�����ˮ�ļ�����������T+n(n>=0)������ˮ��Ӧ���� 10102
	 */
	public final static int DownloadJournalResponseMsg = 10102;
	
	/**
	 * ATMVH���������� 10201
	 */
	public final static int DownloadJournalDataRequestMsg = 10201;
	
	/**
	 * ATMVH���ݴ���Ӧ���� 10202
	 */
	public final static int DownloadJournalDataTransMsg = 10202;
	
	/**
	 * ����������������ص����ļ��������� 
	 */
	public final static int DownloadJournalFileRequestMsg = 10203;
	
	/**
	 *  ����������������ص����ļ�����Ӧ����
	 */
	public final static int DownloadJournalFileResponseMsg = 10204;
	
	/**
	 * ATM�ϴ�������
	 */
	public final static int UploadJournalRequestMsg = 20101;
	
	/**
	 * ���е�����ˮ�ļ�������ATM�ϴ�������Ӧ����
	 */
	public final static int UploadJournalResponseMsg = 20102;
	
	/**
	 * ATM���ݴ��䱨�ģ�20201��
	 */
	public final static int UploadJournalDataTransMsg = 20201;
	
	/**
	 * ATMVH��ʱT+n(n>=0)��ȡ���ģ�30101��
	 */
	public final static int PlanUploadJournalRequestMsg = 30101;

	/**
	 * ATM��ʱ��ȡ������ˮ�ϴ�״̬���ģ�30201��
	 */
	public final static int PlanUploadJournalStatusReportMsg = 30201;

	
	/**
	 * ATMVH ʵʱT+n(n>=0)��ȡ���ģ�40101��
	 */
	public final static int ImmediatelyUploadJournalRequestMsg = 40101;

	/**
	 * ATMʵʱ��ȡ������ˮ�ϴ�״̬���ģ�40201��
	 */
	public final static int ImmediatelyUploadJournalStatusReportMsg = 40201;
	
	/**
	 * WEB����T+n(n>=0)������ˮ���� 90101
	 */
	public final static int WebRequestJournalMsg = 90101;

	/**
	 * ATMVH����T+n(n>=0)������ˮ��Ӧ���� 90102
	 */
	public final static int WebRequestJournalResponseMsg = 90102;

	/**
	 * 20110509 xq ���
	 * ATMP��ATMVʵʱά���̵���ʱ�䱨�����ʹ����� ������ 701002
	 */
	public final static int ManuArriveImmediatelyRequestMsg = 701002;
	
	/**
	 * 20110509 xq ���
	 * ATMP��ATMVʵʱά���̵���ʱ�䱨�����ʹ����� ���ر��� 701002
	 */
	public final static int ManuArriveImmediatelyResponseMsg = 701002;
	
	/**
	 * 20110517 xq ���
	 * ATMP2.0 ʵʱ״̬��ر������ʹ�����  ������ 701001
	 */
	public final static int AtmpRealTimeLookRequestMsg = 701001;
	
	/**
	 * 20110517 xq ���
	 * ATMP2.0 ʵʱ״̬��ر������ʹ�����  ��Ӧ���� 701001
	 */
	public final static int AtmpRealTimeLookResponseMsg = 701001;
	
	/**
	 * 20120612 renshuliang
	 * ʵʱ���Ͷ���
	 */
	public final static int RealTimeNote = 701003;
	
	/**
	 * 20140123 xq
	 * 403008	�豸����֪ͨ
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

	
	
	 // 20110517 xq ����ATMPʵʱ״̬�������ͼ�ά���̵���ʱ������------//
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
