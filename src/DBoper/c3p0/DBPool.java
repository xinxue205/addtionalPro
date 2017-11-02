/**
 * 
 */
package DBoper.c3p0;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author wxx
 * @date 2014-3-26 上午9:07:42
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
 */
public   class   DBPool{       
	   private   static   DBPool   dbPool;       
	   private   ComboPooledDataSource   cpds;  
	   private static String sPropertiesFile = "jdbc.properties";
	   private static int iMinPoolSize = 20;
		private static int iMaxPoolSize = 250;
		private static int iMaxIdleTime = 3000;

	   static   {       
	           dbPool=new   DBPool();       
	   }       
	   
	   public   DBPool(){       
	           try   {       
	        	Properties props = new Properties();
	   			InputStream inputStream = JdbcFactory.class.getResourceAsStream(sPropertiesFile);
	   			props.load(inputStream);
	   			cpds = new ComboPooledDataSource();
	   			cpds.setDriverClass(props.getProperty("jdbc.driverClassName"));
	   			cpds.setJdbcUrl(props.getProperty("jdbc.url"));
	   			cpds.setUser(props.getProperty("jdbc.username"));
	   			cpds.setPassword(props.getProperty("jdbc.password"));
	   			
	   			// 连接池中保留的最小连接数
	   			cpds.setMinPoolSize(iMinPoolSize);
	   			// 连接池中保留的最大连接数
	   			cpds.setMaxPoolSize(iMaxPoolSize);
	   			// 初始化时获取连接数量
	   			cpds.setInitialPoolSize(iMinPoolSize);
	   			// 最大空闲时间,指定秒内未使用则连接被丢弃。若为0则永不丢弃。
	   			cpds.setMaxIdleTime(iMaxIdleTime);
	   			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
	   			cpds.setAcquireIncrement(5);
	   			// 定义在从数据库获取新连接失败后重复尝试的次数
	   			cpds.setAcquireRetryAttempts(20);
	   			// 如果设为true那么在取得连接的同时将校验连接的有效性
	   			cpds.setTestConnectionOnCheckin(true);
	   			// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度
	   			cpds.setTestConnectionOnCheckout(true);
	   			cpds.setAutomaticTestTable("c3p0Test");
	   			cpds.setIdleConnectionTestPeriod(18000);
	   			cpds.setCheckoutTimeout(5000);       
	           }   catch   (Exception   e)   {       
	               e.printStackTrace();       
	           }       
	   }       

	   public   final   static   DBPool   getInstance(){ 
		   return   dbPool;       
	   }       

	   public   final   Connection   getConnection()   {       
	           try   {       
	                   return   cpds.getConnection();       
	           }   catch   (Exception   e)   {       
	                   throw   new   RuntimeException( "无法从数据源获取连接 ",e);       
	           }       
	   }     
	   
	   public   static   void   main(String[]   args)   throws   Exception   { 
	Connection   con   =   null; 
	con   =   DBPool.getInstance().getConnection(); 
		con.close(); 
	} 
	}

