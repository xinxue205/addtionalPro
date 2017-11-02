/**
 * 
 */
package server.server.socket.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import server.server.socket.JournalServerParams;
import server.server.socket.ServiceParameter;
import server.util.JdbcFactory;
import server.util.PubTools;
import server.util.ZipTool;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:50:03
 * @Description
 * @version 1.0 Shawn create
 */
public class FileTools {

	private String errorFileList = "";

	private long iFileLength;

	private String serverFilePath = "";


	private String tempFileName = ""; // 文件名

	private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	//private SqlMapClient sqlMapper; //数据库使用类 20111221 xq 新增

	public FileTools() {

		// 判断服务器类型 如果是分行级就获取分行所在的文件目录 如果是总行就获取总行的目录
		if (JournalServerParams.JournalServerType.equals("branch")) {
			this.serverFilePath = JournalServerParams.JournalServerFilePath;
		} else {
			this.serverFilePath = JournalServerParams.JournalViewerTempFilePath;
		}
	}

	public long getTempFileLength() {
		return this.iFileLength;
	}

	public String getTempFileName() {
		return this.tempFileName;
	}

	public void setServerFilePath(String serverFilePath) {
		this.serverFilePath = serverFilePath;
	}

	public String getErrorFileList() {
		return this.errorFileList;
	}

	/**
	 * 获取文件的hash值
	 * 
	 * @param fileName
	 *            文件名
	 * @param hashType
	 *            hash类型
	 * @return 文件的hash值
	 * @throws Exception
	 * 
	 */
	public String getHash(String fileName, String hashType) throws Exception {
		FileInputStream fis;
		fis = new FileInputStream(fileName);// 读取文件
		byte[] buffer = new byte[10240];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

	/**
	 * 获取指定文件的MD5值
	 * 
	 * @param fileName
	 *            //文件名
	 * @return 返回文件的MD5值 如果有异常则返回空
	 */
	public String getFileMD5(String fileName) {
		try {
			return getHash(fileName, "MD5");
		} catch (Exception e) {
			PubTools.log.error("Catch Exception:" + e.toString());
			return null;
		}
	}

	/**
	 * 根据时间间隔删除指定文件夹内的文件 时间为从今天开始算 比今天小10天以内的文件
	 * 
	 * @param filePath
	 * @param timeSpan
	 * @return
	 */
	public static boolean DeleteFileByTime(String filePath, int timeSpan) {
		
		PubTools.log.info("开始删除文件路径:["+filePath+"] 时间为[" +timeSpan + "]的流水信息") ;
		File[] fileList = null;
		File pathFile = new File(filePath);
		if (pathFile.isDirectory()) {
			fileList = pathFile.listFiles();
		} else if (pathFile.isFile()) {
			fileList = new File[1];
			fileList[0] = pathFile;
		}
		
		long lNowDate = Calendar.getInstance().getTime().getTime();
		long lDaySpan = 1000 * 60 * 60 * 24 * timeSpan;
		if ( fileList == null)
			return false ;
		for (int iCount = 0; iCount < fileList.length; iCount++) {
			if (fileList[iCount] != null) {
				long lModifyDate = fileList[iCount].lastModified();
				if (lModifyDate + lDaySpan <= lNowDate) {
					if (!fileList[iCount].delete()) {
						continue;
					}
				}
			}
		}
		PubTools.log.info("删除文件路径:["+filePath+"] 时间为[" +timeSpan + "]的流水信息成功") ;
		return true;
	}
	
	/**
	 * 合并文件
	 * 
	 * @param outFile
	 *            合并后的文件名
	 * @param fileNameList
	 *            需要合并的文件名列表
	 * @return 
	 * 			如果有错，则返回错误信息列表
	 * @throws IOException
	 *             文件操作异常
	 */
	public static long MergeFile (String outFile, List fileNameList)
			throws Exception {
		int iIndex = 0;
		int MAX_READ_BLOCK = 10240;
		byte[] readBytes = new byte[MAX_READ_BLOCK];
		int readLen = 0;
		iIndex = outFile.lastIndexOf(".");
		String strUnZipFolder = outFile.substring(0,iIndex) ;
		File unZipFolder = new File(strUnZipFolder);
		File[] unZipFileList = unZipFolder.listFiles();

		String tempFileName = outFile.substring(0, outFile.lastIndexOf("."));
		tempFileName += ".ej";
		File tempOutFile = new File(tempFileName);

		// 创建文件输出流
		RandomAccessFile outRandomFile = new RandomAccessFile(tempOutFile, "rw");
		for (iIndex = 0; iIndex < fileNameList.size(); iIndex++) {
			RandomAccessFile inFile = new RandomAccessFile(
					unZipFileList[iIndex], "r");
			while ((readLen = inFile.read(readBytes, 0, MAX_READ_BLOCK)) != -1) {
				outRandomFile.write(readBytes, 0, readLen);
				readBytes = new byte[MAX_READ_BLOCK];
			}
			inFile.close();
			unZipFileList[iIndex].delete();
		}
		outRandomFile.close();
		outRandomFile = null;
		unZipFolder.delete();
		// 把合并后的文件进行压缩
		//Zip zip = new Zip();
		ZipTool.zipFile(tempFileName, outFile);
		// 删除合并的文件
		tempOutFile.delete();
		return new File(outFile).length();
	}



	/**
	 * 把文件列表的文件压缩到指定的目录中去
	 * 
	 * @param outFile
	 *            目录文件(解压目录根据目录文件生成) 如 c:\\test\\temp\\20090909.ej
	 * @param fileNameList
	 *            需要解压的文件列表
	 * @return 返回存放解压文件的解压目录 如 目录文件名为 c:\\test\\temp\\20090909.ej 则解压目录为
	 *         c:\\test\\temp\\20090909\\
	 */
	public static String UnZipFileToFolder(String outFile,String fileName)  throws Exception{
		// 创建目标文件所在目录
		File workingFile = null;
		String unZipfolderName = "";
		String tempFileName = "" ;
		int index = outFile.lastIndexOf(File.separatorChar);
		if (index >= 0) {
			workingFile = new File(outFile.substring(0, index));
			tempFileName = outFile.substring(index + 1, outFile.length());
			if (!workingFile.exists()) {
				if (!workingFile.mkdirs()) {
					return null;
				}
			}
		}
		workingFile = new File(outFile);
		index = tempFileName.lastIndexOf(".");
		if (index >= 0) {
			tempFileName = tempFileName.substring(0, index);
		}
		// 获取临时解压目录并创建该目录
		unZipfolderName = JournalServerParams.JournalServerTempFileDirectory
				+ File.separatorChar + tempFileName;
		File unZipfolderPath = new File(unZipfolderName);
		if (!unZipfolderPath.exists()) {
			if (!unZipfolderPath.mkdirs()) {
				return null;
			}
		}

		//UnZip unZip = new UnZip();
		if ( ZipTool.unzipFile(fileName, unZipfolderName) == null)
			throw new IOException();
		
		return unZipfolderName;
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件是否存在 存在为true 不存在为false
	 * @throws IOException
	 */
	private boolean checkFileExist(String fileName) throws IOException {
		boolean fileIsExist = false;
		File file = new File(fileName);
		if ((file == null) || !file.isFile() || !file.exists()) {
			fileIsExist = false;
		} else {
			fileIsExist = true;
		}
		return fileIsExist;
	}
	
	/**
	 * 		判断文件是否可以解压
	 * 
	 * @param sFileName
	 * 						文件名
	 * @return
	 * 			true 可以解压  false  不能解压
	 */
	public boolean checkFileUnZipable(String sFileName){
		String sFolderName =  sFileName.substring(0,sFileName.lastIndexOf(File.separatorChar)) ;
		//UnZip unZip = new UnZip();
		String[] unZipFileName = ZipTool.unzipFile(sFileName, sFolderName) ;
		if (  unZipFileName == null){
			new File(sFileName).delete();
			return false ;
		}
		
		for(int iIndex = 0 ; iIndex < unZipFileName.length ; iIndex ++){
//			System.out.println(sFolderName + File.separatorChar + unZipFileName[iIndex]) ;
			new File(sFolderName + File.separatorChar + unZipFileName[iIndex]).delete() ;
		}
		return true ;
	}

	/**
	 * 生成文件列表
	 * 
	 * @param termCodeList
	 *            终端列表
	 * @param fileTimeRange
	 *            时间范围
	 * @return
	 */
	public boolean CheckTerminalFileList(String sJournalList) {
		int iIndex = 0;
		try {
			
			PubTools.log.info("电子流水列表 : " + sJournalList) ;			
			String[] sArrTermList = sJournalList.split("\\#");
			for (iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
				String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
				String sTermCode = sArrTermInfo[0] ;
				String sFileTime = sArrTermInfo[1] ;
				// 文件路径 总目录\\终端号\\年\\月\\终端号_年月日.zip
				String fileName = getFileName(sTermCode, sFileTime);
				// 检查文件是否存在，如果存在，则存放在成功的文件列表中，如果失败，则存放在失败文件列表中
				if (!checkFileExist(fileName)) {
					PubTools.log.info("文件不存在:"+fileName);
					this.errorFileList += sTermCode + "$" + sFileTime + "#";
				} else {
					if (!checkFileUnZipable(fileName)){
						PubTools.log.info("文件不能压缩:"+fileName);
						this.errorFileList += sTermCode + "$" + sFileTime + "#";
					}else{
						PubTools.log.info("文件存在:"+fileName);
					}
				}
			}		
			if (this.errorFileList.length() > 0){
				PubTools.log.debug("errorFileList :[" +this.errorFileList + "]" );
				return false;
			}
		} catch (IOException ex) {
			PubTools.log.error("Catch Exception:" + ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * 生成文件列表
	 * 
	 * @param sTermCode
	 *            终端号
	 * @param sFileTime
	 *            设备时间
	 * @return
	 */
	public boolean CheckTerminalFile(String sTermCode, String sFileTime) {
		try {
			PubTools.log.debug("电子流水设备号 : " + sTermCode);
			PubTools.log.debug("电子流水时间 : " + sFileTime);
			// 文件路径 总目录\\终端号\\年\\月\\终端号_年月日.zip
			this.tempFileName = getFileName(sTermCode, sFileTime);
			// 检查文件是否存在，如果存在，则存放在成功的文件列表中，如果失败，则存放在失败文件列表中
			if (!checkFileExist(this.tempFileName)) {
				PubTools.log.info("文件不存在:" + this.tempFileName);
				return false;
			}
			return true;
		} catch (IOException ex) {
			PubTools.log.error("Catch Exception:" + ex.toString());
			return false;
		}
	}

	/**
	 * 根据终端号及电子流水时间生成与服务器对应的文件名
	 * 
	 * @param termCode
	 *            终端号
	 * @param fileTime
	 *            电子流水时间
	 * @return 终端号及时间在服务器上对应的文件的绝对路径
	 * 
	 */
	public String getFileName(String termCode, String fileTime) {
		// 文件路径 总目录\\年\\月\\日\\终端号_年月日_log
		try {
			//String tempFilePath =  this.serverFilePath + File.separatorChar + termCode + File.separatorChar + 
			//fileTime.substring(0, 4) + File.separatorChar + fileTime.substring(4, 6) + File.separatorChar + termCode + "_" + fileTime + ".zip";
//			String tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//			+ fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime + ".zip";
			
			//20111221 xq 修改 用于C端文件上传VH后 VH存放路径  只用于修改VH
			String tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime +".zip";

			return tempFilePath ;
		} catch (IndexOutOfBoundsException ex) {
			PubTools.log.error("Catch IndexOutOfBoundsException" + ex);
			return null;
		}
	}

	/** 20110509 xq 添加根据文件类型获取上送文件名 用于tc文件处理
	 * 根据终端号及电子流水时间生成与服务器对应的文件名 
	 * 
	 * @param termCode
	 *            终端号
	 * @param fileTime
	 *            电子流水时间
	 * @return 终端号及时间在服务器上对应的文件的绝对路径
	 * 
	 */
	public String getFileName(String termCode, String fileTime,String sAtmFileName) {
		// 文件路径 总目录\\年\\月\\日\\终端号_年月日 （流水文件）
		// 文件路径 总目录\\年\\月\\日\\终端号_年月日_tc.zip （tc文件文件）
		try {
			//String tempFilePath =  this.serverFilePath + File.separatorChar + termCode + File.separatorChar + 
			//fileTime.substring(0, 4) + File.separatorChar + fileTime.substring(4, 6) + File.separatorChar + termCode + "_" + fileTime + ".zip";
			String tempFilePath = "";
			if(sAtmFileName.indexOf("tc")>0||sAtmFileName.indexOf("TC")>0){
//				tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//			   	                + fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime + "_tc"+".zip";
				//20111221 xq 修改 用于C端文件上传VH后 VH存放路径  只用于修改VH
				tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime + "_tc"+".zip";
			}else{
//				tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//	                            + fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime +".zip";
				
				//20111221 xq 修改 用于C端文件上传VH后 VH存放路径  只用于修改VH
				tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime +".zip";
			}
			return tempFilePath ;
		} catch (IndexOutOfBoundsException ex) {
			PubTools.log.error("Catch IndexOutOfBoundsException" + ex);
			return null;
		}catch(Exception e){
			PubTools.log.error("获取总行流水文件存放路径异常:" + e);
			return null;
		}
	}
	
	
	/**2011-12-21 向清添加
	 * 用于修改流水存放目录结构  总行文件夹结构为
	 * @param sDevCode 设备号 sFindDate 查找流水日期
	 * @return 文件路径
	 */
	public String getRealFilePath(String sDevCode,String sFindDate){
		String sPath = "";
		//sqlMapper = Dboperator.getSqlMapper();
		String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
		List mBranchList = new ArrayList();
		try {
			String sBranchCode = "";
			//mBranchList = sqlMapper.queryForList("select", sQuerySQL);
			mBranchList = JdbcFactory.queryForList(sQuerySQL);
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
	
	/**
	 * 获取临时文件名 主要用于文件服务器合并文件使的文件名
	 * 
	 * @return
	 */
	public String genTempFilePath(String sTempFilePath) {

		String tempFileName = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		while (true) {
			tempFileName = "";
			tempFileName += sTempFilePath;
			tempFileName += File.separatorChar + df.format(Calendar.getInstance().getTime());

			try {
				if (new File(tempFileName).exists()) {
					Thread.sleep(10);
				} else
					break;
			}catch (InterruptedException ex) {
				PubTools.log.error("Thread Sleep Catch Exception :"
						+ ex.toString());
				return null;
			}
		}

		return tempFileName;
	}

	private String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static void main(String []args){
		ServiceParameter.init(); //初始化配置文件路径
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);
		FileTools fileTools = new FileTools();
		fileTools.checkFileUnZipable("d:\\Log.zip");
	}


}
