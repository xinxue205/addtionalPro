package xml;
//用JDOM解析XML文件并输出到控制台
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

//
public class JdomXMLRead {
	public static void main(String[] args) throws Exception {
		SAXBuilder builder = new SAXBuilder();	//创建JDOM解析器
		Document doc_read = builder.build("D:"+File.separator+"address.xml");	//指定解析文件路径
		Element stu = doc_read.getRootElement();	//获取根节点（集合ELEMENT形式）
		System.out.println("-------"+stu.getName()+"-------");	//读取根节点名
		List<?> list = stu.getChildren("linkman");	//获取根节点下的子节点（List集合）
		for(int i=0;i<list.size();i++){	 //获取子节点下的各项内容
			Element e = (Element) list.get(i);
			String name = e.getChildText("name");
			String id = e.getChild("name").getAttributeValue("id");
			String email = e.getChildText("email");
			System.out.println("********Linkman"+(i+1)+"********");
			System.out.println("Name: "+name+", ID: "+id);
			System.out.println("Email: "+email);
		}
		System.out.println("************************");
	}
}
