package quartz1;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest{
	
	public static void main1(String[] args) throws Throwable {  
		Scheduler sched = new StdSchedulerFactory().getScheduler();
	    // 计算任务的开始时间，DateBuilder.evenMinuteDate方法是取下一个整数分钟  
	    Date runTime = DateBuilder.evenMinuteDate(new Date());  
	    // 定义一个触发器，startAt方法定义了任务应当开始的时间  
	    Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();  
	    // 真正执行的任务并不是Job接口的实例，而是用反射的方式实例化的一个JobDetail实例  
	    JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();  
	    // 将任务和Trigger放入scheduler  
	    sched.scheduleJob(job, trigger);  
	    sched.start();  
	    try {  
	        // 等待65秒，保证下一个整数分钟出现，这里注意，如果主线程停止，任务是不会执行的  
	        Thread.sleep(66 * 1000L);  
	    } catch (Exception e) {  

	    }  
	    // scheduler结束  
	    sched.shutdown(true);  
	}
	
	public static void main(String[] args) throws Throwable {  
        SchedulerFactory factory = new StdSchedulerFactory();  
        Scheduler scheduler = factory.getScheduler();  
        // @NOTICE 任务的开始时间，nextGivenSecondDate方法表示：当前时间之后，每当秒数是13的倍数都是触发时间，当然只触发一次  
        // 比如：00:00:12秒开始主线程，则13秒就会触发任务，如果00:00:14秒开始主线程，则在26秒触发任务  
        Date runTime = DateBuilder.nextGivenSecondDate(null, 13);  
        JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();  
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();  
        scheduler.scheduleJob(job, trigger);  
          
        // @NOTICE 将同一个Job实现作为另外一个任务注册到scheduler，注意名字要区分  
        job = JobBuilder.newJob(MyJob.class).withIdentity("job2", "group1").build();  
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(runTime).build();  
        scheduler.scheduleJob(job, trigger);  
  
        // @NOTICE 重复执行,job3表示第一次执行完之后，每隔3秒钟执行一次，重复5次，withRepeatCount参数不包括第一次执行那次，即job3总共执行6次  
        job = JobBuilder.newJob(MyJob.class).withIdentity("job3", "group1").build();  
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")  
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(3)).startNow().build();  
        scheduler.scheduleJob(job, trigger);  
  
        job = JobBuilder.newJob(MyJob.class).withIdentity("job4", "group1").build();  
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")  
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(3)).startNow().build();  
        scheduler.scheduleJob(job, trigger);  
        
        scheduler.start();  
        JobDataMap map = trigger.getJobDataMap();
        try {  
            // 等待20秒  
            Thread.sleep(50L * 1000L);  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        scheduler.shutdown(true);  
    } 
}