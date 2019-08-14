package netty.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MsgChannelOutboundHandler extends ChannelOutboundHandlerAdapter {
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//		byte[] bytes = null;      
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
//            ObjectOutputStream oos = new ObjectOutputStream(bos);         
//            oos.writeObject(msg);        
//            oos.flush();         
//            bytes = bos.toByteArray ();      
//            oos.close();         
//            bos.close();        
//		MsgProtocol response = new MsgProtocol(bytes.length,  
//				bytes);  
		System.out.println("send to" + ctx +":"+msg);
		super.write(ctx, msg, promise);
	}

}
