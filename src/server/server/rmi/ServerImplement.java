/**
 * 
 */
package server.server.rmi;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 ����11:02:18
 * @Description ��������ӿ�
 * @version 1.0 Shawn create
 */
public interface ServerImplement {
	public void startServer() ;
	
	public void stopServer(boolean bStopServer) ;
	
	public void restartServer();
	
	public void checkServerRunningStatus() ;
	
	public Thread getServerStatus() ;
}
