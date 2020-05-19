/**
 * 
 */
package server.server.rmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;

import org.apache.log4j.Logger;

import server.server.rmi.base.IRmiService;
import server.util.LogUtil;
import server.util.PropReader;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @since 2014-7-18 上午9:25:52
 * @Description RMI服务注册类
 * @version 1.0 Shawn create
 */
public class RmiServer {
	private static Logger log = Logger.getLogger(RmiServer.class);

	public RmiServer() {
		try {
			this.log.info("----------> 注册RMI服务开始 <---------------");

			this.log.info("----------> 读取RMI服务IP地址  <---------------");
			String sServerIP = "128.128.200.68";//PropReader.getProperty("/conf/rmi.properties","serviceIP") == null ? null : PropReader.getProperty("/conf/rmi.properties", "serviceIP").trim();

			this.log.info("----------> 读取RMI服务通讯端口  <---------------");
			int iServicePort = Integer.parseInt(PropReader.getProperty("/conf/rmi.properties", "servicePort") == null ? "0"
					: PropReader.getProperty("/conf/rmi.properties", "servicePort").trim());

			this.log.info("----------> 读取RMI服务服务端口  <---------------");
			int iDataPort = Integer.parseInt(PropReader.getProperty("/conf/rmi.properties", "dataPort") == null ? "0"
					: PropReader.getProperty("/conf/rmi.properties", "dataPort").trim());

			String sServeName = "";

			if ((sServerIP == null) || (iServicePort == 0) || (iDataPort == 0)) {
				this.log.error("服务IP:" + sServerIP + " 端口:" + iServicePort
						+ "数据通讯端口:" + iDataPort + "异常，程序退出");
				return;
			}
			

			this.log.info("----------> 读取RMI服务_RMISocketFactory.setSocketFactory  <---------------");
			RMISocketFactory.setSocketFactory(new SMRMISocket(iDataPort));
			LocateRegistry.createRegistry(iServicePort);
			
			String sServiceName = "";
			String sServiceNum = "";
			
			for (int iIndex = 0; iIndex < 5000; iIndex++) {
				sServiceNum = "rmiService" + iIndex;
				sServiceName = PropReader.getProperty("/conf/rmi.properties", sServiceNum);
				if (sServiceName == null)
					break;
				
				this.log.info("开始注册第" + (iIndex + 1) + "个RMI服务,服务名:[" + sServiceName + "]");
				
				sServiceName = sServiceName.trim();
				IRmiService c = (IRmiService) Class.forName(sServiceName).newInstance();
				
				sServeName = sServiceName.substring(sServiceName.lastIndexOf(".") + 1, sServiceName.length() - 5);
				
				Naming.rebind("//" + sServerIP + ":" + iServicePort + "/" + sServeName, c);
			}

			String[] bindings = Naming.list("//" + sServerIP + ":" + iServicePort + "/" + sServeName);
			
			int length = bindings.length;
			for (int i = 0; i < length; i++) {
				this.log.info("已注册RMI服务：" + bindings[i]);
			}

			this.log.info("----------> 注册RMI服务完成 <---------------");
		} catch (Exception e) {
			this.log.error("注册RMI服务发生异常:" + e.toString());
		}
	}

	private class SMRMISocket extends RMISocketFactory {
		private int rmiPort;

		public SMRMISocket(int rmiPort) {
			this.rmiPort = rmiPort;
		}

		public Socket createSocket(String host, int port) throws IOException {
			
			return new Socket(host, port);
		}

		public ServerSocket createServerSocket(int port) throws IOException {
			if (port == 0) {
				port = rmiPort;
			}
			InetAddress addr = null;
			
			return new ServerSocket(port, 50, addr);
			
			//return new ServerSocket(port);
		}
	}
}
