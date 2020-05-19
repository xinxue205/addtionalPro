package json;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import org.json.JSONArray;  
import org.json.JSONObject;  
import org.json.JSONStringer;  
import org.json.JSONTokener;  
    
/** 
 * 解析多层次Json字符串,封装多层Json，避免字符串中有特殊字符而出现的错误 
 */  
public class JsonUtil {  
    
    private final static String regex = "\"([^\\\" ]+?)\":";  
    
    /** 
     * 一个方法解析多层json数据  json + 正则 + 递归 
     * @see {@link java.util.regex.Matcher}, {@link java.util.regex.Pattern} 
     * @param jsonStr 
     * @return {@link java.util.Map} or {@link java.util.List} or {@link java.lang.String} 
     */  
    public static Object jsonParse(final String jsonStr) {  
        if (jsonStr == null) throw new NullPointerException("JsonString shouldn't be null");  
        try {  
            if (isJsonObject(jsonStr)) {  
                final Pattern pattern = Pattern.compile(regex);  
                final Matcher matcher = pattern.matcher(jsonStr);  
                final Map<String, Object> map = new HashMap<String, Object>();  
                final JSONObject jsonObject = new JSONObject(jsonStr);  
                try {  
                    for (; matcher.find(); ) {  
                        String groupName = matcher.group(1);  
                        Object obj = jsonObject.opt(groupName);  
                        //Log.e(groupName, obj+"");  
                        if (isJsonObject(obj+"") || isJsonArray(obj+"")) {  
                            matcher.region(matcher.end() + (obj+"").replace("\\", "").length(), matcher.regionEnd());  
                            map.put(groupName, jsonParse(obj+""));  
                        } else {  
                            map.put(groupName, obj+"");  
                        }  
                    }  
                } catch (Exception e) {  
                    // TODO: handle exception  
//                    Log.e("object---error", e.getMessage()+"--"+e.getLocalizedMessage());  
                	e.printStackTrace();
                }  
                    
                return map;  
            } else if (isJsonArray(jsonStr)) {  
                List<Object> list = new ArrayList<Object>();  
                try {  
                    JSONArray jsonArray = new JSONArray(jsonStr);  
                    for (int i = 0; i < jsonArray.length(); i++) {  
                        Object object = jsonArray.opt(i);  
                        list.add(jsonParse(object+""));  
                    }  
                } catch (Exception e) {  
                    // TODO: handle exception  
                	e.printStackTrace();
//                    Log.e("array---error", e.getMessage()+"--"+e.getLocalizedMessage());  
                }  
                return list;  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
//            Log.e("RegexUtil--regexJson", e.getMessage()+"");  
        	e.printStackTrace();
        }  
        return jsonStr;  
    }  
        
    /** 
     * To determine whether a string is JsonObject {@link org.json.JSONObject} 
     * @param jsonStr {@link java.lang.String} 
     * @return boolean 
     */  
    private static boolean isJsonObject(final String jsonStr) {  
        if (jsonStr == null) return false;  
        return Pattern.matches("^\\{.*\\}$", jsonStr.trim());  
    }  
        
    /** 
     * To determine whether a string is JsonArray {@link org.json.JSONArray}; 
     * @param jsonStr {@link java.lang.String} 
     * @return boolean 
     */  
    private static boolean isJsonArray(final String jsonStr) {  
        if (jsonStr == null) return false;  
        return Pattern.matches("^\\[.*\\]$", jsonStr.trim());  
    }  
        
    /** 
     * 将对象分装为json字符串 (json + 递归) 
     * @param obj 参数应为{@link java.util.Map} 或者 {@link java.util.List} 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Object jsonEnclose(Object obj) {  
        try {  
            if (obj instanceof Map) {   //如果是Map则转换为JsonObject  
                Map<String, Object> map = (Map<String, Object>)obj;  
                Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();  
                JSONStringer jsonStringer = new JSONStringer();  
                while (iterator.hasNext()) {  
                    Entry<String, Object> entry = iterator.next();  
                    jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));  
                }  
                JSONObject jsonObject = new JSONObject(new JSONTokener(jsonStringer.endObject().toString()));  
                return jsonObject;  
            } else if (obj instanceof List) {  //如果是List则转换为JsonArray  
                List<Object> list = (List<Object>)obj;  
                JSONStringer jsonStringer = new JSONStringer();  
                for (int i = 0; i < list.size(); i++) {  
                    jsonStringer.value(jsonEnclose(list.get(i)));  
                }  
                JSONArray jsonArray = new JSONArray(new JSONTokener(jsonStringer.endArray().toString()));  
                return jsonArray;  
            } else {  
                return obj;  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
            return e.getMessage();  
        }  
    }  
    
    public static void main(String[] args) {
    	String jsonStr = "{\"returnCode\":\"0\",\"returnResult\":[{\"class\":\"1\",\"classname\":\"1ban\",\"classmate\":[{\"mate\":\"11\",\"matename\":\"1-1meta\"}]}" +
    			",{\"class\":\"2\",\"classname\":\"2ban\",\"classmate\":[{\"mate\":\"21\",\"matename\":\"2-1meta\"}," +
    			"{\"mate\":\"23\",\"matename\":\"2-3meta\"}]}],\"returnMsg\":\"成功\"}";
    	//解析时使用示例  
		Object object = JsonUtil.jsonParse(jsonStr.substring(jsonStr.indexOf("{"),  
		                                    jsonStr.lastIndexOf("}")+1));  
		System.out.println(object);
		Object str = JsonUtil.jsonEnclose(object);
		System.out.println(str);
        if (object instanceof String) {  
        } else if (object instanceof Map) {  
            @SuppressWarnings("unchecked")  
            HashMap<String, Object> map = (HashMap<String, Object>)object;  
            Iterator<Entry<String, Object>>  iterator = map.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Entry<String, Object> entry = iterator.next();  
                if (entry.getValue() instanceof List) {  
                } else {  
                }  
            }  
        } else if (object instanceof List) {  
        }  
	}
}  
    
    
