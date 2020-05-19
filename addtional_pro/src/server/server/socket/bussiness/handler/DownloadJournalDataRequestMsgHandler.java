/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.io.IOException;
import java.util.Hashtable;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.msg.DownloadJournalDataRequestMsg;
import server.server.socket.bussiness.msg.DownloadJournalDataTransMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.tool.FileOperationStruct;
import server.server.socket.tool.FileTransferStruct;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:31:56
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalDataRequestMsgHandler {

	private static String getDownloadJournalDataTransMsg(String transCode, boolean isLastBlock,
			long dataSize, String dataBlock) {
		DownloadJournalDataTransMsg downloadJournalDataRequestMsg = new DownloadJournalDataTransMsg();
		downloadJournalDataRequestMsg.setTransCode(transCode);
		downloadJournalDataRequestMsg.setIsLastBlock(isLastBlock);
		downloadJournalDataRequestMsg.setDataSize(dataSize);
		downloadJournalDataRequestMsg.setDataBlock(dataBlock);
		return downloadJournalDataRequestMsg.packMsg();
	}

	public static String getDownloadJournalDataRequestMsg(String transCode, String tempFileName,
			long tempFileOffset) {
		DownloadJournalDataRequestMsg downloadJournalDataRequestMsg = new DownloadJournalDataRequestMsg();
		downloadJournalDataRequestMsg.setTempFileName(tempFileName);
		downloadJournalDataRequestMsg.setTempFileOffset(tempFileOffset);
		downloadJournalDataRequestMsg.setTransCode(transCode);
		return downloadJournalDataRequestMsg.packMsg();
	}

	/**
	 * 		�������ݴ���������
	 * 
	 * @param FileTransferQueue
	 * 						�ļ��������
	 * @param msg
	 * 					������
	 * @param clientIP
	 * 					���������ĵ��豸IP
	 * @return
	 * 				���ݱ���
	 */
	public static String handleDownloadJournalDataRequestMsg(
			Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		//�ֽ����ݴ���������
		DownloadJournalDataRequestMsg downloadJournalDataRequestMsg = new DownloadJournalDataRequestMsg();
		downloadJournalDataRequestMsg.unpackMsg(msg);
		long fileOffset = downloadJournalDataRequestMsg.getTempFileOffset(); // ��ʱ�ļ�ƫ����
		byte[] readbytes = new byte[JournalServerParams.JournalServerDataBlockMax + 1]; // ���ݻ�����
		PubTools.log.debug("ƫ����:["+fileOffset + "]") ;
		FileTransferStruct fileTransferStruct = (FileTransferStruct) FileTransferQueue.get(clientIP);
		FileOperationStruct fileOperationStruct = fileTransferStruct.getFileOperationStruct();
		try {
			boolean isLastBlock = false;
			int readLen = fileOperationStruct.readDataFromFile(readbytes, fileOffset);
			String stringbuf = PubTools.ConvertByteToBase64String(readbytes, readLen);
			//�ж��Ƿ����һ�����ݿ�
			if (readLen < JournalServerParams.JournalServerDataBlockMax) {
				fileOperationStruct.setIsLastBlock(true);
				isLastBlock = true;
				
				//���һ�����ݣ��ر��ļ���
				fileOperationStruct.closeFileInputStream();
			}
			//�������ݴ��䱨��
			toSendString = getDownloadJournalDataTransMsg(JournalTransCodeMsg.getDownloadJournalDataTransMsg(),
					isLastBlock, readLen, stringbuf);
		} catch (IOException ex) {
			// ����ʧ����Ϣ�б���Ӧ����
			toSendString = DownloadJournalFileMsgHandler.getDownloadJournalFileResponseMsg(JournalTransCodeMsg
					.getDownloadJournalResponseMsg(), false, JournalServerParams.ReadFileError, "", "", "");
		}
//		} finally {
//			//�ر��ļ�������
//			fileOperationStruct.closeFileInputStream();
//		}
		return toSendString;
	}



}
