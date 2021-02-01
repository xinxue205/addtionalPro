package zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.RandomStringUtils;

import file.FileListGetterTest;
 
public class ZipAddTest {
 
    private static final int BUFFER_SIZE = 10 * 1024;

    public static void main(String[] args) throws Exception {
        String sourceZip = "C:\\Users\\admin\\Desktop\\新建文件夹\\1.zip";
        String sourceFolderParentPath = "C:\\Users\\admin\\Desktop\\新建文件夹";
        String sourceFolderName = "plugins";
        addFilesToZip(sourceZip, sourceFolderParentPath, sourceFolderName);
 
    }
    
    public static void addFilesToZip(String sourceZip, String sourceFolderParentPath, String sourceFolderName) throws Exception {
    	String tmpFileName = RandomStringUtils.randomAlphanumeric(10)+".zip";
    	ZipOutputStream targetZipOS = new ZipOutputStream(new FileOutputStream(tmpFileName));
    	List<String> list = new ArrayList();
    	FileListGetterTest.listSubDirFiles(list, new File(sourceFolderParentPath+File.separator+sourceFolderName), sourceFolderParentPath);
    	
    	File sourceZipFile = new File(sourceZip);
    	copyZip(targetZipOS, sourceZipFile);
    	for (int i = 0; i < list.size(); i++) {
    		addFileToStream(sourceFolderParentPath, list.get(i), targetZipOS);
    	}
    	
    	targetZipOS.close();
    	sourceZipFile.delete();
    	new File(tmpFileName).renameTo(new File(sourceZip));
    }
    
    public static void copyZip(ZipOutputStream targetZipOS, File sourceZip) throws Exception{
    	ZipFile sourceZipFile = new ZipFile(sourceZip);
    	Enumeration<? extends ZipEntry> entries = sourceZipFile.entries();
    	while (entries.hasMoreElements()) {
    		ZipEntry e = entries.nextElement();
    		targetZipOS.putNextEntry(e);
    		if (!e.isDirectory()) {
    			copy(sourceZipFile.getInputStream(e), targetZipOS);
    		}
    		targetZipOS.closeEntry();
    	}
    	sourceZipFile.close();
    }
    
    public static void copy(InputStream input, OutputStream output) throws IOException {
    	int bytesRead;
    	byte[] buffer = new byte[BUFFER_SIZE];
    	while ((bytesRead = input.read(buffer))!= -1) {
    		output.write(buffer, 0, bytesRead);
    	}
    }

    public static void addFileToStream(String baseDir, String addFile, ZipOutputStream targetZipOS) throws Exception{
    	BufferedInputStream origin = new BufferedInputStream( new FileInputStream(baseDir+ File.separator+addFile), BUFFER_SIZE);
    	targetZipOS.putNextEntry(new ZipEntry(addFile));
    	int count;
    	byte[] data = new byte[BUFFER_SIZE];
    	while ((count = origin.read(data)) != -1) {
    		targetZipOS.write(data, 0, count);
    	}
    	origin.close();
    	targetZipOS.closeEntry();
    }
}