import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class TestP{
	int type;
	TestP(int type){
		this.type = type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return this.type ;
	}
}

class Test3 extends TestP{
	String name;
	Test3(String name){
		super(0);
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
public class Test {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	String test = "123";
	
	static boolean int1() {
		System.out.println("int1");
		return true;
	}
	
	static boolean int2() {
		System.out.println("int2");
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		String str1 = "a.b";
		System.out.println(str1.split("\\.")[0]);
		String str = "2020-02-09 23:59:59";
		Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, 0-7);//这里改为1
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.print(d + "    " + cal.getTime());
		
		if(d.before(cal.getTime())) {
			System.out.println(" before");
		} else {
			System.out.println(" after");
		}

//		if(!int1()&&int2()) {
//			System.out.println("true");
//		}
//		
//		System.out.println("false");
//		Timestamp ts= Timestamp.valueOf("2020-11-11"+" 00:00:00");
//		System.out.println(ts);
//		String unformattedString = "Updating database connection '{0}' ";
//		String[] parameters = {"name1"};
//		String string = MessageFormat.format( unformattedString, parameters );
////		String string = MessageFormat.format("oh, {0} is 'a' pig", "ZhangSan"); 
//		  String newTimer=System.nanoTime()+""+((int)(Math.random()*900)+100);
//		System.out.println(newTimer);
//		
//		System.out.println(new Date(1564004400000L));
//		byte a[] = {'a','1'};
//		System.out.println("s我们".getBytes().length);
//		
//		System.out.println("sh123456".substring(2));
//		String e = null;
//		String message = e == null ? "null exception" : e.toString();
//System.out.println("small exception occurs:" + message);
//System.out.println(new Date(1559615078558L));//1557818582171
//System.out.println(new Date(1559615138557L));//1557818582171
//		System.out.println(8255213568L/1024/1024);
//		System.out.println(new Date(1528236600000L));
//		String instanceId = "6";
//		String sql = "SELECT ct.configuration_code,c.config_value "
//				+ "FROM t_config c INNER JOIN t_config_type ct ON "
//				+ "c.config_type_id = ct.config_id WHERE c.group_id IN "
//				+ "(SELECT group_id FROM t_role_entity WHERE role_entity_id = "+instanceId+") "
//				+ "OR c.role_entity_id = "+instanceId;
//		System.out.println(sql);
	}
	
	
	protected static String getSlowAlarm() {
		double percent = 86D*100/90;
		if(percent>85){
			return ", current step is slow , cache used: " + String.format("%.2f", percent)  + "%";
		}
		return "";
	}
	
	public static void main2(String args[]){
		System.out.println(getSlowAlarm());
//		Pattern patternSource = Pattern.compile( ".*\\.zip" );
//		Matcher matcher = patternSource.matcher( "www.zip" );
//        System.out.println(matcher.matches());
//		Date date = new Date(1543213556055L);
//		System.out.println(date);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		System.out.println(sdf.format(new Date(1541528596991L)));
//		Long cutime = System.currentTimeMillis(); // 微秒
//		Long cutime1 = System.currentTimeMillis(); // 微秒
//		Long nanoTime = System.currentTimeMillis(); 
//		System.out.println(cutime + "-" + cutime1 +"-" + nanoTime);
//		cutime = System.nanoTime(); // 微秒
//		cutime1 = System.nanoTime(); // 微秒
//		nanoTime = System.nanoTime(); 
//		System.out.println(cutime + "-" + cutime1 +"-" + nanoTime);
//		System.out.println((int)(Math.random()*90000)+10000);
//		Long d = 154079465975418888L;
//		BigDecimal b = new BigDecimal(d);
//		System.out.println(b);
//		double d1 = d.doubleValue();
//		long d2 = (long) d1;
//		long d3 = b.longValue();
//		System.out.println(d1);
//		System.out.println(d2);
//		System.out.println(d3);
//		System.out.println(new Date(9999999999999L));
//		System.out.println(new Date(9223372036854775807L));
//		System.out.println("priority".indexOf(" or "));
//		String str= "script|mid|master|truncate|insert|select |update|declare|iframe|'|onready|statechange|alert|atestu|xss|;|'|\"|<|>|\\|svg|confirm|prompt|onload|onmouseover|onfocus|onerror|having|..|/|and| or |%";
//	    String[] s = str.split("\\|");
//	    for (int i = 0; i < s.length; i++) {
//			System.out.println(s[i]);
//		}
//		String m1 = str.substring(2,4);
//    	String m2 = str.substring(4,6);
//    	System.out.println(m1+ ":" +m2);
//		System.out.println(String.valueOf((char)58));
//		System.out.println(String.valueOf((char)32));
//		List list = new ArrayList();
//		list.add("");
//		System.out.println(list.get(0));
//		Map map = new HashMap();
//		System.out.println(map.get(null));
//		System.out.println(new Date(1463025945000L));
//		String[] array = "".split("\\|");
//		System.out.println(array[0]+"+"+array[1]+"+"+array[2]);
	}
	
	
	public static String extractSourceIdFormCheckNo(String checkNo){
		String result = null;
	    if(checkNo!=null){
	    	int createTimeStartIndex = checkNo.indexOf("_")+1;
	    	int sourceidStartIndex = checkNo.indexOf("_", createTimeStartIndex)+1;
	    	int sourceidEndIndex = checkNo.indexOf("_", sourceidStartIndex);
	    	
	    	if(sourceidStartIndex>0&&sourceidEndIndex>sourceidStartIndex){
	    	    result = checkNo.substring(sourceidStartIndex, sourceidEndIndex);
	    	}
	    }
	    return result;
	}
	
	public static B getB(C c){
		return (B) c;
	}
}

class C extends B{

	public void implement() {
		System.out.println("imp");
	}
}

class B {
	public String t;

	
}

interface Inter{
	public void implement();
}
