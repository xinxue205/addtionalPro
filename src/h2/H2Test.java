/**
 * 
 */
package h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 下午3:42:25
 * @Description
 * @version 1.0 Shawn create
 */
public class H2Test {
	private static Connection con;


	static {
		try {
			System.out.println(new Date());
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://192.168.11.79:3306/sdi", "sdi", "Sdi@1234");
			Class.forName("org.h2.Driver");
			con = DriverManager.getConnection("jdbc:h2:tcp://169.254.10.140:9092/~/docdb;MV_STORE=FALSE;MVCC=FALSE", "sdi", "sdi123");
//			con = DriverManager.getConnection("jdbc:mysql:replication://127.0.0.1:3306,192.168.11.122:3306/sdi?autoReconnect=false", "sdi", "sdi@123");
			con.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Throwable {
		String sql = "SELECT * FROM CERTIFICATE_APPLY"; // 表
		PreparedStatement preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
		ResultSet rs = preparedStatement.executeQuery(sql);
		while (rs.next()) {
	    	System.out.println("id_apply:"+rs.getInt(1)+",apply_addr:"+rs.getString(2)); // 取得第二列的值
		}
	}
	
	public static void main1(String[] args) throws Exception {
		String sDropIndex = "drop table if exists tbl_atmphpos_mon";
		H2Tool.h2Tools.createTableOrIndex(sDropIndex);
		String sCreateTable = "create table tbl_atmphpos_mon(test1 integer)";
		H2Tool.h2Tools.createTableOrIndex(sCreateTable);
		String sInsertSql = "insert into tbl_atmphpos_mon values (1)";
		H2Tool.h2Tools.insertDB(sInsertSql);
		String sQuerySql = "select count(*) from tbl_atmphpos_mon";
		int num = H2Tool.h2Tools.hasData(sQuerySql);
		System.out.println("表中数据个数："+num);
	}
}
