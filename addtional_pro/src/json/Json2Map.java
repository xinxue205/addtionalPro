package json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Json2Map {
	
	public static void main(String[] args) {
		String s = "{ allCount: '3', serviceID: 'abcd-1234', comment: '测试数据', data: [{ id: '01', name: 'aa' }, { id: '02', name: 'bb' }, { id: '03', name: 'cc' } ] }";
		JSONObject jsonObject = JSONObject.fromObject(s);
		
		JSONArray ja = (JSONArray)jsonObject.get("data");
		System.out.println(((JSONObject)ja.get(1)).get("name"));
//		System.out.println(map.get("data"));
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
