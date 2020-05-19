/**
 * 
 */
package encode;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 ����3:38:48
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
	 * ��ҳ���ϴ��ݵ�����
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
	 * ��������ת��
	 * @param str
	 * @param charset Ŀ�ı���
	 * @param sCharset Դ����
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
	 * ����ַ������ַ�����Ŀǰ��δ�ڽϴ�Χ��֤��������������쳣��
	 * ���Կ��ǰ���������رգ�Ŀǰ��ԭ�����жϸ��ַ����Ƿ����>256
	 * ���ַ���������ж�Ϊgb2312 �� gbk������Ϊ��������Ҫ����ת����
	 * iso8859-1,��������жϺ�getStr��putStr�����������Ե��ö�Σ�
	 * Ч����һ����ͬ��
	 * @param String str �����ַ���
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
 * ���ļ����еĺ���תΪUTF8����Ĵ�,�Ա�����ʱ����ȷ��ʾ�����ļ���.
 * �ݺ������������������2003.08.01
 * @param s ԭ�ļ���
 * @return ���±������ļ���
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
		String s="/Դ�����嵥-20040409.xls";
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
