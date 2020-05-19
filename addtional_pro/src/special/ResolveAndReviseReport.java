package special;


public class ResolveAndReviseReport {
	public static void main(String[] args) {
		String s= "aa||123||44||5555||123||333||22||11";
		System.out.println(revise(s, 2, "aaa"));		
	}
	static String revise(String orginalString, int sectNo, String replaceContent){
		String nstr="";
		String ps[]=orginalString.split("\\|\\|");
		ps[sectNo-1]=replaceContent;
		for (int i = 0; i < ps.length; i++) {
			System.out.println(ps[i]);
			nstr=nstr+"||"+ps[i];
		}
		nstr=nstr.substring(2);
		return nstr;
	}
}
