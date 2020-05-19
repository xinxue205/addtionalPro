import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class TestAscii {
	public static void main(String[] args) throws Exception {
		if(args.length<2) {
			System.out.println("usage: interval(ms) maxlen()");
			System.out.println("example: 100 100");
			return;
		}
		String sl = args[0];
		String maxLens = args[1];
		long sleep = Long.parseLong(sl);
		int maxLen = Integer.parseInt(maxLens);
		while (true) {
			String s= "";
			Random r = new Random();
			int a = r.nextInt(1000);
			int length = r.nextInt(maxLen)+1;
			for (int i = 0; i < length; i++) {
				int rs = (r.nextInt(126-32)+32);
//				System.out.println(rs);
				s += (char) rs;
			}
			System.out.println(s);
			
//			String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//			Random random=new Random();
//			StringBuffer sb=new StringBuffer();
//			int len = random.nextInt(111);
//			for(int i=0;i<len;i++){
//				int number=random.nextInt(62);
//				sb.append(str.charAt(number));
//			}
//			System.out.println(sb.toString());

//			System.out.println(RandomStringUtils.randomAlphanumeric(111));
			Thread.sleep(sleep);
		}
		// TODO Auto-generated method stub
//		for(int i=32;i<126;i++){
//			char a = (char) i;
//			System.out.println(a+" "+i);
//		}
//		System.out.println(new Random(126).nextInt());
//		while(true) {
//			System.out.println(new Random().nextInt(55));
//			Thread.sleep(100);
//		}
	}
}
