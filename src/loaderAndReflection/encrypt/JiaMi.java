/**
 * 
 */
package loaderAndReflection.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author Administrator
 * @date 2010-11-6 ����4:15:41
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */
public class JiaMi {

    public static void main(String[] args) throws Exception {

        // DES�㷨Ҫ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ����ܳ�����
        FileInputStream fi = new FileInputStream(new File("key.txt"));
        byte rawKeyData[] = new byte[fi.available()];
        fi.read(rawKeyData);
        fi.close();
        // ��ԭʼ�ܳ����ݴ���DESKeySpec����
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����һ��SecretKey����
        SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        // Cipher����ʵ����ɼ��ܲ���
        Cipher cipher = Cipher.getInstance("DES");
        // ���ܳ׳�ʼ��Cipher����
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        // ���ڣ���ȡҪ���ܵ��ļ�����
        FileInputStream fi2 = new FileInputStream(new File("DigestPass.class"));
        byte data[] = new byte[fi2.available()];
        fi2.read(data);
        fi2.close();
        // ��ʽִ�м��ܲ���
        byte encryptedData[] = cipher.doFinal(data);
        // �ü��ܺ�����ݸ���ԭ�ļ�
        FileOutputStream fo = new FileOutputStream(new File("DigestPass.class"));
        fo.write(encryptedData);
        fo.close();
    }
}

