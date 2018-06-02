package jackteng.pattree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.jdom.input.DOMBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import demo.XMLHelper;
import demo.XMLHelperException;

public class RDFGenerator {

	public static String primarykey;

	public static String primarykeyvalue;

	public static String substituteprimaryvalue;

	// private Random r;
	private String ontbasefile;

	private String inputfile;

	private String outputfile;

	private OntModel ontbasemodel;// = new OntModel("F:\\small\\small.owl");

	private OntModel resourcemodel;

	// private String individualname;
	private static String ontbasenamespace = "http://www.owl-ontologies.com/Ontology1168914056.owl#";

	private boolean primarykeyexist = false;

	// private OntResource domain;
	// private OntResource range;
	// private String base;
	public RDFGenerator(String ontbasefile, String inputfile,
			String outputfile) {
		primarykey = "公司名称";
		primarykeyvalue = null;
		substituteprimaryvalue = "匿名股份有限公司";
		this.ontbasefile = ontbasefile;
		this.inputfile = inputfile;
		this.outputfile = outputfile;
		ontbasemodel = ModelFactory.createOntologyModel();
		OntDocumentManager dm = ontbasemodel.getDocumentManager();
		dm.addAltEntry("http://www.owl-ontologies.com/Ontology1168914056.owl#",
				"file:///f:/small/small.owl");
		resourcemodel = ModelFactory.createOntologyModel();
		resourcemodel.addLoadedImport(ontbasenamespace);
		resourcemodel = ontbasemodel;
		// OntDocumentManager dm = resourcemodel.getDocumentManager();
		// dm.addAltEntry("http://www.owl-ontologies.com/Ontology1168914056.owl#","file:///f://small//small.owl");
		loadModel(ontbasefile);

		// Ontology ont = ontbasemodel.getOntology(base);
		// for(Iterator i = ontbasemodel.list)
		// System.out.println(ontbasemodel.);
		// System.out.println(resourcemodel);

	}

	private void loadModel(String filePath) {
		InputStreamReader in;
		try {
			File f = new File(filePath);
			// if (f.exists())
			// System.out.println("本体文件存在");
			FileInputStream file = new FileInputStream(f);
			in = new InputStreamReader(file, "UTF-8");// 处理中文
			ontbasemodel.read(in, null);
			in.close();
		} catch (FileNotFoundException e) {

			System.out.println("无法打开本体文件，程序将终止");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	// public void creatRDFFile(String mappedfile)throws XMLHelperException{
	// File mf = new File(mappedfile);
	// Document d = XMLHelper.parseXMLFromFile(mf);
	// while(d!=null){
	//			
	// }
	// };

	// public void createIndividualName(Node tree){
	// Node node = tree.getFirstChild();
	// while(node != null){
	// createIndividualName(node);
	// if(node.getLocalName().equals("公司名称")){
	// NodeList nl = node.getChildNodes();
	// String value = "";
	// for(int i = 0; i < nl.getLength(); i++){
	// value += nl.item(i).getNodeValue().replaceAll("\\s+", "");
	// }
	// individualname = value;
	// break;
	// }
	// else{
	// node = node.getNextSibling();
	// }
	// }
	// }
	public void findPrimaryKeyValue(Node tree) {
		Node node = tree.getFirstChild();
		String nodevalue = "";

		while (node != null) {
			findPrimaryKeyValue(node);
			if (node.getNodeName().equals(primarykey)) {
				primarykeyexist = true;
				NodeList nl = node.getChildNodes();
				for (int i = 0; i < nl.getLength(); i++) {
					nodevalue += nl.item(i).getNodeValue();
				}
				primarykeyvalue = nodevalue.replaceAll("\\s+", "");
				break;
			} else {
				node = node.getNextSibling();
			}
		}
		
		
	}
	public void createPrimaryKeyValue(){
		if (primarykeyexist == false) {//如果主键属性不存在就设置一个匿名主键
			try {
				org.w3c.dom.Document doc = XMLHelper
						.parseXMLFromFile(inputfile);
				org.w3c.dom.Node child = doc.getFirstChild();
				org.w3c.dom.Element substitutenode = doc.createElement("公司名称");
				substitutenode.appendChild(doc
						.createTextNode(substituteprimaryvalue));
				child.appendChild(substitutenode);
				primarykeyvalue = substituteprimaryvalue;
				XMLHelper.outputXMLToFile(doc, inputfile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public void creatResource(Node tree) {
		Node node = tree.getFirstChild();
		// OntResource domain
		while (node != null) {
			creatResource(node);
			String uri = ontbasenamespace + node.getNodeName();
			// System.out.print("oaisdjfopaisjdopfi");
			OntProperty op = ontbasemodel.getOntProperty(uri);
			if (op != null) {

				OntClass dc = ontbasemodel.getOntClass(op.getDomain()
						.toString());
				OntClass rc = ontbasemodel
						.getOntClass(op.getRange().toString());
				Individual sub;
				// System.out.println(op.getDomain().toString());
				if (dc.getLocalName().equals("股份有限公司"))
					sub = resourcemodel.createIndividual(ontbasenamespace
							+ primarykeyvalue, dc);
				else
					sub = resourcemodel.createIndividual(ontbasenamespace
							+ primarykeyvalue.replace("股份有限公司", "股票"), dc);
				if (op.isObjectProperty()) {
					NodeList nl = node.getChildNodes();
					String value = "";
					for (int i = 0; i < nl.getLength(); i++)
						value += nl.item(i).getNodeValue().replaceAll("\\s+",
								"");
					Individual obj = resourcemodel.createIndividual(
							ontbasenamespace + value, rc);
					sub.addProperty(op, obj);
				} else if (op.isDatatypeProperty()) {
					NodeList nl = node.getChildNodes();
					String value = "";
					for (int i = 0; i < nl.getLength(); i++)
						value += nl.item(i).getNodeValue().replaceAll("\\s+",
								"");
					sub.addProperty(op, value);
				}
			}
			node = node.getNextSibling();

		}

	}

	public void linkIndividual() {
		OntClass gs = resourcemodel
				.getOntClass("http://www.owl-ontologies.com/Ontology1168914056.owl#股份有限公司");
		OntClass gp = resourcemodel
				.getOntClass("http://www.owl-ontologies.com/Ontology1168914056.owl#股票");
		OntProperty ss = resourcemodel
				.getOntProperty("http://www.owl-ontologies.com/Ontology1168914056.owl#所属公司");
		Individual obj = resourcemodel.createIndividual(ontbasenamespace
				+ primarykeyvalue, gs);
		Individual sub = resourcemodel.createIndividual(ontbasenamespace
				+ primarykeyvalue.replace("股份有限公司", "股票"), gp);
		sub.addProperty(ss, obj);
	}

	// public void getIndividual(){
	// Individual[] ind;
	// int i = 0;
	// for(ExtendedIterator ei = resourcemodel.();ei.hasNext();){
	// ind[i] = new Individual();
	// }
	// for(ExtendedIterator ei = resourcemodel.listIndividuals();ei.hasNext();){
	// // ei.next();
	// // i++;
	// // }
	// System.out.print(i);
	// for(ExtendedIterator ei = resourcemodel.listIndividuals();ei.hasNext();){
	// ind[i] = (Individual)ei.next();
	//			
	// i++;
	// }
	// }
	//	
	public File createRDFFile() throws XMLHelperException,
			FileNotFoundException {
		File f = new File(outputfile);
		Document d = XMLHelper.parseXMLFromFile(inputfile);
		findPrimaryKeyValue(d);
		createPrimaryKeyValue();
		creatResource(d);
		FileOutputStream out = new FileOutputStream(f);
		linkIndividual();
		resourcemodel.write(new PrintWriter(System.out), "RDF/XML-ABBREV");
		// System.out.println(primarykeyvalue);
		resourcemodel.write(out, "RDF/XML-ABBREV");
		return f;

	}

	public static void main(String[] args) throws XMLHelperException,
			FileNotFoundException {
		RDFGenerator crdfr = new RDFGenerator("F:\\small\\small.owl",
				"F:\\mappedinfo.xml", "F:\\zgshjj2.owl");
		crdfr.createRDFFile();
		// System.out.println(Mapper.getPrimaryKey());
		// crdfr.getIndividual();

	}
}
