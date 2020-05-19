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
 * 功能概述：发送报文到指定端口
 * CheckUniqueAction.java<br>
 * EMP 交易步骤扩展<br>
 * Extends class EMPAction<br>
 * 
 * 广发银行进件管理系统与网营进件渠道进件渠道实时信用卡申请数据之间的接口
 * 柜面终端上送信息
 * @emp:name 
 * @emp:states 
 * @emp:document
 */
public class creditCardHttpClientAction extends JDBCAction {


	/* 业务逻辑操作单元的执行入口 */
	public String execute(Context context) throws EMPException {
			try{
				creditCardHttpClient( context);
			}catch(Exception e){
				throw new BusinessCommException("EBLN2000", "业务数据异常");
			
			}
			return "0";
	}
	/*
	 * 发送 进件渠道上送报文
	 * 接收 进件响应报文
	 */
	public void creditCardHttpClient(Context context) throws Exception{

		String gateway_senderDate = LianaStandard.getServerTime( "yyyyMMdd" );
		String gateway_senderTime =LianaStandard.getServerTime( "HHmmss" );
		String SETIME  =LianaStandard.getServerTime( "yyyyMMdd HH:mm:ss" );
		
//		String branchId = (String) context.getDataValue("session_userBranchId");
//		Trace.log("机构编号：", branchId);
		String gateway_senderSN = LianaStandard.getServerTime( "yyyyMMddHHmmssSSS" ) + (new Random()).nextInt( 10000000 ) ;
		
		
//		String head_batchid = LianaStandard.getServerTime( "yyyyMMddHHmmssSSS" ) + (new Random()).nextInt( 1000 ) ;
/*
//接口内测start 实际测试从context
		
		IndexedCollection iSendList =  new IndexedCollection();
		KeyedCollection errkcoll = new KeyedCollection();
		errkcoll.setAppend(true);				
//		ID:18位影像条码
		String ID = "012345678900000001";
    	errkcoll.addDataField("ID",ID);
    	
//		DID:20为数据条码
    	String DID="01234567890000000101";
    	errkcoll.addDataField("DID",DID);
    	
    	String BANKID="07";
    	errkcoll.addDataField("BANKID",BANKID);
    	
    	String TABLECODE="029101";
    	errkcoll.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	String VALUE="0711070801001||012345678900000001||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||陈光诚||CHENGUANGCHENG||1||0||19810103||广东省广州市珠江新城１号||||广州市||广东省||2||9||1||2||||81024567||13611128745||||||510000||中国||vivi@gdb.com.cn||陈二||光大银行||||8||广东省广州市东风路||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||陈二||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||广东省广州市珠江新城１号||||广州市||广东省||510000||||||||||||||||||||||99991231||||广东省||广州市||广州市珠江新城１号||人事||经理||广东省||广州市||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll);
 //--第二条测试   	
    	KeyedCollection errkcoll2 = new KeyedCollection();
  
//		ID:18位影像条码
		ID = "012345678900000002";
		errkcoll2.addDataField("ID",ID);
    	
//		DID:20为数据条码
    	DID="01234567890000000201";
    	errkcoll2.addDataField("DID",DID);
    	
    	BANKID="07";
    	errkcoll2.addDataField("BANKID",BANKID);
    	
    	TABLECODE="029101";
    	errkcoll2.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	VALUE="0711070801001||01234567890000000201||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||陈光诚||CHENGUANGCHENG||1||0||19810103||广东省广州市珠江新城１号||||广州市||广东省||2||9||1||2||||81024567||13611128745||||||510000||中国||vivi@gdb.com.cn||陈二||光大银行||||8||广东省广州市东风路||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||陈二||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||广东省广州市珠江新城１号||||广州市||广东省||510000||||||||||||||||||||||99991231||||广东省||广州市||广州市珠江新城１号||人事||经理||广东省||广州市||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll2.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll2);   	
    	
 //--第三条测试   	
    	KeyedCollection errkcoll3 = new KeyedCollection();
  
//		ID:18位影像条码
		ID = "012345678900000003";
		errkcoll3.addDataField("ID",ID);
    	
//		DID:20为数据条码
    	DID="01234567890000000301";
    	errkcoll3.addDataField("DID",DID);
    	
    	BANKID="07";
    	errkcoll3.addDataField("BANKID",BANKID);
    	
    	TABLECODE="029101";
    	errkcoll3.addDataField("TABLECODE",TABLECODE);
 	
//		value
    	VALUE="0711070801001||01234567890000000301||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||陈光诚||CHENGUANGCHENG||1||0||19810103||广东省广州市珠江新城１号||||广州市||广东省||2||9||1||2||||81024567||13611128745||||||510000||中国||vivi@gdb.com.cn||陈二||光大银行||||8||广东省广州市东风路||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||陈二||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||广东省广州市珠江新城１号||||广州市||广东省||510000||||||||||||||||||||||99991231||||广东省||广州市||广州市珠江新城１号||人事||经理||广东省||广州市||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||";
    	errkcoll3.addDataField("VALUE",VALUE);
    	
    	iSendList.addDataElement(errkcoll3);    	
//接口内测end
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
//						<!-- 通用报文头 -->"
				+"		<gateway:HeadType>"
				+"			<gateway:versionNo>1</gateway:versionNo>"
				+"			<gateway:toEncrypt>0</gateway:toEncrypt>"
				+"			<gateway:commCode>500001</gateway:commCode>"
				+"			<gateway:commType>0</gateway:commType>"
//							<!—接收方系统标识,信用卡进件管理系统为GFJ2 -->"
				+"			<gateway:receiverId> GFJ2</gateway:receiverId>"
				+"			<gateway:senderId>AFAAABS</gateway:senderId>"
//				+"			<gateway:senderSN>020080128143000000000001</gateway:senderSN>"
				+"			<gateway:senderSN>"+gateway_senderSN+"</gateway:senderSN>"
//				+"			<gateway:senderDate>20080128</gateway:senderDate>"
				+"			<gateway:senderDate>"+ gateway_senderDate+"</gateway:senderDate>"
//				+"			<gateway:senderTime>143000</gateway:senderTime>"
				+"			<gateway:senderTime>"+gateway_senderTime+"</gateway:senderTime>"
//				+"			<!—交易码,上送信用卡进件管理系统交易码为GFJJ01 -->"
				+"			<gateway:tradeCode>GFJJ01</gateway:tradeCode>"
				+"			<gateway:gwErrorCode >01</gateway:gwErrorCode >"
				+"			<gateway:gwErrorMessage></gateway:gwErrorMessage >"
				+"		</gateway:HeadType>"
				+"	</soapenv:Header>"
				+"	<soapenv:Body>"
//						<!-- 非A400主机报文体 -->"
				+"		<gateway:NoAS400>"
				+"<GFJJ>"
				+"	<HEAD>"
//						<!--批次号{进件管理系统校验，批次号不能重复进件} 20 位-->"
//				+"		<BATCHID>0311070801001</BATCHID>"
				+"		<BATCHID>"+head_batchid+"</BATCHID>"
//						<!--进件渠道{进件管理系统校验，网营：03 }-->"
				+"		<CHANNELID>04</CHANNELID>"
//						<!--报文创建时间(各个进件渠道发送上送报文至进件时赋值)  格式 YYYYMMDD HH:SS:MM-->"
//				+"		<SETIME>20120212 18:59:30</SETIME>"
				+"		<SETIME>"+SETIME+"</SETIME>"
//						<!--批次备用1-->"
				+"		<BATCH1></BATCH1>"
//						<!--批次备用2-->"
				+"		<BATCH2></BATCH2>"
//						<!--批次备用3-->"
				+"		<BATCH3></BATCH3>"
//						<!--批次备用4-->"
				+"		<BATCH4></BATCH4>"
//						<!--批次备用5-->"
				+"		<BATCH5></BATCH5>"
				+"	</HEAD>"
				+"	<BODY>";
//						<!--"
//						<BARCODE ID="18位影像条码" BANKID="行所号2位" DATATYPE="资料类型" TABLECODE="表单代码，比如新版5合一表单代码为060102" REMARK1="备注1" REMARK2="备注2" REMARK3="备注3">"
//							<DATA >"
//						<DID>20位数据条码，前18位必须和影像条码一致</DID>"
//							<TABLECODE>N合一业务单卡表单代码，比如真情卡，聪明卡，南航卡的表单代码</TABLECODE> "
//								<REMARK4>备注4</REMARK4> "
//								<REMARK5>备注5</REMARK5> "
//								<REMARK6>备注6</REMARK6>"
//							</DATA>"
//						</BARCODE>"
//						-->"
//						<!--示例一笔N合一业务拆分两条数据-->"
//				+"		<BARCODE ID=\"072010112201000001\" BANKID=\"07\" DATATYPE=\"047\" TABLECODE=\"060102\" REMARK1=\"\" REMARK2=\"\" REMARK3=\"\">"
//				+"			<DATA>	"
//				+"				<DID>07201011220100000100</DID>"
//				+"		 		<TABLECODE>029101</TABLECODE> "
//				+"				<REMARK4></REMARK4> "
//				+"				<REMARK5></REMARK5> "
//				+"				<REMARK6></REMARK6>"
//				+"				<VALUE>0711070801001||07201011220100000100||77777||C||J||C||1||1||029101||1||440105198101031655||||OC||陈光诚||CHENGUANGCHENG||1||0||19810103||广东省广州市珠江新城１号||||广州市||广东省||2||9||1||2||||81024567||13611128745||||||510000||中国||vivi@gdb.com.cn||陈二||光大银行||||8||广东省广州市东风路||||81034567||56000||510000||||1||5||3||22||8012||001||001||0||||||陈二||4||||0||||||||1||||||||||||||81036748||||||||||||||||||||||||-||||||||||||||||||广东省广州市珠江新城１号||||广州市||广东省||510000||||||||||||||||||||||99991231||||广东省||广州市||广州市珠江新城１号||人事||经理||广东省||广州市||||5||1||81022222||||||||1||||02||0||60||||1||||||||020||||||||||020||||020||||020||||||||||||||0032||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||||||||||||0||||||||||||||||||||||||||0||||||||||||||||||20110609||||||||||||||||||||||||||||||||||||||||||||||3619||10.2.37.240||164805||0||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||E||||||||||||||||||M||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||</VALUE>"
//				+"				</DATA>"
//				+"		</BARCODE>"

//循环填写报文体		
		for(Iterator<KeyedCollection> it =  iSendList.iterator(); it.hasNext(); ) {
			//KeyedCollection netReasonKcoll = new KeyedCollection();
        	KeyedCollection barinfo = it.next();
    		String bar_ID = (String) barinfo.get("yxTiaoXingMa");//影像条码
    		String bar_DID = (String) barinfo.get("tiaoXingMa");//数据条码
    		String bar_BANKID = (String) barinfo.get("SubBranch");//行所号
    		String bar_TABLECODE = (String) barinfo.get("yxTableCode");//影像表单代码
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
//    	     netReasonKcoll.addDataField("JinJianFailedMsg","网络问题发送失败");
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
        context.put("statusMsgNet", "网络问题发送失败");
        context.put("failedCountNet", "0");
		
		String wdURL =  LianaStandard.getSelfDefineSettingsValue("creditCardURL");
		PostMethod post = new PostMethod(wdURL);		
		try {
			post.setRequestEntity(new StringRequestEntity(input, null,"GBK"));
			Trace.log("发送到进件管理系统：", input);
		} catch (UnsupportedEncodingException e) {
			Trace.logError(Trace.COMPONENT_ACTION, "数据编码异常", e);
			throw e;
		}
				
		// 指定请求内容的类型
		post.setRequestHeader("Method", "POST " + "/GFJJ/SSJJservlet" + " HTTP/1.1");
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		HttpClient httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(120000);
		try {
			int result = httpclient.executeMethod(post);   //报文推送
		} catch (HttpException e) {
			Trace.logError(Trace.COMPONENT_ACTION, "post请求进件管理系统异常", e);
			throw e;
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_ACTION,"传送数据到进件管理系统异常",e);
			throw e;
		}

		InputStream resStream=null;
		try {
			resStream = post.getResponseBodyAsStream();   //获取返回流信息
		} catch (IOException e) {
			Trace.logError(Trace.COMPONENT_ACTION,"进件管理系统返回数据异常",e);
			throw e;
		}

		creditCardHttpClientAction doNetNodeAction = new creditCardHttpClientAction();
		doNetNodeAction.getReturnMsg(resStream,context);
		
		post.releaseConnection();		
	}
/*
 * 读取返回信息
 */
public void getReturnMsg(InputStream inputXml,Context context) throws Exception {
	try {
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(inputXml);
		
		System.out.println("接收到的报文"+document.asXML());
		String xpathStr ="//soapenv:Envelope/soapenv:Body/gateway:NoAS400/GFJJ/HEAD";
		Node HeadNode=(Node) document.selectSingleNode(xpathStr);
		
		
		IndexedCollection iLogList =  new IndexedCollection();
		
		if(HeadNode!=null)
		{
//			返回处理批次号	BATCHID	20
			String BATCHID="";
			BATCHID=HeadNode.valueOf("BATCHID");
			context.put("BATCHID",BATCHID);
			/*			
			 * <!--返回处理后状态-->
			 *  <!--状态:
			 *  0000{成功}
			 *  0001{失败}
			 -->*/
			String BATCHSTATUS="";
			BATCHSTATUS=HeadNode.valueOf("BATCHSTATUS");
			if(BATCHSTATUS.equals("0000")){
				BATCHSTATUS="1";
			}else{
				BATCHSTATUS="0";
			}
			context.put("BATCHSTATUS",BATCHSTATUS);
//			返回处理后批次描述	STATUSMSG	100
			String STATUSMSG="";
			STATUSMSG=HeadNode.valueOf("STATUSMSG");
			context.put("STATUSMSG",STATUSMSG);
			
			context.put("reSendType","1");
			context.put("dataStyl","01");
			
			if(! "0000".equals(BATCHSTATUS)){
				context.put("errorCode","ENL10000");
				context.put("errorMessage",STATUSMSG);
				Trace.logDebug( Trace.COMPONENT_MAPPING, "返回处理后状态" + BATCHSTATUS);
				Trace.logDebug( Trace.COMPONENT_MAPPING, STATUSMSG );
			}

			
			Node BARCODE = null;

			xpathStr ="//soapenv:Envelope/soapenv:Body/gateway:NoAS400/GFJJ/BODY/BARCODE";
			
	        for(Iterator<Node> it =  document.selectNodes(xpathStr).iterator(); it.hasNext(); ) {
	        	BARCODE = it.next();
				KeyedCollection errkcoll = new KeyedCollection();
				errkcoll.setAppend(true);				
//				ID:18位影像条码
				String ID = BARCODE.valueOf("@ID");
	        	errkcoll.addDataField("ID",ID);
	        	
//				DID:20为数据条码
	        	String DID=BARCODE.valueOf("DATA/DID");
	        	errkcoll.addDataField("tiaoXingMa",DID);
	        	
	        	/*
				 * 	STATUS:处理状态
				 *	B000 成功
				 *	B001 失败
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
				
//				返回信息
				String MSG=BARCODE.valueOf("DATA/MSG");
				errkcoll.addDataField("JinJianFailedMsg",MSG);
				
				iLogList.addDataElement(errkcoll);
			}
	        Trace.logDebug( Trace.COMPONENT_MAPPING, iLogList.toString() );
	        context.put("sendStatusList",iLogList);
		}		

			
	} catch (DocumentException e) {
		Trace.logError(Trace.COMPONENT_ACTION,"解析xml出错",e);
		throw e;
	}		
}
	


}
