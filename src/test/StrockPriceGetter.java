package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

/**
 * @author popular
 *
 */
public class StrockPriceGetter {
	String sOrURL = "http://hq.sinajs.cn/list=";

	public static void main(String[] args) {
		StrockPriceGetter t = new StrockPriceGetter();
		//t.continuelyGetPrice("600196",3);
		t.getStockPrice("600196");
	}
	
	private void getStockPrice(String stockCode) {
		String aInfos[] = getCurrPrice(stockCode);
		System.out.println(aInfos[0]+":"+aInfos[3]);
	}
		

	/**
	 * 连续取股票价格
	 * @param: stockCode股票代码；separateTime间隔秒数
	 */
	public void continuelyGetPrice(String stockCode, long separateTime){
		while(true){
			String aInfos[] = getCurrPrice(stockCode);
			System.out.println(aInfos[0]+":"+aInfos[3]);
			try {
				Thread.sleep(separateTime*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	String[] getCurrPrice(String sCode){
		String sType = "sz";
		if(sCode.startsWith("6")){
			sType = "sh";
		}
		String sSendUrl = sOrURL + sType + sCode;
		PostMethod post = new PostMethod(sSendUrl);
		try {
			post.setRequestEntity(new StringRequestEntity("", null,"GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpClient httpclient = new HttpClient();
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(120000);
		int result = 0;
		try {
			result = httpclient.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(result>0){
			System.out.println();
		}
		String sRes=null;
		try {
			sRes = post.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String sInfo = sRes.substring(sRes.indexOf("\"")+1, sRes.length()-3);
		String[] aInfos = sInfo.split(",");
		
		return aInfos;	
	}
}
