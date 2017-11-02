/**
 * 
 */
package server.server.socket.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;

import net.sourceforge.jtds.jdbc.Semaphore;

import org.xml.sax.SAXException;

import server.server.socket.RequestController;
import server.server.socket.SocketUtil;
import server.server.socket.tool.FileOperationStruct;
import server.server.socket.tool.FileTransferStruct;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:22:08
 * @Description
 * @version 1.0 Shawn create
 */
public class RequestHandler extends Thread {
	private Socket socket;
	private Semaphore semp = null ;

	private SocketUtil su = new SocketUtil();
	
	private static int iCount = 0;//20120911 xq 主要用于统计当前并发处理线程数
	
//	private boolean bSuccessClose = false ;
	
	private Hashtable FileTransferQueue;
//	private HashMap hmLinkedDevQueue ; 
	public boolean init() {
		
		return true;
	}
	public RequestHandler(Semaphore semp,Socket clientSocket, Hashtable JournalFileTransferQueue,HashMap chmLinkedDevQueue) throws IOException {
		socket = clientSocket;
		su.setSocket(socket);
		this.semp =  semp ;
		this.FileTransferQueue = JournalFileTransferQueue; 
//		this.hmLinkedDevQueue = chmLinkedDevQueue ;
		init();
		start();
	}
	
	public Socket getSocket(){
		return this.socket ;
	}
	
	/**
	 * 处理请求报文，检查格式、必须字段等。
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private boolean processRequestMsg() throws IOException {
		boolean exitflag = false;

		// Step1: 读取请求报文，并解析XML
		if (!su.createConnection()) {
			PubTools.log.warn("socket连接失败");
			return false;
		}

		while(!exitflag){
			String requestMsgXML = su.getMessage();
			if(requestMsgXML == null ||  requestMsgXML.length() < 4 )
				exitflag = true ;
			else
				exitflag = handleRequest(FileTransferQueue,requestMsgXML);	//处理报文
		}
		
		return exitflag;
	}
	
	private boolean handleRequest(Hashtable FileTransferQueue,String msg) throws IOException {
		boolean exitflag = true; //是否退出线程标志
		
		String clientIP = su.getSocket().getRemoteSocketAddress().toString();
		
		PubTools.log.debug("接收设备["+clientIP+"]报文，内容："+msg) ;
		
		String retMsg = ClientMessageHandler.handleClientRequest(FileTransferQueue, msg, clientIP);
		//String retMsg = "<name>肖恩</name><content>ok client</content>";
		
		FileTransferStruct fts = (FileTransferStruct)FileTransferQueue.get(clientIP);
		
		//如果文件传输结构不存在，或者文件传输已处理完成，则退出线程并关闭socket
		if (fts != null) {
			// 判断文件是否已经传输完成
			if (fts.getFileOperationStruct() == null) {
//				this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
				exitflag = true;
//				bSuccessClose = true ;
			} else {
				// 判断当前文件是否是最后一块
				if (fts.getFileOperationStruct().getIsLastBlock()) {
//					this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
					exitflag = true;
//					bSuccessClose = true ;
				} else {
					exitflag = false;// 不是最后一块，则不能关闭socket，不能退出线程
				}
			}
		}
		
		PubTools.log.debug("发送报文到设备:[" + clientIP + "]") ;
		//System.out.println(retMsg) ;

		if (retMsg != null) {
//			if (this.isSSL) {
//				SSLOutputWriter.flushChannel(channel, ByteBuffer.wrap(retMsg.getBytes()));
//			} else 
//				OutputWriter.flushChannel(channel, ByteBuffer.wrap(retMsg.getBytes()));
			su.sendMessage(retMsg);	//发送响应报文
		}
		return exitflag;
	} 
	
	public void run() {
		try {
			
			PubTools.log.info("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]文件传输服务线程开始处理");
			
			processRequestMsg();

			PubTools.log.info("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]文件传输服务线程处理完成");
		} catch (Exception e) {
			PubTools.log.error("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]文件传输服务线程发生异常:" ) ;
			e.printStackTrace();
		}finally{
			try{
				su.closeConnection();
	//			this.semp.release();
				
				String clientIP = su.getSocket().getRemoteSocketAddress().toString();
				// 关闭文件输入输出流
				FileTransferStruct fileTransferStruct = (FileTransferStruct) FileTransferQueue.get(clientIP);
				if (fileTransferStruct != null) {
					FileOperationStruct fileOperationStruct = fileTransferStruct.getFileOperationStruct();
					if (fileOperationStruct != null) {
						fileOperationStruct.closeFileInputStream();
						fileOperationStruct.closeFileOutputStream();
					}
				}	
			}catch(Exception e){
				PubTools.log.error("释放资源异常：",e);
			}finally{
				RequestController.handleSemp(semp, false);
			}
//			if ( !bSuccessClose )
//				this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
		}
	}

	


}
