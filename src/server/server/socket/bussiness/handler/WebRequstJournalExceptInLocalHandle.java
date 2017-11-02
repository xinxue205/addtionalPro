/**
 * 
 */
package server.server.socket.bussiness.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.server.socket.JournalServerParams;
import server.server.socket.tool.FileTools;
import server.util.Dboperator;
import server.util.PubTools;
import server.util.ZipTool;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:44:25
 * @Description
 * @version 1.0 Shawn create
 */
public class WebRequstJournalExceptInLocalHandle {
	private List lExistFileAddList = new ArrayList();//�������ļ����Ѿ����ڵ��ļ�·��
	private List lUnExistFileAddList = new ArrayList();//�������ļ��в����ڵ��ļ��б�
	private SqlMapClient sqlMapper = null; // ���ݿ����
	private FileTools fileTools = new FileTools();
	private String sGetFileMethode = "";//20120917 xq add ��ȡ��ʽ
	private String sDevBranch1Code = "";//20130704 ���ڱ����������Ϣ
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//20120828 xq add ��ʽ��ʱ��


	
	/**
	 * ����������͹���������ȡ��ˮ���豸�Լ����ڹ���  �����ˮ�������еĻ� ����Ҫ������ȥ��ȡ��ˮ
	 * @param sJournalList ���������������ˮ�б�   �豸��@ʱ��#
	 * @return
	 */
	public String removeExistInVHFile(String sJournalList){
		PubTools.log.info("��ǰ��ʼ�ж���ˮ�ļ��Ƿ���VH�Ѿ����ڣ���ǰ�����ļ��б���Ϣ["+sJournalList+"]");
		String sRemovedJournalList = "";
		List lJournalList = this.getJournalList(sJournalList);
		if(lJournalList==null||lJournalList.size()==0){
			return "";
		}
//		String sCurrDate = PubTools.dateToFormatString(new Date());
		
		for(int iIndex=0;iIndex<lJournalList.size();iIndex++){
			Map mDevInfoMap = new HashMap();
			mDevInfoMap = (Map)lJournalList.get(iIndex);
			String sDevCode = mDevInfoMap.get("termCode")==null?"":mDevInfoMap.get("termCode").toString();
			String sFindDate = mDevInfoMap.get("fileTime")==null?"":mDevInfoMap.get("fileTime").toString();
//			if(sFindDate.equals(sCurrDate)){
//				sRemovedJournalList = sRemovedJournalList + sDevCode+"@"+sFindDate+"#";
//			}else 
			if(!findFileIsExist(sDevCode,sFindDate)){
				sRemovedJournalList = sRemovedJournalList + sDevCode+"@"+sFindDate+"#";
			}
		}
		return sRemovedJournalList;
	}
	
	// �����豸����б�
	public List getJournalList(String sJournalList) {
		List alTermList = new ArrayList();
		alTermList.clear() ;
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			HashMap hmTmpTermList = new HashMap();
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			hmTmpTermList.put("termCode", sArrTermInfo[0]);
			hmTmpTermList.put("fileTime", sArrTermInfo[1]);
			alTermList.add(hmTmpTermList);
		}
		return alTermList;
	}
	
	/**
	 * �����豸��źͲ���ʱ���ж� �ļ��Ƿ����
	 * @param sDevCode
	 * @param sFindDate
	 * @return
	 */
	public boolean findFileIsExist(String sDevCode,String sFindDate){
		boolean isExist = false;
		if(sDevCode==null||sDevCode.equals("")){
			return false;
		}
		if(sFindDate==null||sFindDate.equals("")||sFindDate.length()!=8){
			return false;
		}
		sDevBranch1Code = this.getDevBranch1(sDevCode);  //20130704 xq update �������޸�Ϊͳһ��ȡ
		String sFileName = sDevCode+"_"+sFindDate+".zip";
		String sPath = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sDevCode, sFindDate,sDevBranch1Code)+File.separator+sFileName;
		File file = new File(sPath);
		
		/**
		 * 20120319 xq ���� ������ݿ��¼û�л��߸������ϴ����ɹ�!������V����ȡ
		 */
		if(!checkJournalLogIsExist(sDevCode,sFindDate)){
			lUnExistFileAddList.add(sPath);
			return false;
		}
		
		if(file.exists()&&file.isFile()){
			lExistFileAddList.add(sPath);
			isExist = true;
		}else{
			lUnExistFileAddList.add(sPath);
		}
		return isExist;
	}
	
	
	/**
	 * 20120319 xq �жϵ�����ˮ�ϴ���־�����Ƿ��и��ļ��ϴ��ļ�¼
	 * @return true ���� false ������
	 */
	public boolean checkJournalLogIsExist(String sDevCode,String sFileTime){
		PubTools.log.debug("������ݿ��Ƿ����!");
		//---20120319 xq �޸Ĵ�������------
		String sCurrDate =   PubTools.getSystemDate();
		if(sCurrDate.equals(sFileTime)){
			return false;
		}
		
		//20120917 xq add ����ֱ����ATMC��ȡ�ļ�����
		if(this.getSGetFileMethode()==null||"".equals(this.getSGetFileMethode())||"1".equals(this.getSGetFileMethode().trim())){
			return false;
		}
		//------end----------------
		
		String sQuerySQL = "select atmfilename from journal_uploadlog where devcode='"+sDevCode+"' and filetime='"+sFileTime+"' and transresult='0' and atmfilename like '%.J'";
		List lResultList = new ArrayList();
		PubTools.log.debug("��ѯ��ǰ��ȡ�ļ��Ƿ��ϴ��ɹ�SQLΪ:"+sQuerySQL);
		try {
			lResultList = sqlMapper.queryForList("select", sQuerySQL);
			if (lResultList != null && lResultList.size() > 0) {
				PubTools.log.debug("��ѯ���Ϊtrue");
				return true;
			}else{
				PubTools.log.debug("��ѯ���Ϊfalse");
				return false;
			}
		}catch(Exception e){
			PubTools.log.error("��ȡ�ϴ��ļ��Ƿ�����쳣:"+e.toString());
			return false;
		}
	}
	
	
	//���ļ�ָ���ļ����µ��ļ� ��������ʱ�ļ�����
	/**
	 * 20130704 xq �޸� ����ѹ����ļ�����·����֮ǰ��ֱ�ӷ���tempFile�ļ����£����Ϸ���tmepFile/�������ļ�����
	 * @return
	 */
	public String sCopyFileToTemp(){
//		System.out.println("sCopyFileToTemp:"+this.lExistFileAddList.size()+"\t-------:"+this.lUnExistFileAddList.size());
		List lExistFileList = this.lExistFileAddList;
		List lUnExitFileList = this.lUnExistFileAddList;
//		JournalServerParams.JournalViewerTempFilePath
		String sSystemFilePath = JournalServerParams.JournalViewerTempFilePath+File.separator+"tempFile"+File.separator+sDevBranch1Code;
		String sTempPath = fileTools.genTempFilePath(sSystemFilePath);
		try{
			File sTempFileDir = new File(sTempPath);
			if(!sTempFileDir.exists()){
				sTempFileDir.mkdirs();
			}
		}catch(Exception e){
			PubTools.log.error("������ʱ�ļ����쳣��"+e);
		}
		if(lExistFileList!=null&&lExistFileList.size()!=0){
			for(int iIndex=0;iIndex<lExistFileList.size();iIndex++){
				try{
					
					String sFile = (String)lExistFileList.get(iIndex);
					PubTools.log.info("====================----===="+sFile);
					String sFileName = (sFile.substring(sFile.lastIndexOf(File.separator) + 1,sFile.length()));
					String sTempName = sFileName.substring(0,sFileName.lastIndexOf("."));
					
					upZipSuccess(sFile,sTempPath,sTempName);
					//-----------------20110829 xq ����ļ��ǵ���Ļ�������ȡ֮��ɾ�����ļ�------//
//					deleteCurrJournalFile(sFile);
					//-------------------end----------------------------------------------//
				}catch(Exception e){
					e.printStackTrace();
					PubTools.log.error("��ѹ�ļ��쳣:"+e);
				}
			}
		}
		PubTools.log.debug("lUnExitFileList.size()++++++++++++++"+lUnExitFileList.size());
		if(lUnExitFileList!=null&&lUnExitFileList.size()!=0){
			for(int iIndex=0;iIndex<lUnExitFileList.size();iIndex++){
				try{
					String sFile = (String)lUnExitFileList.get(iIndex);
					PubTools.log.debug("====================----====++++++++"+sFile);
					String sFileName = (sFile.substring(sFile.lastIndexOf(File.separator) + 1,sFile.length()));
					String sTempName = sFileName.substring(0,sFileName.lastIndexOf("."));
					upZipSuccess(sFile,sTempPath,sTempName);
					
					//-----------------20110829 xq ����ļ��ǵ���Ļ�������ȡ֮��ɾ�����ļ�------//
//					deleteCurrJournalFile(sFile);
					//-------------------end----------------------------------------------//
				}catch(Exception e){
//					e.printStackTrace();
					PubTools.log.error("��ѹ�ļ��쳣:",e);
				}
			}
		}
		if(this.lExistFileAddList!=null){
			lExistFileAddList = null;
		}
		if(this.lUnExistFileAddList!=null){
			lUnExitFileList = null;
		}
		return sTempPath;
	}
	
	
	/**
	 * 20110829 xq ����ļ���ȡΪ����Ļ�������ȡ���ļ��󽫸��ļ�ɾ��
	 * @param sFileName
	 */
	public void deleteCurrJournalFile(String sFileName){
		String sCurrDate = PubTools.getSystemDate();
		if(sFileName.indexOf(sCurrDate)>0){
			File sCurrFile = new File(sFileName);
			if(sCurrFile.exists()){
				sCurrFile.delete();
			}
		}
	}
	
	
	//��ѹjar���������� 
	public void upZipSuccess(String sFile,String sDirector,String sTempFileName){
	  File file = new File(sFile);
	  if(!file.exists()||!file.isFile()){
		  return ;
	  }
	    try{
			String[] unZipFileName = ZipTool.unzipFile(sFile, sDirector) ;  //��ѹ�ļ�����ʱĿ¼
			String tempFileName =  sDirector	+ File.separatorChar + sTempFileName + ".j"; 
			new File(sDirector + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName)); //��������ѹ�������ļ�
	    }catch(Exception e){
	    	PubTools.log.error("��ѹ�ļ��쳣:"+e.toString());
	    	file.delete();
	    }
	}
	
	
	/**2011-04-15 �������
	 * �����޸���ˮ���Ŀ¼�ṹ  �����ļ��нṹΪ
	 * 20130704 xq �޸� �������Ż�ȡ�޸�Ϊͳһ����
	 * @param sDevCode �豸�� sFindDate ������ˮ����
	 * @return �ļ�·��
	 */
	public String getRealFilePath(String sDevCode,String sFindDate,String sBranchCode){
		String sPath = "";
		try{
			if(sFindDate.length()!=8){
				return "";
			}
			String sYear = sFindDate.substring(0, 4);
			String sMonth = sFindDate.substring(4, 6);
			String sDay = sFindDate.substring(6, 8);
			sPath = sBranchCode+File.separator+sYear+File.separator+sMonth+File.separator+sDay;
		} catch (Exception e) {
			PubTools.log.error("��ȡ��ˮ�����ļ����쳣:",e);
			return "";
		}
		return sPath;
	}

	/**
	 * 20130704 xq �����豸�Ż�ȡ�豸һ�������� ��Ҫ���ڽ��ļ���������ʱ�ļ���������ʱ�ļ�����һ����������
	 * @param sDevCode �豸��
	 * @return һ��������
	 */
    public String getDevBranch1(String sDevCode){
    	String sBranchCode = "";
    	if("".equals(sDevBranch1Code)){
	    	sqlMapper = Dboperator.getSqlMapper();
	    	
			String sQuerySQL = "select dev_branch1 from dev_bmsg where dev_code='"+sDevCode+"'";
			List mBranchList = new ArrayList();
			try {
				
				mBranchList = sqlMapper.queryForList("select", sQuerySQL);
				if (mBranchList != null && mBranchList.size() > 0) {
					Object[] resultArray = mBranchList.toArray();
					sBranchCode = ((HashMap) resultArray[0]).get("dev_branch1")==null?"":((HashMap) resultArray[0]).get("dev_branch1").toString().trim();	
				}
			    sDevBranch1Code = sBranchCode;
			}catch (Exception e) {
				PubTools.log.error("��ȡ�豸["+sDevCode+"]�쳣",e);
			}
    	}else{
    		sBranchCode = sDevBranch1Code;
    	}
		return sBranchCode;
    }

	/**
	 * * ���Ƶ����ļ�
	 * 
	 * @param String
	 *            sSourceFileName ԭ�ļ�·�� �磺c:/a.txt
	 * @param String
	 *            sTargetDirectory ���ƺ��ŵ�Ŀ¼ �磺f:/dir
	 * @return ���Ƴɹ�����T
	 */
	public static boolean copyFile(String sSourceFileName, String sTargetDirectory) {
		boolean bResult = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File fSourceFile = new File(sSourceFileName);
			if (fSourceFile.exists()) { // �ļ�����ʱ
				String sFileName = fSourceFile.getName();// Դ�ļ�����������·����
				String sNewFileName = sTargetDirectory + File.separatorChar + sFileName;// Ŀ���ļ���������·����
				InputStream inStream = new FileInputStream(sSourceFileName); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(sNewFileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				bResult = true;
			}
		} catch (Exception e) {
//			PubTools.log.error("���Ƶ����ļ������쳣:" + e.toString());
		}
		return bResult;
	}
	
	
	/**20120829 xq add ���Ƶ����ļ�������һ���ļ�
	 * * ���Ƶ����ļ�
	 * 
	 * @param String
	 *            sSourceFileName ԭ�ļ�·�� �磺c:/a.txt
	 * @param String
	 *            sTargetDirectory ���ƺ��ŵ�Ŀ¼ �磺f:/dir
	 *       
	 * @param sTargetFileName Ŀ���ļ���
	 * @return ���Ƴɹ�����T
	 */
	public static boolean copyFile(String sSourceFileName, String sTargetDirectory,String sTargetFileName) {
		boolean bResult = false;
		try {
			int bytesum = 0;
			int byteread = 0;
			File fSourceFile = new File(sSourceFileName);
			if (fSourceFile.exists()) { // �ļ�����ʱ
				String sFileName = fSourceFile.getName();// Դ�ļ�����������·����
				String sNewFileName = sTargetDirectory + File.separatorChar + sTargetFileName;// Ŀ���ļ���������·����
				InputStream inStream = new FileInputStream(sSourceFileName); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(sNewFileName);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				bResult = true;
			}
		} catch (Exception e) {
//			PubTools.log.error("���Ƶ����ļ������쳣:" + e.toString());
		}
		return bResult;
	}
	
	/**
	 * 20120828 xq add ����û�ѡ�����ֱ����ȡATMC�ļ� ����ȡ֮ǰ�����ȡ�Ĳ��ǵ�ǰ����ļ����豸�ݸ��ļ�
	 * @param sJournalList�ļ��б�
	 */
	public void copyFileToTmp(String sJournalList){
		PubTools.log.info("��ǰ��ȡ����ֱ�Ӵ�ATMC��ȡ���豸����ȡ��������ˮ�ļ�!");
		String[] sArrTermList = sJournalList.split("\\#");
		for (int iIndex = 0; iIndex < sArrTermList.length; iIndex++) {
			String[] sArrTermInfo = sArrTermList[iIndex].split("\\@", -2);
			String sDevCode = sArrTermInfo[0];
			String sFileTime =  sArrTermInfo[1];
			if(!sFileTime.equals(PubTools.getSystemDate())){
				copyFileToTmpFile(sDevCode,sFileTime);
			}
		}
		PubTools.log.info("��������ˮ�ļ��������!");
	}
	
	/**
	 * 20120828 xq add ���ݶ�Ӧ�豸����ˮ�ļ�
	 * @param sDevCode �豸��
	 * @param sFindDate ����
	 */
	public void copyFileToTmpFile(String sDevCode,String sFindDate){
		PubTools.log.info("��ʼ�����豸��["+sDevCode+"]��Ӧ����["+sFindDate+"]����ˮ�ļ�!");
		try{
			String sFileName = sDevCode+"_"+sFindDate+".zip";
			sDevBranch1Code = this.getDevBranch1(sDevCode);  //20130704 xq update �������޸�Ϊͳһ��ȡ
			String sPathDir = JournalServerParams.JournalViewerTempFilePath+File.separator+this.getRealFilePath(sDevCode, sFindDate,sDevBranch1Code);
			String sPath = sPathDir +File.separator+sFileName;
			File file = new File(sPath);
			if(!file.exists()){
				PubTools.log.info("�追���ļ�["+sPath+"]������!ֱ����ȡ!");
				return ;
			}
	        String sNewFileName = sDevCode+"_"+sFindDate+"_"+simpleDateFormat.format(new Date())+".zip";
	        PubTools.log.info("��ǰԴ�ļ�["+sPath+"]�����ļ�["+sPathDir+File.separator+sNewFileName+"]");
	        copyFile(sPath,sPathDir,sNewFileName);
       }catch(Exception e){
    	   PubTools.log.error("�ļ������쳣",e);
       }
       PubTools.log.info("�豸��["+sDevCode+"]��Ӧ����["+sFindDate+"]����ˮ�ļ��������!");
	}
	


	public static void main(String[] args){
		WebRequstJournalExceptInLocalHandle web = new WebRequstJournalExceptInLocalHandle();
		web.checkJournalLogIsExist("440600300157", "20120319");
//		web.upZipSuccess("C:/Documents and Settings/Administrator/����/310704200221_20120228.zip", "D:", "testFile");
//		File f = new File("C:/Documents and Settings/Administrator/����/330001362057_20120224.zip");
//		System.out.println(f.length());
//		 new UnZip().unzipFile("C:/Documents and Settings/Administrator/����/330001362057_20120224.zip", "D:");
		 
//		 String sFile = "C:/Documents and Settings/Administrator/����/330001362057_20120224.zip";
//		 String sDirector = "D:";
//		 String sTempFileName = "testFile";
//		 File file = new File(sFile);
//		  if(!file.exists()||!file.isFile()){
//			  return ;
//		  }
//	    
//		String[] unZipFileName = new UnZip().unzipFile(sFile, sDirector) ;  //��ѹ�ļ�����ʱĿ¼
//		String tempFileName =  sDirector	+ File.separatorChar + sTempFileName + ".j"; 
//		new File(sDirector + File.separatorChar + unZipFileName[0]).renameTo(new File(tempFileName)); //��������ѹ�������ļ�

	}

	public String getSGetFileMethode() {
		return sGetFileMethode;
	}

	public void setSGetFileMethode(String getFileMethode) {
		sGetFileMethode = getFileMethode;
	}



}
