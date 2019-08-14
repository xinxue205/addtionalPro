package netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.MsgProtocol;
 
//用于读取客户端发来的信息
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
 
	// 客户端与服务端，连接成功的售后
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送SmartCar协议的消息
		// 要发送的信息
		String data = "I am client ...";
//		// 获得要发送信息的字节数组
//		byte[] content = data.getBytes();
//		// 要发送信息的长度
//		int contentLength = content.length;
 
		MsgProtocol protocol = new MsgProtocol(data);
 
		ctx.writeAndFlush(protocol);
	}
 
	// 只是读数据，没有写数据的话
	// 需要自己手动的释放的消息
	@Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
			// 用于获取客户端发来的数据信息
			MsgProtocol body = (MsgProtocol) msg;
			System.out.println("Client接收到的信息 :" + body.getContentStr());
 
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
