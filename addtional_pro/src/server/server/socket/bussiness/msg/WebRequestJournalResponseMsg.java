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
 * @date 2014-7-23 ����12:46:33
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalResponseMsg implements MessageStruct {

	private String faultMessage; // ʧ����Ϣ�б�

	private String tempFileName;// ��ʱ�ļ���

	private String transCode;// ������

	private boolean transFlag;// �����־
	
	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
	}
	
	public String getTransCode(){
		return this.transCode ;
	}
	
	public boolean getTransFlag(){
		return this.transFlag ;
	}
	
	public String getTempFileName(){
		return this.tempFileName ;
	}
	
	public String getFaultMessage(){
		return this.faultMessage ;
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
		xmlMsgUtil.addElement("tempFileName", this.tempFileName); // ������ʱ�ļ���
		xmlMsgUtil.addElement("faultMessage", this.faultMessage); // ���ô�����Ϣ�б�
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
			if ( xmlMsgUtil.getElement("transFlag").equals("0")) //�����־
				this.transFlag = true ;
			else 
				this.transFlag = false ;
			this.tempFileName = xmlMsgUtil.getElement("tempFileName"); // ��ȡ��ʱ�ļ���
			this.faultMessage = xmlMsgUtil.getElement("faultMessage"); // ��ȡʧ�ܴ�����Ϣ
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
