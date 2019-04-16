package socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UdpServer {
    private final static int MAX_LENGTH = 1024;
    private final static int PORT = 5060;
    private static DatagramSocket datagramSocket;
    
    public static void main(String[] args) {
        try {
            init();
            while(true){
                try {
                    byte[] buffer = new byte[MAX_LENGTH];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    receive(packet);
                    String receStr = new String(packet.getData(), 0 , packet.getLength());
                    System.out.println("接收数据包" + receStr);
                    byte[] bt = new byte[packet.getLength()];
                    
                    System.arraycopy(packet.getData(), 0, bt, 0, packet.getLength());
                    System.out.println(packet.getAddress().getHostAddress() + "：" + packet.getPort() + "：" + Arrays.toString(bt));
                    packet.setData(bt);
                    response(packet);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("udp线程出现异常：" + e.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receive(DatagramPacket packet) throws Exception {
        datagramSocket.receive(packet);
    }

    public static void response(DatagramPacket packet) throws Exception {
        datagramSocket.send(packet);
    }
    
    /**
     * 初始化连接
     */
    public static void init(){
        try {
            datagramSocket = new DatagramSocket(PORT);
            System.out.println("udp服务端已经启动！");
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println("udp服务端启动失败！");
            e.printStackTrace();
        }
    }
}
