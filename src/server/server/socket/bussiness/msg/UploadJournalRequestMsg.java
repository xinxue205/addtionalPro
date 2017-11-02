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
 * @date 2014-7-23 ����9:45:10
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalRequestMsg implements MessageStruct {


	private String atmFileMD5; // �ն��ļ�MD5ֵ

	private String atmFileName; // �ն���ˮ�ļ���

	private String fileTime;// ������ˮ���� ��ʽ��yyyyMMdd��

	private boolean isContinueFlag; // ������־

	private String termCode; // �ն˺�

	private String transCode;// ������

	// �ն��ļ�MD5ֵ
	public String getAtmFileMD5() {
		return this.atmFileMD5;
	}

	// �ն���ˮ�ļ���
	public String getAtmFileName() {
		return this.atmFileName;
	}

	// ������ˮ���� ��ʽ��yyyyMMdd��
	public String getFileTime() {
		return this.fileTime;
	}

	// ������־
	public boolean getIsContinueFlag() {
		return this.isContinueFlag;
	}

	// �ն˺�
	public String getTermCode() {
		return this.termCode;
	}

	// ������
	public String getTransCode() {
		return this.transCode;
	}

	/**
	 * ����ӽڵ㵽XML�����У��������ַ���
	 * 
	 * @return ���ĳ���
	 */
	public String packMsg() {
		// ��ӱ��Ľڵ�
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // ���ý�����
		xmlMsgUtil.addElement("termCode", this.termCode); // �����ն˺�
		xmlMsgUtil.addElement("fileTime", this.fileTime); // ���õ�����ˮʱ��
		xmlMsgUtil.addElement("atmFileName", this.atmFileName); // �����ն���ˮ�ļ���
		xmlMsgUtil.addElement("atmFileMD5", this.atmFileMD5); // �����ն��ļ�MD5ֵ
		if (this.isContinueFlag)
			xmlMsgUtil.addElement("isContinueFlag", "T"); // ����������־
		else
			xmlMsgUtil.addElement("isContinueFlag", "F");
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setAtmFileMD5(String atmFileMD5) {
		this.atmFileMD5 = atmFileMD5;
	}

	public void setAtmFileName(String atmFileName) {
		this.atmFileName = atmFileName;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public void setIsContinueFlag(boolean isContinueFlag) {
		this.isContinueFlag = isContinueFlag;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
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
			this.termCode = xmlMsgUtil.getElement("termCode"); // ��ȡ�ն˺�
			this.fileTime = xmlMsgUtil.getElement("fileTime"); // ��ȡ������ˮʱ��
			this.atmFileName = xmlMsgUtil.getElement("atmFileName"); // ��ȡ�ն���ˮ�ļ���
			this.atmFileMD5 = xmlMsgUtil.getElement("atmFileMD5"); // ��ȡ�ն��ļ�MD5ֵ
			String flag = xmlMsgUtil.getElement("isContinueFlag");// ��ȡ������־
			if ("T".equalsIgnoreCase(flag))
				this.isContinueFlag = true;
			else
				this.isContinueFlag = false;
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}


}
