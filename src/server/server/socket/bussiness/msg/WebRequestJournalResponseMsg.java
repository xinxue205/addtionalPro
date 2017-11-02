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
 * @date 2014-7-23 下午12:46:33
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalResponseMsg implements MessageStruct {

	private String faultMessage; // 失败信息列表

	private String tempFileName;// 临时文件名

	private String transCode;// 交易码

	private boolean transFlag;// 处理标志
	
	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}

	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setTransFlag(boolean transFlag) {
		this.transFlag = transFlag;
	}
	
	public String getTransCode(){
		return this.transCode ;
	}
	
	public boolean getTransFlag(){
		return this.transFlag ;
	}
	
	public String getTempFileName(){
		return this.tempFileName ;
	}
	
	public String getFaultMessage(){
		return this.faultMessage ;
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
		xmlMsgUtil.addElement("tempFileName", this.tempFileName); // 设置临时文件名
		xmlMsgUtil.addElement("faultMessage", this.faultMessage); // 设置错误信息列表
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
			if ( xmlMsgUtil.getElement("transFlag").equals("0")) //处理标志
				this.transFlag = true ;
			else 
				this.transFlag = false ;
			this.tempFileName = xmlMsgUtil.getElement("tempFileName"); // 获取临时文件名
			this.faultMessage = xmlMsgUtil.getElement("faultMessage"); // 获取失败错误信息
		} catch (Exception ex) {
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
}
