package jackteng.pattree;

public class Utility {
	public static void main(String[] args)throws Exception{
		TagMap tm = new TagMap();
		PatArray pa = new PatArray(tm.getOriginalTagsFromFile("e:\\htmlsnip.txt"));
		pa.getPatterns();
		
	}
}
