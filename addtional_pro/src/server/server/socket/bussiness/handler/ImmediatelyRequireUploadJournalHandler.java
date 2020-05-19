/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import server.server.socket.JournalServerParams;
import server.server.socket.bussiness.RequireUploadJournalDevTaskStruct;
import server.server.socket.bussiness.RequireUploadJournalfileTimeTaskStruct;
import server.server.socket.bussiness.thread.ImmediatelyRequireUploadJournalThread;
import server.util.PubTools;



/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:49:07
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyRequireUploadJournalHandler {

	private Hashtable devTaskStruct = null ;   //用于存放设备提取任务队列
	
	private Vector requestTasks = null ;   //

	private Hashtable requireUploadJournalQueue = null ;
	
	private String errInfo= "提取电子流水失败:"  ;
	
	private int iTryCount = 0 ;

	public String getDevTaskStatus(String devTaskKey) {
		return (String) devTaskStruct.get(devTaskKey);
	}
	
	/**
	 * 		获取提取失败信息
	 * 
	 * @return
	 * 				提取流水失败信息
	 */
	public String getErrInfo(){
		return errInfo ;
	}

	/**
	 * 			向设备发送实时提取电子流水请求
	 * 
	 * @return
	 * 				是否有处理失败的设备信息  true 都成功  false 有失败的 
	 * @throws Exception
	 */
	public boolean requireUploadJournal() throws Exception {
		
		boolean tradeSuccess = true ;
		
		List listThread = new ArrayList();
		//根据提取的任务对设备电子流水发送提取请求
		for (int i = 0; i < requestTasks.size(); i++) {
			String devTaskKey = (String) requestTasks.elementAt(i);
			ImmediatelyRequireUploadJournalThread immediatelyRequireUploadJournalThread = new ImmediatelyRequireUploadJournalThread();
			RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct = (RequireUploadJournalDevTaskStruct) requireUploadJournalQueue
					.get(devTaskKey);
			immediatelyRequireUploadJournalThread.setRequireUploadJournalDevTaskStruct(requireUploadJournalDevTaskStruct);
			immediatelyRequireUploadJournalThread.start();
			//把提取设备线程放到队列中
			listThread.add(immediatelyRequireUploadJournalThread);
		}
		//检查队列中的提取设备流水线程 是否全部处理完成
		while( listThread.size() != 0){
			for(int i = 0; i < listThread.size(); i++){
				//检查提取设备流水线程 是否已经处理完成
				ImmediatelyRequireUploadJournalThread immediatelyRequireUploadJournalThread =(ImmediatelyRequireUploadJournalThread)listThread.get(i) ;
				if (immediatelyRequireUploadJournalThread.getTradeSuccess() ){//处理完成
					if (immediatelyRequireUploadJournalThread.getRemainTasksCount() != 0 ){  
						//该线程还有多少个任务未完成  
						tradeSuccess = false ;
						RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct = immediatelyRequireUploadJournalThread.getRequireUploadJournalDevTaskStruct() ;
						int iCount =  requireUploadJournalDevTaskStruct.getSize() ;
						List requireUploadJournalfileTimeTaskList = requireUploadJournalDevTaskStruct.getRequireUploadJournalDevTaskStruct() ;
						for(int iIndex = 0 ; iIndex < iCount  ; iIndex++ ){
							RequireUploadJournalfileTimeTaskStruct requireUploadJournalfileTimeTaskStruct = (RequireUploadJournalfileTimeTaskStruct)requireUploadJournalfileTimeTaskList.get(iIndex) ;
							if ( iIndex < immediatelyRequireUploadJournalThread.getRemainTasksCount())
								continue ;
							else
								errInfo += " 设备号:" + requireUploadJournalfileTimeTaskStruct.getDevCode() + " 时间:" + requireUploadJournalfileTimeTaskStruct.getFileTime() ;
						}
					}
					listThread.remove(i) ;
				}
			}
			 
			//增加失败尝试次数的限制  2010-04-01
			iTryCount ++ ;
			if ( iTryCount < JournalServerParams.AtmcJournalServerConnectionTimeout / (2000) ){
				Thread.sleep(1000) ;
			}else{
				PubTools.log.error("实时提取设备电子流水超时,线程退出");
				break ;
			}
		}
		return tradeSuccess ; 
	}

	public void setDevTaskStruct(Hashtable devTaskStruct) {
		this.devTaskStruct = devTaskStruct;
	}
	
	public void setRequestTasks(Vector requestTasks) {
		this.requestTasks = requestTasks;
	}

	public void setRequireUploadJournalQueue(Hashtable requireUploadJournalQueue) {
		this.requireUploadJournalQueue = requireUploadJournalQueue;
	}
}
