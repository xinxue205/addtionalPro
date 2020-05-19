/**
 * 
 */
package server.server.socket.bussiness.handler;

import server.server.socket.bussiness.JournalLog;
import server.server.socket.bussiness.msg.UploadJournalStatusReportMsg;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:56:24
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalStatusReportMsgHandler {

	private String termCode;

	private String fileTime;

	private String atmFileName;

	private String transTime;

	private boolean transResult;

	public String getAtmFileName() {
		return atmFileName;
	}

	public String getFileTime() {
		return fileTime;
	}

	public String getTermCode() {
		return termCode;
	}

	public boolean getTransResult() {
		return transResult;
	}

	public String getTransTime() {
		return transTime;
	}

	private static String getUploadJournalStatusReportResponseMsg(String transCode, String termCode, String respCode) {
		UploadJournalStatusReportMsg uploadJournalStatusReportMsg = new UploadJournalStatusReportMsg();
		uploadJournalStatusReportMsg.setTransCode(transCode);
		uploadJournalStatusReportMsg.setTermCode(termCode);
		uploadJournalStatusReportMsg.setRespCode(respCode);
		return uploadJournalStatusReportMsg.packMsg();
	}

	/**
	 * ��״̬����֪ͨ����
	 * 
	 * @param sTransCode
	 *            ������
	 * @param sTermCode
	 *            �豸��
	 * @param sFileTime
	 *            �ļ�ʱ��
	 * @param sFileName
	 *            �ļ���
	 * @param sFlag
	 *            ������
	 * @return ״̬֪ͨ����
	 */
	public static String getUploadJournalStatusReportMsg(String sTransCode, String sTermCode, String sFileTime, String sFileName,
			String sFlag) {
		UploadJournalStatusReportMsg uploadJournalStatusReportMsg = new UploadJournalStatusReportMsg();
		uploadJournalStatusReportMsg.setTransCode(sTransCode);
		uploadJournalStatusReportMsg.setTermCode(sTermCode);
		uploadJournalStatusReportMsg.setFileTime(sFileTime);
		uploadJournalStatusReportMsg.setFileName(sFileName);
		uploadJournalStatusReportMsg.setRespCode(sFlag);
		return uploadJournalStatusReportMsg.packStatusMsg();
	}

	public static String handleUploadJournalStatusReportMsg(String msg) {
		String toSendString = null;
		try {
			UploadJournalStatusReportMsg uploadJournalStatusReportMsg = new UploadJournalStatusReportMsg();
			uploadJournalStatusReportMsg.unpackMsg(msg);
			boolean isLogUploadStatus = new JournalLog().logDevUploadStatus(uploadJournalStatusReportMsg.getTermCode(),
					uploadJournalStatusReportMsg.getFileTime(), uploadJournalStatusReportMsg.getAtmFileName(), uploadJournalStatusReportMsg
							.getTransResult(), uploadJournalStatusReportMsg.getTransTime());
			String respCode = "ERROR";
			if (isLogUploadStatus) {
				respCode = "OK";
			}
			toSendString = getUploadJournalStatusReportResponseMsg(uploadJournalStatusReportMsg.getTransCode(),
					uploadJournalStatusReportMsg.getTermCode(), respCode);
		} catch (Exception ex) {
			PubTools.log.error("handleUploadJournalStatusMsg Catch Exception:" + ex.getMessage());
			return "";
		}
		return toSendString;
	}
}
