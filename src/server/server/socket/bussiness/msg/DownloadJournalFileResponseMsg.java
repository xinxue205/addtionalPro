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
 * @date 2014-7-23 ����11:27:13
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalFileResponseMsg implements MessageStruct {

	private String errorMsg; // ʧ����Ϣ�б�

	private String respCode;	//��Ӧ��

	private String transCode;// ������

	private boolean transFlag;// �����־
	
	private String fileName = "" ; 
	
	private String fileMD5 = "" ;

	// �������б�
	public String getErrorMsg() {
		return this.errorMsg;
	}

	public String getRespCode() {
		return respCode;
	}

	// �����־
	public boolean getTransFlag() {
		return this.transFlag;
	}
	
	public String getFileName(){
		return this.fileName ;
	}
	
	public String getFileMD5(){
		return this.fileMD5 ;
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
		xmlMsgUtil.addElement("fileName", this.fileName); // �����ļ���
		xmlMsgUtil.addElement("fileMD5", this.fileMD5); // �����ļ�MD5ֵ
		xmlMsgUtil.addElement("errorMsg", this.errorMsg); // ���ô�����Ϣ�б�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
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
			this.fileName = xmlMsgUtil.getElement("fileName");
			this.fileMD5 = xmlMsgUtil.getElement("fileMD5");
			this.errorMsg = xmlMsgUtil.getElement("errorMsg"); // ��ȡ������Ϣ�б�
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
