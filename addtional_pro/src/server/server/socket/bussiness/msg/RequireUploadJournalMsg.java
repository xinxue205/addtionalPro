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
 * @date 2014-7-23 ����12:54:19
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalMsg implements MessageStruct {

	private String fileTime; // ������ˮʱ��

	private boolean isEnforce; // �Ƿ����־

	private String respCode; // ��Ӧ��

	private String termCode; // �ն˺�

	private String transCode; // ������

	// ��Ӧ��
	public String getRespCode() {
		return this.respCode;
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
		xmlMsgUtil.addElement("sbbh", termCode); // �豸���
		xmlMsgUtil.addElement("filetime", fileTime); // ������ˮʱ��
		if (this.isEnforce)
			xmlMsgUtil.addElement("isenforce", "Y"); // �Ƿ����־
		else
			xmlMsgUtil.addElement("isenforce", "N"); // �Ƿ����־
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	// ������ˮʱ��
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	// �Ƿ����־
	public void setIsEnforce(boolean isEnforce) {
		this.isEnforce = isEnforce;
	}

	// �ն˺�
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
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
			this.termCode = xmlMsgUtil.getElement("sbbh") ; //��ȡ�豸���
			this.respCode = xmlMsgUtil.getElement("respcode"); // ��ȡ������	
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
