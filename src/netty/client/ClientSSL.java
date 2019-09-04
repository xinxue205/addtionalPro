package netty.client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslHandler;
import netty.protocol.MsgProtocol;
import netty.protocol.ProtocolDecoder;
import netty.protocol.ProtocolEncoder;  
  
public class ClientSSL {  
	static SSLContext sslContext;
    public ClientSSL() throws Exception {  
    	KeyStore ks = KeyStore.getInstance("JKS");
    	InputStream ksInputStream = new FileInputStream("/home/guogangj/gornix.jks");
    	ks.load(ksInputStream, "123456".toCharArray());
    	KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    	kmf.init(ks, "654321".toCharArray());
    	sslContext = SSLContext.getInstance("TLS");
    	sslContext.init(kmf.getKeyManagers(), null, null);
    }  
  
    /** 
     * 连接服务器 
     *  
     * @param port 
     * @param host 
     * @throws Exception 
     */  
    public void connect(int port, String host) throws Exception {  
        // 配置客户端NIO线程组  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            // 客户端辅助启动类 对客户端配置  
            Bootstrap b = new Bootstrap();  
            b.group(group)//  
                    .channel(NioSocketChannel.class)//  
                    .option(ChannelOption.TCP_NODELAY, true)//  
                    .handler(new MyChannelHandler());//  
            // 异步链接服务器 同步等待链接成功  
            Channel ch = b.connect(host, port).sync().channel();  
  
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
            // 等待链接关闭  
            ch.closeFuture().sync();  
            
        } finally {  
            group.shutdownGracefully();  
            System.out.println("客户端优雅的释放了线程资源...");  
        }  
    }  
  
    /** 
     * 网络事件处理器 
     */  
    private class MyChannelHandler extends ChannelInitializer<SocketChannel> {  
        @Override  
        protected void initChannel(SocketChannel ch) throws Exception {  
        	SSLEngine sslEngine = sslContext.createSSLEngine();
        	sslEngine.setUseClientMode(true); //客户端模式
        	ch.pipeline().addLast("ssl", new SslHandler(sslEngine)); //增加ssl
            // 添加自定义协议的编解码工具  
            ch.pipeline().addLast(new ProtocolEncoder());  
            ch.pipeline().addLast(new ProtocolDecoder());  
            // 处理网络IO  
            ch.pipeline().addLast(new ClientHandler());  
        }  
  
    }  
  
    public static void main(String[] args) throws Exception {  
        new ClientSSL().connect(8889, "127.0.0.1");  
  
    }  
  
}
