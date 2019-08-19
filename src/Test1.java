import java.io.UnsupportedEncodingException;


public class Test1 {
	public static void main(String[] args) {
		String newTimer=System.nanoTime()+""+((int)(Math.random()*900)+100);
		System.out.println(newTimer);
		String url = "jdbc:mysql:thin:@192.168.1.1:1521/asfd";
		int index = url.indexOf("/");
		if(index > 0 ){
			char last = url.charAt(index-1);
			if( last != ':' )	url = url.replaceFirst("/", ":/");
        }
		String[] urlInfos = url.split(":");
		if(urlInfos==null||urlInfos.length<6){
			System.out.println("repository url is wrong!");
			return;
		}
		String dbhost = urlInfos[3].substring(1);
		String port = urlInfos[4];
		String db = urlInfos[5];
		
		System.out.println(dbhost + " " + port + " " +db);
}
	
	public static void main1(String[] args) throws Exception {
		byte[] t = {-73, -21, -48, -37, 40, -46, -4, -59, -32, -42};
		String o =  new String(t, "GBK");
		System.out.println(o);
//		byte[] t = "a".getBytes();
//		String s = "冯雄(尹培中国人";
//		byte[] a = s.getBytes("GBK");
//		System.out.println(subStr(s, 10));
//		String s = "）";
//		byte[] a = s.getBytes("GBK");
//		byte a1 = a[0];
//		byte[] a2 = "冯雄(尹培".getBytes("GBK");
//		System.out.println(a2.length);
//		byte[] b = new byte[a2.length+1];
//		System.arraycopy(a2, 0,b,0,a2.length);
//		b[9] = a1;
//		
//		System.out.println(new String(b, "GBK"));
//		String data = "";
//		if(b.length%2 != 0){
//    		byte[] c = new byte[b.length-1];
//    		System.arraycopy(b, 0,c,0,b.length-1);
//    		data = new String(c, "GBK");
//		} else {
//			data = new String(b, "GBK");
//		}
//		System.out.println(data);
//		System.out.println(data.getBytes("GBK").length);
//		System.out.println(s.replaceAll("\\s*", ""));
//		for (int i = 0; i < s.length(); i++) {
//			System.out.println(i+":"+s.charAt(i));
//		}
//		String jobInfo = "1-20170101020202-name";
//		String jobId = jobInfo.substring(0, jobInfo.indexOf("-"));
//		jobInfo = jobInfo.substring(jobInfo.indexOf("-") +1 );
//		String startTime = jobInfo.substring(0, jobInfo.indexOf("-"));
//		String jobName = jobInfo.substring(jobInfo.indexOf("-")+1);
//		System.out.println(jobId +" "+ startTime + " " + jobName);
	}
	
	public static String subStr(String str, int subSLength)    
            throws UnsupportedEncodingException{   
        if (str == null)    
            return "";    
        else{   
            int tempSubLength = subSLength;//截取字节数  
            String subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串    
            int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度   
            //int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度   
            // 说明截取的字符串中包含有汉字    
            while (subStrByetsL > tempSubLength){    
                int subSLengthTemp = --subSLength;  
                subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);    
                subStrByetsL = subStr.getBytes("GBK").length;  
                //subStrByetsL = subStr.getBytes().length;  
            }    
            return subStr;   
        }  
    } 
}