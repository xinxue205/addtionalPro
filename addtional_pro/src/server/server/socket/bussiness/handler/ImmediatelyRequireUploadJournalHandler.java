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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:49:07
 * @Description
 * @version 1.0 Shawn create
 */
public class ImmediatelyRequireUploadJournalHandler {

	private Hashtable devTaskStruct = null ;   //���ڴ���豸��ȡ�������
	
	private Vector requestTasks = null ;   //

	private Hashtable requireUploadJournalQueue = null ;
	
	private String errInfo= "��ȡ������ˮʧ��:"  ;
	
	private int iTryCount = 0 ;

	public String getDevTaskStatus(String devTaskKey) {
		return (String) devTaskStruct.get(devTaskKey);
	}
	
	/**
	 * 		��ȡ��ȡʧ����Ϣ
	 * 
	 * @return
	 * 				��ȡ��ˮʧ����Ϣ
	 */
	public String getErrInfo(){
		return errInfo ;
	}

	/**
	 * 			���豸����ʵʱ��ȡ������ˮ����
	 * 
	 * @return
	 * 				�Ƿ��д���ʧ�ܵ��豸��Ϣ  true ���ɹ�  false ��ʧ�ܵ� 
	 * @throws Exception
	 */
	public boolean requireUploadJournal() throws Exception {
		
		boolean tradeSuccess = true ;
		
		List listThread = new ArrayList();
		//������ȡ��������豸������ˮ������ȡ����
		for (int i = 0; i < requestTasks.size(); i++) {
			String devTaskKey = (String) requestTasks.elementAt(i);
			ImmediatelyRequireUploadJournalThread immediatelyRequireUploadJournalThread = new ImmediatelyRequireUploadJournalThread();
			RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct = (RequireUploadJournalDevTaskStruct) requireUploadJournalQueue
					.get(devTaskKey);
			immediatelyRequireUploadJournalThread.setRequireUploadJournalDevTaskStruct(requireUploadJournalDevTaskStruct);
			immediatelyRequireUploadJournalThread.start();
			//����ȡ�豸�̷߳ŵ�������
			listThread.add(immediatelyRequireUploadJournalThread);
		}
		//�������е���ȡ�豸��ˮ�߳� �Ƿ�ȫ���������
		while( listThread.size() != 0){
			for(int i = 0; i < listThread.size(); i++){
				//�����ȡ�豸��ˮ�߳� �Ƿ��Ѿ��������
				ImmediatelyRequireUploadJournalThread immediatelyRequireUploadJournalThread =(ImmediatelyRequireUploadJournalThread)listThread.get(i) ;
				if (immediatelyRequireUploadJournalThread.getTradeSuccess() ){//�������
					if (immediatelyRequireUploadJournalThread.getRemainTasksCount() != 0 ){  
						//���̻߳��ж��ٸ�����δ���  
						tradeSuccess = false ;
						RequireUploadJournalDevTaskStruct requireUploadJournalDevTaskStruct = immediatelyRequireUploadJournalThread.getRequireUploadJournalDevTaskStruct() ;
						int iCount =  requireUploadJournalDevTaskStruct.getSize() ;
						List requireUploadJournalfileTimeTaskList = requireUploadJournalDevTaskStruct.getRequireUploadJournalDevTaskStruct() ;
						for(int iIndex = 0 ; iIndex < iCount  ; iIndex++ ){
							RequireUploadJournalfileTimeTaskStruct requireUploadJournalfileTimeTaskStruct = (RequireUploadJournalfileTimeTaskStruct)requireUploadJournalfileTimeTaskList.get(iIndex) ;
							if ( iIndex < immediatelyRequireUploadJournalThread.getRemainTasksCount())
								continue ;
							else
								errInfo += " �豸��:" + requireUploadJournalfileTimeTaskStruct.getDevCode() + " ʱ��:" + requireUploadJournalfileTimeTaskStruct.getFileTime() ;
						}
					}
					listThread.remove(i) ;
				}
			}
			 
			//����ʧ�ܳ��Դ���������  2010-04-01
			iTryCount ++ ;
			if ( iTryCount < JournalServerParams.AtmcJournalServerConnectionTimeout / (2000) ){
				Thread.sleep(1000) ;
			}else{
				PubTools.log.error("ʵʱ��ȡ�豸������ˮ��ʱ,�߳��˳�");
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
