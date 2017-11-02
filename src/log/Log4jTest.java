package log;

import org.apache.log4j.Logger;

public class Log4jTest {
	private static Logger logger = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {	
		try{
			logger.error("s的内容是：\r 空");
			/*String s = null;
			logger.trace("s的内容是：空");
			logger.debug("s的内容是：空");
			logger.info("s的内容是：空");
			logger.warn("s的内容是：空");
			logger.error("s的内容是：空");*/
		} catch (Exception e) {
			logger.error("error 出现异常",e);
		}
	}

}
