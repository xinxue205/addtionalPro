package file;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.lowagie.text.Document;  
import com.lowagie.text.pdf.PdfCopy;  
import com.lowagie.text.pdf.PdfImportedPage;  
import com.lowagie.text.pdf.PdfReader;  

public class PDFCombin {


    public static void main(String[] args) {  
    	String dest = "D:\\BaiduNetdiskDownload\\顾子明\\2019-上半年\\new\\new.pdf";
        String fromPath = "D:\\BaiduNetdiskDownload\\顾子明\\2019-上半年\\";  
        mergePdfFiles(fromPath, dest);  
    } /* 
         * * 合並pdf文件 * * @param files 要合並文件數組(絕對路徑如{ "e:\\1.pdf", "e:\\2.pdf" , 
         * "e:\\3.pdf"}) * @param newfile 
         * 合並後新產生的文件絕對路徑如e:\\temp.pdf,請自己刪除用過後不再用的文件請 * @return boolean 
         * 產生成功返回true, 否則返回false 
         */  
    public static boolean mergePdfFiles(String fromPath, String dest) {  
    	boolean retValue = false;  
        Document document = null;  
        String tmpFile = "d:\\PDF_TEMP.pdf";
        try {  
            document = new Document(new PdfReader(dest).getPageSize(1));  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(tmpFile));  
            document.open();  
            PdfReader reader = new PdfReader(dest);  
            int n = reader.getNumberOfPages();  
            for (int j = 1; j <= n; j++) {  
                document.newPage();  
                PdfImportedPage page = copy.getImportedPage(reader, j);  
                copy.addPage(page);  
            }
            reader.close();
            
            File[] from = new File(fromPath).listFiles();
            for (int i = 0; i < from.length; i++) {
            	if (from[i].isDirectory()) continue;
            	String absolutePath = from[i].getAbsolutePath();
            	System.out.println("curr deal file:"+absolutePath);
				reader = new PdfReader(absolutePath);  
            	n = reader.getNumberOfPages();  
            	for (int j = 1; j <= n; j++) {  
            		document.newPage();  
            		PdfImportedPage page = copy.getImportedPage(reader, j);  
            		copy.addPage(page);  
            	}
            	reader.close();
			}
            
            document.close();  

            Files.move(Paths.get(tmpFile), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            
            retValue = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return retValue;  
    }  
    
    
    public static void main1(String[] args) {  
        String[] files = { "D:\\BaiduNetdiskDownload\\顾子明\\2017\\01.09快讯：民政部原一二把手双双被查.pdf","D:\\BaiduNetdiskDownload\\顾子明\\2017\\01.10政事堂  什么人可以当王岐山的副手？.pdf" };  
        String savepath = "D:\\BaiduNetdiskDownload\\顾子明\\2017\\0000.pdf";  
        mergePdfFiles1(files, savepath);  
    }
    public static boolean mergePdfFiles1(String[] files, String newfile) {  
        boolean retValue = false;  
        Document document = null;  
        try {  
            document = new Document(new PdfReader(files[0]).getPageSize(1));  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));  
            document.open();  
            for (int i = 0; i < files.length; i++) {  
                PdfReader reader = new PdfReader(files[i]);  
                int n = reader.getNumberOfPages();  
                for (int j = 1; j <= n; j++) {  
                    document.newPage();  
                    PdfImportedPage page = copy.getImportedPage(reader, j);  
                    copy.addPage(page);  
                }  
            }  
            retValue = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            document.close();  
        }  
        return retValue;  
    }  

}
