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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:40:26
 * @Description
 * @version 1.0 Shawn create
 */
public class JournalLog {
	
	//private SqlMapClient sqlMapper;
    private static final String sDevVer = "005.003.004.000"; //20121109 xq add 用于在上传状态报文后，需向分行提取文件前 判断该设备版本是否大于等于5340 如果条件成立则不向分行ATMV提取
	/**
	 * 登记ATM电子流水上传状态
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
		PubTools.log.info("流水上传记录 设备编号["+termCode+"] 时间[" + fileTime + "] 终端电子流水文件名[" 
				+ atmFileName + "] 处理结果[" + transResult + "] 上传时间[" + transTime + "]" ) ;
		
		
		if(atmFileName.indexOf("TC")>0||atmFileName.indexOf("xml")>0){
			return true;
		}
	
		int transresult = 1 ;
		if ( transResult )
			transresult = 0 ;
		
		
		//sqlMapper = Dboperator.getSqlMapper();
		
		//20121109 xq add 在向分行ATMV提取文件前先判断该设备版本是否大于5340 如果设备版本大于5340 则不向分行ATMV提取
		String sDevVer = this.getDevVer(termCode);
		if(sDevVer==null||"".equals(sDevVer)){
			
			//20111220 xq 添加用于提取分行V流水  20120319 xq 修改判断文件是否向C端提取流程
			
			if(fileTime!=null&&!"".equals(fileTime)&&termCode!=null&&!"".equals(termCode)&&transResult){
				if(checkJournalLogIsExist(termCode,fileTime)){
				    if(!checkJournalFileIsInLocal(termCode,fileTime)){
				    	PubTools.log.info("开始从VH向分行V提取设备["+termCode+"]["+transTime+"]日流水!");
						sendFileToVH(termCode,fileTime);
						PubTools.log.info("从VH向分行V提取设备["+termCode+"]["+transTime+"]日流水完毕");
					}
				}else{
					PubTools.log.info("开始从VH向分行V提取设备["+termCode+"]["+transTime+"]日流水!");
					sendFileToVH(termCode,fileTime);
					PubTools.log.info("从VH向分行V提取设备["+termCode+"]["+transTime+"]日流水完毕");
	
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
			PubTools.log.error("插入记录失败" + "|" + e.toString());
		}
		return true;
	}
	
	/**xq
	 * 获取当前设备运行状态
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
			PubTools.log.error("查询设备["+sDevCode+"]运行状态异常："  + e.toString());
		}
		return sRunFlag;
	}
	
	/**20111220 xq
	 * 检查传入的文件是否是当天的或者是前一天的 如果是的话 该文件需向分行V提取
	 * @param sDevCode 设备号
	 * @param sFileTime
	 * @return false 满足当前条件 true 不满足条件
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
			PubTools.log.error("获取时间信息异常",e);
			return false;
		}
	   PubTools.log.debug("检查["+sFileTime+"]日是否当前日期完毕");
       return true;
	}
	
	/**2011-12-20 xq 
	 * 检查流水文件在VH本地是否已经存在
	 * @param sDevCode
	 * @param sFileTime
	 * @return true 存在 false 不存在
	 */
	public boolean checkJournalFileIsInLocal(String sDevCode,String sFileTime){
		try{
	       PubTools.log.debug("[从VH向V提取]检查是否本地文件!");
		   String sFileName = sDevCode.trim()+"_"+sFileTime.trim()+".zip";
		   String sFilePath = getRealFilePath(sDevCode,sFileTime);
		   String sFileRealPath =  JournalServerParams.JournalViewerTempFilePath+File.separator+sFilePath+File.separator+sFileName;
		   File sLocalJournalFile = new File(sFileRealPath);
		   if(sLocalJournalFile.exists()){
			   PubTools.log.debug("[从VH向V提取]获取文件路径为["+sFileRealPath+"]的文件存在");
			   return true;
		   }
		  PubTools.log.debug("[从VH向V提取]获取文件路径为["+sFileRealPath+"]的文件不存在");
		}catch(Exception e){
			return false;
		}
		return false;
	}
	
	/**
	 * 20111220 xq 判断电子流水上传日志表中是否有该文件上传的记录
	 * @return true 存在 false 不存在
	 */
	public boolean checkJournalLogIsExist(String sDevCode,String sFileTime){
		PubTools.log.debug("检查数据库是否存在!");
		//---20120319 xq 修改处理流程------
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
			PubTools.log.error("获取上传文件是否存在异常:"+e.toString());
			return false;
		}
	}
	
	/**
	 * 将分行V流水发送到总行
	 */
	public void sendFileToVH(String sDevCode,String sFileTime){
	
		PubTools.log.debug("开始下载设备号["+sDevCode+"]["+sFileTime+"]日期分行V流水!--------------");
		String sJournalList = sDevCode.trim()+"@"+sFileTime+"#";
		try {
			String sBranchIp = "";
			String sQuerySql = "select branch_node from branchjournal_msg where branch_code "
				            + " in (select dev_branch1 from dev_bmsg where dev_code='" + sDevCode + "')";
			PubTools.log.debug("获取SQL:"+sQuerySql);
			List lBranchInfoList  = JdbcFactory.queryForList( sQuerySql);
			if(lBranchInfoList==null||lBranchInfoList.size()>1||lBranchInfoList.size()==0){
				PubTools.log.error("获取设备号["+sDevCode+"]所在分行IP地址为空或不唯一!");
				return ;
			}
			
			Object[] resultArray = lBranchInfoList.toArray();
			sBranchIp = ((HashMap) resultArray[0]).get("branch_node")==null?"":((HashMap) resultArray[0]).get("branch_node").toString().trim();	

            if(sBranchIp==null||"".equals(sBranchIp)){
				PubTools.log.error("获取设备号["+sDevCode+"]所在分行IP地址为空!");
            	return ;
            }
			// 向ATMV发送电子流水列表请求报文
			ImmediatelyDownloadJournalHandler immediatelyDownloadJournalHandler = new ImmediatelyDownloadJournalHandler();
			immediatelyDownloadJournalHandler.setJournalList(sJournalList);
			immediatelyDownloadJournalHandler.setBranchJournalServerIP(sBranchIp);
			if(immediatelyDownloadJournalHandler.requireDownloadJournal()){//先向V发送报文 确认文件是否存在，如果存在则发送上传请求报文
			    PubTools.log.debug("开始向分行发送请求报文-----------------------");
				immediatelyDownloadJournalHandler.downloadJournal();
			}
		} catch (Exception e) {
			PubTools.log.error("向分行ATMV下载设备号["+sDevCode+"]文件时间["+sFileTime+"]异常:"+e.toString());
		}
		PubTools.log.debug("下载设备号["+sDevCode+"]["+sFileTime+"]日期分行V流水完成!--------");
	}
	
	
	/**2011-12-20 向清添加
	 * 用于修改流水存放目录结构  总行文件夹结构为
	 * @param sDevCode 设备号 sFindDate 查找流水日期
	 * @return 文件路径
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
	 * 获取当前设备运行版本是否大于5340 
	 * @param sDevCode 设备号
	 * @return 版本号
	 */
	public String getDevVer(String sDevCode){
		String sReturnDevVer = "";
		String sQuerySQL = "select dev_ver from dev_smsg where dev_code='"+sDevCode+"' and dev_ver >='"+sDevVer+"'";
//		System.out.println(sQuerySQL);
		try{
		  Map mDevMap = new HashMap();
		  List lDevList =  JdbcFactory.queryForList( sQuerySQL);
		  if(lDevList==null||lDevList.size()==0){
			  PubTools.log.debug("获取设备号["+sDevCode+"]版本为["+sReturnDevVer+"]");
			  return "";
		  }
		  mDevMap = (Map)lDevList.get(0);
		  if(mDevMap==null||mDevMap.size()==0){
			  PubTools.log.debug("获取设备号["+sDevCode+"]版本为["+sReturnDevVer+"]");
			  return "";
		  }
		  sReturnDevVer = mDevMap.get("dev_ver")==null?"":mDevMap.get("dev_ver").toString().trim();
		}catch (Exception e) {
			PubTools.log.error("查询设备["+sDevCode+"]运行状态异常：" + "|" , e);
		}
		PubTools.log.debug("获取设备号["+sDevCode+"]版本为["+sReturnDevVer+"]");
		return sReturnDevVer;
	}
	
	public static void main(String args[]){
		ServiceParameter.init(); //初始化配置文件路径
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);

		 JournalLog jl = new JournalLog();
	
		 boolean flag = jl.logDevUploadStatus("440600300169", "20111220", "C:/WSAP/Data/440600300157_20110725.J", true, "20111220180102");
	     System.out.println(flag);
	}
}
