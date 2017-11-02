/**
 * 
 */
package server.server.socket.handler;

import java.util.Hashtable;

import server.server.socket.bussiness.handler.DownloadJournalDataRequestMsgHandler;
import server.server.socket.bussiness.handler.DownloadJournalFileMsgHandler;
import server.server.socket.bussiness.handler.DownloadJournalRequestMsgHandler;
import server.server.socket.bussiness.handler.UploadJournalRequestMsgHandler;
import server.server.socket.bussiness.handler.WebRequestJournalMsgHandler;
import server.server.socket.bussiness.msg.GetTransCodeMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:26:45
 * @Description
 * @version 1.0 Shawn create
 */
public class ClientMessageHandler {
	public static String handleClientRequest(Hashtable FileTransferQueue, String XMLMsgString,
			String clientIP) {
		String toSendString = "";
		int transCode = 0;

		/***********************************************************************
		 * 先判断包是否合法 *
		 **********************************************************************/
		// 增加报文的处理 如果报文长错误，则把包丢掉
		try {
			if (XMLMsgString.length() < 4){
				PubTools.log.error("接收报文错误,获取到的报文长度小于4位" );
				return null;
			}
			else {
				String strNum = XMLMsgString.substring(0, 4);
				if (PubTools.isNumber(strNum)) {
					int packLength = Integer.parseInt(XMLMsgString.substring(0, 4));
					if (packLength + 4 > XMLMsgString.getBytes().length) {//由XMLMsgString.length()改为XMLMsgString.getBytes().length,防止中文无法通过验证
						PubTools.log.error("接收报文错误,报文长度不等于报文前四位" );
						return null;
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
				webDownloadJournalMsgHandler.setRequestMsg(XMLMsgString);
				toSendString = webDownloadJournalMsgHandler.handleWebRequestJournalMsg();
			}
				break;

			/*******************************************************************
			 * ATMVH<-->分行电子流水文件服务器 *
			 ******************************************************************/
			// ATMVH查阅T+n（n>=0)电子流水报文 10101
			/*case JournalTransCodeMsg.DownloadJournalRequestMsg : {
				PubTools.log.debug("响应电子流水下载请求");
				toSendString = DownloadJournalRequestMsgHandler.handleDownloadJournalRequestMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;
			// ATMVH数据请求报文 10201
			case JournalTransCodeMsg.DownloadJournalDataRequestMsg : {
				PubTools.log.debug("响应电子流水下载文件传输请求");
				toSendString = DownloadJournalDataRequestMsgHandler.handleDownloadJournalDataRequestMsg(
						FileTransferQueue, XMLMsgString, clientIP);

			}
				break;
			// ATMVH数据请求报文 10201
			case JournalTransCodeMsg.DownloadJournalFileRequestMsg : {
				PubTools.log.debug("响应电子流水下载文件传输请求");
				toSendString = DownloadJournalFileMsgHandler.handleDownloadJournalFileMsg(
						FileTransferQueue, XMLMsgString, clientIP);

			}
				break;
			*//*******************************************************************
			 * ATM<-->分行电子流水文件服务器 *
			 ******************************************************************//*
			// ATM电子流水上传请求报文20101
			case JournalTransCodeMsg.UploadJournalRequestMsg : {
				PubTools.log.debug("响应电子流水上传请求");
				toSendString = UploadJournalRequestMsgHandler.handleRequestUploadJournalMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;
			// ATM电子流水数据传输报文20201
			case JournalTransCodeMsg.UploadJournalDataTransMsg : {
				PubTools.log.debug("响应电子流水上传数据传输请求");
				toSendString = UploadJournalDataTransMsgHandler.handleUploadJournalDataTransMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;

			*//*******************************************************************
			 * ATM电子流水上传状态报文 *
			 ******************************************************************//*
			// ATM上传定时电子流水状态通知报文 30201
			case JournalTransCodeMsg.PlanUploadJournalStatusReportMsg : {
				PubTools.log.debug("响应ATMC状态通知");
				toSendString = UploadJournalStatusReportMsgHandler.handleUploadJournalStatusReportMsg(XMLMsgString);
			}
				break;
			
			*//*******************************************************************
			 * 维护商到场时间上送处理*
			 ******************************************************************//*
			// 维护商到场时间上送报文 701002
			case JournalTransCodeMsg.ManuArriveImmediatelyRequestMsg : {
				PubTools.log.debug("响应维护商到场时间处理");
				ManuInfoJournalReportMsgHandler manuInfoJournalReportMsgHandler = new ManuInfoJournalReportMsgHandler();
				toSendString = manuInfoJournalReportMsgHandler.handleManuInfoJournalReportMsg(XMLMsgString);
			}
				break;
				
			*//*******************************************************************
			 * ATMP2.0实时状态监控上送*
			 ******************************************************************//*
			// 维护商到场时间上送报文 701001
			case JournalTransCodeMsg.AtmpRealTimeLookRequestMsg: {
				PubTools.log.debug("强制风险监控报文处理");
				ForceRiskInspecHandler forceRiskInspecHandler = new ForceRiskInspecHandler();
				toSendString = forceRiskInspecHandler.handlerForceRiskInspec(XMLMsgString);
			}
				break;
			*//*******************************************************************
			 *实时发送短信邮件*
			 ******************************************************************//*
			case JournalTransCodeMsg.RealTimeNote:{
				PubTools.log.debug("实时发送短信");
				RealTimeNoteHandler realTimeNoteHandler = new RealTimeNoteHandler();
				toSendString = realTimeNoteHandler.handlerRealTimeNote(XMLMsgString);
			}
			break;
			*//*************************************************************
			 * 设备故障通知  403008
			 *//*
			case JournalTransCodeMsg.DevFaultNotice:{
				PubTools.log.debug("设备故障通知  ");
				DevFaultNoticeMsgHand devFaultNoticeMsgHand = new DevFaultNoticeMsgHand();
				toSendString = devFaultNoticeMsgHand.handlerDevFaultNoticeMsg(XMLMsgString);
			}
			break;
			*//********************************************************************
			 * add by tq 20140515  验证码短信发送
			 *******************************************************************//*
			case JournalTransCodeMsg.VerificationCodeSend:{
				PubTools.log.debug("验证码短信发送  ");
				VerificationCodeHandler handler = new VerificationCodeHandler();
				toSendString = handler.handlerBuilderMsg(XMLMsgString);
			}
			break;
			
			*//********************************************************************
			 * add by tq 20140515  验证码验证  
			 *******************************************************************//*
			case JournalTransCodeMsg.VerificationCodeConfirm:{
				PubTools.log.debug("验证码验证  ");
				VerificationCodeHandler handler = new VerificationCodeHandler();
				toSendString = handler.handlerVerifyMsg(XMLMsgString);
			}
			break;*/
		}
		PubTools.log.debug("发送的数据包为:"+toSendString);
		return toSendString;
	}


}
