package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/** 
 * <pre> 
 * �Լ������Э�� 
 *  ���ݰ���ʽ 
 * +����----����+����-----����+����----����+ 
 * |Э�鿪ʼ��־|  ����             |   ����       | 
 * +����----����+����-----����+����----����+ 
 * 1.Э�鿪ʼ��־head_data��Ϊint���͵����ݣ�16���Ʊ�ʾΪ0X76 
 * 2.�������ݵĳ���contentLength��int���� 
 * 3.Ҫ��������� 
 * </pre> 
 */  
public class ProtocolEncoder extends MessageToByteEncoder<MsgProtocol> {  
  
    @Override  
    protected void encode(ChannelHandlerContext tcx, MsgProtocol msg,  
            ByteBuf out) throws Exception {  
        // д����ϢSmartCar�ľ�������  
        // 1.д����Ϣ�Ŀ�ͷ����Ϣ��־(int����)  
        out.writeInt(msg.getHead_data());  
        // 2.д����Ϣ�ĳ���(int ����)  
        out.writeInt(msg.getContentLength());  
        // 3.д����Ϣ������(byte[]����)  
        out.writeBytes(msg.getContent());  
    }

}  
