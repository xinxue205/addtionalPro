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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 ����11:03:20
 * @Description ATMV�����ɷ���
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
	 * @param mMsgParams ���������룬�����������Ĳ���
	 * @return HashMap "cwxx"����������Ϣ��get("cwxx")Ϊ��ʱ����������
	 */
	public Map execute(Map mParams) {
		log.info("rmi����AtmvService������");
		try {
			if ( mParams == null ){
				Map mResult = new HashMap() ;
				log.error("����Ĳ��������߳��˳���") ;
				mResult.put("cwxx", "������������!") ;
				return mResult ;
			}
			String sTransCode = mParams.get("jydm") + "";
			if (sTransCode == null || sTransCode.equals("null") || sTransCode.equals("")) {
				log.error("����������Ϊ�գ��߳��˳�!");
				mParams.put("cwxx", "����������Ϊ��!");
				return mParams;
			}
			String sClassName = PropReader.getProperty("/conf/rmi.properties", sTransCode);
			log.info("������:["+sClassName+"] ������:["+sTransCode+"]") ;
			if (sClassName==null||sClassName.equals("")) {
				log.error("�ý����룺["+sTransCode+"]��Ӧ�ĵĴ��������ô���!");
				mParams.put("cwxx", "�ý��׵Ĵ��������ô���");
				return mParams;
			}
			IRmiProcess rmiPorecss = (IRmiProcess) Class.forName(sClassName).newInstance();
			if(rmiPorecss==null){
				log.error("�Ҳ����ý����룺["+sTransCode+"]��Ӧ�ĵĴ����࣡!");
				mParams.put("cwxx", "�Ҳ����ý��׵Ĵ����࣡");
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
