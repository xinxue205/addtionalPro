package DBoper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OdbcTest {
	public static void main(String[] args) throws Exception {
		Connection conn = null;  
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
        try {  
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  //sun.jdbc.odbc.JdbcOdbcDriver
//            String url = "jdbc:odbc:MYSQL-15.82a-sdi_kettle"; //
            String url = "jdbc:odbc:MYSQL-15.82a-sdi_kettle?defaultFetchSize=500&useCursorFetch=true"; //
            conn = DriverManager.getConnection(url, "sdi", "sdi123");  
            pstmt = conn.prepareStatement("SELECT * from r_database");  
            rs = pstmt.executeQuery();  
            while(rs.next()){  
                System.out.println(rs.getString(2));  
            }  
        } finally {  
            if(rs != null){  
                try {  
                   rs.close();  
               } catch (SQLException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
                rs = null;  
            }  
              
            if(pstmt != null){  
                try {  
                   pstmt.close();  
               } catch (SQLException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
                pstmt = null;  
            }  
              
            if(conn != null){  
                try {  
                   conn.close();  
               } catch (SQLException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
                conn = null;  
            }  
        }  
	}
}
