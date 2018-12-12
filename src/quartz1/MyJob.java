package quartz1;

import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {  
    @Override  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
//        System.out.println("��������ִ�У�ִ��ʱ��: " + Calendar.getInstance().getTime());  
        String jobName = context.getJobDetail().getKey().getName();  
        System.out.println("����Key:" + jobName + " ����ִ�У�ִ��ʱ��: " + Calendar.getInstance().getTime());
    }  
} 
