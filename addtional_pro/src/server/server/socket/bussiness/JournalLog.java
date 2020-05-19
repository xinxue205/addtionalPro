/**
 * 
 */
package server.server.socket.bussiness;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import server.server.socket.JournalServerParams;
import server.server.socket.ServiceParameter;
import server.server.socket.bussiness.handler.ImmediatelyDownloadJournalHandler;
import server.util.JdbcFactory;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����11:40:26
 * @Description
 * @version 1.0 Shawn create
 */
public class JournalLog {
	
	//private SqlMapClient sqlMapper;
    private static final String sDevVer = "005.003.004.000"; //20121109 xq add �������ϴ�״̬���ĺ����������ȡ�ļ�ǰ �жϸ��豸�汾�Ƿ���ڵ���5340 ������������������ATMV��ȡ
	/**
	 * �Ǽ�ATM������ˮ�ϴ�״̬
	 * 
	 * @param termCode
	 * @param fileTime
	 * @param atmFileName
	 * @param transResult
	 * @param transTime
	 * @return
	 */
	public boolean logDevUploadStatus(String termCode, String fileTime, String atmFileName,
			boolean transResult, String transTime) {
		PubTools.log.info("��ˮ�ϴ���¼ �豸���["+termCode+"] ʱ��[" + fileTime + "] �ն˵�����ˮ�ļ���[" 
				+ atmFileName + "] ������[" + transResult + "] �ϴ�ʱ��[" + transTime + "]" ) ;
		
		
		if(atmFileName.indexOf("TC")>0||atmFileName.indexOf("xml")>0){
			return true;
		}
	
		int transresult = 1 ;
		if ( transResult )
			transresult = 0 ;
		
		
		//sqlMapper = Dboperator.getSqlMapper();
		
		//20121109 xq add �������ATMV��ȡ�ļ�ǰ���жϸ��豸�汾�Ƿ����5340 ����豸�汾����5340 �������ATMV��ȡ
		String sDevVer = this.getDevVer(termCode);
		if(sDevVer==null||"".equals(sDevVer)){
			
			//20111220 xq ���������ȡ����V��ˮ  20120319 xq �޸��ж��ļ��Ƿ���C����ȡ����
			
			if(fileTime!=null&&!"".equals(fileTime)&&termCode!=null&&!"".equals(termCode)&&transResult){
				if(checkJournalLogIsExist(termCode,fileTime)){
				    if(!checkJournalFileIsInLocal(termCode,fileTime)){
				    	PubTools.log.info("��ʼ��VH�����V��ȡ�豸["+termCode+"]["+transTime+"]����ˮ!");
						sendFileToVH(termCode,fileTime);
						PubTools.log.info("��VH�����V��ȡ�豸["+termCode+"]["+transTime+"]����ˮ���");
					}
				}else{
					PubTools.log.info("��ʼ��VH�����V��ȡ�豸["+termCode+"]["+transTime+"]����ˮ!");
					sendFileToVH(termCode,fileTime);
					PubTools.log.info("��VH�����V��ȡ�豸["+termCode+"]["+transTime+"]����ˮ���");
	
				}
			}
		}
		if (fileTime.equals(PubTools.getSystemDate())) {
			return true;
		}
		
		String sRunFlag = getDevRunFlag(termCode);
//		String querySql = "SELECT COUNT(*) AS COUNTS FROM JOURNAL_UPLOADLOG WHERE FILETIME='"
//				+ fileTime + "' AND DEVCODE='" + termCode + "'";

		String insertSql = "INSERT INTO JOURNAL_UPLOADLOG(ATMFILENAME,"
				+ "TRANSTIME,TRANSRESULT,FILETIME,DEVCODE,RUN_FLAG)" + " VALUES('" + atmFileName + "','"
				+ transTime + "','" + transresult + "','" + fileTime + "','" + termCode + "','"+sRunFlag+"')";

		String updateSql = "UPDATE  JOURNAL_UPLOADLOG	SET ATMFILENAME='" + atmFileName
				+ "',TRANSTIME='" + transTime + "',TRANSRESULT='" + transresult
				+ "',RUN_FLAG='"+sRunFlag+"' WHERE FILETIME='" + fileTime + "' AND DEVCODE='" + termCode + "'";
//		try {
//			List resultJobStatusList = sqlMapper.queryForList("select", querySql);
//			int recordCount = -1;
//			if (resultJobStatusList != null && resultJobStatusList.size() > 0) {
//				Object[] resultArray = resultJobStatusList.toArray();
//				if (resultArray.length > 0) {
//					if ( ((HashMap) resultArray[0]).get("counts") != null)
//						recordCount = Integer.valueOf(((HashMap) resultArray[0]).get("counts").toString()).intValue();
//				} else
//					return false;
//			} else
//				return false;
//			if (recordCount > 0) {
//				sqlMapper.update("update", updateSql);
//			} else {
//				sqlMapper.insert("insert", insertSql);
//			}
//		System.out.println(insertSql);
		try {
			boolean bResult = JdbcFactory.update(updateSql);
			if (!bResult) {
				JdbcFactory.update(insertSql);
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			PubTools.log.error("�����¼ʧ��" + "|" + e.toString());
		}
		return true;
	}
	
	/**xq
	 * ��ȡ��ǰ�豸����״̬
	 * @param sDevCode
	 * @return
	 */
	public String getDevRunFlag(String sDevCode){
		String sRunFlag = "";
		String sQuerySQL = "select run_flag from dev_bmsg where dev_code='"+sDevCode+"'";
//		System.out.println(sQuerySQL);
		try{
		  Map mDevMap = new HashMap();
		  List lDevList =  JdbcFactory.queryForList(sQuerySQL);
		  if(lDevList==null||lDevList.size()==0){
			  return "";
		  }
		  mDevMap = (Map)lDevList.get(0);
		  if(mDevMap==null||mDevMap.size()==0){
			  return "";
		  }
		   sRunFlag = mDevMap.get("run_flag")==null?"":mDevMap.get("run_flag").toString().trim();
		}catch (Exception e) {
			PubTools.log.error("��ѯ�豸["+sDevCode+"]����״̬�쳣��"  + e.toString());
		}
		return sRunFlag;
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
	       if(iDiffDay<=30){
	    	   return false;
	       }
		}catch(Exception e){
			PubTools.log.error("��ȡʱ����Ϣ�쳣",e);
			return false;
		}
	   PubTools.log.debug("���["+sFileTime+"]���Ƿ�ǰ�������");
       return true;
	}
	
	/**2011-12-20 xq 
	 * �����ˮ�ļ���VH�����Ƿ��Ѿ�����
	 * @param sDevCode
	 * @param sFileTime
	 * @return true ���� false ������
	 */
	public boolean checkJournalFileIsInLocal(String sDevCode,String sFileTime){
		try{
	       PubTools.log.debug("[��VH��V��ȡ]����Ƿ񱾵��ļ�!");
		   String sFileName = sDevCode.trim()+"_"+sFileTime.trim()+".zip";
		   String sFilePath = getRealFilePath(sDevCode,sFileTime);
		   String sFileRealPath =  JournalServerParams.JournalViewerTempFilePath+File.separator+sFilePath+File.separator+sFileName;
		   File sLocalJournalFile = new File(sFileRealPath);
		   if(sLocalJournalFile.exists()){
			   PubTools.log.debug("[��VH��V��ȡ]��ȡ�ļ�·��Ϊ["+sFileRealPath+"]���ļ�����");
			   return true;
		   }
		  PubTools.log.debug("[��VH��V��ȡ]��ȡ�ļ�·��Ϊ["+sFileRealPath+"]���ļ�������");
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * 20111220 xq �жϵ�����ˮ�ϴ���־�����Ƿ��и��ļ��ϴ��ļ�¼
	 * @return true ���� false ������
	 */
	public boolean checkJournalLogIsExist(String sDevCode,String sFileTime){
		PubTools.log.debug("������ݿ��Ƿ����!");
		//---20120319 xq �޸Ĵ�������------
		String sCurrDate =   PubTools.getSystemDate();
		if(sCurrDate.equals(sFileTime)){
			return false;
		}
		//------end----------------
		
		String sQuerySQL = "select atmfilename from journal_uploadlog where devcode='"+sDevCode+"' and filetime='"+sFileTime+"' and transresult='0' and atmfilename like '%.J'";
		List lResultList = new ArrayList();
		try {
			lResultList = JdbcFactory.queryForList( sQuerySQL);
			if (lResultList != null && lResultList.size() > 0) {
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			PubTools.log.error("��ȡ�ϴ��ļ��Ƿ�����쳣:"+e.toString());
			return false;
		}
	}
	
	/**
	 * ������V��ˮ���͵�����
	 */
	public void sendFileToVH(String sDevCode,String sFileTime){
	
		PubTools.log.debug("��ʼ�����豸��["+sDevCode+"]["+sFileTime+"]���ڷ���V��ˮ!--------------");
		String sJournalList = sDevCode.trim()+"@"+sFileTime+"#";
		try {
			String sBranchIp = "";
			String sQuerySql = "select branch_node from branchjournal_msg where branch_code "
				            + " in (select dev_branch1 from dev_bmsg where dev_code='" + sDevCode + "')";
			PubTools.log.debug("��ȡSQL:"+sQuerySql);
			List lBranchInfoList  = JdbcFactory.queryForList( sQuerySql);
			if(lBranchInfoList==null||lBranchInfoList.size()>1||lBranchInfoList.size()==0){
				PubTools.log.error("��ȡ�豸��["+sDevCode+"]���ڷ���IP��ַΪ�ջ�Ψһ!");
				return ;
			}
			
			Object[] resultArray = lBranchInfoList.toArray();
			sBranchIp = ((HashMap) resultArray[0]).get("branch_node")==null?"":((HashMap) resultArray[0]).get("branch_node").toString().trim();	

            if(sBranchIp==null||"".equals(sBranchIp)){
				PubTools.log.error("��ȡ�豸��["+sDevCode+"]���ڷ���IP��ַΪ��!");
            	return ;
            }
			// ��ATMV���͵�����ˮ�б�������
			ImmediatelyDownloadJournalHandler immediatelyDownloadJournalHandler = new ImmediatelyDownloadJournalHandler();
			immediatelyDownloadJournalHandler.setJournalList(sJournalList);
			immediatelyDownloadJournalHandler.setBranchJournalServerIP(sBranchIp);
			if(immediatelyDownloadJournalHandler.requireDownloadJournal()){//����V���ͱ��� ȷ���ļ��Ƿ���ڣ�������������ϴ�������
			    PubTools.log.debug("��ʼ����з���������-----------------------");
				immediatelyDownloadJournalHandler.downloadJournal();
			}
		} catch (Exception e) {
			PubTools.log.error("�����ATMV�����豸��["+sDevCode+"]�ļ�ʱ��["+sFileTime+"]�쳣:"+e.toString());
		}
		PubTools.log.debug("�����豸��["+sDevCode+"]["+sFileTime+"]���ڷ���V��ˮ���!--------");
	}
	
	
	/**2011-12-20 �������
	 * �����޸���ˮ���Ŀ¼�ṹ  �����ļ��нṹΪ
	 * @param sDevCode �豸�� sFindDate ������ˮ����
	 * @return �ļ�·��
	 */
	public String getRealFilePath(String sDevCode,String sFindDate){
		String sPath = "";
		String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
		List mBranchList = new ArrayList();
		try {
			String sBranchCode = "";
			mBranchList = JdbcFactory.queryForList( sQuerySQL);
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
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sPath;
	}
	
	
	/**xq 20121109 add 
	 * ��ȡ��ǰ�豸���а汾�Ƿ����5340 
	 * @param sDevCode �豸��
	 * @return �汾��
	 */
	public String getDevVer(String sDevCode){
		String sReturnDevVer = "";
		String sQuerySQL = "select dev_ver from dev_smsg where dev_code='"+sDevCode+"' and dev_ver >='"+sDevVer+"'";
//		System.out.println(sQuerySQL);
		try{
		  Map mDevMap = new HashMap();
		  List lDevList =  JdbcFactory.queryForList( sQuerySQL);
		  if(lDevList==null||lDevList.size()==0){
			  PubTools.log.debug("��ȡ�豸��["+sDevCode+"]�汾Ϊ["+sReturnDevVer+"]");
			  return "";
		  }
		  mDevMap = (Map)lDevList.get(0);
		  if(mDevMap==null||mDevMap.size()==0){
			  PubTools.log.debug("��ȡ�豸��["+sDevCode+"]�汾Ϊ["+sReturnDevVer+"]");
			  return "";
		  }
		  sReturnDevVer = mDevMap.get("dev_ver")==null?"":mDevMap.get("dev_ver").toString().trim();
		}catch (Exception e) {
			PubTools.log.error("��ѯ�豸["+sDevCode+"]����״̬�쳣��" + "|" , e);
		}
		PubTools.log.debug("��ȡ�豸��["+sDevCode+"]�汾Ϊ["+sReturnDevVer+"]");
		return sReturnDevVer;
	}
	
	public static void main(String args[]){
		ServiceParameter.init(); //��ʼ�������ļ�·��
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);

		 JournalLog jl = new JournalLog();
	
		 boolean flag = jl.logDevUploadStatus("440600300169", "20111220", "C:/WSAP/Data/440600300157_20110725.J", true, "20111220180102");
	     System.out.println(flag);
	}
}
