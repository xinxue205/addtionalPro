/**
 * 
 */
package server.server.socket.bussiness.thread;

import java.util.Vector;

import server.server.socket.JournalServerParams;
import server.server.socket.SocketUtil;
import server.server.socket.bussiness.RequireUploadJournalDevTaskStruct;
import server.server.socket.bussiness.RequireUploadJournalfileTimeTaskStruct;
import server.server.socket.bussiness.handler.RequireUploadJournalMsgHandler;
import server.server.socket.bussiness.handler.UploadJournalStatusReportMsgHandler;
import server.server.socket.bussiness.msg.GetTransCodeMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.bussiness.msg.RequireUploadJournalMsg;
import server.server.socket.bussiness.msg.UploadJournalStatusReportMsg;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:50:46
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyRequireUploadJournalThread extends Thread {

	// ATMC�˿ں�
	private int atmcJournalServerPort = JournalServerParams.AtmcJournalServerPort;

	private int iRemainTasks = 0;

	private boolean tradeSuccess = false;

	private RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct;

	public void setRequireUploadJournalDevTaskStruct(RequireUploadJournalDevTaskStruct requireuploadJournalDevTaskStruct) {
		this.requireUploadJournalDevTaskStruct = requireuploadJournalDevTaskStruct;
	}

	public RequireUploadJournalDevTaskStruct getRequireUploadJournalDevTaskStruct() {
		return this.requireUploadJournalDevTaskStruct;
	}

	// ��ȡʣ���������
	public int getRemainTasksCount() {
		return this.iRemainTasks;
	}

	// ��ȡ���׽��
	public boolean getTradeSuccess() {
		return this.tradeSuccess;
	}

	public ImmediatelyRequireUploadJournalThread() {

	}

	public void run() {
		try {
			requireUploadJournal();
		} catch (Exception e) {
			PubTools.log.error("Require UpLoad Journal Thread Catch Exception:" + e.toString());
		}
		tradeSuccess = true;
	}

	public boolean requireUploadJournal()  throws Exception {
		SocketUtil socketUtil = null;
		try {
			iRemainTasks = requireUploadJournalDevTaskStruct.getSize();
			Vector temprequireUploadJournalDevTaskStruct = requireUploadJournalDevTaskStruct.getRequireUploadJournalDevTaskStruct();
			for (int i = 0; i < requireUploadJournalDevTaskStruct.getSize(); i++) {
				RequireUploadJournalfileTimeTaskStruct requireUploadJournalfileTimeTaskStruct = (RequireUploadJournalfileTimeTaskStruct) temprequireUploadJournalDevTaskStruct
						.get(i);
				if (requireUploadJournalfileTimeTaskStruct == null)
					return false;
				String termCode = requireUploadJournalfileTimeTaskStruct.getDevCode().trim();
				String devIP = requireUploadJournalfileTimeTaskStruct.getDevIP().trim();
				PubTools.log.info("��ʼʵʱ��ȡATM �豸��[" + termCode + "] �豸IP[" + devIP + "] ʱ��["
						+ requireUploadJournalfileTimeTaskStruct.getFileTime() + "] ��ˮ");
				socketUtil = new SocketUtil(devIP, this.atmcJournalServerPort,10000); //20121114 xq update ��ʱʱ����Ĭ��ʱ�� ��Ϊ10�볬ʱ

				if (socketUtil.createConnection()) {
					// ATMVH��ATMC���͵��ϴ�������ˮ������
					String requestMsg = RequireUploadJournalMsgHandler.getRequireUploadJournalMsg(requireUploadJournalfileTimeTaskStruct
							.getTransCode(), requireUploadJournalfileTimeTaskStruct.getDevCode(), requireUploadJournalfileTimeTaskStruct
							.getFileTime(), requireUploadJournalfileTimeTaskStruct.getIsEnforce());
					// ����������
					socketUtil.sendMessage(requestMsg);
					PubTools.log.debug("���͵����ݱ���Ϊ[" + requestMsg + "]");
					PubTools.log.info("�������ݱ��ĵ�ATM:[" + termCode + "]�ɹ�");
					boolean isContinue = true;
					while (isContinue) {
						// ��ȡ��Ӧ����
						String responseMsg = socketUtil.getMessage();
						PubTools.log.info("�����豸:[" + termCode + "]���ĳɹ�");
						PubTools.log.debug("���յ������ݱ���Ϊ:[" + responseMsg + "]");
						//�жϽ��յ�����Ӧ�����Ƿ�Ϊ�ջ��߳����Ƿ�С��4
						if (responseMsg.equals("") || responseMsg.length() < 4) {
							PubTools.log.error("���յ��豸:[" + termCode + "]��Ӧ���ĳ��ȳ���");
							return false;
						}
						int transCode = GetTransCodeMsg.getTransCode(responseMsg);
						if (transCode == -1) {
							PubTools.log.error("���յ��豸:[" + termCode + "]��Ӧ��Ӧ���������");
							return false;
						}
						switch (transCode) {
							case JournalTransCodeMsg.ImmediatelyUploadJournalRequestMsg : {
								PubTools.log.info("���յ��豸:["+termCode+"]���ϴ��ļ�����Ӧ����");
								RequireUploadJournalMsg requireUploadJournalMsg = new RequireUploadJournalMsg();
								requireUploadJournalMsg.unpackMsg(responseMsg);
								String respCode = requireUploadJournalMsg.getRespCode();
								if (JournalServerParams.Error.equals(respCode)) {
									return false;
								}
								break;
							}
							case JournalTransCodeMsg.ImmediatelyUploadJournalStatusReportMsg : {
								PubTools.log.info("���յ��豸:["+termCode+"]���ϴ��ļ����ϴ�״̬����");
								String toSendString = UploadJournalStatusReportMsgHandler.handleUploadJournalStatusReportMsg(responseMsg);
								PubTools.log.info("ʵʱ��ȡATM �豸��[" + requireUploadJournalfileTimeTaskStruct.getDevCode() + "] ��ˮ���");
								UploadJournalStatusReportMsg uploadJournalStatusMsg = new UploadJournalStatusReportMsg();
								uploadJournalStatusMsg.unpackMsg(responseMsg);
								if (uploadJournalStatusMsg.getTransResult()) {
									iRemainTasks--;
								}
								// �����ϴ�״̬���ĵ���Ӧ����
								socketUtil.sendMessage(toSendString);
								isContinue = false;
								break;
							}
						}
					}
				} else {
					PubTools.log.info("�豸[" + termCode + "]����ͨѶ�쳣 ����ʧ��");
					return false;
				}
			}
		} finally {
			if (socketUtil != null)
				socketUtil.closeConnection();
		}
		return true;
	}
}
