package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Reificator {
	private Model om = ModelFactory.createDefaultModel();
	private Model rom = ModelFactory.createDefaultModel();
	private String inputfile;
	private String outputfile;
	private Random id;
	public Reificator(String in, String out){
		inputfile = in;
		outputfile = out;
		loadModel();
		rom = om;
	}
	private  void loadModel(){
		InputStreamReader in;
		try {
			File f = new File(inputfile);
//			if (f.exists())
//				System.out.println("本体文件存在");
			FileInputStream file = new FileInputStream(f);
			in = new InputStreamReader(file, "UTF-8");// 处理中文
			om.read(in, null);
			in.close();
		} catch (FileNotFoundException e) {

			System.out.println("无法打开本体文件，程序将终止");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
	public void reifyOntFile()throws Exception{
		
		StmtIterator iter = rom.listStatements();
		try{
			int i = 0;
			while(iter.hasNext()){
				Statement stmt = (Statement)iter.next();
				stmt.createReifiedStatement("http://www.owl-ontologies.com/Ontology1168914056.owl#id"+i);
				i++;
				
			}
		}
		catch(Exception e){
			
		}
		
		try {
			File file = new File(outputfile);
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
			rom.write(osw, "RDF/XML-ABBREV");
			
		} catch (Exception e) {
			System.out.println("File cannot be created.");
		}
	}
	public static void main(String[] args)throws Exception{
		Reificator rei = new Reificator("f:\\zgshjj2.owl", "f:\\111.owl");
		rei.reifyOntFile();
	}
}
