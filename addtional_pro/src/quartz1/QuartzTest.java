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
	    // ��������Ŀ�ʼʱ�䣬DateBuilder.evenMinuteDate������ȡ��һ����������  
	    Date runTime = DateBuilder.evenMinuteDate(new Date());  
	    // ����һ����������startAt��������������Ӧ����ʼ��ʱ��  
	    Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();  
	    // ����ִ�е����񲢲���Job�ӿڵ�ʵ���������÷���ķ�ʽʵ������һ��JobDetailʵ��  
	    JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();  
	    // �������Trigger����scheduler  
	    sched.scheduleJob(job, trigger);  
	    sched.start();  
	    try {  
	        // �ȴ�65�룬��֤��һ���������ӳ��֣�����ע�⣬������߳�ֹͣ�������ǲ���ִ�е�  
	        Thread.sleep(66 * 1000L);  
	    } catch (Exception e) {  

	    }  
	    // scheduler����  
	    sched.shutdown(true);  
	}
	
	public static void main(String[] args) throws Throwable {  
        SchedulerFactory factory = new StdSchedulerFactory();  
        Scheduler scheduler = factory.getScheduler();  
        // @NOTICE ����Ŀ�ʼʱ�䣬nextGivenSecondDate������ʾ����ǰʱ��֮��ÿ��������13�ı������Ǵ���ʱ�䣬��Ȼֻ����һ��  
        // ���磺00:00:12�뿪ʼ���̣߳���13��ͻᴥ���������00:00:14�뿪ʼ���̣߳�����26�봥������  
        Date runTime = DateBuilder.nextGivenSecondDate(null, 13);  
        JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();  
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();  
        scheduler.scheduleJob(job, trigger);  
          
        // @NOTICE ��ͬһ��Jobʵ����Ϊ����һ������ע�ᵽscheduler��ע������Ҫ����  
        job = JobBuilder.newJob(MyJob.class).withIdentity("job2", "group1").build();  
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(runTime).build();  
        scheduler.scheduleJob(job, trigger);  
  
        // @NOTICE �ظ�ִ��,job3��ʾ��һ��ִ����֮��ÿ��3����ִ��һ�Σ��ظ�5�Σ�withRepeatCount������������һ��ִ���ǴΣ���job3�ܹ�ִ��6��  
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
            // �ȴ�20��  
            Thread.sleep(50L * 1000L);  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        scheduler.shutdown(true);  
    } 
}