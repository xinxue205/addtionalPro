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
 * @date 2014-7-23 ����11:28:05
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalStatusReportMsg implements MessageStruct {

	private String atmFileName;// ATM���ļ���

	private String fileTime; // ������ˮʱ��

	private String termCode; // �ն˺�

	private String transCode; // ������

	private boolean transResult; // �ϴ����

	private String transTime; // �ϴ�ʱ��

	private String respCode; // ��Ӧ��

	// ATM���ļ���
	public String getAtmFileName() {
		return this.atmFileName;
	}

	// ������ˮʱ��
	public String getFileTime() {
		return this.fileTime;
	}

	// �ն˺�
	public String getTermCode() {
		return this.termCode;
	}

	// ������
	public String getTransCode() {
		return this.transCode;
	}

	// �ϴ����
	public boolean getTransResult() {
		return this.transResult;
	}

	// �ϴ�ʱ��
	public String getTransTime() {
		return this.transTime;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
	public void setFileTime(String sFileTime){
		this.fileTime = sFileTime ;
	}
	
	public void setFileName(String sFileName){
		this.atmFileName = sFileName ;
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
		xmlMsgUtil.addElement("sbbh", this.termCode); // ���ý�����
		// xmlMsgUtil.addElement("xtgzh", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("jyrq", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("jysj", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("xym", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("jyzh", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("qzlsh", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zxlsh", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zxxym", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zxxyxx", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("bwbb", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zjbz", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zjxx", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("zwptxx", ""); // ��Ӧ��
		// xmlMsgUtil.addElement("ywptxx", ""); // ��Ӧ��
		xmlMsgUtil.addElement("respcode", this.respCode); // ��Ӧ��
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	/**
	 * ����XML�ַ������
	 * 
	 * @param XMLMsgString
	 *            �ַ�����ʽ��XML����
	 */
	public int unpackMsg(String XMLMsgString) {
		try {
			// ��ȡ���Ľڵ�
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // ���ַ�����ʽ����ת����XML��ʽ
			this.transCode = xmlMsgUtil.getElement("jydm"); // ��ȡ������
			this.termCode = xmlMsgUtil.getElement("sbbh"); // �ն˺�
			this.fileTime = xmlMsgUtil.getElement("filetime"); // ������ˮʱ��
			this.atmFileName = xmlMsgUtil.getElement("atmfilename"); // ATM���ļ���
			String flag = xmlMsgUtil.getElement("transresult"); // �ϴ����
			if ("O".equalsIgnoreCase(flag))
				this.transResult = true;
			else
				this.transResult = false;
			this.transTime = xmlMsgUtil.getElement("transtime"); // �ϴ�ʱ��
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
	
	public String packStatusMsg(){
		// ��ӱ��Ľڵ�
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // ���ý�����
		xmlMsgUtil.addElement("sbbh", this.termCode); // �����豸��
		xmlMsgUtil.addElement("filetime", this.fileTime); // �����ļ�ʱ��
		xmlMsgUtil.addElement("atmfilename", this.atmFileName); // �����ļ���
		xmlMsgUtil.addElement("transresult", this.respCode); // ��Ӧ��
		xmlMsgUtil.addElement("transtime",PubTools.getFullNowTime()); // �ϴ�ʱ��
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}
	
	public int unpackStatusMsg(String XMLMsgString){
		try {
			// ��ȡ���Ľڵ�
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // ���ַ�����ʽ����ת����XML��ʽ
			this.transCode = xmlMsgUtil.getElement("jydm"); // ��ȡ������
			this.termCode = xmlMsgUtil.getElement("sbbh"); // �ն˺�
			this.respCode = xmlMsgUtil.getElement("respcode"); // ������
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
