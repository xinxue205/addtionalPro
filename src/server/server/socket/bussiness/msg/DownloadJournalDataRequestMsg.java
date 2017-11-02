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
 * @date 2014-7-23 上午11:34:35
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalDataRequestMsg implements MessageStruct {

	private String tempFileName; // 临时文件名

	private long tempFileOffset; // 临时文件偏移量

	private String transCode;

	// 临时文件名
	public String getTempFileName() {
		return this.tempFileName;
	}

	// 临时文件偏移量
	public long getTempFileOffset() {
		return this.tempFileOffset;
	}

	public String getTransCode() {
		return transCode;
	}

	public String packMsg() {
		// 添加报文节点
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // 设置交易码
		xmlMsgUtil.addElement("tempFileName", this.tempFileName); // 设置临时文件名
		xmlMsgUtil.addElement("tempFileOffset", String.valueOf(this.tempFileOffset)); // 设置临时文件偏移量
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public void setTempFileOffset(long tempFileOffset) {
		this.tempFileOffset = tempFileOffset;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public int unpackMsg(String XMLMsgString) {
		try {
			// 获取报文节点
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // 将字符串格式报文转化成XML格式
			this.transCode = xmlMsgUtil.getElement("jydm"); // 获取交易码
			this.tempFileName = xmlMsgUtil.getElement("tempFileName"); // 获取临时文件名
			this.tempFileOffset = Long.valueOf(xmlMsgUtil.getElement("tempFileOffset")).longValue(); // 获取临时文件偏移量
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.toString());
			return -1;
		}
		return 0;
	}
}
