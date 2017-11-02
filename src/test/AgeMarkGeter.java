package test;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgeMarkGeter {
	public String getAgeMark(String PSID, int minAge, int maxAge){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String todayS = sdf.format(new Date()); 
		int today = Integer.parseInt(todayS);
		String refusedMark="NB";
		int minYear=Integer.parseInt(PSID.substring(6, 10))+minAge;
		int maxYear=Integer.parseInt(PSID.substring(6, 10))+maxAge;
		String minBirthdayS = Integer.toString(minYear)+PSID.substring(10, 14);
		String maxBirthdayS = Integer.toString(maxYear)+PSID.substring(10, 14);
		int minBirthday = Integer.parseInt(minBirthdayS);
		int maxBirthday = Integer.parseInt(maxBirthdayS);
		if(today<minBirthday || today>maxBirthday){
			refusedMark ="B";
		}
		return refusedMark;
	}
}
