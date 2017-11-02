/**
 * 
 */
package server.server;

import server.server.socket.ServiceParameter;
import server.server.socket.SocketServerLauncher;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-25 ����3:28:30
 * @Description
 * @version 1.0 Shawn create
 */
public class ServerLauncher {
	public static void main(String[] args) {
		//����RMI����
		/*RmiServerLauncher rsl = new RmiServerLauncher();
		rsl.startServer();*/
		
		//����Socket����
		ServiceParameter.init();
		SocketServerLauncher ssl = new SocketServerLauncher();
		try {
			ssl.open("128.128.201.135", 13330);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
