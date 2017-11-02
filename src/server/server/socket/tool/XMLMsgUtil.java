/**
 * 
 */
package server.server.socket.tool;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import server.server.socket.JournalServerParams;
import server.util.PubTools;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:40:54
 * @Description
 * @version 1.0 Shawn create
 */
public class XMLMsgUtil {

	// private static final LoggingUtil logger =
	// LoggingUtil.getInstance(XMLMsgUtil.class);

	Element xmlRoot = null; // 根结点

	public String XmlMsgString = ""; // XML字符串

	private Document document;

	/**
	 * 构造函数(创建根结点及创建Document)
	 * 
	 */
	public XMLMsgUtil() {

		// xmlRoot = new Element(SysConfig.XMLMsgRootName);
		xmlRoot = new Element("root");
		document = new Document(xmlRoot);
		xmlRoot = document.getRootElement();

	}

	/**
	 * 增加Root下的子节点
	 * 
	 * @param elementName
	 *            节点
	 * @param elementValue
	 *            节点值
	 */
	public void addElement(String elementName, String elementValue) {

		Element tempElement = new Element(elementName);
		tempElement.setText(elementValue);
		xmlRoot.addContent(tempElement);

	}

	/**
	 * 移称ROOT下的子节点
	 * 
	 * @param elementName
	 *            节点名
	 */
	public void removeElement(String elementName) {

		Element tempElement = new Element(elementName);
		xmlRoot.removeContent(tempElement);
	}

	/**
	 * 获取ROOT下的子节点值
	 * 
	 * @param elementName
	 *            节点名
	 * @return 节点值
	 */
	public String getElement(String elementName) {

		Element tempElement = xmlRoot.getChild(elementName);
		return tempElement.getText();

	}

	/**
	 * 将Document生成字符串格式
	 * 
	 * @return 字符串
	 */
//	public String getXMLString() {
//
//		StringWriter out = new StringWriter();
//		XMLOutputter outputter = new XMLOutputter("", false);
//		outputter.setEncoding("UTF-8");
//		try {
//			outputter.output(document, out);
//			out.close();
//		} catch (IOException ex) {
//			PubTools.log.error("Transfer Document to String Catch Exception:" +ex.toString());
//		}
//		return out.toString();
//	}
	public String getXMLString(){
		String xmlString = "" ;
		
		String xmlHeadString = "<?xml version=\"" + "1.0\"" + " encoding=\""+JournalServerParams.JournalXMLMsgEncoding + "\"?>" ;
		
		String xmlBodyString = "<"+JournalServerParams.JournalXMLMsgRootName+ ">" ; 
		List childElements = xmlRoot.getChildren() ;
		for(int iIndex = 0 ; iIndex < childElements.size() ; iIndex ++){
			Element element = (Element)childElements.get(iIndex) ;
			xmlBodyString += "<"+element.getName()+">" ;
			xmlBodyString += element.getText() ;
			xmlBodyString += "</"+element.getName()+">" ;
		}
		xmlBodyString += "</"+JournalServerParams.JournalXMLMsgRootName + ">" ;
		
		xmlString = xmlHeadString + xmlBodyString ;
		return xmlString ;
	}

	/**
	 * 将XML字符串生成Document格式
	 * 
	 * @param XMLString
	 *            XML字符串格式
	 */
	public void readXMLDocumentFromString(String XMLString) {

		XmlMsgString = XMLString;
		ByteArrayInputStream bais = null;

		try {
			bais = new ByteArrayInputStream(XMLString.getBytes("GBK"));
		} catch (UnsupportedEncodingException uee) {
			PubTools.log.error("Read String Catch Exception :"+uee.toString());
		}

		if (bais != null) {
			try {
				SAXBuilder saxBuilder = new SAXBuilder();
				document = saxBuilder.build(bais);
				xmlRoot = document.getRootElement();
				bais.close();
			} catch (IOException ex) {
				PubTools.log.error("Create SAXBuilder Catch IOException:"+ex.toString());
			} catch (JDOMException ex) {
				PubTools.log.error("Create SAXBuilder Catch Exception:"+ex.toString());
			}
		} else {
			throw new NullPointerException("输入流为空！");
		}
	}
	
	public static void main(String []args){
		String testString =  "<?xml version=\"" + "1.0" + "\" encoding=\"" + "UTF-8" + "\"?><root><jydm>40101</jydm><sbbh>440600100152</sbbh><filetime>20091112</filetime><isenforce>Y</isenforce></root> " ;
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil() ;
		xmlMsgUtil.readXMLDocumentFromString(testString);
		System.out.println(xmlMsgUtil.getXMLString()) ;
	}


}
