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
 * @date 2014-7-23 上午11:28:05
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalStatusReportMsg implements MessageStruct {

	private String atmFileName;// ATM端文件名

	private String fileTime; // 电子流水时间

	private String termCode; // 终端号

	private String transCode; // 交易码

	private boolean transResult; // 上传结果

	private String transTime; // 上传时间

	private String respCode; // 响应码

	// ATM端文件名
	public String getAtmFileName() {
		return this.atmFileName;
	}

	// 电子流水时间
	public String getFileTime() {
		return this.fileTime;
	}

	// 终端号
	public String getTermCode() {
		return this.termCode;
	}

	// 交易码
	public String getTransCode() {
		return this.transCode;
	}

	// 上传结果
	public boolean getTransResult() {
		return this.transResult;
	}

	// 上传时间
	public String getTransTime() {
		return this.transTime;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
	public void setFileTime(String sFileTime){
		this.fileTime = sFileTime ;
	}
	
	public void setFileName(String sFileName){
		this.atmFileName = sFileName ;
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
		xmlMsgUtil.addElement("sbbh", this.termCode); // 设置交易码
		// xmlMsgUtil.addElement("xtgzh", ""); // 响应码
		// xmlMsgUtil.addElement("jyrq", ""); // 响应码
		// xmlMsgUtil.addElement("jysj", ""); // 响应码
		// xmlMsgUtil.addElement("xym", ""); // 响应码
		// xmlMsgUtil.addElement("jyzh", ""); // 响应码
		// xmlMsgUtil.addElement("qzlsh", ""); // 响应码
		// xmlMsgUtil.addElement("zxlsh", ""); // 响应码
		// xmlMsgUtil.addElement("zxxym", ""); // 响应码
		// xmlMsgUtil.addElement("zxxyxx", ""); // 响应码
		// xmlMsgUtil.addElement("bwbb", ""); // 响应码
		// xmlMsgUtil.addElement("zjbz", ""); // 响应码
		// xmlMsgUtil.addElement("zjxx", ""); // 响应码
		// xmlMsgUtil.addElement("zwptxx", ""); // 响应码
		// xmlMsgUtil.addElement("ywptxx", ""); // 响应码
		xmlMsgUtil.addElement("respcode", this.respCode); // 响应码
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
			this.termCode = xmlMsgUtil.getElement("sbbh"); // 终端号
			this.fileTime = xmlMsgUtil.getElement("filetime"); // 电子流水时间
			this.atmFileName = xmlMsgUtil.getElement("atmfilename"); // ATM端文件名
			String flag = xmlMsgUtil.getElement("transresult"); // 上传结果
			if ("O".equalsIgnoreCase(flag))
				this.transResult = true;
			else
				this.transResult = false;
			this.transTime = xmlMsgUtil.getElement("transtime"); // 上传时间
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}
	
	public String packStatusMsg(){
		// 添加报文节点
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.addElement("jydm", this.transCode); // 设置交易码
		xmlMsgUtil.addElement("sbbh", this.termCode); // 设置设备号
		xmlMsgUtil.addElement("filetime", this.fileTime); // 设置文件时间
		xmlMsgUtil.addElement("atmfilename", this.atmFileName); // 设置文件名
		xmlMsgUtil.addElement("transresult", this.respCode); // 响应码
		xmlMsgUtil.addElement("transtime",PubTools.getFullNowTime()); // 上传时间
		return PubTools.addStrngLength(xmlMsgUtil.getXMLString());
	}
	
	public int unpackStatusMsg(String XMLMsgString){
		try {
			// 获取报文节点
			XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
			xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // 将字符串格式报文转化成XML格式
			this.transCode = xmlMsgUtil.getElement("jydm"); // 获取交易码
			this.termCode = xmlMsgUtil.getElement("sbbh"); // 终端号
			this.respCode = xmlMsgUtil.getElement("respcode"); // 处理结果
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("unpackMsg Catch Exception:" + ex.getMessage());
			return -1;
		}
		return 0;
	}

}
