package test;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReverseDateFormat {
	public static void main(String[] args) throws ParseException {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println(sdf.format(date));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date1 = sdf.parse("20130303033003");
		Date date2 = sdf.parse("20130304025003");
		Double pauseHours = ((double) ((date2.getTime()-date1.getTime()))/1000/60/60);
		DecimalFormat df = new DecimalFormat("0.00");
		df.format(pauseHours);
	}
	
/* static String reverseForm(String s){
		String r="";
		int yy = 2000;
		String ww = "Mon";
		String mm = "Jan";
		int dd = 1;
		int hh = 0;
		int mm1 = 0;
		int ss = 0;
		ww=s.substring(0, 3);
		mm=s.substring(4, 7);
		dd=Integer.parseInt(s.substring(8, 10));
		hh=Integer.parseInt(s.substring(11, 13));
		mm1=Integer.parseInt(s.substring(14, 16));
		ss=Integer.parseInt(s.substring(17, 19));
		yy=Integer.parseInt(s.substring(24, 28));
		r = yy+" "+mm+" "+dd+"("+ww+")"+hh+":"+mm1+":"+ss;
		return r;
	}*/
	
}
