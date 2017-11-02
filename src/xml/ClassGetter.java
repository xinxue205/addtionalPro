package xml;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;

public class ClassGetter {
	
	/**
     * 获取XML配置文件中对应CLASS对象
	 * @param XMLFilePath XML文件路径
	 * @param clazzName CLASS名
	 * @return 对应class的Object对象
	 * @author wuxx
     */
	public Object getClassInstance(String XMLFilePath, String clazzName) throws Exception {
		String s = getClassPath(XMLFilePath,clazzName);
		Class<?> c = null;
		c = Class.forName(s);
		Object obj = c.newInstance();
		return obj;
	}
	
	/**
     * 获取XML配置文件中对应CLASS的地址
	 * @param XMLFilePath XML文件路径
	 * @param clazzName CLASS名
	 * @author wuxx
     */
	public String getClassPath(String XMLFilePath, String clazzName) throws Exception {
		File file = new File(XMLFilePath);
		//File file = new File("src/classPath.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Iterator<?> iter = root.elementIterator();
		HashMap<String, String> hm = new HashMap<String, String>();
		while (iter.hasNext()){
			Element clazz = (Element) iter.next();
			hm.put(clazz.attributeValue("id"), clazz.attributeValue("value")+"||"+clazz.attributeValue("desc"));
		}
		String valueAndDesc = hm.get(clazzName);
		String path = valueAndDesc.substring(0,valueAndDesc.lastIndexOf("||"));
		return path;
	}
}
