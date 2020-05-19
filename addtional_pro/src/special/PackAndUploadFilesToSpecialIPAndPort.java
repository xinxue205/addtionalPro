package special;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ecc.emp.action.SetTimeAction;
import com.ecc.emp.core.Context;
import com.ecc.emp.core.EMPException;
import com.ecc.emp.jdbc.JDBCAction;
import com.ecc.liana.base.LianaConstants;
import com.ecc.liana.base.LianaStandard;
import com.ecc.liana.base.Trace;
import com.ecc.liana.exception.TranFailException;
import com.ecc.liana.file.LianaFile;
import com.ecc.liana.format.LianaFormat;


/**
 * ���ܸ�����ѹ�����ϴ�ָ��·���µ��ļ���ָ��Ip������ָ���˿�
 * ImageZipUpload.java<br>
 * EMP ���ײ�����չ<br>
 * Extends class JDBCAction<br>
 * 
 * ��������-Ӱ�������ϴ�
 * 
 * @emp:name 
 * @emp:states 
 * @emp:document
 * @author ��ϸ��
 */
public class ImageZipUpload extends JDBCAction {
	private String dataSourceName;
	
	/**
	 *ɨ������ 
	 *DATASTYLE 01������,02��ɨ����03��ɨ��
	 * 
	 */
	private String dataTypeField;
	
	/**
	 *���κ�
	 *BATCHCODE
	 */
	private String batchCodeField;
	
	/**
	 *���η���
	 *BATCHNUM �����η���ʱ����Ϊ��
	 */
	private String bathNumField;
	
	/**
	 *������
	 *BANKCODE
	 */
	private String bankCodeField;
	
	/**
	 *����ʱ��
	 *GROUPTIME
	 */	
	private String groupTimeField;
	
	/**
	 *�������ӡ����
	 *BARCODEDATE
	 **/
	private String barcodeDateField;
	
	
	/**
	 *�ļ����� zip/rar
	 *���� ������ �� tableCode pkey
	 **/
	private String selectop;
	
	/**
	 *�������κŲ�ѯ���������
	 *���� ������ �� tableCode
	 **/
	//private static String query1 = "select distinct MARKETINGID,MARKETINGID,MARKETINGID from AD_CB_CASTLINK where adaid = ? with ur";
	//��ʽ
	private static String query1 = "select CMG_DID,CMG_TABLECODE,CMG_APPLYNO from CL_CREDITCARD_MESSAGE where CMG_BATCHID = ? with ur";
	
	/**
	 *�����������ѯӰ����Ϣ���
	 *����query1�����ͨ������������ѯӰ����Ϣ
	 *����ͼƬ��Ե�ַ��ͼƬ������
	 **/
	private static String query2 = "select fls_path,1 from CL_FILELIST where FLS_APPLYNO =(select cap_applyunion from CL_CREDITCARD_APPLY where cap_applyno=? fetch first 1 row only) with ur";
	
	
	/**
	 * ��ͼƬ���ƣ�
	 **/
	private static String emptyImageName = "1.jpg"; 
	
	
	/**
	 * �ļ�����
	 * 
	 **/
	private String encoding="GBK";

	/* ҵ���߼�������Ԫ��ִ����� */
	public String execute(Context context) throws EMPException {
		String batchCode = null;//���κ�
		if(batchCodeField != null && !batchCodeField.equals("")){
			batchCode = (String)context.get(batchCodeField);
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "���κ�Ϊ�գ�ͨѶ����" );
		}
		
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String fileNamePath = null;//ѹ���ļ�·�������ơ�
		ZipOutputStream out = null;//ѹ���ļ�ʹ����
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<COSP>");
		//��ȡ<Head>����
		try {
			getHead(context,sb);
		} catch (Exception e) {
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, e );
		}
		try{
			//�ļ�����:���κ�.zip
			//fileNamePath = "D:/fuck/xianxia/"+batchCode+"."+selectop;
			//��ʽ�õ�ַ:
			fileNamePath = LianaStandard.getSelfDefineSettingsValue( LianaConstants.SETTINGS_DOWNLOAD_ROOT )+batchCode+"."+selectop;
			out = new ZipOutputStream(new FileOutputStream(fileNamePath));
			sb.append("<BODY>");	
			dataSource=(DataSource) context.getService(dataSourceName);
			
			//connection = ConnectionUtil.getConnection();
			//��ʽ������
			connection = this.getConnection(context, dataSource);
			ps = connection.prepareStatement(query1);
			ps.setString(1, batchCode);
			Trace.logDebug( Trace.COMPONENT_JDBC, "��ѯ����--->" + query1 );
			Trace.logDebug( Trace.COMPONENT_JDBC, "�������--->" + batchCode );
			//��ѯ�������µ����������롣(ִ�й����У�����XML������)
			rs = ps.executeQuery();
			String barCode="";//������  
			String tigi_docType="";//��������
			String aidi_p_barCode="";//����ԭ�������,����Ҫ��д
			String tableCode = "";//������
			String formCode_x="-1";//ԭ�㶨λ������
			String formCode_y="-1";//ԭ�㶨λ������
			String cmg_applyno = "";//������
			//���������룬��ȡ����������Ӱ����Ϣ��(ִ�й����У�����XML������)
			while (rs.next()) {
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				barCode = rs.getString(1).substring(0,18);
				tigi_docType = batchCode.substring(8, 11);
				tableCode = rs.getString(2);
				cmg_applyno = rs.getString(3);
				//����OPERATION ͷ  
				sb.append("<OPERATION BARCODE=\"").append(barCode).append("\"  TIGI_DOCTYPE=\"")
					.append(tigi_docType).append("\" AIDI_P_BARCODE=\"").append(aidi_p_barCode).append("\" TABLECODE=\"")
					.append(tableCode).append("\" FORMCODE_X=\"").append(formCode_x).append("\" FORMCODE_Y=\"")
					.append(formCode_y).append("\">");
				List<String> name_directList = new ArrayList<String>();
				//2012��09��29 �ӿڱ�������ô��������ÿ�ͼƬ���档
				/*--------------���ѯ������ʼ---------*/
				//��ѯ�������µ�Ӱ�����ݣ�����Ӱ�����ݲ������ļ���
				//String query2 = "select b.adcontent,b.status from AD_CB_CASTLINK a left join AD_CB_ad b on a.adid=b.adid  where a.marketingid = ? with ur";
				/*ps2 = connection.prepareStatement(query2);
				//ps2.setString(1,barCode);
				ps2.setString(1,cmg_applyno);
				Trace.logDebug( Trace.COMPONENT_JDBC, "��ѯ����--->" + query2 );
				Trace.logDebug( Trace.COMPONENT_JDBC, "�������--->" + cmg_applyno );
				rs2 = ps2.executeQuery();
				while(rs2.next()){
					String fileName = rs2.getString(1);//*.jpg
					String fileDirect = rs2.getString(2);//������
					if(fileName == null || fileName.equals("") || fileName.lastIndexOf(".") == -1 ){//�ļ��������ڻ��߷��ļ�
						continue;
					}
					//fileName = "D:/affix/"+fileName;
					//��ʽ�õ�ַ
					fileName = LianaStandard.getSelfDefineSettingsValue("affixDir")+ fileName;
					
					if(fileDirect == null || fileDirect.equals("")){
						fileDirect = "1";
					}
					String name_direct = fileName+"|"+fileDirect;//*.jpg|0
					name_directList.add(name_direct);
				}
				rs2.close();
				ps2.close();*/
				/*--------------���ѯ��������---------*/
				//2012��09��29 �ӿڱ�������ô��������ÿ�ͼƬ���档
				/*--------------�����ѯ������ʼ---------*/
				//String fileName = "D:/affix/"+ emptyImageName;
				//��ʽ
				String fileName = LianaStandard.getSelfDefineSettingsValue("affixDir")+ emptyImageName;
				String fileDirect = "1";//����
				String name_direct = fileName+"|"+fileDirect;//*.jpg|0
				name_directList.add(name_direct);
				
				/*--------------�����ѯ��������---------*/

				//��ѹ������Ӱ���ļ�,������xml����
				ImageZipUpload.packFiles(sb,out,name_directList, barCode, false, encoding);
				name_directList = null;
				sb.append("</OPERATION>");
			}
			rs.close();
			ps.close();
			sb.append("</BODY></COSP>");
			//��xml����д����ʱ�ļ�,���ŵ�ѹ�����
			Trace.logDebug( Trace.COMPONENT_XML, "��������:" + sb.toString());
			//�ļ�����:���κ�.xml
			String xmlFileName = batchCode+".xml";
			ImageZipUpload.packFiles(sb,out,xmlFileName, false, encoding);
			sb=null;
		}catch(Exception e){
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE,e);
		}finally{
			try{
				if(rs != null){
					rs.close();
				}
				if(ps!=null)
					ps.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if (connection != null){
				this.releaseConnection(dataSource, connection);
			}
			if(out !=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//�������ӣ����ͱ��� SOCKET ͨѶ
		//fileNamePath ѹ���ļ�·��
		//IP
		//PORT
		//1-10λ�ֽ�Ϊ����ѹ���ļ������ֽ�����������30λ�ļ������ȣ����Ȳ���10λǰ��0��ո񲹳䣻
		//11-40λ�ֽ�Ϊ�������ɨ���ļ����ļ���������30λ�ļ������Կո��㣬�ļ������������κż���.rar�ļ���չ���������ļ���ʱʹ�á�GBK�������ʽ��
		//41λ��ʼΪ���ɨ���ļ������ݣ�
		try {
			sendBySocket(fileNamePath,encoding,context);
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_MESSAGE,"�ӿڷ����쳣",e);
			e.printStackTrace();
		}
		
		
		return "0";
	}
	
	/**
	 * ���ͱ���
	 * 1-10λ�ֽ�Ϊ����ѹ���ļ������ֽ�����������30λ�ļ������ȣ����Ȳ���10λǰ��0��ո񲹳䣻
	 * 11-30λ�ֽ�Ϊ�������ɨ���ļ����ļ���������30λ�ļ������Կո��㣬�ļ������������κż���.rar�ļ���չ���������ļ���ʱʹ�á�GBK�������ʽ��
	 * 31λ��ʼΪ���ɨ���ļ������ݣ�
	 * @param fileNamePath 
	 * @param encoding 
	 * @param context
	 * @throws IOException,TranFailException
	 */	
	private void sendBySocket(String fileNamePath, String encoding, Context context) throws IOException, TranFailException {
		//����Ӱ���ʼ�ϵͳIP
		//String IP = "10.2.26.241";
		//��ʽ
		String IP = LianaStandard.getSelfDefineSettingsValue("imageUploadIp");
		//����Ӱ���ʼ�ϵͳ�˿�
		//String port_str = "8602";
		//��ʽ
		String port_str = LianaStandard.getSelfDefineSettingsValue("imageUploadPort");
		
		int port = Integer.parseInt(port_str);
		//���ⷢ�͵�����
		byte[] content = getContent(fileNamePath, encoding);
		Socket socket =null;
		try{
			socket = new Socket(IP, port);//����socket����
		}catch(ConnectException e){
			Trace.logError(Trace.COMPONENT_MESSAGE, "socket�����쳣", e);
			e.printStackTrace();
		}
		
		OutputStream sout = socket.getOutputStream();
		InputStream sin = socket.getInputStream();
		/*int _length=0;
		//��ȡ���ֽ���
		for (int i = 0; i < bytesList.size(); i++) {
			_length+=bytesList.get(i).length;
			//sout.write(bytesList.get(i));
		}
		byte[] _total = new byte[_length];
		int i1 = 0;
		for (int j = 0; j< bytesList.size(); j++) {
			byte[] bbb = bytesList.get(j);
			for(int k = 0;k<bbb.length;k++){
				_total[i1]=bbb[k];
				i1++;
			}
		}
		sout.write(_total);*/
		sout.write(content);
		sout.flush();
		try {
			readDocument(sin, context);
		} finally {
			sout.close();
			sin.close();
			socket.close();
		}
	}
	
	/**
	 * ���ر��Ľ���
	 * @param inputXml 
	 * @param context 
	 * @throws DocumentException
	 */	
	private void readDocument(InputStream inputXml,Context context){
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(inputXml);
			Trace.logDebug(Trace.COMPONENT_XML, "��������:" + document.asXML());
		} catch (DocumentException e) {
			Trace.logError(Trace.COMPONENT_MESSAGE, "����xml����", e);
			e.printStackTrace();
		}
		String xpathStr = "//BODY";
		Node node = (Node) document.selectSingleNode(xpathStr);
		if(node != null){
			String DATASTYLE = "";//ɨ������
			String MAILCODE = "";//�ʰ���
			String BATCHCODE = "";//���κ�
			String MESSAGECODE = "";//������Ϣ����
			DATASTYLE = node.valueOf("DATASTYLE");
			MAILCODE = node.valueOf("MAILCODE");
			BATCHCODE = node.valueOf("BATCHCODE");
			MESSAGECODE = node.valueOf("MESSAGECODE");
			
			Trace.logDebug( Trace.COMPONENT_MESSAGE, "���ؽ����"+MESSAGECODE);
			
			if(MESSAGECODE.equals("0000")){
				MESSAGECODE="1";
			}else{
				MESSAGECODE="0";
			}
			context.put("DATASTYLE", DATASTYLE);
			context.put("MAILCODE", MAILCODE);
			context.put("BATCHCODE", BATCHCODE);
			context.put("MESSAGECODE", MESSAGECODE);
		
		}
	}
	
	/**
	 * ���ɷ��ͱ���
	 * @param fileNamePath 
	 * @param encoding 
	 * @throws IOException, TranFailException
	 */	
	private byte[] getContent(String fileNamePath,String encoding) throws IOException, TranFailException{
		//List<byte[]> bytesList = new ArrayList<byte[]>();
		int totalSize=0;
		int part1 = 10;//1-10λ
		int part2 = 30;//11-40λ
		FileInputStream in =null;//41�Ժ�
		int size = 0;//�ļ���С
		byte[] sizes = null;//�ļ�����
		byte[] names = new byte[part2];//�ļ���
		File f = new File(fileNamePath);
		if(!f.isDirectory()){
			in = new FileInputStream(f);
			//�ļ���С
			size = in.available();
			if(String.valueOf(size).length()>part1){
				throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "�ļ����ȳ����ӿ������ȣ����鲢�����ļ���С" );
			}
			String size_str=String.format("%010d", size);//����10λ��ǰ��0
			sizes = size_str.getBytes(encoding);
			//bytesList.add(sizes);
			totalSize = totalSize + part1;//��һ�α��ĳ���
		}
		String filename = f.getName();
		byte[] names_tmp=filename.getBytes(encoding);
		if(names_tmp.length<part2){//�ļ�����С��20λ���ں��油�ո�
			int length = part2-names_tmp.length;//���λ��
			for(int i = 0 ;i < names_tmp.length ; i++){//д����ڵ�����
				names[i]=names_tmp[i];
			}
			for(int j = 0 ; j < length ; j++){//��ȫ�ո�
				names[names_tmp.length+j]=new String(" ").getBytes(encoding)[0];
			}
		}else if(names_tmp.length > part2){
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "�ļ��������ӿ������ȣ���ȷ���ļ����Ƿ���Ч" );
		}else{
			names=names_tmp;
		}	
		//bytesList.add(names);
		totalSize = totalSize +part2;//�ڶ��α��ĳ���
		//���ļ�תΪ�ֽڡ�
		byte[] fileContent = new byte[size];
		in.read(fileContent);
		in.close();
		//bytesList.add(fileContent);
		totalSize = totalSize + size;//�����α��ĳ���
		byte[] content = new byte[totalSize]; 
		int index=0;//��־λ
		//�ϲ��ļ����Ȳ�����content
		for(int k=0;k < part1;k++){
			content[index]=sizes[k];
			index++;			
		}
		//�ϲ��ļ���������content
		for(int k=0;k < part2;k++){
			content[index]=names[k];
			index++;			
		}
		//�ϲ��ļ����ݲ�����content
		for(int k=0;k < size;k++){
			content[index]=fileContent[k];
			index++;			
		}
		return content;
	}
	
	/**
	 * ����XML�ļ�<HEAD>����
	 * @param context 
	 * @param sb 
	 * @throws Exception
	 */	
 	private void getHead(Context context,StringBuffer sb) throws Exception{
		sb.append("<HEAD>");
		String dataType = "01";//ɨ������:01������,02��ɨ����03��ɨ��
		if(dataTypeField != null && !dataTypeField.equals("")){
			dataType = (String)context.get(dataTypeField);
		}
		sb.append("<DATASTYLE>").append(dataType).append("</DATASTYLE>");
		sb.append("<MAILCODE></MAILCODE>");//�ʰ���,��Ӫ������д
		
		String batchCode = null;//���κ�
		if(batchCodeField != null && !batchCodeField.equals("")){
			batchCode = (String)context.get(batchCodeField);
			sb.append("<BATCHCODE>").append(batchCode).append("</BATCHCODE>");
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "���κ�Ϊ�ղ���ִ��" );
		}
		
		String bathNum = "";//���η���(�����η���ʱ��Ϊ��)
		if(bathNumField != null && !bathNumField.equals("")){
			bathNum = (String)context.get(bathNumField);
		}
		sb.append("<BATCHNUM>").append(bathNum).append("</BATCHNUM>");//�����η���ʱ��Ϊ��
		
		String bankCode=null;//������
		if(bankCodeField != null && !bankCodeField.equals("")){
			bankCode = (String)context.get(bankCodeField);
			sb.append("<BANKCODE>").append(bankCode).append("</BANKCODE>");
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "������Ϊ�ղ���ִ��" );
		}
		//Ӱ����״̬,Ĭ���0��
		sb.append("<IMGSTATUS>0</IMGSTATUS>");
		//����ʱ��
		String groupTime = "";
		if(groupTimeField != null && !groupTimeField.equals("")){
			groupTime = LianaFormat.formatDate((String)context.get(groupTimeField),"yyyyMMdd HH:mm:ss");
		}else{
			groupTime = new SetTimeAction().getCurrentTime("yyyyMMdd HH:mm:ss");
		}
		sb.append("<GROUPTIME>").append(groupTime).append("</GROUPTIME>");	
		//�������ӡ����
		String barCodeDate = "";
		if(barcodeDateField != null && !barcodeDateField.equals("")){
			barCodeDate = LianaFormat.formatDate((String)context.get(barcodeDateField),"yyyyMMdd HH:mm:ss");
		}else{
			barCodeDate = new SetTimeAction().getCurrentTime("yyyyMMdd HH:mm:ss");
		}
		sb.append("<BARCODEDATE>").append(barCodeDate).append("</BARCODEDATE>");	
		
		//ɨ�����ԱID
		sb.append("<SCANOPERID></SCANOPERID>");
		//��˾ID
		sb.append("<CORPID>04</CORPID>");
		//����ID
		sb.append("<CHANNELID>04</CHANNELID>");
		sb.append("</HEAD>");
	}
	
	/**
	 * ����ļ�
	 * @param sb 
	 * @param out 
	 * @param name_directList
	 * @param directoryPath
	 * @param isDlete
	 * @param encoding
	 * @throws IOException
	 */
	public static void packFiles(StringBuffer sb,ZipOutputStream out, List<String> name_directList,String directoryPath,boolean isDlete,String encoding) throws IOException{
		
		byte[] buffer = new byte[4096];
		int bufferLen = 4096;
		int bytesRead;
		if(encoding != null && !"".equals(encoding)){
			out.setEncoding(encoding);
		}
		for (int i = 0; i < name_directList.size(); i++) {
			//*.jpg|1
			String name_direct = name_directList.get(i);
			//*.jpg
			String tempFileName = name_direct.substring(0, name_direct.indexOf("|"));
			//1 ���� 0
			String direct = name_direct.substring(name_direct.indexOf("|")+1,name_direct.length());
			//i.jpg
			String fileName = (i+1) + tempFileName.substring(tempFileName.lastIndexOf("."),tempFileName.length());
			
			sb.append("<IMG NUMBER=\"").append(i+1).append("\" NAME=\"").append(fileName)
				.append("\" DIRECT=\"").append(direct);
						
			File f = new File(tempFileName);
			//�ӿڸ��졣���Ϳ��ļ�
			if(!f.exists() && f.getName().equals(emptyImageName)){
				f.createNewFile();
			}
			if(f.isDirectory()){
				continue;
			}
			
			FileInputStream in = new FileInputStream(f);
			Trace.logDebug( Trace.COMPONENT_FILE, "�ļ�����--->" + f.getName());
			int fileSize = in.available();//��ȡ�ļ���С
			//����xml
			sb.append("\" IMGSIZE=\"").append(fileSize).append("\"/>");
			//���ļ����� "������/*.jpg" �¡�Ŀ¼����Ϊ��Ӧ�����롣
			ZipEntry entry = new ZipEntry(directoryPath+LianaFile.DIR_SEPARATOR+fileName);
			out.putNextEntry(entry);
			while((bytesRead = in.read(buffer,0,bufferLen))!= -1){
				out.write(buffer,0,bytesRead);
			}
			in.close();
			if(isDlete){
				f.delete();
			}
		}
	}
	
	
	/**
	 * ����ļ�
	 * @param sb 
	 * @param out 
	 * @param fileName
	 * @param directoryPath
	 * @param isDlete
	 * @param encoding
	 * @throws IOException
	 */
	public static void packFiles(StringBuffer sb,ZipOutputStream out,String filePathName,boolean isDlete,String encoding) throws IOException{
		if(encoding != null && !"".equals(encoding)){
			out.setEncoding(encoding);
		}
		byte[] bufferOut = sb.toString().getBytes();//��д������
		ZipEntry entry = new ZipEntry(filePathName);
		out.putNextEntry(entry);
		out.write(bufferOut);
	}
	
	
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setDataTypeField(String dataTypeField) {
		this.dataTypeField = dataTypeField;
	}


	public void setBatchCodeField(String batchCodeField) {
		this.batchCodeField = batchCodeField;
	}
	
	
	public void setBathNumField(String bathNumField) {
		this.bathNumField = bathNumField;
	}


	public void setBankCodeField(String bankCodeField) {
		this.bankCodeField = bankCodeField;
	}


	public void setGroupTimeField(String groupTimeField) {
		this.groupTimeField = groupTimeField;
	}


	public void setBarcodeDateField(String barcodeDateField) {
		this.barcodeDateField = barcodeDateField;
	}
	
	
	public void setSelectop(String selectop) {
		this.selectop = selectop;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	
	public static void main(String[] args){
		/*ImageZipUpload iz = new ImageZipUpload();
		Socket socket = null;
		try {
			socket = new Socket("10.2.26.241",8602);
			OutputStream sout = socket.getOutputStream();
			InputStream sin = socket.getInputStream();
			byte[] aa = new byte[31];
			String a = "0000000001";
			byte[] a1 = a.getBytes();
			String b = "0123123123112312.zip";
			byte[] b1 = b.getBytes();
			int k = 0;
			for(int i=0;i<a1.length;i++){
				aa[k]=new Integer(1).byteValue();
				k++;
			}
			for(int j=0;j<b1.length;j++){
				aa[k]=b1[j];
				k++;
			}
			
			aa[k]=new Integer(1).byteValue();
			System.out.println(new String(aa));
			sout.write(aa);
			sout.flush();
			
			Context context = new Context();
			try {
				iz.readDocument(sin, context);
			} finally {
				sout.close();
				sin.close();
				socket.close();
			}
			
		} catch (ConnectException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
	
		/*ImageZipUpload izu = new ImageZipUpload();
		Context context = new Context();
		KeyedCollection kc = new KeyedCollection();
		try {
		context.setDataElement(kc);
		izu.setDataSource("ebank_ds");
		izu.setSelectop("zip");
		izu.setEncoding("GBK");
		izu.setBatchCodeField("batchCode");
		
		kc.addDataField("batchCode","");
		
		context.put("batchCode","0420121001000003" );//���κ�
		
		izu.setDataTypeField("dataType");
		kc.addDataField("dataType","");
		context.put("dataType","01" );//ɨ������
		
		izu.setBathNumField("bathNum");
		kc.addDataField("bathNum","");
		context.put("bathNum","" );//�����η���ʱ��Ϊ��
		
		izu.setBankCodeField("bankCode");
		kc.addDataField("bankCode","");
		context.put("bankCode","21" );//������
		
		izu.setGroupTimeField("groupTime");
		kc.addDataField("groupTime","");
		context.put("groupTime","20120924 11:11:11" );//����ʱ��
		
		izu.setBarcodeDateField("barcodeDate");
		kc.addDataField("barcodeDate","");
		context.put("barcodeDate","20120924 11:11:11" );//����ʱ��
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		try {
			izu.execute(context);
		} catch (EMPException e) {
			e.printStackTrace();
		}*/
		
		/*int part1 = 10;
		int size = 12321341;
		String size_str = String.valueOf(size);
		
		int zeroLeng = part1-size_str.length();
		for(int i=0;i<zeroLeng;i++){
			size_str = "0"+size_str;
		}
		System.out.println(size_str);
		byte[] b = size_str.getBytes();
		System.out.println(new String(b));
		
		
		int a = 111;
		String sa=String.format("%010d", a);
		System.out.println(sa);
		
		int part2=20;
		byte[] names = new byte[part2];
		String filename = "1231212341.jpg";
		System.out.println(filename);
		byte[] names_tmp=filename.getBytes();
		System.out.println(names_tmp.length);
		System.out.println("----------------");
		System.out.println(names_tmp.length);
		if(names_tmp.length<part2){
			int length = part2-names_tmp.length;
			for(int i = 0 ;i < names_tmp.length ; i++){
				names[i]=names_tmp[i];
			}
			for(int j = 0 ; j < length ; j++){
				names[names_tmp.length+j]=new String(" ").getBytes()[0];
			}
		}else if(names_tmp.length > part2){
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "�ļ��������ӿ������ȣ���ȷ���ļ����Ƿ���Ч" );
		}		
		
		String cc1 = new String(names);
		System.out.println(cc1);
		System.out.println(cc1.trim());*/
		
		/*try {
			Socket socket = new Socket("10.2.21.59",1312);
			OutputStream out = socket.getOutputStream();
			String aa = "1234533212";
			out.write(aa.getBytes("GBK"));
			out.flush();
			InputStream in = socket.getInputStream();
			in.read();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/*String date = "20120909111111";
		String rs = LianaFormat.formatDate(date, "yyyyMMdd HH:mm:ss");
		System.out.println(rs);*/
		/*File f = new File("d:/1.jpg");
		if(!f.exists()){
			try {
				boolean nn = f.createNewFile();
				System.out.println(nn);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*String fileName = "D:/fuck/xianxia/0420121001000003/0420121001000003.xml";
		File f = new File(fileName);
		try {
			FileInputStream in = new FileInputStream(f);
			SAXReader saxReader = new SAXReader();
			Document document = null;
			try {
				document = saxReader.read(in);
				Trace.logDebug(Trace.COMPONENT_XML, "��������--->" + document.asXML());
			} catch (DocumentException e) {
				Trace.logError(Trace.COMPONENT_MESSAGE, "����xml����", e);
				e.printStackTrace();
			}
			String xpathStr = "//BODY";
			Node node = (Node) document.selectSingleNode(xpathStr);
			if(node != null){
				String DATASTYLE = "";//ɨ������
				String MAILCODE = "";//�ʰ���
				String BATCHCODE = "";//���κ�
				String MESSAGECODE = "";//������Ϣ����
				DATASTYLE = node.valueOf("DATASTYLE");
				MAILCODE = node.valueOf("MAILCODE");
				BATCHCODE = node.valueOf("BATCHCODE");
				MESSAGECODE = node.valueOf("MESSAGECODE");
				
				Trace.logDebug( Trace.COMPONENT_MESSAGE, "���ؽ����"+MESSAGECODE);
			
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
}
