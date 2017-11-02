package array;

public class TestDateSort {
	public static void main(String args[]) {
		Date[] days = new Date[5];
		days[0] = new Date(2006, 5, 4);
		days[1] = new Date(2006, 7, 4);
		days[2] = new Date(2008, 5, 4);	
		days[3] = new Date(2004, 5, 9);
		days[4] = new Date(2004, 5, 4);
		
		bubbleSort(days);
		
		for(int i=0; i<days.length; i++) {
			System.out.println(days[i]);
		}
	}
	
	public static Date[] bubbleSort(Date[] a) {
		int len=a.length;
		for(int i=len-1;i>=1;i--) {
			Date temp;
			for(int j=0;j<i;j++) {
				if (a[j].compare(a[j+1])>0) {
					temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;					
				}
			}
		}
		return a;
	}
}

class Date {
	int year, month, day;
	Date(int Y, int M, int D) {
		year = Y; month = M;	day = D;
	}
	
	public int compare(Date date) {
		return year > date.year ? 1
					: year < date.year ? -1
					: month > date.month ? 1
					: month < date.month ? -1
					: day > date.day ? 1
					: day < date.day ? -1 : 0;
	}
	
	public String toString(){
		return "Year Month Day = "+ year+" "+month+" "+day;
	}
}