package spring;


public class LowerToUpper implements ChangeLetter {
	private String str;
	public String change() {
		return str.toUpperCase();
		// TODO Auto-generated method stub
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getStr() {
		return str;
	}

}
