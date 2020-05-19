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
 * @date 2014-7-23 ����12:43:36
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalMsg implements MessageStruct {

	private String journalList = "" ;   //������ˮ�����б�

	private String transCode;// ������
	
	private String sGetMethod = "";//20120828 xq add ��ȡ��ʽ

	//������ˮ�����б�
	public String getJournalList() {
		return this.journalList;
	}

	// ������
	public String getTransCode() {
		return this.transCode;
	}
	
	//20120828 xq add ��ȡ��ʽ
	public String getSGetMethod() {
		return sGetMethod;
	}

	//20120828 xq add ��ȡ��ʽ
	public void setSGetMethod(String getMethod) {
		sGetMethod = getMethod;
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
		xmlMsgUtil.addElement("journalsList", this.journalList); // ���õ�����ˮ�����б�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setJournalList(String journalList) {
		this.journalList = journalList;
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
			this.journalList = xmlMsgUtil.getElement("journalsList"); // ��ȡ������ˮ�����б�
			this.sGetMethod = xmlMsgUtil.getElement("tqfs");//20120828 xq add ��ȡ��ʽ
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
	
	public static void main(String[] args) {
		WebRequestJournalMsg requestJournalMsg = new WebRequestJournalMsg();
		requestJournalMsg.unpackMsg("0182<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jydm>90101</jydm><journalsList>440600300167@20120820</journalsList><tqfs>1</tqfs><fs1>20120823</fs1><fs2>234811</fs2><fs3>03</fs3></root>");
		System.out.println(requestJournalMsg.journalList+"\t"+requestJournalMsg.sGetMethod);
	}

	
}
