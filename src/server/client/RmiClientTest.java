/**
 * 
 */
package server.client;

import java.rmi.Naming;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;

import server.server.rmi.base.IRmiService;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 下午4:22:38
 * @Description
 * @version 1.0 Shawn create
 */
public class RmiClientTest {
	private static String rmiAddress = "rmi://128.128.200.14:29977/AtmvService";
	public RmiClientTest(){
	}
	
	public static void main(String[] args) {
		/*String address = "rmi://128.128.201.36:29977/AtmvService";
		Remote rmt = Naming.lookup(address);*/
		RmiClientTest rct = new RmiClientTest();
		rct.testExportAtmvDataImpl(rmiAddress);
	}
	
	/** 
	 * @title 数据导出
	 */
	public void testExportAtmvDataImpl(String rmiAddress) {
		try {
			IRmiService c = (IRmiService) Naming.lookup(rmiAddress);
			Map m = new HashMap();
			m.put("jydm","rmi_sjdc");
			m.put("sql","select * from m30_approve_info");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println(m.get("fhxx"));
			else System.out.println(m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * @title 服务器开关操作
	 * @Description 用于在分行系统执行脚本的后台管理程序（总行发shell命令，让分行执行）
	 */
	public void testMainServerOperate() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://127.0.0.1:29977/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_fwqcz");
			m.put("dz","g");//停止：t,启动：q,重启：c
			m.put("fwm","RmiJobServer");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println("fhxx:"+m.get("fhxx"));
			else System.out.println("cwxx:"+m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title 执行脚本
	 * @Description 用于在分行系统执行脚本的后台管理程序（总行发shell命令，让分行执行）
	 */
	public void testExecuteScriptImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://localhost/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_zxjb");
			String sBat = "mkdir c:\\log\\w dir";
			m.put("nr",sBat);
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println(m.get("fhxx"));
			else System.out.println(m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title 提取程序生成文件
	 */
	public void testGetAtmvBatchFileImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://128.128.81.161:29977/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_tqplwj");
			m.put("wjm","a.dat");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println(m.get("fhxx"));
			else System.out.println(m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title 数据更新
	 */
	public void testUpdateAtmvDBImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://localhost/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_sjgx");
			m.put("sql","update m30_ap_info set spare_int1='aa'");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println("返回信息###"+m.get("fhxx"));
			else System.out.println("错误信息###"+m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title 提取成文件
	 */
	public void testGetAtmvFileImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://128.128.96.161:29977/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_tqwy");
			m.put("wjm","/home/ap/atmvh/app/ATMVH_APP/tmp/batch/a.dat");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println(m.get("fhxx"));
			else System.out.println(m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title 下传文件
	 */
	public void testSendFileToAtmvImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://localhost/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_xcwj");
			m.put("wjm","c:\\log\\vh\\vh.txt");
			m.put("lj","c:\\log\\v\\vh.txt");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println(m.get("fhxx"));
			else System.out.println(m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
