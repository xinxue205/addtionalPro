package log;

import org.apache.log4j.Logger;

public class Log4jTest {
	private static Logger logger = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {	
		try{
			logger.error("s�������ǣ�\r ��");
			/*String s = null;
			logger.trace("s�������ǣ���");
			logger.debug("s�������ǣ���");
			logger.info("s�������ǣ���");
			logger.warn("s�������ǣ���");
			logger.error("s�������ǣ���");*/
		} catch (Exception e) {
			logger.error("error �����쳣",e);
		}
	}

}
