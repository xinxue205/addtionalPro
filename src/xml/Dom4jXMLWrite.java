package xml;
//��DOM4Jд��XML�ļ�
import java.io.File;
import java.io.FileOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Dom4jXMLWrite {
	public static void main(String[] args) {
		Document doc = DocumentHelper.createDocument();  //����д����
		Element addresslist = doc.addElement("addresslist");  //������ڵ�
		Element linkman = addresslist.addElement("linkman");  //�����ӽڵ�
		Element name = linkman.addElement("name");  //����ڵ���Ŀ
		name.addAttribute("id", "lxh");  //��������
		Element email = linkman.addElement("email");
		name.setText("lxh");  //������Ŀ����
		email.setText("lxh@126.com");
		OutputFormat format = OutputFormat.createPrettyPrint();  //������ʽ��
		 OutputFormat.createPrettyPrint().setEncoding("GBK");  //�趨��ʽ
		try {
			XMLWriter writer = new XMLWriter(new FileOutputStream("D:"+File.separator+"address.xml"),format);
			writer.write(doc);  //д���ļ�
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("s");
	}
}
