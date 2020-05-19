/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.util.Hashtable;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.msg.DownloadJournalRequestMsg;
import server.server.socket.bussiness.msg.DownloadJournalResponseMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.tool.FileTools;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:29:26
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalRequestMsgHandler {

	/**
	 * 		��ȡ������ˮ������
	 * 
	 * @param transCode
	 * 						������		
	 * @param sJournalList
	 * 						��ȡ�ĵ�����ˮ��Χ
	 * @return
	 * 			������ˮ������
	 */
	public static String getDownloadJournalRequestMsg(String transCode, String sJournalList) {
		DownloadJournalRequestMsg downloadJournalRequestMsg = new DownloadJournalRequestMsg();
		downloadJournalRequestMsg.setJournalList(sJournalList) ;
		downloadJournalRequestMsg.setTransCode(transCode);
		return downloadJournalRequestMsg.packMsg();
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
	 *   �������ص�����ˮ������
	 *   
	 * @param FileTransferQueue
	 * 								�ļ���������
	 * @param msg
	 * 					����������
	 * @param clientIP
	 * 						���������豸IP
	 * @return
	 */
	public static String handleDownloadJournalRequestMsg(
			Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		try {
			//�ֽ�����������
			DownloadJournalRequestMsg requireDownloadJournalMsg = new DownloadJournalRequestMsg();
			requireDownloadJournalMsg.unpackMsg(msg);
			String sJournalList = requireDownloadJournalMsg.getJournalList() ;  //��ѯ�ĵ�����ˮ�б�
			PubTools.log.debug("������ˮ�б�:"+sJournalList) ;
		
			//�����豸�б���ˮʱ�䷶Χ�����ļ�
			FileTools fileOperator = new FileTools();
			boolean queryfile = fileOperator.CheckTerminalFileList(sJournalList);
			boolean transFlag = false;
			String respCode = "", faultTermList = "";
			if (queryfile) {//�ļ����ҳɹ�,�����ļ������ļ�MD5ֵ
				PubTools.log.info("�����豸�б�������ˮʱ�䷶Χ�����ļ��ɹ�") ;
				transFlag = true;
				//2009-11-13 �޸�Ϊ��Ӧ����ʱ�������ļ�������
				respCode = JournalServerParams.Success;
			} else {  //�ļ�����ʧ�ܣ�����ʧ����Ϣ�б�
				PubTools.log.info("�����豸�б�������ˮʱ�䷶Χ�����ļ�ʧ��") ;
				respCode = JournalServerParams.Error;
				faultTermList = fileOperator.getErrorFileList();
				PubTools.log.info("ʧ����Ϣ�б��� "+faultTermList) ;
			}
			
			//������Ӧ����
			toSendString = getDownloadJournalResponseMsg(JournalTransCodeMsg
					.getDownloadJournalResponseMsg(), transFlag, respCode,faultTermList);
		} catch (Exception ex) {
			PubTools.log.error("handleRequireDownloadJournalMsg Catch Exception:" + ex.toString());
			return "";
		}
		return toSendString;
	}

}
