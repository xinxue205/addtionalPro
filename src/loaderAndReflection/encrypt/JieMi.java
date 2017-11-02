/**
 * 
 */
package loaderAndReflection.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import loaderAndReflection.MyClassLoader;

/**
 * @author Administrator
 * @date 2010-11-6 ����4:16:37
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */
public class JieMi {

    public static void main(String[] args) throws Exception {

        // DES�㷨Ҫ����һ�������ε������Դ
        SecureRandom sr = new SecureRandom();
        // ����ܳ�����
        FileInputStream fi = new FileInputStream(new File("key.txt"));
        byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
        fi.read(rawKeyData);
        fi.close();
        // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����һ��SecretKey����
        SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        // Cipher����ʵ����ɽ��ܲ���
        Cipher cipher = Cipher.getInstance("DES");
        // ���ܳ׳�ʼ��Cipher����
        cipher.init(Cipher.DECRYPT_MODE, key, sr);
        // ���ڣ���ȡ���ݲ�����
        FileInputStream fi2 = new FileInputStream(new File("DigestPass.class"));
        byte encryptedData[] = new byte[fi2.available()];
        fi2.read(encryptedData);
        fi2.close();
        // ��ʽִ�н��ܲ���
        byte decryptedData[] = cipher.doFinal(encryptedData);
        // ��ʱ�����ݻ�ԭ��ԭ�е����ļ�
        // FileOutputStream fo = new FileOutputStream(new
        // File("DigestPass.class"));
        // fo.write(decryptedData);
        // �ý��ܺ�����ݼ����ಢӦ��
        MyClassLoader mcl = new MyClassLoader("E:/");
        Class cl = mcl.loadClass(decryptedData, "loaderAndReflection.encrypt.DigestPass");
        Class<?> clazz = new MyClassLoader("wxxlib").loadClass("loaderAndReflection.ClassLoaderAttachment");
        DigestPass dp = (DigestPass) cl.newInstance();
        dp.test();
    }
}
