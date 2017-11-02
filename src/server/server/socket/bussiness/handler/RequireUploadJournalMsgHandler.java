/**
 * 
 */
package server.server.socket.bussiness.handler;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.msg.RequireUploadJournalMsg;
import server.server.socket.bussiness.msg.UploadJournalResponseMsg;
import server.server.socket.tool.FileTools;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:53:34
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalMsgHandler {

	public static String getRequireUploadJournalMsg(String transCode, String devCode,
			String fileTime, boolean isEnforce) {
		RequireUploadJournalMsg requireUploadJournalMsg = new RequireUploadJournalMsg();
		requireUploadJournalMsg.setTermCode(devCode);
		requireUploadJournalMsg.setFileTime(fileTime);
		requireUploadJournalMsg.setTransCode(transCode);
		requireUploadJournalMsg.setIsEnforce(isEnforce);
		return requireUploadJournalMsg.packMsg();
	}

	public static String handleRequestUploadJournalResponseMsg(String transCode, boolean transFlag,
			String respCode, boolean isLastBlock, String atmFileName, String atmFileMD5) {
		UploadJournalResponseMsg requestUploadJournalResponseMsg = new UploadJournalResponseMsg();
		requestUploadJournalResponseMsg.setTransCode(transCode);
		requestUploadJournalResponseMsg.setTransFlag(transFlag);
		requestUploadJournalResponseMsg.setRespondCode(respCode);
		if (transFlag == true && isLastBlock == true) {
			// 文件MD5验证
			try {
				String checkFileMD5 = new FileTools().getFileMD5(atmFileName);
				if (!checkFileMD5.equalsIgnoreCase(atmFileMD5)) {
					requestUploadJournalResponseMsg.setTransFlag(false);
					requestUploadJournalResponseMsg.setRespondCode(JournalServerParams.MD5Error);
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
				PubTools.log.error("handleRequestUploadJournalResponseMsg Catch Exception:"
						+ ex.getMessage());
				requestUploadJournalResponseMsg.setTransFlag(false);
				requestUploadJournalResponseMsg.setRespondCode(JournalServerParams.MD5Error);
			}
		}
		return requestUploadJournalResponseMsg.packMsg();
	}

}
