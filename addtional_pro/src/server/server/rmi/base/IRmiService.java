/**
 * 
 */
package server.server.rmi.base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 上午9:46:30
 * @Description rmi服务接口
 * @version 1.0 Shawn create
 */
public interface IRmiService extends Remote {
	Map execute(Map mparams)throws RemoteException;
}
