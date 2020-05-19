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
 * @date 2014-7-23 ����11:30:59
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalRequestMsg implements MessageStruct {

	private String journalsList = "" ;   //������ˮ�����б�

	private String transCode;// ������

	//������ˮ�����б�
	public String getJournalList() {
		return this.journalsList;
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
		xmlMsgUtil.addElement("journalsList", this.journalsList); // ���õ�����ˮ�����б�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setJournalList(String journalsList) {
		this.journalsList = journalsList;
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
			this.journalsList = xmlMsgUtil.getElement("journalsList"); // ��ȡ������ˮ�����б�
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
