package encrypt;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

/**
* @author wxx
* @date 2014-2-14 上午10:18:28
* @version 1.0 wxx create
* @CopyRight (c) 2014 广州南天电脑系统有限公司
*/
public class DESEncrypter {
	public static final Logger log = Logger.getLogger("ThreeDesUtil"); // 日志记录器
	
	private final static String Algorithm = "DESede/CBC/NoPadding";//加密方法／运算模式／填充模式   
	private final static String AlgorithmECB = "DESede/ECB/NoPadding";//加密方法／运算模式／填充模式
	
	private String sMasterKey = "";    //加密主密钥
	private String sIvData = "" ; //加密初始化向量
	private byte []sMessage = null  ; //加密字符串
	
	/**
	 * 			 设备加密主密钥
	 * @param sTmpMasterKey
	 * 					加密主密钥
	 * @return
	 * 			false  主密钥格式错误   true 主密钥设置成功
	 */
	public boolean setMasterKey(String sTmpMasterKey){
		if ( sTmpMasterKey.length() != 16 ){
			log.info("主密钥长度错误，请输入16位的主密钥") ;
			return  false ;
		}
		sMasterKey = sTmpMasterKey + sTmpMasterKey.substring(0, 8);
		return true ;
	}
	
	/**
	 * 			 设备初始化向量
	 * @param sTmpMasterKey
	 * 					初始化向量
	 * @return
	 * 			false  初始化向量格式错误   true 初始化向量设置成功
	 */
	public boolean setIVKey(String sTmpIVKey){
		if ( sTmpIVKey.length() != 16 ){
			log.info("初始化向量长度错误，请输入16位的初始化向量") ;
			return  false ;
		}
		sIvData=sTmpIVKey;
		return true ;
	}
	
	/**
	 * 			 设置并格式化加解密数据,如果加解密数据不足8的倍数，则数据后补0x00 直到够8的倍数
	 * @param sTmpMessage
	 * 					加解密数据
	 * @param iType
	 * 		 	数据类型 0-10进制  1-16进制
	 * @return
	 * 			true 初始化向量设置成功
	 */
	public boolean setMessage(String sTmpMessage,int iType){
		if (iType == 0) {
			if (sTmpMessage.length() % 8 != 0) {
				int iMessLen = sTmpMessage.length();
				String sTmpString = "";
				for (int iIndex = 0; iIndex < 8 - iMessLen % 8; iIndex++) {
					sTmpString += "00";
				}
				sTmpMessage += new String(this.hexToBytes(sTmpString));
			}
			sMessage = sTmpMessage.getBytes() ;
		}else{
			if (sTmpMessage.length() % 16 != 0){
				int iMessLen = sTmpMessage.length();
				for (int iIndex = 0; iIndex < 16 - iMessLen % 16; iIndex++) {
					sTmpMessage += "00";
				}
			}
			sMessage = this.hexToBytes(sTmpMessage);
		}
		
		return true ;
	}
	

	/**  
	 * 3DES加密  使用CBC加密方式
	 * @return 加密后密文  
	 */
	public String encryptByCBC() {
		try {
			SecureRandom sr = new SecureRandom();
			DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			IvParameterSpec ips = new IvParameterSpec(this.hexToBytes(sIvData));
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, ips, sr);
			byte[] res = cipher.doFinal(sMessage);
			return byte2Hex(res);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("3DES加密异常:"+e.toString());
			return null;
		}
	}
	
	/**  
	 * 3DES加密 
	 * @return 加密后密文  
	 */
	public String encryptByECB() {
		try {
			sMasterKey = sMasterKey + sMasterKey.substring(0, 8);
			DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(AlgorithmECB);
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			byte[] res = cipher.doFinal(sMessage);
			return byte2Hex(res);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("3DES加密异常:"+e.toString());
			return null;
		}
	}

	/**
	 * 3DES解密
	 * @return 解密后密码
	 */
	public String decryptByCBC() {
		try {
			sMasterKey = sMasterKey + sMasterKey.substring(0, 8);
			SecureRandom sr = new SecureRandom();
			DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			IvParameterSpec ips = new IvParameterSpec(this.hexToBytes(sIvData));
			Cipher cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, securekey, ips, sr);
			byte[] res = cipher.doFinal(sMessage);
			return byte2Hex(res);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("解密异常:"+e.toString());
			return null;
		}
	}
	
	/**
	 * 3DES解密
	 * @return 解密后密码
	 */
	public String decryptByECB() {
		try {
			sMasterKey = sMasterKey + sMasterKey.substring(0, 8);
			DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(AlgorithmECB);
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			byte[] res = cipher.doFinal(sMessage);
			return byte2Hex(res);
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("3DES解密异常:"+e.toString());
			return null;
		}
	}
	
	/**
	 * 		字符串压缩,将两个字节的数据高位截掉，压缩成一个字节
	 * @param str
	 * 				原字符串
	 * @return
	 * 			压缩后的字符串
	 */
	public byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * 转换成十六进制字符串
	 */
	public String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	private char ConvertChar(char sSourceString) {
		if (sSourceString >= '0' && sSourceString <= '9')
			return (char) (sSourceString - 48);
		else if (sSourceString >= 'A' && sSourceString <= 'F')
			return (char) (sSourceString - 55);
		else if (sSourceString >= 'a' && sSourceString <= 'f')
			return (char) (sSourceString - 87);
		else
			return 0;
	}

	private char reConvertChar(char sSourceString) {
		if (sSourceString >= 0 && sSourceString <= 9)
			return (char) (sSourceString + 48);
		else if (sSourceString >= 10 && sSourceString <= 16)
			return (char) (sSourceString + 55);
		else
			return 0;
	}
	
	public String reConvertString(String sSourceString) {
		if (sSourceString.length() != 16)
			return null;
		int iCount = 0;
		char sArrSource[] = sSourceString.toCharArray();
		char sArrDest[] = new char[sSourceString.length() * 2];
		while (iCount < sSourceString.length()) {
			sArrDest[iCount * 2] = reConvertChar((char) (sArrSource[iCount] >> 4));
			sArrDest[iCount * 2 + 1] = reConvertChar((char) (sArrSource[iCount] & 0x0F));
			iCount++;
		}
		return new String(sArrDest);
	}

	public String ConvertString(String sSourceString) {
		if (sSourceString.length() %2 != 0)
			return null;
		int iCount = 0;
		char sArrSource[] = sSourceString.toCharArray();
		char sArrDest[] = new char[sSourceString.length() / 2];
		while (iCount < sSourceString.length() / 2) {
			sArrDest[iCount] = (char) (ConvertChar(sArrSource[iCount * 2]) << 4);
			sArrDest[iCount] |= ConvertChar(sArrSource[iCount * 2 + 1]);
			iCount++;
		}
		return new String(sArrDest);
	}


	
	public static void main(String[] args) {
		DESEncrypter threeDes = new DESEncrypter();
		
		/*System.out.println("------------------------- CBC方式加解密--------------------------");
		System.out.println("主密钥:[4406001001520304] 初始化向量:[0000440600100152] 数据:[888888881]");
		threeDes.setMasterKey("4406003001510916") ;
		threeDes.setIVKey("0000440600100152") ;
		threeDes.setMessage("888888881",0) ;
		String sEncryptString = threeDes.encryptByCBC();
		System.out.println("加密结果为:[" + sEncryptString + "]");
		String sDecryptString = threeDes.decryptByCBC();
		System.out.println("结密结果为:[" + sDecryptString + "]");*/
		
		System.out.println("-------------------------ECB方式加解密 --------------------------");
		String sMastKey = "2014ABCDDCBA2222";//密钥
		String sMsg = "1234567890123456";//要加密的串
		System.out.println("----测试加密----");
		System.out.println("主密钥:["+sMastKey+"] 数据:["+sMsg+"]");
		threeDes.setMasterKey(sMastKey) ;
		threeDes.setMessage(sMsg,1) ;
		String sEncryptString = threeDes.encryptByECB();
		System.out.println("加密结果为:[" + sEncryptString + "]");
		
		System.out.println("----测试解密----");
		String encryptedString = "B56B51FBC98BB8F3";
		System.out.println("密文:"+encryptedString);
		threeDes.setMessage(encryptedString, 1);
		String sDecryptString1 = threeDes.decryptByECB();
		System.out.println("解密结果为: 16-[" + sDecryptString1 + "] 10-[" + threeDes.ConvertString(sDecryptString1).trim() + "]");
	}
	
	
}
