/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import server.server.socket.ServiceParameter;
import server.server.socket.bussiness.RequireUploadJournalDevTaskStruct;
import server.server.socket.bussiness.RequireUploadJournalfileTimeTaskStruct;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.bussiness.msg.WebRequestJournalMsg;
import server.server.socket.bussiness.msg.WebRequestJournalResponseMsg;
import server.util.JdbcFactory;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:22:41
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalMsgHandler {

	private List alTermList = new ArrayList(); // ������ˮ�����б�

	private HashMap hmHashMap = new HashMap(); // �豸�б�

	private String journalList = "";     // ��ʽ"ermCode@fileTime#termCode@fileTime"
									
	private boolean getFileSuccess = false; // �Ƿ��ȡ�ļ��ɹ�

	private String requestMsg = ""; // ������ˮ������

	//private SqlMapClient sqlMapper = null; // ���ݿ����

	private String responseMsg = ""; // ʧ�ܴ�����Ϣ
	
	private String tempFilePath = "" ; //��ʱ�ļ���
	
	private String sGetMethod = "";//20120828 xq add ��ȡ��ʽ 0 �����з�ʽ��ȡ 1ֱ�Ӵ�ATMC��ȡ
	
	private String sDevCodeStr = "";//20121108 xq add ��ȡ�豸���б���Ϣ���������豸����Ϣ
	
	private static final String sDevVer = "005.003.004.000"; 
	
	

	/**
	 * ����WEB->ATHVH������ˮ�ᵽ�����ĵ���Ӧ����
	 * 
	 * @param transCode
	 *            ��Ӧ���ĵĽ�����
	 * @param transFlag
	 *            �����־λ
	 * @param tempFileName
	 *            ��ʱ�ļ���
	 * @param faultMessage
	 *            ������Ϣ
	 * @return ��Ӧ����
	 */
	public String getWebRequestJournalResponseMsg(String transCode, boolean transFlag, String tempFileName,
			String faultMessage) {
		String returnString = ""; // ���ر���
		WebRequestJournalResponseMsg webRequestJournalResponseMsg = new WebRequestJournalResponseMsg();
		webRequestJournalResponseMsg.setTransCode(transCode); // ���ý�����
		webRequestJournalResponseMsg.setTempFileName(tempFileName);
		webRequestJournalResponseMsg.setFaultMessage(faultMessage);
		// ���ݴ����־��ֵ
		if (transFlag) {
			// ��������־Ϊ�ɹ� ��������ʱ�ļ���
			webRequestJournalResponseMsg.setTransFlag(true);
		} else {
			// ��������־Ϊʧ�� �����ô�����Ϣ	
			webRequestJournalResponseMsg.setTransFlag(false);
		}
		// �����ᵽ������ˮ�����ĵ���Ӧ����
		returnString = webRequestJournalResponseMsg.packMsg();
		PubTools.log.debug(returnString); // �ѷ��ر��ļ�¼ΪDebug��־
		return returnString;
	}

	/**
	 * ����WEB->ATHVH������ˮ�ᵽ������
	 * 
	 * @return ��Ӧ����
	 */
	public String handleWebRequestJournalMsg() {
		PubTools.log.info("����WEB��ȡ����׶�handleWebRequestJournalMsg>>>>>>>>>>>>>>>>>>>>>");
		String toSendString = ""; // ��Ӧ����
		WebRequestJournalMsg webDownloadJournalMsg = new WebRequestJournalMsg();
		webDownloadJournalMsg.unpackMsg(this.requestMsg);
		this.journalList = webDownloadJournalMsg.getJournalList(); // ��ȡ�����豸���б�
		this.sGetMethod = webDownloadJournalMsg.getSGetMethod();//20120828 xq add ������ȡ��ʽ
		
		//------------------20110418���� �޸�
		WebRequstJournalExceptInLocalHandle webRequstJournalExceptInLocalHandle = new WebRequstJournalExceptInLocalHandle();
		webRequstJournalExceptInLocalHandle.setSGetFileMethode(sGetMethod);
		
		//20120828 xq add �����ȡ��ʽΪ0 �������з�ʽ���� ����ֱ����ATMC��ȡ�ļ�������ȡ�ļ�֮ǰ�����ȱ���һ����VH�Ѿ����ڵ��ļ�
		if(sGetMethod!=null&&!"".equals(sGetMethod)&&sGetMethod.equals("1")){
			webRequstJournalExceptInLocalHandle.copyFileToTmp(journalList);//�����ֱ�Ӵ�ATMC��ȡ�ļ��������Ƚ����ǵ�ǰ�����ˮ����һ�� �����ļ��� �豸��_����_yyyyMMddHHssmm.zip
		}
//		else{
//			this.journalList = webRequstJournalExceptInLocalHandle.removeExistInVHFile(this.journalList);	
//		}
		this.journalList = webRequstJournalExceptInLocalHandle.removeExistInVHFile(this.journalList);	

		//----------------end 20120828-------------------------------------------------------------------------
		
		
		if(journalList==null||journalList.equals("")){//������е��ļ���VH�˿����ҵ��Ļ� ����Ҫ��V����ȡ��ˮ
			// �����ȡ�ļ��ɹ����򷵻سɹ������Լ���ʱ�ļ���
			toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(), true,
					webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
		}else{
			requireDownloadJournal(); // ��ATMV�������ص�����ˮ�ļ�������
			if (this.getFileSuccess) {
				
				// �����ȡ�ļ��ɹ����򷵻سɹ������Լ���ʱ�ļ���
				toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(), true,
						webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
			} else {
				// �����ȡ�ļ�ʧ�ܣ��򷵻�ʧ�ܱ���,����ʧ�ܴ�����Ϣ
				toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(),
						false, webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
			}
		}
		
		PubTools.log.info("������ȡ���ر��ģ�"+toSendString);
		return toSendString;
	}

	/**
	 * ATMVH��ATMV���͵�����ˮ�������� ���ATMVû�иõ�����ˮ ����ATMC�����ϴ�������ˮ������
	 * ATMC�ϴ��������ˮ��֪ͨATMVH ATMVH����ATMV���ص�����ˮ
	 */
	public void  requireDownloadJournal() {
		int iIndex = 0, jIndex = 0;
		try {
//			PubTools.log.info("��ʼ��ѯ���е�����ˮ�ļ���������IP��ַ����������������������������");
			//sqlMapper = Dboperator.getSqlMapper();
			// ��ȡ�豸�б�
			this.getTermListFromRequestList(this.journalList);
			if (this.alTermList.size() == 0) {	
				this.responseMsg = "�豸���Ϊ��" ;
				PubTools.log.info(this.responseMsg);
				return ;
			}
			
			
			Map mDevVerMap = queryDevVersionInfo();//20121109 xq add ��ѯ�豸��ǰ����5340�汾����
			
			//----------------------------20121109 xq add �ȹ��˵���ATMC��ȡ���豸���ļ���ȡ������ж��Ƿ��������ATMV��ȡ-------//
			// ���ж��Ƿ��е������ˮ������У�������C�����ϴ������ϴ�������V��ѯ����
			Vector vecCurDateDevInfo = new Vector();
			HashMap hmTermInfo = null;
			int iCountSize = alTermList.size();
			for (iIndex = 0; iIndex < iCountSize; iIndex++) {
				hmTermInfo = (HashMap) alTermList.get(iIndex);
				boolean isGetFromAtmc = false;
//				vecCurDateDevInfo.addElement(hmTermInfo.get("termCode"));
//				vecCurDateDevInfo.addElement(hmTermInfo.get("fileTime"));
				String sTermCode = hmTermInfo.get("termCode")==null?"":hmTermInfo.get("termCode").toString().trim();
				String sFileTime = hmTermInfo.get("fileTime")==null?"":hmTermInfo.get("fileTime").toString().trim();
				String sCurrVer = mDevVerMap.get(sTermCode)==null?"": mDevVerMap.get(sTermCode).toString().trim();
				if(sFileTime.equals(PubTools.getSystemDate())){
					isGetFromAtmc = true; //�����ֱ�Ӵ�ATMC��ȡ
				}
				if(sGetMethod.equals("1")){
					isGetFromAtmc = true;//ҳ���й�ѡ��ATMC��ǰ��ֱ�Ӵ�ATMC��ȡ
				}
				if(!"".equals(sCurrVer)){
					isGetFromAtmc = true;//����汾���ڵ���5340��ֱ�Ӵ�ATMC��ȡ
					alTermList.remove(hmTermInfo);
				}
			    
			     //20120829 xq add �����ȡΪ����� ������ֱ�Ӵ�ATMC��ȡ��ʶ�� �����ATMC��ȡ���ļ�
			     //20121109 xq  ��ATMC��ȡ����: 1��������ļ� 2����ȡ��ʽΪ����ֱ����ȡ 3������汾����5340
				if (isGetFromAtmc) {
					vecCurDateDevInfo.addElement(sTermCode);
					vecCurDateDevInfo.addElement(sFileTime);
					//����е�����ˮ��������C�˷��ϴ���������ϴ����ɹ��Ļ����򽫵�ǰ����������ϴ�������豸�ĵ���Ϣɾ��
					if (!getFileFromATMC(vecCurDateDevInfo)) {
						
//						 this.journalList = this.removeStr(this.journalList,sTermCode+"@"+sFileTime+"#" );
//						    PubTools.log.error("�豸���Ϊ"+sTermCode+" �������ˮ�����ڣ���ȥ���豸������ˮ��ȡ����," 
//						    		           + "   ��ȥ���������������Ϊ:"+this.journalList);
					}
					//��������� ��������豸����ȡ��Ϣ �����������
					if(vecCurDateDevInfo.size() != 0){
					   vecCurDateDevInfo.clear();
					}
				}
			}
			
			this.journalList = changeLeftDevList(alTermList);//������Ҫ������ATMV��ȡ��ˮ���豸���˵�
			if(journalList==null||"".equals(journalList)){//���Ϊ�յĻ� ����Ҫ�����V���κ���ȡ����
				return;
			}
			//--------------------------------end-----------------------------------------------------------------------------
			
	
			
			
			// �����豸���б��ѯ�豸���ڵķ��еĵ�����ˮ�ļ���������ַ
			String branchJournalServerIP = "";
			// �����豸�Ų�ѯ�豸���ڵķ��е�����ˮ�ļ�������
			// ������е��豸��������һ�£���Ϊ�ɹ���������ʾ����ͬһ������
			 Iterator itTermList = this.hmHashMap.keySet().iterator();
			 iIndex = 0 ;
			while (itTermList.hasNext()) {
				String sTermCode = (String) itTermList.next();
				String querySql = "select branch_node from branchjournal_msg where branch_code "
						      + " in (select dev_branch1 from dev_bmsg where dev_code='" + sTermCode + "')";
				PubTools.log.debug(querySql);
				// �Աȸ����豸�Ų�ѯ���ķ��е�IP�Ƿ�һ��
				List resultList = JdbcFactory.queryForList(querySql);
				if (resultList != null && resultList.size() > 0) {
					Object[] resultArray = resultList.toArray();
					if (iIndex == 0){
						branchJournalServerIP = ((HashMap) resultArray[0]).get("branch_node").toString().trim();	
					}else {
						if (!branchJournalServerIP.equalsIgnoreCase(((HashMap) resultArray[0]).get("branch_node")
								.toString().trim())) {
							this.responseMsg = "�豸����ͬһ������,ֻ�ܲ�ѯͬһ���е��豸" ;
							PubTools.log.info(this.responseMsg);
							return ;
						}
					}
				} else {
					this.responseMsg = "�豸[" + sTermCode + "]��Ӧ�ķ�����Ϣ������" ;
					PubTools.log.info(this.responseMsg);
					return ;
				}
				iIndex ++ ; 
			}
			if (branchJournalServerIP.equals("")) {
				this.responseMsg = "��ѯ����ATM������ˮ�ļ���������IP��ַʧ�ܣ���" ;
				PubTools.log.info(this.responseMsg );
				return ;
			}

			// ��ATMV���͵�����ˮ�б�������
			ImmediatelyDownloadJournalHandler immediatelyDownloadJournalHandler = new ImmediatelyDownloadJournalHandler();
			immediatelyDownloadJournalHandler.setJournalList(this.journalList);
			immediatelyDownloadJournalHandler.setBranchJournalServerIP(branchJournalServerIP);

			
			//-----------------------20121109 xq ע��  ���ò����������� ���ȹ��˵���ATMC��ȡ���豸����ATMC��ȡ���Ȼ����ATMV��ȡ-----//
//			// ���ж��Ƿ��е������ˮ������У�������C�����ϴ������ϴ�������V��ѯ����
//			Vector vecCurDateDevInfo = new Vector();
//			for (iIndex = 0; iIndex < alTermList.size(); iIndex++) {
//				HashMap hmTermInfo = (HashMap) alTermList.get(iIndex);
//				//20120829 xq add �����ȡΪ����� ������ֱ�Ӵ�ATMC��ȡ��ʶ�� �����ATMC��ȡ���ļ�
//				if (hmTermInfo.get("fileTime").equals(PubTools.getSystemDate())||sGetMethod.equals("1")) {
//					vecCurDateDevInfo.addElement(hmTermInfo.get("termCode"));
//					vecCurDateDevInfo.addElement(hmTermInfo.get("fileTime"));
//					String sTermCode = ""+hmTermInfo.get("termCode");
//					String sFileTime = ""+hmTermInfo.get("fileTime");
//					//����е�����ˮ��������C�˷��ϴ���������ϴ����ɹ��Ļ����򽫵�ǰ����������ϴ�������豸�ĵ���Ϣɾ��
//					if (!getFileFromATMC(vecCurDateDevInfo)) {
////						 this.journalList = this.removeStr(this.journalList,sTermCode+"@"+sFileTime+"#" );
////						    PubTools.log.error("�豸���Ϊ"+sTermCode+" �������ˮ�����ڣ���ȥ���豸������ˮ��ȡ����," 
////						    		           + "   ��ȥ���������������Ϊ:"+this.journalList);
//					}
//					//��������� ������е�����豸����ȡ��Ϣ �����������
//					if(vecCurDateDevInfo.size() != 0){
//					   vecCurDateDevInfo.clear();
//					}
//				}
//			}
			
			//---------------------------------20121109 end------------------------------------------------------------------//
			
			// ����б�Ϊ�գ����ʾ���е������ˮ��������C�˷��ϴ�����
//			if (vecCurDateDevInfo.size() != 0) {
//				if (!getFileFromATMC(vecCurDateDevInfo)) {
//  					for (iIndex = 0; iIndex < vecCurDateDevInfo.size() / 2; iIndex++) {
//						String strDevCode = (String) vecCurDateDevInfo.elementAt(iIndex * 2);
//						String strFileTime = (String) vecCurDateDevInfo.elementAt(iIndex * 2 + 1);
//						
//					    this.journalList = this.removeStr(this.journalList,strDevCode+"@"+strFileTime+"#" );
//					    PubTools.log.error("�豸���Ϊ"+strDevCode+" �������ˮ�����ڣ���ȥ���豸������ˮ��ȡ����," 
//					    		           + "   ��ȥ���������������Ϊ:"+this.journalList);
//					   
//					this.getFileSuccess = false;
//					return ;
//					}
//				}
//			}

			// ��V�˷����ϴ�������ˮ��������
			boolean isContinue = true;
			int iCount = 0;
//			int iMaxCount = 1;//20121108 xq ����ǰ��ȡ���ɹ������Ĵ� �޸�Ϊֻ��ȡһ��
//			while (isContinue) {//20121108 xq ����ǰ��ȡ���ɹ������Ĵ� �޸�Ϊֻ��ȡһ��
//				// ��C�����ص�����ˮ
				boolean isSuccessGet = false;
				try {
					isSuccessGet = immediatelyDownloadJournalHandler.requireDownloadJournal();
					iCount++;
				} catch (Exception ex) {
					this.responseMsg = "��ѯ������ˮ�쳣";
					this.getFileSuccess = false;
					return ;
				}
				if (!isSuccessGet) {
					// ��ȡ������Ϣ�б�
					String faultTermList = immediatelyDownloadJournalHandler.getFaultTermList();
					PubTools.log.info("ʧ�ܴ�����Ϣ�б�:" + faultTermList);
					// ������ص�ʧ�ܴ�����Ϣ�б�Ϊ�գ���ֱ�ӷ���ʧ��
					if (faultTermList == null || faultTermList.length() == 0) {
						this.responseMsg = "��ѯ������ˮʧ��";
						this.getFileSuccess = false;
						return ;
					}
					// ������ص�ʧ�ܴ�����Ϣ�б�Ϊ�գ����ʧ�ܴ�����Ϣ�б���豸�����ϴ���ˮ�ļ�������
					// �Ѵ�����Ϣ��ӵ�Vector��
					// ������Ϣ�б��ʽΪ �豸��$��ˮʱ��#�豸��$��ˮʱ��#
					String[] faultTermInfo = faultTermList.split("\\#");
					Vector vecDevInfo = new Vector();
					if (faultTermInfo != null && faultTermInfo.length > 0) {
						for (iIndex = 0; iIndex < faultTermInfo.length; iIndex++) {
							String[] termInfo = faultTermInfo[iIndex].split("\\$", -2);
							String devCode = termInfo[0];
							String fileTime = termInfo[1];
							for (jIndex = 0; jIndex < vecDevInfo.size() / 2; jIndex++) {
								String strDevCode = (String) vecDevInfo.elementAt(jIndex * 2);
								if (devCode.equals(strDevCode)) {
									String strFileTime = (String) vecDevInfo.elementAt(jIndex * 2 + 1);
									vecDevInfo.setElementAt(strFileTime + "#" + fileTime, jIndex * 2 + 1);
									break;
								}
							}
							if (jIndex == vecDevInfo.size() / 2) {
								vecDevInfo.addElement(devCode);
								vecDevInfo.addElement(fileTime);
							}
						}
					} else{
						this.responseMsg = "��ԃʧ���豸�б�Ϊ�գ�";
						return ;
					}
					// �����ȡ������ˮʧ�ܣ�����C�˷����ϴ�������ˮ������
					// ����������ѭ��������
					if (getFileFromATMC(vecDevInfo)){
						isContinue = false;
					}else
						isContinue = true;
				} else {
					isContinue = false;
				}
				
//				if(iCount>iMaxCount){
//					isContinue = false;
//					this.responseMsg = "��C�˲�ԃ������ˮ��������["+iMaxCount+"]�Σ�������ˮ�ϴ���ֹ��";
//				}
//			}
			// ������ص�����ˮ�ɹ����򷵻ص�����ˮ���ڵ�λ��
			PubTools.log.error("���ϴ�ǰ��journalList�б�Ϊ:"+this.journalList);
			immediatelyDownloadJournalHandler.setJournalList(this.journalList);
			immediatelyDownloadJournalHandler.downloadJournal();
			this.getFileSuccess = true ;
			this.tempFilePath = immediatelyDownloadJournalHandler.getTempFilePath();  //��ȡ��ʱ�ļ���
			this.responseMsg = immediatelyDownloadJournalHandler.getFaultTermList() ;  //��ȡ������Ϣ
		} catch (Exception ex) {
			PubTools.log.error("requireDownloadJournal Catch Exception:" + ex.toString());
		}
	}

	/**
	 * 			��ATMC�����ϴ�������ˮ��������
	 * 
	 * @param vecDevInfo
	 * 							ATMC�豸��Ϣ
	 * @return
	 * 				�Ƿ��ͳɹ�
	 * @throws Exception
	 */
	public boolean getFileFromATMC(Vector vecDevInfo) throws Exception {
		PubTools.log.info("��ʼ������ATMC��ȡ�ļ��������");
		int iIndex = 0, jIndex = 0;
		Vector requestTasks = new Vector(1);
		Hashtable requireUploadJournalQueue = new Hashtable();
		Hashtable devTaskStruct = new Hashtable();
		for (iIndex = 0; iIndex < vecDevInfo.size() / 2; iIndex++) {
			String strDevCode = (String) vecDevInfo.elementAt(iIndex * 2);
			String strFileTime = (String) vecDevInfo.elementAt(iIndex * 2 + 1);
			String devNode = "";
			String queryDevSql = "select dev_node from dev_bmsg where dev_code='" + strDevCode + "' ";
			List resultDevList = JdbcFactory.queryForList( queryDevSql);
			PubTools.log.info("��ѯATM�豸���[" + strDevCode + "]��IP��ַ");
			if (resultDevList != null && resultDevList.size() > 0) {
				Object[] resultArray = resultDevList.toArray();
				for (jIndex = 0; jIndex < resultArray.length; jIndex++) {
					devNode = ((HashMap) resultArray[jIndex]).get("dev_node").toString().trim();
				}
			}
			if (devNode.equals("")) {
				this.responseMsg = "��ѯATM�豸���[" + strDevCode + "]��IP��ַʧ��";
				PubTools.log.info(this.responseMsg);
				return false;
			}
			RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct = new RequireUploadJournalDevTaskStruct();
			String[] fileTimes = strFileTime.split("\\#", -2);
			for (jIndex = 0; jIndex < fileTimes.length; jIndex++) {
				String fileTime = fileTimes[jIndex];
				RequireUploadJournalfileTimeTaskStruct requireUploadJournalfileTimeTaskStruct = new RequireUploadJournalfileTimeTaskStruct();
				requireUploadJournalfileTimeTaskStruct.setTransCode(JournalTransCodeMsg
						.getImmediatelyUploadJournalRequestMsg());
				// �����豸���
				requireUploadJournalfileTimeTaskStruct.setDevCode(strDevCode);
				// ������ˮ����
				requireUploadJournalfileTimeTaskStruct.setFileTime(fileTime);
				// ��ȡ������ˮ��һ������ȡ,����Ϊǿ����ȡ
				if (fileTime.equals(PubTools.getSystemDate()))
					requireUploadJournalfileTimeTaskStruct.setIsEnforce(false);
				else
					requireUploadJournalfileTimeTaskStruct.setIsEnforce(true);
				// �����豸IP
				requireUploadJournalfileTimeTaskStruct.setDevIP(devNode);
				requireUploadJournalDevTaskStruct
						.addRequireUploadJournalDevTaskStruct(requireUploadJournalfileTimeTaskStruct);
			}
			requireUploadJournalQueue.put(strDevCode + ":" + devNode, requireUploadJournalDevTaskStruct);
			devTaskStruct.put(strDevCode + ":" + devNode, "false");
			requestTasks.addElement(strDevCode + ":" + devNode);
		}

		PubTools.log.info("��ʼʵʱ��ȡ������ˮ");
		ImmediatelyRequireUploadJournalHandler immediatelyRequireUploadJournalHandler = new ImmediatelyRequireUploadJournalHandler();
		immediatelyRequireUploadJournalHandler.setRequestTasks(requestTasks);
		immediatelyRequireUploadJournalHandler.setDevTaskStruct(devTaskStruct);
		immediatelyRequireUploadJournalHandler.setRequireUploadJournalQueue(requireUploadJournalQueue);
		if (!immediatelyRequireUploadJournalHandler.requireUploadJournal()) {
//			this.getFileSuccess = false;
			this.responseMsg = "��ATMC�˻�ȡ�ļ�ʧ��:" + immediatelyRequireUploadJournalHandler.getErrInfo();
			
//			PubTools.log.info("����Ĵ�����ϢΪ........................");

			PubTools.log.info(this.responseMsg);
			return false;
		} else {
			PubTools.log.info("ʵʱ��ȡ������ˮ�ɹ�");
			return true;
		}
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	/**
	 * 		�ӵ�����ˮ�����б��еõ��豸�ż�������ˮʱ��
	 * 
	 * @param sJournalList
	 * 						������ˮ�����б�
	 */
	public void getTermListFromRequestList(String sJournalList) {
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			HashMap hmTmpTermList = new HashMap();
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			hmTmpTermList.put("termCode", sArrTermInfo[0]);
			hmTmpTermList.put("fileTime", sArrTermInfo[1]);
			hmHashMap.put(sArrTermInfo[0], "1"); // ��¼�豸���
			alTermList.add(hmTmpTermList);
			sDevCodeStr= sDevCodeStr + "'"+sArrTermInfo[0]+"',";//20121108 xq add  �����ڻ�ȡ���������V��ATMC��ȡ�ļ�ʱ���豸�б�����ѯ���������豸�б���豸�汾��
		}
		sDevCodeStr = sDevCodeStr + "''";//20121108 xq add ԭ��ͬ��
	}

    /**
     * ȥ���ַ��������ض����ַ���
     * @param allStr ԭ�ַ���
     * @param removeStr  ��Ҫȥ�����ַ���
     * @return  ȥ���ض��ַ�������ַ���
     */
	public String  removeStr(String strAll,String strRemove){
		String strNew = "";
//		boolean flag = false;
		
		if(strAll.indexOf(strRemove)<0){
			PubTools.log.error("�Բ���Ҫȥ�����ַ���"+strRemove+"����ԭ�ַ���"+strAll+"��");
		}else{
		int i = strAll.indexOf(strRemove);
		strNew = strAll.substring(0, i)+strAll.substring(i+strRemove.length(), strAll.length());
		}
	 	return strNew;
	}
	
	/**20121109 xq add ��Ҫ�����豸����ATMV��ȡ�ļ�ǰ�ж�һ�� ����豸�汾���ڵ���5340�Ļ����������ATMV��ȡ
	 * ��ѯ�豸��Ӧ�豸�汾��Ϣ������豸�汾����005.003.004.000���䷵�ط��뵽MAP��
	 * @return �汾����005.003.004.000���豸��Ϣ
	 */
	public Map queryDevVersionInfo(){
		Map mQueryMap = new HashMap();
		String sQuerySQL = "SELECT dev_code,dev_ver FROM dev_smsg WHERE dev_code in("+this.sDevCodeStr+") AND dev_ver>='"+sDevVer+"'";
		PubTools.log.debug("��ѯ�豸��ǰ�汾SQLΪ:"+sQuerySQL);
		
		try {
			List lDevVerInfoList = JdbcFactory.queryForList( sQuerySQL);
			if (lDevVerInfoList != null && lDevVerInfoList.size() > 0) {
				Object[] resultArray = lDevVerInfoList.toArray();
				for (int iIndex = 0; iIndex < resultArray.length; iIndex++) {
					String sDevCode = ((HashMap) resultArray[iIndex]).get("dev_code")==null?"":((HashMap) resultArray[iIndex]).get("dev_code").toString().trim();
					String sDevVer = ((HashMap) resultArray[iIndex]).get("dev_ver")==null?"":((HashMap) resultArray[iIndex]).get("dev_ver").toString().trim();
					mQueryMap.put(sDevCode, sDevVer);
				}
			}
		} catch (Exception e) {
			PubTools.log.error("��ѯ�豸�汾��Ϣ�쳣���豸��Ϣ�б�["+sDevCodeStr+"],"+e);
		}
		return mQueryMap;
	}
	
	/**
	 * 20121109 xq add ��������Ҫ���������ATMV��ȡ�ļ�ǰ�� ���˵�����Ҫ������ATMV��ȡ��ˮ���豸
	 * @param lDevTrmList ����ǰ�б�
	 * @return ���˺��豸��Ϣ
	 */
	public String changeLeftDevList(List lDevTrmList){
		PubTools.log.debug("��ʼ��ȡ�������ATMV��ȡ��ˮ���豸��Ϣ!");
		String sReturnStr = "";
	    if(lDevTrmList==null||lDevTrmList.size()==0){
	    	PubTools.log.info("���β�ѯ�����豸������Ҫ������ATMV����ȡ�ļ�!");
	    	return "";
	    }
		int iCountSize = lDevTrmList.size();
		Map hmTermInfo = null;
		for (int iIndex = 0; iIndex < iCountSize; iIndex++) {
			hmTermInfo = (HashMap) lDevTrmList.get(iIndex);
			String sTermCode = hmTermInfo.get("termCode")==null?"":hmTermInfo.get("termCode").toString().trim();
			String sFileTime = hmTermInfo.get("fileTime")==null?"":hmTermInfo.get("fileTime").toString().trim();
			sReturnStr =  sReturnStr + sTermCode+"@"+sFileTime+"#";
		}
		  PubTools.log.info("��ȡ������ATMV��ȡ��ˮ���豸�б���ϢΪ["+sReturnStr+"]");
		  return sReturnStr;
	   }

	
	public static void main(String[] args) {

		// ��ATMV���͵�����ˮ�б�������
		ServiceParameter.init(); // ��ʼ�������ļ�·��
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);
		WebRequestJournalMsgHandler webRequestHandler = new WebRequestJournalMsgHandler();
		webRequestHandler.requireDownloadJournal();
	}



}
