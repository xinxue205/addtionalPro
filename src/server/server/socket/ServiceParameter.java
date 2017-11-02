/**
 * 
 */
package server.server.socket;

import java.io.File;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:09:00
 * @Description
 * @version 1.0 Shawn create
 */
public class ServiceParameter {
	public static String CasReportParams = "CasReportParams.xml";

	public static String JournalServerParams = "JournalServerParams.xml";

	public static String log4jConfigFile = "log4j.xml";

	public static String dbConfigFile = "SqlMapConfig.xml";

	/**
	 * 任务调度配置文件
	 */
	public static String dailyCasQuartzConfigFile = "dailyCasQuartz.properties";

	public static String dailyJournalQuartzConfigFile = "dailyJournalQuartz.properties";

	public static String dailyQuartzConfigFile = "dailyQuartz.properties";

	/**
	 * 月任务调度配置文件
	 */
	public static String monthlyCasQuartzConfigFile = "monthlyCasQuartz.properties";

	public static String monthlyJournalQuartzConfigFile = "monthlyJournalQuartz.properties";

	public static String monthlyQuartzConfigFile = "monthlyQuartz.properties";

	public ServiceParameter() {
		init();
	}
	
	public static boolean init(){
		String configDir = ServiceParameter.class.getResource("/").getPath();
		//configDir = System.getProperty("CONFIG_DIR");	
		configDir = configDir.substring(1)+"conf";
		/*if (configDir==null || configDir.equals("")) {
			configDir += "conf";
		}
		System.out.println(configDir) ;*/
		
		return init(configDir);
	}
	/**
	 * 初始化路径
	 * @return
	 */
	public static boolean init(String configDir) {
		//System.out.println("* Using configure dir: " + configDir);		
		CasReportParams = configDir + File.separator + "CasReportParams.xml";
		JournalServerParams = configDir + File.separator + "JournalServerParams.xml";
		log4jConfigFile = configDir + File.separator + "log4j.properties";
		dbConfigFile = configDir + File.separator + "SqlMapConfig.xml";
		dailyCasQuartzConfigFile = configDir + File.separator + "dailyCasQuartz.properties";
		dailyJournalQuartzConfigFile = configDir + File.separator + "dailyJournalQuartz.properties";
		dailyQuartzConfigFile = configDir + File.separator + "dailyQuartz.properties";
		monthlyCasQuartzConfigFile = configDir + File.separator + "monthlyCasQuartz.properties";
		monthlyJournalQuartzConfigFile = configDir + File.separator + "monthlyJournalQuartz.properties";
		monthlyQuartzConfigFile = configDir + File.separator + "monthlyQuartz.properties";
		return true;
	}
	
	public static void main(String args[]){
		ServiceParameter.init();
		System.out.println("CasReportParams:" + ServiceParameter.CasReportParams);
	}


}
