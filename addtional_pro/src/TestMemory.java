
public class TestMemory {  
    public static void main(String[] args) {  
        System.out.println("free:" + Runtime.getRuntime().freeMemory() / 1024  
                / 1024);  
        System.out.println("total:" + Runtime.getRuntime().totalMemory() / 1024  
                / 1024);  
        System.out.println("max:" + Runtime.getRuntime().maxMemory() / 1024  
                / 1024);  
        System.out.println("=============");  
        long t = System.currentTimeMillis();  
        try {  
            Thread.sleep(3000);  
        } catch (Exception ee) {  
            ee.printStackTrace();  
        }  
        String[] aaa = new String[2000000];  
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);  
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);  
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);  
        System.out.println("=============");  
        try {  
            Thread.sleep(3000);  
        } catch (Exception ee) {  
            ee.printStackTrace();  
        }  
        for (int i = 0; i < 2000000; i++) {  
            aaa[i] = new String("aaa");  
        }  
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);  
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);  
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);  
        System.out.println("=============");  
        try {  
            Thread.sleep(30000);  
        } catch (Exception ee) {  
            ee.printStackTrace();  
        }  
    }  
} 
