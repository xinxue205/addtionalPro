package netty.common;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;

public class Utils {
	
	public static String getRemoteAddressPort(ChannelHandlerContext ctx) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
    	String ip = insocket.getAddress().getHostAddress();
    	int port = insocket.getPort();
    	ChannelAttr nChannel = ctx.channel().attr(ChannelAttr.NETTY_CHANNEL_KEY).get();
    	if (nChannel != null) {
    		String name = nChannel.getName() ;
        	return "["+name+"-"+ip+":"+port+"]";
        } else {
        	return "["+ip+":"+port+"]";
        }
	}
	
}
