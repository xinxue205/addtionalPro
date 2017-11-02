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
		//��ɾ����ʱĿ¼�������ļ�,���½��ļ���
		init();
		
		boolean runFlag = true;
		int count = 1;
		while (runFlag){
			System.out.println("------------------------��ʼ����,ʱ��"+sdf.format(new Date()));
			int i = getNewIP();
			System.out.println("��ǰ����ip��"+thisIp+"��֮ǰ����ip��"+lastIp+",ʱ��"+sdf.format(new Date()));
			
			//��ѯ�ɹ��������һ�������ɵ�¼��Ϣ�ļ������ϴ�
			if (i == 0 && !lastIp.equals(thisIp)){
				//���ɵ�¼��Ϣ�ļ�
				createLogInfoFile(thisIp,count);
				
				//�ϴ���¼��Ϣ�ļ�
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
		System.out.println("��ʼ�ϴ���¼��Ϣ�ļ�,ʱ��"+sdf.format(new Date()));
		FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
        	Long beginTime = System.currentTimeMillis();
            ftpClient.connect("98.126.208.92",21); 
            ftpClient.login("xinxue", "xinxue205"); 

            File srcFile = new File(tmpPath+count+".txt"); 
            fis = new FileInputStream(srcFile); 
            //�����ϴ�Ŀ¼ 
            ftpClient.changeWorkingDirectory("/xinxue/web/"); 
            ftpClient.setBufferSize(8192); 
            ftpClient.setControlEncoding("GBK"); 
            //�����ļ����ͣ������ƣ� 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("7t.txt", fis); 
            System.out.println("�ϴ��ɹ�����ʱ��"+(System.currentTimeMillis()-beginTime)+"ms");
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP�ͻ��˳���", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) {
                e.printStackTrace(); 
                throw new RuntimeException("�ر�FTP���ӷ����쳣��", e); 
            } 
        }
        System.out.println("��¼��Ϣ�ļ��ϴ����,ʱ��"+sdf.format(new Date()));
	}

	/**
	 * 
	 */
	private static void createLogInfoFile(String newIp, int num) {
		System.out.println("��ʼ��¼��Ϣ�ļ�����,ʱ��"+sdf.format(new Date()));
		String content = getFileContent(newIp);
		writeFile(content,tmpPath+num+".txt");
		System.out.println("��ɵ�¼��Ϣ�ļ�����,ʱ��"+sdf.format(new Date()));
	}

	/**
	 * @param newIp
	 * @return
	 */
	private static String getFileContent(String newIp) {
		// TODO Auto-generated method stub
		StringBuffer loginfo = new StringBuffer();
		loginfo.append("[���������ؽ����-�𱬿�����������]\r");
		loginfo.append("�X�T�j�T�T�T�T�T�T�T�T�T�T�T�[=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U��U��������һ��[����]���ڿ��ŨU=��������һ��|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U�ըU��������һ��[����]���ڿ��ŨU=��������һ��|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U�¨U��������һ��[��ͨ]���ڿ��ŨU=��������һ��|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U���U��������һ��[��ͨ]���ڿ��ŨU=��������һ��|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�^�T�m�T�T�T�T�T�T�T�T�T�T�T�a=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("[���������ؽ����-�𱬿�����������]\r");
		loginfo.append("�X�T�T�T�T�T�T�T�T�T�T�T�T�T�[=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U�Ʒ�Ҽ����ڡ�٢ܾ���֮���U=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U���Ծ��ʣ����Ҵ��������ǳ��U=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�U��ƽ�������������ľ��䴫��U=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("�^�T�T�T�T�T�T�T�T�T�T�T�T�T�a=��������|"+newIp+"|3003|1122334455|0\r");
		loginfo.append("\r");
		loginfo.append("[weburl]\r");
		loginfo.append("���洰��=http://www.diaosx.com/\r");
		loginfo.append("�ٷ���ҳ=http://www.diaosx.com\r");
		loginfo.append("��Ϸ����=http://www.diaosx.com\r");
		loginfo.append("������ֵ=http://www.diaosx.com/\r");
		loginfo.append("�˵���ַ=\r");
		loginfo.append("\r");
		loginfo.append("[Updata]\r");
		loginfo.append("ǿ�Ƹ���=0\r");
		loginfo.append("�汾��=100\r");
		loginfo.append("���µ�ַ=\r");
		loginfo.append("\r");
		loginfo.append("[config]\r");
		loginfo.append("��ݷ�ʽ=���������½��\r");
		loginfo.append("�����ı�=�Ʋ�ɱ����������������÷�Ŵ���\r");
		loginfo.append("����ǽ����=0\r");
		loginfo.append("�࿪����=2\r");
		loginfo.append("ͼƬ����=0,11|1,8|2,6|3,9|4,10|5,7|6,5|7,9|8,0|9,1|10,4|11,3|12,2|13,11\r");
		loginfo.append("����ʽ=0\r");
		loginfo.append("\r");
		loginfo.append("[patch]\r");
		loginfo.append("��ѡ����=\r");
		loginfo.append("����1=\r");
		loginfo.append("\r");
		loginfo.append("[������]\r");
		loginfo.append(";�������б�=AG4D5F4AD56GF465DA|AD4FG54AD56GF4DA6GF5   ��|�ָ\r");
		loginfo.append("�������б�=\r");
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
