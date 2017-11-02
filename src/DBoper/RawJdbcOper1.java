package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RawJdbcOper1 {
	
	public static void main(String[] args) {
		String sql = "SELECT ID_JOB FROM R_JOB_EXTENAL WHERE STATE IN (0, 1, 2)";
		String jobs = getStartJobs(sql);
		System.out.println("started job is :"+jobs);
	}
	
	public int updateRec() {
		PreparedStatement ps;
		ResultSet rs = null;
		int count = 0;
		
		Connection conn = getConnection();
		String sql = "insert into z_wxx str0='2013-11-11'";
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
				System.out.print(rs.getString(1)+"||"+rs.getString(2)+"||"+rs.getString(3)+"||"+rs.getString(4)+"||"+rs.getString(5));
			}
			if (ps != null) {
				rs.close();
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static String getStartJobs(String sql) {
		PreparedStatement ps;
		ResultSet rs = null;
		String jobIds = "";
		
		Connection conn = getConnection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				jobIds += rs.getString(1)+",";
			}
			if (ps != null) {
				rs.close();
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobIds;
	}
	
	static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(  
	                "jdbc:oracle:thin:@192.168.14.4:1521:zxjck", "pcs_kettle_yfzx", "pcs_kettle_yfzx");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
