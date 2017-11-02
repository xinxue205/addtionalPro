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
* @date 2014-2-14 ����10:18:28
* @version 1.0 wxx create
* @CopyRight (c) 2014 �����������ϵͳ���޹�˾
*/
public class DESEncrypter {
	public static final Logger log = Logger.getLogger("ThreeDesUtil"); // ��־��¼��
	
	private final static String Algorithm = "DESede/CBC/NoPadding";//���ܷ���������ģʽ�����ģʽ   
	private final static String AlgorithmECB = "DESede/ECB/NoPadding";//���ܷ���������ģʽ�����ģʽ
	
	private String sMasterKey = "";    //��������Կ
	private String sIvData = "" ; //���ܳ�ʼ������
	private byte []sMessage = null  ; //�����ַ���
	
	/**
	 * 			 �豸��������Կ
	 * @param sTmpMasterKey
	 * 					��������Կ
	 * @return
	 * 			false  ����Կ��ʽ����   true ����Կ���óɹ�
	 */
	public boolean setMasterKey(String sTmpMasterKey){
		if ( sTmpMasterKey.length() != 16 ){
			log.info("����Կ���ȴ���������16λ������Կ") ;
			return  false ;
		}
		sMasterKey = sTmpMasterKey + sTmpMasterKey.substring(0, 8);
		return true ;
	}
	
	/**
	 * 			 �豸��ʼ������
	 * @param sTmpMasterKey
	 * 					��ʼ������
	 * @return
	 * 			false  ��ʼ��������ʽ����   true ��ʼ���������óɹ�
	 */
	public boolean setIVKey(String sTmpIVKey){
		if ( sTmpIVKey.length() != 16 ){
			log.info("��ʼ���������ȴ���������16λ�ĳ�ʼ������") ;
			return  false ;
		}
		sIvData=sTmpIVKey;
		return true ;
	}
	
	/**
	 * 			 ���ò���ʽ���ӽ�������,����ӽ������ݲ���8�ı����������ݺ�0x00 ֱ����8�ı���
	 * @param sTmpMessage
	 * 					�ӽ�������
	 * @param iType
	 * 		 	�������� 0-10����  1-16����
	 * @return
	 * 			true ��ʼ���������óɹ�
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
	 * 3DES����  ʹ��CBC���ܷ�ʽ
	 * @return ���ܺ�����  
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
			log.error("3DES�����쳣:"+e.toString());
			return null;
		}
	}
	
	/**  
	 * 3DES���� 
	 * @return ���ܺ�����  
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
			log.error("3DES�����쳣:"+e.toString());
			return null;
		}
	}

	/**
	 * 3DES����
	 * @return ���ܺ�����
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
			log.error("�����쳣:"+e.toString());
			return null;
		}
	}
	
	/**
	 * 3DES����
	 * @return ���ܺ�����
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
			log.error("3DES�����쳣:"+e.toString());
			return null;
		}
	}
	
	/**
	 * 		�ַ���ѹ��,�������ֽڵ����ݸ�λ�ص���ѹ����һ���ֽ�
	 * @param str
	 * 				ԭ�ַ���
	 * @return
	 * 			ѹ������ַ���
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
	 * ת����ʮ�������ַ���
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
		
		/*System.out.println("------------------------- CBC��ʽ�ӽ���--------------------------");
		System.out.println("����Կ:[4406001001520304] ��ʼ������:[0000440600100152] ����:[888888881]");
		threeDes.setMasterKey("4406003001510916") ;
		threeDes.setIVKey("0000440600100152") ;
		threeDes.setMessage("888888881",0) ;
		String sEncryptString = threeDes.encryptByCBC();
		System.out.println("���ܽ��Ϊ:[" + sEncryptString + "]");
		String sDecryptString = threeDes.decryptByCBC();
		System.out.println("���ܽ��Ϊ:[" + sDecryptString + "]");*/
		
		System.out.println("-------------------------ECB��ʽ�ӽ��� --------------------------");
		String sMastKey = "2014ABCDDCBA2222";//��Կ
		String sMsg = "1234567890123456";//Ҫ���ܵĴ�
		System.out.println("----���Լ���----");
		System.out.println("����Կ:["+sMastKey+"] ����:["+sMsg+"]");
		threeDes.setMasterKey(sMastKey) ;
		threeDes.setMessage(sMsg,1) ;
		String sEncryptString = threeDes.encryptByECB();
		System.out.println("���ܽ��Ϊ:[" + sEncryptString + "]");
		
		System.out.println("----���Խ���----");
		String encryptedString = "B56B51FBC98BB8F3";
		System.out.println("����:"+encryptedString);
		threeDes.setMessage(encryptedString, 1);
		String sDecryptString1 = threeDes.decryptByECB();
		System.out.println("���ܽ��Ϊ: 16-[" + sDecryptString1 + "] 10-[" + threeDes.ConvertString(sDecryptString1).trim() + "]");
	}
	
	
}
