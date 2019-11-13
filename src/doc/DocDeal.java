package doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

public class DocDeal {
	public static void main(String[] args) throws Exception {
		InputStream inputStream = new FileInputStream( new File("D:\\2018�ꡰ��ǹ��������˹����ܲ�ҵְ��ְҵ���ܴ���������Ʒ�걨��(1)(1)-��ҵ��ǰ������-����ѧ.docx"));
		 BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
	      // ����PDF�ĵ�ʱӦ�ɳ���AbstractParser��������PDFParserʵ��
	      try {
	    	  new AutoDetectParser().parse(inputStream, textHandler, new Metadata(), new ParseContext());
	      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	      }//ִ�н�������
	      System.out.println(textHandler.toString());
	      
//		System.out.println(parseFile(new FileInputStream( new File("D:\\work\\��ְ�ʸ���̸��-����ѧ.docx"))));
	}
	
	public static String parseFile(InputStream inputStream) throws Exception {
        // FileInputStream  input=new FileInputStream(new File("C:\\Users\\liuzhaoen\\Desktop\\����q1�й��������������ȷ�������.pdf"));//����д�ļ�·����pdf��word��html��
        BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
        Metadata matadata=new Metadata();//Metadata���󱣴������ߣ������Ԫ����
        AutoDetectParser parser = new AutoDetectParser();//������parser��AutoDetectParser���Զ������ĵ�MIME���ͣ��˴�����pdf�ļ�����˿���ʹ��PDFParser
        // ����PDF�ĵ�ʱӦ�ɳ���AbstractParser��������PDFParserʵ��
        ParseContext context=new ParseContext();
        parser.parse(inputStream, textHandler, matadata, context);//ִ�н�������
        inputStream.close();
        return textHandler.toString();
    }
}
