/**
 * 
 */
package server.server.rmi.base;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 ����9:46:30
 * @Description rmi����ӿ�
 * @version 1.0 Shawn create
 */
public interface IRmiService extends Remote {
	Map execute(Map mparams)throws RemoteException;
}
