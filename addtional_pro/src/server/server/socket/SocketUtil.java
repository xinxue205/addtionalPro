/**
 * 
 */
package server.server.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:20:22
 * @Description
 * @version 1.0 Shawn create
 */
public class SocketUtil {
	//protected final Log log = LogFactory.getLog(getClass());
	
	private Socket socket = null;
	private String SERVER_IP;
	private int SERVER_PORT;
	
	private int iTimeOut = 0 ;
	
	public SocketUtil(){
		
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	

	/**
	 * ���캯������ʼ��server_ip��server_port
	 * @param server_ip
	 * @param server_port
	 */
	public SocketUtil(String server_ip, int server_port){
		this.SERVER_IP = server_ip;
		this.SERVER_PORT = server_port;
	}
	
	public SocketUtil(String server_ip, int server_port,int iTimeOut){
		this.SERVER_IP = server_ip;
		this.SERVER_PORT = server_port;
		this.iTimeOut = iTimeOut ;
	}
	
	
	public boolean closeConnection(){
		if(socket.isConnected()){
			try {
				socket.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * ��������
	 * @return
	 */
	public boolean createConnection(){
		try {
			//���socketΪ�գ��򴴽��µ�socket����
			SocketAddress sa;
			if(socket == null){
				PubTools.log.debug("Socket is null, create a new Socket()");
				socket = new Socket();
				
			}else{
				PubTools.log.debug("socket ��Ϊnull");
			}
			
			//���socket�����ӣ�ֱ�ӷ���true
			if(socket.isConnected()) {
				return true;
			}
			
			PubTools.log.info("socket not connected, start connecting...");
			
			sa = new InetSocketAddress(SERVER_IP, SERVER_PORT );
			if (iTimeOut == 0 ){
				socket.connect(sa); //���ӿͻ��� ��ʱʱ��Ϊ5��
			}else{
				socket.connect(sa,iTimeOut); //���ӿͻ��� ��ʱʱ��Ϊ5��
			}

			PubTools.log.info("Socket connected is [" + socket.isConnected() + "]");
		} catch (UnknownHostException e) {
			PubTools.log.error(e);
			return false;
		} catch (IOException e) {
			PubTools.log.error(e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * ������Ϣ
	 * @param msg
	 * @return
	 */
	public boolean sendMessage(String msg){
		if(socket.isConnected() == false) return false;
		
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			//dos.writeUTF(msg);
			dos.write(msg.getBytes(),0,msg.getBytes().length);
			dos.flush();
			
		} catch (IOException e) {
			PubTools.log.error(e);
			return false;
		}
		
		return true;
	}
	
	/**
	 * ��ȡ����
	 * @return
	 */
	public String getMessage(){
		String msg = "";
		byte []readBytes = new byte[JournalServerParams.JournalServerMsgContainsDataMax+1];
		if ( socket == null || socket.isConnected() == false) {
			PubTools.log.error("Socket is Null") ;
			return msg;
		}
		DataInputStream dis = null ;
		try {
			dis = new DataInputStream(socket.getInputStream());
			int iTotalLen = 0;
			while (true) {
				int iReadLen = 0;
				iReadLen = dis.read(readBytes);
				if (iReadLen == -1) {
					break;
				} else {
					iTotalLen += iReadLen;
					msg += new String(readBytes, 0, iReadLen);
					if (iTotalLen < 4){
						continue;
					}
					if (Integer.parseInt(msg.substring(0, 4)) + 4 <= iTotalLen)
						break;
				}
			}
			/*int iTotalLen = 0;
			char[]  chars = new char[JournalServerParams.JournalServerMsgContainsDataMax+1];
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			iTotalLen = br.read(chars);
			if (iTotalLen==-1){
				return null;
			} else {
				msg = new String(chars);
			}*/
		} catch (IOException e) {
			PubTools.log.error(e);
		}
		return msg;
	}
	
	public SocketUtil(Socket s){
		socket = s;
	}

	public String getSERVER_IP() {
		return SERVER_IP;
	}

	public void setSERVER_IP(String server_ip) {
		SERVER_IP = server_ip;
	}

	public int getSERVER_PORT() {
		return SERVER_PORT;
	}

	public void setSERVER_PORT(int server_port) {
		SERVER_PORT = server_port;
	}


}
