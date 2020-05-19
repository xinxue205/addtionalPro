/**
 * 
 */
package server.server.rmi.base;

import java.util.Map;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 下午4:02:23
 * @Description IRM调用器
 * @version 1.0 Shawn create
 */
public interface IRmiProcess {
	/**
	 * @param mMsgParams 包含交易码，和其他传来的参数
	 * @return HashMap "cwxx"包含错误信息，get("cwxx")为空时，处理正常
	 */
	Map<String,Object> process(Map<String,Object> s);
}
