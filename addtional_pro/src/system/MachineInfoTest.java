package system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MachineInfoTest {
	public static void main1(String[] args) {  
	    // TODO Auto-generated method stub  
	    try {  
	        long start = System.currentTimeMillis();  
	        Process process = Runtime.getRuntime().exec(  
	        		new String[] { "wmic", "cpu", "get", "ProcessorId" });  
	        process.getOutputStream().close();  
	        Scanner sc = new Scanner(process.getInputStream());  
	        String property = sc.next();  
	        String serial = sc.next();  
	        System.out.println(property + ": " + serial);  
	        System.out.println("time:" + (System.currentTimeMillis() - start));  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	  
	}
	
	public static String getHdSerialInfo() {

        String line = "";
        String HdSerial = "";//������� Ӳ�����к�
        try {
         Process proces = Runtime.getRuntime().exec("cmd /c dir c:");//��ȡ�����в���
         BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream(),"gbk"));

         while ((line = buffreader.readLine()) != null) {
          if (line.indexOf("������к��� ") != -1) {  //��ȡ��������ȡӲ�����к�

           HdSerial = line.substring(line.indexOf("������к��� ") + "������к��� ".length(), line.length());
           break;
          }
         }

        } catch (IOException e) {
         e.printStackTrace();
        }

        return HdSerial;
       }

  /**
   * @param args
   */
  public static void main(String[] args) {
      System.out.println(getHdSerialInfo());

  }
}
