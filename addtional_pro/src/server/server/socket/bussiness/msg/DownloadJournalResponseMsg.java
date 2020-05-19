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
 * @date 2014-7-23 上午11:25:54
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalResponseMsg implements MessageStruct {

	private String faultTermList; // 失败信息列表

	private String respCode;	//响应码

	private String transCode;// 交易码

	private boolean transFlag;// 处理标志

	// 错误处理列表
	public String getFaultTermList() {
		return this.faultTermList;
	}

	public String getRespCode() {
		return respCode;
	}

	// 处理标志
	public boolean getTransFlag() {
		return this.transFlag;
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
		if (this.transFlag)
			xmlMsgUtil.addElement("transFlag", "0"); // 设置处理标志
		else
			xmlMsgUtil.addElement("transFlag", "1"); // 设置处理标志
		xmlMsgUtil.addElement("respCode", this.respCode); // 设置错误信息列表
		xmlMsgUtil.addElement("faultTermList", this.faultTermList); // 设置错误信息列表
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}

	public void setFaultTermList(String faultTermList) {
		this.faultTermList = faultTermList;
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
			String flag = xmlMsgUtil.getElement("transFlag"); // 获取处理标志
			if ("0".equals(flag))
				this.transFlag = true;
			else
				this.transFlag = false;
			this.respCode = xmlMsgUtil.getElement("respCode");
			this.faultTermList = xmlMsgUtil.getElement("faultTermList"); // 获取错误信息列表
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
