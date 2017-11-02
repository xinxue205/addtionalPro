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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:18:29
 * @Description
 * @version 1.0 Shawn create
 */
public class ClientMessageErrorHandle extends Thread {
	private Socket socket; //���ڴ����SOCKET��
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
			PubTools.log.error("��ǰ�߳������쳣:",e);
		}finally{
			RequestController.handleSempOutCount(semp,false);
		}
	}
	
	public  String handleClientRequest(String XMLMsgString) {
		String toSendString = "";
		int transCode = 0;

		/***********************************************************************
		 * ���жϰ��Ƿ�Ϸ� *
		 **********************************************************************/
		// ���ӱ��ĵĴ��� ������ĳ�������Ѱ�����
		try {
			if (XMLMsgString.length() < 4){
				PubTools.log.error("���ձ��Ĵ���,��ȡ���ı��ĳ���С��4λ" );
				return "";
			}
			else {
				String strNum = XMLMsgString.substring(0, 4);
				if (PubTools.isNumber(strNum)) {
					int packLength = Integer.parseInt(XMLMsgString.substring(0, 4));
					if (packLength + 4 > XMLMsgString.length()) {
						PubTools.log.error("���ձ��Ĵ���,���ĳ��Ȳ����ڱ���ǰ��λ" );
						return "";
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
				toSendString = webDownloadJournalMsgHandler.getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(),false,"","");
			}
				break;

			/*******************************************************************
			 * ATMVH<-->���е�����ˮ�ļ������� *
			 ******************************************************************/
			// ATMVH����T+n��n>=0)������ˮ���� 10101
			case JournalTransCodeMsg.DownloadJournalRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ��������");
				toSendString = getDownloadJournalResponseMsg(JournalTransCodeMsg.getDownloadJournalResponseMsg(),
						       false, JournalServerParams.Error, "");
			}
				break;
			// ATMVH���������� 10201
//			case JournalTransCodeMsg.DownloadJournalDataRequestMsg : {
//				PubTools.log.debug("��Ӧ������ˮ�����ļ���������");
//				toSendString = DownloadJournalDataRequestMsgHandler.handleDownloadJournalDataRequestMsg(
//						FileTransferQueue, XMLMsgString, clientIP);
//
//			}
//				break;
			// ATMVH���������� 10201
			case JournalTransCodeMsg.DownloadJournalFileRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ�����ļ���������");
				toSendString = getDownloadJournalFileResponseMsg(JournalTransCodeMsg.getDownloadJournalFileResponseMsg(), false, JournalServerParams.Error, "", "", "");

			}
				break;
			/*******************************************************************
			 * ATM<-->���е�����ˮ�ļ������� *
			 ******************************************************************/
			// ATM������ˮ�ϴ�������20101
			case JournalTransCodeMsg.UploadJournalRequestMsg : {
				PubTools.log.debug("��Ӧ������ˮ�ϴ�����");
				toSendString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(),false,  JournalServerParams.Error);
			}
				break;
			// ATM������ˮ���ݴ��䱨��20201
			case JournalTransCodeMsg.UploadJournalDataTransMsg : {
				PubTools.log.debug("��Ӧ������ˮ�ϴ����ݴ�������");
				toSendString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), false, JournalServerParams.Error);
			}
				break;

			/*******************************************************************
			 * ATM������ˮ�ϴ�״̬���� *
			 ******************************************************************/
			// ATM�ϴ���ʱ������ˮ״̬֪ͨ���� 30201
			case JournalTransCodeMsg.PlanUploadJournalStatusReportMsg : {
				PubTools.log.debug("��ӦATMC״̬֪ͨ");
				toSendString =  getUploadJournalStatusReportResponseMsg(String.valueOf(transCode),"",  JournalServerParams.Error);
			}
				break;
			
//			/*******************************************************************
//			 * ά���̵���ʱ�����ʹ���*
//			 ******************************************************************/
//			// ά���̵���ʱ�����ͱ��� 701002
//			case JournalTransCodeMsg.ManuArriveImmediatelyRequestMsg : {
//				PubTools.log.debug("��Ӧά���̵���ʱ�䴦��");
//				ManuInfoJournalReportMsgHandler manuInfoJournalReportMsgHandler = new ManuInfoJournalReportMsgHandler();
//				toSendString = manuInfoJournalReportMsgHandler.handleManuInfoJournalReportMsg(XMLMsgString);
//			}
//				break;
				
//			/*******************************************************************
//			 * ATMP2.0ʵʱ״̬�������*
//			 ******************************************************************/
//			// ά���̵���ʱ�����ͱ��� 701001
//			case JournalTransCodeMsg.AtmpRealTimeLookRequestMsg: {
//				PubTools.log.debug("ǿ�Ʒ��ռ�ر��Ĵ���");
//				ForceRiskInspecHandler forceRiskInspecHandler = new ForceRiskInspecHandler();
//				toSendString = forceRiskInspecHandler.handlerForceRiskInspec(XMLMsgString);
//			}
//				break;
//			/*******************************************************************
//			 *ʵʱ���Ͷ����ʼ�*
//			 ******************************************************************/
//			case JournalTransCodeMsg.RealTimeNote:{
//				PubTools.log.debug("ʵʱ���Ͷ���");
//				RealTimeNoteHandler realTimeNoteHandler = new RealTimeNoteHandler();
//				toSendString = realTimeNoteHandler.handlerRealTimeNote(XMLMsgString);
//			}
//			break;
		}
		PubTools.log.debug("���͵����ݰ�Ϊ:"+toSendString);
		return toSendString;
	}
	
	/**
	 * 		��ȡ������ˮ��Ӧ����
	 * 
	 * @param transCode
	 * 						������ 10102
	 * @param transFlag
	 * 						�����־  0�ɹ� 1ʧ��
	 * @param respCode
	 * 						��Ӧ��  �������־Ϊ0��������Ч
	 * @param faultTermList
	 * 						ʧ����Ϣ�б�  ���ն˺�1$ʱ��1#�ն˺�2$ʱ��2��
	 * @return
	 * 			��Ӧ����
	 */
	protected static String getDownloadJournalResponseMsg(String transCode,
			boolean transFlag, String respCode,String faultTermList) {
		DownloadJournalResponseMsg downloadJournalResponseMsg = new DownloadJournalResponseMsg();
		downloadJournalResponseMsg.setTransCode(transCode);
		downloadJournalResponseMsg.setRespCode(respCode);
		//��������־λΪtrue ��������ʱ�ļ�������ʱ�ļ�MD5ֵ
		if (transFlag) {
			downloadJournalResponseMsg.setTransFlag(true);
		} else {  //��������ʧ����Ϣ�б�
			downloadJournalResponseMsg.setFaultTermList(faultTermList);
			downloadJournalResponseMsg.setTransFlag(false);
		}
		return downloadJournalResponseMsg.packMsg();
	} 
	
	
	/**
	 * 		��ȡ������ˮ��Ӧ����
	 * 
	 * @param transCode
	 * 						������ 10102
	 * @param transFlag
	 * 						�����־  0�ɹ� 1ʧ��
	 * @param respCode
	 * 						��Ӧ��  �������־Ϊ0��������Ч
	 * @param faultTermList
	 * 						ʧ����Ϣ�б�  ���ն˺�1$ʱ��1#�ն˺�2$ʱ��2��
	 * @return
	 * 			��Ӧ����
	 */
	protected static String getDownloadJournalFileResponseMsg(String transCode,
			boolean transFlag, String respCode,String fileName,String fileMD5,String errorMsg) {
		DownloadJournalFileResponseMsg downloadJournalFileResponseMsg = new DownloadJournalFileResponseMsg();
		downloadJournalFileResponseMsg.setTransCode(transCode);
		downloadJournalFileResponseMsg.setRespCode(respCode);
		//��������־λΪtrue ������Ϊ�ɹ�
		if (transFlag) {
			downloadJournalFileResponseMsg.setTransFlag(true);
			downloadJournalFileResponseMsg.setFileName(fileName) ;
			downloadJournalFileResponseMsg.setFileMD5(fileMD5) ;
		} else {  //��������ʧ����Ϣ�б�
			downloadJournalFileResponseMsg.setErrorMsg(errorMsg);
			downloadJournalFileResponseMsg.setTransFlag(false);
		}
		return downloadJournalFileResponseMsg.packMsg();
	}
	
	/**
	 * ATMC״̬֪ͨ���ر���
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
	 * 20121212 xq add ���������󲢷����Ļ���������������������ֱ�ӽ��������������������� ����������ݵķ�ʽ�޸�Ϊ���̴߳���
	 * @param clientSocket
	 * @return
	 */
	public boolean sendReturnMsg(){
	    SocketUtil su = new SocketUtil();
	    su.setSocket(socket);
		if (!su.createConnection()) {
			PubTools.log.warn("socket����ʧ��");
			return false;
		}
		try{
//			String sReturnStr = "0117<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jydm>30201</jydm><sbbh>440600300169</sbbh><respcode>OK</respcode></root>";//ʧ���򽫷�����ֱ�ӷ��ػ�ȥ
			String sRevMsg = su.getMessage();
			String sReturnStr = handleClientRequest(sRevMsg);
			PubTools.log.debug("��������Ϊ:"+sReturnStr);
			su.sendMessage(sReturnStr);
		}catch(Exception e){
			PubTools.log.error("���ͷ��ر���ʧ��:",e);
		}finally{
			su.closeConnection();
		}
		return true;
	}


}
