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
 * @date 2014-7-23 下午12:43:36
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalMsg implements MessageStruct {

	private String journalList = "" ;   //电子流水查阅列表

	private String transCode;// 交易码
	
	private String sGetMethod = "";//20120828 xq add 提取方式

	//电子流水查阅列表
	public String getJournalList() {
		return this.journalList;
	}

	// 交易码
	public String getTransCode() {
		return this.transCode;
	}
	
	//20120828 xq add 提取方式
	public String getSGetMethod() {
		return sGetMethod;
	}

	//20120828 xq add 提取方式
	public void setSGetMethod(String getMethod) {
		sGetMethod = getMethod;
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
		xmlMsgUtil.addElement("journalsList", this.journalList); // 设置电子流水查阅列表
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setJournalList(String journalList) {
		this.journalList = journalList;
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
			this.journalList = xmlMsgUtil.getElement("journalsList"); // 获取电子流水查阅列表
			this.sGetMethod = xmlMsgUtil.getElement("tqfs");//20120828 xq add 提取方式
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
