package socket;

import java.io.*;
import java.net.*;

public class TCPServer {
	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(6666);
		while(true){
			Socket s = ss.accept();
			System.out.println("A client connect");
			DataInputStream dis = new DataInputStream(s.getInputStream());
			System.out.println(dis.readUTF());
			dis.close();

		}
	}
}
