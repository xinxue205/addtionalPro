package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class PostgresqlTest {
	public static void main1(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connect= null;
		connect = DriverManager.getConnection("jdbc:mysql://192.168.15.102:3306/sdi","sdi","sdiuat@2017");
		java.sql.CallableStatement cstm =  connect.prepareCall("{ ? = call ascii (?)}");
		cstm.registerOutParameter(1, Types.INTEGER);
		cstm.setString(2, "a");
//		proc.registerOutParameter(2, Types.VARCHAR);
		cstm.execute(); 
		int rs = cstm.getInt(1);
//		while (rs.next()) {
			System.out.print(rs);
//		}
	}
	
	public static void main3(String[] args) throws Exception {
		Class.forName("org.postgresql.Driver");
		Connection connect= null;
		connect = DriverManager.getConnection("jdbc:postgresql://192.168.15.103:5432/postgres","postgres","sdi@123");
		java.sql.CallableStatement cstm =  connect.prepareCall("{ ? = call ascii (?)}");
		cstm.registerOutParameter(1, Types.SMALLINT);
		cstm.setString(2, "a");
//		proc.registerOutParameter(2, Types.VARCHAR);
		cstm.execute(); 
		int rs = cstm.getInt(1);
//		while (rs.next()) {
			System.out.print(rs);
//		}
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
	
	public static void main(String[] args) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		Connection connect= null;
		connect = DriverManager.getConnection("jdbc:sqlserver://192.168.67.49:47362;databaseName=test2;integratedSecurity=false","sa","sdi@123");
		java.sql.CallableStatement cstm =  connect.prepareCall("{ ? = call ascii (?)}");
		cstm.registerOutParameter(1, Types.INTEGER);
		cstm.setString(2, "a");
		cstm.execute(); 
		int rs = cstm.getInt(1);
		System.out.print(rs);
	}
}
