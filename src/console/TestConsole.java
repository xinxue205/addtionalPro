package console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author wuxinxue
 * @time 2015-6-29 下午3:28:28
 * @copyright hnisi
 */
public class TestConsole {
	public static void main(String[] args) {
		test1();
//		test2();
	}
	
	static void test1(){
		try {  
            BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
            System.out.print("请输入一个字符串：");  
            String str = strin.readLine();  
              
            System.out.println("第一个："+str);  
              
            System.out.println("请输入第二个字符串：");  
            String str2 = strin.readLine();  
            System.out.println("第2个："+str2);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	static void test2(){
		Scanner sc = new Scanner(System.in);   
        System.out.println("输入第一个boolean值(true/false):");  
        if(sc.nextBoolean()){  
            System.out.println("输入布尔：真的");  
        }else{  
            System.out.println("输入布尔：假的");  
        }  
          
          
        System.out.println("输入第一个数字:");  
        System.out.println("输入数字："+sc.nextInt());  
  
        System.out.println("输入一个字符串:");  
        System.out.println("输入字符串："+sc.next());  
  
        System.out.println("输入一个长整型:");  
        System.out.println("输入长整型："+sc.nextLong());
	}
}
