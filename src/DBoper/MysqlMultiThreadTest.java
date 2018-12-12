package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MysqlMultiThreadTest {
	
	static {
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		new ExecuteThread().start();
		new ExecuteThread().start();
	}
	
}

class ExecuteThread extends Thread{
	@Override
	public void run() {
		
		try {
//			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.82:1521/ggjs", "wxx_kettle_test", "A");
//			con.setAutoCommit(false); 
//			PreparedStatement ps = con.prepareStatement("lock table wxx_test IN EXCLUSIVE MODE");
//			System.out.println(new Date() + " prepare to exe");
//			ps.execute();
//			Thread.sleep(3000);
//			con.commit();
			
			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.15.82:3306/sdi", "sdi", "sdi123");
			PreparedStatement ps = con.prepareStatement("lock table wxx_test write");
			ps.execute();
			ps = con.prepareStatement("UNLOCK TABLES");
			ps.execute();
			Thread.sleep(3000);
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print(new Date());
			e.printStackTrace();
		}
	}
}
