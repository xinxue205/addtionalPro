/**
 * 
 */
package server.server.rmi.base;

import java.util.Map;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 ����4:02:23
 * @Description IRM������
 * @version 1.0 Shawn create
 */
public interface IRmiProcess {
	/**
	 * @param mMsgParams ���������룬�����������Ĳ���
	 * @return HashMap "cwxx"����������Ϣ��get("cwxx")Ϊ��ʱ����������
	 */
	Map<String,Object> process(Map<String,Object> s);
}
