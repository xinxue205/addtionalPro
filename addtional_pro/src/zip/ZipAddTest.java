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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import file.FileListGetterTest;
 
public class ZipAddTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ZipAddTest.class);
 
    private static final int BUFFER_SIZE = 10 * 1024;

    public static void main(String[] args) throws Exception {
        String sourceZip = "C:\\Users\\admin\\Desktop\\新建文件夹\\worker.zip";
        String sourceFolderParentPath = "C:\\Users\\admin\\Desktop\\新建文件夹";
        String sourceFolderName = "plugins";
        addFilesToZip(sourceZip, sourceFolderParentPath, sourceFolderName);
 
    }
    
    public static void addFilesToZip(String sourceZip, String sourceFolderParentPath, String sourceFolderName) throws Exception {
		File tmpFile = new File("worker_tmp.zip");
    	ZipOutputStream targetZipOS = new ZipOutputStream(new FileOutputStream(tmpFile));
    	List<String> list = new ArrayList();
    	listSubDirFiles(list, new File(sourceFolderParentPath+File.separator+sourceFolderName), sourceFolderParentPath);
    	
    	File sourceZipFile = new File(sourceZip);
    	copyZip(targetZipOS, sourceZipFile);
    	for (int i = 0; i < list.size(); i++) {
    		try {
    			addFileToStream(sourceFolderParentPath, list.get(i), targetZipOS);
    		} catch(Exception e) {
    			logger.warn("添加压缩文件"+list.get(i)+"出错(跳过处理)："+e);
    		}
    	}
    	
    	targetZipOS.close();
    	sourceZipFile.delete();
    	tmpFile.renameTo(new File(sourceZip));
	}
	
    public static void copyZip(ZipOutputStream targetZipOS, File sourceZip) throws Exception{
    	ZipFile sourceZipFile = new ZipFile(sourceZip);
    	Enumeration<? extends ZipEntry> entries = sourceZipFile.entries();
    	while (entries.hasMoreElements()) {
    		ZipEntry e = entries.nextElement();
    		targetZipOS.putNextEntry(new ZipEntry(e.getName()));
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
    
    public static void listSubDirFiles(List list , File folder, String baseDir) {
    	if (!folder.isDirectory()) {
			list.add(baseDir+File.separator+folder);
			return;
		}
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				listSubDirFiles(list, file, baseDir);
			} else {
				String filePath = file.getAbsolutePath();
				String subString = filePath.substring(baseDir.length()+1);
				list.add(subString);
			}
		}
	}
}