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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:35:29
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalFileMsgHandler {

	/**
	 * 		获取下载流水文件请求报文
	 * 
	 * @param transCode
	 * 						交易码		
	 * @param sTermCode
	 * 						设备编号
	 * @param sFileTime
	 * 						文件时间
	 * @return
	 * 			下载流水请求报文
	 */
	public static String getDownloadJournalFileRequestMsg(String transCode, String sTermCode,String sFileTime) {
		DownloadJournalFileRequestMsg downloadJournalFileRequestMsg = new DownloadJournalFileRequestMsg();
		downloadJournalFileRequestMsg.setTermCode(sTermCode) ;
		downloadJournalFileRequestMsg.setFileTime(sFileTime) ;
		downloadJournalFileRequestMsg.setTransCode(transCode);
		return downloadJournalFileRequestMsg.packMsg();
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
	 *   处理下载电子流水请求报文
	 *   
	 * @param FileTransferQueue
	 * 								文件操作队列
	 * @param msg
	 * 					下载请求报文
	 * @param clientIP
	 * 						发起请求设备IP
	 * @return
	 */
	public static String handleDownloadJournalFileMsg(Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		try {
			//分解下载请求报文
			DownloadJournalFileRequestMsg downloadJournalFileRequestMsg = new DownloadJournalFileRequestMsg();
			downloadJournalFileRequestMsg.unpackMsg(msg);
			String sTermCode = downloadJournalFileRequestMsg.getTermCode() ;  //查询的设备号
			String sFileTime = downloadJournalFileRequestMsg.getFileTime();  //查询的文件时间
			PubTools.log.debug("设备号:"+sTermCode) ;
			PubTools.log.debug("文件时间:"+sFileTime) ;
		
			//根据设备列表及流水时间范围查找文件
			FileTools fileOperator = new FileTools();
			boolean queryfile = fileOperator.CheckTerminalFile(sTermCode,sFileTime);
			boolean transFlag = false;
			String tempFileName = "", tempFileMD5 = "";
			String respCode = "", faultTermList = "";
			
			if (queryfile) {//文件查找成功,返回文件名及文件MD5值
				PubTools.log.info("根据设备号及电子流水时间查找文件成功") ;
				transFlag = true;
				tempFileName = fileOperator.getTempFileName();
				tempFileMD5 = fileOperator.getFileMD5(tempFileName);
				PubTools.log.info("FileName is " + tempFileName + "FileMD5 is "+tempFileMD5) ;
				FileTransferStruct fileTransferStruct = new FileTransferStruct();
				FileOperationStruct fileOperationStruct = new FileOperationStruct();
				fileOperationStruct.setFileName(tempFileName);
				fileOperationStruct.setFileMD5(tempFileMD5);
				
				//2009-11-13 修改为响应请求时不创建文件读入流
				respCode = JournalServerParams.Success;
				fileTransferStruct.addFileOperationStruct(fileOperationStruct);
				FileTransferQueue.put(clientIP, fileTransferStruct);
			} else {  //文件查找失败，返回失败信息列表
				PubTools.log.info("根据设备列表及电子流水时间范围查找文件失败") ;
				respCode = JournalServerParams.Error;
				faultTermList = fileOperator.getErrorFileList();
				PubTools.log.info("失败信息列表是 "+faultTermList) ;
			}
			
			//返回响应报文
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
