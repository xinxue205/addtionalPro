/**
 * 
 */


import java.math.BigInteger;

/**
 * @author wuxinxue
 * @time 2015-7-23 ÉÏÎç9:40:15
 * @copyright sinobest
 */
public class TestEncrypt {
	private static final int RADIX = 16;
	  private static final String SEED = "0933910847463829827159347601486730416058";
	  
	  public static void main(String[] args) {
		System.out.println(encryptPassword("SJPT_KETTLE"));
//		System.out.println(decryptPassword("2be98afc86aa7f2e4cb79ce77cb97bcce"));
		System.out.println(decryptPassword("2be98afc82ef4b8bb89339e44e1c3fd89"));
	}

	  public static final String encryptPassword( String password ) {
		    if ( password == null ) {
		      return "";
		    }
		    if ( password.length() == 0 ) {
		      return "";
		    }

		    BigInteger bi_passwd = new BigInteger( password.getBytes() );

		    BigInteger bi_r0 = new BigInteger( SEED );
		    BigInteger bi_r1 = bi_r0.xor( bi_passwd );

		    return bi_r1.toString( RADIX );
		  }
	  
	  public static final String decryptPassword(String encrypted){
		  if ( encrypted == null ) {
		      return "";
		    }
		    if ( encrypted.length() == 0 ) {
		      return "";
		    }

		    BigInteger bi_confuse = new BigInteger( SEED );

		    try {
		      BigInteger bi_r1 = new BigInteger( encrypted, RADIX );
		      BigInteger bi_r0 = bi_r1.xor( bi_confuse );

		      return new String( bi_r0.toByteArray() );
		    } catch ( Exception e ) {
		      return "";
		    }
	  }
}
