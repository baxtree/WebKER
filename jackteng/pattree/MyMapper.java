package jackteng.pattree;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import demo.*;
public class MyMapper {
	
	
	
	private static HashMap hm = new HashMap();
	private static HashMap mh = new HashMap();
	private static Document document;
	private String originalfile;
	private String mappedfile;
	
	public MyMapper(String extractedfilename, String mapppedfilename)throws XMLHelperException{
		originalfile = extractedfilename;
		mappedfile = mapppedfilename;
		Document output = getMappedDocument(originalfile);
		XMLHelper.outputXMLToFile(output, mappedfile);
		//System.out.print("Map tags into ontology classes...");
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
				node = node.getNextSibling();
			}
		}
	}

	public void map()throws FileNotFoundException, XMLHelperException{
		Document output = getMappedDocument(originalfile);
		XMLHelper.outputXMLToFile(output, mappedfile);
		System.out.println("Save to "+mappedfile);
	}

}
	
