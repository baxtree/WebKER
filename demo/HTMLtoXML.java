package demo;

import java.io.File;
import org.w3c.dom.Node;

import org.w3c.dom.Document;
//import edu.byu.deg.util.*;
//import com.sun.xml.tree.*;
public class HTMLtoXML {
	private String htmlfile;
	private String xmlfile;
	public HTMLtoXML(String originalhtmlfilepath, String nomalizedxmlfilepath){
		htmlfile = originalhtmlfilepath;
		xmlfile = nomalizedxmlfilepath;
		System.out.print("Normalize HTML file to XML file...");
	}
	public void transHTMLToXML(){
		try{
		Document doc = XMLHelper.tidyHTML(htmlfile);
		//Node n = doc.getFirstChild();
		//DOMPrinter.print(n);
		//XmlDocument xmld = (XmlDocument)doc;
		//System.out.println(XMLHelper.convertXMLToString(doc));
		XMLHelper.outputXMLToFile(doc, xmlfile);
		
		System.out.println("Save to "+xmlfile);
		}
		catch(XMLHelperException xmle){
			System.out.println(xmle.getMessage());
			xmle.getMessage();
		}
	}
}
