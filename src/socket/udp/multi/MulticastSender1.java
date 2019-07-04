package socket.udp.multi;

import java.net.DatagramPacket;  
import java.net.InetAddress;  
import java.net.MulticastSocket;  
  
public class MulticastSender1 {  
    private int port;  
    private String host;  
    private String data;  
  
    public MulticastSender1(String data, String host, int port) {  
        this.data = data;  
        this.host = host;  
        this.port = port;  
    }  
  
    public void send() {  
        try {  
            InetAddress ip = InetAddress.getByName(this.host);  
            DatagramPacket packet = new DatagramPacket(this.data.getBytes(), this.data.length(), ip, this.port);  
            MulticastSocket ms = new MulticastSocket();  
            ms.send(packet);  
            ms.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
        int port = 1234;  
        String host = "224.0.0.1";  
        String data = "hello world.";  
        System.out.println(data);  
        MulticastSender1 ms = new MulticastSender1(data, host, port);  
        ms.send();  
    }  
} 