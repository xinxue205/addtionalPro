package xml;
//用JDOM输出XML文件
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;


public class JdomXMLWrite {
	public static void main(String[] args) {
		Element addresslist = new Element("addresslist"); //新建节点
		Element linkman = new Element("linkman");
		Element name = new Element("name");
		Element email = new Element("email");
		Attribute id = new Attribute("id","lxh"); //新建属性
		Document doc = new Document(addresslist); //定义文件首节点
		linkman.addContent(name);	//添加节点
		linkman.addContent(email);
		addresslist.addContent(linkman);
		name.setAttribute(id);	//添加属性
		name.setText("李兴华");  //设定内容
		email.setText("lxh@126.com");
		XMLOutputter out = new XMLOutputter();
		//out.setEncoding("GBK");	//设定编码
		try {
			out.output(doc, new FileOutputStream("D:"+File.separator+"address.xml")); //输出文件
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
