package DBoper;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author tzm
 *关键点<br/>
 *1、数据库连接串中必须增加rewriteBatchedStatements=true<br/>
 *2、并且jdbc插入数据时使用PreparedStatement的setxx（paraindex,paraValue）<br/>
 *  PreparedStatement的addBatch,executeBatch方法。<br/>
 */
public class BatchedInsert {

	/**
	 * 数据库连接串.
	 *  打开批量提交，必须在创建连接时使用 rewriteBatchedStatements=true参数.
	 */
//	static final String DB_URL = "jdbc:gbase://192.168.15.116:5258/test?user=gbase&password=gbase123&useUnicode=true"
//			+ "&characterEncoding=utf8&rewriteBatchedStatements=true";

	/**
	 * 总插入条数.
	 */
	static int rowCount = 50000; // 插入总条数
	
	/**
	 * 一次提交条数.
	 */
	static int commitCount = 1;// 多少条提交一次 可以根据jvm配置的内存堆大小来调整这个数值，这个数值的大小跟性能有关，推荐65535提交一次。
	
	/*
	 * create table tgbase (c1 varchar(100), c2 varchar(100), c3 varchar(100),c4 varchar(100),c5 varchar(100),c6 varchar(100),c7 varchar(100),c8 varchar(100),c9 varchar(100),c10 varchar(100));
	 */
	static String sql = "INSERT INTO a_test VALUES (?,?,?,?,?)";
//	static String sql = "INSERT INTO GZMZ_JJHD_SQ_SQRXX_A (YB) VALUES ( ?)";//INSERT INTO GZMZ_JJHD_SQ_SQRXX_A VALUES (?)

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
//			Class.forName("com.gbase.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

		Connection conn = null;
		PreparedStatement pstmt = null;
		String url = "jdbc:oracle:thin:@192.168.14.7:1521:SJZ10G";//"jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true";
		conn = DriverManager.getConnection(url, "test001", "test001");
//    	conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.4:1521:zxjck", "pcs_kettle_yfzx","pcs_kettle_yfzx");  

		//jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(sql);
		try {

			for (int i = 0; i < rowCount; i++) {
				pstmt.setString(1, "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.setInt(2, i);
				pstmt.setString(3, "c200000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.setString(4, "c300000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.setString(5, "c400000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.addBatch();
				if (i != 0 && (i % commitCount == 0)) {
					pstmt.executeBatch();
					conn.commit();
					pstmt.clearBatch();
				}
			}
			pstmt.executeBatch();
			pstmt.clearBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}
}
