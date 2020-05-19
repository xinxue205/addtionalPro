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
 * 功能概述：压缩并上传指定路径下的文件到指定Ip主机的指定端口
 * ImageZipUpload.java<br>
 * EMP 交易步骤扩展<br>
 * Extends class JDBCAction<br>
 * 
 * 线下渠道-影像数据上传
 * 
 * @emp:name 
 * @emp:states 
 * @emp:document
 * @author 李细成
 */
public class ImageZipUpload extends JDBCAction {
	private String dataSourceName;
	
	/**
	 *扫描类型 
	 *DATASTYLE 01正常件,02重扫件，03补扫件
	 * 
	 */
	private String dataTypeField;
	
	/**
	 *批次号
	 *BATCHCODE
	 */
	private String batchCodeField;
	
	/**
	 *批次份数
	 *BATCHNUM 无批次份数时可以为空
	 */
	private String bathNumField;
	
	/**
	 *行所号
	 *BANKCODE
	 */
	private String bankCodeField;
	
	/**
	 *分组时间
	 *GROUPTIME
	 */	
	private String groupTimeField;
	
	/**
	 *条形码打印日期
	 *BARCODEDATE
	 **/
	private String barcodeDateField;
	
	
	/**
	 *文件类型 zip/rar
	 *返回 条形码 及 tableCode pkey
	 **/
	private String selectop;
	
	/**
	 *根据批次号查询条形码语句
	 *返回 条形码 及 tableCode
	 **/
	//private static String query1 = "select distinct MARKETINGID,MARKETINGID,MARKETINGID from AD_CB_CASTLINK where adaid = ? with ur";
	//正式
	private static String query1 = "select CMG_DID,CMG_TABLECODE,CMG_APPLYNO from CL_CREDITCARD_MESSAGE where CMG_BATCHID = ? with ur";
	
	/**
	 *根据条形码查询影像信息语句
	 *遍历query1结果，通过条形码语句查询影像信息
	 *返回图片相对地址及图片正反面
	 **/
	private static String query2 = "select fls_path,1 from CL_FILELIST where FLS_APPLYNO =(select cap_applyunion from CL_CREDITCARD_APPLY where cap_applyno=? fetch first 1 row only) with ur";
	
	
	/**
	 * 空图片名称；
	 **/
	private static String emptyImageName = "1.jpg"; 
	
	
	/**
	 * 文件编码
	 * 
	 **/
	private String encoding="GBK";

	/* 业务逻辑操作单元的执行入口 */
	public String execute(Context context) throws EMPException {
		String batchCode = null;//批次号
		if(batchCodeField != null && !batchCodeField.equals("")){
			batchCode = (String)context.get(batchCodeField);
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "批次号为空，通讯结束" );
		}
		
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String fileNamePath = null;//压缩文件路径及名称。
		ZipOutputStream out = null;//压缩文件使用流
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sb.append("<COSP>");
		//获取<Head>部分
		try {
			getHead(context,sb);
		} catch (Exception e) {
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, e );
		}
		try{
			//文件名称:批次号.zip
			//fileNamePath = "D:/fuck/xianxia/"+batchCode+"."+selectop;
			//正式用地址:
			fileNamePath = LianaStandard.getSelfDefineSettingsValue( LianaConstants.SETTINGS_DOWNLOAD_ROOT )+batchCode+"."+selectop;
			out = new ZipOutputStream(new FileOutputStream(fileNamePath));
			sb.append("<BODY>");	
			dataSource=(DataSource) context.getService(dataSourceName);
			
			//connection = ConnectionUtil.getConnection();
			//正式用链接
			connection = this.getConnection(context, dataSource);
			ps = connection.prepareStatement(query1);
			ps.setString(1, batchCode);
			Trace.logDebug( Trace.COMPONENT_JDBC, "查询条件--->" + query1 );
			Trace.logDebug( Trace.COMPONENT_JDBC, "输入参数--->" + batchCode );
			//查询该批次下的所有条形码。(执行过程中，构建XML的内容)
			rs = ps.executeQuery();
			String barCode="";//条形码  
			String tigi_docType="";//资料类型
			String aidi_p_barCode="";//补件原件条码号,不需要填写
			String tableCode = "";//表单代码
			String formCode_x="-1";//原点定位横坐标
			String formCode_y="-1";//原点定位纵坐标
			String cmg_applyno = "";//申请编号
			//遍历条形码，获取该条形码下影像信息。(执行过程中，构建XML的内容)
			while (rs.next()) {
				PreparedStatement ps2 = null;
				ResultSet rs2 = null;
				barCode = rs.getString(1).substring(0,18);
				tigi_docType = batchCode.substring(8, 11);
				tableCode = rs.getString(2);
				cmg_applyno = rs.getString(3);
				//构建OPERATION 头  
				sb.append("<OPERATION BARCODE=\"").append(barCode).append("\"  TIGI_DOCTYPE=\"")
					.append(tigi_docType).append("\" AIDI_P_BARCODE=\"").append(aidi_p_barCode).append("\" TABLECODE=\"")
					.append(tableCode).append("\" FORMCODE_X=\"").append(formCode_x).append("\" FORMCODE_Y=\"")
					.append(formCode_y).append("\">");
				List<String> name_directList = new ArrayList<String>();
				//2012—09—29 接口变更，不用传附件，用空图片代替。
				/*--------------需查询附件开始---------*/
				//查询条形码下的影像数据，遍历影像数据并插入文件中
				//String query2 = "select b.adcontent,b.status from AD_CB_CASTLINK a left join AD_CB_ad b on a.adid=b.adid  where a.marketingid = ? with ur";
				/*ps2 = connection.prepareStatement(query2);
				//ps2.setString(1,barCode);
				ps2.setString(1,cmg_applyno);
				Trace.logDebug( Trace.COMPONENT_JDBC, "查询条件--->" + query2 );
				Trace.logDebug( Trace.COMPONENT_JDBC, "输入参数--->" + cmg_applyno );
				rs2 = ps2.executeQuery();
				while(rs2.next()){
					String fileName = rs2.getString(1);//*.jpg
					String fileDirect = rs2.getString(2);//正反面
					if(fileName == null || fileName.equals("") || fileName.lastIndexOf(".") == -1 ){//文件名不存在或者非文件
						continue;
					}
					//fileName = "D:/affix/"+fileName;
					//正式用地址
					fileName = LianaStandard.getSelfDefineSettingsValue("affixDir")+ fileName;
					
					if(fileDirect == null || fileDirect.equals("")){
						fileDirect = "1";
					}
					String name_direct = fileName+"|"+fileDirect;//*.jpg|0
					name_directList.add(name_direct);
				}
				rs2.close();
				ps2.close();*/
				/*--------------需查询附件结束---------*/
				//2012—09—29 接口变更，不用传附件，用空图片代替。
				/*--------------无需查询附件开始---------*/
				//String fileName = "D:/affix/"+ emptyImageName;
				//正式
				String fileName = LianaStandard.getSelfDefineSettingsValue("affixDir")+ emptyImageName;
				String fileDirect = "1";//正面
				String name_direct = fileName+"|"+fileDirect;//*.jpg|0
				name_directList.add(name_direct);
				
				/*--------------无需查询附件结束---------*/

				//往压缩包放影像文件,并构建xml内容
				ImageZipUpload.packFiles(sb,out,name_directList, barCode, false, encoding);
				name_directList = null;
				sb.append("</OPERATION>");
			}
			rs.close();
			ps.close();
			sb.append("</BODY></COSP>");
			//将xml内容写入临时文件,并放到压缩包里。
			Trace.logDebug( Trace.COMPONENT_XML, "报文内容:" + sb.toString());
			//文件名称:批次号.xml
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
		//建立链接，发送报文 SOCKET 通讯
		//fileNamePath 压缩文件路径
		//IP
		//PORT
		//1-10位字节为上送压缩文件长度字节数，不包函30位文件名长度，长度不足10位前用0或空格补充；
		//11-40位字节为上送外包扫描文件包文件名，不足30位文件名后以空格补足，文件命名是以批次号加上.rar文件扩展名，上送文件名时使用“GBK”编码格式；
		//41位开始为外包扫描文件包内容；
		try {
			sendBySocket(fileNamePath,encoding,context);
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_MESSAGE,"接口发送异常",e);
			e.printStackTrace();
		}
		
		
		return "0";
	}
	
	/**
	 * 发送报文
	 * 1-10位字节为上送压缩文件长度字节数，不包函30位文件名长度，长度不足10位前用0或空格补充；
	 * 11-30位字节为上送外包扫描文件包文件名，不足30位文件名后以空格补足，文件命名是以批次号加上.rar文件扩展名，上送文件名时使用“GBK”编码格式；
	 * 31位开始为外包扫描文件包内容；
	 * @param fileNamePath 
	 * @param encoding 
	 * @param context
	 * @throws IOException,TranFailException
	 */	
	private void sendBySocket(String fileNamePath, String encoding, Context context) throws IOException, TranFailException {
		//行外影像质检系统IP
		//String IP = "10.2.26.241";
		//正式
		String IP = LianaStandard.getSelfDefineSettingsValue("imageUploadIp");
		//行外影像质检系统端口
		//String port_str = "8602";
		//正式
		String port_str = LianaStandard.getSelfDefineSettingsValue("imageUploadPort");
		
		int port = Integer.parseInt(port_str);
		//向外发送的内容
		byte[] content = getContent(fileNamePath, encoding);
		Socket socket =null;
		try{
			socket = new Socket(IP, port);//建立socket连接
		}catch(ConnectException e){
			Trace.logError(Trace.COMPONENT_MESSAGE, "socket连接异常", e);
			e.printStackTrace();
		}
		
		OutputStream sout = socket.getOutputStream();
		InputStream sin = socket.getInputStream();
		/*int _length=0;
		//获取总字节数
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
	 * 返回报文解析
	 * @param inputXml 
	 * @param context 
	 * @throws DocumentException
	 */	
	private void readDocument(InputStream inputXml,Context context){
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(inputXml);
			Trace.logDebug(Trace.COMPONENT_XML, "报文内容:" + document.asXML());
		} catch (DocumentException e) {
			Trace.logError(Trace.COMPONENT_MESSAGE, "解析xml出错", e);
			e.printStackTrace();
		}
		String xpathStr = "//BODY";
		Node node = (Node) document.selectSingleNode(xpathStr);
		if(node != null){
			String DATASTYLE = "";//扫描类型
			String MAILCODE = "";//邮包号
			String BATCHCODE = "";//批次号
			String MESSAGECODE = "";//返回消息代码
			DATASTYLE = node.valueOf("DATASTYLE");
			MAILCODE = node.valueOf("MAILCODE");
			BATCHCODE = node.valueOf("BATCHCODE");
			MESSAGECODE = node.valueOf("MESSAGECODE");
			
			Trace.logDebug( Trace.COMPONENT_MESSAGE, "返回结果："+MESSAGECODE);
			
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
	 * 生成发送报文
	 * @param fileNamePath 
	 * @param encoding 
	 * @throws IOException, TranFailException
	 */	
	private byte[] getContent(String fileNamePath,String encoding) throws IOException, TranFailException{
		//List<byte[]> bytesList = new ArrayList<byte[]>();
		int totalSize=0;
		int part1 = 10;//1-10位
		int part2 = 30;//11-40位
		FileInputStream in =null;//41以后
		int size = 0;//文件大小
		byte[] sizes = null;//文件长度
		byte[] names = new byte[part2];//文件名
		File f = new File(fileNamePath);
		if(!f.isDirectory()){
			in = new FileInputStream(f);
			//文件大小
			size = in.available();
			if(String.valueOf(size).length()>part1){
				throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "文件长度超出接口允许长度，请检查并控制文件大小" );
			}
			String size_str=String.format("%010d", size);//不足10位在前补0
			sizes = size_str.getBytes(encoding);
			//bytesList.add(sizes);
			totalSize = totalSize + part1;//第一段报文长度
		}
		String filename = f.getName();
		byte[] names_tmp=filename.getBytes(encoding);
		if(names_tmp.length<part2){//文件名称小于20位，在后面补空格
			int length = part2-names_tmp.length;//相差位数
			for(int i = 0 ;i < names_tmp.length ; i++){//写入存在的数据
				names[i]=names_tmp[i];
			}
			for(int j = 0 ; j < length ; j++){//补全空格
				names[names_tmp.length+j]=new String(" ").getBytes(encoding)[0];
			}
		}else if(names_tmp.length > part2){
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "文件名超出接口允许长度，请确认文件名是否有效" );
		}else{
			names=names_tmp;
		}	
		//bytesList.add(names);
		totalSize = totalSize +part2;//第二段报文长度
		//将文件转为字节。
		byte[] fileContent = new byte[size];
		in.read(fileContent);
		in.close();
		//bytesList.add(fileContent);
		totalSize = totalSize + size;//第三段报文长度
		byte[] content = new byte[totalSize]; 
		int index=0;//标志位
		//合并文件长度部分至content
		for(int k=0;k < part1;k++){
			content[index]=sizes[k];
			index++;			
		}
		//合并文件名部分至content
		for(int k=0;k < part2;k++){
			content[index]=names[k];
			index++;			
		}
		//合并文件内容部分至content
		for(int k=0;k < size;k++){
			content[index]=fileContent[k];
			index++;			
		}
		return content;
	}
	
	/**
	 * 构造XML文件<HEAD>内容
	 * @param context 
	 * @param sb 
	 * @throws Exception
	 */	
 	private void getHead(Context context,StringBuffer sb) throws Exception{
		sb.append("<HEAD>");
		String dataType = "01";//扫描类型:01正常件,02重扫件，03补扫件
		if(dataTypeField != null && !dataTypeField.equals("")){
			dataType = (String)context.get(dataTypeField);
		}
		sb.append("<DATASTYLE>").append(dataType).append("</DATASTYLE>");
		sb.append("<MAILCODE></MAILCODE>");//邮包号,网营不用填写
		
		String batchCode = null;//批次号
		if(batchCodeField != null && !batchCodeField.equals("")){
			batchCode = (String)context.get(batchCodeField);
			sb.append("<BATCHCODE>").append(batchCode).append("</BATCHCODE>");
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "批次号为空不能执行" );
		}
		
		String bathNum = "";//批次份数(无批次份数时可为空)
		if(bathNumField != null && !bathNumField.equals("")){
			bathNum = (String)context.get(bathNumField);
		}
		sb.append("<BATCHNUM>").append(bathNum).append("</BATCHNUM>");//无批次份数时可为空
		
		String bankCode=null;//行所号
		if(bankCodeField != null && !bankCodeField.equals("")){
			bankCode = (String)context.get(bankCodeField);
			sb.append("<BANKCODE>").append(bankCode).append("</BANKCODE>");
		}else{
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "行所号为空不能执行" );
		}
		//影像组状态,默认填“0”
		sb.append("<IMGSTATUS>0</IMGSTATUS>");
		//分组时间
		String groupTime = "";
		if(groupTimeField != null && !groupTimeField.equals("")){
			groupTime = LianaFormat.formatDate((String)context.get(groupTimeField),"yyyyMMdd HH:mm:ss");
		}else{
			groupTime = new SetTimeAction().getCurrentTime("yyyyMMdd HH:mm:ss");
		}
		sb.append("<GROUPTIME>").append(groupTime).append("</GROUPTIME>");	
		//条形码打印日期
		String barCodeDate = "";
		if(barcodeDateField != null && !barcodeDateField.equals("")){
			barCodeDate = LianaFormat.formatDate((String)context.get(barcodeDateField),"yyyyMMdd HH:mm:ss");
		}else{
			barCodeDate = new SetTimeAction().getCurrentTime("yyyyMMdd HH:mm:ss");
		}
		sb.append("<BARCODEDATE>").append(barCodeDate).append("</BARCODEDATE>");	
		
		//扫描操作员ID
		sb.append("<SCANOPERID></SCANOPERID>");
		//公司ID
		sb.append("<CORPID>04</CORPID>");
		//渠道ID
		sb.append("<CHANNELID>04</CHANNELID>");
		sb.append("</HEAD>");
	}
	
	/**
	 * 打包文件
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
			//1 或者 0
			String direct = name_direct.substring(name_direct.indexOf("|")+1,name_direct.length());
			//i.jpg
			String fileName = (i+1) + tempFileName.substring(tempFileName.lastIndexOf("."),tempFileName.length());
			
			sb.append("<IMG NUMBER=\"").append(i+1).append("\" NAME=\"").append(fileName)
				.append("\" DIRECT=\"").append(direct);
						
			File f = new File(tempFileName);
			//接口改造。发送空文件
			if(!f.exists() && f.getName().equals(emptyImageName)){
				f.createNewFile();
			}
			if(f.isDirectory()){
				continue;
			}
			
			FileInputStream in = new FileInputStream(f);
			Trace.logDebug( Trace.COMPONENT_FILE, "文件名称--->" + f.getName());
			int fileSize = in.available();//获取文件大小
			//构造xml
			sb.append("\" IMGSIZE=\"").append(fileSize).append("\"/>");
			//将文件放至 "条形码/*.jpg" 下。目录名称为对应条形码。
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
	 * 打包文件
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
		byte[] bufferOut = sb.toString().getBytes();//待写入内容
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
		
		context.put("batchCode","0420121001000003" );//批次号
		
		izu.setDataTypeField("dataType");
		kc.addDataField("dataType","");
		context.put("dataType","01" );//扫描类型
		
		izu.setBathNumField("bathNum");
		kc.addDataField("bathNum","");
		context.put("bathNum","" );//无批次份数时可为空
		
		izu.setBankCodeField("bankCode");
		kc.addDataField("bankCode","");
		context.put("bankCode","21" );//行所号
		
		izu.setGroupTimeField("groupTime");
		kc.addDataField("groupTime","");
		context.put("groupTime","20120924 11:11:11" );//分组时间
		
		izu.setBarcodeDateField("barcodeDate");
		kc.addDataField("barcodeDate","");
		context.put("barcodeDate","20120924 11:11:11" );//分组时间
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
			throw new TranFailException( LianaConstants.DEFAULT_ERROR_CODE, "文件名超出接口允许长度，请确认文件名是否有效" );
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
				Trace.logDebug(Trace.COMPONENT_XML, "报文内容--->" + document.asXML());
			} catch (DocumentException e) {
				Trace.logError(Trace.COMPONENT_MESSAGE, "解析xml出错", e);
				e.printStackTrace();
			}
			String xpathStr = "//BODY";
			Node node = (Node) document.selectSingleNode(xpathStr);
			if(node != null){
				String DATASTYLE = "";//扫描类型
				String MAILCODE = "";//邮包号
				String BATCHCODE = "";//批次号
				String MESSAGECODE = "";//返回消息代码
				DATASTYLE = node.valueOf("DATASTYLE");
				MAILCODE = node.valueOf("MAILCODE");
				BATCHCODE = node.valueOf("BATCHCODE");
				MESSAGECODE = node.valueOf("MESSAGECODE");
				
				Trace.logDebug( Trace.COMPONENT_MESSAGE, "返回结果："+MESSAGECODE);
			
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
}
