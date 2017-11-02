/**
 * 
 */
package server.util;

import org.apache.log4j.Logger;

/**
* @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
* @author Shawn.wu
* @date 2014-7-18 ����9:33:15
* @Description ��־������
* @version 1.0 Shawn create
*/
public class LogUtil {
public Logger log  =null ;
	
	public LogUtil(Class<?> clazz){
		log =Logger.getLogger(clazz);
	}
	
	public LogUtil(String loggerName){
		log = Logger.getLogger(loggerName);
	}
	
	public  LogUtil(Class<?> clazz,String sBranchCode){
		System.setProperty("BRANCH_CODE", sBranchCode);
		log = Logger.getLogger(clazz);
	}

	public LogUtil(String loggerName,String sBranchCode) {
		System.setProperty("BRANCH_CODE", sBranchCode);
		log = Logger.getLogger(loggerName);
	}
	
	public void info(String msg) {
		log.info(msg);
	}

	public void info(Exception e) {
		if (e != null) {
			log.info(e.toString());
			log.info(this.getExceptionStackTrace(e));
		} else {
			log.info("�����쳣�����쳣��ϢΪ��");
		}
	}

	public void trace(String msg) {
		log.trace(msg);
	}

	public void trace(Exception e) {
		if (e != null) {
			log.trace(e.toString());
			log.trace(this.getExceptionStackTrace(e));
		} else {
			log.trace("�����쳣�����쳣��ϢΪ��");
		}
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void debug(Exception e) {
		if (e != null) {
			log.debug(e.toString());
		} else {
			log.debug("�����쳣�����쳣��ϢΪ��");
		}
	}

	public void warn(String msg) {
		log.warn(msg);
	}

	public void warn(Exception e) {
		if (e != null) {
			log.warn(e.toString());
			log.warn(this.getExceptionStackTrace(e));
		} else {
			log.warn("�����쳣�����쳣��ϢΪ��");
		}
	}

	public void error(String msg) {
		log.error(msg);
	}

	public void error(Exception e) {
		if (e != null) {
			log.error(e.toString());
			log.error(this.getExceptionStackTrace(e));
		} else {
			log.error("�����쳣�����쳣��ϢΪ��");
		}
	}
	
	public void error(String msg,Exception e) {
		if (e != null) {
			log.error(msg+e.toString());
			log.error(this.getExceptionStackTrace(e));
		} else {
			log.error(msg);
		}
	}

	public void fatal(String msg) {
		log.fatal(msg);
	}

	public void fatal(Exception e) {
		if (e != null) {
			log.fatal(e.toString());
			log.fatal(this.getExceptionStackTrace(e));
		} else {
			log.error("�����쳣�����쳣��ϢΪ��");
		}
	}
	
	public void fatal(Object msg,Exception e) {
		if (e != null) {
			log.fatal(msg + e.toString());
		} else {
			log.fatal(msg);
		}
	}
	
	private String getExceptionStackTrace(Exception e) {
		StringBuffer sbExceptionInfo = new StringBuffer();
		for (int iIndex = 0; iIndex < e.getStackTrace().length; iIndex++) {
			sbExceptionInfo.append(e.getStackTrace()[iIndex] + "\r\n");
		}
		return sbExceptionInfo.toString();
	}
}
