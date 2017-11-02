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
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:02:52
 * @Description
 * @version 1.0 Shawn create
 */
public class SocketServerLauncher {


	private InetSocketAddress JournalServerIP;
	
	private static int iCount = 0;//20120911 xq 主要用于统计当前并发处理线程数
	
	

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
			PubTools.log.info("创建ServerSocket成功!");
		} catch (IOException e1) {
			PubTools.log.error("创建ServerSocket失败!", e1);
			return;
		}
		Hashtable JournalFileTransferQueue = new Hashtable() ;
		HashMap chmLinkedDevQueue = new HashMap();
		
		String sExceptIP = JournalServerParams.sAtmvhIP  ;
		//如果是分行 则只获取总行的IP
		if ( !JournalServerParams.JournalServerType.equals("branch") ){
				sExceptIP +=  ",11.152.36.107,11.152.36.108,11.152.36.110,";// +  queryForBranchIP() ;
		}
		
		PubTools.log.info("忽略IP地址为:" + sExceptIP ) ;
		
		PubTools.log.info("-------> 文件传输守护进程已启动 [" + journalServerPort + "] <--------");
		try {
			final Semaphore semp = new Semaphore(JournalServerParams.JournalServerAcceptCounts); 
			
			//20130122 xq add 主要用于当信号量达到最大并发数时，限制直接返回异常码给ATMC的信号量控制
			final Semaphore sempOutCount = new Semaphore(JournalServerParams.JournalServerAcceptCounts*2); 
//			final Semaphore semp = new Semaphore(0); 
			while (true) {
				PubTools.log.debug("正在等待客户端请求...");
//				semp.acquire() ; //20120911 xq 注释 将获取一个信号量由父线程修改为由子线程获取
//				PubTools.log.debug("获取到资源") ;
//				handleSemp(semp,true); //20121107注释 将获取信号量修改为在 获取到连接之后获取
				Socket clientSocket = serverSocket.accept();
				
				//20121107 xq add 先获取剩余信号量，如果剩余信号量为0的时候，将获取到的Socket 直接close掉
				//修改该内容主要原因:如果是在获取链接之前将客户端的Socket断掉，会导致C端链接时间过长，引起通讯故障
				long lLeftSemp = semp.permits();
				boolean isContinue = false;
				if(lLeftSemp==0){
					try{
						//-------------20130122 xq add 如果该进入到直接返回处理时 信号量为0，则直接返回
					   long lLeftSempOutCount = sempOutCount.permits();
					   if(lLeftSempOutCount==0){
						   PubTools.log.info("并发数已经达到要求后处理剩余信号量为0!");
						   clientSocket.close();
						   clientSocket=null;
						   continue ;
					   }
					   handleSempOutCount(sempOutCount,true);
					   //------------------------end----------------------------------------------------//
					   
					   ClientMessageErrorHandle clientMessageErrorHandle = new ClientMessageErrorHandle(clientSocket,sempOutCount);//20121127 xq add 如果超过最大并发数，需直接将返回码返回给C端，不需要C端等待超时
					   clientMessageErrorHandle.start();
					   isContinue = true;
					   PubTools.log.info("当前信号量为0,不处理发送过来的请求，直接返回异常码后关闭该Socket!");
					}catch(Exception e){
						PubTools.log.error("关闭超过信号量Socket异常：",e);
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
				
				handleSemp(semp,true);//如果剩余信号量不为0，则先获取一个信号量再处理该模块内容
				
				//--------------------------------------------end---------------------------------
				
				
				PubTools.log.debug("--- 接收到客户端的请求 ---");
				try {
					//创建新的FileServer线程来处理
					PubTools.log.debug("启动新的线程处理...");
					
//					//2010-04-24 加入IP是否已经处理连接状态的判断 如果已处理连接 则不允许更新的连接接入
					//判断如果是总行的IP或者是分行的IP，则不受限制
					String clientIP = clientSocket.getRemoteSocketAddress().toString();
					clientIP = clientIP.substring(0, clientIP.indexOf(":")) ;
					//2011-10-31 xq 用于处理获取的地址带"/" ，需去掉
					PubTools.log.debug("处理前的IP地址为:"+clientIP);
					if(clientIP.indexOf("/")>=0){
						clientIP = clientIP.substring(1, clientIP.length());
					}
					PubTools.log.debug("处理后的IP地址为:"+clientIP);
					if ( sExceptIP.indexOf(clientIP) < 0  ){
						RequestHandler requestHandler = (RequestHandler)chmLinkedDevQueue.get(clientIP) ;
						if ( requestHandler != null ){
							Socket socket = requestHandler.getSocket() ;
							if ( !socket.isClosed() ){
								socket.close() ;
//								semp.release() ;//20120911 xq 注释 该处会引起信号量过多的释放，导致并发数不受控制，注释掉，修改其获取和释放方式
								requestHandler = null ;
							}
						}
					}
//					String sState = chmLinkedDevQueue.get(clientIP.substring(0, clientIP.indexOf(":"))) == null ? null : chmLinkedDevQueue
//							.get(clientIP.substring(0, clientIP.indexOf(":"))).toString();
//					if (sState != null) {
//						if (sState.equals("running")) {
//							PubTools.log.warn("IP:[" + clientIP.substring(0, clientIP.indexOf(":")) + "]正处理连接状态，不允许该IP更多的连接接入");
//							continue;
//						}
//					}
//					chmLinkedDevQueue.put(clientIP.substring(0, clientIP.indexOf(":")), "running") ;
					
					RequestHandler requestHandler = new RequestHandler(semp,clientSocket,JournalFileTransferQueue,chmLinkedDevQueue); //构造函数中自动调用了start()方法
					chmLinkedDevQueue.put(clientIP, requestHandler) ;
					PubTools.log.debug("----- 线程处理完成 ------");

				} catch (IOException e) {
					PubTools.log.error("IO异常", e);
					if (clientSocket != null && !clientSocket.isClosed()) {
						clientSocket.close();
					}
				}
			}
		} catch (IOException e) {
			PubTools.log.error("流水服务器发生异常:" + e.toString()) ;
			return ;
		} finally {
			PubTools.log.info("--------> 关闭文件传输守护进程 <-------");
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
				PubTools.log.info("开始向分行增加未上传流水记录");
				for (int iIndex = 0; iIndex < resultList.size(); iIndex++) {
					String sBranchCode = ((HashMap) resultList.get(iIndex)).get("branch_node").toString().trim();
					sBranchNodeList += sBranchCode + ",";
				}
			}
		} catch (Exception e) {
			PubTools.log.error("通知ATM上传电子流水出现异常" + e);
		}
		return sBranchNodeList;
	}
	
	
	/**20120911 xq 用于将信号量计数器放入到线程启动和关闭步骤，统一其执行顺序 防止因过多释放导致并发数不受控制
	 * 
	 * @param bAcquire true 增加一个请求 false 释放一个请求
	 * @return true 处理成功 false 处理失败
	 */
	public static void handleSemp(Semaphore semp, boolean bAcquire){
//		synchronized (JournalServerRequestController.class){
			if(bAcquire){
				try {
					semp.acquire() ;
					iCount++;
					PubTools.log.info("当前并发数为["+iCount+"]");
					PubTools.log.debug("当前获取一个信号量，当前剩余信号量为["+semp.permits()+"]");
				} catch (InterruptedException e) {
					PubTools.log.error("获取信号量发生异常:", e) ;
					return;
				}
			} else{
				semp.release();
				PubTools.log.info("当前释放一个信号量，当前剩余信号量为["+semp.permits()+"]");
				iCount--;
			}
//		}
		return ;
	}
	
	/**20130122 xq 用于将信号量计数器放入到线程启动和关闭步骤，统一其执行顺序 防止因过多释放导致并发数不受控制
	 * 
	 * @param bAcquire true 增加一个请求 false 释放一个请求
	 * @return true 处理成功 false 处理失败
	 */
	public static void handleSempOutCount(Semaphore semp, boolean bAcquire){
//		synchronized (JournalServerRequestController.class){
			if(bAcquire){
				try {
					semp.acquire() ;
					iCount++;
					PubTools.log.info("当前达到并发数后，异常返回处理并发数为["+iCount+"]");
					PubTools.log.debug("当前达到并发数后，异常返回处理获取一个信号量，剩余信号量为["+semp.permits()+"]");
				} catch (InterruptedException e) {
					PubTools.log.error("获取信号量发生异常:", e) ;
					return;
				}
			} else{
				semp.release();
				PubTools.log.info("当前达到并发数后，异常返回处理释放一个信号量，剩余信号量为["+semp.permits()+"]");
				iCount--;
			}
//		}
		return ;
	}
		
	/**
	 * 20121127 xq add 如果超过最大并发数的话，对于连接上来的请求直接将返回码连接上来的请求
	 * @param clientSocket
	 * @return
	 */
//	public boolean sendReturnMsg(Socket clientSocket){
//	    SocketUtil su = new SocketUtil();
//	    su.setSocket(clientSocket);
//		if (!su.createConnection()) {
//			PubTools.log.warn("socket连接失败");
//			return false;
//		}
//		try{
////			String sReturnStr = "0117<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jydm>30201</jydm><sbbh>440600300169</sbbh><respcode>OK</respcode></root>";//失败则将返回码直接返回回去
//			String sRevMsg = su.getMessage();
////			String sReturnStr = ClientMessageErrorHandle.handleClientRequest(sRevMsg);
//			PubTools.log.debug("返回内容为:"+sReturnStr);
//			su.sendMessage(sReturnStr);
//		}catch(Exception e){
//			PubTools.log.error("发送返回报文失败:",e);
//		}finally{
//			su.closeConnection();
//		}
//		return true;
//	}

}
