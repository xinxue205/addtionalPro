package quartz1;

import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {  
    @Override  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
//        System.out.println("任务正在执行，执行时间: " + Calendar.getInstance().getTime());  
        String jobName = context.getJobDetail().getKey().getName();  
        System.out.println("任务Key:" + jobName + " 正在执行，执行时间: " + Calendar.getInstance().getTime());
    }  
} 
