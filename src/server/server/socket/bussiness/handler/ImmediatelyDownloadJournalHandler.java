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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:46:22
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyDownloadJournalHandler {

	private String branchJournalServerIP = "";

	private String faultTermList = "";

	private String respCode = "";

	private String tempFilePath = "";
	
	private String sJournalList = "";

	private List alTermList = new ArrayList(); // 请求下载的电子流水列表
	
	private SqlMapClient sqlMapper = null; // 数据库操作

	private boolean bTransFlag = false; // 交易标志

	public String getFaultTermList() {
		return faultTermList;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getTempFilePath() {
		return tempFilePath;
	}

	// 设置设备编号列表
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

	// 设置分行电子流水服务器地址
	public void setBranchJournalServerIP(String branchJournalServerIP) {
		this.branchJournalServerIP = branchJournalServerIP;
	}

	/**
	 * 查询电子流水列表是否存在
	 * 
	 * @return 是否存在 存在 true 不存在 false
	 * @throws Exception
	 */
	public boolean requireDownloadJournal() throws Exception {
		SocketUtil socketUtil = null;
		boolean successGetFile = false;
		try {
			PubTools.log.info("开始向分行ATMV提取电子流水,提取电子流水列表[" + this.sJournalList + "] 分行IP[" + this.branchJournalServerIP.trim() + "] 设备端口["
					+ JournalServerParams.BranchJournalServerPort + "]");
//
//			PubTools.log.info("分行IP[" + this.branchJournalServerIP.trim() + "] 设备端口["
//					+ JournalServerParams.BranchJournalServerPort + "]");
			
			socketUtil = new SocketUtil(this.branchJournalServerIP.trim(), JournalServerParams.BranchJournalServerPort,10000);//20121108 xq 增加向分行ATMV提取电子流水超时时间

			if (socketUtil.createConnection()) {
				String requestMsg = DownloadJournalRequestMsgHandler.getDownloadJournalRequestMsg(JournalTransCodeMsg
						.getDownloadJournalRequestMsg(), this.sJournalList);
				// 发送请求报文
				socketUtil.sendMessage(requestMsg);
				PubTools.log.info("发送数据报文到分行:[" + this.branchJournalServerIP.trim() + "成功");
				// 获取响应报文
				String responseMsg = socketUtil.getMessage();
				PubTools.log.debug(responseMsg);
				// 如果响应报文为空，则直接返回失败
				if (responseMsg.equals("") || responseMsg == null)
					return false;
				PubTools.log.info("从分行:[" + this.branchJournalServerIP + "]接收数据成功");
				int transCode = GetTransCodeMsg.getTransCode(responseMsg);
				if (transCode == JournalTransCodeMsg.DownloadJournalResponseMsg) {
					PubTools.log.debug("接收到分行对下载文件的响应报文");
					DownloadJournalResponseMsg downloadJournalResponseMsg = new DownloadJournalResponseMsg();
					downloadJournalResponseMsg.unpackMsg(responseMsg);
					this.respCode = downloadJournalResponseMsg.getRespCode(); // 获取响应码
					this.bTransFlag = downloadJournalResponseMsg.getTransFlag();// 处理标志
					if (!this.bTransFlag) {
						this.faultTermList = downloadJournalResponseMsg.getFaultTermList();// 失败信息列表
					} else
						successGetFile = true;
				}
			} else {
				PubTools.log.info("连接失败");
			}
		} finally {
			if (socketUtil != null)
				socketUtil.closeConnection();
		}
		return successGetFile;
	}

	/**
	 * 下载电子流水
	 * 
	 * @return 是否下载成功
	 * @throws Exception
	 */
	public boolean downloadJournal() throws Exception {
		SocketUtil socketUtil = null;
		boolean successGetFile = false;
		int iMaxTry = 3 ;   //文件下载失败最大尝试次数
		
		for (int iIndex = 0; iIndex < this.alTermList.size(); iIndex++) {
			
			HashMap hmTermInfo = (HashMap) alTermList.get(iIndex);    //电子流水请求信息
			String sTermCode = (String) hmTermInfo.get("termCode");   //获取设备号
			String sFileTime = (String) hmTermInfo.get("fileTime"); 		//获取电子流水时间
			
			try {
				//----------------------20110415 xq 修改 用于更改总行文件存放结构------------------------// 
				// 获取文件的存放路径
				this.tempFilePath = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sTermCode, sFileTime);
//				System.out.println("-============----------+++++++++:"+tempFilePath);
				// 创建文件所在目录
				File workingFile = new File(this.tempFilePath);
				// 判断文件夹是否存在,如果存在,则不创建文件夹
				if (!workingFile.exists()) {
					if (!workingFile.mkdirs()) {
						// 创建文件夹失败
						return successGetFile;
					}
				}
				
				//20120319 xq 向分行V提取文件时不需判断
//				 if(checkLocalFileIsExist(tempFilePath,sTermCode, sFileTime)){
//					 return true;
//				 }
				
				PubTools.log.info("提取电子流水设备号[" + sTermCode + "] 时间[" + sFileTime + "]");

				PubTools.log.debug("分行IP[" + this.branchJournalServerIP.trim() + "] 设备端口["
						+ JournalServerParams.BranchJournalServerPort + "]");
				socketUtil = new SocketUtil(this.branchJournalServerIP.trim(),JournalServerParams.BranchJournalServerPort,10000);//20121108 xq 增加向分行ATMV提取文件时增加超时时间判断
				
				int iTryCount = 0 ;   //设置文件下载尝试次数  

				if (socketUtil.createConnection()) {
					String requestMsg = DownloadJournalFileMsgHandler.getDownloadJournalFileRequestMsg(
							JournalTransCodeMsg.getDownloadJournalFileRequestMsg(), sTermCode, sFileTime);
					// 发送请求报文
					socketUtil.sendMessage(requestMsg);
					PubTools.log.debug("发送数据报文到分行:[" + this.branchJournalServerIP.trim() + "]成功");
					FileOperationStruct fileOperationStruct = new FileOperationStruct();
					boolean isContinue = true;
					while (isContinue) {
						// 获取响应报文
						String responseMsg = socketUtil.getMessage();
						PubTools.log.debug(responseMsg);
						// 如果响应报文为空，则直接返回失败
						if (responseMsg == "" || responseMsg == null)
							break;
						PubTools.log.debug("从分行:[" + this.branchJournalServerIP + "]接收数据成功");
						int transCode = GetTransCodeMsg.getTransCode(responseMsg);
						switch (transCode) {
							case JournalTransCodeMsg.DownloadJournalFileResponseMsg : {
								PubTools.log.debug("接收到分行对下载文件的响应报文");
								DownloadJournalFileResponseMsg downloadJournalFileResponseMsg = new DownloadJournalFileResponseMsg();
								downloadJournalFileResponseMsg.unpackMsg(responseMsg);
								this.bTransFlag = downloadJournalFileResponseMsg.getTransFlag();// 处理标志
								if (this.bTransFlag) {
									String tempFileName = downloadJournalFileResponseMsg.getFileName();// 临时文件名
									String tempFileMD5 = downloadJournalFileResponseMsg.getFileMD5();// 临时文件MD5值
									String localTempFileName = this.tempFilePath
											+ File.separator
											
//											+ tempFileName.substring(tempFileName.lastIndexOf(File.separatorChar) + 1,//生产路径
											
											+ tempFileName.substring(tempFileName.lastIndexOf("/") + 1,//记住自己修改
													tempFileName.length());
									PubTools.log.debug("临时文件名为: " + localTempFileName);
									
									fileOperationStruct.setFileName(localTempFileName);
									fileOperationStruct.setFileMD5(tempFileMD5);
									fileOperationStruct.setFileOffSet(0);
									String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
											.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
													.getDownloadJournalDataRequestMsg(), tempFileName,
													fileOperationStruct.getFileOffSet());
									// 发送请求报文
									socketUtil.sendMessage(downloadJournalDataRequestMsg);
								} else {
									this.respCode = downloadJournalFileResponseMsg.getRespCode(); // 获取响应码
									this.faultTermList = downloadJournalFileResponseMsg.getErrorMsg();// 失败信息列表
									PubTools.log.warn("获取电子流水失败: 设备号" + sTermCode + " 时间:" + 
											sFileTime + "respCode" + this.respCode + " 失败信息列表:" + this.faultTermList);
									isContinue = false; // 结束循环
								}
								break;
							}
							case JournalTransCodeMsg.DownloadJournalDataTransMsg : {
								PubTools.log.debug("接收到分行对文件文件的传输的报文");
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
									// 如果是最后一块数据，则进行文件MD5验证
									String checkFileMD5 = new FileTools().getFileMD5(fileOperationStruct.getFileName());
									if (!checkFileMD5.equalsIgnoreCase(fileOperationStruct.getFileMD5())) {
										PubTools.log.warn("文件MD5校验失败: 设备号" + sTermCode + " 时间:" + sFileTime + "原MD5"
												+ fileOperationStruct.getFileMD5() + " 文件MD5:" + checkFileMD5);
										iTryCount++;
										if (iTryCount < iMaxTry) {
											PubTools.log.warn("第" + iTryCount + "次尝试下载文件:" + fileOperationStruct.getFileName());
											// 如果MD5验证不一致，则重新下载文件
											fileOperationStruct.setFileOffSet(0);
											String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
													.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
															.getDownloadJournalDataRequestMsg(), fileOperationStruct.getFileName(),
															fileOperationStruct.getFileOffSet());
											// 发送请求报文
											socketUtil.sendMessage(downloadJournalDataRequestMsg);
										}else{
											PubTools.log.warn("下载文件失败 MD5校验失败 文件名:[" + fileOperationStruct.getFileName() + "]");
											isContinue = false; // 结束循环
										}
									} else {
										//-------------------------20110415向清修改 修改文件夹结构 分行V上送的文件先不解压 将文件返回给浏览器的时候再解压----------------//
										// MD5验证通过，则解压缩文件
										PubTools.log.debug("文件:"+fileOperationStruct.getFileName()+"MD5验证通过，解压文件") ;
//										String tempFileName = fileOperationStruct.getFileName();  //下载的文伯名
//										String[] unZipFileName = new UnZip().unzipFile(tempFileName, this.tempFilePath) ;  //解压文件到临时目录
//										new File(tempFileName).delete();   //把下载的文件删除
//										tempFileName =  this.tempFilePath	+ File.separatorChar + sTermCode + "_" + sFileTime + ".j"; 
//										if (!new File(this.tempFilePath + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName))){ //重命名解压出来的文件
//											successGetFile = false; // 标识文件下载成功
//										}else{
//											successGetFile = true; // 标识文件下载成功
//										}
										successGetFile = true;
										isContinue = false; // 结束循环
//										PubTools.log.info("下载文件成功 文件路径:[" + tempFileName + "]");
									}
								} else {
									// 如果不是最后一块数据，则继续发送数据传输请求报文
									long tempFileOffset = fileOperationStruct.getFileOffSet() + dataSize;
									fileOperationStruct.setFileOffSet(tempFileOffset);
									String downloadJournalDataRequestMsg = DownloadJournalDataRequestMsgHandler
											.getDownloadJournalDataRequestMsg(JournalTransCodeMsg
													.getDownloadJournalDataRequestMsg(), fileOperationStruct
													.getFileName(), fileOperationStruct.getFileOffSet());
									// 发送请求报文
									socketUtil.sendMessage(downloadJournalDataRequestMsg);
								}
								break;
							}
						}
					}
				} else {
					PubTools.log.warn("连接分行:["+this.branchJournalServerIP.trim()+"]失败");
				}
			} finally {
				if (socketUtil != null)
					socketUtil.closeConnection();
			}
		}
		return successGetFile;
	}
	
	/**2011-04-15 向清添加
	 * 用于修改流水存放目录结构  总行文件夹结构为
	 * @param sDevCode 设备号 sFindDate 查找流水日期
	 * @return 文件路径
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
     * 20111220 xq  新增
	 * 判断本地文件是否存在
     * @return true 存在 false 不存在
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
			PubTools.log.error("获取本地文件是否存在异常:",e);
			return false;
		}
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
	       if(iDiffDay==1||iDiffDay==0){
	    	   return false;
	       }
		}catch(Exception e){
			PubTools.log.error("获取时间信息异常",e);
			return false;
		}
	   PubTools.log.debug("检查是否当前日期");
       return true;
	}
	

	public static void main(String[] args) {
		ServiceParameter.init(); // 初始化配置文件路径
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
