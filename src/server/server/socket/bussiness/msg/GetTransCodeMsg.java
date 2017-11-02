/**
 * 
 */
package server.server.socket.bussiness.msg;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.handler.UploadJournalRequestMsgHandler;
import server.server.socket.tool.XMLMsgUtil;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:37:33
 * @Description
 * @version 1.0 Shawn create
 */
public class GetTransCodeMsg {

	public static int getTransCode(String XMLMsgString) {
		int transCode;
		// ��ȡ���Ľڵ�
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil();
		xmlMsgUtil.readXMLDocumentFromString(PubTools.removeStringLength(XMLMsgString)); // ���ַ�����ʽ����ת����XML��ʽ
		transCode = Integer.valueOf(xmlMsgUtil.getElement("jydm")).intValue(); // ��ȡ������
		return transCode;
	}
	
	/**
	 * ������ĳ��ȴ��� �򷵻���Ӧ����Ӧ����
	 * 
	 * @param XMLMsgString	
	 * 							������Ϣ
	 * @return
	 * 			��Ӧ����
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
			//��������ݴ��䱨��
			if (transCode == JournalTransCodeMsg.UploadJournalDataTransMsg) {
				PubTools.log.error("���յ������ݰ����� ������" + transCode);
				errString = UploadJournalRequestMsgHandler.getUploadJournalResponseMsg(JournalTransCodeMsg
						.getUploadJournalResponseMsg(), false, JournalServerParams.PackageError);
			}
		}
		return errString;
	}


}
