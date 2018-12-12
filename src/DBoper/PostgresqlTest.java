package DBoper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

public class PostgresqlTest {
	public static void main4(String[] args) throws Exception {
		Connection connect= null;
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://192.168.15.48:3306/sdi","sdi","sdi@123");
		String sql = "SELECT t.name,"
				+ "? AS D_SYSDATE,"
				+ "now() AS ZLCQ_JSSJ"
				+ " FROM"
				+ " r_database T"
				+ " WHERE created_date <= now()";
		System.out.println(sql);
		PreparedStatement pstmt = connect.prepareStatement(
		            sql , ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
		pstmt.setDate( 1, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
//		pstmt.setDate( 2, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
//		pstmt.setDate( 3, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
		ResultSet rst = pstmt.executeQuery();
		if (rst.next()) {
			System.out.println(rst.getString(1));
			System.out.println(rst.getString(2));
		}
	}
	
	public static void main3(String[] args) throws Exception {
		Connection connect= null;
		Class.forName("org.postgresql.Driver");
		connect = DriverManager.getConnection("jdbc:postgresql://192.168.15.103:5432/postgres","postgres","sdi@123");
		java.sql.CallableStatement cstm =  connect.prepareCall("{ ? = call ascii (?)}");
		cstm.registerOutParameter(1, Types.SMALLINT);
		cstm.setString(2, "a");
//		proc.registerOutParameter(2, Types.VARCHAR);
		cstm.execute(); 
		int rs = cstm.getInt(1);
			System.out.print(rs);
	}
	
	public static void main2(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection connect= null;
		connect = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.82:1521:ggjs","TEST02","A");
		java.sql.CallableStatement cstm =  connect.prepareCall("{ ? = call ascii (?)}");
		cstm.registerOutParameter(1, Types.INTEGER);
		cstm.setString(2, "a");
		cstm.execute(); 
		int rs = cstm.getInt(1);
		System.out.print(rs);
	}
	
	public static void main5(String[] args) throws Exception {
		Connection connect= null;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		connect = DriverManager.getConnection("jdbc:sqlserver://192.168.15.62:1433;databaseName=SqlserverTest;integratedSecurity=false","SqlTest","Lbn@1234");
		String sql = "SELECT t.name,"
				+ "? AS D_SYSDATE,"
				+ "getdate() AS ZLCQ_JSSJ"
				+ " FROM"
				+ " Test T";
//				+ " WHERE created_date <= getdate()";
		System.out.println(sql);
		PreparedStatement pstmt = connect.prepareStatement(
		            sql , ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
		pstmt.setDate( 1, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
//		pstmt.setDate( 2, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
//		pstmt.setDate( 3, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
		ResultSet rst = pstmt.executeQuery();
		if (rst.next()) {
			System.out.println(rst.getString(1));
			System.out.println(rst.getString(2));
		}
	}
	
	public static void main6(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection connect = DriverManager.getConnection("jdbc:postgresql://192.168.15.62:5432/PsqlTest","PsqlTest","Lbn@1234");		
		String sql = "select max(col3) as selectTimeSql FROM wxx_test where col3<= now()"; // 表
		PreparedStatement preparedStatement = connect.prepareStatement(
	            sql , ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
		ResultSet rs = preparedStatement.executeQuery();
	      while (rs.next()) {
	          System.out.println("time:"+rs.getTimestamp(1)); // 取得第二列的值
	      }
	}
	
	public static void main(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection connect = DriverManager.getConnection("jdbc:postgresql://192.168.15.62:5432/PsqlTest","PsqlTest","Lbn@1234");
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection connect = DriverManager.getConnection("jdbc:mysql://192.168.15.48:3306/sdi", "sdi", "sdi@123");
		connect.setAutoCommit(false);
		
		String sql = "INSERT INTO wxx_test(col1, col2) VALUES (?,?)";
		PreparedStatement pstmt = connect.prepareStatement(sql);
		int rowCount = 10000;
		int commitCount = 100;
		try {

			for (int i = 0; i < rowCount; i++) {
				pstmt.setInt(1, i);
				pstmt.setString(2, "c200_" + i);
				pstmt.addBatch();
				if (i != 0 && (i % commitCount == 0)) {
					pstmt.executeBatch();
					connect.commit();
					pstmt.clearBatch();
					Thread.sleep(5000);
				}
			}
			pstmt.executeBatch();
			pstmt.clearBatch();
			connect.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			try {
				connect.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void main1(String[] args) throws Exception {
		Connection connect= null;
		Class.forName("org.postgresql.Driver");
		connect = DriverManager.getConnection("jdbc:postgresql://192.168.18.63:5432/GreenPlum_hck","gpadmin","gpadmin");
		String sql = "SELECT t.BD_FULL_ADDR1,"
				+ "?::TIMESTAMP AS D_SYSDATE,"
				+ "?::TIMESTAMP AS ZLCQ_KSSJ,"
				+ "now() - INTERVAL '5 MINUTE' AS ZLCQ_JSSJ"
				+ " FROM"
				+ " T_YW_CK_ZJXX T"
				+ " WHERE HCK_GXSJ > ?"
				+ " AND"
				+ "	HCK_GXSJ <= now()";
		System.out.println(sql);
		PreparedStatement pstmt = connect.prepareStatement(
		            sql , ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
		pstmt.setDate( 1, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
		pstmt.setDate( 2, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
		pstmt.setDate( 3, new Date(0), Calendar.getInstance( TimeZone.getDefault() ) );
		ResultSet rst = pstmt.executeQuery();
		if (rst.next()) {
			System.out.println(rst.getString(1));
			System.out.println(rst.getString(2));
		}
	}
}
