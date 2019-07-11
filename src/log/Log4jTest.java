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
				logger.info("��ǰ�����  "+ i +" �ļ�, idΪ��"+s);
				/*String s = null;
			logger.trace("s�������ǣ���");
			logger.debug("s�������ǣ���");
			logger.info("s�������ǣ���");
			logger.warn("s�������ǣ���");
			logger.error("s�������ǣ���");*/
				if(i%t==0){
					throw new Exception("ϵͳ�쳣���ļ��𻵻��ʽ������"+t);
				}
				int d = (int) (1+Math.random()*5);
				Thread.sleep(d*1000);
			} catch (Exception e) {
				logger.error("error�����쳣��", e);
			}
		}
	}

}
