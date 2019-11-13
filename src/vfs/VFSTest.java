package vfs;

import java.io.File;  

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.apache.commons.vfs2.FileChangeEvent;  
import org.apache.commons.vfs2.FileListener;  
import org.apache.commons.vfs2.FileName;  
import org.apache.commons.vfs2.FileObject;  
import org.apache.commons.vfs2.FileSystemException;  
import org.apache.commons.vfs2.FileSystemManager;  
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.events.ChangedEvent;
import org.apache.commons.vfs2.events.CreateEvent;
import org.apache.commons.vfs2.events.DeleteEvent;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;  
  
/** 
 * Hello world! 
 *  
 */  
public class VFSTest {  
    public static final Log log = LogFactory.getLog(VFSTest.class);  
  
    public static void main(String[] args) throws Exception {  
        FileSystemManager fsManager = null;  
        FileObject listendir = null;  
        try {  
            fsManager = VFS.getManager();  
            listendir = fsManager.resolveFile(new File("D:\\download\\vfs").getAbsolutePath());  
        } catch (FileSystemException e) {  
            log.error("�����ļ��г�����", e);  
            e.printStackTrace();  
        }  
          
        System.out.println(listendir.getName());  
          
        // ����һ�����������¼��������  
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
          
          
        fm.setRecursive(true); // ����Ϊ�������  
        fm.addFile(listendir); // ���Ӽ���ļ�  
        fm.start();  // ����������  
        
//        Thread.sleep(600000);
    }  
}
