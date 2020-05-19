package frequentClass;

public class TestString {
	public static void main(String[] args) {
		String s = new String("sSdfFadsSSdAfasAS23i24ou239842309__)+_)123");
		System.out.println(s.indexOf("s", 4));
		int lCount = 0, uCount = 0, oCount = 0;
		for(int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (Character.isLowerCase(c)){
				lCount ++;
			} else if(Character.isUpperCase(c)){
				uCount ++;
			}
			else oCount++;
		}
		System.out.println(lCount);
		System.out.println(uCount);
		System.out.println(oCount);
	}

}
