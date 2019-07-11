package log;

import java.util.UUID;

import org.apache.log4j.Logger;

public class Log4jTest {
	private static Logger logger = Logger.getLogger(Log4jTest.class);

	
	public static void main(String[] args) {	
		int i = 0;
		while(true){
			int t = (int) (1+Math.random()*10);
			try{
				i++;
				String s = UUID.randomUUID().toString();
				logger.info("当前处理第  "+ i +" 文件, id为："+s);
				/*String s = null;
			logger.trace("s的内容是：空");
			logger.debug("s的内容是：空");
			logger.info("s的内容是：空");
			logger.warn("s的内容是：空");
			logger.error("s的内容是：空");*/
				if(i%t==0){
					throw new Exception("系统异常，文件损坏或格式有问题"+t);
				}
				int d = (int) (1+Math.random()*5);
				Thread.sleep(d*1000);
			} catch (Exception e) {
				logger.error("error发生异常：", e);
			}
		}
	}

}
