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
 * @date 2014-3-26 ����9:07:42
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 �����������ϵͳ���޹�˾
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
	   			
	   			// ���ӳ��б�������С������
	   			cpds.setMinPoolSize(iMinPoolSize);
	   			// ���ӳ��б��������������
	   			cpds.setMaxPoolSize(iMaxPoolSize);
	   			// ��ʼ��ʱ��ȡ��������
	   			cpds.setInitialPoolSize(iMinPoolSize);
	   			// ������ʱ��,ָ������δʹ�������ӱ���������Ϊ0������������
	   			cpds.setMaxIdleTime(iMaxIdleTime);
	   			// �����ӳ��е����Ӻľ���ʱ��c3p0һ��ͬʱ��ȡ��������
	   			cpds.setAcquireIncrement(5);
	   			// �����ڴ����ݿ��ȡ������ʧ�ܺ��ظ����ԵĴ���
	   			cpds.setAcquireRetryAttempts(20);
	   			// �����Ϊtrue��ô��ȡ�����ӵ�ͬʱ��У�����ӵ���Ч��
	   			cpds.setTestConnectionOnCheckin(true);
	   			// �����������Ӳ��Զ�ִ�еĲ�����䡣��ʹ�����Ӳ��Ե���������һ������߲����ٶ�
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
	                   throw   new   RuntimeException( "�޷�������Դ��ȡ���� ",e);       
	           }       
	   }     
	   
	   public   static   void   main(String[]   args)   throws   Exception   { 
	Connection   con   =   null; 
	con   =   DBPool.getInstance().getConnection(); 
		con.close(); 
	} 
	}

