package demo;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InformationExtractor {
	private String source;// = "f:\\zgshjj2.xml";
	private String xslFile;// = "f:\\infowithoutspace.xsl";
	private String xmlFile;// = "f:\\extractedinfo.xml";
	//public static void main(String[] agrs){
	public InformationExtractor(String nomalizedxmlfilepath, String templatefilename, String extractedfilename){
		source = nomalizedxmlfilepath;
		xslFile = templatefilename;
		xmlFile = extractedfilename;
		System.out.print("Extract information...");
	}
	
	public void extracInfo(){
		try{
			Document xhtml = XMLHelper.parseXMLFromFile(source);
			//System.out.println(XMLHelper.convertXMLToString(xhtml).replace(Integer.toString(0x20), " "));
			Document xsl = XMLHelper.parseXMLFromFile(xslFile);
			Document xml = XMLHelper.transformXML(xhtml, xsl);
//			Element root = xml.getDocumentElement();
			
			
			//System.out.println(root.getNodeName());
			//System.out.println(XMLHelper.convertXMLToString(xml));
			//System.out.println(root.getFirstChild().getNodeName());
//			NodeList nl = root.getElementsByTagName("software");
			
			//System.out.println(nl.getLength());
			//System.out.println(nl.item(0).getNodeName());
			
			File dataFile  = new File(xmlFile);
			if (dataFile.exists()) {
				// If we have extracted before, merge the data and write the file
				Document oldData = XMLHelper.parseXMLFromFile(dataFile);
//				for(int i = 0; i < nl.getLength(); i++){
//					Element software = (Element)nl.item(i);
//					XMLHelper.mergeXML(oldData.getDocumentElement(), software, false);
//				}
				XMLHelper.outputXMLToFile(oldData, xmlFile);
			} else {
				 //If this is our first extraction, just write the file
				XMLHelper.outputXMLToFile(xml, xmlFile);
			}
			
		}
		catch(Exception xmle){
			xmle.printStackTrace();
		}
		System.out.println("Save to "+xmlFile);
	}
}

