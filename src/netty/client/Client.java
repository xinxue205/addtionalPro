package netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.protocol.MsgProtocol;
import netty.protocol.ProtocolDecoder;
import netty.protocol.ProtocolEncoder;  
  
public class Client {  
  
    /** 
     * ���ӷ����� 
     *  
     * @param port 
     * @param host 
     * @throws Exception 
     */  
    public void connect(int port, String host) throws Exception {  
        // ���ÿͻ���NIO�߳���  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            // �ͻ��˸��������� �Կͻ�������  
            Bootstrap b = new Bootstrap();  
            b.group(group)//  
                    .channel(NioSocketChannel.class)//  
                    .option(ChannelOption.TCP_NODELAY, true)//  
                    .handler(new MyChannelHandler());//  
            // �첽���ӷ����� ͬ���ȴ����ӳɹ�  
            final Channel ch = b.connect(host, port).sync().channel();  
  
            new Thread() {
            	@Override
            	public void run() {
            		try {
						Thread.sleep(11555);
						ch.writeAndFlush(new MsgProtocol("i am still ok now"));
						Thread.sleep(11333);
						ch.writeAndFlush(new MsgProtocol("i'll close connect soon, bye!") );
						ch.close().sync();
            		} catch (InterruptedException e) {
            			e.printStackTrace();
            		}
            	}
            }.start();
            // �ȴ����ӹر�  
            ch.closeFuture().sync();  
            
        } finally {  
            group.shutdownGracefully();  
            System.out.println("�ͻ������ŵ��ͷ����߳���Դ...");  
        }  
    }  
  
    /** 
     * �����¼������� 
     */  
    private class MyChannelHandler extends ChannelInitializer<SocketChannel> {  
        @Override  
        protected void initChannel(SocketChannel ch) throws Exception {  
            // ����Զ���Э��ı���빤��  
            ch.pipeline().addLast(new ProtocolEncoder());  
            ch.pipeline().addLast(new ProtocolDecoder());  
            // ��������IO  
            ch.pipeline().addLast(new ClientHandler());  
        }  
  
    }  
  
    public static void main(String[] args) throws Exception {  
        new Client().connect(8889, "127.0.0.1");  
  
    }  
  
}
