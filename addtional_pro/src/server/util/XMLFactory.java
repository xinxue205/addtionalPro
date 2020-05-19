/**
 * 
 */
package server.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:16:44
 * @Description
 * @version 1.0 Shawn create
 */
public class XMLFactory {
	private Document document;

	private String fileString;

	public XMLFactory(String fileString) throws Exception {
		this.fileString = fileString;
		SAXBuilder builder = new SAXBuilder();
		document = builder.build(new File(fileString));

	}

	public Element getRootElement() {
		return document.getRootElement();
	}

	public Element getChildByAttribute(Element element, String attributeName,
			String attributeValue) {
		Element retElement = null;
		List list = element.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element temp = (Element) it.next();
			if (temp.getAttributeValue(attributeName).equals(attributeValue)) {
				retElement = temp;
				break;
			}
		}
		return retElement;
	}

	public List getChildrenByAttribute(Element element,
			String attributeName, String attributeValue) {
		List elementList = new LinkedList();
		List list = element.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element temp = (Element) it.next();
			if (temp.getAttributeValue(attributeName).equals(attributeValue)) {
				elementList.add(temp);
			}
		}
		if (elementList.size() == 0)
			elementList = null;
		return elementList;
	}

	public Element getChildByText(Element element, String text) {
		Element retElement = null;
		List list = element.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element temp = (Element) it.next();
			if (temp.getText().equals(text)) {
				retElement = temp;
				break;
			}
		}
		return retElement;

	}

	public List getChildrenByText(Element element, String text) {
		List elementList = new LinkedList();
		List list = element.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element temp = (Element) it.next();
			if (temp.getText().equals(text)) {
				elementList.add(temp);
			}
		}
		return elementList;
	}

	public void commit() throws Exception {
		FileWriter writer = new FileWriter(fileString);
		XMLOutputter outputter = new XMLOutputter();//new XMLOutputter("  ", true, "BIG5");
//		outputter.setTextNormalize(true);
		outputter.output(document, writer);
		writer.close();
		SAXBuilder builder = new SAXBuilder();
		document = builder.build(new File(fileString));
	}


}
