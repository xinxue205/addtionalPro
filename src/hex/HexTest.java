package hex;

public class HexTest {
	/** 
     * @Title:bytes2HexString 
     * @Description:�ֽ�����ת16�����ַ��� 
     * @param b 
     *            �ֽ����� 
     * @return 16�����ַ��� 
     * @throws 
     */  
    public static String bytes2HexString(byte[] b) {  
        StringBuffer result = new StringBuffer();  
        String hex;  
        for (int i = 0; i < b.length; i++) {  
            hex = Integer.toHexString(b[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            result.append(hex.toUpperCase());  
        }  
        return result.toString();  
    }  
  
    /** 
     * @Title:hexString2Bytes 
     * @Description:16�����ַ���ת�ֽ����� 
     * @param src 
     *            16�����ַ��� 
     * @return �ֽ����� 
     * @throws 
     */  
    public static byte[] hexString2Bytes(String src) {  
        int l = src.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer  
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;  
    }  
  
    /** 
     * @Title:string2HexString 
     * @Description:�ַ���ת16�����ַ��� 
     * @param strPart 
     *            �ַ��� 
     * @return 16�����ַ��� 
     * @throws 
     */  
    public static String string2HexString(String strPart) {  
        StringBuffer hexString = new StringBuffer();  
        for (int i = 0; i < strPart.length(); i++) {  
            int ch = (int) strPart.charAt(i);  
            String strHex = Integer.toHexString(ch);  
            hexString.append(strHex);  
        }  
        return hexString.toString();  
    }  
  
    /** 
     * @Title:hexString2String 
     * @Description:16�����ַ���ת�ַ��� 
     * @param src 
     *            16�����ַ��� 
     * @return �ֽ����� 
     * @throws 
     */  
    public static String hexString2String(String src) {  
        String temp = "";  
        for (int i = 0; i < src.length() / 2; i++) {  
            temp = temp  
                    + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),  
                            16).byteValue();  
        }  
        return temp;  
    }  
      
    /** 
     * @Title:char2Byte 
     * @Description:�ַ�ת���ֽ�����char-->integer-->byte 
     * @param src 
     * @return 
     * @throws 
     */  
    public static Byte char2Byte(Character src) {  
        return Integer.valueOf((int)src).byteValue();  
    }  
      
        /** 
     * @Title:intToHexString 
     * @Description:10��������ת��16���� 
     * @param a ת������ 
     * @param len ռ���ֽ��� 
     * @return 
     * @throws 
     */  
    private static String intToHexString(int a,int len){  
        len<<=1;  
        String hexString = Integer.toHexString(a);  
        int b = len -hexString.length();  
        if(b>0){  
            for(int i=0;i<b;i++)  {  
                hexString = "0" + hexString;  
            }  
        }  
        return hexString;  
    }  
    
    /** 
	 * @Title: intStringToHexString
	 * @Description:10�������ִ�ת��16�������ִ� 
	 * @return 
	 * @throws 
	 */  
	private static String intStringToHexString(String intStr){  
		 String temp = "";  
	     for (int i = 0; i < intStr.length() / 3; i++) {
	    	 String tmp = Integer.toHexString(Integer.valueOf(intStr.substring(i * 3, i * 3 + 3)));
	         temp = temp + tmp;
	     }  
	     return temp;  
	}
      
      
    public static void main(String args[]) {  
    	System.out.println(string2HexString("a"));
    	String src = "119120120051";
        System.out.println(intStringToHexString(src));  
        System.out.println(hexString2String(intStringToHexString(src)));
    }
}
