package netty.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class Constant {
	public static final int MSG_HEAD = -32523523;
	
	public static Map<ChannelHandlerContext, String> CNL_MAP = new ConcurrentHashMap<ChannelHandlerContext, String>();
	public static void main(String[] args) {
		System.out.println(Integer.MIN_VALUE+" "+Integer.MAX_VALUE);
	}
}
