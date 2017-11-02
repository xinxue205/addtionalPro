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
 * @date 2014-7-23 ����11:25:54
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalResponseMsg implements MessageStruct {

	private String faultTermList; // ʧ����Ϣ�б�

	private String respCode;	//��Ӧ��

	private String transCode;// ������

	private boolean transFlag;// �����־

	// �������б�
	public String getFaultTermList() {
		return this.faultTermList;
	}

	public String getRespCode() {
		return respCode;
	}

	// �����־
	public boolean getTransFlag() {
		return this.transFlag;
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
		if (this.transFlag)
			xmlMsgUtil.addElement("transFlag", "0"); // ���ô����־
		else
			xmlMsgUtil.addElement("transFlag", "1"); // ���ô����־
		xmlMsgUtil.addElement("respCode", this.respCode); // ���ô�����Ϣ�б�
		xmlMsgUtil.addElement("faultTermList", this.faultTermList); // ���ô�����Ϣ�б�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setFaultTermList(String faultTermList) {
		this.faultTermList = faultTermList;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
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
			String flag = xmlMsgUtil.getElement("transFlag"); // ��ȡ�����־
			if ("0".equals(flag))
				this.transFlag = true;
			else
				this.transFlag = false;
			this.respCode = xmlMsgUtil.getElement("respCode");
			this.faultTermList = xmlMsgUtil.getElement("faultTermList"); // ��ȡ������Ϣ�б�
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
