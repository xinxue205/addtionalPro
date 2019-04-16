package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * 
 * @author tzm
 *�ؼ���<br/>
 *1�����ݿ����Ӵ��б�������rewriteBatchedStatements=true<br/>
 *2������jdbc��������ʱʹ��PreparedStatement��setxx��paraindex,paraValue��<br/>
 *  PreparedStatement��addBatch,executeBatch������<br/>
 */
public class BatchedInsert {

	/**
	 * ���ݿ����Ӵ�.
	 *  �������ύ�������ڴ�������ʱʹ�� rewriteBatchedStatements=true����.
	 */
//	static final String DB_URL = "jdbc:gbase://192.168.15.116:5258/test?user=gbase&password=gbase123&useUnicode=true"
//			+ "&characterEncoding=utf8&rewriteBatchedStatements=true";

	/**
	 * �ܲ�������.
	 */
	static int rowCount = 20000000; // ����������
	
	/**
	 * һ���ύ����.
	 */
	static int commitCount = 50000;// �������ύһ�� ���Ը���jvm���õ��ڴ�Ѵ�С�����������ֵ�������ֵ�Ĵ�С�������йأ��Ƽ�65535�ύһ�Ρ�
	
	/*
	 * create table tgbase (c1 varchar(100), c2 varchar(100), c3 varchar(100),c4 varchar(100),c5 varchar(100),c6 varchar(100),c7 varchar(100),c8 varchar(100),c9 varchar(100),c10 varchar(100));
	 */
	static String sql = "INSERT INTO a_testt VALUES (?,?,?,?,?)";
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
		String url = "jdbc:oracle:thin:@192.168.16.9:1521:ora10";//"jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true";
		conn = DriverManager.getConnection(url, "sdi_test", "sdi_test");
//    	conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.4:1521:zxjck", "pcs_kettle_yfzx","pcs_kettle_yfzx");  

		//jdbc:gbase://192.168.15.116:5258/test?rewriteBatchedStatements=true
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(sql);
		try {

			for (int i = 10000000; i < rowCount; i++) {
				pstmt.setString(1, "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.setInt(2, i);
				pstmt.setString(3, UUID.randomUUID().toString() + "_" + i);
				pstmt.setString(4, "c300000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.setString(5, "c400000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i);
				pstmt.addBatch();
				if (i != 0 && (i % commitCount == 0)) {
					System.out.println("curr linenr:" + i);
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
