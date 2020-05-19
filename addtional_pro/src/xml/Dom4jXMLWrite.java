package xml;
//用DOM4J写入XML文件
import java.io.File;
import java.io.FileOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Dom4jXMLWrite {
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();  //创建写入器
		Element addresslist = doc.addElement("addresslist");  //加入根节点
		Element linkman = addresslist.addElement("linkman");  //加入子节点
		Element name = linkman.addElement("name");  //加入节点项目
		name.addAttribute("id", "lxh");  //加入属性
		Element email = linkman.addElement("email");
		name.setText("lxh");  //加入项目内容
		email.setText("lxh@126.com");
		OutputFormat format = OutputFormat.createPrettyPrint();  //创建格式器
		 OutputFormat.createPrettyPrint().setEncoding("GBK");  //设定格式
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream("D:"+File.separator+"address.xml"),format);
			writer.write(doc);  //写入文件
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("s");
	}
}
