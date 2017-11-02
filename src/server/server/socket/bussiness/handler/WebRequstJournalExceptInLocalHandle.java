/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.server.socket.JournalServerParams;
import server.server.socket.tool.FileTools;
import server.util.Dboperator;
import server.util.PubTools;
import server.util.ZipTool;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:44:25
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequstJournalExceptInLocalHandle {
	private List lExistFileAddList = new ArrayList();//在总行文件夹已经存在的文件路径
	private List lUnExistFileAddList = new ArrayList();//在总行文件夹不存在的文件列表
	private SqlMapClient sqlMapper = null; // 数据库操作
	private FileTools fileTools = new FileTools();
	private String sGetFileMethode = "";//20120917 xq add 提取方式
	private String sDevBranch1Code = "";//20130704 用于保存机构号信息
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//20120828 xq add 格式化时间


	
	/**
	 * 将浏览器发送过来的需提取流水的设备以及日期过滤  如果流水在在总行的话 则不需要到分行去提取流水
	 * @param sJournalList 浏览器穿过来的流水列表   设备号@时间#
	 * @return
	 */
	public String removeExistInVHFile(String sJournalList){
		PubTools.log.info("当前开始判断流水文件是否在VH已经存在，当前传入文件列表信息["+sJournalList+"]");
		String sRemovedJournalList = "";
		List lJournalList = this.getJournalList(sJournalList);
		if(lJournalList==null||lJournalList.size()==0){
			return "";
		}
//		String sCurrDate = PubTools.dateToFormatString(new Date());
		
		for(int iIndex=0;iIndex<lJournalList.size();iIndex++){
			Map mDevInfoMap = new HashMap();
			mDevInfoMap = (Map)lJournalList.get(iIndex);
			String sDevCode = mDevInfoMap.get("termCode")==null?"":mDevInfoMap.get("termCode").toString();
			String sFindDate = mDevInfoMap.get("fileTime")==null?"":mDevInfoMap.get("fileTime").toString();
//			if(sFindDate.equals(sCurrDate)){
//				sRemovedJournalList = sRemovedJournalList + sDevCode+"@"+sFindDate+"#";
//			}else 
			if(!findFileIsExist(sDevCode,sFindDate)){
				sRemovedJournalList = sRemovedJournalList + sDevCode+"@"+sFindDate+"#";
			}
		}
		return sRemovedJournalList;
	}
	
	// 设置设备编号列表
	public List getJournalList(String sJournalList) {
		List alTermList = new ArrayList();
		alTermList.clear() ;
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			HashMap hmTmpTermList = new HashMap();
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			hmTmpTermList.put("termCode", sArrTermInfo[0]);
			hmTmpTermList.put("fileTime", sArrTermInfo[1]);
			alTermList.add(hmTmpTermList);
		}
		return alTermList;
	}
	
	/**
	 * 根据设备编号和查找时间判断 文件是否存在
	 * @param sDevCode
	 * @param sFindDate
	 * @return
	 */
	public boolean findFileIsExist(String sDevCode,String sFindDate){
		boolean isExist = false;
		if(sDevCode==null||sDevCode.equals("")){
			return false;
		}
		if(sFindDate==null||sFindDate.equals("")||sFindDate.length()!=8){
			return false;
		}
		sDevBranch1Code = this.getDevBranch1(sDevCode);  //20130704 xq update 机构号修改为统一获取
		String sFileName = sDevCode+"_"+sFindDate+".zip";
		String sPath = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sDevCode, sFindDate,sDevBranch1Code)+File.separator+sFileName;
		File file = new File(sPath);
		
		/**
		 * 20120319 xq 新增 如果数据库记录没有或者该数据上传不成功!则需向V端提取
		 */
		if(!checkJournalLogIsExist(sDevCode,sFindDate)){
			lUnExistFileAddList.add(sPath);
			return false;
		}
		
		if(file.exists()&&file.isFile()){
			lExistFileAddList.add(sPath);
			isExist = true;
		}else{
			lUnExistFileAddList.add(sPath);
		}
		return isExist;
	}
	
	
	/**
	 * 20120319 xq 判断电子流水上传日志表中是否有该文件上传的记录
	 * @return true 存在 false 不存在
	 */
	public boolean checkJournalLogIsExist(String sDevCode,String sFileTime){
		PubTools.log.debug("检查数据库是否存在!");
		//---20120319 xq 修改处理流程------
		String sCurrDate =   PubTools.getSystemDate();
		if(sCurrDate.equals(sFileTime)){
			return false;
		}
		
		//20120917 xq add 新增直接向ATMC提取文件流程
		if(this.getSGetFileMethode()==null||"".equals(this.getSGetFileMethode())||"1".equals(this.getSGetFileMethode().trim())){
			return false;
		}
		//------end----------------
		
		String sQuerySQL = "select atmfilename from journal_uploadlog where devcode='"+sDevCode+"' and filetime='"+sFileTime+"' and transresult='0' and atmfilename like '%.J'";
		List lResultList = new ArrayList();
		PubTools.log.debug("查询当前提取文件是否上传成功SQL为:"+sQuerySQL);
		try {
			lResultList = sqlMapper.queryForList("select", sQuerySQL);
			if (lResultList != null && lResultList.size() > 0) {
				PubTools.log.debug("查询结果为true");
				return true;
			}else{
				PubTools.log.debug("查询结果为false");
				return false;
			}
		}catch(Exception e){
			PubTools.log.error("获取上传文件是否存在异常:"+e.toString());
			return false;
		}
	}
	
	
	//将文件指定文件夹下的文件 拷贝到临时文件夹下
	/**
	 * 20130704 xq 修改 将解压后的文件保存路径由之前的直接放入tempFile文件加下，加上放入tmepFile/机构号文件夹下
	 * @return
	 */
	public String sCopyFileToTemp(){
//		System.out.println("sCopyFileToTemp:"+this.lExistFileAddList.size()+"\t-------:"+this.lUnExistFileAddList.size());
		List lExistFileList = this.lExistFileAddList;
		List lUnExitFileList = this.lUnExistFileAddList;
//		JournalServerParams.JournalViewerTempFilePath
		String sSystemFilePath = JournalServerParams.JournalViewerTempFilePath+File.separator+"tempFile"+File.separator+sDevBranch1Code;
		String sTempPath = fileTools.genTempFilePath(sSystemFilePath);
		try{
			File sTempFileDir = new File(sTempPath);
			if(!sTempFileDir.exists()){
				sTempFileDir.mkdirs();
			}
		}catch(Exception e){
			PubTools.log.error("创建临时文件夹异常："+e);
		}
		if(lExistFileList!=null&&lExistFileList.size()!=0){
			for(int iIndex=0;iIndex<lExistFileList.size();iIndex++){
				try{
					
					String sFile = (String)lExistFileList.get(iIndex);
					PubTools.log.info("====================----===="+sFile);
					String sFileName = (sFile.substring(sFile.lastIndexOf(File.separator) + 1,sFile.length()));
					String sTempName = sFileName.substring(0,sFileName.lastIndexOf("."));
					
					upZipSuccess(sFile,sTempPath,sTempName);
					//-----------------20110829 xq 如果文件是当天的话，再提取之后删除该文件------//
//					deleteCurrJournalFile(sFile);
					//-------------------end----------------------------------------------//
				}catch(Exception e){
					e.printStackTrace();
					PubTools.log.error("解压文件异常:"+e);
				}
			}
		}
		PubTools.log.debug("lUnExitFileList.size()++++++++++++++"+lUnExitFileList.size());
		if(lUnExitFileList!=null&&lUnExitFileList.size()!=0){
			for(int iIndex=0;iIndex<lUnExitFileList.size();iIndex++){
				try{
					String sFile = (String)lUnExitFileList.get(iIndex);
					PubTools.log.debug("====================----====++++++++"+sFile);
					String sFileName = (sFile.substring(sFile.lastIndexOf(File.separator) + 1,sFile.length()));
					String sTempName = sFileName.substring(0,sFileName.lastIndexOf("."));
					upZipSuccess(sFile,sTempPath,sTempName);
					
					//-----------------20110829 xq 如果文件是当天的话，再提取之后删除该文件------//
//					deleteCurrJournalFile(sFile);
					//-------------------end----------------------------------------------//
				}catch(Exception e){
//					e.printStackTrace();
					PubTools.log.error("解压文件异常:",e);
				}
			}
		}
		if(this.lExistFileAddList!=null){
			lExistFileAddList = null;
		}
		if(this.lUnExistFileAddList!=null){
			lUnExitFileList = null;
		}
		return sTempPath;
	}
	
	
	/**
	 * 20110829 xq 如果文件提取为当天的话，在提取完文件后将该文件删除
	 * @param sFileName
	 */
	public void deleteCurrJournalFile(String sFileName){
		String sCurrDate = PubTools.getSystemDate();
		if(sFileName.indexOf(sCurrDate)>0){
			File sCurrFile = new File(sFileName);
			if(sCurrFile.exists()){
				sCurrFile.delete();
			}
		}
	}
	
	
	//解压jar包后重命名 
	public void upZipSuccess(String sFile,String sDirector,String sTempFileName){
	  File file = new File(sFile);
	  if(!file.exists()||!file.isFile()){
		  return ;
	  }
	    try{
			String[] unZipFileName = ZipTool.unzipFile(sFile, sDirector) ;  //解压文件到临时目录
			String tempFileName =  sDirector	+ File.separatorChar + sTempFileName + ".j"; 
			new File(sDirector + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName)); //重命名解压出来的文件
	    }catch(Exception e){
	    	PubTools.log.error("解压文件异常:"+e.toString());
	    	file.delete();
	    }
	}
	
	
	/**2011-04-15 向清添加
	 * 用于修改流水存放目录结构  总行文件夹结构为
	 * 20130704 xq 修改 将机构号获取修改为统一传入
	 * @param sDevCode 设备号 sFindDate 查找流水日期
	 * @return 文件路径
	 */
	public String getRealFilePath(String sDevCode,String sFindDate,String sBranchCode){
		String sPath = "";
		try{
			if(sFindDate.length()!=8){
				return "";
			}
			String sYear = sFindDate.substring(0, 4);
			String sMonth = sFindDate.substring(4, 6);
			String sDay = sFindDate.substring(6, 8);
			sPath = sBranchCode+File.separator+sYear+File.separator+sMonth+File.separator+sDay;
		} catch (Exception e) {
			PubTools.log.error("获取流水所在文件夹异常:",e);
			return "";
		}
		return sPath;
	}

	/**
	 * 20130704 xq 根据设备号获取设备一级机构号 主要用于将文件拷贝到临时文件后，需在临时文件中以一级机构保存
	 * @param sDevCode 设备号
	 * @return 一级机构号
	 */
    public String getDevBranch1(String sDevCode){
    	String sBranchCode = "";
    	if("".equals(sDevBranch1Code)){
	    	sqlMapper = Dboperator.getSqlMapper();
	    	
			String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
			List mBranchList = new ArrayList();
			try {
				
				mBranchList = sqlMapper.queryForList("select", sQuerySQL);
				if (mBranchList != null && mBranchList.size() > 0) {
					Object[] resultArray = mBranchList.toArray();
					sBranchCode = ((HashMap) resultArray[0]).get("dev_branch1")==null?"":((HashMap) resultArray[0]).get("dev_branch1").toString().trim();	
				}
			    sDevBranch1Code = sBranchCode;
			}catch (Exception e) {
				PubTools.log.error("获取设备["+sDevCode+"]异常",e);
			}
    	}else{
    		sBranchCode = sDevBranch1Code;
    	}
		return sBranchCode;
    }

	/**
	 * * 复制单个文件
	 * 
	 * @param String
	 *            sSourceFileName 原文件路径 如：c:/a.txt
	 * @param String
	 *            sTargetDirectory 复制后存放的目录 如：f:/dir
	 * @return 复制成功返回T
	 */
	public static boolean copyFile(String sSourceFileName, String sTargetDirectory) {
		boolean bResult = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File fSourceFile = new File(sSourceFileName);
			if (fSourceFile.exists()) { // 文件存在时
				String sFileName = fSourceFile.getName();// 源文件名（不包含路径）
				String sNewFileName = sTargetDirectory + File.separatorChar + sFileName;// 目标文件名（包含路径）
				InputStream inStream = new FileInputStream(sSourceFileName); // 读入原文件
				FileOutputStream fs = new FileOutputStream(sNewFileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				bResult = true;
			}
		} catch (Exception e) {
//			PubTools.log.error("复制单个文件发生异常:" + e.toString());
		}
		return bResult;
	}
	
	
	/**20120829 xq add 复制单个文件成另外一个文件
	 * * 复制单个文件
	 * 
	 * @param String
	 *            sSourceFileName 原文件路径 如：c:/a.txt
	 * @param String
	 *            sTargetDirectory 复制后存放的目录 如：f:/dir
	 *       
	 * @param sTargetFileName 目标文件名
	 * @return 复制成功返回T
	 */
	public static boolean copyFile(String sSourceFileName, String sTargetDirectory,String sTargetFileName) {
		boolean bResult = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File fSourceFile = new File(sSourceFileName);
			if (fSourceFile.exists()) { // 文件存在时
				String sFileName = fSourceFile.getName();// 源文件名（不包含路径）
				String sNewFileName = sTargetDirectory + File.separatorChar + sTargetFileName;// 目标文件名（包含路径）
				InputStream inStream = new FileInputStream(sSourceFileName); // 读入原文件
				FileOutputStream fs = new FileOutputStream(sNewFileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				bResult = true;
			}
		} catch (Exception e) {
//			PubTools.log.error("复制单个文件发生异常:" + e.toString());
		}
		return bResult;
	}
	
	/**
	 * 20120828 xq add 如果用户选择的是直接提取ATMC文件 在提取之前如果提取的不是当前天的文件，需备份该文件
	 * @param sJournalList文件列表
	 */
	public void copyFileToTmp(String sJournalList){
		PubTools.log.info("当前提取属于直接从ATMC提取，需备份提取本批次流水文件!");
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			String sDevCode = sArrTermInfo[0];
			String sFileTime =  sArrTermInfo[1];
			if(!sFileTime.equals(PubTools.getSystemDate())){
				copyFileToTmpFile(sDevCode,sFileTime);
			}
		}
		PubTools.log.info("本批次流水文件备份完毕!");
	}
	
	/**
	 * 20120828 xq add 备份对应设备的流水文件
	 * @param sDevCode 设备号
	 * @param sFindDate 日期
	 */
	public void copyFileToTmpFile(String sDevCode,String sFindDate){
		PubTools.log.info("开始备份设备号["+sDevCode+"]对应日期["+sFindDate+"]的流水文件!");
		try{
			String sFileName = sDevCode+"_"+sFindDate+".zip";
			sDevBranch1Code = this.getDevBranch1(sDevCode);  //20130704 xq update 机构号修改为统一获取
			String sPathDir = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sDevCode, sFindDate,sDevBranch1Code);
			String sPath = sPathDir +File.separator+sFileName;
			File file = new File(sPath);
			if(!file.exists()){
				PubTools.log.info("需拷贝文件["+sPath+"]不存在!直接提取!");
				return ;
			}
	        String sNewFileName = sDevCode+"_"+sFindDate+"_"+simpleDateFormat.format(new Date())+".zip";
	        PubTools.log.info("当前源文件["+sPath+"]拷贝文件["+sPathDir+File.separator+sNewFileName+"]");
	        copyFile(sPath,sPathDir,sNewFileName);
       }catch(Exception e){
    	   PubTools.log.error("文件拷贝异常",e);
       }
       PubTools.log.info("设备号["+sDevCode+"]对应日期["+sFindDate+"]的流水文件备份完毕!");
	}
	


	public static void main(String[] args){
		WebRequstJournalExceptInLocalHandle web = new WebRequstJournalExceptInLocalHandle();
		web.checkJournalLogIsExist("440600300157", "20120319");
//		web.upZipSuccess("C:/Documents and Settings/Administrator/桌面/310704200221_20120228.zip", "D:", "testFile");
//		File f = new File("C:/Documents and Settings/Administrator/桌面/330001362057_20120224.zip");
//		System.out.println(f.length());
//		 new UnZip().unzipFile("C:/Documents and Settings/Administrator/桌面/330001362057_20120224.zip", "D:");
		 
//		 String sFile = "C:/Documents and Settings/Administrator/桌面/330001362057_20120224.zip";
//		 String sDirector = "D:";
//		 String sTempFileName = "testFile";
//		 File file = new File(sFile);
//		  if(!file.exists()||!file.isFile()){
//			  return ;
//		  }
//	    
//		String[] unZipFileName = new UnZip().unzipFile(sFile, sDirector) ;  //解压文件到临时目录
//		String tempFileName =  sDirector	+ File.separatorChar + sTempFileName + ".j"; 
//		new File(sDirector + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName)); //重命名解压出来的文件

	}

	public String getSGetFileMethode() {
		return sGetFileMethode;
	}

	public void setSGetFileMethode(String getFileMethode) {
		sGetFileMethode = getFileMethode;
	}



}
