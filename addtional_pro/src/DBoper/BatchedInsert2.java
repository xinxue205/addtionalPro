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
 *�ؼ���<br/>
 *1�����ݿ����Ӵ��б�������rewriteBatchedStatements=true<br/>
 *2������jdbc��������ʱʹ��PreparedStatement��setxx��paraindex,paraValue��<br/>
 *  PreparedStatement��addBatch,executeBatch������<br/>
 */
public class BatchedInsert2 {

	/**
	 * ���ݿ����Ӵ�.
	 *  �������ύ�������ڴ�������ʱʹ�� rewriteBatchedStatements=true����.
	 */
//	static final String DB_URL = "jdbc:gbase://192.168.15.116:5258/test?user=gbase&password=gbase123&useUnicode=true"
//			+ "&characterEncoding=utf8&rewriteBatchedStatements=true";

	/**
	 * �ܲ�������.
	 */
	static int rowCount = 50000; // ����������
	
	/**
	 * һ���ύ����.
	 */
	static int commitCount = 1;// �������ύһ�� ���Ը���jvm���õ��ڴ�Ѵ�С�����������ֵ�������ֵ�Ĵ�С�������йأ��Ƽ�65535�ύһ�Ρ�
	
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
//			String sql = "insert into T11_FCTDQKDJ(LXR) values ('����(����?')";
////			String sql = "insert into wxx_test1 values("+ i +", '�߰ɣ���Ҷ�ȥ�ɡ���"+i+"�Ρ�', '��ַ��ţ�"+ i +"')"; // ��
//	        System.out.println(stmt.execute(sql));
			for (int i = 0; i < rowCount; i++) {
				String str1 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str2 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str3 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				String str4 = "c100000000000000000000000000000000000000000000000000000000000000000000000000000000_" + i;
				
				String sql = "insert into a_test values ('"+ str1 +"', "+ i + " , '"+ str2 +"','"+ str3 +"','"+ str4 +"')"; // ��
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
