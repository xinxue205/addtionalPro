package ftp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
 
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
 
public class SFTPUtil {
	static String user = "sdi";
	static String passwd = "sdi123";
	static String host = "192.168.11.108";
	static int timeout = 60000;
	static int port = 22;
	
	private static final Log log = LogFactory.getLog(SFTPUtil.class);
	/**从sftp中后去文件内容
	 * @param path      远程文件目录
	 * @param fileName  远程文件
	 * @return          以  #@# 为分隔的字符串，每一个部分为一行数据
	 * @throws JSchException  
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getLs("/home/sdi/test2"));
	}
	
	public static String downLoadFileTxt(String path,String fileName) throws JSchException,  Exception{
		log.info("down_load_file:"+path+"/"+fileName);
		Session session = null;
		ChannelSftp sftpChannel = null;
		InputStream is = null;
		BufferedReader buf = null;
		StringBuffer content = new StringBuffer();
		try {
			session = getSession();
			
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    sftpChannel.cd(path);
		
			is = sftpChannel.get(fileName);
			if(is==null){
				throw new Exception("文件不存在");
			}else{
				buf = new BufferedReader(new InputStreamReader(is,"UTF-8"));
				String tcontent = null;
				
				while((tcontent = buf.readLine())!=null){
					if(StringUtils.isNotEmpty(tcontent)){
						content.append("\n").append(tcontent);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(is!=null)is.close();
			if(buf!=null)buf.close();
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
		//去掉最后部分
		if(content.toString().startsWith("\n")){
			return content.toString().replaceFirst("\n", "");
		}else{
			return content.toString();
		}
	}
	
	/**
	 * @param 
	 * @param sFile         老文件
	 * @param dFile         先文件
	 * @return
	 * @throws JSchException
	 * @throws Exception
	 */
	public static boolean rename(String sFile,String dFile) throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    sftpChannel.rename(sFile, dFile);
		    return true;
		} catch (Exception e) {
			log.error("",e);
			return false;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
	}
	/**创建文件目录
	 * @param   配置文件中的sftp前缀例如 DJ_SFTP_USER 就等于  =DJ
	 * @param paths   形成靠左/靠前的先创建
	 * @return
	 * @throws JSchException
	 * @throws Exception
	 */
	public static boolean getMkdirs(List<String> paths) throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    int size = paths.size();
		    for(int i=0;i<size;i++){
		    	String path = paths.get(i);
		    	try {
		    		if(fileExist(path)){
		    			sftpChannel.mkdir(path);
		    		}
		    		if(fileExist(path)) {
		    			sftpChannel.mkdir(path);
		    		}
				} catch (Exception e) {
					log.warn(e.getMessage(),e);
				}
		    }
		    return true;
		} catch (Exception e) {
			throw e;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
	}
	/**
	 * 判断文件夹是否存在
	 * 存在  false
	 * 不存在  true
	 */
	public static boolean fileExist(String path) throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    try {
		    	 Vector ls = sftpChannel.ls(path);
		    	 return false;
			} catch (Exception e) {
				return true;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
	}
	public static void upLoadFile(String rpath,String rFileName,byte[] fileData) throws Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    
		    sftpChannel.cd(rpath);
		    OutputStream os = sftpChannel.put(rFileName);
		    os.write(fileData);
		    os.flush();
		    os.close();
		} catch (Exception e) {
			throw e;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
	}
	public static boolean cdPath(String path) throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		final List fs = new ArrayList();
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    log.debug("cd="+path);
		    sftpChannel.cd(path);
		} catch (Exception e) {
			log.error("",e);
			return false;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
		return true;
	}
	/**查看当前目录下所有文件夹以及文件
	 * @param 
	 * @return
	 * @throws JSchException
	 * @throws Exception
	 */
	public static List<String> getLs(String path) throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		List fs = new ArrayList();
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    //sftpChannel.cd(path);
		    log.debug("ls_pwd="+sftpChannel.pwd());
		    getFileList(sftpChannel, fs, path);
		} catch (Exception e) {
			throw e;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
		 return fs;
	}
	/**查看当前目录
	 * @param 
	 * @return
	 * @throws JSchException
	 * @throws Exception
	 */
	public static String getPwd() throws JSchException,  Exception{
		Session session = null;
		ChannelSftp sftpChannel = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    String pwd = sftpChannel.pwd();
		    log.debug("pwd="+pwd);
		    return pwd;
		} catch (Exception e) {
			throw e;
		}finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
	}
	
	/**连接ftp
	 * @param   配置文件中的sftp前缀例如 DJ_SFTP_USER 就等于  =DJ
	 * @return
	 */
	public static Session getSession(){
		JSch jsch=new JSch(); 
		Session session = null;
		try {
			//从properties配置文件中获取服务器参数，自己定义
			
			log.debug("user>>"+user);
			log.debug("passwd>>"+passwd);
			log.debug("host>>"+host);
			log.debug("host>>"+host);
			session=jsch.getSession(user, host, port);  
			session.setPassword(passwd);
			Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setTimeout(timeout); // 设置timeout时间
			session.connect();
			return session;
		} catch (JSchException e) {
			e.printStackTrace();
			if(session!=null && session.isConnected()){
				session.disconnect();
			}
			return null;
		} 
	}
	/**
	 * 删除文件夹及其子文件
	 * @param  类型 例如 DJ
	 * @param directory 上一级文件夹
	 * @param desdir 目标文件夹
	 */
	public static void deleteAll(String directory,String desdir) {
		Session session = null;
		ChannelSftp sftpChannel = null;
	    try {
	    	session = getSession();
	    	Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
	    	channel.connect();
	    	sftpChannel=(ChannelSftp)channel;
	    	//获取删除目录下的所有文件，要先删除目录下的所有文件再删除文件夹
//	    	Vector fileList = null;
//	    	try {
//				fileList = sftpChannel.ls(directory+"/"+desdir);
//			} catch (Exception e) {
//				log.debug(directory+"/"+desdir+",该目录不存在");
//				return;
//			}
		    List<String> list= new ArrayList();
		    getFileList(sftpChannel, list, directory+"/"+desdir);
		    sftpChannel.cd(directory+"/"+desdir);
		    for (int i = 0; i < list.size(); i++) {
		    	
		    	sftpChannel.rm(list.get(i));
		    }
		    sftpChannel.cd("../");
	    	sftpChannel.rmdir(desdir);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }finally{
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
    }
	/**
	 * 下载文件
	 * @param   类型 例如 DJ
	 * @param directory ftp上的文件夹
	 * @param downloadFile 下载的目标文件
	 * @param saveDirectory 下载保存的路径
	 * @throws Exception
	 */
	public static void download(String directory, String downloadFile, String saveDirectory)
		    throws Exception
		  {
		Session session = null;
		ChannelSftp sftpChannel = null;
		FileOutputStream fileOutputStream = null;
		try {
			String saveFile = saveDirectory + "//" + downloadFile;
			session = getSession();
	    	Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
	    	channel.connect();
	    	sftpChannel=(ChannelSftp)channel;
	    	sftpChannel.cd(directory);
		    File file = new File(saveFile);
		    fileOutputStream = new FileOutputStream(file);
		    sftpChannel.get(downloadFile, fileOutputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fileOutputStream!=null){fileOutputStream.close();}
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
		   
	 }
	
	public static void getFileList(ChannelSftp sftpChannel, List list, String path) throws Exception{
	    Vector fileList = sftpChannel.ls(path);
		Iterator it = fileList.iterator();
	    while (it.hasNext())
	    {
	    	ChannelSftp.LsEntry ls = (ChannelSftp.LsEntry)it.next();
	    	String fileName = ls.getFilename();
	    	System.out.println(path+"/"+fileName + ":" + ls.getAttrs().getAtimeString()+ " " +ls.getAttrs().getMtimeString());
	    	if ((".".equals(fileName)) || ("..".equals(fileName))) {
	    		continue;
	    	} else if (fileName.startsWith(".")) {
	    		System.out.println("跳过隐藏文件或目录");
	    		continue;
	    	}
	    	
	    	if(ls.getAttrs().isDir()){
	    		getFileList(sftpChannel, list, path+"/"+fileName);
	    	} else {
	    		list.add(fileName);
	    	}
	    }
	}
	/**
	 * 下载文件，返回InputStream
	 */
	public static byte[] downLoadFileIo(String path,String fileName) throws JSchException,  Exception{
		log.info("下载文件，down_load_file:"+path+"/"+fileName);
		Session session = null;
		ChannelSftp sftpChannel = null;
		InputStream is = null;
		byte[] bs = null;
		try {
			session = getSession();
			Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
			channel.connect(); // 建立SFTP通道的连接
		    sftpChannel=(ChannelSftp)channel;  
		    sftpChannel.cd(path);
			is = sftpChannel.get(fileName);
			bs = readInputStream(is);
		} catch (Exception e) {
			throw e;
		}finally{
			if(is!=null)is.close();
			if(sftpChannel!=null)sftpChannel.disconnect();
			if(session!=null) session.disconnect();
		}
		return bs;
	}
	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static  byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}
