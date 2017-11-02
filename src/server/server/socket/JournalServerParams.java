/**
 * 
 */
package server.server.socket;

import java.io.File;

import org.jdom.Element;

import server.util.PubTools;
import server.util.XMLFactory;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:06:35
 * @Description
 * @version 1.0 Shawn create
 */
public class JournalServerParams {
	
	public static void main(String []args){
		System.out.println(JournalServerParams.PlanUploadCount) ;
	}
	/**
	 * 电子流水服务器参数配置
	 */
	public static String JournalServerType;
	public static String JournalServerIP;
	public static int JournalServerPort;
	public static int JournalServerAcceptCounts;
	public static int JournalServerMaxQueueSize;
	public static long JournalThreadInterval;
	public static int JournalJobDay;
	public static String JournalJobDevClass;
	public static String JouranlJobDevRunFlag;
	public static int JournalUploadLogReserveDay = 365;
	public static int BranchJournalServerConnectionTimeout = 300000;
	public static int BranchJournalServerPort = 0 ;
	public static int AtmcJournalServerPort;
	public static int AtmcJournalServerConnectionTimeout;
	
	public static String BranchOneList  = "" ;
	
	public static String sAtmvhIP = "128.128.96.52" ;  //总行流水服务器IP
	public static int sAtmvhPort = 25115 ;  //总行流水服务器端口
	
	
	
	/**
	 *  定时提取电子流水线程参数配置
	 */
	public static int PlanUploadCount = 5 ;
	public static int PlanUploadInterval = 5 ;
	
	/**
	 * 电子流水服务器文件相关参数配置
	 */
	public static String JournalServerFilePath = "D:\\ServerPath";
	public static String JournalServerTempFileDirectory = "";
	public static int JournalServerTempFileReserveDay = 10;
	public static String JournalViewerTempFilePath = "";
	public static int JournalViewerTempFileReserveDay = 10;
	public static String JournalXMLMsgEncoding = "UTF-8";
	public static String JournalXMLMsgRootName = "root";
	public static int JournalServerMsgContainsDataMax = 0;
	public static int JournalServerMsgNoContainsDataMax = 0;
	public static int JournalServerDataBlockMax = 0;

	/**
	 * 电子流水服务器错误码配置
	 */
	public static String Success = "0000";
	public static String Error = "1000";
	public static String CreateFileError = "1001";
	public static String FileNotFound = "1002";
	public static String WriteFileError = "1003";
	public static String ReadFileError = "1004";
	public static String FieldError = "1005";
	public static String MD5Error = "1006";
	public static String TermCodeListFormatError = "1007" ;
	public static String TimeFormatError = "1008" ;
	public static String PackageError = "1010" ;

	static {
		XMLFactory XMLreader = null;
		try {
			XMLreader = new XMLFactory(ServiceParameter.JournalServerParams);
			Element root = XMLreader.getRootElement();
			Element JournalServerConfig = (Element) root.getChild("JournalServerConfig");
			JournalServerType = JournalServerConfig.getChild("JournalServerType").getText();
			JournalServerPort = Integer.parseInt(JournalServerConfig.getChild("JournalServerPort").getText());
			JournalServerIP = JournalServerConfig.getChild("JournalServerIP").getText();
			JournalServerAcceptCounts = Integer.parseInt(JournalServerConfig.getChild("JournalServerAcceptCounts")
					.getText());
			JournalServerMaxQueueSize = Integer.parseInt(JournalServerConfig.getChild("JournalServerMaxQueueSize")
					.getText());
			JournalJobDay = Integer.parseInt(JournalServerConfig.getChild("JournalJobDay").getText());
			JournalThreadInterval = Long.parseLong(JournalServerConfig.getChild("JournalThreadInterval").getText());
			AtmcJournalServerConnectionTimeout = Integer.parseInt(JournalServerConfig.getChild(
					"AtmcJournalServerConnectionTimeout").getText());
			AtmcJournalServerPort = Integer.parseInt(JournalServerConfig.getChild("AtmcJournalServerPort").getText());
			BranchJournalServerConnectionTimeout = Integer.parseInt(JournalServerConfig.getChild(
					"BranchJournalServerConnectionTimeout").getText());
			BranchJournalServerPort = Integer.parseInt(JournalServerConfig.getChild("BranchJournalServerPort").getText());
			JournalJobDevClass = JournalServerConfig.getChild("JouranlJobDevClass").getText();
			JouranlJobDevRunFlag = JournalServerConfig.getChild("JouranlJobDevRunFlag").getText();
			JournalUploadLogReserveDay = Integer.parseInt(JournalServerConfig.getChild("JournalUploadLogReserveDay")
					.getText());
			BranchOneList = JournalServerConfig.getChild("BranchOneList").getText();
			//sAtmvhIP = JournalServerConfig.getChild("AtmvhIP").getText();
			//sAtmvhPort = Integer.parseInt(JournalServerConfig.getChild("AtmvhPort").getText());
			
			PlanUploadCount =  Integer.parseInt(JournalServerConfig.getChild("PlanUploadCount").getText());
			PlanUploadInterval =  Integer.parseInt(JournalServerConfig.getChild("PlanUploadInterval").getText());

			// 从配置文件获取返回码
			Element JournalTransResultCodeConfig = (Element) root.getChild("JournalTransResultCodeConfig");
			Success = JournalTransResultCodeConfig.getChild("Success").getText();
			Error = JournalTransResultCodeConfig.getChild("Error").getText();
			FileNotFound = JournalTransResultCodeConfig.getChild("FileNotFound").getText();
			CreateFileError = JournalTransResultCodeConfig.getChild("CreateFileError").getText();
			WriteFileError = JournalTransResultCodeConfig.getChild("WriteFileError").getText();
			FieldError = JournalTransResultCodeConfig.getChild("FieldError").getText();
			MD5Error = JournalTransResultCodeConfig.getChild("MD5Error").getText();
			ReadFileError = JournalTransResultCodeConfig.getChild("ReadFileError").getText();
			TermCodeListFormatError = JournalTransResultCodeConfig.getChild("TermCodeListFormatError").getText();
			TimeFormatError = JournalTransResultCodeConfig.getChild("TimeFormatError").getText();
			PackageError = JournalTransResultCodeConfig.getChild("PackageError").getText() ;

			// 从配置文件获取服务器文件存放配置
			Element JournalServerFileConfig = (Element) root.getChild("JournalServerFileConfig");
			JournalServerFilePath = System.getProperty("user.home")+ File.separatorChar+JournalServerFileConfig.getChild("JournalServerFilePath").getText();
			JournalServerTempFileDirectory = JournalServerFilePath + File.separatorChar
					+ JournalServerFileConfig.getChild("JournalServerTempFileDirectory").getText();
			JournalServerTempFileReserveDay = Integer.parseInt(JournalServerFileConfig.getChild(
					"JournalServerTempFileReserveDay").getText());
			JournalViewerTempFilePath = JournalServerFilePath + File.separatorChar
					+ JournalServerFileConfig.getChild("JournalViewerTempFilePath").getText();
			JournalViewerTempFileReserveDay = Integer.parseInt(JournalServerFileConfig.getChild(
					"JournalViewerTempFileReserveDay").getText());
			JournalServerMsgContainsDataMax = Integer.parseInt(JournalServerFileConfig.getChild(
					"JournalServerMsgContainsDataMax").getText());
			JournalServerMsgNoContainsDataMax = Integer.parseInt(JournalServerFileConfig.getChild(
					"JournalServerMsgNoContainsDataMax").getText());
			JournalServerDataBlockMax = Integer.parseInt(JournalServerFileConfig.getChild("JournalServerDataBlockMax")
					.getText());
			JournalXMLMsgEncoding = JournalServerFileConfig.getChild("JournalXMLMsgEncoding").getText();
			JournalXMLMsgRootName = JournalServerFileConfig.getChild("JournalXMLMsgRootName").getText();
		} catch (Exception e) {
			PubTools.log.error("读取电子流水服务器参数配置文件出现异常！" + e);
			e.printStackTrace();
		} finally {
			XMLreader = null;
		}
	}



}
