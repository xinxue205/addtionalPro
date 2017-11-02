package machine;

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
        String HdSerial = "";//定义变量 硬盘序列号
        try {
         Process proces = Runtime.getRuntime().exec("cmd /c dir c:");//获取命令行参数
         BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream(),"gbk"));

         while ((line = buffreader.readLine()) != null) {
          if (line.indexOf("卷的序列号是 ") != -1) {  //读取参数并获取硬盘序列号

           HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length(), line.length());
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
