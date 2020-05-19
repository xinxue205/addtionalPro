/**
 * 
 */
package encrypt;

import java.util.UUID;

import com.datech.crypto.adv.AdvConst;
import com.datech.crypto.adv.Symmetric;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-9-19 下午5:01:36
 * @Description
 * @version 1.0 Shawn create
 */
public class AesCipher {
	public AesCipher()
    {
    }

    public static void main(String args[])
        throws Exception
    {
       // String s = (new StringBuilder("UASS-SSO|")).append(getRandomString()).toString();
    	String appCode = "sdi1@123";//(new StringBuilder(String.valueOf("ATMVH"))).append("|").append(getRandomString()).toString();
        String password = "sdi1";//"82d1cea67b92452fadcba9f386104b09";
        password = lengthRevise(password);
		String serc = encryptAndParseHexStr(appCode, password);
        System.out.println(serc);
        String s2 = decryptAndParseStr(serc, password);
        System.out.println(s2);
    }

    private static String lengthRevise(String password) {
		if(password.length()>=32) {
			password = password.substring(0,32);
		} else {
			int len = 32 - password.length();
			for (int i = 0; i < len; i++) {
				password += "0";
			}
		}
		return password;
	}

	/**
	 * @return
	 */
	private static Object getRandomString() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
	}

	public static String decryptAndParseStr(String content, String password)
        throws Exception
    {
        if(password == null || password.length() != 32)
        {
            throw new Exception("\u5BC6\u94A5\u957F\u5EA6\u5FC5\u987B\u4E3A32\u4F4D!");
        } else
        {
            byte bContent[] = parseHexStr2Byte(content);
            byte b[] = decrypt(bContent, password);
            return new String(b);
        }
    }

    public static String encryptAndParseHexStr(String content, String password)
        throws Exception
    {
        if(password == null || password.length() != 32)
        {
            throw new Exception("\u5BC6\u94A5\u957F\u5EA6\u5FC5\u987B\u4E3A32\u4F4D!");
        } else
        {
            byte buff[] = encrypt(content.getBytes(), password);
            return parseByte2HexStr(buff);
        }
    }

    public static byte[] encrypt(byte content[], String password)
        throws Exception
    {
        if(password == null || password.length() != 32)
        {
            throw new Exception("\u5BC6\u94A5\u957F\u5EA6\u5FC5\u987B\u4E3A32\u4F4D!");
        } else
        {
            byte key16[] = password.substring(0, 16).getBytes();
            byte iv16[] = password.substring(16).getBytes();
            Symmetric symm = new Symmetric();
            symm.symmetricInit(1, AdvConst.ALGO_SYMM_AES_CBC, key16, iv16);
            byte resp[] = symm.symmetricUpdate(addPadBlock16(content));
            symm.symmetricFinal();
            return resp;
        }
    }

    public static byte[] decrypt(byte content[], String password)
        throws Exception
    {
        if(password == null || password.length() != 32)
        {
            throw new Exception("\u5BC6\u94A5\u957F\u5EA6\u5FC5\u987B\u4E3A32\u4F4D!");
        } else
        {
            byte key16[] = password.substring(0, 16).getBytes();
            byte iv16[] = password.substring(16).getBytes();
            Symmetric symm = new Symmetric();
            symm.symmetricInit(0, AdvConst.ALGO_SYMM_AES_CBC, key16, iv16);
            byte resp[] = symm.symmetricUpdate(content);
            symm.symmetricFinal();
            return delPadBlock16(resp);
        }
    }

    private static byte[] addPadBlock16(byte inData[])
    {
        int nPlainLength = inData.length;
        int nPadLen = 16 - nPlainLength % 16;
        int PaddedDataLen = nPlainLength + nPadLen;
        byte padedData[] = new byte[PaddedDataLen];
        System.arraycopy(inData, 0, padedData, 0, nPlainLength);
        for(int i = 0; i < nPadLen; i++)
            padedData[nPlainLength + i] = (byte)nPadLen;

        return padedData;
    }

    private static byte[] delPadBlock16(byte inData[])
    {
        int padlen = inData[inData.length - 1];
        for(int i = 1; i < padlen; i++)
            if(padlen != inData[inData.length - 1 - i])
                return null;

        byte outData[] = new byte[inData.length - padlen];
        System.arraycopy(inData, 0, outData, 0, inData.length - padlen);
        return outData;
    }
    
    static byte[] parseHexStr2Byte(String hexStr)
    {
        if(hexStr.length() < 1)
            return null;
        byte result[] = new byte[hexStr.length() / 2];
        for(int i = 0; i < hexStr.length() / 2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }

        return result;
    }
    
    public static String parseByte2HexStr(byte buf[])
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 255);
            if(hex.length() == 1)
                hex = (new StringBuilder(String.valueOf('0'))).append(hex).toString();
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }
}
