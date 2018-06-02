package jackteng.pattree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class RDFOperater {
	public OntModel om;
	public RDFOperater(String ontfilename){
		om = ModelFactory.createOntologyModel();
		loadModel(ontfilename);
	}
	private  void loadModel(String filePath){
		InputStreamReader in;
		try {
			File f = new File(filePath);
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
	
	public String[][] get3TuplesTable(){
		ArrayList al = new ArrayList();
		Iterator iter = om.listIndividuals();
		for(;iter.hasNext();){
			Individual ind = (Individual)iter.next();
			Iterator iter1 = ind.listProperties();
			for(;iter1.hasNext();){
				Statement sta = (Statement)iter1.next();
				Resource subject = sta.getSubject();
				Property predicate = sta.getPredicate();
				RDFNode object = sta.getObject();
				
				//System.out.println(rs+": )"); 
				String[] sa = {subject.toString().replaceAll("http://.*?#", "#"),predicate.toString().replaceAll("http://.*?#", "#"),object.toString().replaceAll("http://.*?#", "#")};
				al.add(sa);
				System.out.print(subject.toString());
				System.out.print(" "+predicate.toString()+" ");
				if(object instanceof Resource){
					System.out.println(object.toString());
				}
				else{
					System.out.println("\""+object.toString()+"\"");
				}
			}
//			System.out.println();
		}
		String[][] result = new String[al.size()][3];
		for(int i = 0; i < al.size(); i++)
			for(int j = 0; j < 3; j++){
				result[i][j] = ((String[])al.get(i))[j];
				System.out.println(result[i][j]);
			}
		return result;
	}
	public static void main(String[] args){
		RDFOperater rdfo = new RDFOperater("e:\\test\\zgshrdf.owl");
		String[][] s = rdfo.get3TuplesTable();
		System.out.println(s[16][0]+" "+s[16][1]+" "+s[16][2]);
//		for(int i = 0; i < s.length; i++)
//			for(int j = 0; j < s[i].length; i++){
//				System.out.println(s[i][j]);
//			}
	}
}
