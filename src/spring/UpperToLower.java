package spring;


public class UpperToLower implements ChangeLetter {
	private String str;
	
	public String change() {
		return str.toLowerCase();
	}
	

	public void setStr(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

}
