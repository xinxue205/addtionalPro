package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

public class DMTest {
	
	
	public static void main(String[] args) throws Exception {
		new MyThread().start();
		new MyThread().start();
		new MyThread().start();
	}
}

class MyThread extends Thread{
	public void run() {
		try {
			Class.forName("dm.jdbc.driver.DmDriver");
			Properties properties = new Properties();
			String user = "gdmz_sjzx_sjjh";
			String pass = "gdmz_sjzx_sjjh";
			properties.put( "user", user );
			properties.put( "password", pass );
//			Connection conn = DriverManager.getConnection("jdbc:dm://192.168.22.231:5236/gdmz_sjzx_sjjh", user, pass);
			Connection conn = DriverManager.getConnection("jdbc:dm://192.168.22.231:5236/gdmz_sjzx_sjjh", properties);
			conn.setAutoCommit(true); 
			String sql = "select * from R_DATABASE"; // ±ít_02, t_06, xn_gbase02
			Statement preparedStatement = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
			preparedStatement.setMaxRows(1);
			ResultSet r = preparedStatement.executeQuery(sql);
			ResultSetMetaData rm = r.getMetaData();
			int columnCount = rm.getColumnCount();
			System.out.println(Thread.currentThread().getName()+":"+columnCount);
			sleep(22*1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
