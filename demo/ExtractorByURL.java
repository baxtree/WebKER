package demo;

import java.io.BufferedReader;
import java.io.FileReader;

public class ExtractorByURL {
	String urlsfile;
	public void extractRDFFromURL(String u){
		try {
		      BufferedReader i = new BufferedReader(new FileReader(u));
		      while(i.ready()){
		    	  GenFileName gn = new GenFileName(i.readLine());
		  		
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
		      }
		    } catch(Exception e){ System.err.println();}
	}
	public static void main(String[] args){
		ExtractorByURL ebu = new ExtractorByURL();
		ebu.extractRDFFromURL("f:\\url.txt");
	}
}
