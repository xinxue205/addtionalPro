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
 * @date 2014-7-23 上午11:36:44
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalFileRequestMsg implements MessageStruct {

	private String termCode = "" ;   //电子流水查阅列表
	
	private String fileTime = "" ;  //文件时间

	private String transCode;// 交易码

	//电子流水查阅列表
	public String getTermCode() {
		return this.termCode;
	}
	
	//文件时间
	public String getFileTime(){
		return this.fileTime ;
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
		xmlMsgUtil.addElement("termCode", this.termCode); // 设置电子流水查阅列表
		xmlMsgUtil.addElement("fileTime", this.fileTime); // 设置电子流水查阅列表
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
	 * 根据XML字符串解包
	 * 
	 * @param XMLMsgString
	 *            字符串格式的XML报文
	 */
	public int unpackMsg(String XMLMsgString) {
		try {
			// 获取报文节点
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // 将字符串格式报文转化成XML格式
			this.transCode = xmlMsgUtil.getElement("jydm"); // 获取交易码
			this.termCode = xmlMsgUtil.getElement("termCode"); // 获取电子流水查阅列表
			this.fileTime = xmlMsgUtil.getElement("fileTime"); // 获取电子流水查阅列表
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
