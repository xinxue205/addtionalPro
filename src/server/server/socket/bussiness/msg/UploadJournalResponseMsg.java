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
 * @date 2014-7-23 ����9:48:32
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalResponseMsg implements MessageStruct {

	private String respCode; // ��Ӧ��

	private boolean transFlag;// �����־ 0�ɹ� 1ʧ��

	private String transCode; // ������

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
			xmlMsgUtil.addElement("transFlag", "0"); // ��Ӧ��
		else
			xmlMsgUtil.addElement("transFlag", "1"); // ��Ӧ��
		xmlMsgUtil.addElement("respCode", this.respCode);
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
			String flag = xmlMsgUtil.getElement("transFlag");
			if ("0".equals(flag))
				this.transFlag = true;
			else
				this.transFlag = false;
			this.respCode = xmlMsgUtil.getElement("respCode");
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

	public static void main(String[] args) {

	}

	public String getRespondCode() {
		return respCode;
	}

	public void setRespondCode(String respondCode) {
		this.respCode = respondCode;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public boolean getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
	}
}
