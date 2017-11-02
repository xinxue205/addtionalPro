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
 * @date 2014-7-23 ����11:33:29
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalDataTransMsg implements MessageStruct {

	private String transCode; // ������

	private boolean isLastBlock; // �Ƿ����һ������

	private long dataSize; // �ļ����ݿ鳤��

	private String dataBlock; // �ļ����ݿ�

	// ������
	public String getTransCode() {
		return this.transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	// �Ƿ����һ������
	public boolean getIsLastBlock() {
		return this.isLastBlock;
	}

	public void setIsLastBlock(boolean isLastBlock) {
		this.isLastBlock = isLastBlock;
	}

	public long getDataSize() {
		return this.dataSize;
	}

	public void setDataSize(long dataSize) {
		this.dataSize = dataSize;
	}

	// �ļ����ݿ�
	public String getDataBlock() {
		return this.dataBlock;
	}

	public void setDataBlock(String dataBlock) {
		this.dataBlock = dataBlock;
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
		if (isLastBlock)
			xmlMsgUtil.addElement("isLastBlock", "T"); // �����Ƿ����һ������
		else
			xmlMsgUtil.addElement("isLastBlock", "F");
		xmlMsgUtil.addElement("dataSize", String.valueOf(this.dataSize)); // �����ļ�����ƫ����
		xmlMsgUtil.addElement("dataBlock", this.dataBlock); // �����ļ����ݿ�
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public int unpackMsg(String XMLMsgString) {
		// ��ȡ���Ľڵ�
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // ���ַ�����ʽ����ת����XML��ʽ
		this.transCode = xmlMsgUtil.getElement("jydm"); // ��ȡ������
		String flag = xmlMsgUtil.getElement("isLastBlock"); // ��ȡ�Ƿ����һ������
		if ("T".equals(flag))
			this.isLastBlock = true;
		else
			this.isLastBlock = false;
		this.dataSize = Long.valueOf(xmlMsgUtil.getElement("dataSize")).longValue(); // ��ȡ�ļ�����ƫ����
		this.dataBlock = xmlMsgUtil.getElement("dataBlock"); // ��ȡ�ļ����ݿ�
		return 0;
	}
}
