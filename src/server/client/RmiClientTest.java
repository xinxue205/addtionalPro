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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-22 ����4:22:38
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
	 * @title ���ݵ���
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
	 * @title ���������ز���
	 * @Description �����ڷ���ϵͳִ�нű��ĺ�̨����������з�shell����÷���ִ�У�
	 */
	public void testMainServerOperate() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://127.0.0.1:29977/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_fwqcz");
			m.put("dz","g");//ֹͣ��t,������q,������c
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
	 * @title ִ�нű�
	 * @Description �����ڷ���ϵͳִ�нű��ĺ�̨����������з�shell����÷���ִ�У�
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
	 * @title ��ȡ���������ļ�
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
	 * @title ���ݸ���
	 */
	public void testUpdateAtmvDBImpl() {
		try {
			IRmiService c = (IRmiService) Naming.lookup("rmi://localhost/AtmvService");
			Map m = new HashMap();
			m.put("jydm","rmi_sjgx");
			m.put("sql","update m30_ap_info set spare_int1='aa'");
			m = c.execute(m);
			if(m.get("cwxx")==null||m.get("cwxx").equals(""))
				System.out.println("������Ϣ###"+m.get("fhxx"));
			else System.out.println("������Ϣ###"+m.get("cwxx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 
	 * @title ��ȡ���ļ�
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
	 * @title �´��ļ�
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
