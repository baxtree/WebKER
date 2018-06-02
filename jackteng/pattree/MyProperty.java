package jackteng.pattree;

public class MyProperty {
	String pname = null;
	String pvalue = null;
	public MyProperty(String pn, String pv ){
		pname = pn;
		pvalue = pv;
	}
	public String getPropertyName(){
		return pname;
	}
	public String getPropertyValue(){
		return pvalue;
	}
}
