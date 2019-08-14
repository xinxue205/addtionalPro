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
    	
        // 为新连接发送欢迎信息
    	String str = "you are welcome, " + Utils.getRemoteAddressPort(ctx);
    	MsgProtocol m = new MsgProtocol(str);  
    	ctx.writeAndFlush(m);  
    }
	
    // 用于获取客户端发送的信息  
    @Override  
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {  
        // 用于获取客户端发来的数据信息  
    	MsgProtocol body = (MsgProtocol) msg;  
        System.out.println(new Date()+" - "+Utils.getRemoteAddressPort(ctx) + " say: " + body.getContentStr());  
        // 会写数据给客户端  
//        String str = "i am "+ctx;
//        MsgProtocol response = new MsgProtocol(str.getBytes().length,  
//                str.getBytes());  
//        // 当服务端完成写操作后，关闭与客户端的连接  
//        ctx.writeAndFlush(response);  
        // .addListener(ChannelFutureListener.CLOSE);  
  
        // 当有写操作时，不需要手动释放msg的引用  
        // 当只有读操作时，才需要手动释放msg的引用  
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
