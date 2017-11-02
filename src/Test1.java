import java.util.HashMap;
import java.util.Map;


public class Test1 {
	public static void main(String[] args) {
		String jobInfo = "1-20170101020202-name";
		String jobId = jobInfo.substring(0, jobInfo.indexOf("-"));
		jobInfo = jobInfo.substring(jobInfo.indexOf("-") +1 );
		String startTime = jobInfo.substring(0, jobInfo.indexOf("-"));
		String jobName = jobInfo.substring(jobInfo.indexOf("-")+1);
		System.out.println(jobId +" "+ startTime + " " + jobName);
	}
}