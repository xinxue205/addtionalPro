/**
 * 
 */
package loaderAndReflection.encrypt;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author Administrator
 * @date 2010-11-6 ����4:14:42
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */
class Key {

    private String keyName;

    public Key(String keyName) {
        this.keyName = keyName;
    }

    public void createKey(String keyName) throws Exception {

        // ����һ�������ε������Դ��DES�㷨��Ҫ
        SecureRandom sr = new SecureRandom();
        // ��DES�㷨����һ��KeyGenerator����
        KeyGenerator kg = KeyGenerator.getInstance("DES");
        // ��ʼ������Կ������,ʹ�����ȷ������Կ����
        kg.init(sr);
        // �����ܳ�
        SecretKey key = kg.generateKey();
        // ��ȡ��Կ����
        byte rawKeyData[] = key.getEncoded();
        // ����ȡ����Կ���ݱ��浽�ļ��У�������ʱʹ��
        FileOutputStream fo = new FileOutputStream(new File(keyName));
        fo.write(rawKeyData);
    }

    public static void main(String args[]) {
        try {
            new Key("key.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
