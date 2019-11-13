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
    	String dest = "D:\\BaiduNetdiskDownload\\������\\2019-�ϰ���\\new\\new.pdf";
        String fromPath = "D:\\BaiduNetdiskDownload\\������\\2019-�ϰ���\\";  
        mergePdfFiles(fromPath, dest);  
    } /* 
         * * �ρKpdf�ļ� * * @param files Ҫ�ρK�ļ����M(�^��·����{ "e:\\1.pdf", "e:\\2.pdf" , 
         * "e:\\3.pdf"}) * @param newfile 
         * �ρK���®a�����ļ��^��·����e:\\temp.pdf,Ո�Լ��h�����^�᲻���õ��ļ�Ո * @return boolean 
         * �a���ɹ�����true, ��t����false 
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
        String[] files = { "D:\\BaiduNetdiskDownload\\������\\2017\\01.09��Ѷ��������ԭһ������˫˫����.pdf","D:\\BaiduNetdiskDownload\\������\\2017\\01.10������  ʲô�˿��Ե����ɽ�ĸ��֣�.pdf" };  
        String savepath = "D:\\BaiduNetdiskDownload\\������\\2017\\0000.pdf";  
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
