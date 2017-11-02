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
	public static void main(String args[]){
		List list = new ArrayList();
		list.add("");
		System.out.println(list.get(1));
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
