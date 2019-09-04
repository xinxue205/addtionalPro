package netty.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.SocketChannel;  
import io.netty.channel.socket.nio.NioServerSocketChannel;  
import io.netty.handler.logging.LogLevel;  
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import netty.protocol.ProtocolDecoder;
import netty.protocol.ProtocolEncoder;  
  
public class ServerSSL {  
	static SSLContext sslContext;
    public ServerSSL() throws Exception {  
    	KeyStore ks = KeyStore.getInstance("JKS");
    	InputStream ksInputStream = new FileInputStream("/home/guogangj/gornix.jks");
    	ks.load(ksInputStream, "123456".toCharArray());
    	KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    	kmf.init(ks, "654321".toCharArray());
    	sslContext = SSLContext.getInstance("TLS");
    	sslContext.init(kmf.getKeyManagers(), null, null);
    }  
  
    public void bind(int port) throws Exception {  
        // 配置NIO线程组  
//        EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup bossGroup = new EpollEventLoopGroup();//epoll实现
        EventLoopGroup workerGroup = new NioEventLoopGroup();//nio实现
        try {  
            // 服务器辅助启动类配置  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)  
                    .channel(NioServerSocketChannel.class)  
                    .handler(new LoggingHandler(LogLevel.INFO))  
                    .childHandler(new ChildChannelHandler())//  
                    .option(ChannelOption.SO_BACKLOG, 1024) // 设置tcp缓冲区 // (5)  
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)  
            // 绑定端口 同步等待绑定成功  
            ChannelFuture f = b.bind(port).sync(); // (7)  
            // 等到服务端监听端口关闭  
            f.channel().closeFuture().sync();  
        } finally {  
            // 优雅释放线程资源  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
  
    /** 
     * 网络事件处理器 
     */  
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {  
        @Override  
        protected void initChannel(SocketChannel ch) throws Exception {  
        	SSLEngine sslEngine = sslContext.createSSLEngine();
        	sslEngine.setUseClientMode(false); //模式
        	sslEngine.setNeedClientAuth(true); //如果客户端不提供其证书结束通信(只有服务器模式有效)
//        	sslEngine.setWantClientAuth(true); //客户端是否提供证书都可以(只有服务器模式有效)
        	ch.pipeline().addLast("ssl", new SslHandler(sslEngine)); //增加ssl
            // 添加自定义协议的编解码工具  
            ch.pipeline().addLast(new ProtocolEncoder());  
            ch.pipeline().addLast(new ProtocolDecoder());  
            // 处理网络IO  
            ch.pipeline().addLast(new ServerHandler());  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
        new ServerSSL().bind(8889);  
    }  
} 
