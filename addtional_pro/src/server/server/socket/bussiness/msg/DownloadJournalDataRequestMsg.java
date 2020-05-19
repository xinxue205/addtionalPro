/**
 * 
 */
package server.server.socket.bussiness.msg;

import server.server.socket.inter.MessageStruct;
import server.server.socket.tool.XMLMsgUtil;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:34:35
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalDataRequestMsg implements MessageStruct {

	private String tempFileName; // ��ʱ�ļ���

	private long tempFileOffset; // ��ʱ�ļ�ƫ����

	private String transCode;

	// ��ʱ�ļ���
	public String getTempFileName() {
		return this.tempFileName;
	}

	// ��ʱ�ļ�ƫ����
	public long getTempFileOffset() {
		return this.tempFileOffset;
	}

	public String getTransCode() {
		return transCode;
	}

	public String packMsg() {
		// ��ӱ��Ľڵ�
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // ���ý�����
		xmlMsgUtil.addElement("tempFileName", this.tempFileName); // ������ʱ�ļ���
		xmlMsgUtil.addElement("tempFileOffset", String.valueOf(this.tempFileOffset)); // ������ʱ�ļ�ƫ����
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public void setTempFileOffset(long tempFileOffset) {
		this.tempFileOffset = tempFileOffset;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public int unpackMsg(String XMLMsgString) {
		try {
			// ��ȡ���Ľڵ�
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // ���ַ�����ʽ����ת����XML��ʽ
			this.transCode = xmlMsgUtil.getElement("jydm"); // ��ȡ������
			this.tempFileName = xmlMsgUtil.getElement("tempFileName"); // ��ȡ��ʱ�ļ���
			this.tempFileOffset = Long.valueOf(xmlMsgUtil.getElement("tempFileOffset")).longValue(); // ��ȡ��ʱ�ļ�ƫ����
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.toString());
			return -1;
		}
		return 0;
	}
}
