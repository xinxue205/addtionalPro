package doc;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SFTPUtil {
	
	private static final int timeout = 60000;

	public static ChannelSftp getChannel(String host, int port, String user, String passwd) throws Exception {
		Session session = new JSch().getSession(user, host, port);  
		session.setPassword(passwd);
		Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setTimeout(timeout); // 设置timeout时间
		session.connect();
		Channel channel=session.openChannel("sftp"); // 打开SFTP通道 
		channel.connect(); // 建立SFTP通道的连接
		return (ChannelSftp)channel;  
	}
	
	@SuppressWarnings("unused")
	public static void getFileList(ChannelSftp sftpChannel, List list, String path) throws Exception{
		sftpChannel.pwd();
		Iterator it = sftpChannel.ls(path).iterator();
	    while (it.hasNext())
	    {
	    	ChannelSftp.LsEntry ls = (ChannelSftp.LsEntry)it.next();
	    	String fileName = ls.getFilename();
//	    	System.out.println(path+"/"+fileName + ":" + ls.getAttrs().getAtimeString()+ " " +ls.getAttrs().getMtimeString());
	    	if ((".".equals(fileName)) || ("..".equals(fileName))) {
	    		continue;
	    	} else if (fileName.startsWith(".")) {
	    		continue;
	    	}
	    	
	    	if(ls.getAttrs().isDir()){
	    		getFileList(sftpChannel, list, path+"/"+fileName);
	    	} else {
	    		String doc_name = ls.getFilename();
	    		long doc_size = ls.getAttrs().getSize();
	    		long doc_atime = ls.getAttrs().getATime()*1000L;
	    		long doc_mtime = ls.getAttrs().getMTime()*1000L;
	    		String doc_desc = "";
				list.add(path+"/"+fileName);
	    	}
	    }
	}
	
	public static void downloadFile(ChannelSftp sftpChannel, String path, String fileName, String localTMPath) throws Exception {
		sftpChannel.get(path+"/"+fileName, localTMPath);
	}
}
