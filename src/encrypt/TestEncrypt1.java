package encrypt;

public class TestEncrypt1 {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        System.out.println(decrypt("137%128%143%145%124%144%135%143%76%"));  
          
        System.out.println(encrypt("netvault1"));  
    }  
      
    /** 
       *用户名解密 
       *@param ssoToken 字符串 
       *@return String 返回加密字符串 
      */  
      public static String decrypt(String ssoToken)  
      {  
        try  
        {  
          String name = new String();  
          java.util.StringTokenizer st=new java.util.StringTokenizer(ssoToken,"%");  
          while (st.hasMoreElements()) {  
            int asc =  Integer.parseInt((String)st.nextElement()) - 27;  
            name = name + (char)asc;  
          }  
  
          return name;  
        }catch(Exception e)  
        {  
          e.printStackTrace() ;  
          return null;  
        }  
      }  
  
      /** 
       *用户名加密 
       *@param ssoToken 字符串 
       *@return String 返回加密字符串 
      */  
      public static String encrypt(String ssoToken)  
      {  
        try  
        {  
          byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");  
          String name = new String();  
         // char[] _ssoToken = ssoToken.toCharArray();  
          for (int i = 0; i < _ssoToken.length; i++) {  
              int asc = _ssoToken[i];  
              _ssoToken[i] = (byte) (asc + 27);  
              name = name + (asc + 27) + "%";  
          }  
          return name;  
        }catch(Exception e)  
        {  
          e.printStackTrace() ;  
          return null;  
        }  
      }  
} 