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
 * @date 2014-7-23 下午12:54:19
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalMsg implements MessageStruct {

	private String fileTime; // 电子流水时间

	private boolean isEnforce; // 是否补提标志

	private String respCode; // 响应码

	private String termCode; // 终端号

	private String transCode; // 交易码

	// 响应码
	public String getRespCode() {
		return this.respCode;
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
		xmlMsgUtil.addElement("sbbh", termCode); // 设备编号
		xmlMsgUtil.addElement("filetime", fileTime); // 电子流水时间
		if (this.isEnforce)
			xmlMsgUtil.addElement("isenforce", "Y"); // 是否补提标志
		else
			xmlMsgUtil.addElement("isenforce", "N"); // 是否补提标志
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	// 电子流水时间
	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	// 是否补提标志
	public void setIsEnforce(boolean isEnforce) {
		this.isEnforce = isEnforce;
	}

	// 终端号
	public void setTermCode(String termCode) {
		this.termCode = termCode;
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
			this.termCode = xmlMsgUtil.getElement("sbbh") ; //获取设备编号
			this.respCode = xmlMsgUtil.getElement("respcode"); // 获取交易码	
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
