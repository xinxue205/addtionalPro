package json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

public class Json2Map {
	
	public static void main(String[] args) {
		Map map = toHashMap("{'01':'name', '02':'id', 'pwd':'place'}");
		System.out.println(map);
		System.out.println(map.get("01"));
	}
	private static Map toHashMap(Object object) {  
	    Map data = new HashMap();  
	    // 将json字符串转换成jsonObject  
	     JSONObject jsonObject = JSONObject.fromObject(object);
	     Iterator it = jsonObject.keys();  
	     // 遍历jsonObject数据，添加到Map对象  
	     while (it.hasNext())  
	     {  
	         String key = String.valueOf(it.next());  
	         String value = (String) jsonObject.get(key);  
	         data.put(key, value);  
	     }  
	     return data;  
	 }

}
