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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:26:45
 * @Description
 * @version 1.0 Shawn create
 */
public class ClientMessageHandler {
	public static String handleClientRequest(Hashtable FileTransferQueue, String XMLMsgString,
			String clientIP) {
		String toSendString = "";
		int transCode = 0;

		/***********************************************************************
		 * ���жϰ��Ƿ�Ϸ� *
		 **********************************************************************/
		// ���ӱ��ĵĴ��� ������ĳ�������Ѱ�����
		try {
			if (XMLMsgString.length() < 4){
				PubTools.log.error("���ձ��Ĵ���,��ȡ���ı��ĳ���С��4λ" );
				return null;
			}
			else {
				String strNum = XMLMsgString.substring(0, 4);
				if (PubTools.isNumber(strNum)) {
					int packLength = Integer.parseInt(XMLMsgString.substring(0, 4));
					if (packLength + 4 > XMLMsgString.getBytes().length) {//��XMLMsgString.length()��ΪXMLMsgString.getBytes().length,��ֹ�����޷�ͨ����֤
						PubTools.log.error("���ձ��Ĵ���,���ĳ��Ȳ����ڱ���ǰ��λ" );
						return null;
					} 
				} else{
					PubTools.log.error("���ձ��Ĵ���,����ǰ��λ��������" );
					return null;
				}
			}
		} catch (Exception ex) {
			PubTools.log.error("get Message Catch Exception :" + ex.toString());
			return null;
		}
		
		PubTools.log.debug("���յ������ݰ�Ϊ:" + XMLMsgString) ;

		//�ӽ��յ��ĵı����л�ȡ�����룬�����ȡ�����������������ֱ�ӷ���ʧ��
		try {
			transCode = GetTransCodeMsg.getTransCode(XMLMsgString);
		} catch (Exception ex) {
			return GetTransCodeMsg.getErrorResponse(XMLMsgString);
		}
		if (transCode == -1) {
			return null;
		}
		PubTools.log.debug("���յ��ı��ĵĽ�����Ϊ" + transCode);
		
		//���ݻ�ȡ���Ľ�����ѡ��������
		switch (transCode) {
			/*******************************************************************
			 * WEB<-->ATMVH *
			 ******************************************************************/
			// WEB����T+n��n>=0)������ˮ����  90101
			case JournalTransCodeMsg.WebRequestJournalMsg : {
				PubTools.log.debug("��Ӧ������ˮ��ѯ����");
				WebRequestJournalMsgHandler webDownloadJournalMsgHandler = new WebRequestJournalMsgHandler();
				webDownloadJournalMsgHandler.setRequestMsg(XMLMsgString);
				toSendString = webDownloadJournalMsgHandler.handleWebRequestJournalMsg();
			}
				break;

			/*******************************************************************
			 * ATMVH<-->���е�����ˮ�ļ������� *
			 ******************************************************************/
			// ATMVH����T+n��n>=0)������ˮ���� 10101
			/*case JournalTransCodeMsg.DownloadJournalRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ��������");
				toSendString = DownloadJournalRequestMsgHandler.handleDownloadJournalRequestMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;
			// ATMVH���������� 10201
			case JournalTransCodeMsg.DownloadJournalDataRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ�����ļ���������");
				toSendString = DownloadJournalDataRequestMsgHandler.handleDownloadJournalDataRequestMsg(
						FileTransferQueue, XMLMsgString, clientIP);

			}
				break;
			// ATMVH���������� 10201
			case JournalTransCodeMsg.DownloadJournalFileRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ�����ļ���������");
				toSendString = DownloadJournalFileMsgHandler.handleDownloadJournalFileMsg(
						FileTransferQueue, XMLMsgString, clientIP);

			}
				break;
			*//*******************************************************************
			 * ATM<-->���е�����ˮ�ļ������� *
			 ******************************************************************//*
			// ATM������ˮ�ϴ�������20101
			case JournalTransCodeMsg.UploadJournalRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ�ϴ�����");
				toSendString = UploadJournalRequestMsgHandler.handleRequestUploadJournalMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;
			// ATM������ˮ���ݴ��䱨��20201
			case JournalTransCodeMsg.UploadJournalDataTransMsg : {
				PubTools.log.debug("��Ӧ������ˮ�ϴ����ݴ�������");
				toSendString = UploadJournalDataTransMsgHandler.handleUploadJournalDataTransMsg(FileTransferQueue,
						XMLMsgString, clientIP);
			}
				break;

			*//*******************************************************************
			 * ATM������ˮ�ϴ�״̬���� *
			 ******************************************************************//*
			// ATM�ϴ���ʱ������ˮ״̬֪ͨ���� 30201
			case JournalTransCodeMsg.PlanUploadJournalStatusReportMsg : {
				PubTools.log.debug("��ӦATMC״̬֪ͨ");
				toSendString = UploadJournalStatusReportMsgHandler.handleUploadJournalStatusReportMsg(XMLMsgString);
			}
				break;
			
			*//*******************************************************************
			 * ά���̵���ʱ�����ʹ���*
			 ******************************************************************//*
			// ά���̵���ʱ�����ͱ��� 701002
			case JournalTransCodeMsg.ManuArriveImmediatelyRequestMsg : {
				PubTools.log.debug("��Ӧά���̵���ʱ�䴦��");
				ManuInfoJournalReportMsgHandler manuInfoJournalReportMsgHandler = new ManuInfoJournalReportMsgHandler();
				toSendString = manuInfoJournalReportMsgHandler.handleManuInfoJournalReportMsg(XMLMsgString);
			}
				break;
				
			*//*******************************************************************
			 * ATMP2.0ʵʱ״̬�������*
			 ******************************************************************//*
			// ά���̵���ʱ�����ͱ��� 701001
			case JournalTransCodeMsg.AtmpRealTimeLookRequestMsg: {
				PubTools.log.debug("ǿ�Ʒ��ռ�ر��Ĵ���");
				ForceRiskInspecHandler forceRiskInspecHandler = new ForceRiskInspecHandler();
				toSendString = forceRiskInspecHandler.handlerForceRiskInspec(XMLMsgString);
			}
				break;
			*//*******************************************************************
			 *ʵʱ���Ͷ����ʼ�*
			 ******************************************************************//*
			case JournalTransCodeMsg.RealTimeNote:{
				PubTools.log.debug("ʵʱ���Ͷ���");
				RealTimeNoteHandler realTimeNoteHandler = new RealTimeNoteHandler();
				toSendString = realTimeNoteHandler.handlerRealTimeNote(XMLMsgString);
			}
			break;
			*//*************************************************************
			 * �豸����֪ͨ  403008
			 *//*
			case JournalTransCodeMsg.DevFaultNotice:{
				PubTools.log.debug("�豸����֪ͨ  ");
				DevFaultNoticeMsgHand devFaultNoticeMsgHand = new DevFaultNoticeMsgHand();
				toSendString = devFaultNoticeMsgHand.handlerDevFaultNoticeMsg(XMLMsgString);
			}
			break;
			*//********************************************************************
			 * add by tq 20140515  ��֤����ŷ���
			 *******************************************************************//*
			case JournalTransCodeMsg.VerificationCodeSend:{
				PubTools.log.debug("��֤����ŷ���  ");
				VerificationCodeHandler handler = new VerificationCodeHandler();
				toSendString = handler.handlerBuilderMsg(XMLMsgString);
			}
			break;
			
			*//********************************************************************
			 * add by tq 20140515  ��֤����֤  
			 *******************************************************************//*
			case JournalTransCodeMsg.VerificationCodeConfirm:{
				PubTools.log.debug("��֤����֤  ");
				VerificationCodeHandler handler = new VerificationCodeHandler();
				toSendString = handler.handlerVerifyMsg(XMLMsgString);
			}
			break;*/
		}
		PubTools.log.debug("���͵����ݰ�Ϊ:"+toSendString);
		return toSendString;
	}


}
