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
		InputStream inputStream = new FileInputStream( new File("D:\\2018年“羊城工匠杯”人工智能产业职工职业技能大赛参赛作品申报书(1)(1)-产业化前景分析-吴新学.docx"));
		 BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
	      // 解析PDF文档时应由超类AbstractParser的派生类PDFParser实现
	      try {
	    	  new AutoDetectParser().parse(inputStream, textHandler, new Metadata(), new ParseContext());
	      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	      }//执行解析过程
	      System.out.println(textHandler.toString());
	      
//		System.out.println(parseFile(new FileInputStream( new File("D:\\work\\任职资格面谈表-吴新学.docx"))));
	}
	
	public static String parseFile(InputStream inputStream) throws Exception {
        // FileInputStream  input=new FileInputStream(new File("C:\\Users\\liuzhaoen\\Desktop\\艾瑞：q1中国互联网流量季度分析报告.pdf"));//可以写文件路径，pdf，word，html等
        BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
        Metadata matadata=new Metadata();//Metadata对象保存了作者，标题等元数据
        AutoDetectParser parser = new AutoDetectParser();//当调用parser，AutoDetectParser会自动估计文档MIME类型，此处输入pdf文件，因此可以使用PDFParser
        // 解析PDF文档时应由超类AbstractParser的派生类PDFParser实现
        ParseContext context=new ParseContext();
        parser.parse(inputStream, textHandler, matadata, context);//执行解析过程
        inputStream.close();
        return textHandler.toString();
    }
}
