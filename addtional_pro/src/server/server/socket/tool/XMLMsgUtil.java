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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:40:54
 * @Description
 * @version 1.0 Shawn create
 */
public class XMLMsgUtil {

	// private static final LoggingUtil logger =
	// LoggingUtil.getInstance(XMLMsgUtil.class);

	Element xmlRoot = null; // �����

	public String XmlMsgString = ""; // XML�ַ���

	private Document document;

	/**
	 * ���캯��(��������㼰����Document)
	 * 
	 */
	public XMLMsgUtil() {

		// xmlRoot = new Element(SysConfig.XMLMsgRootName);
		xmlRoot = new Element("root");
		document = new Document(xmlRoot);
		xmlRoot = document.getRootElement();

	}

	/**
	 * ����Root�µ��ӽڵ�
	 * 
	 * @param elementName
	 *            �ڵ�
	 * @param elementValue
	 *            �ڵ�ֵ
	 */
	public void addElement(String elementName, String elementValue) {

		Element tempElement = new Element(elementName);
		tempElement.setText(elementValue);
		xmlRoot.addContent(tempElement);

	}

	/**
	 * �Ƴ�ROOT�µ��ӽڵ�
	 * 
	 * @param elementName
	 *            �ڵ���
	 */
	public void removeElement(String elementName) {

		Element tempElement = new Element(elementName);
		xmlRoot.removeContent(tempElement);
	}

	/**
	 * ��ȡROOT�µ��ӽڵ�ֵ
	 * 
	 * @param elementName
	 *            �ڵ���
	 * @return �ڵ�ֵ
	 */
	public String getElement(String elementName) {

		Element tempElement = xmlRoot.getChild(elementName);
		return tempElement.getText();

	}

	/**
	 * ��Document�����ַ�����ʽ
	 * 
	 * @return �ַ���
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
	 * ��XML�ַ�������Document��ʽ
	 * 
	 * @param XMLString
	 *            XML�ַ�����ʽ
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
			throw new NullPointerException("������Ϊ�գ�");
		}
	}
	
	public static void main(String []args){
		String testString =  "<?xml version=\"" + "1.0" + "\" encoding=\"" + "UTF-8" + "\"?><root><jydm>40101</jydm><sbbh>440600100152</sbbh><filetime>20091112</filetime><isenforce>Y</isenforce></root> " ;
		XMLMsgUtil xmlMsgUtil = new XMLMsgUtil() ;
		xmlMsgUtil.readXMLDocumentFromString(testString);
		System.out.println(xmlMsgUtil.getXMLString()) ;
	}


}
