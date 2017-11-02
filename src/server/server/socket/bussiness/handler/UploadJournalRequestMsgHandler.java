/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.io.File;
import java.util.Hashtable;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.bussiness.msg.UploadJournalRequestMsg;
import server.server.socket.bussiness.msg.UploadJournalResponseMsg;
import server.server.socket.tool.FileOperationStruct;
import server.server.socket.tool.FileTools;
import server.server.socket.tool.FileTransferStruct;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:43:05
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalRequestMsgHandler {

	/**
	 * 	�����ϴ������ĵ�Ӧ����
	 * 
	 * @param transCode
	 * 						������
	 * @param transFlag
	 * 						�����־
	 * @param respCode
	 * 						��Ӧ��
	 * @return
	 * 			  ��Ӧ����
	 */
	public static String getUploadJournalResponseMsg(String transCode, boolean transFlag, String respCode) {
		UploadJournalResponseMsg requestUploadJournalResponseMsg = new UploadJournalResponseMsg();
		requestUploadJournalResponseMsg.setTransCode(transCode);
		requestUploadJournalResponseMsg.setTransFlag(transFlag);
		requestUploadJournalResponseMsg.setRespondCode(respCode);
		return requestUploadJournalResponseMsg.packMsg();
	}

	/**
	 *   ���������ˮ�ϴ�����
	 *   
	 * @param FileTransferQueue    �ļ���������
	 * @param msg		���յ���������
	 * @param clientIP   ����������豸IP
	 * @return
	 * 			 �����Ӧ����
	 */
	public static String handleRequestUploadJournalMsg(Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = null;
		try {
			//�ֽⱨ��
			UploadJournalRequestMsg requestUploadJournalMsg = new UploadJournalRequestMsg();
			requestUploadJournalMsg.unpackMsg(msg);
			String termCode = requestUploadJournalMsg.getTermCode();
			String fileTime = requestUploadJournalMsg.getFileTime();
			String atmFileMD5 = requestUploadJournalMsg.getAtmFileMD5();
			String atmFileName = requestUploadJournalMsg.getAtmFileName() ;
			boolean isContinueFlag = requestUploadJournalMsg.getIsContinueFlag();
			requestUploadJournalMsg.getAtmFileName() ;
			PubTools.log.info("������ˮ�ϴ�����: �豸��:[" + termCode + "] ����:[" + fileTime + "] MD5ֵ[" + atmFileMD5 + "]C���ϴ��ļ�����["+atmFileName+"]");
			File workingFile = null;
			FileTools fileOperator = new FileTools();
			// �ļ��� ��Ŀ¼\\�ն˺�\\��\\��\\�ն˺�_������.zip
			//*************20110509 xq �޸� ����tc�ļ��ı��� ������͵��ļ�������tc ����tc���� �������ǰ��ͬ
			String tempFileName = fileOperator.getFileName(termCode, fileTime,atmFileName);
			//****************************end****************************************************//
			PubTools.log.info("�ļ����·��Ϊ:" + tempFileName);
 
			// �����ļ�����Ŀ¼
			int index = tempFileName.lastIndexOf(File.separatorChar);
			if (index >= 0) {
				workingFile = new File(tempFileName.substring(0, index));
				// �ж��ļ����Ƿ����,�������,�򲻴����ļ���
				if (!workingFile.exists()) {
					if (!workingFile.mkdirs()) {
						// �����ļ���ʧ��
						PubTools.log.error("�����ļ�ʧ��");
						return getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), false,
								JournalServerParams.CreateFileError);
					}
				}
			}
			workingFile = new File(tempFileName);
			FileTransferStruct fileTransferStruct = new FileTransferStruct();
			FileOperationStruct fileOperationStruct = new FileOperationStruct();
			fileOperationStruct.setFileName(tempFileName);
			fileOperationStruct.setTermCode(termCode);
			fileOperationStruct.setFileMD5(atmFileMD5);
			fileOperationStruct.setFileTime(fileTime);
			fileOperationStruct.setAtmFileName(atmFileName) ;

			// ����Ƿ�ϵ�����,������� ���ж��ļ��Ƿ����,�������,��ɾ���ļ�
			if (!isContinueFlag) {
				if (workingFile.exists()) {
					workingFile.delete();
					fileOperationStruct.setFileOffSet(0);
				}
			}

			//2009-11-13 �޸�Ϊ��Ӧ����ʱ�������ļ������
			fileTransferStruct.addFileOperationStruct(fileOperationStruct);
			FileTransferQueue.put(clientIP, fileTransferStruct);
			// ���ɳɹ���Ӧ����
			toSendString = getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), true, JournalServerParams.Success);
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("handleRequestUploadJournalMsg Catch Exception:" + ex.getMessage());
			toSendString = getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), false, JournalServerParams.Error);
		}
		return toSendString;
	}



}
