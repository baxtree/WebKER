package demo;

import java.io.BufferedReader;
import java.io.FileReader;

public class Utility {
	
//需要现在test文件夹中新建一个result文件夹
	public static void main(String[] args)throws Exception{
//		for(int i = 0; i < 100; i++){
		
		GenFileName gn = new GenFileName("file:///f:/zgshjj2.html");
		
		HTMLtoXML htx = new HTMLtoXML(gn.getInput(),gn.getNormalizedfile());
		htx.transHTMLToXML();
		InformationExtractor ie = new InformationExtractor(gn.getNormalizedfile(), "f:\\info.xsl", gn.getExtractedinfo());
		ie.extracInfo();
		Mapper m = new Mapper(gn.getExtractedinfo(), gn.getMappedinfo());
		m.map(); 
		CreateRDFResource crr = new CreateRDFResource("F:\\small\\small.owl", gn.getMappedinfo(), gn.getRdfinfo());
		crr.createRDFFile();
		Reificator r = new Reificator(gn.getRdfinfo(), gn.getRdfreification());
  		r.reifyOntFile();
		MyMerge mm = new MyMerge("F:\\small\\samll.owl", gn.getRdfreification(), gn.getOutput()) ;
		mm.Merge() ;
		mm.Output() ;
		
		System.out.println("done");
//		}
	}
	
}
