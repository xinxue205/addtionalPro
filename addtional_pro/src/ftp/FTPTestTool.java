package ftp;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 
import java.util.Properties;

/** 
* Apache commons-net 试用一把，看看FTP客户端工具做的好用不 
* 
* @author : leizhimin，2008-8-20 14:00:38。<p> 
*/ 
public class FTPTestTool { 
	static int port = 22;
	
    public static void main(String[] args) {
    	String ips = "192.168.14.43,192.168.14.44,192.168.14.45";
    	String users = "exPlatSIT,exPlatSIT,exPlatSIT";
    	String passs = "Ggjs@123,Ggjs@123,Ggjs@123";
    	String srcPath = "D:/work/workspace-sinobest/sjjhpt-new/sjjh-worker/lib";
    	String fileNames = "kettle-core-5.3.0.0-213.jar,kettle-engine-5.3.0.0-213.jar,sinobest-sjjh-worker.jar";
    	String destPaths = "/home/exPlatSIT/apps/worker/lib,/home/exPlatSIT/apps/worker/lib,/home/exPlatSIT/apps/worker/";
        testUploads(ips, users, passs, srcPath, fileNames, destPaths); 
    } 

    private static void testUploads(String ips, String users, String passs,
			final String filePath, String fileNames, String destPaths) {
		final String[] ipss = ips.split(",");
		final String[] userss = users.split(",");
		final String[] passss = passs.split(",");
		
		if(ipss.length!=userss.length){
			System.out.println("用户名个数和ip个数不一致");
			return;
		}
		if(ipss.length!=passss.length){
			System.out.println("密码个数和ip个数不一致");
			return;
		}
		
		final String[] fileNamess = fileNames.split(",");
		final String[] destPathss = destPaths.split(",");
		if(fileNamess.length!=destPathss.length){
			System.out.println("目标路径个数和文件个数不一致");
			return;
		}
		
		for (int i = 0; i < ipss.length; i++) {
			final String ip = ipss[i];
			final String user = userss[i];
			final String pass = passss[i];
			
			new Thread(){
				public void run() {
					ChannelSftp sftp = null;
					Session sshSession = null;
					Channel channel = null;
					try {
						System.out.println("connect to ["+ ip + "] user ["+ user  + "--" + pass +"]");
						JSch jsch = new JSch();
						jsch.getSession(user, ip, port);
						sshSession = jsch.getSession(user, ip, port);
						sshSession.setPassword(pass);
						Properties sshConfig = new Properties();
						sshConfig.put("StrictHostKeyChecking", "no");
						sshSession.setConfig(sshConfig);
						sshSession.connect();
						channel = sshSession.openChannel("sftp");
						channel.connect();
						sftp = (ChannelSftp) channel;

			            for (int j = 0; j < fileNamess.length; j++) {
			            	String fileName = fileNamess[j];
			            	String destPath = destPathss[j];
			            	String fullPath = filePath+"/"+fileName;
				        	System.out.println("put file ["+ fullPath + "] to ["+ destPath +"]");
			            	File srcFile = new File(fullPath); 
			            	sftp.cd(destPath);
			            	sftp.put(new FileInputStream(srcFile), fileName);
						}
			        } catch (Exception e) { 
			            e.printStackTrace(); 
			        } finally{
			        	channel.disconnect();
			        	sshSession.disconnect();
			        	sftp.disconnect();
			        }
					System.out.println("put file to "+ ip + " success!!!");
				};
			}.start();
		}
	}

	/** 
     * FTP上传单个文件测试 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
            ftpClient.connect("128.128.97.32"); 
            ftpClient.login("atmvh", "atmv1234"); 

            File srcFile = new File("H:\\ftp\\select.js"); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory("/home/ap/atmvh/users/wxx/"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("select.js", fis); 
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
    } 

}