package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

public class GbaseTest {
	
	private static Connection con;

	static {
		try {
			Class.forName("com.gbase.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:gbase://192.168.15.116:5258/test","gbase","gbase123");
			con.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		PreparedStatement stmt = con.prepareStatement("DELETE FROM user_info01 WHERE user_id = ? "); 
//		stmt.setString(1, "2"); 
//		stmt.addBatch();
//		stmt.setString(1, "3"); 
//		stmt.addBatch();
//		
//		PreparedStatement stmt = con.prepareStatement("INSERT INTO user_info01 VALUES (?,?,?)"); 
//		stmt.setString(1, "2"); 
//		stmt.setString(2, "22"); 
//		stmt.setString(3, "222"); 
//		stmt.addBatch();
//		stmt.setString(1, "3"); 
//		stmt.setString(2, "33"); 
//		stmt.setString(3, "333"); 
//		stmt.addBatch();
		
		
		Statement stmt = con.createStatement(); 
		stmt.addBatch("DELETE FROM user_info01 WHERE user_id = 2 "); 
		stmt.addBatch("DELETE FROM user_info01 WHERE user_id = 3 "); 

		// 提交要执行的批处理 
		int[] updateCounts = stmt.executeBatch();
		con.commit();
		System.out.print("ok");
	}
	
	public static void main1(String[] args) throws Exception {
		
		Statement stmt = con.createStatement(); 

		stmt.addBatch("INSERT INTO user_info01 VALUES ('2', '22', '222')"); 
		stmt.addBatch("INSERT INTO user_info01 VALUES ('3', '33', '333')"); 
		int[] updateCounts = stmt.executeBatch();
		con.commit();
		System.out.print("ok");
	}
}
