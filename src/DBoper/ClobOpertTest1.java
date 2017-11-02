package DBoper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

public class ClobOpertTest1 {

    private static PreparedStatement ps=null;  
      
    private static ResultSet rst=null;  
    
    static String driverClassName = "oracle.jdbc.OracleDriver";
    static String urlPrefix = "jdbc:oracle:thin:@";
    
    public static void main(String[] args) throws Exception{  
    	Class.forName("oracle.jdbc.driver.OracleDriver");  
//          conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.59.199:1521:orcl", "sjpt_kettle","A");  
//    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.82:1522/PDBORCL01.hnisi.com.cn", "TEST01","A");  
    	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.4:1521:zxjck", "pcs_kettle_yfzx","pcs_kettle_yfzx");  

    	String table = "test2";
		inserClob(conn, 333+"" , table, content);  
		inserClob(conn, 400+"" , table, content);  
		inserClob(conn, 555+"" , table, content);  
		inserClob(conn, 777+"" , table, content);  
//        String test=readClob("4");  
//        System.out.println(test);  
    }
	
	public static void main1(String args[]){
//			String url = "192.168.14.82:1522/PDBORCL01.hnisi.com.cn";
//		    String username = "test01";
//		    String password = "A";
		String url = "192.168.14.4:1521:zxjck";
	    String username = "pcs_kettle_yfzx";
	    String password = "pcs_kettle_yfzx";
		    Connection con = null;
		    Statement stmt = null;
		    try
		    {
		      System.out.println("connect info: [url-" + url + "], [username-" + username + "], [password-" + password + "]");
		      url = urlPrefix + url;

		      Class.forName(driverClassName).newInstance();
		      con = DriverManager.getConnection(url, username, password);

		      stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("select 1 from dual");
		      ResultSetMetaData rm = rs.getMetaData();
		      int count = rm.getColumnCount();
		      for (int i = 1; i <= count; i++) {
		    	  System.out.println(rm.getColumnType( i ));
		      }
		      
		      System.out.println("DataBase connect is OK!");
		      stmt.close();
		      con.close();
		    }
		    catch (Exception e) {
		      System.out.println("DataBase connect is ERROR:");
		      e.printStackTrace();
		      try {
		        stmt.close();
		      } catch (Exception localException1) {
		      }
		      try {
		        con.close();
		      }
		      catch (Exception localException2)
		      {
		      }
		}
	}
    
    /** 
     * 插入Clob数据 
     * @param strClob 
     * @return 
     */  
    public static boolean inserClob(Connection conn, String id,String table, String strClob){  
        boolean suc=false;  
        //CLOB使用的是oracle.sql.CLOB貌似JDBC接口的java.sql.Clob支持读入，但不支持写入Clob对象，Blob也是。。。  
        //这里需要注意的是，在给HH调试bug的时候，它出的错误是，无法把oracle.sql.CLOB对象转换成oracle.sql.CLOB，因为它连接数据库是用  
        //了Tomcat配置的数据源，所以在tomcat的common/lib目录下面有一个classes12.jar或者是ojdbc.jar，它的项目里面因为要引用oracle.sql.jar  
        //所以在项目lib目录下它也放了一个jar，这样造成了jar包之间的冲突，解决方法就是，将Web应用lib目录下的oracle驱动给移除掉（不是从构建路径上给  
        //remove掉，而是删除，如果remove不起作用，试过。。。）,然后外部引用common/lib目录下面的oracle驱动  
        CLOB clob=null;  
        //插入一个空的Clob对象，这是一个Cursor  
        String sql="INSERT into "+ table +" (ID_VALUE,NAME,VALUE_TYPE,CLOBTXT) VALUES(?,?,?,empty_clob())";  
        try {  
            //禁用自动提交事务  
            conn.setAutoCommit(false);  
            ps=conn.prepareStatement(sql);  
            ps.setString(1, id);  
            ps.setString(2, "aaa");  
            ps.setString(3, "bbb");  
            ps.executeUpdate();  
            ps.close();  
              
            //查询并获得这个cursor,并且加锁  
            sql="SELECT CLOBTXT FROM  "+ table +"  WHERE ID_VALUE=? for update";
            ps=conn.prepareStatement(sql);
            ps.setString(1, id);  
            rst=ps.executeQuery();  
            if(rst.next()){  
                clob=(CLOB)rst.getClob(1);  
            }  
            PrintWriter pw=new PrintWriter(clob.getCharacterOutputStream());  
            pw.write(strClob);  
            pw.flush();  
            ps.close();  
              
            //更新clob对象  
            sql="UPDATE  "+ table +"  set CLOBTXT =? where ID_VALUE=?";  
            ps=conn.prepareStatement(sql);  
            ps.setClob(1, clob);  
            ps.setString(2, id);  
            ps.executeUpdate();  
            ps.close();  
            conn.commit();  
            pw.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
          
        return suc;  
    }  
   
    /** 
     * 输出Clob对象 
     * @param userid 
     */  
    public static String readClob(Connection conn, String userid){  
        String test_clob="";  
        CLOB clob=null;  
        StringBuffer sb=new StringBuffer();  
        String sql="SELECT * FROM test_clob WHERE userid='"+userid+"'";  
        try {  
            ps=conn.prepareStatement(sql);  
            rst=ps.executeQuery();  
            if(rst.next()){  
                clob=(CLOB)rst.getClob(2);  
            }  
            Reader reader=clob.getCharacterStream();  
            char[] buffer=new char[1024];  
            int length=0;  
            while((length=reader.read(buffer))!=-1){  
                sb.append(buffer, 0, length);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        test_clob=sb.toString();  
        return test_clob;  
    }  
    
	static String content = "长字符串内容写入到数据库中时，经常出现单列的长度最长不能超过4000的限制，而实际设置varchar类型时，写入1000～2000长度的内容却进场报错。所以，对于单字段超长的字符内容，最好使用CLOB类型。CLOB类型可以存放的内容非常大，个人测试写入过70多k的字符。\r"
			+"\r"
			+"不同的session获取，写入CLOB时，略有不同，可能与hibernate的版本有关，还没有仔细研究。\r"
			+"\r"
			+"单独获取hibernate的会话，实现代码如下：\r"
			+"\r"
			+"Session session = null;  \r"
			+"Transaction tx = null;  \r"
			+"java.io.Writer writer = null;  \r"
			+"Reader reader = null;  \r"
			+"try {  \r"
			+"    session = this.getSessionFactory().openSession();  \r"
			+"    String serviceStr = entity.getScheduleService();\r"
			+"    entity.setScheduleService(\"\");\\/\\/CLOB字段\r"
			+"    tx = session.beginTransaction();  \r"
			+"    session.saveOrUpdate(entity);  \r"
			+"    session.flush();  \r"
			+"    session.refresh(entity, LockMode.UPGRADE);                         \r"
			+"    CLOB clob = CLOB.createTemporary(session.connection(), false,CLOB.DURATION_SESSION);\r"
			+"    clob.open(CLOB.MODE_READWRITE);\r"
			+"    writer = new BufferedWriter(clob.getCharacterOutputStream());                          \r"
			+"    reader = new BufferedReader(new StringReader(serviceStr));  \r"
			+"    char[] buffer = new char[1024];  \r"
			+"    int length;  \r"
			+"    while ((length = reader.read(buffer)) > 0) {  \r"
			+"writer.write(buffer, 0, length);                  \r"
			+"writer.flush();  \r"
			+"    }    \r"
			+"    clob.close();\r"
			+"    tx.commit();            \r"
			+"} catch (Exception e) {               \r"
			+"    session.getTransaction().rollback();  \r"
			+"} finally {  \r"
			+"    try {  \r"
			+"if (null != reader)  \r"
			+"   reader.close();  \r"
			+"if (null != writer)  \r"
			+"   writer.close();  \r"
			+"    } catch (IOException ioe) {                    \r"
			+"    }  \r"
			+"    if (session != null) {  \r"
			+"if (session.isOpen()) {  \r"
			+"   session.close();  \r"
			+"}  \r"
			+"    }  \r"
			+"}\r"
			+"\r"
			+"可能出现的问题是，CLOB.createTemporary方法报错，或无法创建对应的CLOB，可以试试下面的方法：\r"
			+"该方法要求对应实体的字段类型必须设置为CLOB类型，即hibernate映射文件中字段类型要设置为CLOB类型。\r"
			+"\r"
			+"\r"
			+"boolean res = false;\r"
			+"Session session = null;\r"
			+"Transaction tr = null;\r"
			+"java.io.Writer writer = null;  \r"
			+"Reader reader = null;\r"
			+"try {\r"
			+"  session = this.getSession();\r"
			+"  if(session != null){\r"
			+" String name = null;\r"
			+"      tr = session.beginTransaction () ;\r"
			+"      for ( Iterator iter = events.iterator () ; iter.hasNext () ; ) {\r"
			+" EventHistory item = ( EventHistory ) iter.next () ;\r"
			+" name = item.getServiceName();\r"
			+" Clob tmpClob = Hibernate.createClob(\" \");\\/\\/创建空的CLOB\r"
			+" item.setServiceNameClob(tmpClob);//先写入空的CLOB\r"
			+" session.save ( item ) ;\r"
			+" session.flush();\r"
			+" session.refresh(item, LockMode.UPGRADE);                    \r"
			+" SerializableClob clob = (SerializableClob)item.getServiceNameClob();//取出已经写入的CLOB\r"
			+" Clob wrapClob = clob.getWrappedClob();\r"
			+" CLOB oracleClob = (CLOB)wrapClob;//类型转换\r"
			+" oracleClob.open(CLOB.MODE_READWRITE);//打开CLOB读写通道\r"
			+" writer = new BufferedWriter(oracleClob.getCharacterOutputStream());                           \r"
			+" reader = new BufferedReader(new StringReader(name));  \r"
			+" char[] buffer = new char[1024];  \r"
			+" int length;  \r"
			+" while ((length = reader.read(buffer)) > 0) {  \r"
			+"     writer.write(buffer, 0, length); //按字节分多次写入，不可以一次写入太多\r"
			+"     writer.flush();  \r"
			+" }\r"
			+" reader.close();\r"
			+" writer.close();\r"
			+" oracleClob.close();\r"
			+"      }\r"
			+"      session.flush();\r"
			+"      tr.commit () ;              \r"
			+"      session.close () ;\r"
			+"      res = true ;\r"
			+"  }\r"
			+"}\r"
			+"catch (Exception ex) {\r"
			+"  if (tr != null)\r"
			+"    tr.rollback();\r"
			+"  try {  \r"
			+"      if (null != reader)  \r"
			+" reader.close();  \r"
			+"      if (null != writer)  \r"
			+" writer.close();  \r"
			+"  } catch (IOException ioe) {                    \r"
			+"  } \r"
			+"  if (session != null)\r"
			+"    session.close();\r"
			+"  log.error(\"Occured exception when add eventsHistory:\", ex);\r"
			+"}\r"
			+"java对Oracle中Clob数据类型是不能够直接插入的，但是可以通过流的形式对clob类型数据写入或者读取，网上代码并不算特别多，讲的也不是很清楚，我对网上资料进行了整理和总结，具体看代码：\r"
			+"写入clob数据\r"
			+"import java.io.Writer;\r"
			+"import java.sql.Connection;\r"
			+"import java.sql.DriverManager;\r"
			+"import java.sql.ResultSet;\r"
			+"import java.sql.Statement;\r"
			+"\r"
			+"public class TestClobIn {\r"
			+"  public static void main(String args[]){\r"
			+"  String data=\"this is a long passage！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！\";\r"
			+"  Writer outStream = null;\r"
			+" \\/\\/通过JDBC获得数据库连接\r"
			+"  try {\r"
			+"  Class.forName(\"oracle.jdbc.driver.OracleDriver\");\r"
			+"  Connection con = DriverManager.getConnection(\"jdbc:oracle:thin:@localhost:1521:ewins\", \"scott\", \"tiger\");\r"
			+"  con.setAutoCommit(false);\r"
			+"  Statement st = con.createStatement();\r"
			+"  //插入一个空对象empty_clob()，这个是必须的\r"
			+"  st.executeUpdate(\"insert into TESTCLOB(ID, NAME, CLOBATTR)values(2,'thename', empty_clob())\");\r"
			+"  //锁定数据行进行更新，注意“for update”语句,这里不用for update锁定不可以插入clob\r"
			+"  ResultSet rs = st.executeQuery(\"select CLOBATTR from TESTCLOB where ID=1 for update\");\r"
			+"  if (rs.next())\r"
			+"  {\r"
			+"  //得到java.sql.Clob对象后强制转换为oracle.sql.CLOB\r"
			+"  oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(\"CLOBATTR\");\r"
			+"  outStream = clob.getCharacterOutputStream();\r"
			+"  //data是传入的字符串，定义：String data\r"
			+"  char[] c = data.toCharArray();\r"
			+"  outStream.write(c, 0, c.length);\r"
			+"  }\r"
			+"  outStream.flush();\r"
			+"  outStream.close();\r"
			+"  con.commit();\r"
			+"  con.close();\r"
			+"  } catch (Exception e) {\r"
			+"   // TODO Auto-generated catch block\r"
			+"   e.printStackTrace();\r"
			+"  }\r"
			+"  }\r"
			+"}\r"
			+"读取clob数据\r"
			+"import java.io.InputStream;\r"
			+"import java.io.Reader;\r"
			+"import java.sql.Connection;\r"
			+"import java.sql.ResultSet;\r"
			+"import java.sql.Statement;\r"
			+"\r"
			+"public class TestClobOut {\r"
			+"  public static void main(String args[]){\r"
			+"  String data;\r"
			+"  Reader inStream=null;\r"
			+"  //获得数据库连接\r"
			+"  Connection con = ConnectionFactory.getConnection();//ConnectionFactory类是另外定义的，不必纠结\r"
			+"  con.setAutoCommit(false);\r"
			+"  Statement st = con.createStatement();\r"
			+"  //不需要“for update”\r"
			+"  ResultSet rs = st.executeQuery(\"select CLOBATTR from TESTCLOB where ID=1\");\r"
			+"  if (rs.next())\r"
			+"  {\r"
			+"  java.sql.Clob clob = rs.getClob(\"CLOBATTR\");\r"
			+"  inStream = clob.getCharacterStream();\r"
			+"  char[] c = new char[(int) clob.length()];\r"
			+"  inStream.read(c);\r"
			+"  //data是读出并需要返回的数据，类型是String\r"
			+"  data = new String(c);\r"
			+"  inStream.close();\r"
			+"  }\r"
			+"  inStream.close();\r"
			+"  con.commit();\r"
			+"  con.close();\r"
			+"  }\r"
			+"}\r"
			+"对比我们可以看出，无论出库入库，都要对clob数据类型进行查询操作，写入clob数据相对来说更复杂一点，需要先插入empty_clob() 值，然后使用带“for update”的查询语句锁定更新行，最后实例化输出流并对clob类型字段数据进行写入操作；读取clob相对轻松一些，利用 getCharacterStream方法得到输入流，从数据库中clob字段下，直接将数据读取出来。\r"
			+"原文：http://www.linuxidc.com/Linux/2013-06/86381.htm\r";
}

