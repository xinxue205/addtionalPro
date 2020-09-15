package ssh;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;



/**
 *
 *  storm 远程调用脚本..
 * @author chenwen
 *
 */
public class SSHTest {

	private static String uip="192.168.11.12";
	private static int port=22;
	private static String user="root";//"sdi";
	private String pwd="85574999";

	public static void main(String[] args) throws Exception {
		exec("java -version");
	}
	
	public static void main1(String[] args) throws Exception {
//		exec("cat -n /home/sdi/test108/worker117/logs/25841796338700264/135222-25841796338700264-worker117.log | tail -n 1000");
		String allPath = "/home/sdi/test108/worker117/logs/25841796338700264/135222-25841796338700264-worker117.log";
		String fileName = "135222-25841796338700264-worker117.log";
//		String allPath = "/home/grid/runWordCount.sh";
//		String fileName = "runWordCount.sh";
		Session session = new JSch().getSession(user, uip, port);
		InputStream in=downLoad(session, allPath, fileName);
        //先创建文件路径 4
        File file = new File(fileName);
        if(!file.exists()){
     	   file.createNewFile();
     	}
        //1.从其他主机下载的文件放在服务端
        FileWriter fw = new FileWriter(fileName);		           		        	     		           
        char[] a=new char[1024];
	       InputStreamReader isr=new InputStreamReader(in);
	       int length=0;
	       while((length=isr.read(a))!=-1){
	    	  fw.write(a, 0, length);
	       }
	      in.close();
	      fw.close();
	      
	      session.disconnect();
	}
	
	/**
     * 执行脚本
     *
     * @param cmds
     * @return
     * @throws Exception
     */
    public static boolean exec(String cmds) throws Exception {
    	JSch jsch = new JSch();
		Session session = null;
		ChannelExec channel = null;
		try {
			session = jsch.getSession(user, uip, port);
			UserInfo info = new SSHTest().new MyUserInfo();
			session.setUserInfo(info);
			session.connect();

			channel = (ChannelExec) session.openChannel("exec");
         
			//InputStream in = channel.getInputStream(); 
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream(), "UTF-8"));
			channel.setCommand(cmds);  
			channel.setErrStream(System.err);  
			channel.connect();  
			//result = IOUtils.toString(in,"UTF-8");
			String content;
            while ((content = in.readLine()) != null) {
				System.out.println(content);
			}
		} catch (JSchException | IOException e) {
			return false;
		} finally {
			if (channel != null && !channel.isClosed()) {
				channel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return true;
    }

	public class MyUserInfo implements UserInfo, UIKeyboardInteractive {

		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public String getPassword() {
			return pwd;
		}

		@Override
		public boolean promptPassphrase(String arg0) {
			return true;
		}

		@Override
		public boolean promptPassword(String arg0) {
			return true;
		}

		@Override
		public boolean promptYesNo(String arg0) {
			return true;
		}

		@Override
		public void showMessage(String arg0) {
		}

		@Override
		public String[] promptKeyboardInteractive(String arg0, String arg1, String arg2, String[] arg3,
				boolean[] arg4) {
			return null;
		}
	}
	
	public static InputStream downLoad(Session session, String path,String name) throws IOException, SftpException, JSchException {
		ChannelSftp channel = null;
		ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
		InputStream	stream=null;
		//try {
			UserInfo info = new SSHTest().new MyUserInfo();
			session.setUserInfo(info);
			session.connect();

			channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect(); 
			//resp.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(name,"utf-8"));
			
			/*try {
				OutputStream os=resp.getOutputStream();*/
			 stream =channel.get(path);
			 /* byte[] b=new byte[1024];
			  int len=0;
			  while( (len=stream.read(b))>0){					 
				  os.write(b, 0, len);
			  }
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
			   stream.close();
			}*/
			
		/*} catch ( JSchException e) {
			log.info(e.getMessage());
			
		} finally {
			if ((channel != null) && (!channel.isClosed())) {
				channel.disconnect();
			}
			if ((session != null) && (session.isConnected())) {
				session.disconnect();
			}
		}*/
		return stream;
	}

}

