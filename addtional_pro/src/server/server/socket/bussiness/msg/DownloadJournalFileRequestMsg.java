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
 * @date 2014-7-23 ����11:36:44
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalFileRequestMsg implements MessageStruct {

	private String termCode = "" ;   //������ˮ�����б�
	
	private String fileTime = "" ;  //�ļ�ʱ��

	private String transCode;// ������

	//������ˮ�����б�
	public String getTermCode() {
		return this.termCode;
	}
	
	//�ļ�ʱ��
	public String getFileTime(){
		return this.fileTime ;
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
		xmlMsgUtil.addElement("termCode", this.termCode); // ���õ�����ˮ�����б�
		xmlMsgUtil.addElement("fileTime", this.fileTime); // ���õ�����ˮ�����б�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
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
			this.termCode = xmlMsgUtil.getElement("termCode"); // ��ȡ������ˮ�����б�
			this.fileTime = xmlMsgUtil.getElement("fileTime"); // ��ȡ������ˮ�����б�
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
