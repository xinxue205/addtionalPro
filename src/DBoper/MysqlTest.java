package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlTest {
	
	private static Connection con;


	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.11.122:3306/sdi", "sdi", "sdi@123");
			con.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int i = 0;
		while (true){
			i++;
			int state = (int) (1+Math.random()*100000);
			String sql = "replace r_job_last_t values("+ state +","+ state +", now())"; // 表
	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//传入参数：
	     	System.out.println(String.valueOf(i) + ":" +  pstmt.execute(sql));
	        con.commit();
		}
	}
	
	public static void main4(String[] args) throws Exception {
		String sql = "select * from wxx_test1"; // 表
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//        preparedStatement.setMaxRows( 1 );
//        ResultSetMetaData rsmd = preparedStatement.getMetaData();
        PreparedStatement preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
        preparedStatement.setMaxRows( 1 );
        ResultSetMetaData resultMetaData = preparedStatement.getMetaData();
        int columnCount = resultMetaData.getColumnCount();
        System.out.println(columnCount);
//        ResultSet rs = preparedStatement.executeQuery(sql);
//	      while (rs.next()) {
//	          System.out.println("oject_id:"+rs.getInt(1)+",oject_name:"+rs.getString(2)); // 取得第二列的值
//	      }
	}
	
	public static void main3(String[] args) throws Exception {
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
