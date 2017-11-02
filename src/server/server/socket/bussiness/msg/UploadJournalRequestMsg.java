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
 * @date 2014-7-23 上午9:45:10
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalRequestMsg implements MessageStruct {


	private String atmFileMD5; // 终端文件MD5值

	private String atmFileName; // 终端流水文件名

	private String fileTime;// 电子流水日期 格式”yyyyMMdd”

	private boolean isContinueFlag; // 续传标志

	private String termCode; // 终端号

	private String transCode;// 交易码

	// 终端文件MD5值
	public String getAtmFileMD5() {
		return this.atmFileMD5;
	}

	// 终端流水文件名
	public String getAtmFileName() {
		return this.atmFileName;
	}

	// 电子流水日期 格式”yyyyMMdd”
	public String getFileTime() {
		return this.fileTime;
	}

	// 续传标志
	public boolean getIsContinueFlag() {
		return this.isContinueFlag;
	}

	// 终端号
	public String getTermCode() {
		return this.termCode;
	}

	// 交易码
	public String getTransCode() {
		return this.transCode;
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
		xmlMsgUtil.addElement("termCode", this.termCode); // 设置终端号
		xmlMsgUtil.addElement("fileTime", this.fileTime); // 设置电子流水时间
		xmlMsgUtil.addElement("atmFileName", this.atmFileName); // 设置终端流水文件名
		xmlMsgUtil.addElement("atmFileMD5", this.atmFileMD5); // 设置终端文件MD5值
		if (this.isContinueFlag)
			xmlMsgUtil.addElement("isContinueFlag", "T"); // 设置续传标志
		else
			xmlMsgUtil.addElement("isContinueFlag", "F");
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setAtmFileMD5(String atmFileMD5) {
		this.atmFileMD5 = atmFileMD5;
	}

	public void setAtmFileName(String atmFileName) {
		this.atmFileName = atmFileName;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public void setIsContinueFlag(boolean isContinueFlag) {
		this.isContinueFlag = isContinueFlag;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
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
			this.termCode = xmlMsgUtil.getElement("termCode"); // 获取终端号
			this.fileTime = xmlMsgUtil.getElement("fileTime"); // 获取电子流水时间
			this.atmFileName = xmlMsgUtil.getElement("atmFileName"); // 获取终端流水文件名
			this.atmFileMD5 = xmlMsgUtil.getElement("atmFileMD5"); // 获取终端文件MD5值
			String flag = xmlMsgUtil.getElement("isContinueFlag");// 获取续传标志
			if ("T".equalsIgnoreCase(flag))
				this.isContinueFlag = true;
			else
				this.isContinueFlag = false;
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}


}
