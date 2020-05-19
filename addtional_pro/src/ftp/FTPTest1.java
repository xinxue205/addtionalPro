package ftp;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 

import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 

/** 
* Apache commons-net 试用一把，看看FTP客户端工具做的好用不 
* 
* @author : leizhimin，2008-8-20 14:00:38。<p> 
*/ 
public class FTPTest1 { 
    public static void main(String[] args) { 
        testUpload(); 
        testDownload(); 
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

    /** 
     * FTP下载单个文件测试 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 

        try { 
        	ftpClient.connect("128.128.97.32"); 
            ftpClient.login("atmvh", "atmv1234"); 

            String remoteFileName = "/home/ap/atmvh/users/wxx/select.js"; 
            fos = new FileOutputStream("H:\\ftp\\select2.js"); 

            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.retrieveFile(remoteFileName, fos); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 
}