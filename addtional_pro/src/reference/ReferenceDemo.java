package reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
    public class ReferenceDemo {
        public static void main(String[] args) {
            // ǿ����
            String str=new String("abc"); 
            SoftReference<String> softRef=new SoftReference<String>(str);  // ������   
            str = null;  // ȥ��ǿ����
            System.gc(); // �������������л���
            System.out.println(softRef.get());        
            // ǿ����
           String abc = new String("123");
            WeakReference<String> weakRef=new WeakReference<String>(abc); // ������    
            abc = null;  // ȥ��ǿ����
            System.gc(); // �������������л���
            System.out.println(weakRef.get());
        }
    }