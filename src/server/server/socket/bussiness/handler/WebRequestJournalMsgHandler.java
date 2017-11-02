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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:22:41
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequestJournalMsgHandler {

	private List alTermList = new ArrayList(); // 电子流水请求列表

	private HashMap hmHashMap = new HashMap(); // 设备列表

	private String journalList = "";     // 格式"ermCode@fileTime#termCode@fileTime"
									
	private boolean getFileSuccess = false; // 是否获取文件成功

	private String requestMsg = ""; // 电子流水请求报文

	//private SqlMapClient sqlMapper = null; // 数据库操作

	private String responseMsg = ""; // 失败错误信息
	
	private String tempFilePath = "" ; //临时文件名
	
	private String sGetMethod = "";//20120828 xq add 提取方式 0 按现有方式提取 1直接从ATMC提取
	
	private String sDevCodeStr = "";//20121108 xq add 获取设备号列表信息，仅存在设备号信息
	
	private static final String sDevVer = "005.003.004.000"; 
	
	

	/**
	 * 生成WEB->ATHVH电子流水提到请求报文的响应报文
	 * 
	 * @param transCode
	 *            响应报文的交易码
	 * @param transFlag
	 *            处理标志位
	 * @param tempFileName
	 *            临时文件名
	 * @param faultMessage
	 *            错误信息
	 * @return 响应报文
	 */
	public String getWebRequestJournalResponseMsg(String transCode, boolean transFlag, String tempFileName,
			String faultMessage) {
		String returnString = ""; // 返回报文
		WebRequestJournalResponseMsg webRequestJournalResponseMsg = new WebRequestJournalResponseMsg();
		webRequestJournalResponseMsg.setTransCode(transCode); // 设置交易码
		webRequestJournalResponseMsg.setTempFileName(tempFileName);
		webRequestJournalResponseMsg.setFaultMessage(faultMessage);
		// 跟据处理标志赋值
		if (transFlag) {
			// 如果处理标志为成功 则设置临时文件名
			webRequestJournalResponseMsg.setTransFlag(true);
		} else {
			// 如果处理标志为失败 则设置错误信息	
			webRequestJournalResponseMsg.setTransFlag(false);
		}
		// 返回提到电子流水请求报文的响应报文
		returnString = webRequestJournalResponseMsg.packMsg();
		PubTools.log.debug(returnString); // 把返回报文记录为Debug日志
		return returnString;
	}

	/**
	 * 处理WEB->ATHVH电子流水提到请求报文
	 * 
	 * @return 响应报文
	 */
	public String handleWebRequestJournalMsg() {
		PubTools.log.info("进入WEB提取处理阶段handleWebRequestJournalMsg>>>>>>>>>>>>>>>>>>>>>");
		String toSendString = ""; // 响应报文
		WebRequestJournalMsg webDownloadJournalMsg = new WebRequestJournalMsg();
		webDownloadJournalMsg.unpackMsg(this.requestMsg);
		this.journalList = webDownloadJournalMsg.getJournalList(); // 获取请求设备号列表
		this.sGetMethod = webDownloadJournalMsg.getSGetMethod();//20120828 xq add 增加提取方式
		
		//------------------20110418向清 修改
		WebRequstJournalExceptInLocalHandle webRequstJournalExceptInLocalHandle = new WebRequstJournalExceptInLocalHandle();
		webRequstJournalExceptInLocalHandle.setSGetFileMethode(sGetMethod);
		
		//20120828 xq add 如果提取方式为0 则按照现有方式不变 否则直接向ATMC提取文件，在提取文件之前必须先备份一下在VH已经存在的文件
		if(sGetMethod!=null&&!"".equals(sGetMethod)&&sGetMethod.equals("1")){
			webRequstJournalExceptInLocalHandle.copyFileToTmp(journalList);//如果是直接从ATMC提取文件，则需先将不是当前天的流水备份一遍 备份文件名 设备号_日期_yyyyMMddHHssmm.zip
		}
//		else{
//			this.journalList = webRequstJournalExceptInLocalHandle.removeExistInVHFile(this.journalList);	
//		}
		this.journalList = webRequstJournalExceptInLocalHandle.removeExistInVHFile(this.journalList);	

		//----------------end 20120828-------------------------------------------------------------------------
		
		
		if(journalList==null||journalList.equals("")){//如果所有的文件在VH端可以找到的话 则不需要到V端提取流水
			// 如果获取文件成功，则返回成功报文以及临时文件名
			toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(), true,
					webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
		}else{
			requireDownloadJournal(); // 向ATMV发送下载电子流水文件的请求
			if (this.getFileSuccess) {
				
				// 如果获取文件成功，则返回成功报文以及临时文件名
				toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(), true,
						webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
			} else {
				// 如果获取文件失败，则返回失败报文,包含失败错误信息
				toSendString = getWebRequestJournalResponseMsg(JournalTransCodeMsg.getWebRequestJournalResponseMsg(),
						false, webRequstJournalExceptInLocalHandle.sCopyFileToTemp(), this.responseMsg);
			}
		}
		
		PubTools.log.info("本次提取返回报文："+toSendString);
		return toSendString;
	}

	/**
	 * ATMVH向ATMV发送电子流水下载请求 如果ATMV没有该电子流水 则向ATMC发送上传电子流水请求报文
	 * ATMC上传完电子流水后，通知ATMVH ATMVH再向ATMV下载电子流水
	 */
	public void  requireDownloadJournal() {
		int iIndex = 0, jIndex = 0;
		try {
//			PubTools.log.info("开始查询分行电子流水文件服务器的IP地址。。。。。。。。。。。。。。");
			//sqlMapper = Dboperator.getSqlMapper();
			// 获取设备列表
			this.getTermListFromRequestList(this.journalList);
			if (this.alTermList.size() == 0) {	
				this.responseMsg = "设备编号为空" ;
				PubTools.log.info(this.responseMsg);
				return ;
			}
			
			
			Map mDevVerMap = queryDevVersionInfo();//20121109 xq add 查询设备当前大于5340版本集合
			
			//----------------------------20121109 xq add 先过滤掉向ATMC提取的设备，文件提取完后，再判断是否存在需向ATMV提取-------//
			// 先判断是否含有当天的流水，如果有，则先向C发起上传请求，上传完再向V查询数据
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
					isGetFromAtmc = true; //当天的直接从ATMC提取
				}
				if(sGetMethod.equals("1")){
					isGetFromAtmc = true;//页面有勾选从ATMC提前的直接从ATMC提取
				}
				if(!"".equals(sCurrVer)){
					isGetFromAtmc = true;//软件版本大于等于5340的直接从ATMC提取
					alTermList.remove(hmTermInfo);
				}
			    
			     //20120829 xq add 如果提取为当天的 或者有直接从ATMC提取标识的 都需从ATMC提取该文件
			     //20121109 xq  向ATMC提取条件: 1、当天的文件 2、提取方式为总行直接提取 3、软件版本大于5340
				if (isGetFromAtmc) {
					vecCurDateDevInfo.addElement(sTermCode);
					vecCurDateDevInfo.addElement(sFileTime);
					//如果有当天流水，则先向C端发上传请求，如果上传不成功的话，则将当前任务队列中上传当天该设备的的信息删除
					if (!getFileFromATMC(vecCurDateDevInfo)) {
						
//						 this.journalList = this.removeStr(this.journalList,sTermCode+"@"+sFileTime+"#" );
//						    PubTools.log.error("设备编号为"+sTermCode+" 当天的流水不存在，除去该设备当天流水提取任务," 
//						    		           + "   除去该任务后的任务队列为:"+this.journalList);
					}
					//任务处理完后 如果含有设备的提取信息 清除任务向量
					if(vecCurDateDevInfo.size() != 0){
					   vecCurDateDevInfo.clear();
					}
				}
			}
			
			this.journalList = changeLeftDevList(alTermList);//将不需要到分行ATMV提取流水的设备过滤掉
			if(journalList==null||"".equals(journalList)){//如果为空的话 则不需要向分行V做任何提取操作
				return;
			}
			//--------------------------------end-----------------------------------------------------------------------------
			
	
			
			
			// 根据设备号列表查询设备所在的分行的电子流水文件服务器地址
			String branchJournalServerIP = "";
			// 根据设备号查询设备所在的分行电子流水文件服务器
			// 如果所有的设备所属分行一致，则为成功，否则提示不在同一个分行
			 Iterator itTermList = this.hmHashMap.keySet().iterator();
			 iIndex = 0 ;
			while (itTermList.hasNext()) {
				String sTermCode = (String) itTermList.next();
				String querySql = "select branch_node from branchjournal_msg where branch_code "
						      + " in (select dev_branch1 from dev_bmsg where dev_code='" + sTermCode + "')";
				PubTools.log.debug(querySql);
				// 对比根据设备号查询到的分行的IP是否一致
				List resultList = JdbcFactory.queryForList(querySql);
				if (resultList != null && resultList.size() > 0) {
					Object[] resultArray = resultList.toArray();
					if (iIndex == 0){
						branchJournalServerIP = ((HashMap) resultArray[0]).get("branch_node").toString().trim();	
					}else {
						if (!branchJournalServerIP.equalsIgnoreCase(((HashMap) resultArray[0]).get("branch_node")
								.toString().trim())) {
							this.responseMsg = "设备不在同一个分行,只能查询同一分行的设备" ;
							PubTools.log.info(this.responseMsg);
							return ;
						}
					}
				} else {
					this.responseMsg = "设备[" + sTermCode + "]对应的分行信息不存在" ;
					PubTools.log.info(this.responseMsg);
					return ;
				}
				iIndex ++ ; 
			}
			if (branchJournalServerIP.equals("")) {
				this.responseMsg = "查询分行ATM电子流水文件服务器的IP地址失败！！" ;
				PubTools.log.info(this.responseMsg );
				return ;
			}

			// 向ATMV发送电子流水列表请求报文
			ImmediatelyDownloadJournalHandler immediatelyDownloadJournalHandler = new ImmediatelyDownloadJournalHandler();
			immediatelyDownloadJournalHandler.setJournalList(this.journalList);
			immediatelyDownloadJournalHandler.setBranchJournalServerIP(branchJournalServerIP);

			
			//-----------------------20121109 xq 注释  将该部分内容上移 即先过滤掉向ATMC提取的设备，从ATMC提取完后，然后再ATMV提取-----//
//			// 先判断是否含有当天的流水，如果有，则先向C发起上传请求，上传完再向V查询数据
//			Vector vecCurDateDevInfo = new Vector();
//			for (iIndex = 0; iIndex < alTermList.size(); iIndex++) {
//				HashMap hmTermInfo = (HashMap) alTermList.get(iIndex);
//				//20120829 xq add 如果提取为当天的 或者有直接从ATMC提取标识的 都需从ATMC提取该文件
//				if (hmTermInfo.get("fileTime").equals(PubTools.getSystemDate())||sGetMethod.equals("1")) {
//					vecCurDateDevInfo.addElement(hmTermInfo.get("termCode"));
//					vecCurDateDevInfo.addElement(hmTermInfo.get("fileTime"));
//					String sTermCode = ""+hmTermInfo.get("termCode");
//					String sFileTime = ""+hmTermInfo.get("fileTime");
//					//如果有当天流水，则先向C端发上传请求，如果上传不成功的话，则将当前任务队列中上传当天该设备的的信息删除
//					if (!getFileFromATMC(vecCurDateDevInfo)) {
////						 this.journalList = this.removeStr(this.journalList,sTermCode+"@"+sFileTime+"#" );
////						    PubTools.log.error("设备编号为"+sTermCode+" 当天的流水不存在，除去该设备当天流水提取任务," 
////						    		           + "   除去该任务后的任务队列为:"+this.journalList);
//					}
//					//任务处理完后 如果含有当天的设备的提取信息 清除任务向量
//					if(vecCurDateDevInfo.size() != 0){
//					   vecCurDateDevInfo.clear();
//					}
//				}
//			}
			
			//---------------------------------20121109 end------------------------------------------------------------------//
			
			// 如果列表不为空，则表示含有当天的流水，则先向C端发上传请求
//			if (vecCurDateDevInfo.size() != 0) {
//				if (!getFileFromATMC(vecCurDateDevInfo)) {
//  					for (iIndex = 0; iIndex < vecCurDateDevInfo.size() / 2; iIndex++) {
//						String strDevCode = (String) vecCurDateDevInfo.elementAt(iIndex * 2);
//						String strFileTime = (String) vecCurDateDevInfo.elementAt(iIndex * 2 + 1);
//						
//					    this.journalList = this.removeStr(this.journalList,strDevCode+"@"+strFileTime+"#" );
//					    PubTools.log.error("设备编号为"+strDevCode+" 当天的流水不存在，除去该设备当天流水提取任务," 
//					    		           + "   除去该任务后的任务队列为:"+this.journalList);
//					   
//					this.getFileSuccess = false;
//					return ;
//					}
//				}
//			}

			// 向V端发送上传电子流水的请求报文
			boolean isContinue = true;
			int iCount = 0;
//			int iMaxCount = 1;//20121108 xq 将以前提取不成功发送四次 修改为只提取一次
//			while (isContinue) {//20121108 xq 将以前提取不成功发送四次 修改为只提取一次
//				// 向C端下载电子流水
				boolean isSuccessGet = false;
				try {
					isSuccessGet = immediatelyDownloadJournalHandler.requireDownloadJournal();
					iCount++;
				} catch (Exception ex) {
					this.responseMsg = "查询电子流水异常";
					this.getFileSuccess = false;
					return ;
				}
				if (!isSuccessGet) {
					// 获取错误信息列表
					String faultTermList = immediatelyDownloadJournalHandler.getFaultTermList();
					PubTools.log.info("失败错误信息列表:" + faultTermList);
					// 如果返回的失败错误信息列表为空，则直接返回失败
					if (faultTermList == null || faultTermList.length() == 0) {
						this.responseMsg = "查询电子流水失败";
						this.getFileSuccess = false;
						return ;
					}
					// 如果返回的失败错误信息列表不为空，则对失败错误信息列表的设备发送上传流水文件的请求
					// 把错误信息添加到Vector中
					// 错误信息列表格式为 设备号$流水时间#设备号$流水时间#
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
						this.responseMsg = "查詢失败设备列表为空！";
						return ;
					}
					// 如果获取电子流水失败，则向C端发送上传电子流水的申请
					// 修正引起死循环的问题
					if (getFileFromATMC(vecDevInfo)){
						isContinue = false;
					}else
						isContinue = true;
				} else {
					isContinue = false;
				}
				
//				if(iCount>iMaxCount){
//					isContinue = false;
//					this.responseMsg = "向C端查詢电子流水次数大于["+iMaxCount+"]次，电子流水上传终止。";
//				}
//			}
			// 如果下载电子流水成功，则返回电子流水所在的位置
			PubTools.log.error("在上传前的journalList列表为:"+this.journalList);
			immediatelyDownloadJournalHandler.setJournalList(this.journalList);
			immediatelyDownloadJournalHandler.downloadJournal();
			this.getFileSuccess = true ;
			this.tempFilePath = immediatelyDownloadJournalHandler.getTempFilePath();  //获取临时文件夹
			this.responseMsg = immediatelyDownloadJournalHandler.getFaultTermList() ;  //获取错误信息
		} catch (Exception ex) {
			PubTools.log.error("requireDownloadJournal Catch Exception:" + ex.toString());
		}
	}

	/**
	 * 			向ATMC发送上传电子流水的请求报文
	 * 
	 * @param vecDevInfo
	 * 							ATMC设备信息
	 * @return
	 * 				是否发送成功
	 * @throws Exception
	 */
	public boolean getFileFromATMC(Vector vecDevInfo) throws Exception {
		PubTools.log.info("开始生成向ATMC提取文件任务队列");
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
			PubTools.log.info("查询ATM设备编号[" + strDevCode + "]的IP地址");
			if (resultDevList != null && resultDevList.size() > 0) {
				Object[] resultArray = resultDevList.toArray();
				for (jIndex = 0; jIndex < resultArray.length; jIndex++) {
					devNode = ((HashMap) resultArray[jIndex]).get("dev_node").toString().trim();
				}
			}
			if (devNode.equals("")) {
				this.responseMsg = "查询ATM设备编号[" + strDevCode + "]的IP地址失败";
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
				// 设置设备编号
				requireUploadJournalfileTimeTaskStruct.setDevCode(strDevCode);
				// 设置流水日期
				requireUploadJournalfileTimeTaskStruct.setFileTime(fileTime);
				// 提取当天流水是一般性提取,否则为强制提取
				if (fileTime.equals(PubTools.getSystemDate()))
					requireUploadJournalfileTimeTaskStruct.setIsEnforce(false);
				else
					requireUploadJournalfileTimeTaskStruct.setIsEnforce(true);
				// 设置设备IP
				requireUploadJournalfileTimeTaskStruct.setDevIP(devNode);
				requireUploadJournalDevTaskStruct
						.addRequireUploadJournalDevTaskStruct(requireUploadJournalfileTimeTaskStruct);
			}
			requireUploadJournalQueue.put(strDevCode + ":" + devNode, requireUploadJournalDevTaskStruct);
			devTaskStruct.put(strDevCode + ":" + devNode, "false");
			requestTasks.addElement(strDevCode + ":" + devNode);
		}

		PubTools.log.info("开始实时提取电子流水");
		ImmediatelyRequireUploadJournalHandler immediatelyRequireUploadJournalHandler = new ImmediatelyRequireUploadJournalHandler();
		immediatelyRequireUploadJournalHandler.setRequestTasks(requestTasks);
		immediatelyRequireUploadJournalHandler.setDevTaskStruct(devTaskStruct);
		immediatelyRequireUploadJournalHandler.setRequireUploadJournalQueue(requireUploadJournalQueue);
		if (!immediatelyRequireUploadJournalHandler.requireUploadJournal()) {
//			this.getFileSuccess = false;
			this.responseMsg = "从ATMC端获取文件失败:" + immediatelyRequireUploadJournalHandler.getErrInfo();
			
//			PubTools.log.info("输出的错误信息为........................");

			PubTools.log.info(this.responseMsg);
			return false;
		} else {
			PubTools.log.info("实时提取电子流水成功");
			return true;
		}
	}

	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}

	/**
	 * 		从电子流水请求列表中得到设备号及电子流水时间
	 * 
	 * @param sJournalList
	 * 						电子流水请求列表
	 */
	public void getTermListFromRequestList(String sJournalList) {
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			HashMap hmTmpTermList = new HashMap();
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			hmTmpTermList.put("termCode", sArrTermInfo[0]);
			hmTmpTermList.put("fileTime", sArrTermInfo[1]);
			hmHashMap.put(sArrTermInfo[0], "1"); // 记录设备编号
			alTermList.add(hmTmpTermList);
			sDevCodeStr= sDevCodeStr + "'"+sArrTermInfo[0]+"',";//20121108 xq add  用于在获取到需向分行V和ATMC提取文件时的设备列表，供查询出该批次设备列表的设备版本号
		}
		sDevCodeStr = sDevCodeStr + "''";//20121108 xq add 原因同上
	}

    /**
     * 去掉字符串当中特定的字符串
     * @param allStr 原字符串
     * @param removeStr  需要去掉的字符串
     * @return  去掉特定字符串后的字符串
     */
	public String  removeStr(String strAll,String strRemove){
		String strNew = "";
//		boolean flag = false;
		
		if(strAll.indexOf(strRemove)<0){
			PubTools.log.error("对不起，要去掉的字符串"+strRemove+"不在原字符串"+strAll+"中");
		}else{
		int i = strAll.indexOf(strRemove);
		strNew = strAll.substring(0, i)+strAll.substring(i+strRemove.length(), strAll.length());
		}
	 	return strNew;
	}
	
	/**20121109 xq add 主要用于设备在向ATMV提取文件前判断一下 如果设备版本大于等于5340的话，则不向分行ATMV提取
	 * 查询设备对应设备版本信息，如果设备版本大于005.003.004.000则将其返回放入到MAP中
	 * @return 版本大于005.003.004.000的设备信息
	 */
	public Map queryDevVersionInfo(){
		Map mQueryMap = new HashMap();
		String sQuerySQL = "SELECT dev_code,dev_ver FROM dev_smsg WHERE dev_code in("+this.sDevCodeStr+") AND dev_ver>='"+sDevVer+"'";
		PubTools.log.debug("查询设备当前版本SQL为:"+sQuerySQL);
		
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
			PubTools.log.error("查询设备版本信息异常，设备信息列表["+sDevCodeStr+"],"+e);
		}
		return mQueryMap;
	}
	
	/**
	 * 20121109 xq add 本方法主要用于向分行ATMV提取文件前先 过滤掉不需要到分行ATMV提取流水的设备
	 * @param lDevTrmList 过滤前列表
	 * @return 过滤后设备信息
	 */
	public String changeLeftDevList(List lDevTrmList){
		PubTools.log.debug("开始获取需向分行ATMV提取流水的设备信息!");
		String sReturnStr = "";
	    if(lDevTrmList==null||lDevTrmList.size()==0){
	    	PubTools.log.info("本次查询所有设备都不需要到分行ATMV上提取文件!");
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
		  PubTools.log.info("获取到分行ATMV提取流水的设备列表信息为["+sReturnStr+"]");
		  return sReturnStr;
	   }

	
	public static void main(String[] args) {

		// 向ATMV发送电子流水列表请求报文
		ServiceParameter.init(); // 初始化配置文件路径
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);
		WebRequestJournalMsgHandler webRequestHandler = new WebRequestJournalMsgHandler();
		webRequestHandler.requireDownloadJournal();
	}



}
