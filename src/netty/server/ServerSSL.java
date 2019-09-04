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
        // ����NIO�߳���  
//        EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup bossGroup = new EpollEventLoopGroup();//epollʵ��
        EventLoopGroup workerGroup = new NioEventLoopGroup();//nioʵ��
        try {  
            // ��������������������  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)  
                    .channel(NioServerSocketChannel.class)  
                    .handler(new LoggingHandler(LogLevel.INFO))  
                    .childHandler(new ChildChannelHandler())//  
                    .option(ChannelOption.SO_BACKLOG, 1024) // ����tcp������ // (5)  
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)  
            // �󶨶˿� ͬ���ȴ��󶨳ɹ�  
            ChannelFuture f = b.bind(port).sync(); // (7)  
            // �ȵ�����˼����˿ڹر�  
            f.channel().closeFuture().sync();  
        } finally {  
            // �����ͷ��߳���Դ  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
  
    /** 
     * �����¼������� 
     */  
    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {  
        @Override  
        protected void initChannel(SocketChannel ch) throws Exception {  
        	SSLEngine sslEngine = sslContext.createSSLEngine();
        	sslEngine.setUseClientMode(false); //ģʽ
        	sslEngine.setNeedClientAuth(true); //����ͻ��˲��ṩ��֤�����ͨ��(ֻ�з�����ģʽ��Ч)
//        	sslEngine.setWantClientAuth(true); //�ͻ����Ƿ��ṩ֤�鶼����(ֻ�з�����ģʽ��Ч)
        	ch.pipeline().addLast("ssl", new SslHandler(sslEngine)); //����ssl
            // ����Զ���Э��ı���빤��  
            ch.pipeline().addLast(new ProtocolEncoder());  
            ch.pipeline().addLast(new ProtocolDecoder());  
            // ��������IO  
            ch.pipeline().addLast(new ServerHandler());  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
        new ServerSSL().bind(8889);  
    }  
} 
