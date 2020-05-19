/**
 * 
 */
package server.server.rmi;


import org.apache.log4j.Logger;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 上午11:03:20
 * @Description RMI启动类
 * @version 1.0 Shawn create
 */
public class RmiServerLauncher implements ServerImplement {
	private static Logger logUtil = Logger.getLogger(RmiServerLauncher.class);
	//Logger logUtil = Logger.getLogger(RmiJobServer.class.getName());
	//private final LogUtil logUtil = new LogUtil(RmiJobServer.class); // 日志记录器

	private static Thread rmiServerThread = null;
	private static boolean bStopServer = false; // 是否停止服务 false 停止后同守护进程自动启动服务 true 停止后守护进程不自动启动服务

	/**
	 * 重启服务
	 */
	public void restartServer() {
		this.stopServer(false);
		this.startServer();
	}

	/**
	 * 启动服务
	 */
	public void startServer() {
		try {
			if (rmiServerThread != null) {
				logUtil.error("-------->远程命令服务器正在运行中，只能开一个服务");
				return;
			} else {
				logUtil.info("-------->远程命令服务器正在启动，请稍候 <--------");
				bStopServer = false;
				rmiServerThread = new Thread() {
					public void run() {
						new RmiServer();
					}
				};
				rmiServerThread.start();
				logUtil.info("--------> 远程命令服务器启动完成!!! <-------- ");
			}
		} catch (Exception ex) {
			logUtil.error("-------->启动远程命令服务器发生异常:", ex);
			stopServer(false);
		}
	}

	/**
	 * 关闭服务
	 */
	public void stopServer(boolean bTmpStopServer) {
		try {
			bStopServer = bTmpStopServer;
			logUtil.info("--------> 远程命令服务器正在关闭，请稍候 <--------");
			if (rmiServerThread == null) {
				logUtil.error("-------->远程命令服务器已经关闭!!!");
				return;
			} else {
				rmiServerThread.interrupt();
				rmiServerThread = null;
			}
			logUtil.info("-------->远程命令服务器关闭完成!!! <--------");
		} catch (Exception ex) {
			logUtil.error("-------->关闭远程命令服务器发生异常:" + ex.toString());
		}
	}

	/**
	 * 检查服务运行状态 如果服务停止，则重启服务
	 */
	public void checkServerRunningStatus() {
		if (!bStopServer) {
			if (rmiServerThread == null) {
				logUtil.info("--------> 守护进程检测到远程命令服务器已关闭，自动启动该服务<--------");
				startServer();
			}
		}
	}

	/**
	 * 返回当前服务状态
	 */
	public Thread getServerStatus() {
		return rmiServerThread;
	}
	
	public static void main(String[] args) {
		RmiServerLauncher rjs = new RmiServerLauncher();
		rjs.startServer();
	}
}
