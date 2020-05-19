package DBoper;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author tzm
 *关键点<br/>
 *1、数据库连接串中必须增加rewriteBatchedStatements=true<br/>
 *2、并且jdbc插入数据时使用PreparedStatement的setxx（paraindex,paraValue）<br/>
 *  PreparedStatement的addBatch,executeBatch方法。<br/>
 */
public class BatchedInsert2 {

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
		Statement pstmt = null;
		String url = "jdbc:oracle:thin:@192.168.14.4:1521:zxjck";//"jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true";
		conn = DriverManager.getConnection(url, "kffwpt", "kffwpt");
//    	conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.4:1521:zxjck", "pcs_kettle_yfzx","pcs_kettle_yfzx");  

		//jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true
		conn.setAutoCommit(false);
		
		try {
			pstmt = conn.createStatement();
//			String sql = "insert into T11_FCTDQKDJ(LXR) values ('冯雄(尹培?')";
////			String sql = "insert into wxx_test1 values("+ i +", '走吧，大家都去吧【第"+i+"次】', '地址编号："+ i +"')"; // 表
//	        System.out.println(stmt.execute(sql));
			for (int i = 0; i < rowCount; i++) {
				String str1 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str2 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str3 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str4 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				
				String sql = "insert into a_test values ('"+ str1 +"', "+ i + " , '"+ str2 +"','"+ str3 +"','"+ str4 +"')"; // 表
				pstmt.addBatch(sql);
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
