package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleTest {
	
	private static Connection conn;


	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.16.9:1521/ora10", "sdi_test", "sdi_test");
			conn.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String sql = "select * from XN_02"; // 表t_02, t_06, xn_gbase02
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//        preparedStatement.setMaxRows( 1 );
//        ResultSetMetaData rsmd = preparedStatement.getMetaData();
        PreparedStatement preparedStatement = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
        preparedStatement.setMaxRows(1);
        ResultSet r = preparedStatement.executeQuery();
        ResultSetMetaData resultMetaData = preparedStatement.getMetaData();
        int columnCount = resultMetaData.getColumnCount();
        System.out.println(columnCount);
//        ResultSet rs = preparedStatement.executeQuery(sql);
//	      while (rs.next()) {
//	          System.out.println("oject_id:"+rs.getInt(1)+",oject_name:"+rs.getString(2)); // 取得第二列的值
//	      }
	}
	
	public static void main4(String[] args) throws Exception {
    	System.out.println(System.getProperty("file.encoding"));

        Statement stmt = conn.createStatement();
		
		 byte[] t = {-73, -21, -48, -37, 40, -46, -4, -59, -32, 42};
 		String s =  new String(t, "GBK");
 		byte[] x = s.getBytes();
 		for (int i = 0; i < x.length; i++) {
			System.out.print(x[i]);
		}
		System.out.println("");
     	String sql = "insert into T11_FCTDQKDJ(LXR) values ('"+  s +"')"; // 表
     	System.out.println(stmt.execute(sql));
		
//		String sql = "insert into T11_FCTDQKDJ(LXR) values ('冯雄(尹培?')";
////		String sql = "insert into wxx_test1 values("+ i +", '走吧，大家都去吧【第"+i+"次】', '地址编号："+ i +"')"; // 表
//        System.out.println(stmt.execute(sql));
        conn.commit();
//		
//		ResultSet rs = pstmt.getGeneratedKeys(); //获取结果     
//		if (rs.next()) {  
//			System.out.println(rs.getInt(1));//取得ID  
//		} 
		System.out.println("end");
	}
	
	public static void main1(String[] args) throws Exception {
		PreparedStatement pstmt = conn.prepareStatement("SELECT * from T11_FCTDQKDJ");  
        ResultSet rs = pstmt.executeQuery();  
        while(rs.next()){  
            System.out.println(rs.getString(2));  
        }  
		System.out.print("ok");
	}
	
	public static void main3(String[] args) throws Exception {
		
		String sql = "insert into wxx_test1(str1, str2) values ('w1', 'w1')";
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//传入参数：
		pstmt.executeUpdate();//执行
		
		ResultSet rs = pstmt.getGeneratedKeys(); //获取结果     
		if (rs.next()) {  
			System.out.println(rs.getInt(1));//取得ID  
		} 
		System.out.println("end");
	}
	
	
	public static void main2(String[] args) {
		try {
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
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
