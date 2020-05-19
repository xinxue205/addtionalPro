package netty.common;

import java.util.Date;
import java.util.UUID;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.MsgProtocol;

public class MsgChannelInboundHandler extends SimpleChannelInboundHandler<Object> {

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String id = UUID.randomUUID().toString();
    	Constant.CNL_MAP.put(ctx, id);
    	ChannelAttr channelAttr = new ChannelAttr(id, new Date());
    	ctx.channel().attr(ChannelAttr.NETTY_CHANNEL_KEY).setIfAbsent(channelAttr);
    	System.out.println("current connect count:"+Constant.CNL_MAP.size());
    	
        // Ϊ�����ӷ��ͻ�ӭ��Ϣ
    	String str = "you are welcome, " + Utils.getRemoteAddressPort(ctx);
    	MsgProtocol m = new MsgProtocol(str);  
    	ctx.writeAndFlush(m);  
    }
	
    // ���ڻ�ȡ�ͻ��˷��͵���Ϣ  
    @Override  
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        // ���ڻ�ȡ�ͻ��˷�����������Ϣ  
    	MsgProtocol body = (MsgProtocol) msg;  
        System.out.println(new Date()+" - "+Utils.getRemoteAddressPort(ctx) + " say: " + body.getContentStr());  
        // ��д���ݸ��ͻ���  
//        String str = "i am "+ctx;
//        MsgProtocol response = new MsgProtocol(str.getBytes().length,  
//                str.getBytes());  
//        // ����������д�����󣬹ر���ͻ��˵�����  
//        ctx.writeAndFlush(response);  
        // .addListener(ChannelFutureListener.CLOSE);  
  
        // ����д����ʱ������Ҫ�ֶ��ͷ�msg������  
        // ��ֻ�ж�����ʱ������Ҫ�ֶ��ͷ�msg������  
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	Constant.CNL_MAP.remove(ctx);
        System.out.println(new Date()+" - "+Utils.getRemoteAddressPort(ctx) + " is going to disconnect!");  
    	super.channelInactive(ctx);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	cause.printStackTrace();
    	super.exceptionCaught(ctx, cause);
    }
}
