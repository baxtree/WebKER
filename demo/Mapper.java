package demo;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Mapper {
	
	
	
	private static HashMap hm = new HashMap();
	private static HashMap mh = new HashMap();
	private static Document document;
	private String originalfile;
	private String mappedfile;
	
	public Mapper(String extractedfilename, String mapppedfilename){
		originalfile = extractedfilename;
		mappedfile = mapppedfilename;
		System.out.print("Map tags into ontology classes...");
		
	}
	public  Document getMappedDocument(String filename)throws XMLHelperException{
		document = XMLHelper.parseXMLFromFile(filename);
		
		hm.put("��˾����", "��������");
		hm.put("���е�", "�����г�");
		hm.put("����ʱ��", "��������");
		hm.put("�������", "������ҵ");
		hm.put("ע���", "ע���ַ");
		hm.put("�����", "���ڻ��ʦ������");
		hm.put("�����", "������ʦ������");
		hm.put("��Ӫ״��", "��˾�ظ�");
//		hm.put("��Ʊ����", "֤ȯ����");
//		hm.put("��Ʊ����", "֤ȯ���");
		
		mh.put("��������","��˾����");
		mh.put("�����г�","���е�");
		mh.put("��������","����ʱ��");
		mh.put("������ҵ","�������");
		mh.put("ע���ַ","ע���"); 
		mh.put("���ڻ��ʦ������","�����");
		mh.put("������ʦ������","�����");
		mh.put("��˾�ظ�", "��Ӫ״��");
//		hm.put("֤ȯ����", "��Ʊ����");
//		hm.put("֤ȯ���", "��Ʊ����");
		
//		String[][] alias = {
//				{"��˾����","��������"},
//				{"���е�", "�����г�"},
//				{"����ʱ��", "��������"},
//				{"�������", "������ҵ"},
//				{"ע���", "ע���ַ"},
//				{"�����", "���ڻ��ʦ������", "������ʦ������"},
//				{"��Ӫ״��", "��˾�ظ�"},
//		
//		};
		
		mapping(document);
		return document;
	}
	
	public void mapping(Node tree) throws XMLHelperException{
		Node node = tree.getFirstChild();
		while(node != null){
			mapping(node);
			if(hm.containsValue(node.getNodeName())){
				Element newnode = document.createElement((String)mh.get(node.getNodeName()));
				
				NodeList nl = node.getChildNodes();
				String nodevalue = "";
				for(int i = 0; i < nl.getLength();i++)
					nodevalue += nl.item(i).getNodeValue().replaceAll("\\s+", "");
				newnode.appendChild(document.createTextNode(nodevalue));
				(node.getParentNode()).replaceChild(newnode, node);
				node = newnode.getNextSibling();
			}
			else{
				//String nodevalue = node.getNodeValue();
				node = node.getNextSibling();}
		}
	}
//	public static void mapping(Node tree) throws XMLHelperException{
//		Node node = tree.getFirstChild();
//		while(node != null){
//			mapping(node);
//			if(hm.containsValue(node.getNodeName())){
//				Element newnode = document.createElement((String)mh.get(node.getNodeName()));
//				newnode.setNodeValue(node.getNodeValue());
//				//newnode.appendChild(document.createTextNode(value));
//				(node.getParentNode()).replaceChild(newnode, node);
//				
//				node = newnode.getNextSibling();
//			}
//			else{
//				//String nodevalue = node.getNodeValue();
//				node = node.getNextSibling();}
//		}
//	}
//	public void printnodeanvalue(Node tree) throws XMLHelperException{
//		Node node = tree.getFirstChild();
//		//NodeList nl = tree.getChildNodes();
//		while(node != null){
//			printnodeanvalue(node);
//			if(hm.containsValue(node.getNodeName())){
//				System.out.println(node.getNodeName()+" "+node.getNodeValue());
//				
//				node = node.getNextSibling();
//			}
//			else{
//				if(!node.getNodeName().toString().equals("#text"))
//				System.out.pri]tln(node.getNodeName()+" "+node.getNodeValue());
//				//String nodevalue = node.getNodeValue();
//				node = node.getNextSibling();}
//		}
//	}

	public void map()throws FileNotFoundException, XMLHelperException{
		//String output1 = XMLHelper.convertXMLToString(Mapper.getMappedDocument(originalfile));
		Document output = getMappedDocument(originalfile);
		XMLHelper.outputXMLToFile(output, mappedfile);
		System.out.println("Save to "+mappedfile);
		//System.out.println(output1);
	}
//	public static void main(String[] agrs) throws Exception{
//		//String output = XMLHelper.convertXMLToString(d);
//		//Node tree = d.getFirstChild();
//		//System.out.println(output);
//		String filename = "f:\\extractedinfo.xml";
//		String output1 = XMLHelper.convertXMLToString(Mapper.getMappedDocument(filename));
//		Document output = Mapper.getMappedDocument(filename);
//		XMLHelper.outputXMLToFile(output, "f:\\mappedinfo.xml");
//		System.out.println(output1);
//	}
	public static void main(String[] args)throws Exception{
		Mapper m = new Mapper("e:\\test\\corps.xml","e:\\test\\mapped.xml");
		m.map();
	}
}
	
