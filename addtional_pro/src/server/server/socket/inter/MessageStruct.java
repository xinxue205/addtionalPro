/**
 * 
 */
package server.server.socket.inter;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:46:29
 * @Description
 * @version 1.0 Shawn create
 */
public interface MessageStruct {
	public String packMsg();

	public int unpackMsg(String XMLMsgString);
}