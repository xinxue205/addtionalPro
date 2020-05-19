/**
 * 
 */
package server.server.socket.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import server.server.socket.JournalServerParams;
import server.server.socket.ServiceParameter;
import server.util.JdbcFactory;
import server.util.PubTools;
import server.util.ZipTool;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:50:03
 * @Description
 * @version 1.0 Shawn create
 */
public class FileTools {

	private String errorFileList = "";

	private long iFileLength;

	private String serverFilePath = "";


	private String tempFileName = ""; // �ļ���

	private static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	//private SqlMapClient sqlMapper; //���ݿ�ʹ���� 20111221 xq ����

	public FileTools() {

		// �жϷ��������� ����Ƿ��м��ͻ�ȡ�������ڵ��ļ�Ŀ¼ ��������оͻ�ȡ���е�Ŀ¼
		if (JournalServerParams.JournalServerType.equals("branch")) {
			this.serverFilePath = JournalServerParams.JournalServerFilePath;
		} else {
			this.serverFilePath = JournalServerParams.JournalViewerTempFilePath;
		}
	}

	public long getTempFileLength() {
		return this.iFileLength;
	}

	public String getTempFileName() {
		return this.tempFileName;
	}

	public void setServerFilePath(String serverFilePath) {
		this.serverFilePath = serverFilePath;
	}

	public String getErrorFileList() {
		return this.errorFileList;
	}

	/**
	 * ��ȡ�ļ���hashֵ
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param hashType
	 *            hash����
	 * @return �ļ���hashֵ
	 * @throws Exception
	 * 
	 */
	public String getHash(String fileName, String hashType) throws Exception {
		FileInputStream fis;
		fis = new FileInputStream(fileName);// ��ȡ�ļ�
		byte[] buffer = new byte[10240];
		MessageDigest md5 = MessageDigest.getInstance(hashType);
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, numRead);
		}
		fis.close();
		return toHexString(md5.digest());
	}

	/**
	 * ��ȡָ���ļ���MD5ֵ
	 * 
	 * @param fileName
	 *            //�ļ���
	 * @return �����ļ���MD5ֵ ������쳣�򷵻ؿ�
	 */
	public String getFileMD5(String fileName) {
		try {
			return getHash(fileName, "MD5");
		} catch (Exception e) {
			PubTools.log.error("Catch Exception:" + e.toString());
			return null;
		}
	}

	/**
	 * ����ʱ����ɾ��ָ���ļ����ڵ��ļ� ʱ��Ϊ�ӽ��쿪ʼ�� �Ƚ���С10�����ڵ��ļ�
	 * 
	 * @param filePath
	 * @param timeSpan
	 * @return
	 */
	public static boolean DeleteFileByTime(String filePath, int timeSpan) {
		
		PubTools.log.info("��ʼɾ���ļ�·��:["+filePath+"] ʱ��Ϊ[" +timeSpan + "]����ˮ��Ϣ") ;
		File[] fileList = null;
		File pathFile = new File(filePath);
		if (pathFile.isDirectory()) {
			fileList = pathFile.listFiles();
		} else if (pathFile.isFile()) {
			fileList = new File[1];
			fileList[0] = pathFile;
		}
		
		long lNowDate = Calendar.getInstance().getTime().getTime();
		long lDaySpan = 1000 * 60 * 60 * 24 * timeSpan;
		if ( fileList == null)
			return false ;
		for (int iCount = 0; iCount < fileList.length; iCount++) {
			if (fileList[iCount] != null) {
				long lModifyDate = fileList[iCount].lastModified();
				if (lModifyDate + lDaySpan <= lNowDate) {
					if (!fileList[iCount].delete()) {
						continue;
					}
				}
			}
		}
		PubTools.log.info("ɾ���ļ�·��:["+filePath+"] ʱ��Ϊ[" +timeSpan + "]����ˮ��Ϣ�ɹ�") ;
		return true;
	}
	
	/**
	 * �ϲ��ļ�
	 * 
	 * @param outFile
	 *            �ϲ�����ļ���
	 * @param fileNameList
	 *            ��Ҫ�ϲ����ļ����б�
	 * @return 
	 * 			����д��򷵻ش�����Ϣ�б�
	 * @throws IOException
	 *             �ļ������쳣
	 */
	public static long MergeFile (String outFile, List fileNameList)
			throws Exception {
		int iIndex = 0;
		int MAX_READ_BLOCK = 10240;
		byte[] readBytes = new byte[MAX_READ_BLOCK];
		int readLen = 0;
		iIndex = outFile.lastIndexOf(".");
		String strUnZipFolder = outFile.substring(0,iIndex) ;
		File unZipFolder = new File(strUnZipFolder);
		File[] unZipFileList = unZipFolder.listFiles();

		String tempFileName = outFile.substring(0, outFile.lastIndexOf("."));
		tempFileName += ".ej";
		File tempOutFile = new File(tempFileName);

		// �����ļ������
		RandomAccessFile outRandomFile = new RandomAccessFile(tempOutFile, "rw");
		for (iIndex = 0; iIndex < fileNameList.size(); iIndex++) {
			RandomAccessFile inFile = new RandomAccessFile(
					unZipFileList[iIndex], "r");
			while ((readLen = inFile.read(readBytes, 0, MAX_READ_BLOCK)) != -1) {
				outRandomFile.write(readBytes, 0, readLen);
				readBytes = new byte[MAX_READ_BLOCK];
			}
			inFile.close();
			unZipFileList[iIndex].delete();
		}
		outRandomFile.close();
		outRandomFile = null;
		unZipFolder.delete();
		// �Ѻϲ�����ļ�����ѹ��
		//Zip zip = new Zip();
		ZipTool.zipFile(tempFileName, outFile);
		// ɾ���ϲ����ļ�
		tempOutFile.delete();
		return new File(outFile).length();
	}



	/**
	 * ���ļ��б���ļ�ѹ����ָ����Ŀ¼��ȥ
	 * 
	 * @param outFile
	 *            Ŀ¼�ļ�(��ѹĿ¼����Ŀ¼�ļ�����) �� c:\\test\\temp\\20090909.ej
	 * @param fileNameList
	 *            ��Ҫ��ѹ���ļ��б�
	 * @return ���ش�Ž�ѹ�ļ��Ľ�ѹĿ¼ �� Ŀ¼�ļ���Ϊ c:\\test\\temp\\20090909.ej ���ѹĿ¼Ϊ
	 *         c:\\test\\temp\\20090909\\
	 */
	public static String UnZipFileToFolder(String outFile,String fileName)  throws Exception{
		// ����Ŀ���ļ�����Ŀ¼
		File workingFile = null;
		String unZipfolderName = "";
		String tempFileName = "" ;
		int index = outFile.lastIndexOf(File.separatorChar);
		if (index >= 0) {
			workingFile = new File(outFile.substring(0, index));
			tempFileName = outFile.substring(index + 1, outFile.length());
			if (!workingFile.exists()) {
				if (!workingFile.mkdirs()) {
					return null;
				}
			}
		}
		workingFile = new File(outFile);
		index = tempFileName.lastIndexOf(".");
		if (index >= 0) {
			tempFileName = tempFileName.substring(0, index);
		}
		// ��ȡ��ʱ��ѹĿ¼��������Ŀ¼
		unZipfolderName = JournalServerParams.JournalServerTempFileDirectory
				+ File.separatorChar + tempFileName;
		File unZipfolderPath = new File(unZipfolderName);
		if (!unZipfolderPath.exists()) {
			if (!unZipfolderPath.mkdirs()) {
				return null;
			}
		}

		//UnZip unZip = new UnZip();
		if ( ZipTool.unzipFile(fileName, unZipfolderName) == null)
			throw new IOException();
		
		return unZipfolderName;
	}

	/**
	 * ����ļ��Ƿ����
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return �ļ��Ƿ���� ����Ϊtrue ������Ϊfalse
	 * @throws IOException
	 */
	private boolean checkFileExist(String fileName) throws IOException {
		boolean fileIsExist = false;
		File file = new File(fileName);
		if ((file == null) || !file.isFile() || !file.exists()) {
			fileIsExist = false;
		} else {
			fileIsExist = true;
		}
		return fileIsExist;
	}
	
	/**
	 * 		�ж��ļ��Ƿ���Խ�ѹ
	 * 
	 * @param sFileName
	 * 						�ļ���
	 * @return
	 * 			true ���Խ�ѹ  false  ���ܽ�ѹ
	 */
	public boolean checkFileUnZipable(String sFileName){
		String sFolderName =  sFileName.substring(0,sFileName.lastIndexOf(File.separatorChar)) ;
		//UnZip unZip = new UnZip();
		String[] unZipFileName = ZipTool.unzipFile(sFileName, sFolderName) ;
		if (  unZipFileName == null){
			new File(sFileName).delete();
			return false ;
		}
		
		for(int iIndex = 0 ; iIndex < unZipFileName.length ; iIndex ++){
//			System.out.println(sFolderName + File.separatorChar + unZipFileName[iIndex]) ;
			new File(sFolderName + File.separatorChar + unZipFileName[iIndex]).delete() ;
		}
		return true ;
	}

	/**
	 * �����ļ��б�
	 * 
	 * @param termCodeList
	 *            �ն��б�
	 * @param fileTimeRange
	 *            ʱ�䷶Χ
	 * @return
	 */
	public boolean CheckTerminalFileList(String sJournalList) {
		int iIndex = 0;
		try {
			
			PubTools.log.info("������ˮ�б� : " + sJournalList) ;			
			String[] sArrTermList = sJournalList.split("\\#");
			for (iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
				String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
				String sTermCode = sArrTermInfo[0] ;
				String sFileTime = sArrTermInfo[1] ;
				// �ļ�·�� ��Ŀ¼\\�ն˺�\\��\\��\\�ն˺�_������.zip
				String fileName = getFileName(sTermCode, sFileTime);
				// ����ļ��Ƿ���ڣ�������ڣ������ڳɹ����ļ��б��У����ʧ�ܣ�������ʧ���ļ��б���
				if (!checkFileExist(fileName)) {
					PubTools.log.info("�ļ�������:"+fileName);
					this.errorFileList += sTermCode + "$" + sFileTime + "#";
				} else {
					if (!checkFileUnZipable(fileName)){
						PubTools.log.info("�ļ�����ѹ��:"+fileName);
						this.errorFileList += sTermCode + "$" + sFileTime + "#";
					}else{
						PubTools.log.info("�ļ�����:"+fileName);
					}
				}
			}		
			if (this.errorFileList.length() > 0){
				PubTools.log.debug("errorFileList :[" +this.errorFileList + "]" );
				return false;
			}
		} catch (IOException ex) {
			PubTools.log.error("Catch Exception:" + ex.toString());
			return false;
		}
		return true;
	}
	
	/**
	 * �����ļ��б�
	 * 
	 * @param sTermCode
	 *            �ն˺�
	 * @param sFileTime
	 *            �豸ʱ��
	 * @return
	 */
	public boolean CheckTerminalFile(String sTermCode, String sFileTime) {
		try {
			PubTools.log.debug("������ˮ�豸�� : " + sTermCode);
			PubTools.log.debug("������ˮʱ�� : " + sFileTime);
			// �ļ�·�� ��Ŀ¼\\�ն˺�\\��\\��\\�ն˺�_������.zip
			this.tempFileName = getFileName(sTermCode, sFileTime);
			// ����ļ��Ƿ���ڣ�������ڣ������ڳɹ����ļ��б��У����ʧ�ܣ�������ʧ���ļ��б���
			if (!checkFileExist(this.tempFileName)) {
				PubTools.log.info("�ļ�������:" + this.tempFileName);
				return false;
			}
			return true;
		} catch (IOException ex) {
			PubTools.log.error("Catch Exception:" + ex.toString());
			return false;
		}
	}

	/**
	 * �����ն˺ż�������ˮʱ���������������Ӧ���ļ���
	 * 
	 * @param termCode
	 *            �ն˺�
	 * @param fileTime
	 *            ������ˮʱ��
	 * @return �ն˺ż�ʱ���ڷ������϶�Ӧ���ļ��ľ���·��
	 * 
	 */
	public String getFileName(String termCode, String fileTime) {
		// �ļ�·�� ��Ŀ¼\\��\\��\\��\\�ն˺�_������_log
		try {
			//String tempFilePath =  this.serverFilePath + File.separatorChar + termCode + File.separatorChar + 
			//fileTime.substring(0, 4) + File.separatorChar + fileTime.substring(4, 6) + File.separatorChar + termCode + "_" + fileTime + ".zip";
//			String tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//			+ fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime + ".zip";
			
			//20111221 xq �޸� ����C���ļ��ϴ�VH�� VH���·��  ֻ�����޸�VH
			String tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime +".zip";

			return tempFilePath ;
		} catch (IndexOutOfBoundsException ex) {
			PubTools.log.error("Catch IndexOutOfBoundsException" + ex);
			return null;
		}
	}

	/** 20110509 xq ��Ӹ����ļ����ͻ�ȡ�����ļ��� ����tc�ļ�����
	 * �����ն˺ż�������ˮʱ���������������Ӧ���ļ��� 
	 * 
	 * @param termCode
	 *            �ն˺�
	 * @param fileTime
	 *            ������ˮʱ��
	 * @return �ն˺ż�ʱ���ڷ������϶�Ӧ���ļ��ľ���·��
	 * 
	 */
	public String getFileName(String termCode, String fileTime,String sAtmFileName) {
		// �ļ�·�� ��Ŀ¼\\��\\��\\��\\�ն˺�_������ ����ˮ�ļ���
		// �ļ�·�� ��Ŀ¼\\��\\��\\��\\�ն˺�_������_tc.zip ��tc�ļ��ļ���
		try {
			//String tempFilePath =  this.serverFilePath + File.separatorChar + termCode + File.separatorChar + 
			//fileTime.substring(0, 4) + File.separatorChar + fileTime.substring(4, 6) + File.separatorChar + termCode + "_" + fileTime + ".zip";
			String tempFilePath = "";
			if(sAtmFileName.indexOf("tc")>0||sAtmFileName.indexOf("TC")>0){
//				tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//			   	                + fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime + "_tc"+".zip";
				//20111221 xq �޸� ����C���ļ��ϴ�VH�� VH���·��  ֻ�����޸�VH
				tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime + "_tc"+".zip";
			}else{
//				tempFilePath =  this.serverFilePath + File.separatorChar + 	fileTime.substring(0, 4) + File.separatorChar 
//	                            + fileTime.substring(4, 6) + File.separatorChar + fileTime.substring(6, 8) + File.separatorChar + termCode + "_" + fileTime +".zip";
				
				//20111221 xq �޸� ����C���ļ��ϴ�VH�� VH���·��  ֻ�����޸�VH
				tempFilePath =  this.serverFilePath + File.separatorChar + getRealFilePath(termCode,fileTime)+ File.separatorChar + termCode + "_" + fileTime +".zip";
			}
			return tempFilePath ;
		} catch (IndexOutOfBoundsException ex) {
			PubTools.log.error("Catch IndexOutOfBoundsException" + ex);
			return null;
		}catch(Exception e){
			PubTools.log.error("��ȡ������ˮ�ļ����·���쳣:" + e);
			return null;
		}
	}
	
	
	/**2011-12-21 �������
	 * �����޸���ˮ���Ŀ¼�ṹ  �����ļ��нṹΪ
	 * @param sDevCode �豸�� sFindDate ������ˮ����
	 * @return �ļ�·��
	 */
	public String getRealFilePath(String sDevCode,String sFindDate){
		String sPath = "";
		//sqlMapper = Dboperator.getSqlMapper();
		String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
		List mBranchList = new ArrayList();
		try {
			String sBranchCode = "";
			//mBranchList = sqlMapper.queryForList("select", sQuerySQL);
			mBranchList = JdbcFactory.queryForList(sQuerySQL);
			if (mBranchList != null && mBranchList.size() > 0) {
				Object[] resultArray = mBranchList.toArray();
				sBranchCode = ((HashMap) resultArray[0]).get("dev_branch1")==null?"":((HashMap) resultArray[0]).get("dev_branch1").toString().trim();	
			}else{
				return "";
			}
			
			if(sFindDate.length()!=8){
				return "";
			}
			String sYear = sFindDate.substring(0, 4);
			String sMonth = sFindDate.substring(4, 6);
			String sDay = sFindDate.substring(6, 8);
			sPath = sBranchCode+File.separator+sYear+File.separator+sMonth+File.separator+sDay;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sPath;
	}
	
	/**
	 * ��ȡ��ʱ�ļ��� ��Ҫ�����ļ��������ϲ��ļ�ʹ���ļ���
	 * 
	 * @return
	 */
	public String genTempFilePath(String sTempFilePath) {

		String tempFileName = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		while (true) {
			tempFileName = "";
			tempFileName += sTempFilePath;
			tempFileName += File.separatorChar + df.format(Calendar.getInstance().getTime());

			try {
				if (new File(tempFileName).exists()) {
					Thread.sleep(10);
				} else
					break;
			}catch (InterruptedException ex) {
				PubTools.log.error("Thread Sleep Catch Exception :"
						+ ex.toString());
				return null;
			}
		}

		return tempFileName;
	}

	private String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
	
	public static void main(String []args){
		ServiceParameter.init(); //��ʼ�������ļ�·��
		LogManager.resetConfiguration();
		PropertyConfigurator.configure(ServiceParameter.log4jConfigFile);
		FileTools fileTools = new FileTools();
		fileTools.checkFileUnZipable("d:\\Log.zip");
	}


}
