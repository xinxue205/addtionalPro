/**
 * 
 */
package server.server.socket.handler;

import java.net.Socket;

import server.server.socket.JournalServerParams;
import server.server.socket.RequestController;
import server.server.socket.SocketUtil;
import server.server.socket.bussiness.handler.UploadJournalRequestMsgHandler;
import server.server.socket.bussiness.handler.WebRequestJournalMsgHandler;
import server.server.socket.bussiness.msg.DownloadJournalFileResponseMsg;
import server.server.socket.bussiness.msg.DownloadJournalResponseMsg;
import server.server.socket.bussiness.msg.GetTransCodeMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.bussiness.msg.UploadJournalStatusReportMsg;
import server.util.PubTools;


import net.sourceforge.jtds.jdbc.Semaphore;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:18:29
 * @Description
 * @version 1.0 Shawn create
 */
public class ClientMessageErrorHandle extends Thread {
	private Socket socket; //用于处理的SOCKET流
	private Semaphore semp = null ;
	
	public ClientMessageErrorHandle(Socket clientSocket,Semaphore semaOutCount){
		socket = clientSocket;
		this.semp = semaOutCount;
//		start();
	}
	
	public void run(){
		try{
		   sendReturnMsg();
		}catch(Exception e){
			PubTools.log.error("当前线程数量异常:",e);
		}finally{
			RequestController.handleSempOutCount(semp,false);
		}
	}
	
	public  String handleClientRequest(String XMLMsgString) {
		String toSendString = "";
		int transCode = 0;

		/***********************************************************************
		 * 先判断包是否合法 *
		 **********************************************************************/
		// 增加报文的处理 如果报文长错误，则把包丢掉
		try {
			if (XMLMsgString.length() < 4){
				PubTools.log.error("接收报文错误,获取到的报文长度小于4位" );
				return "";
			}
			else {
				String strNum = XMLMsgString.substring(0, 4);
				if (PubTools.isNumber(strNum)) {
					int packLength = Integer.parseInt(XMLMsgString.substring(0, 4));
					if (packLength + 4 > XMLMsgString.length()) {
						PubTools.log.error("接收报文错误,报文长度不等于报文前四位" );
						return "";
					} 
				} else{
					PubTools.log.error("接收报文错误,报文前四位不是数字" );
					return null;
				}
			}
		} catch (Exception ex) {
			PubTools.log.error("get Message Catch Exception :" + ex.toString());
			return null;
		}
		
		PubTools.log.debug("接收到的数据包为:" + XMLMsgString) ;

		//从接收到的的报文中获取交易码，如果获取交易码错误，则不做处理，直接返回失败
		try {
			transCode = GetTransCodeMsg.getTransCode(XMLMsgString);
		} catch (Exception ex) {
			return GetTransCodeMsg.getErrorResponse(XMLMsgString);
		}
		if (transCode == -1) {
			return null;
		}
		PubTools.log.debug("接收到的报文的交易码为" + transCode);
		
		//根据获取到的交易码选择交易类型
		switch (transCode) {
			/*******************************************************************
			 * WEB<-->ATMVH *
			 ******************************************************************/
			// WEB查阅T+n（n>=0)电子流水报文  90101
			case JournalTransCodeMsg.WebRequestJournalMsg : {
				PubTools.log.debug("响应电子流水查询请求");
				WebRequestJournalMsgHandler webDownloadJournalMsgHandler = new WebRequestJournalMsgHandler();
				toSendString = webDownloadJournalMsgHandler.getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(),false,"","");
			}
				break;

			/*******************************************************************
			 * ATMVH<-->分行电子流水文件服务器 *
			 ******************************************************************/
			// ATMVH查阅T+n（n>=0)电子流水报文 10101
			case JournalTransCodeMsg.DownloadJournalRequestMsg : {
				PubTools.log.debug("响应电子流水下载请求");
				toSendString = getDownloadJournalResponseMsg(JournalTransCodeMsg.getDownloadJournalResponseMsg(),
						       false, JournalServerParams.Error, "");
			}
				break;
			// ATMVH数据请求报文 10201
//			case JournalTransCodeMsg.DownloadJournalDataRequestMsg : {
//				PubTools.log.debug("响应电子流水下载文件传输请求");
//				toSendString = DownloadJournalDataRequestMsgHandler.handleDownloadJournalDataRequestMsg(
//						FileTransferQueue, XMLMsgString, clientIP);
//
//			}
//				break;
			// ATMVH数据请求报文 10201
			case JournalTransCodeMsg.DownloadJournalFileRequestMsg : {
				PubTools.log.debug("响应电子流水下载文件传输请求");
				toSendString = getDownloadJournalFileResponseMsg(JournalTransCodeMsg.getDownloadJournalFileResponseMsg(), false, JournalServerParams.Error, "", "", "");

			}
				break;
			/*******************************************************************
			 * ATM<-->分行电子流水文件服务器 *
			 ******************************************************************/
			// ATM电子流水上传请求报文20101
			case JournalTransCodeMsg.UploadJournalRequestMsg : {
				PubTools.log.debug("响应电子流水上传请求");
				toSendString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(),false,  JournalServerParams.Error);
			}
				break;
			// ATM电子流水数据传输报文20201
			case JournalTransCodeMsg.UploadJournalDataTransMsg : {
				PubTools.log.debug("响应电子流水上传数据传输请求");
				toSendString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), false, JournalServerParams.Error);
			}
				break;

			/*******************************************************************
			 * ATM电子流水上传状态报文 *
			 ******************************************************************/
			// ATM上传定时电子流水状态通知报文 30201
			case JournalTransCodeMsg.PlanUploadJournalStatusReportMsg : {
				PubTools.log.debug("响应ATMC状态通知");
				toSendString =  getUploadJournalStatusReportResponseMsg(String.valueOf(transCode),"",  JournalServerParams.Error);
			}
				break;
			
//			/*******************************************************************
//			 * 维护商到场时间上送处理*
//			 ******************************************************************/
//			// 维护商到场时间上送报文 701002
//			case JournalTransCodeMsg.ManuArriveImmediatelyRequestMsg : {
//				PubTools.log.debug("响应维护商到场时间处理");
//				ManuInfoJournalReportMsgHandler manuInfoJournalReportMsgHandler = new ManuInfoJournalReportMsgHandler();
//				toSendString = manuInfoJournalReportMsgHandler.handleManuInfoJournalReportMsg(XMLMsgString);
//			}
//				break;
				
//			/*******************************************************************
//			 * ATMP2.0实时状态监控上送*
//			 ******************************************************************/
//			// 维护商到场时间上送报文 701001
//			case JournalTransCodeMsg.AtmpRealTimeLookRequestMsg: {
//				PubTools.log.debug("强制风险监控报文处理");
//				ForceRiskInspecHandler forceRiskInspecHandler = new ForceRiskInspecHandler();
//				toSendString = forceRiskInspecHandler.handlerForceRiskInspec(XMLMsgString);
//			}
//				break;
//			/*******************************************************************
//			 *实时发送短信邮件*
//			 ******************************************************************/
//			case JournalTransCodeMsg.RealTimeNote:{
//				PubTools.log.debug("实时发送短信");
//				RealTimeNoteHandler realTimeNoteHandler = new RealTimeNoteHandler();
//				toSendString = realTimeNoteHandler.handlerRealTimeNote(XMLMsgString);
//			}
//			break;
		}
		PubTools.log.debug("发送的数据包为:"+toSendString);
		return toSendString;
	}
	
	/**
	 * 		获取下载流水响应报文
	 * 
	 * @param transCode
	 * 						交易码 10102
	 * @param transFlag
	 * 						处理标志  0成功 1失败
	 * @param respCode
	 * 						响应码  当处理标志为0，该域有效
	 * @param faultTermList
	 * 						失败信息列表  ”终端号1$时间1#终端号2$时间2”
	 * @return
	 * 			响应报文
	 */
	protected static String getDownloadJournalResponseMsg(String transCode,
			boolean transFlag, String respCode,String faultTermList) {
		DownloadJournalResponseMsg downloadJournalResponseMsg = new DownloadJournalResponseMsg();
		downloadJournalResponseMsg.setTransCode(transCode);
		downloadJournalResponseMsg.setRespCode(respCode);
		//如果处理标志位为true 则设置临时文件名及临时文件MD5值
		if (transFlag) {
			downloadJournalResponseMsg.setTransFlag(true);
		} else {  //否则设置失败信息列表
			downloadJournalResponseMsg.setFaultTermList(faultTermList);
			downloadJournalResponseMsg.setTransFlag(false);
		}
		return downloadJournalResponseMsg.packMsg();
	} 
	
	
	/**
	 * 		获取下载流水响应报文
	 * 
	 * @param transCode
	 * 						交易码 10102
	 * @param transFlag
	 * 						处理标志  0成功 1失败
	 * @param respCode
	 * 						响应码  当处理标志为0，该域有效
	 * @param faultTermList
	 * 						失败信息列表  ”终端号1$时间1#终端号2$时间2”
	 * @return
	 * 			响应报文
	 */
	protected static String getDownloadJournalFileResponseMsg(String transCode,
			boolean transFlag, String respCode,String fileName,String fileMD5,String errorMsg) {
		DownloadJournalFileResponseMsg downloadJournalFileResponseMsg = new DownloadJournalFileResponseMsg();
		downloadJournalFileResponseMsg.setTransCode(transCode);
		downloadJournalFileResponseMsg.setRespCode(respCode);
		//如果处理标志位为true 则设置为成功
		if (transFlag) {
			downloadJournalFileResponseMsg.setTransFlag(true);
			downloadJournalFileResponseMsg.setFileName(fileName) ;
			downloadJournalFileResponseMsg.setFileMD5(fileMD5) ;
		} else {  //否则设置失败信息列表
			downloadJournalFileResponseMsg.setErrorMsg(errorMsg);
			downloadJournalFileResponseMsg.setTransFlag(false);
		}
		return downloadJournalFileResponseMsg.packMsg();
	}
	
	/**
	 * ATMC状态通知返回报文
	 * @param transCode
	 * @param termCode
	 * @param respCode
	 * @return
	 */
	private static String getUploadJournalStatusReportResponseMsg(String transCode, String termCode, String respCode) {
		UploadJournalStatusReportMsg uploadJournalStatusReportMsg = new UploadJournalStatusReportMsg();
		uploadJournalStatusReportMsg.setTransCode(transCode);
		uploadJournalStatusReportMsg.setTermCode(termCode);
		uploadJournalStatusReportMsg.setRespCode(respCode);
		return uploadJournalStatusReportMsg.packMsg();
	}
	
	
	/**
	 * 20121212 xq add 如果超过最大并发数的话，对于连接上来的请求直接将返回码连接上来的请求 将处理该内容的方式修改为多线程处理
	 * @param clientSocket
	 * @return
	 */
	public boolean sendReturnMsg(){
	    SocketUtil su = new SocketUtil();
	    su.setSocket(socket);
		if (!su.createConnection()) {
			PubTools.log.warn("socket连接失败");
			return false;
		}
		try{
//			String sReturnStr = "0117<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jydm>30201</jydm><sbbh>440600300169</sbbh><respcode>OK</respcode></root>";//失败则将返回码直接返回回去
			String sRevMsg = su.getMessage();
			String sReturnStr = handleClientRequest(sRevMsg);
			PubTools.log.debug("返回内容为:"+sReturnStr);
			su.sendMessage(sReturnStr);
		}catch(Exception e){
			PubTools.log.error("发送返回报文失败:",e);
		}finally{
			su.closeConnection();
		}
		return true;
	}


}
