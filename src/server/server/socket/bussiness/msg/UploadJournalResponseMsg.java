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
 * @date 2014-7-23 上午9:48:32
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalResponseMsg implements MessageStruct {

	private String respCode; // 响应码

	private boolean transFlag;// 处理标志 0成功 1失败

	private String transCode; // 交易码

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
			xmlMsgUtil.addElement("transFlag", "0"); // 响应码
		else
			xmlMsgUtil.addElement("transFlag", "1"); // 响应码
		xmlMsgUtil.addElement("respCode", this.respCode);
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
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
			String flag = xmlMsgUtil.getElement("transFlag");
			if ("0".equals(flag))
				this.transFlag = true;
			else
				this.transFlag = false;
			this.respCode = xmlMsgUtil.getElement("respCode");
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

	public static void main(String[] args) {

	}

	public String getRespondCode() {
		return respCode;
	}

	public void setRespondCode(String respondCode) {
		this.respCode = respondCode;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public boolean getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
	}
}
