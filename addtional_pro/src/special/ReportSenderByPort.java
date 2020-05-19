package com.ecc.liana.innermanage.action;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ecc.emp.core.Context;
import com.ecc.emp.core.EMPException;
import com.ecc.emp.data.IndexedCollection;
import com.ecc.emp.data.KeyedCollection;
import com.ecc.emp.jdbc.JDBCAction;
import com.ecc.liana.base.LianaStandard;
import com.ecc.liana.base.Trace;
import com.ecc.liana.exception.BusinessCommException;




/**
 * ���ܸ��������ͱ��ĵ�ָ���˿�
 * CheckUniqueAction.java<br>
 * EMP ���ײ�����չ<br>
 * Extends class EMPAction<br>
 * 
 * �㷢���н�������ϵͳ����Ӫ����������������ʵʱ���ÿ���������֮��Ľӿ�
 * �����ն�������Ϣ
 * @emp:name 
 * @emp:states 
 * @emp:document
 */
public class creditCardHttpClientAction extends JDBCAction {


	/* ҵ���߼�������Ԫ��ִ����� */
	public String execute(Context context) throws EMPException {
			try{
				creditCardHttpClient( context);
			}catch(Exception e){
				throw new BusinessCommException("EBLN2000", "ҵ�������쳣");
			
			}
			return "0";
	}
	/*
	 * ���� �����������ͱ���
	 * ���� ������Ӧ����
	 */
	public void creditCardHttpClient(Context context) throws Exception{

		String gateway_senderDate = LianaStandard.getServerTime( "yyyyMMdd" );
		String gateway_senderTime =LianaStandard.getServerTime( "HHmmss" );
		String SETIME  =LianaStandard.getServerTime( "yyyyMMdd HH:mm:ss" );
		
//		String branchId = (String) context.getDataValue("session_userBranchId");
//		Trace.log("������ţ�", branchId);
		String gateway_senderSN = LianaStandard.getServerTime( "yyyyMMddHHmmssSSS" ) + (new Random()).nextInt( 10000000 ) ;
		
		
//		String head_batchid = LianaStandard.getServerTime( "yyyyMMddHHmmssSSS" ) + (new Random()).nextInt( 1000 ) ;
/*
//�ӿ��ڲ�start ʵ�ʲ��Դ�context
		
		IndexedCollection iSendList =  new IndexedCollection();
		KeyedCollection errkcoll = new KeyedCollection();
		errkcoll.setAppend(true);				
//		ID:18λӰ������
		String ID = "012345678900000001";
    	errkcoll.addDataField("ID",ID);
    	
//		DID:20Ϊ��������
    	String DID="01234567890000000101";
    	errkcoll.addDataField("DID",DID);
    	
    	String BANKID="07";
    	errkcoll.addDataField("BANKID",BANKID);
    	
    	String TABLECODE="029101";
    	errkcoll.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	String VALUE="0711070801001||012345678900000001||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||�¹��||CHENGUANGCHENG||1||0||19810103||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||2||9||1||2||||81024567||13611128745||||||510000||�й�||vivi@gdb.com.cn||�¶�||�������||||8||�㶫ʡ�����ж���·||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||�¶�||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||510000||||||||||||||||||||||99991231||||�㶫ʡ||������||�������齭�³ǣ���||����||����||�㶫ʡ||������||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll);
 //--�ڶ�������   	
    	KeyedCollection errkcoll2 = new KeyedCollection();
  
//		ID:18λӰ������
		ID = "012345678900000002";
		errkcoll2.addDataField("ID",ID);
    	
//		DID:20Ϊ��������
    	DID="01234567890000000201";
    	errkcoll2.addDataField("DID",DID);
    	
    	BANKID="07";
    	errkcoll2.addDataField("BANKID",BANKID);
    	
    	TABLECODE="029101";
    	errkcoll2.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	VALUE="0711070801001||01234567890000000201||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||�¹��||CHENGUANGCHENG||1||0||19810103||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||2||9||1||2||||81024567||13611128745||||||510000||�й�||vivi@gdb.com.cn||�¶�||�������||||8||�㶫ʡ�����ж���·||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||�¶�||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||510000||||||||||||||||||||||99991231||||�㶫ʡ||������||�������齭�³ǣ���||����||����||�㶫ʡ||������||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll2.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll2);   	
    	
 //--����������   	
    	KeyedCollection errkcoll3 = new KeyedCollection();
  
//		ID:18λӰ������
		ID = "012345678900000003";
		errkcoll3.addDataField("ID",ID);
    	
//		DID:20Ϊ��������
    	DID="01234567890000000301";
    	errkcoll3.addDataField("DID",DID);
    	
    	BANKID="07";
    	errkcoll3.addDataField("BANKID",BANKID);
    	
    	TABLECODE="029101";
    	errkcoll3.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	VALUE="0711070801001||01234567890000000301||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||�¹��||CHENGUANGCHENG||1||0||19810103||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||2||9||1||2||||81024567||13611128745||||||510000||�й�||vivi@gdb.com.cn||�¶�||�������||||8||�㶫ʡ�����ж���·||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||�¶�||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||510000||||||||||||||||||||||99991231||||�㶫ʡ||������||�������齭�³ǣ���||����||����||�㶫ʡ||������||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll3.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll3);    	
//�ӿ��ڲ�end
 * 
 */
		IndexedCollection iSendList =  (IndexedCollection) context
		.getDataElement("applyDataSendList");
		
//		IndexedCollection netReasonFailedList =  (IndexedCollection) context
//		.getDataElement("netReasonFailedList");
		
		String head_batchid = (String)context.get("batchNo");
		
		String input ="<?xml version=\"1.0\" encoding=\"GBK\"?>"
			+"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+" xmlns:gateway=\"http:// www. gdb.com.cn /GDBGateway\">"
				+"	<soapenv:Header>"
//						<!-- ͨ�ñ���ͷ -->"
				+"		<gateway:HeadType>"
				+"			<gateway:versionNo>1</gateway:versionNo>"
				+"			<gateway:toEncrypt>0</gateway:toEncrypt>"
				+"			<gateway:commCode>500001</gateway:commCode>"
				+"			<gateway:commType>0</gateway:commType>"
//							<!�����շ�ϵͳ��ʶ,���ÿ���������ϵͳΪGFJ2 -->"
				+"			<gateway:receiverId> GFJ2</gateway:receiverId>"
				+"			<gateway:senderId>AFAAABS</gateway:senderId>"
//				+"			<gateway:senderSN>020080128143000000000001</gateway:senderSN>"
				+"			<gateway:senderSN>"+gateway_senderSN+"</gateway:senderSN>"
//				+"			<gateway:senderDate>20080128</gateway:senderDate>"
				+"			<gateway:senderDate>"+ gateway_senderDate+"</gateway:senderDate>"
//				+"			<gateway:senderTime>143000</gateway:senderTime>"
				+"			<gateway:senderTime>"+gateway_senderTime+"</gateway:senderTime>"
//				+"			<!��������,�������ÿ���������ϵͳ������ΪGFJJ01 -->"
				+"			<gateway:tradeCode>GFJJ01</gateway:tradeCode>"
				+"			<gateway:gwErrorCode >01</gateway:gwErrorCode >"
				+"			<gateway:gwErrorMessage></gateway:gwErrorMessage >"
				+"		</gateway:HeadType>"
				+"	</soapenv:Header>"
				+"	<soapenv:Body>"
//						<!-- ��A400���������� -->"
				+"		<gateway:NoAS400>"
				+"<GFJJ>"
				+"	<HEAD>"
//						<!--���κ�{��������ϵͳУ�飬���κŲ����ظ�����} 20 λ-->"
//				+"		<BATCHID>0311070801001</BATCHID>"
				+"		<BATCHID>"+head_batchid+"</BATCHID>"
//						<!--��������{��������ϵͳУ�飬��Ӫ��03 }-->"
				+"		<CHANNELID>04</CHANNELID>"
//						<!--���Ĵ���ʱ��(�������������������ͱ���������ʱ��ֵ)  ��ʽ YYYYMMDD HH:SS:MM-->"
//				+"		<SETIME>20120212 18:59:30</SETIME>"
				+"		<SETIME>"+SETIME+"</SETIME>"
//						<!--���α���1-->"
				+"		<BATCH1></BATCH1>"
//						<!--���α���2-->"
				+"		<BATCH2></BATCH2>"
//						<!--���α���3-->"
				+"		<BATCH3></BATCH3>"
//						<!--���α���4-->"
				+"		<BATCH4></BATCH4>"
//						<!--���α���5-->"
				+"		<BATCH5></BATCH5>"
				+"	</HEAD>"
				+"	<BODY>";
//						<!--"
//						<BARCODE ID="18λӰ������" BANKID="������2λ" DATATYPE="��������" TABLECODE="�����룬�����°�5��һ������Ϊ060102" REMARK1="��ע1" REMARK2="��ע2" REMARK3="��ע3">"
//							<DATA >"
//						<DID>20λ�������룬ǰ18λ�����Ӱ������һ��</DID>"
//							<TABLECODE>N��һҵ�񵥿������룬�������鿨�����������Ϻ����ı�����</TABLECODE> "
//								<REMARK4>��ע4</REMARK4> "
//								<REMARK5>��ע5</REMARK5> "
//								<REMARK6>��ע6</REMARK6>"
//							</DATA>"
//						</BARCODE>"
//						-->"
//						<!--ʾ��һ��N��һҵ������������-->"
//				+"		<BARCODE ID=\"072010112201000001\" BANKID=\"07\" DATATYPE=\"047\" TABLECODE=\"060102\" REMARK1=\"\" REMARK2=\"\" REMARK3=\"\">"
//				+"			<DATA>	"
//				+"				<DID>07201011220100000100</DID>"
//				+"		 		<TABLECODE>029101</TABLECODE> "
//				+"				<REMARK4></REMARK4> "
//				+"				<REMARK5></REMARK5> "
//				+"				<REMARK6></REMARK6>"
//				+"				<VALUE>0711070801001||07201011220100000100||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||�¹��||CHENGUANGCHENG||1||0||19810103||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||2||9||1||2||||81024567||13611128745||||||510000||�й�||vivi@gdb.com.cn||�¶�||�������||||8||�㶫ʡ�����ж���·||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||�¶�||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||�㶫ʡ�������齭�³ǣ���||||������||�㶫ʡ||510000||||||||||||||||||||||99991231||||�㶫ʡ||������||�������齭�³ǣ���||����||����||�㶫ʡ||������||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||</VALUE>"
//				+"				</DATA>"
//				+"		</BARCODE>"

//ѭ����д������		
		for(Iterator<KeyedCollection> it =  iSendList.iterator(); it.hasNext(); ) {
			//KeyedCollection netReasonKcoll = new KeyedCollection();
        	KeyedCollection barinfo = it.next();
    		String bar_ID = (String) barinfo.get("yxTiaoXingMa");//Ӱ������
    		String bar_DID = (String) barinfo.get("tiaoXingMa");//��������
    		String bar_BANKID = (String) barinfo.get("SubBranch");//������
    		String bar_TABLECODE = (String) barinfo.get("yxTableCode");//Ӱ�������
    		String bar_VALUE = (String) barinfo.get("840Data");
    		input +="<BARCODE ID=\""+bar_ID
    		          +"\" BANKID=\""+bar_BANKID+"\" DATATYPE=\"010\""
    		          +" TABLECODE=\""+bar_TABLECODE+"\" REMARK1=\"\" REMARK2=\"\" REMARK3=\"\">"
				+"			<DATA>	"
				+"				<DID>"+bar_DID+"</DID>"
				+"		 		<TABLECODE>"+bar_TABLECODE+"</TABLECODE> "
				+"				<REMARK4></REMARK4> "
				+"				<REMARK5></REMARK5> "
				+"				<REMARK6></REMARK6>"
				+"				<VALUE>"+bar_VALUE+"</VALUE>"
				+"				</DATA>"
				+"		</BARCODE>";
//    		
//    		 netReasonKcoll.setAppend(true);
//    	     netReasonKcoll.addDataField("tiaoXingMa",head_batchid);
//    	     netReasonKcoll.addDataField("JinJianFailedMsg","�������ⷢ��ʧ��");
//    	     netReasonKcoll.addDataField("sendJinJianStatus","0");
//    	     netReasonKcoll.addDataField("reSendType","0");
//    	     
//    	     netReasonFailedList.addDataElement(netReasonKcoll);
    		
        }
        input +="	</BODY>"
				+"</GFJJ>"
				+"		</gateway:NoAS400>"
				+"	</soapenv:Body>"
				+"</soapenv:Envelope>";
        
        context.put("batchNoNet", head_batchid);
        context.put("batchStatusNet", "0");
        context.put("resendTypeNet", "0");
        context.put("statusMsgNet", "�������ⷢ��ʧ��");
        context.put("failedCountNet", "0");
		
		String wdURL =  LianaStandard.getSelfDefineSettingsValue("creditCardURL");
		PostMethod post = new PostMethod(wdURL);		
		try {
			post.setRequestEntity(new StringRequestEntity(input, null,"GBK"));
			Trace.log("���͵���������ϵͳ��", input);
		} catch (UnsupportedEncodingException e) {
			Trace.logError(Trace.COMPONENT_ACTION, "���ݱ����쳣", e);
			throw e;
		}
				
		// ָ���������ݵ�����
		post.setRequestHeader("Method", "POST " + "/GFJJ/SSJJservlet" + " HTTP/1.1");
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		HttpClient httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(120000);
		try {
			int result = httpclient.executeMethod(post);   //��������
		} catch (HttpException e) {
			Trace.logError(Trace.COMPONENT_ACTION, "post�����������ϵͳ�쳣", e);
			throw e;
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_ACTION,"�������ݵ���������ϵͳ�쳣",e);
			throw e;
		}

		InputStream resStream=null;
		try {
			resStream = post.getResponseBodyAsStream();   //��ȡ��������Ϣ
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_ACTION,"��������ϵͳ���������쳣",e);
			throw e;
		}

		creditCardHttpClientAction doNetNodeAction = new creditCardHttpClientAction();
		doNetNodeAction.getReturnMsg(resStream,context);
		
		post.releaseConnection();		
	}
/*
 * ��ȡ������Ϣ
 */
public void getReturnMsg(InputStream inputXml,Context context) throws Exception {
	try {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputXml);
		
		System.out.println("���յ��ı���"+document.asXML());
		String xpathStr ="//soapenv:Envelope/soapenv:Body/gateway:NoAS400/GFJJ/HEAD";
		Node HeadNode=(Node) document.selectSingleNode(xpathStr);
		
		
		IndexedCollection iLogList =  new IndexedCollection();
		
		if(HeadNode!=null)
		{
//			���ش������κ�	BATCHID	20
			String BATCHID="";
			BATCHID=HeadNode.valueOf("BATCHID");
			context.put("BATCHID",BATCHID);
			/*			
			 * <!--���ش����״̬-->
			 *  <!--״̬:
			 *  0000{�ɹ�}
			 *  0001{ʧ��}
			 -->*/
			String BATCHSTATUS="";
			BATCHSTATUS=HeadNode.valueOf("BATCHSTATUS");
			if(BATCHSTATUS.equals("0000")){
				BATCHSTATUS="1";
			}else{
				BATCHSTATUS="0";
			}
			context.put("BATCHSTATUS",BATCHSTATUS);
//			���ش������������	STATUSMSG	100
			String STATUSMSG="";
			STATUSMSG=HeadNode.valueOf("STATUSMSG");
			context.put("STATUSMSG",STATUSMSG);
			
			context.put("reSendType","1");
			context.put("dataStyl","01");
			
			if(! "0000".equals(BATCHSTATUS)){
				context.put("errorCode","ENL10000");
				context.put("errorMessage",STATUSMSG);
				Trace.logDebug( Trace.COMPONENT_MAPPING, "���ش����״̬" + BATCHSTATUS);
				Trace.logDebug( Trace.COMPONENT_MAPPING, STATUSMSG );
			}

			
			Node BARCODE = null;

			xpathStr ="//soapenv:Envelope/soapenv:Body/gateway:NoAS400/GFJJ/BODY/BARCODE";
			
	        for(Iterator<Node> it =  document.selectNodes(xpathStr).iterator(); it.hasNext(); ) {
	        	BARCODE = it.next();
				KeyedCollection errkcoll = new KeyedCollection();
				errkcoll.setAppend(true);				
//				ID:18λӰ������
				String ID = BARCODE.valueOf("@ID");
	        	errkcoll.addDataField("ID",ID);
	        	
//				DID:20Ϊ��������
	        	String DID=BARCODE.valueOf("DATA/DID");
	        	errkcoll.addDataField("tiaoXingMa",DID);
	        	
	        	/*
				 * 	STATUS:����״̬
				 *	B000 �ɹ�
				 *	B001 ʧ��
				 */
				String STATUS=BARCODE.valueOf("DATA/STATUS");
				if(STATUS.equals("B000")){
					STATUS="1";
					errkcoll.addDataField("reSendType","");
				}else if(STATUS.equals("B001")){
					STATUS="0";
					errkcoll.addDataField("reSendType","1");
				}
				errkcoll.addDataField("sendJinJianStatus",STATUS);
				
//				������Ϣ
				String MSG=BARCODE.valueOf("DATA/MSG");
				errkcoll.addDataField("JinJianFailedMsg",MSG);
				
				iLogList.addDataElement(errkcoll);
			}
	        Trace.logDebug( Trace.COMPONENT_MAPPING, iLogList.toString() );
	        context.put("sendStatusList",iLogList);
		}		

			
	} catch (DocumentException e) {
		Trace.logError(Trace.COMPONENT_ACTION,"����xml����",e);
		throw e;
	}		
}
	


}
