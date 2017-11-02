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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:29:26
 * @Description
 * @version 1.0 Shawn create
 */
public class DownloadJournalRequestMsgHandler {

	/**
	 * 		获取下载流水请求报文
	 * 
	 * @param transCode
	 * 						交易码		
	 * @param sJournalList
	 * 						提取的电子流水范围
	 * @return
	 * 			下载流水请求报文
	 */
	public static String getDownloadJournalRequestMsg(String transCode, String sJournalList) {
		DownloadJournalRequestMsg downloadJournalRequestMsg = new DownloadJournalRequestMsg();
		downloadJournalRequestMsg.setJournalList(sJournalList) ;
		downloadJournalRequestMsg.setTransCode(transCode);
		return downloadJournalRequestMsg.packMsg();
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
	protected static String getDownloadJournalResponseMsg(String transCode,
			boolean transFlag, String respCode,String faultTermList) {
		DownloadJournalResponseMsg downloadJournalResponseMsg = new DownloadJournalResponseMsg();
		downloadJournalResponseMsg.setTransCode(transCode);
		downloadJournalResponseMsg.setRespCode(respCode);
		//如果处理标志位为true 则设置临时文件名及临时文件MD5值
		if (transFlag) {
			downloadJournalResponseMsg.setTransFlag(true);
		} else {  //否则设置失败信息列表
			downloadJournalResponseMsg.setFaultTermList(faultTermList);
			downloadJournalResponseMsg.setTransFlag(false);
		}
		return downloadJournalResponseMsg.packMsg();
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
	public static String handleDownloadJournalRequestMsg(
			Hashtable FileTransferQueue, String msg, String clientIP) {
		String toSendString = "";
		try {
			//分解下载请求报文
			DownloadJournalRequestMsg requireDownloadJournalMsg = new DownloadJournalRequestMsg();
			requireDownloadJournalMsg.unpackMsg(msg);
			String sJournalList = requireDownloadJournalMsg.getJournalList() ;  //查询的电子流水列表
			PubTools.log.debug("电子流水列表:"+sJournalList) ;
		
			//根据设备列表及流水时间范围查找文件
			FileTools fileOperator = new FileTools();
			boolean queryfile = fileOperator.CheckTerminalFileList(sJournalList);
			boolean transFlag = false;
			String respCode = "", faultTermList = "";
			if (queryfile) {//文件查找成功,返回文件名及文件MD5值
				PubTools.log.info("根据设备列表及电子流水时间范围查找文件成功") ;
				transFlag = true;
				//2009-11-13 修改为响应请求时不创建文件读入流
				respCode = JournalServerParams.Success;
			} else {  //文件查找失败，返回失败信息列表
				PubTools.log.info("根据设备列表及电子流水时间范围查找文件失败") ;
				respCode = JournalServerParams.Error;
				faultTermList = fileOperator.getErrorFileList();
				PubTools.log.info("失败信息列表是 "+faultTermList) ;
			}
			
			//返回响应报文
			toSendString = getDownloadJournalResponseMsg(JournalTransCodeMsg
					.getDownloadJournalResponseMsg(), transFlag, respCode,faultTermList);
		} catch (Exception ex) {
			PubTools.log.error("handleRequireDownloadJournalMsg Catch Exception:" + ex.toString());
			return "";
		}
		return toSendString;
	}

}
