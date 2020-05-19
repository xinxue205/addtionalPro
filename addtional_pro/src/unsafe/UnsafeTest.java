package unsafe;

import java.lang.reflect.Field;  
import java.util.Arrays;  
import sun.misc.Unsafe;  
  
public class UnsafeTest {  
    private static int byteArrayBaseOffset;  
    private static int a = 11;
    private int aa = 1111;
  
    public static void main(String[] args) throws SecurityException,  
            NoSuchFieldException, IllegalArgumentException,  
            IllegalAccessException {  
    	UnsafeTest ut = new UnsafeTest();
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");  
        theUnsafe.setAccessible(true);  
        Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);  
  
        System.out.println(UNSAFE.getInt(UnsafeTest.class, UNSAFE.staticFieldOffset(UnsafeTest.class.getDeclaredField("a"))));
        long objectOffset = UNSAFE.objectFieldOffset(UnsafeTest.class.getDeclaredField("aa"));
        System.out.println(UNSAFE.getInt(ut, objectOffset));
        
        byte[] data = new byte[10];  
        System.out.println(Arrays.toString(data));  
        byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);  
        System.out.println(byteArrayBaseOffset);  
        UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);  
        UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);  
        System.out.println(Arrays.toString(data));  
    }  
}
