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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:31:56
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
	 * 		处理数据传输请求报文
	 * 
	 * @param FileTransferQueue
	 * 						文件处理队列
	 * @param msg
	 * 					请求报文
	 * @param clientIP
	 * 					发起请求报文的设备IP
	 * @return
	 * 				数据报文
	 */
	public static String handleDownloadJournalDataRequestMsg(
			Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		//分解数据传输请求报文
		DownloadJournalDataRequestMsg downloadJournalDataRequestMsg = new DownloadJournalDataRequestMsg();
		downloadJournalDataRequestMsg.unpackMsg(msg);
		long fileOffset = downloadJournalDataRequestMsg.getTempFileOffset(); // 临时文件偏移量
		byte[] readbytes = new byte[JournalServerParams.JournalServerDataBlockMax + 1]; // 数据缓冲区
		PubTools.log.debug("偏移量:["+fileOffset + "]") ;
		FileTransferStruct fileTransferStruct = (FileTransferStruct) FileTransferQueue.get(clientIP);
		FileOperationStruct fileOperationStruct = fileTransferStruct.getFileOperationStruct();
		try {
			boolean isLastBlock = false;
			int readLen = fileOperationStruct.readDataFromFile(readbytes, fileOffset);
			String stringbuf = PubTools.ConvertByteToBase64String(readbytes, readLen);
			//判断是否最后一个数据块
			if (readLen < JournalServerParams.JournalServerDataBlockMax) {
				fileOperationStruct.setIsLastBlock(true);
				isLastBlock = true;
				
				//最后一块数据，关闭文件流
				fileOperationStruct.closeFileInputStream();
			}
			//生成数据传输报文
			toSendString = getDownloadJournalDataTransMsg(JournalTransCodeMsg.getDownloadJournalDataTransMsg(),
					isLastBlock, readLen, stringbuf);
		} catch (IOException ex) {
			// 生成失败信息列表响应报文
			toSendString = DownloadJournalFileMsgHandler.getDownloadJournalFileResponseMsg(JournalTransCodeMsg
					.getDownloadJournalResponseMsg(), false, JournalServerParams.ReadFileError, "", "", "");
		}
//		} finally {
//			//关闭文件输入流
//			fileOperationStruct.closeFileInputStream();
//		}
		return toSendString;
	}



}
