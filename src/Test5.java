import org.apache.commons.lang.StringUtils;

public class Test5 {
public static void main(String[] args) {
	String str = "123::saf";
	System.out.println(str.substring(0,str.indexOf(":")));
	
	String[] logs = {"123","456","78","9"};
	int maxCount = 2;
	String cLog = null;
	if(logs.length>maxCount){
		String[] logsNew = new String[maxCount+1];
		logsNew[0] = "Log detail is too long, begining lines is skipped. If you want get whole log, please contact system admin.";
		System.arraycopy(logs, logs.length-maxCount, logsNew, 1, maxCount);
		cLog = StringUtils.join(logsNew, "\n");
	}
	System.out.println(cLog);
}
}
