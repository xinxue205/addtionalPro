/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.util.Hashtable;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.msg.DownloadJournalFileRequestMsg;
import server.server.socket.bussiness.msg.DownloadJournalFileResponseMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.tool.FileOperationStruct;
import server.server.socket.tool.FileTools;
import server.server.socket.tool.FileTransferStruct;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:35:29
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalFileMsgHandler {

	/**
	 * 		��ȡ������ˮ�ļ�������
	 * 
	 * @param transCode
	 * 						������		
	 * @param sTermCode
	 * 						�豸���
	 * @param sFileTime
	 * 						�ļ�ʱ��
	 * @return
	 * 			������ˮ������
	 */
	public static String getDownloadJournalFileRequestMsg(String transCode, String sTermCode,String sFileTime) {
		DownloadJournalFileRequestMsg downloadJournalFileRequestMsg = new DownloadJournalFileRequestMsg();
		downloadJournalFileRequestMsg.setTermCode(sTermCode) ;
		downloadJournalFileRequestMsg.setFileTime(sFileTime) ;
		downloadJournalFileRequestMsg.setTransCode(transCode);
		return downloadJournalFileRequestMsg.packMsg();
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
	public static String handleDownloadJournalFileMsg(Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		try {
			//�ֽ�����������
			DownloadJournalFileRequestMsg downloadJournalFileRequestMsg = new DownloadJournalFileRequestMsg();
			downloadJournalFileRequestMsg.unpackMsg(msg);
			String sTermCode = downloadJournalFileRequestMsg.getTermCode() ;  //��ѯ���豸��
			String sFileTime = downloadJournalFileRequestMsg.getFileTime();  //��ѯ���ļ�ʱ��
			PubTools.log.debug("�豸��:"+sTermCode) ;
			PubTools.log.debug("�ļ�ʱ��:"+sFileTime) ;
		
			//�����豸�б���ˮʱ�䷶Χ�����ļ�
			FileTools fileOperator = new FileTools();
			boolean queryfile = fileOperator.CheckTerminalFile(sTermCode,sFileTime);
			boolean transFlag = false;
			String tempFileName = "", tempFileMD5 = "";
			String respCode = "", faultTermList = "";
			
			if (queryfile) {//�ļ����ҳɹ�,�����ļ������ļ�MD5ֵ
				PubTools.log.info("�����豸�ż�������ˮʱ������ļ��ɹ�") ;
				transFlag = true;
				tempFileName = fileOperator.getTempFileName();
				tempFileMD5 = fileOperator.getFileMD5(tempFileName);
				PubTools.log.info("FileName is " + tempFileName + "FileMD5 is "+tempFileMD5) ;
				FileTransferStruct fileTransferStruct = new FileTransferStruct();
				FileOperationStruct fileOperationStruct = new FileOperationStruct();
				fileOperationStruct.setFileName(tempFileName);
				fileOperationStruct.setFileMD5(tempFileMD5);
				
				//2009-11-13 �޸�Ϊ��Ӧ����ʱ�������ļ�������
				respCode = JournalServerParams.Success;
				fileTransferStruct.addFileOperationStruct(fileOperationStruct);
				FileTransferQueue.put(clientIP, fileTransferStruct);
			} else {  //�ļ�����ʧ�ܣ�����ʧ����Ϣ�б�
				PubTools.log.info("�����豸�б�������ˮʱ�䷶Χ�����ļ�ʧ��") ;
				respCode = JournalServerParams.Error;
				faultTermList = fileOperator.getErrorFileList();
				PubTools.log.info("ʧ����Ϣ�б��� "+faultTermList) ;
			}
			
			//������Ӧ����
			toSendString = getDownloadJournalFileResponseMsg(JournalTransCodeMsg
					.getDownloadJournalFileResponseMsg(), transFlag, respCode, tempFileName,
					tempFileMD5, faultTermList);
		} catch (Exception ex) {
			PubTools.log.error("handleRequireDownloadJournalMsg Catch Exception:" + ex.toString());
			return "";
		}
		return toSendString;
	}

}
