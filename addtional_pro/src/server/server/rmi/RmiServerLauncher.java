/**
 * 
 */
package server.server.rmi;


import org.apache.log4j.Logger;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 ����11:03:20
 * @Description RMI������
 * @version 1.0 Shawn create
 */
public class RmiServerLauncher implements ServerImplement {
	private static Logger logUtil = Logger.getLogger(RmiServerLauncher.class);
	//Logger logUtil = Logger.getLogger(RmiJobServer.class.getName());
	//private final LogUtil logUtil = new LogUtil(RmiJobServer.class); // ��־��¼��

	private static Thread rmiServerThread = null;
	private static boolean bStopServer = false; // �Ƿ�ֹͣ���� false ֹͣ��ͬ�ػ������Զ��������� true ֹͣ���ػ����̲��Զ���������

	/**
	 * ��������
	 */
	public void restartServer() {
		this.stopServer(false);
		this.startServer();
	}

	/**
	 * ��������
	 */
	public void startServer() {
		try {
			if (rmiServerThread != null) {
				logUtil.error("-------->Զ��������������������У�ֻ�ܿ�һ������");
				return;
			} else {
				logUtil.info("-------->Զ������������������������Ժ� <--------");
				bStopServer = false;
				rmiServerThread = new Thread() {
					public void run() {
						new RmiServer();
					}
				};
				rmiServerThread.start();
				logUtil.info("--------> Զ������������������!!! <-------- ");
			}
		} catch (Exception ex) {
			logUtil.error("-------->����Զ����������������쳣:", ex);
			stopServer(false);
		}
	}

	/**
	 * �رշ���
	 */
	public void stopServer(boolean bTmpStopServer) {
		try {
			bStopServer = bTmpStopServer;
			logUtil.info("--------> Զ��������������ڹرգ����Ժ� <--------");
			if (rmiServerThread == null) {
				logUtil.error("-------->Զ������������Ѿ��ر�!!!");
				return;
			} else {
				rmiServerThread.interrupt();
				rmiServerThread = null;
			}
			logUtil.info("-------->Զ������������ر����!!! <--------");
		} catch (Exception ex) {
			logUtil.error("-------->�ر�Զ����������������쳣:" + ex.toString());
		}
	}

	/**
	 * ����������״̬ �������ֹͣ������������
	 */
	public void checkServerRunningStatus() {
		if (!bStopServer) {
			if (rmiServerThread == null) {
				logUtil.info("--------> �ػ����̼�⵽Զ������������ѹرգ��Զ������÷���<--------");
				startServer();
			}
		}
	}

	/**
	 * ���ص�ǰ����״̬
	 */
	public Thread getServerStatus() {
		return rmiServerThread;
	}
	
	public static void main(String[] args) {
		RmiServerLauncher rjs = new RmiServerLauncher();
		rjs.startServer();
	}
}
