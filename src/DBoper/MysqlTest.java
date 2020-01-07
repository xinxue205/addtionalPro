package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MysqlTest {
	
	private static Connection con;


	static {
		try {
			System.out.println(new Date());
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.11.79:3306/sdd", "sdd", "Sdd@1234");
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://192.168.11.122:3306/sdi", "sdi", "sdi@123");
//			con = DriverManager.getConnection("jdbc:mysql:replication://127.0.0.1:3306,192.168.11.122:3306/sdi?autoReconnect=false", "sdi", "sdi@123");
			con.setAutoCommit(false); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main4(String[] args) throws Exception {
//		Connection con = DriverManager.getConnection("jdbc:mysql://192.168.11.122:3306/sdi", "sdi", "sdi@123");
		PreparedStatement ps = con.prepareStatement("lock table r_repository_log write");
		ps.execute();
//		ps = con.prepareStatement("UNLOCK TABLES");
//		ps.execute();
		Thread.sleep(30*1000);
		
		ps.close();
		con.close();
	}
	
	public static void main1(String[] args) throws Exception {
		int i = 0;
		while (true){
			i++;
			int state = (int) (1+Math.random()*100000);
			String sql = "replace r_job_last_t values("+ state +","+ state +", now())"; // ��
	        PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//���������
	     	System.out.println(String.valueOf(i) + ":" +  pstmt.execute(sql));
	        con.commit();
		}
	}
	
	public static void main5(String[] args)  {
		System.out.println(new Date());
		String sql = "SELECT * FROM r_user WHERE ID_USER=2"; // ��
//		PreparedStatement preparedStatement = con.prepareStatement(sql);
//        preparedStatement.setMaxRows( 1 );
//        ResultSetMetaData rsmd = preparedStatement.getMetaData();
        PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
        preparedStatement.setMaxRows( 1 );
        ResultSetMetaData resultMetaData = preparedStatement.getMetaData();
        int columnCount = resultMetaData.getColumnCount();
        System.out.println(columnCount);
        System.out.println(resultMetaData.getColumnName(1));
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			System.out.println(new Date() + "error!!!");
		}
//        ResultSet rs = preparedStatement.executeQuery(sql);
//	      while (rs.next()) {
//	          System.out.println("oject_id:"+rs.getInt(1)+",oject_name:"+rs.getString(2)); // ȡ�õڶ��е�ֵ
//	      }
	}
	
	public static void main3(String[] args) throws Exception {
		PreparedStatement stmt = con.prepareStatement("DELETE FROM atest WHERE a = ? "); 

		stmt.setString(1, "2"); 
		stmt.addBatch();
		
		stmt.setString(1, "3"); 
		stmt.addBatch();
		// �ύҪִ�е������� 
		int[] updateCounts = stmt.executeBatch();
		System.out.print("ok");
	}
	
	public static void main(String[] args) throws Exception {
		
//		ResultSet rs = pstmt.getGeneratedKeys(); //��ȡ���     
//		if (rs.next()) {  
//			System.out.println(rs.getInt(1));//ȡ��ID  
//		} 
		class Insert extends Thread{
			public void run() {
				try {
					String pre = "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
					String log = "";
					String tName = Thread.currentThread().getName();
					for (int i = 0; i < 200; i++) {
						log += pre+i+"\\n";
					}
					for (int j = 0; j < 111; j++) {
						for (int i = 0; i < 5555; i++) {
							System.out.println(tName + " - curr id:"+j+ "-" +i);
							Date d = new Date();
							String names = pre + i;
							String logs = log + d;
							//					log += name+"\\n";
//							String sql = "insert into t_job_logbak(JOBID, jobname, LOG_FIELD, logdate) values ("+ i +", '"+ names +"', '"+ logs +"', now())";
							String sql = "update t_job_logbak set jobname='"+ names +"', LOG_FIELD= '"+logs+"', logdate=now() where JOBID="+i;
//							String sql = "update atest set jobname='"+ names +"', LOG_FIELD= '"+logs+"' where id_job="+i;
							PreparedStatement pstmt;
							pstmt = (PreparedStatement) con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
							pstmt.executeUpdate();//ִ��
							con.commit();
							Thread.sleep(311);
						}
					}
					System.out.println("end");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//���������
			}
		}
		
		new Insert().start();;
		new Insert().start();;
//		new Insert().start();;
//		new Insert().start();;
//		new Insert().start();;
	}
	
	
	
	public static void main2(String[] args) {
		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement();
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

