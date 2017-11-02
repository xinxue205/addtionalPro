/**
 * 
 */
package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
 
public class AutoFTPFile {
	static String lastIp = "58.61.202.115";
	static String thisIp = "";
	static String tmpPath = "";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static void main(String[] args) {
		//先删除临时目录下所有文件,并新建文件夹
		init();
		
		boolean runFlag = true;
		int count = 1;
		while (runFlag){
			System.out.println("------------------------开始处理,时间"+sdf.format(new Date()));
			int i = getNewIP();
			System.out.println("当前外网ip："+thisIp+"；之前外网ip："+lastIp+",时间"+sdf.format(new Date()));
			
			//查询成功，如果不一致则生成登录信息文件，并上传
			if (i == 0 && !lastIp.equals(thisIp)){
				//生成登录信息文件
				createLogInfoFile(thisIp,count);
				
				//上传登录信息文件
				uploadLogInfoFile(count);
				count++;
				lastIp = thisIp;
			}
			
			try {
				Thread.sleep(90000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 */
	private static void init() {
		// TODO Auto-generated method stub
		try {
			Runtime.getRuntime().exec("cmd /c del /Q "+tmpPath+"*");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmpPath = "D:\\Mirtools\\loginfoFielTmp\\"+sdf.format(new Date()).substring(0,8)+"\\";
		File file = new File(tmpPath);
		if(!file.exists()) file.mkdirs();
	}

	/**
	 * 
	 */
	private static void uploadLogInfoFile(int count) {
		System.out.println("开始上传登录信息文件,时间"+sdf.format(new Date()));
		FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
        	Long beginTime = System.currentTimeMillis();
            ftpClient.connect("98.126.208.92",21); 
            ftpClient.login("xinxue", "xinxue205"); 

            File srcFile = new File(tmpPath+count+".txt"); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory("/xinxue/web/"); 
            ftpClient.setBufferSize(8192); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("7t.txt", fis); 
            System.out.println("上传成功，耗时："+(System.currentTimeMillis()-beginTime)+"ms");
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) {
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        }
        System.out.println("登录信息文件上传完成,时间"+sdf.format(new Date()));
	}

	/**
	 * 
	 */
	private static void createLogInfoFile(String newIp, int num) {
		System.out.println("开始登录信息文件生成,时间"+sdf.format(new Date()));
		String content = getFileContent(newIp);
		writeFile(content,tmpPath+num+".txt");
		System.out.println("完成登录信息文件生成,时间"+sdf.format(new Date()));
	}

	/**
	 * @param newIp
	 * @return
	 */
	private static String getFileContent(String newIp) {
		// TODO Auto-generated method stub
		StringBuffer loginfo = new StringBuffer();
		loginfo.append("[◆◆◆◆重金打造-火爆开区◆◆◆◆]\r");
		loginfo.append("╔═╦═══════════╗=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║今║风网传奇一区[电信]正在开放║=风网传奇一区|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║日║风网传奇一区[电信]正在开放║=风网传奇一区|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║新║风网传奇一区[网通]正在开放║=风网传奇一区|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║区║风网传奇一区[网通]正在开放║=风网传奇一区|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("╚═╩═══════════╝=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("[◆◆◆◆重金打造-火爆开区◆◆◆◆]\r");
		loginfo.append("╔═════════════╗=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║云封挂技术②○①④经典之作║=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║绝对精彩：独家打造闪亮登场║=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("║公平，公正，真正的经典传奇║=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("╚═════════════╝=风网传奇|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("\r");
		loginfo.append("[weburl]\r");
		loginfo.append("公告窗口=http://www.diaosx.com/\r");
		loginfo.append("官方主页=http://www.diaosx.com\r");
		loginfo.append("游戏介绍=http://www.diaosx.com\r");
		loginfo.append("购卡充值=http://www.diaosx.com/\r");
		loginfo.append("退弹地址=\r");
		loginfo.append("\r");
		loginfo.append("[Updata]\r");
		loginfo.append("强制更新=0\r");
		loginfo.append("版本号=100\r");
		loginfo.append("更新地址=\r");
		loginfo.append("\r");
		loginfo.append("[config]\r");
		loginfo.append("快捷方式=风网传奇登陆器\r");
		loginfo.append("公告文本=云查杀封挂启动—开挂永久封号处理\r");
		loginfo.append("防火墙类型=0\r");
		loginfo.append("多开限制=2\r");
		loginfo.append("图片代码=0,11|1,8|2,6|3,9|4,10|5,7|6,5|7,9|8,0|9,1|10,4|11,3|12,2|13,11\r");
		loginfo.append("处理方式=0\r");
		loginfo.append("\r");
		loginfo.append("[patch]\r");
		loginfo.append("必选补丁=\r");
		loginfo.append("补丁1=\r");
		loginfo.append("\r");
		loginfo.append("[机器码]\r");
		loginfo.append(";机器码列表=AG4D5F4AD56GF465DA|AD4FG54AD56GF4DA6GF5   用|分割开\r");
		loginfo.append("机器码列表=\r");
		return loginfo.toString();
	}

	static int getNewIP(){
		try {
			URL url = new URL("http://apps.game.qq.com/comm-htdocs/ip/get_ip.php");
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"GBK"));
			String ipInfo = reader.readLine().trim();
			String ipAdd = ipInfo.split(":")[1];
			thisIp = ipAdd.substring(1,ipAdd.length()-2);
			reader.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	static void writeFile(String content, String filePath){
		try {
			File file = new File(filePath);
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath)));
			bw.newLine();				
			bw.write(content);				
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
}
