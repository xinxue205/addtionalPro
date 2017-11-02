/**
 * 
 */
package server.server.socket.bussiness.thread;

import java.util.Vector;

import server.server.socket.JournalServerParams;
import server.server.socket.SocketUtil;
import server.server.socket.bussiness.RequireUploadJournalDevTaskStruct;
import server.server.socket.bussiness.RequireUploadJournalfileTimeTaskStruct;
import server.server.socket.bussiness.handler.RequireUploadJournalMsgHandler;
import server.server.socket.bussiness.handler.UploadJournalStatusReportMsgHandler;
import server.server.socket.bussiness.msg.GetTransCodeMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.bussiness.msg.RequireUploadJournalMsg;
import server.server.socket.bussiness.msg.UploadJournalStatusReportMsg;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:50:46
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyRequireUploadJournalThread extends Thread {

	// ATMC端口号
	private int atmcJournalServerPort = JournalServerParams.AtmcJournalServerPort;

	private int iRemainTasks = 0;

	private boolean tradeSuccess = false;

	private RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct;

	public void setRequireUploadJournalDevTaskStruct(RequireUploadJournalDevTaskStruct requireuploadJournalDevTaskStruct) {
		this.requireUploadJournalDevTaskStruct = requireuploadJournalDevTaskStruct;
	}

	public RequireUploadJournalDevTaskStruct getRequireUploadJournalDevTaskStruct() {
		return this.requireUploadJournalDevTaskStruct;
	}

	// 获取剩余的任务数
	public int getRemainTasksCount() {
		return this.iRemainTasks;
	}

	// 获取交易结果
	public boolean getTradeSuccess() {
		return this.tradeSuccess;
	}

	public ImmediatelyRequireUploadJournalThread() {

	}

	public void run() {
		try {
			requireUploadJournal();
		} catch (Exception e) {
			PubTools.log.error("Require UpLoad Journal Thread Catch Exception:" + e.toString());
		}
		tradeSuccess = true;
	}

	public boolean requireUploadJournal()  throws Exception {
		SocketUtil socketUtil = null;
		try {
			iRemainTasks = requireUploadJournalDevTaskStruct.getSize();
			Vector temprequireUploadJournalDevTaskStruct = requireUploadJournalDevTaskStruct.getRequireUploadJournalDevTaskStruct();
			for (int i = 0; i < requireUploadJournalDevTaskStruct.getSize(); i++) {
				RequireUploadJournalfileTimeTaskStruct requireUploadJournalfileTimeTaskStruct = (RequireUploadJournalfileTimeTaskStruct) temprequireUploadJournalDevTaskStruct
						.get(i);
				if (requireUploadJournalfileTimeTaskStruct == null)
					return false;
				String termCode = requireUploadJournalfileTimeTaskStruct.getDevCode().trim();
				String devIP = requireUploadJournalfileTimeTaskStruct.getDevIP().trim();
				PubTools.log.info("开始实时提取ATM 设备号[" + termCode + "] 设备IP[" + devIP + "] 时间["
						+ requireUploadJournalfileTimeTaskStruct.getFileTime() + "] 流水");
				socketUtil = new SocketUtil(devIP, this.atmcJournalServerPort,10000); //20121114 xq update 超时时间由默认时间 设为10秒超时

				if (socketUtil.createConnection()) {
					// ATMVH向ATMC发送的上传电子流水请求报文
					String requestMsg = RequireUploadJournalMsgHandler.getRequireUploadJournalMsg(requireUploadJournalfileTimeTaskStruct
							.getTransCode(), requireUploadJournalfileTimeTaskStruct.getDevCode(), requireUploadJournalfileTimeTaskStruct
							.getFileTime(), requireUploadJournalfileTimeTaskStruct.getIsEnforce());
					// 发送请求报文
					socketUtil.sendMessage(requestMsg);
					PubTools.log.debug("发送的数据报文为[" + requestMsg + "]");
					PubTools.log.info("发送数据报文到ATM:[" + termCode + "]成功");
					boolean isContinue = true;
					while (isContinue) {
						// 获取响应报文
						String responseMsg = socketUtil.getMessage();
						PubTools.log.info("接收设备:[" + termCode + "]报文成功");
						PubTools.log.debug("接收到的数据报文为:[" + responseMsg + "]");
						//判断接收到的响应报文是否为空或者长度是否小于4
						if (responseMsg.equals("") || responseMsg.length() < 4) {
							PubTools.log.error("接收到设备:[" + termCode + "]响应报文长度出错");
							return false;
						}
						int transCode = GetTransCodeMsg.getTransCode(responseMsg);
						if (transCode == -1) {
							PubTools.log.error("接收到设备:[" + termCode + "]响应报应交易码出错");
							return false;
						}
						switch (transCode) {
							case JournalTransCodeMsg.ImmediatelyUploadJournalRequestMsg : {
								PubTools.log.info("接收到设备:["+termCode+"]对上传文件的响应报文");
								RequireUploadJournalMsg requireUploadJournalMsg = new RequireUploadJournalMsg();
								requireUploadJournalMsg.unpackMsg(responseMsg);
								String respCode = requireUploadJournalMsg.getRespCode();
								if (JournalServerParams.Error.equals(respCode)) {
									return false;
								}
								break;
							}
							case JournalTransCodeMsg.ImmediatelyUploadJournalStatusReportMsg : {
								PubTools.log.info("接收到设备:["+termCode+"]对上传文件的上传状态报文");
								String toSendString = UploadJournalStatusReportMsgHandler.handleUploadJournalStatusReportMsg(responseMsg);
								PubTools.log.info("实时提取ATM 设备号[" + requireUploadJournalfileTimeTaskStruct.getDevCode() + "] 流水完成");
								UploadJournalStatusReportMsg uploadJournalStatusMsg = new UploadJournalStatusReportMsg();
								uploadJournalStatusMsg.unpackMsg(responseMsg);
								if (uploadJournalStatusMsg.getTransResult()) {
									iRemainTasks--;
								}
								// 发送上传状态报文的响应报文
								socketUtil.sendMessage(toSendString);
								isContinue = false;
								break;
							}
						}
					}
				} else {
					PubTools.log.info("设备[" + termCode + "]网络通讯异常 连接失败");
					return false;
				}
			}
		} finally {
			if (socketUtil != null)
				socketUtil.closeConnection();
		}
		return true;
	}
}
