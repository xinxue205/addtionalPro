package DBoper;

import java.sql.*; 
import java.util.*;
public class GbaseTest2 {
    public static void main(String[] args) {
        try {
//            Class.forName("com.sybase.jdbc2.jdbc.SybDriver").newInstance();
        	Class.forName("com.gbase.jdbc.Driver");
        	Connection conn = DriverManager.getConnection("jdbc:gbase://192.168.15.116:5258/test","gbase","gbase123");
            
            String sql = "SELECT * FROM xn_gbase"; // 表
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setMaxRows( 1 );
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
//            ResultSetMetaData rsmd = preparedStatement.getMetaData();
            //得到了ResultSetMetaData 
            //得到列的个数、列的别名及列名
            for(int i=0;i<resultSetMetaData.getColumnCount();i++){
	            String columnLabel=resultSetMetaData.getColumnLabel(i+1);
	            System.out.println(columnLabel);
	            String columnName=resultSetMetaData.getColumnName(i+1);
	            System.out.println(columnName+":"+columnLabel);
            }	
//            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String sql = "select id,str from wxx_test"; // 表
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                System.out.println("oject_id:"+rs.getInt(1)+",oject_name:"+rs.getString(2)); // 取得第二列的值
//            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
