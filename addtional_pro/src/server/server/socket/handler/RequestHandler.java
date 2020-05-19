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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:22:08
 * @Description
 * @version 1.0 Shawn create
 */
public class RequestHandler extends Thread {
	private Socket socket;
	private Semaphore semp = null ;

	private SocketUtil su = new SocketUtil();
	
	private static int iCount = 0;//20120911 xq ��Ҫ����ͳ�Ƶ�ǰ���������߳���
	
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
	 * ���������ģ�����ʽ�������ֶεȡ�
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private boolean processRequestMsg() throws IOException {
		boolean exitflag = false;

		// Step1: ��ȡ�����ģ�������XML
		if (!su.createConnection()) {
			PubTools.log.warn("socket����ʧ��");
			return false;
		}

		while(!exitflag){
			String requestMsgXML = su.getMessage();
			if(requestMsgXML == null ||  requestMsgXML.length() < 4 )
				exitflag = true ;
			else
				exitflag = handleRequest(FileTransferQueue,requestMsgXML);	//������
		}
		
		return exitflag;
	}
	
	private boolean handleRequest(Hashtable FileTransferQueue,String msg) throws IOException {
		boolean exitflag = true; //�Ƿ��˳��̱߳�־
		
		String clientIP = su.getSocket().getRemoteSocketAddress().toString();
		
		PubTools.log.debug("�����豸["+clientIP+"]���ģ����ݣ�"+msg) ;
		
		String retMsg = ClientMessageHandler.handleClientRequest(FileTransferQueue, msg, clientIP);
		//String retMsg = "<name>Ф��</name><content>ok client</content>";
		
		FileTransferStruct fts = (FileTransferStruct)FileTransferQueue.get(clientIP);
		
		//����ļ�����ṹ�����ڣ������ļ������Ѵ�����ɣ����˳��̲߳��ر�socket
		if (fts != null) {
			// �ж��ļ��Ƿ��Ѿ��������
			if (fts.getFileOperationStruct() == null) {
//				this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
				exitflag = true;
//				bSuccessClose = true ;
			} else {
				// �жϵ�ǰ�ļ��Ƿ������һ��
				if (fts.getFileOperationStruct().getIsLastBlock()) {
//					this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
					exitflag = true;
//					bSuccessClose = true ;
				} else {
					exitflag = false;// �������һ�飬���ܹر�socket�������˳��߳�
				}
			}
		}
		
		PubTools.log.debug("���ͱ��ĵ��豸:[" + clientIP + "]") ;
		//System.out.println(retMsg) ;

		if (retMsg != null) {
//			if (this.isSSL) {
//				SSLOutputWriter.flushChannel(channel, ByteBuffer.wrap(retMsg.getBytes()));
//			} else 
//				OutputWriter.flushChannel(channel, ByteBuffer.wrap(retMsg.getBytes()));
			su.sendMessage(retMsg);	//������Ӧ����
		}
		return exitflag;
	} 
	
	public void run() {
		try {
			
			PubTools.log.info("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]�ļ���������߳̿�ʼ����");
			
			processRequestMsg();

			PubTools.log.info("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]�ļ���������̴߳������");
		} catch (Exception e) {
			PubTools.log.error("IP:["+su.getSocket().getRemoteSocketAddress().toString()+"]�ļ���������̷߳����쳣:" ) ;
			e.printStackTrace();
		}finally{
			try{
				su.closeConnection();
	//			this.semp.release();
				
				String clientIP = su.getSocket().getRemoteSocketAddress().toString();
				// �ر��ļ����������
				FileTransferStruct fileTransferStruct = (FileTransferStruct) FileTransferQueue.get(clientIP);
				if (fileTransferStruct != null) {
					FileOperationStruct fileOperationStruct = fileTransferStruct.getFileOperationStruct();
					if (fileOperationStruct != null) {
						fileOperationStruct.closeFileInputStream();
						fileOperationStruct.closeFileOutputStream();
					}
				}	
			}catch(Exception e){
				PubTools.log.error("�ͷ���Դ�쳣��",e);
			}finally{
				RequestController.handleSemp(semp, false);
			}
//			if ( !bSuccessClose )
//				this.hmLinkedDevQueue.put(clientIP.substring(0,clientIP.indexOf(":")), "close") ;
		}
	}

	


}
