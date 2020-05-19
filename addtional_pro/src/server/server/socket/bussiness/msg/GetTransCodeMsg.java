/**
 * 
 */
package server.server.socket.bussiness.msg;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.handler.UploadJournalRequestMsgHandler;
import server.server.socket.tool.XMLMsgUtil;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:37:33
 * @Description
 * @version 1.0 Shawn create
 */
public class GetTransCodeMsg {

	public static int getTransCode(String XMLMsgString) {
		int transCode;
		// 获取报文节点
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // 将字符串格式报文转化成XML格式
		transCode = Integer.valueOf(xmlMsgUtil.getElement("jydm")).intValue(); // 获取交易码
		return transCode;
	}
	
	/**
	 * 如果报文长度错误 则返回相应的响应报文
	 * 
	 * @param XMLMsgString	
	 * 							报文信息
	 * @return
	 * 			响应报文
	 */
	public static String getErrorResponse(String XMLMsgString){
		String errString = "" ;
		int transCode = 0 ;
		int iIndex = XMLMsgString.indexOf("<jydm>");
		int jIndex = XMLMsgString.indexOf("</jydm>") ;
		if (iIndex < 0 || jIndex < 0 )
			return errString;
		else {
			String transString = XMLMsgString.substring(iIndex+6, jIndex);
			System.out.println(transString);
			transCode = Integer.parseInt(transString);
			//如果是数据传输报文
			if (transCode == JournalTransCodeMsg.UploadJournalDataTransMsg) {
				PubTools.log.error("接收到的数据包出错 交易码" + transCode);
				errString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg
						.getUploadJournalResponseMsg(), false, JournalServerParams.PackageError);
			}
		}
		return errString;
	}


}
