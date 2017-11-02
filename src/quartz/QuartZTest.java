package quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import static org.quartz.JobBuilder.*;

public class QuartZTest {
	
	public static void main1(String[] args) {
		try {  
            CronExpression exp = new CronExpression("0 0 0/1 20/3 * ?");  
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date d = new Date();  
            int i = 0;  
            // 循环得到接下来n此的触发时间点，供验证  
            while (i < 10) {  
                d = exp.getNextValidTimeAfter(d);  
                System.out.println(df.format(d));  
                ++i;  
            }  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
	}
	
	public static void main(String[] args) throws Exception {
		String cronExpr = "cron_0 0 0/1 20/3 * ?";
		long startTime = 1486557782810L;
		System.out.println(new Date(startTime));
		if(cronExpr.startsWith("cron_")){//cron表达式调度时，启动时间则是取启动时间之后的
			cronExpr = cronExpr.substring("cron_".length());
			CronExpression exp = new CronExpression(cronExpr);
			long now = new Date().getTime();
			long beginTime =  startTime > now ? startTime : now;
			System.out.println(exp.getNextValidTimeAfter(new Date(startTime)));  
		}
	}
}

class JobMaker implements org.quartz.Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Trigger trigger = context.getTrigger();
		String jobIdentity = trigger.getKey().getName();
		System.out.println(trigger.getNextFireTime()+"===="+jobIdentity);
	}
	
}

