package jackteng.pattree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import demo.XMLHelper;



public class InfoSplit {
	public static MyProperty splitInfo(String s) {
		String[] result = new String[2];
		if (s.indexOf('：') != -1) {
			result = s.split("：");
		} else if (s.indexOf(":") != -1) {
			result = s.split(":");
		}
		MyProperty mp;
		if(result.length == 2)
			mp = new MyProperty(result[0].trim().replace("\\s+", ""),result[1].trim().replace("\\s+", ""));
		else{
			mp = new MyProperty(result[0].trim().replace("\\s+", ""),"null");
		}
		return mp;
	}

	public static void generateXMLfile(String originalfname, String outputfname) throws Exception {
		Document d;
		Element root = new Element("INFORMATION");
		d = new Document(root);
		Element root2 = new Element("CORPERATION");
		FileInputStream fis = new FileInputStream(new File(originalfname));
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader bf = new BufferedReader(isr);
		String s = "";
		while ((s = bf.readLine()) != null) {
			MyProperty mp = splitInfo(s);
			Element pro = new Element(mp.getPropertyName());
			pro.setText(mp.getPropertyValue());
			root2.addContent(pro);			
		}
		root.addContent(root2);
		Format format = Format.getCompactFormat();
		format.setEncoding("utf-8");
//		format.setIndent("    ");
		XMLOutputter XMLOut = new XMLOutputter(format);
		XMLOut.output(d, new FileOutputStream(outputfname));
//		org.w3c.dom.Document doc = XMLHelper.parseXMLFromFile(outputfname);
//		XMLHelper.outputXMLToFile(doc, outputfname);
		fis.close();
		isr.close();
		bf.close();
		
	}
	public static void generateXMLfile(String[][] data, String outputfname) throws Exception{
		Document d;
		Element root = new Element("INFORMATION");
		d = new Document(root);
		Element root2 = new Element("CORPERATION");
		for(int i = 0; i < data.length; i++){
			Element pro = new Element(data[i][0]);
			pro.setText(data[i][1]);
			root2.addContent(pro);
		}
		root.addContent(root2);
		Format format = Format.getCompactFormat();
		format.setEncoding("utf-8");
//		format.setIndent("    ");
		XMLOutputter XMLOut = new XMLOutputter(format);
		XMLOut.output(d, new FileOutputStream(outputfname));
		org.w3c.dom.Document doc = XMLHelper.parseXMLFromFile(outputfname);
		XMLHelper.outputXMLToFile(doc, outputfname);
		System.out.println("save xml to "+outputfname);

	}
	public static String[][] generatePairs(String originalfname) throws Exception {
		FileInputStream fis = new FileInputStream(originalfname);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader bf = new BufferedReader(isr);
		int counter = 0;
		String s = "";
		while((s = bf.readLine())!=null){
			counter++;
		}
		String[][] result = new String[counter][2];
		FileInputStream fis1 = new FileInputStream(originalfname);
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		BufferedReader bf1 = new BufferedReader(isr1);
		counter = 0;
		while((s = bf1.readLine())!=null){
			MyProperty mp = splitInfo(s);
			result[counter][0] = mp.getPropertyName();
			result[counter][1] = mp.getPropertyValue();
			counter++;
			
		}
		fis.close();
		fis1.close();
		isr.close();
		isr1.close();
		return result;
	}
	public static void main(String[] args)throws Exception {
		String a = "公司名称：中国石化";
		String b = "Name: ChinaPetro";
		MyProperty mp1 = InfoSplit.splitInfo(a);
		MyProperty mp2 = InfoSplit.splitInfo(b);
		System.out.println(mp1.getPropertyName() + "->"
				+ mp1.getPropertyValue());
		System.out.println(mp2.getPropertyName() + "->"
				+ mp2.getPropertyValue());
		InfoSplit.generateXMLfile("e:\\test\\corps1.txt","e:\\test\\corps1.xml");
		MyMapper m = new MyMapper("e:\\test\\corps1.xml", "e:\\test\\mappedcorp1.xml");
		System.out.println(InfoSplit.generatePairs("e:\\test\\corps.txt").toString());
		
	}
}
