package quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * 获取定时器执行时间列表，用于验证
 * @author wuxinxue
 * @copyRight sinobest
 * @time 2015年8月31日 下午2:04:39
 */
public class CronTest {

	/*
   * @param args 
   * @throws ParseException  
    * @throws InterruptedException  
    */  
   public static void main(String[] args) throws Exception {
	   System.out.println(isValid(1, "0 1/5 * * * ?", 100));
   }
   
   /**
    * 验证调度配置是否合法
    * @param type 1-分钟数， 2-表达式
    * @param cronExpr 
    * @param intervalMinites
    * @return 0-合法， -1-表达式或间隔时间有误， -2-间隔时间过短
    */
   static int isValid(int type, String cronExpr, long intervalMinites) {
	   if(type==1){
		   CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
		   try {
				cronTriggerImpl.setCronExpression(cronExpr);
			} catch (ParseException e) {
				e.printStackTrace();
				return -1;
			}
	       List<Date> dates =  TriggerUtils.computeFireTimes(cronTriggerImpl, null, 2);
	       if(dates.size()<2){
	    	   return 0;
	       }

	       long startTime = dates.get(0).getTime();
		   long endTime = dates.get(1).getTime();
		   
		   if (endTime - startTime > 5 * 60 * 1000){
			   return 0; 
		   } else {
			   return -2;
		   }
	   } else {
		   if (intervalMinites > 5){
			   return 0;
		   } else if (intervalMinites < 0){
			   return -1;
		   } else {
			   return -2;
		   }
	   }
   }
}
