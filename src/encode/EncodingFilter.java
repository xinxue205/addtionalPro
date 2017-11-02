/**
 * 
 */
package encode;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 下午3:38:48
 * @Description
 * @version 1.0 Shawn create
 */
public class EncodingFilter {

	/* sco unix + informix */
	public static String getStrFromPageSourceEncoding="ISO-8859-1";
	public static String getStrFromPageDestEncoding="GBK";

	public static String getStrFromDBSSourceEncoding="ISO-8859-1";
	public static String getStrFromDBSDestEncoding="GBK";

	public static String getStrFromFileSourceEncoding="ISO-8859-1";
	public static String getStrFromFileDestEncoding="GBK";
	

	/* windows + informix 
	public static String getStrFromPageSourceEncoding="ISO-8859-1";
	public static String getStrFromPageDestEncoding="GBK";

	public static String getStrFromDBSSourceEncoding="ISO-8859-1";
	public static String getStrFromDBSDestEncoding="GBK";

	public static String getStrFromFileSourceEncoding="ISO-8859-1";
	public static String getStrFromFileDestEncoding="ISO-8859-1";
	/**/
	
	public static void init()
	{
		/*
		//Encoding enc=Encoding.getInstance();
		getStrFromPageSourceEncoding=Encoding.getStrFromPageSourceEncoding;
		getStrFromPageDestEncoding=Encoding.getStrFromPageDestEncoding;

		getStrFromDBSSourceEncoding=Encoding.getStrFromDBSSourceEncoding;
		getStrFromDBSDestEncoding=Encoding.getStrFromDBSDestEncoding;

		getStrFromFileSourceEncoding=Encoding.getStrFromFileSourceEncoding;
		getStrFromFileDestEncoding=Encoding.getStrFromFileDestEncoding;
/*System.out.println(getStrFromPageSourceEncoding
+","+getStrFromPageDestEncoding
+","+getStrFromDBSSourceEncoding
+","+getStrFromDBSDestEncoding
+","+getStrFromFileSourceEncoding
+","+getStrFromFileDestEncoding);*/
	}
	/**
	 * 从页面上传递到程序
	 * @param str
	 * @return
	 */
	public static String getStrFromPage(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromPageDestEncoding
					,getStrFromPageSourceEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	public static String putStrToPage(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromPageSourceEncoding
					,getStrFromPageDestEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}

	public static String getStrFromDBS(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromDBSDestEncoding
					,getStrFromDBSSourceEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	public static String putStrToDBS(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromDBSSourceEncoding
					,getStrFromDBSDestEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}

	public static String getStrFromFile(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromFileDestEncoding
					,getStrFromFileSourceEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	public static String putStrToFile(String str)
	{
		init();
		try {
			return convertCharset(str,getStrFromFileSourceEncoding
					,getStrFromFileDestEncoding);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	/**
	 * 汉字内码转换
	 * @param str
	 * @param charset 目的编码
	 * @param sCharset 源编码
	 * @return
	 */
	
	public static String convertCharset(String str,String charset,String sCharset)
	throws Exception
	{
		if(str == null)return null;
		if(charset==null||sCharset==null)
		{
			throw new Exception("charset is null or sCharset is null");
		}
		if(charset.compareToIgnoreCase(sCharset)==0)return str;
		boolean b=checkCharset(str);
		if(b==true && 
			( charset.compareToIgnoreCase("iso8859-1")==0
			||charset.compareToIgnoreCase("iso-8859-1")==0)
		)
		{
			return str;
		}
		if(b==false && 
				( charset.compareToIgnoreCase("GBK")==0
				||charset.compareToIgnoreCase("GB2312")==0
				||charset.compareToIgnoreCase("GB18030")==0)
		    &&(sCharset.compareToIgnoreCase("ISO-8859-9")!=0))
		{
			return str;
		}
		try
		{
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes(sCharset);
			String temp = new String(temp_t,charset);
			return temp;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return "null";
	}
	/**
	 * 检查字符串的字符集，目前尚未在较大范围验证，如果将来出现异常，
	 * 可以考虑把这个函数关闭，目前的原理是判断该字符串是否出现>256
	 * 的字符，如果有判断为gb2312 或 gbk否则认为编码是需要进行转换的
	 * iso8859-1,经过这个判断后，getStr和putStr两个函数可以调用多次，
	 * 效果跟一次相同。
	 * @param String str 输入字符串
	 * @return boolean true --- iso8859-1
	 * @return boolean false --- gbk
	 */
	public static boolean checkCharset(String str)
	{
		if(str==null)return false;
//System.out.println(str);
		for(int i=0;i<str.length();i++)
		{
//if(i==0)
//System.out.println("str["+i+"]="+(int)str.charAt(i));
			if((int)str.charAt(i)>256)//gb2312
			{
				return false;
			}
		}
		return true;
	}
public static String test(String str,String charset,String sCharset)
{
	System.out.println(sCharset+"==>"+charset);
	try
	{
		String temp_p = str;
		byte[] temp_t = temp_p.getBytes(sCharset);
		String temp = new String(temp_t,charset);
		return temp;

	}
	catch(Exception e)
	{
		e.printStackTrace();
		return str;
	}
}
/**
 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
 * 纵横软件制作中心雨亦奇2003.08.01
 * @param s 原文件名
 * @return 重新编码后的文件名
 */
public static String toUtf8String(String s) {
StringBuffer sb = new StringBuffer();
for (int i=0;i<s.length();i++) {
    char c = s.charAt(i);
    if (c >= 0 && c <= 255) {
	sb.append(c);
    } else {
	byte[] b;
	try {
	    b = Character.toString(c).getBytes("utf-8");
	} catch (Exception ex) {
	    System.out.println(ex);
	    b = new byte[0];
	}
	for (int j = 0; j < b.length; j++) {
	    int k = b[j];
	    if (k < 0) k += 256;
	    sb.append("%" + Integer.toHexString(k).
	    toUpperCase());
	}
    }
}
return sb.toString();
}
	public static void main(String[] args)
	{
		String s="/源代码清单-20040409.xls";
		s=EncodingFilter.toUtf8String(s);
	
		byte[] t=s.getBytes();
		int len=t.length;
		System.out.println("len="+len+",");
		for(int i=0;i<len;i++)
		{
			System.out.print(" "+(byte)s.charAt(i));
		}
		System.out.println("");
	}
}
