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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:43:05
 * @Description
 * @version 1.0 Shawn create
 */
public class UploadJournalRequestMsgHandler {

	/**
	 * 	返回上传请求报文的应答报文
	 * 
	 * @param transCode
	 * 						交易码
	 * @param transFlag
	 * 						处理标志
	 * @param respCode
	 * 						响应码
	 * @return
	 * 			  响应报文
	 */
	public static String getUploadJournalResponseMsg(String transCode, boolean transFlag, String respCode) {
		UploadJournalResponseMsg requestUploadJournalResponseMsg = new UploadJournalResponseMsg();
		requestUploadJournalResponseMsg.setTransCode(transCode);
		requestUploadJournalResponseMsg.setTransFlag(transFlag);
		requestUploadJournalResponseMsg.setRespondCode(respCode);
		return requestUploadJournalResponseMsg.packMsg();
	}

	/**
	 *   处理电子流水上传请求
	 *   
	 * @param FileTransferQueue    文件操作队列
	 * @param msg		接收到的请求报文
	 * @param clientIP   发起请求的设备IP
	 * @return
	 * 			 请求的应答报文
	 */
	public static String handleRequestUploadJournalMsg(Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = null;
		try {
			//分解报文
			UploadJournalRequestMsg requestUploadJournalMsg = new UploadJournalRequestMsg();
			requestUploadJournalMsg.unpackMsg(msg);
			String termCode = requestUploadJournalMsg.getTermCode();
			String fileTime = requestUploadJournalMsg.getFileTime();
			String atmFileMD5 = requestUploadJournalMsg.getAtmFileMD5();
			String atmFileName = requestUploadJournalMsg.getAtmFileName() ;
			boolean isContinueFlag = requestUploadJournalMsg.getIsContinueFlag();
			requestUploadJournalMsg.getAtmFileName() ;
			PubTools.log.info("电子流水上传请求: 设备号:[" + termCode + "] 日期:[" + fileTime + "] MD5值[" + atmFileMD5 + "]C端上传文件名：["+atmFileName+"]");
			File workingFile = null;
			FileTools fileOperator = new FileTools();
			// 文件名 总目录\\终端号\\年\\月\\终端号_年月日.zip
			//*************20110509 xq 修改 用于tc文件的保存 如果上送的文件命中有tc 则用tc命名 否则和以前相同
			String tempFileName = fileOperator.getFileName(termCode, fileTime,atmFileName);
			//****************************end****************************************************//
			PubTools.log.info("文件存放路径为:" + tempFileName);
 
			// 创建文件所在目录
			int index = tempFileName.lastIndexOf(File.separatorChar);
			if (index >= 0) {
				workingFile = new File(tempFileName.substring(0, index));
				// 判断文件夹是否存在,如果存在,则不创建文件夹
				if (!workingFile.exists()) {
					if (!workingFile.mkdirs()) {
						// 创建文件夹失败
						PubTools.log.error("创建文件失败");
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

			// 检查是否断点续存,如果不是 则判断文件是否存在,如果存在,则删除文件
			if (!isContinueFlag) {
				if (workingFile.exists()) {
					workingFile.delete();
					fileOperationStruct.setFileOffSet(0);
				}
			}

			//2009-11-13 修改为响应请求时不创建文件输出流
			fileTransferStruct.addFileOperationStruct(fileOperationStruct);
			FileTransferQueue.put(clientIP, fileTransferStruct);
			// 生成成功响应报文
			toSendString = getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), true, JournalServerParams.Success);
		} catch (Exception ex) {
			// ex.printStackTrace();
			PubTools.log.error("handleRequestUploadJournalMsg Catch Exception:" + ex.getMessage());
			toSendString = getUploadJournalResponseMsg(JournalTransCodeMsg.getUploadJournalResponseMsg(), false, JournalServerParams.Error);
		}
		return toSendString;
	}



}
