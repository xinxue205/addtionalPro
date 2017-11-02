/**
 * 
 */
package server.server.socket.bussiness.msg;

import server.server.socket.inter.MessageStruct;
import server.server.socket.tool.XMLMsgUtil;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:33:29
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalDataTransMsg implements MessageStruct {

	private String transCode; // 交易码

	private boolean isLastBlock; // 是否最后一块数据

	private long dataSize; // 文件数据块长度

	private String dataBlock; // 文件数据块

	// 交易码
	public String getTransCode() {
		return this.transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	// 是否最后一块数据
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

	// 文件数据块
	public String getDataBlock() {
		return this.dataBlock;
	}

	public void setDataBlock(String dataBlock) {
		this.dataBlock = dataBlock;
	}

	/**
	 * 添加子节点到XML报文中，并生成字符串
	 * 
	 * @return 报文长度
	 */
	public String packMsg() {
		// 添加报文节点
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // 设置交易码
		if (isLastBlock)
			xmlMsgUtil.addElement("isLastBlock", "T"); // 设置是否最后一块数据
		else
			xmlMsgUtil.addElement("isLastBlock", "F");
		xmlMsgUtil.addElement("dataSize", String.valueOf(this.dataSize)); // 设置文件传输偏移量
		xmlMsgUtil.addElement("dataBlock", this.dataBlock); // 设置文件数据块
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public int unpackMsg(String XMLMsgString) {
		// 获取报文节点
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // 将字符串格式报文转化成XML格式
		this.transCode = xmlMsgUtil.getElement("jydm"); // 获取交易码
		String flag = xmlMsgUtil.getElement("isLastBlock"); // 获取是否最后一块数据
		if ("T".equals(flag))
			this.isLastBlock = true;
		else
			this.isLastBlock = false;
		this.dataSize = Long.valueOf(xmlMsgUtil.getElement("dataSize")).longValue(); // 获取文件传输偏移量
		this.dataBlock = xmlMsgUtil.getElement("dataBlock"); // 获取文件数据块
		return 0;
	}
}
