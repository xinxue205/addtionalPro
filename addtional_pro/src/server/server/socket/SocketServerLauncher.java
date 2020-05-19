/**
 * 
 */
package server.server.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import server.server.socket.handler.ClientMessageErrorHandle;
import server.server.socket.handler.RequestHandler;
import server.util.JdbcFactory;
import server.util.PubTools;

import net.sourceforge.jtds.jdbc.Semaphore;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:02:52
 * @Description
 * @version 1.0 Shawn create
 */
public class SocketServerLauncher {


	private InetSocketAddress JournalServerIP;
	
	private static int iCount = 0;//20120911 xq ��Ҫ����ͳ�Ƶ�ǰ���������߳���
	
	

	public SocketServerLauncher() {
	}

	public void close() {
		this.JournalServerIP = null;
	}

	public InetSocketAddress getJournalServerIP() {
		return this.JournalServerIP;
	}

	public void open(String journalServerIP, int journalServerPort) throws Exception {
		this.JournalServerIP = PubTools.resolveAddressParam(journalServerIP, journalServerPort);
		
		
		//-------------------------------------------------------------------------------
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(journalServerPort);
			PubTools.log.info("����ServerSocket�ɹ�!");
		} catch (IOException e1) {
			PubTools.log.error("����ServerSocketʧ��!", e1);
			return;
		}
		Hashtable JournalFileTransferQueue = new Hashtable() ;
		HashMap chmLinkedDevQueue = new HashMap();
		
		String sExceptIP = JournalServerParams.sAtmvhIP  ;
		//����Ƿ��� ��ֻ��ȡ���е�IP
		if ( !JournalServerParams.JournalServerType.equals("branch") ){
				sExceptIP +=  ",11.152.36.107,11.152.36.108,11.152.36.110,";// +  queryForBranchIP() ;
		}
		
		PubTools.log.info("����IP��ַΪ:" + sExceptIP ) ;
		
		PubTools.log.info("-------> �ļ������ػ����������� [" + journalServerPort + "] <--------");
		try {
			final Semaphore semp = new Semaphore(JournalServerParams.JournalServerAcceptCounts); 
			
			//20130122 xq add ��Ҫ���ڵ��ź����ﵽ��󲢷���ʱ������ֱ�ӷ����쳣���ATMC���ź�������
			final Semaphore sempOutCount = new Semaphore(JournalServerParams.JournalServerAcceptCounts*2); 
//			final Semaphore semp = new Semaphore(0); 
			while (true) {
				PubTools.log.debug("���ڵȴ��ͻ�������...");
//				semp.acquire() ; //20120911 xq ע�� ����ȡһ���ź����ɸ��߳��޸�Ϊ�����̻߳�ȡ
//				PubTools.log.debug("��ȡ����Դ") ;
//				handleSemp(semp,true); //20121107ע�� ����ȡ�ź����޸�Ϊ�� ��ȡ������֮���ȡ
				Socket clientSocket = serverSocket.accept();
				
				//20121107 xq add �Ȼ�ȡʣ���ź��������ʣ���ź���Ϊ0��ʱ�򣬽���ȡ����Socket ֱ��close��
				//�޸ĸ�������Ҫԭ��:������ڻ�ȡ����֮ǰ���ͻ��˵�Socket�ϵ����ᵼ��C������ʱ�����������ͨѶ����
				long lLeftSemp = semp.permits();
				boolean isContinue = false;
				if(lLeftSemp==0){
					try{
						//-------------20130122 xq add ����ý��뵽ֱ�ӷ��ش���ʱ �ź���Ϊ0����ֱ�ӷ���
					   long lLeftSempOutCount = sempOutCount.permits();
					   if(lLeftSempOutCount==0){
						   PubTools.log.info("�������Ѿ��ﵽҪ�����ʣ���ź���Ϊ0!");
						   clientSocket.close();
						   clientSocket=null;
						   continue ;
					   }
					   handleSempOutCount(sempOutCount,true);
					   //------------------------end----------------------------------------------------//
					   
					   ClientMessageErrorHandle clientMessageErrorHandle = new ClientMessageErrorHandle(clientSocket,sempOutCount);//20121127 xq add ���������󲢷�������ֱ�ӽ������뷵�ظ�C�ˣ�����ҪC�˵ȴ���ʱ
					   clientMessageErrorHandle.start();
					   isContinue = true;
					   PubTools.log.info("��ǰ�ź���Ϊ0,�������͹���������ֱ�ӷ����쳣���رո�Socket!");
					}catch(Exception e){
						PubTools.log.error("�رճ����ź���Socket�쳣��",e);
					}finally{
						if(clientSocket!=null){
							 clientSocket.close();
							 clientSocket = null;
//							 continue;
						}
//						 continue;
					}
				}
				if(isContinue){
					continue;
				}
				
				handleSemp(semp,true);//���ʣ���ź�����Ϊ0�����Ȼ�ȡһ���ź����ٴ����ģ������
				
				//--------------------------------------------end---------------------------------
				
				
				PubTools.log.debug("--- ���յ��ͻ��˵����� ---");
				try {
					//�����µ�FileServer�߳�������
					PubTools.log.debug("�����µ��̴߳���...");
					
//					//2010-04-24 ����IP�Ƿ��Ѿ���������״̬���ж� ����Ѵ������� ��������µ����ӽ���
					//�ж���������е�IP�����Ƿ��е�IP����������
					String clientIP = clientSocket.getRemoteSocketAddress().toString();
					clientIP = clientIP.substring(0, clientIP.indexOf(":")) ;
					//2011-10-31 xq ���ڴ����ȡ�ĵ�ַ��"/" ����ȥ��
					PubTools.log.debug("����ǰ��IP��ַΪ:"+clientIP);
					if(clientIP.indexOf("/")>=0){
						clientIP = clientIP.substring(1, clientIP.length());
					}
					PubTools.log.debug("������IP��ַΪ:"+clientIP);
					if ( sExceptIP.indexOf(clientIP) < 0  ){
						RequestHandler requestHandler = (RequestHandler)chmLinkedDevQueue.get(clientIP) ;
						if ( requestHandler != null ){
							Socket socket = requestHandler.getSocket() ;
							if ( !socket.isClosed() ){
								socket.close() ;
//								semp.release() ;//20120911 xq ע�� �ô��������ź���������ͷţ����²��������ܿ��ƣ�ע�͵����޸����ȡ���ͷŷ�ʽ
								requestHandler = null ;
							}
						}
					}
//					String sState = chmLinkedDevQueue.get(clientIP.substring(0, clientIP.indexOf(":"))) == null ? null : chmLinkedDevQueue
//							.get(clientIP.substring(0, clientIP.indexOf(":"))).toString();
//					if (sState != null) {
//						if (sState.equals("running")) {
//							PubTools.log.warn("IP:[" + clientIP.substring(0, clientIP.indexOf(":")) + "]����������״̬���������IP��������ӽ���");
//							continue;
//						}
//					}
//					chmLinkedDevQueue.put(clientIP.substring(0, clientIP.indexOf(":")), "running") ;
					
					RequestHandler requestHandler = new RequestHandler(semp,clientSocket,JournalFileTransferQueue,chmLinkedDevQueue); //���캯�����Զ�������start()����
					chmLinkedDevQueue.put(clientIP, requestHandler) ;
					PubTools.log.debug("----- �̴߳������ ------");

				} catch (IOException e) {
					PubTools.log.error("IO�쳣", e);
					if (clientSocket != null && !clientSocket.isClosed()) {
						clientSocket.close();
					}
				}
			}
		} catch (IOException e) {
			PubTools.log.error("��ˮ�����������쳣:" + e.toString()) ;
			return ;
		} finally {
			PubTools.log.info("--------> �ر��ļ������ػ����� <-------");
			if (!serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					PubTools.log.error(e);
				}
			}
		}
		//-------------------------------------------------------------------------------
		
	}
	
	private String queryForBranchIP() {
		String sBranchNodeList = "";
		try {
			//SqlMapClient sqlMapper = Dboperator.getSqlMapper();
			String querySql = "select branch_node from  branchjournal_msg where branch_code in(" + JournalServerParams.BranchOneList + ")";
			List resultList = JdbcFactory.queryForList(querySql);
			if (resultList != null && resultList.size() > 0) {
				PubTools.log.info("��ʼ���������δ�ϴ���ˮ��¼");
				for (int iIndex = 0; iIndex < resultList.size(); iIndex++) {
					String sBranchCode = ((HashMap) resultList.get(iIndex)).get("branch_node").toString().trim();
					sBranchNodeList += sBranchCode + ",";
				}
			}
		} catch (Exception e) {
			PubTools.log.error("֪ͨATM�ϴ�������ˮ�����쳣" + e);
		}
		return sBranchNodeList;
	}
	
	
	/**20120911 xq ���ڽ��ź������������뵽�߳������͹رղ��裬ͳһ��ִ��˳�� ��ֹ������ͷŵ��²��������ܿ���
	 * 
	 * @param bAcquire true ����һ������ false �ͷ�һ������
	 * @return true ����ɹ� false ����ʧ��
	 */
	public static void handleSemp(Semaphore semp, boolean bAcquire){
//		synchronized (JournalServerRequestController.class){
			if(bAcquire){
				try {
					semp.acquire() ;
					iCount++;
					PubTools.log.info("��ǰ������Ϊ["+iCount+"]");
					PubTools.log.debug("��ǰ��ȡһ���ź�������ǰʣ���ź���Ϊ["+semp.permits()+"]");
				} catch (InterruptedException e) {
					PubTools.log.error("��ȡ�ź��������쳣:", e) ;
					return;
				}
			} else{
				semp.release();
				PubTools.log.info("��ǰ�ͷ�һ���ź�������ǰʣ���ź���Ϊ["+semp.permits()+"]");
				iCount--;
			}
//		}
		return ;
	}
	
	/**20130122 xq ���ڽ��ź������������뵽�߳������͹رղ��裬ͳһ��ִ��˳�� ��ֹ������ͷŵ��²��������ܿ���
	 * 
	 * @param bAcquire true ����һ������ false �ͷ�һ������
	 * @return true ����ɹ� false ����ʧ��
	 */
	public static void handleSempOutCount(Semaphore semp, boolean bAcquire){
//		synchronized (JournalServerRequestController.class){
			if(bAcquire){
				try {
					semp.acquire() ;
					iCount++;
					PubTools.log.info("��ǰ�ﵽ���������쳣���ش�������Ϊ["+iCount+"]");
					PubTools.log.debug("��ǰ�ﵽ���������쳣���ش����ȡһ���ź�����ʣ���ź���Ϊ["+semp.permits()+"]");
				} catch (InterruptedException e) {
					PubTools.log.error("��ȡ�ź��������쳣:", e) ;
					return;
				}
			} else{
				semp.release();
				PubTools.log.info("��ǰ�ﵽ���������쳣���ش����ͷ�һ���ź�����ʣ���ź���Ϊ["+semp.permits()+"]");
				iCount--;
			}
//		}
		return ;
	}
		
	/**
	 * 20121127 xq add ���������󲢷����Ļ���������������������ֱ�ӽ���������������������
	 * @param clientSocket
	 * @return
	 */
//	public boolean sendReturnMsg(Socket clientSocket){
//	    SocketUtil su = new SocketUtil();
//	    su.setSocket(clientSocket);
//		if (!su.createConnection()) {
//			PubTools.log.warn("socket����ʧ��");
//			return false;
//		}
//		try{
////			String sReturnStr = "0117<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jydm>30201</jydm><sbbh>440600300169</sbbh><respcode>OK</respcode></root>";//ʧ���򽫷�����ֱ�ӷ��ػ�ȥ
//			String sRevMsg = su.getMessage();
////			String sReturnStr = ClientMessageErrorHandle.handleClientRequest(sRevMsg);
//			PubTools.log.debug("��������Ϊ:"+sReturnStr);
//			su.sendMessage(sReturnStr);
//		}catch(Exception e){
//			PubTools.log.error("���ͷ��ر���ʧ��:",e);
//		}finally{
//			su.closeConnection();
//		}
//		return true;
//	}

}
