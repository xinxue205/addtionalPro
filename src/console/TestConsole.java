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
 * @time 2015-6-29 ����3:28:28
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
            System.out.print("������һ���ַ�����");  
            String str = strin.readLine();  
              
            System.out.println("��һ����"+str);  
              
            System.out.println("������ڶ����ַ�����");  
            String str2 = strin.readLine();  
            System.out.println("��2����"+str2);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	static void test2(){
		Scanner sc = new Scanner(System.in);   
        System.out.println("�����һ��booleanֵ(true/false):");  
        if(sc.nextBoolean()){  
            System.out.println("���벼�������");  
        }else{  
            System.out.println("���벼�����ٵ�");  
        }  
          
          
        System.out.println("�����һ������:");  
        System.out.println("�������֣�"+sc.nextInt());  
  
        System.out.println("����һ���ַ���:");  
        System.out.println("�����ַ�����"+sc.next());  
  
        System.out.println("����һ��������:");  
        System.out.println("���볤���ͣ�"+sc.nextLong());
	}
}
