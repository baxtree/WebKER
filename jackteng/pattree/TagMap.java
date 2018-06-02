package jackteng.pattree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class TagMap {
	// 可以用hashtable优化
	private static String[] tags = { "<TABLE>", "</TABLE>", "<TBODY>",
			"</TBODY>", "<TR>", "</TR>", "<TD>", "</TD>", "<TXT/>", "<FORM>",
			"</FORM>", "<A>", "</A>", "<OPTION>", "</OPTION>", "<INPUT>",
			"<SELECT>", "</SELECT>", "<B>", "</B>", "<BR>", "<HTML>",
			"</HTML>", "<HEAD>", "</HEAD>", "<TITLE>", "</TITLE>", "<META>",
			"</META>", "<SCRIPT>", "</SCRIPT>", "<STYLE>", "</STYLE>",
			"<BODY>", "</BODY>", "<DIV>", "</DIV>", "<BUTTON>", "</BUTTON>",
			"<SMALL>", "</SMALL>", "<LINK>", "</LINK>", "<IMG>", "<IFRAME>",
			"</IFRAME>", "<SPAN>", "</SPAN>", "<SPACER>", "</SPACER>",
			"<STRONG>", "</STRONG>", "<BIG>", "</BIG>", "<CENTER>",
			"</CENTER>", "<FONT>", "</FONT>", "<HR>", "</NOSCRIPT>",
			"</NOSCRIPT>" };

	private static String[] codedalph = { "a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
			"u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6",
			"7", "8" };

	// public TagMap(String filename1, String filename2){
	//		
	// }
	private String ib;

	public static String[] mapTagToCodedAlph(String[] tag) {
		String[] result = new String[tag.length];
		// for(int i = 0; i < tag.length; i++){
		// result[i] = "";
		// }
		for (int i = 0; i < tag.length; i++) {
			for (int j = 0; j < tags.length; j++) {
				if (tag[i].equals(tags[j])) {
					result[i] = codedalph[j];
					break;
				}
			}
		}
		return result;
	}

	public static String[] mapCodedAlphToTag(String[] alph) {
		String[] result = new String[alph.length];
		for (int i = 0; i < alph.length; i++) {
			for (int j = 0; j < codedalph.length; j++) {
				if (alph[i].equals(codedalph[j])) {
					result[i] = tags[j];
					break;
				}
			}
		}
		return result;
	}

	public static String[] mapCodedAlphToTag(char[] alph) {
		String[] s = new String[alph.length];
		for (int i = 0; i < s.length; i++)
			s[i] = String.valueOf(alph[i]);
		return mapCodedAlphToTag(s);
	}

	public static String getNomalizedTags(String s) {
		s = s.toUpperCase();
		s = s.replaceAll("<!.*?<.*?>.*?>", " ");
		s = s.replaceAll("<!.*?>", " ");
		s = s.replaceAll("<!--", " ");
		s = s.replaceAll("-->", " ");
		s = s.replaceAll("<B[^>]*(>|\\n*)", "<B>");
		s = s.replaceAll("<TD[^>]*(>|\\n*)", "<TD>");
		s = s.replaceAll("<TR[^>]*(>|\\n*)", "<TR>");
		s = s.replaceAll("<FORM[^>]*(>|\\n*)", "<FORM>");
		s = s.replaceAll("<TABLE[^>]*(>|\\n*)", "<TABLE>");
		s = s.replaceAll("<HTML[^>]*(>|\\n*)", "<HTML>");
		s = s.replaceAll("<HEAD[^>]*(>|\\n*)", "<HEAD>");
		s = s.replaceAll("<TITLE[^>]*(>|\\n*)", "<TITLE>");
		s = s.replaceAll("<META[^>]*(>|\\n*)", "<META>");
		s = s.replaceAll("<SCRIPT[^>]*(>|\\n*)", "<SCRIPT>");
		s = s.replaceAll("<IFRAME[^>]*(>|\\n*)", "<IFRAME>");
		s = s.replaceAll("<STYLE[^>]*(>|\\n*)", "<STYLE>");
		s = s.replaceAll("<SPAN[^>]*(>|\\n*)", "<SPAN>");
		s = s.replaceAll("<BODY[^>]*(>|\\n*)", "<BODY>");
		s = s.replaceAll("<DIV[^>]*(>|\\n*)", "<DIV>");
		s = s.replaceAll("<BUTTON[^>]*(>|\\n*)", "<BUTTON>");
		s = s.replaceAll("<SMALL[^>]*(>|\\n*)", "<SMALL>");
		s = s.replaceAll("<BIG[^>]*(>|\\n*)", "<BIG>");
		s = s.replaceAll("<LINK[^>]*(>|\\n*)", "<LINK>");
		s = s.replaceAll("<IMG[^>]*(>|\\n*)", "<IMG>");
		s = s.replaceAll("<A[^>]*(>|\\n*)", "<A>");
		s = s.replaceAll("<SPACER[^>]*(>|\\n*)", "<SPACER>");
		s = s.replaceAll("<STRONG[^>]*(>|\\n*)", "<STRONG>");
		s = s.replaceAll("<CENTER[^>]*(>|\\n*)", "<CENTER>");
		s = s.replaceAll("<FONT[^>]*(>|\\n*)", "<FONT>");
		s = s.replaceAll("<HR[^>]*(>|\\n*)", "<HR>");
		s = s.replaceAll("<NOSCRIPT[^>]*(>|\\n*)", "<NOSCRIPT>");
		// s=s.replaceAll("<TABLE[^>]*\\n*","<TABLE>");
		// s = s.replaceAll("<A[^>]*", " ");
		// s = s.replaceAll("</A[^>]*", " ");
		s = s.replaceAll("<SELECT[^>]*(>|\\n*)", "<SELECT>");
		s = s.replaceAll("<OPTION[^>]*(>|[^>]*)", "<OPTION>");
		s = s.replaceAll("<INPUT[^>]*(>|[^>]*)", "<INPUT>");
		return s;
	}

	public static String[] getOriginalStringFromFile(String filename)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(new File(filename));
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader in = new BufferedReader(isr);
		String s = "";
		while ((s = in.readLine()) != null) {
			if (s.indexOf("<SCRIPT") != -1) {// 跳过Script区域
				while (s.indexOf("</SCRIPT>") == -1){
					s = in.readLine();
					if(s == null) break;
				}
				s = in.readLine();
				if(s == null)break;
			}
			if (s.indexOf('>') == -1 && s.indexOf('<') == -1)// 不含有<或>的行直接跳过
				continue;
			if (s.indexOf(">=") != -1 || s.indexOf("<=") != -1
					|| s.indexOf("<>") != -1 || s.indexOf("<<") != -1
					|| s.indexOf(">>") != -1)// 排除比较运算符的干扰
				continue;

			s = getNomalizedTags(s);
			System.out.println(s);
			s = removeRightSemiTag(s);
			sb.append(s.trim().replaceAll("\\s+", "").replaceAll("<A>", "")
					.replaceAll("</A>", ""));// 把超级连接变成普通文本
			// System.out.println(s);
		}
		String sistring = sb.toString().replaceAll("><", "> <");
		// String sistring = sb.toString();
		System.out.println("------------------------------");
		System.out.println(sistring);
		System.out.println("------------------------------");
		String[] ss = sistring.split(" ");
		fis.close();
		isr.close();
		in.close();
		return ss;
	}

	public static String[] getOriginalTagsFromFile(String filename)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(new File(filename));
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader in = new BufferedReader(isr);
		String s = "";
		while ((s = in.readLine()) != null) {
			if (s.indexOf("<SCRIPT") != -1) {// 跳过Script区域
				while (s.indexOf("</SCRIPT>") == -1){
					s = in.readLine();
					if(s == null) break;
				}
				s = in.readLine();
				if(s == null)break;
			}
			if (s.indexOf('>') == -1 && s.indexOf('<') == -1)// 不含有<或>的行直接跳过
				continue;
			if (s.indexOf(">=") != -1 || s.indexOf("<=") != -1
					|| s.indexOf("<>") != -1 || s.indexOf("<<") != -1
					|| s.indexOf(">>") != -1)// 排除比较运算符的干扰
				continue;
			s = getNomalizedTags(s);
			s = removeRightSemiTag(s); // 删除以XXX>开头的行
			// s = s.replaceAll("<!--.+>", " ");
			System.out.println(s);
			sb.append(s.trim().replaceAll("\\s+", "").replaceAll("<A>", "")
					.replaceAll("</A>", ""));// 把超级连接变成普通文本
			// System.out.println(s);
		}
		System.out.println(sb.toString());
		String sistring = sb.toString().replaceAll(">[^<^>]*<", "><TXT/><");
		System.out.println(sistring);
		sistring = sistring.replaceAll("><", "> <");
		String[] ss = sistring.split(" ");
		return ss;
	}

	public static String removeRightSemiTag(String s) {
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[0] == '<')
				break;
			// if(c[i]=='\n')
			// break;
			if (c[i] == '<')
				break;
			if (c[i] == '>') {
				for (int j = 0; j < i + 1; j++)
					c[j] = ' ';
				break;

			}
		}
		s = String.valueOf(c);
		return s;
	}

	public static void main(String[] args) throws Exception {
		TagMap tm = new TagMap();
		System.out
				.println(tm
						.removeRightSemiTag("HREF=\"HTTP://TECH.SINA.COM.CN/FOCUS/SINAHELP.SHTML\">产品答疑</A> "));
		// String[] tags = tm.getOriginalTagsFromFile("e:\\htmlsnip.txt");
		// ArrayPrinter.printArray(tm.mapTagToCodedAlph(tags));
		// System.out.println();
		// String[] alph = tm.mapTagToCodedAlph(tags);
		// // ArrayPrinter.printArray(tm.mapCodedAlphToTag(alph));
		// String s = tm.getInformationBlock("e:\\600030.html",
		// "e:\\600031.html");
		// System.out.println(s);

	}
}
