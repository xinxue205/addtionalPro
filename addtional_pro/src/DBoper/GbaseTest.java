package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;

public class GbaseTest {
	
	private static Connection conn;

	static {
		try {
			Class.forName("com.gbase.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:gbase://192.168.15.64:5258/testdb","sdi_gbase","sdi_gbase");
			conn.setAutoCommit(true); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String sql = "select * from g560w02"; // 表t_02, t_06, xn_gbase02
		Statement preparedStatement = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
	        preparedStatement.setMaxRows(1);
	        ResultSet r = preparedStatement.executeQuery(sql);
	        ResultSetMetaData rm = r.getMetaData();
	        int columnCount = rm.getColumnCount();
	        System.out.println(columnCount);
	}
	
	public static void main3(String[] args) throws Exception {
		String sql = "select * from g560w02"; // 表t_02, t_06, xn_gbase02
		 PreparedStatement preparedStatement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
	        preparedStatement.setMaxRows(1);
	        ResultSet r = preparedStatement.executeQuery();
	        ResultSetMetaData resultMetaData = preparedStatement.getMetaData();
	        int columnCount = resultMetaData.getColumnCount();
	        System.out.println(columnCount);
	}
	
	public static void main2(String[] args) throws Exception {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_info01 WHERE user_id = ? "); 
		stmt.setString(1, "2"); 
		stmt.addBatch();
		stmt.setString(1, "3"); 
		stmt.addBatch();
		
		// 提交要执行的批处理 
		int[] updateCounts = stmt.executeBatch();
//		con.commit();
		System.out.print("ok");
	}
	
	public static void main1(String[] args) throws Exception {
		
		Statement stmt = conn.createStatement(); 

		stmt.addBatch("INSERT INTO user_info01 VALUES ('2', '22', '222')"); 
		stmt.addBatch("INSERT INTO user_info01 VALUES ('3', '33', '333')"); 
		int[] updateCounts = stmt.executeBatch();
		conn.commit();
		System.out.print("ok");
	}
}
