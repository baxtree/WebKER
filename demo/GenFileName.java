package demo;

public class GenFileName {
	private  String input;
	private  String inputhtmltran;
	private  String normalizedfile;
	private  String extractedinfo;
	private  String mappedinfo;
	private  String rdfinfo;
	private  String rdfreification;
	private  String output;
	public GenFileName(String in){
		input = in;
		String localpath = (input.replace("file:///", "")).replace("/", "\\");
		inputhtmltran = localpath;
		normalizedfile = inputhtmltran.replace("html","xml");
		extractedinfo = inputhtmltran.replace(".html", "extracted.xml");
		mappedinfo = inputhtmltran.replace(".html", "mapped.xml");
		rdfinfo = inputhtmltran.replace(".html", ".owl");
		rdfreification = inputhtmltran.replace(".html", "Reified.owl");
		output = inputhtmltran.replace(".html", "Ont.owl").replace("test", "test\\result");
	}

	public  String getExtractedinfo() {
		return extractedinfo;
	}

	public  String getMappedinfo() {
		return mappedinfo;
	}

	public  String getNormalizedfile() {
		return normalizedfile;
	}

	public  String getRdfinfo() {
		return rdfinfo;
	}

	public  String getInput() {
		return input;
	}

	public  String getOutput() {
		return output;
	}

	public String getRdfreification() {
		return rdfreification;
	}
}
