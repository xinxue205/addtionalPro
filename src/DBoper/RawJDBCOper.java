package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLRecoverableException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executor;

public class RawJDBCOper {
	public static void main4(String[] args) {
		String db = "sdi_kettle?useUnicode=true&characterEncoding=utf8&useFastDateParsing=false&zeroDateTimeBehavior=convertToNull&autoReconnect=true";
	}
	/** 
	 * 20170227 wxx mysql��close���������ӶϿ�����»��������쳣����
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");  
		String url = "jdbc:mysql://192.168.14.43:3306/sdi_kettle?user=sdi&password=sdi123&connectTimeout=10000";
//		DriverManager.setLoginTimeout(0);
//		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.159.199:1521:orcl", "pcs_kettle_yfzx", "pcs_kettle_yfzx");
		Connection conn = DriverManager.getConnection(url);
//		conn.setNetworkTimeout(new Executor(){
//  		  public void execute(Runnable command) {
//  		  }
//  	    }, 10000);
		String sql="select * from r_cluster";
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rst=ps.executeQuery();
		System.out.println(new Date()+": query finished!");
		Thread.sleep(22*1000);
		System.out.println(new Date()+": begin close...");
		try {
			ps.close();
			conn.close();
			System.out.println(new Date()+": close succ...");
		} catch (Exception e) {
			System.out.println(new Date()+": throw excepion...");
			e.printStackTrace();
		}
	}
	
	/** 
	 * 20170227 wxx ���ojdbc7��settimeout��oracle���ӳ�ʱ��Ч(��ʱʱ��Ϊ0ʱ��ͬ������)
	 * @param args
	 * @throws Exception
	 */
	public static void main3(String[] args) throws Exception {
		Properties props = new Properties();
		props.put("user", "SIT_KETTLE");
		props.put("password", "Ggjs123");
//		props.setProperty(oracle.jdbc.internal.OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "33000");//20170227 wxx ��������Ч
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@192.168.14.82:1521/ggjs";
		Connection conn = DriverManager.getConnection(url, props);
//		conn.setNetworkTimeout(new Executor(){
//  		  public void execute(Runnable command) {
//  		  }
//  	    }, 10*1000);
		String sql="select 1 from dual";
		PreparedStatement ps=conn.prepareStatement(sql);	
		ResultSet rst=ps.executeQuery();
		System.out.println(new Date()+": query finished!");
		Thread.sleep(22*1000);
		System.out.println(new Date()+": begin close...");
		try {
			ps.close();
			conn.close();
			System.out.println(new Date()+": close succ...");
		} catch (Exception e) {
			System.out.println(new Date()+": throw excepion...");
			e.printStackTrace();
		}
	}
	
	
	public static void main2(String[] args) throws Exception {
		try {
			Properties props = new Properties();
			props.put("user", "SIT_KETTLE");
			props.put("password", "Ggjs123");
	//		props.put("oracle.jdbc.ReadTimeout", "4000");
	//		props.put("oracle.net.CONNECT_TIMEOUT", "1000");
			Class.forName("oracle.jdbc.driver.OracleDriver");
	//		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.59.199:1521:orcl", props);
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.82:1521/ggjs", props);
			String sql="select saddr, sid, status,paddr,PORT from v$session where USERNAME='SIT_KETTLE' and MACHINE='sqr5'";
	//		conn.setNetworkTimeout(null, 1000);
			PreparedStatement ps=conn.prepareStatement(sql);
			while(true){
				System.out.println("--------------"+new Date());
				ResultSet rst=ps.executeQuery();  
				int i = 0;
				while (rst.next()) {
					i++;
//					System.out.println(rst.getString(1)+"|"+rst.getString(2)+"|"+rst.getString(3)+"|"+rst.getString(4)+"|"+rst.getString(5));
				}
				System.out.println("curr connection:"+i);
		        Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main1(String[] args) {
		System.out.println(new Date());
		Executor exe = new Executor(){
			public void execute(Runnable command) {
				System.out.println("��ѯʧ����");
			}
		};
		
		Executor exec = new Executor()
		{ public void execute( Runnable command ) { command.run(); } };
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			String url = "jdbc:mysql://192.168.59.199:3306/kettle?user=kettle&password=kettle&useUnicode=true&characterEncoding=UTF8";
//			DriverManager.setLoginTimeout(0);
//			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.159.199:1521:orcl", "pcs_kettle_yfzx", "pcs_kettle_yfzx");
			Connection conn = DriverManager.getConnection(url);
			conn.setNetworkTimeout(null, 1000);
			String sql="select * from test_from01";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.executeQuery(sql);
//        ResultSet rst=ps.executeQuery();  
//        if(rst.next()){  
//        	System.out.println(rst.getString(1));
//        }  
//        rst.close();
			
			ps.close();
			conn.close();
		} catch (SQLRecoverableException e ) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
