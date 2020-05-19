package xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jXMLRead {
	public static void main(String[] args) {
		File file = new File("src/classPath.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Iterator<?> iter = root.elementIterator();
		HashMap<String, String> hm = new HashMap<String, String>();
		while (iter.hasNext()){
			Element clazz = (Element) iter.next();
			hm.put(clazz.attributeValue("id"), clazz.attributeValue("value"));
		}
		System.out.println(hm.get("test"));;
	}
}
