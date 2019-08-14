package netty.protocol;

import java.util.Arrays;

import netty.common.Constant;  

/** 
 * <pre> 
 * �Լ������Э�� 
 *  ���ݰ���ʽ 
 * +����----����+����-----����+����----����+ 
 * |Э�鿪ʼ��־|  ����             |   ����       | 
 * +����----����+����-----����+����----����+ 
 * 1.Э�鿪ʼ��־MSG_HEAD��Ϊint���͵����ݣ�16���Ʊ�ʾΪ0X76 
 * 2.�������ݵĳ���contentLength��int���� 
 * 3.Ҫ��������� 
 * </pre> 
 */  
public class MsgProtocol {  
    /** 
     * ��Ϣ�Ŀ�ͷ����Ϣ��־ 
     */  
    private int head_data = Constant.MSG_HEAD;  
    /** 
     * ��Ϣ�ĳ��� 
     */  
    private int contentLength;  
    /** 
     * ��Ϣ������ 
     */  
    private byte[] content;  
  
    /** 
     * ���ڳ�ʼ����MsgProtocol 
     *  
     * @param contentLength 
     *            Э�����棬��Ϣ���ݵĳ��� 
     * @param content 
     *            Э�����棬��Ϣ������ 
     */  
    public MsgProtocol(int contentLength, byte[] content) {  
        this.contentLength = contentLength;  
        this.content = content;  
    }  
  
    public int getHead_data() {  
        return head_data;  
    }  
  
    public int getContentLength() {  
        return contentLength;  
    }  
  
    public void setContentLength(int contentLength) {  
        this.contentLength = contentLength;  
    }  
  
    public byte[] getContent() {  
        return content;  
    }  
  
    public void setContent(byte[] content) {  
        this.content = content;  
    }  
  
    @Override  
    public String toString() {  
        return "MsgProtocol [head_data=" + head_data + ", contentLength="  
                + contentLength + ", content=" + Arrays.toString(content) + "]";  
    }  
  
    public MsgProtocol(String contentStr) {
    	this.content = contentStr.getBytes();
    	this.contentLength = this.content.length;  
    }
    
    public String getContentStr() {
    	return new String(content);  
    }
}