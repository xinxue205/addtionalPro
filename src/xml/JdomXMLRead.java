package xml;
//��JDOM����XML�ļ������������̨
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
		SAXBuilder builder = new SAXBuilder();	//����JDOM������
		Document doc_read = builder.build("D:"+File.separator+"address.xml");	//ָ�������ļ�·��
		Element stu = doc_read.getRootElement();	//��ȡ���ڵ㣨����ELEMENT��ʽ��
		System.out.println("-------"+stu.getName()+"-------");	//��ȡ���ڵ���
		List<?> list = stu.getChildren("linkman");	//��ȡ���ڵ��µ��ӽڵ㣨List���ϣ�
		for(int i=0;i<list.size();i++){	 //��ȡ�ӽڵ��µĸ�������
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
