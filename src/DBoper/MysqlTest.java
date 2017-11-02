package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlTest {
	
	private static Connection con;


	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.15.82:3306/sdi", "sdi", "sdi123");
			con.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		PreparedStatement stmt = con.prepareStatement("DELETE FROM wxx_test WHERE user_id = ? "); 

		stmt.setString(1, "2"); 
		stmt.addBatch();
		
		stmt.setString(1, "3"); 
		stmt.addBatch();
		// 提交要执行的批处理 
		int[] updateCounts = stmt.executeBatch();
		System.out.print("ok");
	}
	
	public static void main1(String[] args) throws Exception {
		
		String sql = "insert into wxx_test1(str1, str2) values ('w1', 'w1')";
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//传入参数：
		pstmt.executeUpdate();//执行
		
		ResultSet rs = pstmt.getGeneratedKeys(); //获取结果     
		if (rs.next()) {  
			System.out.println(rs.getInt(1));//取得ID  
		} 
		System.out.println("end");
	}
	
	
	public static void main2(String[] args) {
		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
			stmt.setFetchSize(Integer.MIN_VALUE);
			stmt.setFetchDirection(1000);
			stmt.setQueryTimeout(7200);
			String query = "SELECT * FROM c3p0test";
			ResultSet rs=stmt.executeQuery(query);
			
//			query = "lock tables anewtable write";
//			stmt.execute(query);
			System.out.println("end");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
