package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.driver.OracleTypes;

public class ProcedureTest {
	
	public static void main(String[] args) throws Exception {
//		Connection connection = DBoper.DBFactory.getConnection();
		 //1.构造驱动  
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        // 2.创建连接  
        Connection connection = DriverManager.getConnection(  
                "jdbc:oracle:thin:@192.168.14.82:1521:ggjs", "kettle5", "A");
        //db_url=jdbc:oracle:thin:@192.168.22.232:1521:orcl
		String argsT[] = {"orcl-14.82-kettle5", "TEST_FROM01"};
        OracleCallableStatement ocs = null;  
        try {
			ocs=(OracleCallableStatement) connection.prepareCall("{call P_GETJOBID(?,?,?)}");
		
	        ocs.registerOutParameter(3, OracleTypes.CURSOR);  
	        ocs.setString(1, argsT[0]);
	        ocs.setString(2,argsT[1]); 
	        ocs.execute();  
	        ResultSet cur = ocs.getCursor(3);  
	        while (cur.next()){  
	            System.out.println(cur.getString(1));  
	        }  
	        cur.close();  
	        cur=null;
        } catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {  
                if(ocs!=null){  
                    ocs.close();  
                    ocs=null;  
                }  
                if(connection!=null){  
                	connection.close();  
                	connection=null;  
                }  
            } catch (Exception e1) {  
                e1.printStackTrace();  
            }  
		}
	}
}
