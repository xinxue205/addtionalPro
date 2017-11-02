package test;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;


public class YesterdayCurrent {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
		System.out.println(sdf.format(d));
		Date yc = new Date(d.getYear(),d.getMonth(),d.getDate()-1,d.getHours(),d.getMinutes(),d.getSeconds());
		System.out.println(sdf.format(yc));
		/*YesterdayCurrent yc = new YesterdayCurrent();
		Calendar c = Calendar.getInstance();
		System.out.println(yc.toString(c));
		c.set(Calendar.YEAR, Calendar.MONTH, (Calendar.DATE-1), Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
		System.out.println(yc.toString(c));
		
	}
	public String toString(Calendar c){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c);*/
	}
}
