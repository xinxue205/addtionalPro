/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import server.server.socket.JournalServerParams;
import server.server.socket.ServiceParameter;
import server.server.socket.SocketUtil;
import server.server.socket.bussiness.msg.DownloadJournalDataTransMsg;
import server.server.socket.bussiness.msg.DownloadJournalFileResponseMsg;
import server.server.socket.bussiness.msg.DownloadJournalResponseMsg;
import server.server.socket.bussiness.msg.GetTransCodeMsg;
import server.server.socket.bussiness.msg.JournalTransCodeMsg;
import server.server.socket.tool.FileOperationStruct;
import server.server.socket.tool.FileTools;
import server.util.Dboperator;
import server.util.PubTools;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:46:22
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyDownloadJournalHandler {

	private String branchJournalServerIP = "";

	private String faultTermList = "";

	private String respCode = "";

	private String tempFilePath = "";
	
	private String sJournalList = "";

	private List alTermList = new ArrayList(); // �������صĵ�����ˮ�б�
	
	private SqlMapClient sqlMapper = null; // ���ݿ����

	private boolean bTransFlag = false; // ���ױ�־

	public String getFaultTermList() {
		return faultTermList;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getTempFilePath() {
		return tempFilePath;
	}

	// �����豸����б�
	public void setJournalList(String sJournalList) {
		this.sJournalList = sJournalList;
		alTermList.clear() ;
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			HashMap hmTmpTermList = new HashMap();
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			hmTmpTermList.put("termCode", sArrTermInfo[0]);
			hmTmpTermList.put("fileTime", sArrTermInfo[1]);
			alTermList.add(hmTmpTermList);
		}
	}

	// ���÷��е�����ˮ��������ַ
	public void setBranchJournalServerIP(String branchJournalServerIP) {
		this.branchJournalServerIP = branchJournalServerIP;
	}

	/**
	 * ��ѯ������ˮ�б��Ƿ����
	 * 
	 * @return �Ƿ���� ���� true ������ false
	 * @throws Exception
	 */
	public boolean requireDownloadJournal() throws Exception {
		SocketUtil socketUtil = null;
		boolean successGetFile = false;
		try {
			PubTools.log.info("��ʼ�����ATMV��ȡ������ˮ,��ȡ������ˮ�б�[" + this.sJournalList + "] ����IP[" + this.branchJournalServerIP.trim() + "] �豸�˿�["
					+ JournalServerParams.BranchJournalServerPort + "]");
//
//			PubTools.log.info("����IP[" + this.branchJournalServerIP.trim() + "] �豸�˿�["
//					+ JournalServerParams.BranchJournalServerPort + "]");
			
			socketUtil = new SocketUtil(this.branchJournalServerIP.trim(), JournalServerParams.BranchJournalServerPort,10000);//20121108 xq ���������ATMV��ȡ������ˮ��ʱʱ��

			if (socketUtil.createConnection()) {
				String requestMsg = DownloadJournalRequestMsgHandler.getDownloadJournalRequestMsg(JournalTransCodeMsg
						.getDownloadJournalRequestMsg(), this.sJournalList);
				// ����������
				socketUtil.sendMessage(requestMsg);
				PubTools.log.info("�������ݱ��ĵ�����:[" + this.branchJournalServerIP.trim() + "�ɹ�");
				// ��ȡ��Ӧ����
				String responseMsg = socketUtil.getMessage();
				PubTools.log.debug(responseMsg);
				// �����Ӧ����Ϊ�գ���ֱ�ӷ���ʧ��
				if (responseMsg.equals("") || responseMsg == null)
					return false;
				PubTools.log.info("�ӷ���:[" + this.branchJournalServerIP + "]�������ݳɹ�");
				int transCode = GetTransCodeMsg.getTransCode(responseMsg);
				if (transCode == JournalTransCodeMsg.DownloadJournalResponseMsg) {
					PubTools.log.debug("���յ����ж������ļ�����Ӧ����");
					DownloadJournalResponseMsg downloadJournalResponseMsg = new DownloadJournalResponseMsg();
					downloadJournalResponseMsg.unpackMsg(responseMsg);
					this.respCode = downloadJournalResponseMsg.getRespCode(); // ��ȡ��Ӧ��
					this.bTransFlag = downloadJournalResponseMsg.getTransFlag();// �����־
					if (!this.bTransFlag) {
						this.faultTermList = downloadJournalResponseMsg.getFaultTermList();// ʧ����Ϣ�б�
					} else
						successGetFile = true;
				}
			} else {
				PubTools.log.info("����ʧ��");
			}
		} finally {
			if (socketUtil != null)
				socketUtil.closeConnection();
		}
		return successGetFile;
	}

	/**
	 * ���ص�����ˮ
	 * 
	 * @return �Ƿ����سɹ�
	 * @throws Exception
	 */
	public boolean downloadJournal() throws Exception {
		SocketUtil socketUtil = null;
		boolean successGetFile = false;
		int iMaxTry = 3 ;   //�ļ�����ʧ������Դ���
		
		for (int iIndex = 0; iIndex < this.alTermList.size(); iIndex++) {
			
			HashMap hmTermInfo = (HashMap) alTermList.get(iIndex);    //������ˮ������Ϣ
			String sTermCode = (String) hmTermInfo.get("termCode");   //��ȡ�豸��
			String sFileTime = (String) hmTermInfo.get("fileTime"); 		//��ȡ������ˮʱ��
			
			try {
				//----------------------20110415 xq �޸� ���ڸ��������ļ���Žṹ------------------------// 
				// ��ȡ�ļ��Ĵ��·��
				this.tempFilePath = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sTermCode, sFileTime);
//				System.out.println("-============----------+++++++++:"+tempFilePath);
				// �����ļ�����Ŀ¼
				File workingFile = new File(this.tempFilePath);
				// �ж��ļ����Ƿ����,�������,�򲻴����ļ���
				if (!workingFile.exists()) {
					if (!workingFile.mkdirs()) {
						// �����ļ���ʧ��
						return successGetFile;
					}
				}
				
				//20120319 xq �����V��ȡ�ļ�ʱ�����ж�
//				 if(checkLocalFileIsExist(tempFilePath,sTermCode, sFileTime)){
//					 return true;
//				 }
				
				PubTools.log.info("��ȡ������ˮ�豸��[" + sTermCode + "] ʱ��[" + sFileTime + "]");

				PubTools.log.debug("����IP[" + this.branchJournalServerIP.trim() + "] �豸�˿�["
						+ JournalServerParams.BranchJournalServerPort + "]");
				socketUtil = new SocketUtil(this.branchJournalServerIP.trim(),JournalServerParams.BranchJournalServerPort,10000);//20121108 xq ���������ATMV��ȡ�ļ�ʱ���ӳ�ʱʱ���ж�
				
				int iTryCount = 0 ;   //�����ļ����س��Դ���  

				if (socketUtil.createConnection()) {
					String requestMsg = DownloadJournalFileMsgHandler.getDownloadJournalFileRequestMsg(
							JournalTransCodeMsg.getDownloadJournalFileRequestMsg(), sTermCode, sFileTime);
					// ����������
					socketUtil.sendMessage(requestMsg);
					PubTools.log.debug("�������ݱ��ĵ�����:[" + this.branchJournalServerIP.trim() + "]�ɹ�");
					FileOperationStruct fileOperationStruct = new FileOperationStruct();
					boolean isContinue = true;
					while (isContinue) {
						// ��ȡ��Ӧ����
						String responseMsg = socketUtil.getMessage();
						PubTools.log.debug(responseMsg);
						// �����Ӧ����Ϊ�գ���ֱ�ӷ���ʧ��
						if (responseMsg == "" || responseMsg == null)
							break;
						PubTools.log.debug("�ӷ���:[" + this.branchJournalServerIP + "]�������ݳɹ�");
						int transCode = GetTransCodeMsg.getTransCode(responseMsg);
						switch (transCode) {
							case JournalTransCodeMsg.DownloadJournalFileResponseMsg : {
								PubTools.log.debug("���յ����ж������ļ�����Ӧ����");
								DownloadJournalFileResponseMsg downloadJournalFileResponseMsg = new DownloadJournalFileResponseMsg();
								downloadJournalFileResponseMsg.unpackMsg(responseMsg);
								this.bTransFlag = downloadJournalFileResponseMsg.getTransFlag();// �����־
								if (this.bTransFlag) {
									String tempFileName = downloadJournalFileResponseMsg.getFileName();// ��ʱ�ļ���
									String tempFileMD5 = downloadJournalFileResponseMsg.getFileMD5();// ��ʱ�ļ�MD5ֵ
									String localTempFileName = this.tempFilePath
											+ File.separator
											
//											+ tempFileName.substring(tempFileName.lastIndexOf(File.separatorChar) + 1,//����·��
											
											+ tempFileName.substring(tempFileName.lastIndexOf("/") + 1,//��ס�Լ��޸�
													tempFileName.length());
									PubTools.log.debug("��ʱ�ļ���Ϊ: " + localTempFileName);
									
									fileOperationStruct.setFileName(localTempFileName);
									fileOperationStruct.setFileMD5(tempFileMD5);
									fileOperationStruct.setFileOffSet(0);
									String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
											.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
													.getDownloadJournalDataRequestMsg(), tempFileName,
													fileOperationStruct.getFileOffSet());
									// ����������
									socketUtil.sendMessage(downloadJournalDataRequestMsg);
								} else {
									this.respCode = downloadJournalFileResponseMsg.getRespCode(); // ��ȡ��Ӧ��
									this.faultTermList = downloadJournalFileResponseMsg.getErrorMsg();// ʧ����Ϣ�б�
									PubTools.log.warn("��ȡ������ˮʧ��: �豸��" + sTermCode + " ʱ��:" + 
											sFileTime + "respCode" + this.respCode + " ʧ����Ϣ�б�:" + this.faultTermList);
									isContinue = false; // ����ѭ��
								}
								break;
							}
							case JournalTransCodeMsg.DownloadJournalDataTransMsg : {
								PubTools.log.debug("���յ����ж��ļ��ļ��Ĵ���ı���");
								DownloadJournalDataTransMsg downloadJournalDataTransMsg = new DownloadJournalDataTransMsg();
								downloadJournalDataTransMsg.unpackMsg(responseMsg);
								boolean isLastBlock = downloadJournalDataTransMsg.getIsLastBlock();
								long dataSize = downloadJournalDataTransMsg.getDataSize();
								String dataBlock = downloadJournalDataTransMsg.getDataBlock();
								long lFileOffset = fileOperationStruct.getFileOffSet();
								byte[] byteBlock = PubTools.ConvertBase64StringToByte(dataBlock);
								PubTools.log.debug("isLastBlock :[" + isLastBlock + "]");
								PubTools.log.debug("dataSize :[" + dataSize + "]");
								PubTools.log.debug("lFileOffset :[" + lFileOffset + "]");
								fileOperationStruct.writeDataToFile(byteBlock, dataSize, lFileOffset);
								fileOperationStruct.closeFileOutputStream();
								if (isLastBlock) {
									// ��������һ�����ݣ�������ļ�MD5��֤
									String checkFileMD5 = new FileTools().getFileMD5(fileOperationStruct.getFileName());
									if (!checkFileMD5.equalsIgnoreCase(fileOperationStruct.getFileMD5())) {
										PubTools.log.warn("�ļ�MD5У��ʧ��: �豸��" + sTermCode + " ʱ��:" + sFileTime + "ԭMD5"
												+ fileOperationStruct.getFileMD5() + " �ļ�MD5:" + checkFileMD5);
										iTryCount++;
										if (iTryCount < iMaxTry) {
											PubTools.log.warn("��" + iTryCount + "�γ��������ļ�:" + fileOperationStruct.getFileName());
											// ���MD5��֤��һ�£������������ļ�
											fileOperationStruct.setFileOffSet(0);
											String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
													.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
															.getDownloadJournalDataRequestMsg(), fileOperationStruct.getFileName(),
															fileOperationStruct.getFileOffSet());
											// ����������
											socketUtil.sendMessage(downloadJournalDataRequestMsg);
										}else{
											PubTools.log.warn("�����ļ�ʧ�� MD5У��ʧ�� �ļ���:[" + fileOperationStruct.getFileName() + "]");
											isContinue = false; // ����ѭ��
										}
									} else {
										//-------------------------20110415�����޸� �޸��ļ��нṹ ����V���͵��ļ��Ȳ���ѹ ���ļ����ظ��������ʱ���ٽ�ѹ----------------//
										// MD5��֤ͨ�������ѹ���ļ�
										PubTools.log.debug("�ļ�:"+fileOperationStruct.getFileName()+"MD5��֤ͨ������ѹ�ļ�") ;
//										String tempFileName = fileOperationStruct.getFileName();  //���ص��Ĳ���
//										String[] unZipFileName = new UnZip().unzipFile(tempFileName, this.tempFilePath) ;  //��ѹ�ļ�����ʱĿ¼
//										new File(tempFileName).delete();   //�����ص��ļ�ɾ��
//										tempFileName =  this.tempFilePath	+ File.separatorChar + sTermCode + "_" + sFileTime + ".j"; 
//										if (!new File(this.tempFilePath + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName))){ //��������ѹ�������ļ�
//											successGetFile = false; // ��ʶ�ļ����سɹ�
//										}else{
//											successGetFile = true; // ��ʶ�ļ����سɹ�
//										}
										successGetFile = true;
										isContinue = false; // ����ѭ��
//										PubTools.log.info("�����ļ��ɹ� �ļ�·��:[" + tempFileName + "]");
									}
								} else {
									// ����������һ�����ݣ�������������ݴ���������
									long tempFileOffset = fileOperationStruct.getFileOffSet() + dataSize;
									fileOperationStruct.setFileOffSet(tempFileOffset);
									String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
											.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
													.getDownloadJournalDataRequestMsg(), fileOperationStruct
													.getFileName(), fileOperationStruct.getFileOffSet());
									// ����������
									socketUtil.sendMessage(downloadJournalDataRequestMsg);
								}
								break;
							}
						}
					}
				} else {
					PubTools.log.warn("���ӷ���:["+this.branchJournalServerIP.trim()+"]ʧ��");
				}
			} finally {
				if (socketUtil != null)
					socketUtil.closeConnection();
			}
		}
		return successGetFile;
	}
	
	/**2011-04-15 �������
	 * �����޸���ˮ���Ŀ¼�ṹ  �����ļ��нṹΪ
	 * @param sDevCode �豸�� sFindDate ������ˮ����
	 * @return �ļ�·��
	 */
	public String getRealFilePath(String sDevCode,String sFindDate){
		String sPath = "";
		sqlMapper = Dboperator.getSqlMapper();
		String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
		List mBranchList = new ArrayList();
		try {
			String sBranchCode = "";
			mBranchList = sqlMapper.queryForList("select", sQuerySQL);
			if (mBranchList != null && mBranchList.size() > 0) {
				Object[] resultArray = mBranchList.toArray();
				sBranchCode = ((HashMap) resultArray[0]).get("dev_branch1")==null?"":((HashMap) resultArray[0]).get("dev_branch1").toString().trim();	
			}else{
				return "";
			}
			
			if(sFindDate.length()!=8){
				return "";
			}
			String sYear = sFindDate.substring(0, 4);
			String sMonth = sFindDate.substring(4, 6);
			String sDay = sFindDate.substring(6, 8);
			sPath = sBranchCode+File.separator+sYear+File.separator+sMonth+File.separator+sDay;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
		return sPath;
	}
	
    /**
     * 20111220 xq  ����
	 * �жϱ����ļ��Ƿ����
     * @return true ���� false ������
     */
	public boolean checkLocalFileIsExist(String sFilePath,String sDevCode,String sFileTime){
		if(!checkFileIsCurrDateFile(sDevCode,sFileTime)){
			return false;
		}
		try{
			String sRealFilePath = sFilePath + File.separator +sDevCode+"_"+sFileTime+".zip";
			File sLocalFile = new File(sRealFilePath);
			if(!sLocalFile.exists()){
				return false;
			}
		   return true;
		}catch(Exception e){
			PubTools.log.error("��ȡ�����ļ��Ƿ�����쳣:",e);
			return false;
		}
	}
	
	/**20111220 xq
	 * ��鴫����ļ��Ƿ��ǵ���Ļ�����ǰһ��� ����ǵĻ� ���ļ��������V��ȡ
	 * @param sDevCode �豸��
	 * @param sFileTime
	 * @return false ���㵱ǰ���� true ����������
	 */
	public boolean checkFileIsCurrDateFile(String sDevCode,String sFileTime){
		try{
		   String sCurrDate = PubTools.getSystemDate();
	       if(sCurrDate.equals(sFileTime)){
	    	   return false;
	       }
	       int iDiffDay = PubTools.diffDate(sFileTime, sCurrDate);
	       if(iDiffDay==1||iDiffDay==0){
	    	   return false;
	       }
		}catch(Exception e){
			PubTools.log.error("��ȡʱ����Ϣ�쳣",e);
			return false;
		}
	   PubTools.log.debug("����Ƿ�ǰ����");
       return true;
	}
	

	public static void main(String[] args) {
		ServiceParameter.init(); // ��ʼ�������ļ�·��
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);
		ImmediatelyDownloadJournalHandler testDownLoad = new ImmediatelyDownloadJournalHandler();
		testDownLoad.setBranchJournalServerIP("128.128.81.56");
		testDownLoad.setJournalList("B00035@20091104");
		try {
			testDownLoad.downloadJournal() ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
