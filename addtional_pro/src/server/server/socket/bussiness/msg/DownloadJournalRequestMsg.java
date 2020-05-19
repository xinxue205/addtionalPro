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
 * @date 2014-7-23 上午11:30:59
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalRequestMsg implements MessageStruct {

	private String journalsList = "" ;   //电子流水查阅列表

	private String transCode;// 交易码

	//电子流水查阅列表
	public String getJournalList() {
		return this.journalsList;
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
		xmlMsgUtil.addElement("journalsList", this.journalsList); // 设置电子流水查阅列表
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setJournalList(String journalsList) {
		this.journalsList = journalsList;
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
			this.journalsList = xmlMsgUtil.getElement("journalsList"); // 获取电子流水查阅列表
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
