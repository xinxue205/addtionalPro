package log;

import java.util.logging.*;

public class LoggingTest {
	private static Logger logger = Logger.getLogger(LoggingTest.class.getName());
	
	public LoggingTest() {
		super();
		logger.setLevel(Level.FINEST);
		logger.severe("severe");
		logger.warning("warning");
		logger.info("INFO");
		logger.config("config");
		logger.fine("fine");
		logger.finer("finer");
		logger.finest("finest");
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		LoggingTest lt = new LoggingTest();
	}

}
