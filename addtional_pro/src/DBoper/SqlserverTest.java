package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlserverTest {
	public static void main(String[] args) throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//		String url="jdbc:sqlserver://192.168.15.103;DatabaseName=dbfyyy;instance=dbfyyy";
//		String url="jdbc:sqlserver://192.168.15.103:1433;DatabaseName=dbfyyy";
		//jdbc:sqlserver://192.168.15.103:1433;DatabaseName=dbfyyy;instance=dbfyyy
		String url="jdbc:sqlserver://caiyingcheng\\MSSQLSERVER2:47362; DatabaseName=test2";
		String user= "sa";
		String pass= "sdi@123";
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement stmt = con.createStatement();

		String query = "SELECT Name FROM SysObjects Where XType='U' ORDER BY Name";
		ResultSet rs=stmt.executeQuery(query);
        while(rs.next()){  
            System.out.println(rs.getString(1));  
        } 
//		query = "lock tables anewtable write";
//		stmt.execute(query);
		System.out.println("end");
	}
}
