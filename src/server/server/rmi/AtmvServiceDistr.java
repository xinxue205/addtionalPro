package server.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import server.server.rmi.base.IRmiProcess;
import server.server.rmi.base.IRmiService;
import server.util.PropReader;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 上午11:03:20
 * @Description ATMV服务派发类
 * @version 1.0 Shawn create
 */
public class AtmvServiceDistr extends UnicastRemoteObject implements IRmiService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(AtmvServiceDistr.class);

	public AtmvServiceDistr() throws RemoteException {
		super();
	}  
	/**
	 * @param mMsgParams 包含交易码，和其他传来的参数
	 * @return HashMap "cwxx"包含错误信息，get("cwxx")为空时，处理正常
	 */
	public Map execute(Map mParams) {
		log.info("rmi服务AtmvService被调用");
		try {
			if ( mParams == null ){
				Map mResult = new HashMap() ;
				log.error("传入的参数错误，线程退出！") ;
				mResult.put("cwxx", "传进参数错误!") ;
				return mResult ;
			}
			String sTransCode = mParams.get("jydm") + "";
			if (sTransCode == null || sTransCode.equals("null") || sTransCode.equals("")) {
				log.error("传进交易码为空，线程退出!");
				mParams.put("cwxx", "传进交易码为空!");
				return mParams;
			}
			String sClassName = PropReader.getProperty("/conf/rmi.properties", sTransCode);
			log.info("处理类:["+sClassName+"] 交易码:["+sTransCode+"]") ;
			if (sClassName==null||sClassName.equals("")) {
				log.error("该交易码：["+sTransCode+"]对应的的处理类配置错误！!");
				mParams.put("cwxx", "该交易的处理类配置错误！");
				return mParams;
			}
			IRmiProcess rmiPorecss = (IRmiProcess) Class.forName(sClassName).newInstance();
			if(rmiPorecss==null){
				log.error("找不到该交易码：["+sTransCode+"]对应的的处理类！!");
				mParams.put("cwxx", "找不到该交易的处理类！");
				return mParams;
			}
			mParams = rmiPorecss.process(mParams);
		} catch (InstantiationException e) {
			mParams.put("cwxx", e.toString());
			log.error(e.toString());
		} catch (IllegalAccessException e) {
			mParams.put("cwxx", e.toString());
			log.error(e.toString());
		} catch (ClassNotFoundException e) {
			mParams.put("cwxx", e.toString());
			log.error(e.toString());
		} catch (Exception e) {
			mParams.put("cwxx", e.toString());
			log.error(e.toString());
		}
		return mParams;
	}
}
