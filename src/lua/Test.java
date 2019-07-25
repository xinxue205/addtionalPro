package lua;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Test {
	
	//java调用lua函数（无返回对象）
	public static void main1(String[] args) throws Exception {
		String luaFileName = Test.class.getClassLoader().getResource("lua/hello.lua").toURI().getPath();
		Globals globals = JsePlatform.standardGlobals();
		globals.loadfile(luaFileName).call();
		LuaValue func = globals.get(LuaValue.valueOf("helloWithoutTranscoder"));
		String result = func.call().toString();
		System.out.println("result---"+result);
	}
	
	//java调用lua函数（无参，有返回值）
	public static void main2(String[] args) throws Exception {
		String luaFileName = Test.class.getClassLoader().getResource("lua/hello.lua").toURI().getPath();
		Globals globals = JsePlatform.standardGlobals();
		LuaValue transcoderObj = globals.loadfile(luaFileName).call();
		LuaValue func = transcoderObj.get(LuaValue.valueOf("hello"));
		String result = func.call().toString();
		System.out.println("result---"+result);
	}
	
	//java调用lua函数（有参，有返回值）
	public static void main3(String[] args) throws Exception {
		String luaFileName = Test.class.getClassLoader().getResource("lua/hello.lua").toURI().getPath();
		Globals globals = JsePlatform.standardGlobals();
		LuaValue transcoderObj = globals.loadfile(luaFileName).call();
		LuaValue func = transcoderObj.get(LuaValue.valueOf("test"));
		String result = func.call(LuaValue.valueOf("sky")).toString();
		System.out.println("result---"+result);
	}
	
	//java调用lua函数（无参，返回一个lua对象）
	public static void main(String[] args) throws URISyntaxException {
		String luaFileName = Test.class.getClassLoader().getResource("lua/hello.lua").toURI().getPath();
		Globals globals = JsePlatform.standardGlobals();
		LuaValue transcoderObj = globals.loadfile(luaFileName).call();
		LuaValue func = transcoderObj.get(LuaValue.valueOf("getInfo"));
		LuaValue hTable  = func.call();
		//解析返回来的table，这里按照格式，一个个参数去取
		String userId = hTable.get("userId").toString();
		LuaTable servicesTable = (LuaTable)CoerceLuaToJava.coerce(hTable.get("services"), LuaTable.class);
		List<String> servciesList = new ArrayList<>();
		for (int i = 1; i <= servicesTable.length(); i++) {
		      int length = servicesTable.get(i).length();
		      StringBuilder service = new StringBuilder();
		      for (int j = 1; j <= length; j++) {
		            service.append(servicesTable.get(i).get(j).toString());
		      }
		      servciesList.add(service.toString());
		 }
		System.out.println("userId:"+userId);
		System.out.println("servcies:"+servciesList);
	}
}
