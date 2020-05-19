/**
 * 
 */
package server.server;

import server.server.socket.ServiceParameter;
import server.server.socket.SocketServerLauncher;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-25 下午3:28:30
 * @Description
 * @version 1.0 Shawn create
 */
public class ServerLauncher {
	public static void main(String[] args) {
		//启动RMI服务
		/*RmiServerLauncher rsl = new RmiServerLauncher();
		rsl.startServer();*/
		
		//启动Socket服务
		ServiceParameter.init();
		SocketServerLauncher ssl = new SocketServerLauncher();
		try {
			ssl.open("192.168.43.29", 13330);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
