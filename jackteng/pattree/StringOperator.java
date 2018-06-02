package jackteng.pattree;

public class StringOperator {
	public static String removeSelfRepeated(String pat) {
		int leftpoint = 1;
		int rightpoint = pat.length() - 1;
		while (leftpoint <= rightpoint) {
			String leftstring = pat.substring(0, leftpoint);
			String rightstring = pat.substring(rightpoint, pat.length());
			if (leftstring.equals(rightstring)) {
				pat = pat.substring(0, rightpoint);
				leftpoint = 1;
				rightpoint = pat.length() - 1;
			} else {
				leftpoint++;
				rightpoint--;
			}
		}
		return pat;
	}

	public static String inverseString(String ori) {
		char[] o = ori.toCharArray();
		int len = o.length;
		char[] d = new char[len];
		for (int i = 0; i < len; i++) {
			d[i] = o[len - i - 1];
		}
		return String.valueOf(d);
	}
	public static String getSringFromArray(String[] s){
		String result = "";
		for(int i = 0; i < s.length; i++)
			result += s[i];
		return result;
	}
	public static String getNormalizedFilePath(String s){
		s = s.replace("\\", "\\\\");
		return s;
	}
	public static void main(String[] args){
		String pat = "abcabcabcabcabcab";
		System.out.println(inverseString(pat));
		System.out.println(removeSelfRepeated(pat));
		System.out.println(StringOperator.getNormalizedFilePath("f:\\asldf.txt"));
	}
}
