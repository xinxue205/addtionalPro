package vfs;

import java.io.File;
import java.net.URI;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.commons.vfs2.FileChangeEvent;  
import org.apache.commons.vfs2.FileListener;  
import org.apache.commons.vfs2.FileName;  
import org.apache.commons.vfs2.FileObject;  
import org.apache.commons.vfs2.FileSystemException;  
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.UserAuthenticator;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.events.ChangedEvent;
import org.apache.commons.vfs2.events.CreateEvent;
import org.apache.commons.vfs2.events.DeleteEvent;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;  
  
/** 
 * Hello world! 
 *  
 */  
public class VFS_FTPTest2 {  
    public static final Log log = LogFactory.getLog(VFS_FTPTest2.class);  
  
    public static void main(String[] args) throws Exception {  
    	FileSystemOptions opts = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking( opts, "no");
        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
        StandardFileSystemManager manager = new StandardFileSystemManager();
//    	SftpFileProvider fp = new SftpFileProvider();
//    	manager.addProvider("sftp", fp);
    	manager.init();


//        FileObject listendir = manager.resolveFile("sftp://sdi:sdi123@192.168.11.108/home/sdi/test2/", opts);
//        FileSystemOptions opts = new FileSystemOptions();
//        UserAuthenticator auth =  new StaticUserAuthenticator(null, "sdi", "sdi123");
//        DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
//        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
//        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
//        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
        FileObject    listendir = manager.resolveFile("sftp://sdi:sdi123@192.168.11.108/test2/", opts);
     
//        Create remote file object
//        FileSystemManager fsManager = VFS.getManager();  
//        FileObject    listendir = fsManager.resolveFile(new File("D:\\download\\vfs").getAbsolutePath());  
          
        System.out.println(listendir.getName());  
        
//        FileObject localFile = manager.resolveFile(new File("D:\\download\\vfs\\sdi.version").getAbsolutePath());
//        localFile.copyFrom(listendir, Selectors.SELECT_SELF);
          
        // 定义一个监视器及事件处理程序  
        DefaultFileMonitor fm = new DefaultFileMonitor(new FileListener() {  
            public void fileCreated(FileChangeEvent event) throws Exception {  
                monitor(event);  
            }  
  
            public void fileDeleted(FileChangeEvent event) throws Exception {  
                monitor(event);  
            }  
  
            public void fileChanged(FileChangeEvent event) throws Exception {  
                monitor(event);  
            }  
              
            private void monitor(FileChangeEvent event) {  
            	FileObject fileObject = event.getFile();  
            	FileName fileName = fileObject.getName();  
            	if(event instanceof DeleteEvent) {
            		System.out.println(fileName.toString()+ " file delete");  
            	} else if(event instanceof ChangedEvent) {
            		System.out.println(fileName.toString()+ " file modify");  
            	} else {
            		System.out.println(fileName.toString()+ " file create");  
            	}
            }  
        });  
//          
//          
        fm.setRecursive(true); // 设置为级联监控  
        fm.addFile(listendir); // 增加监控文件  
        fm.start();  // 启动监视器  
        
        Thread.sleep(600 * 1000);
    }  
}
