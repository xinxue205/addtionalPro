import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleException;

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
	
	
	String test = "123";
	public static void main2(String[] args) {
		System.out.println(8255213568L/1024/1024);
		System.out.println(new Date(1528236600000L));
		String instanceId = "6";
		String sql = "SELECT ct.configuration_code,c.config_value "
				+ "FROM t_config c INNER JOIN t_config_type ct ON "
				+ "c.config_type_id = ct.config_id WHERE c.group_id IN "
				+ "(SELECT group_id FROM t_role_entity WHERE role_entity_id = "+instanceId+") "
				+ "OR c.role_entity_id = "+instanceId;
		System.out.println(sql);
	}
	
	public static void main(String args[]){
		Date date = new Date(1543213556055L);
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out.println(sdf.format(new Date(1541528596991L)));
		Long cutime = System.currentTimeMillis(); // Œ¢√Î
		Long cutime1 = System.currentTimeMillis(); // Œ¢√Î
		Long nanoTime = System.currentTimeMillis(); 
		System.out.println(cutime + "-" + cutime1 +"-" + nanoTime);
		cutime = System.nanoTime(); // Œ¢√Î
		cutime1 = System.nanoTime(); // Œ¢√Î
		nanoTime = System.nanoTime(); 
		System.out.println(cutime + "-" + cutime1 +"-" + nanoTime);
		System.out.println((int)(Math.random()*90000)+10000);
		Long d = 154079465975418888L;
		BigDecimal b = new BigDecimal(d);
		System.out.println(b);
		double d1 = d.doubleValue();
		long d2 = (long) d1;
		long d3 = b.longValue();
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
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
	
	public static void main1(String[] args) throws KettleException {
		KettleEnvironment.init();
		String str = Encr.encryptPassword("123456");
		System.out.println(str);
		String str1 = Encr.decryptPassword(str);
		System.out.println(str1);
		
		Map map = new HashMap();
		map.put("1", new Test3("aaa"));
		map.put("2", new Test3("bbb"));
		Test3 val1 = (Test3) map.get("1");
		val1.setName("ccc");;
		Test3 val2 = (Test3) map.get("1");
		System.out.println(val2.name);
		Date d = new Date(1439299807554L);
		if (d !=null)
		System.out.println(d);
		String b = "sdf";
		String c = "sadf";
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
