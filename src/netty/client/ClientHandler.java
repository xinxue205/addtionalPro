package netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.MsgProtocol;
 
//���ڶ�ȡ�ͻ��˷�������Ϣ
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
 
	// �ͻ��������ˣ����ӳɹ����ۺ�
	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// ����SmartCarЭ�����Ϣ
		// Ҫ���͵���Ϣ
		String data = "I am client ...";
//		// ���Ҫ������Ϣ���ֽ�����
//		byte[] content = data.getBytes();
//		// Ҫ������Ϣ�ĳ���
//		int contentLength = content.length;
 
		MsgProtocol protocol = new MsgProtocol(data);
 
		ctx.writeAndFlush(protocol);
	}
 
	// ֻ�Ƕ����ݣ�û��д���ݵĻ�
	// ��Ҫ�Լ��ֶ����ͷŵ���Ϣ
	@Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
			// ���ڻ�ȡ�ͻ��˷�����������Ϣ
			MsgProtocol body = (MsgProtocol) msg;
			System.out.println("Client���յ�����Ϣ :" + body.getContentStr());
 
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
