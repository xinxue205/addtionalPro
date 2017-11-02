package xml;
//��JDOM���XML�ļ�
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
		Element addresslist = new Element("addresslist"); //�½��ڵ�
		Element linkman = new Element("linkman");
		Element name = new Element("name");
		Element email = new Element("email");
		Attribute id = new Attribute("id","lxh"); //�½�����
		Document doc = new Document(addresslist); //�����ļ��׽ڵ�
		linkman.addContent(name);	//��ӽڵ�
		linkman.addContent(email);
		addresslist.addContent(linkman);
		name.setAttribute(id);	//�������
		name.setText("���˻�");  //�趨����
		email.setText("lxh@126.com");
		XMLOutputter out = new XMLOutputter();
		//out.setEncoding("GBK");	//�趨����
		try {
			out.output(doc, new FileOutputStream("D:"+File.separator+"address.xml")); //����ļ�
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
