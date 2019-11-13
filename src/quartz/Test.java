package quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    public static void main(String[] args) {
        try {
            //����scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.getListenerManager().addSchedulerListener(new SimpleSchedulerListener());

//            scheduler.getListenerManager().addJobListener(new SimpleJobListener(), KeyMatcher.keyEquals(JobKey.jobKey("job1", "group")));
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener("SimpleTrigger"), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "group")));
            Date date = new Date();
            System.out.println("now :"+date);
            long start = date.getTime() - 3000;
            long end = start + 22*1000L;

            //����һ��Trigger
            Trigger trigger = newTrigger().withIdentity("trigger1", "group") //����trigger��name/group
//                .startNow()//һ������scheduler��������Ч
            		.startAt(new Date(start)).endAt(new Date(end))
                .withSchedule(simpleSchedule() //ʹ��SimpleTrigger
                    .withIntervalInSeconds(5) //ÿ��һ��ִ��һ��
                    .repeatForever()) //һֱִ�У����ڵ��ϲ�ͣЪ
                .build();

            //����һ��JobDetail
            JobDetail job = newJob(HelloQuartz.class) //����Job��ΪHelloQuartz�࣬����������ִ���߼�����
                .withIdentity("job1", "group") //����job��name/group
                .usingJobData("name", "quartz") //��������
                .build();

            //�����������
            scheduler.scheduleJob(job, trigger);

            //����֮
            scheduler.start();

            //����һ��ʱ���ر�
//            Thread.sleep(50000);
//            scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SimpleJobListener implements JobListener{

    private static Logger logger = LoggerFactory.getLogger(SimpleJobListener.class);

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        logger.info(" listener name is:"+name);
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " is going to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " was vetoed and not executed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " was executed");

    }
    
}

class SimpleTriggerListener implements TriggerListener{

    private static Logger logger = LoggerFactory.getLogger(SimpleTriggerListener.class);

    private String name;

    public SimpleTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().getName();
        logger.info(triggerName + " was fired");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().getName();
        logger.info(triggerName + " was not vetoed");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String triggerName = trigger.getKey().getName();
        logger.info(triggerName + " misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
            CompletedExecutionInstruction triggerInstructionCode) {
        String triggerName = trigger.getKey().getName();
        logger.info(triggerName + " is complete");
    }
}

class SimpleSchedulerListener implements SchedulerListener{
    private static Logger logger = LoggerFactory.getLogger(SimpleSchedulerListener.class);

	@Override
	public void jobScheduled(Trigger trigger) {
		 logger.info(trigger.getKey().getName() + " jobScheduled");
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		 logger.info(triggerKey.getName() + " jobUnscheduled");

	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		 logger.info(trigger.getKey().getName() + " triggerFinalized");
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		 logger.info(triggerKey.getName() + " triggerKey triggersPaused");

	}

	@Override
	public void triggersPaused(String triggerGroup) {
		 logger.info(triggerGroup + " triggerGroup triggersPaused");

	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		 logger.info(triggerKey.getName() + " triggerKey triggersResumed");

	}

	@Override
	public void triggersResumed(String triggerGroup) {
		 logger.info(triggerGroup + " triggerGroup triggersResumed");

	}

	@Override
	public void jobAdded(JobDetail jobDetail) {
		 logger.info(jobDetail.getKey().getName() + " jobAdded");

	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		 logger.info(jobKey.getName() + " jobDeleted");

	}

	@Override
	public void jobPaused(JobKey jobKey) {
		 logger.info(jobKey.getName() + " jobKey jobsPaused");

	}

	@Override
	public void jobsPaused(String jobGroup) {
		 logger.info(jobGroup + " jobGroup jobsPaused");

	}

	@Override
	public void jobResumed(JobKey jobKey) {
		 logger.info(jobKey+ " jobKey jobResumed");

	}

	@Override
	public void jobsResumed(String jobGroup) {
		 logger.info(jobGroup + " jobGroup jobsResumed");

	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		 logger.info(msg + " schedulerError");

	}

	@Override
	public void schedulerInStandbyMode() {
		 logger.info(" schedulerInStandbyMode");

	}

	@Override
	public void schedulerStarted() {
		 logger.info(" schedulerStarted");

	}

	@Override
	public void schedulerStarting() {
		 logger.info(" schedulerStarting");

	}

	@Override
	public void schedulerShutdown() {
		 logger.info(" schedulerShutdown");

	}

	@Override
	public void schedulerShuttingdown() {
		 logger.info(" schedulerShuttingdown");

	}

	@Override
	public void schedulingDataCleared() {
		 logger.info(" schedulingDataCleared");
	}
	
}