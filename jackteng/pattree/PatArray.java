package jackteng.pattree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatArray {

	private int[] patarray; // = { 9, 1, 16, 8, 13, 5, 10, 2, 15, 7, 12, 4, 14,

	// 6, 11, 3 };

	private int[] testP;

	// String[] S = { "000", "010", "111", "011", "010", "111", "011", "001",
	// "000", "010", "111", "011", "010", "111", "011", "001" };//����
	String[] tagarray; // =

	// {"a","c","e","d","c","e","d","b","a","c","e","d","c","e","d","b"};

	String[] filestring; // = { "acedcedbacedcedb"};

	public int[] a;

	public int[] b;

	// private TagMap tm = new TagMap();
	public PatArray(String[] tags) {
		tagarray = new String[tags.length];
		for (int i = 0; i < tags.length; i++) {
			tagarray[i] = tags[i];
		}
		filestring = new String[1];
		for (int i = 0; i < filestring.length; i++) {
			filestring[i] = "";
		}
		for (int i = 0; i < tagarray.length; i++) {
			filestring[0] += tagarray[i];
		}
		patarray = getPatArray(filestring);
		testP = new int[tagarray.length];
		a = new int[tagarray.length + 1];
		a[0] = a[tagarray.length] = 0;
		b = new int[tagarray.length];
	}

	public int[] getPatArray(String[] s) {
		SuffixArray sa1 = new SuffixArray(s);
		// sa.printArray(sa.getSuffixArray());
		int[] savalue = sa1.getSuffixArray();
		int[] sa = new int[savalue.length - 2];
		for (int i = 0; i < sa.length; i++) {
			sa[i] = savalue[i + 2];
		}
		return sa;

	}

	public int[] getPatArray() {
		return patarray;
	}

	public int[] getAssitantArray() {

		for (int i = 1; i < a.length - 1; i++) {
			int lidx = patarray[i - 1] - 1;
			int ridx = patarray[i] - 1;
			if (tagarray[lidx].equals(tagarray[ridx])) {
				int count = 0;
				while ((lidx < tagarray.length) && (ridx < tagarray.length)) {
					if (tagarray[lidx].equals(tagarray[ridx])) {
						count++;
					} else {
						break;
					}
					lidx++;
					ridx++;
				}
				a[i] = count;
			} else {
				a[i] = 0;
			}
		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
		return a;
	}

	public int[] getPrefixStartPosition() {
		IntegerStack is = new IntegerStack();
		int m = 0;
		int t = 0;
		for (int i = 1; i < a.length; i++) {
			if (is.empty()) {
				if (a[i] > 0)
					is.push(Integer.toString(i));
			} else {
				t = Integer.parseInt((String) is.peek());
				if (a[t] < a[i]) {
					is.push(Integer.toString(i));
				} else if (a[t] == a[i]) {
					continue;
				} else {
					b[m] = Integer.parseInt((String) is.pop());
					m++;
					i--;
				}
			}

		}
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println();
		return b;
	}

	public String[] getPatterns() {
		return getPatterns(0);
	}

	public String[] getPatterns(int minlength) {
		int nonzerocount = 0;
		for (int i = 1; i < a.length - 1; i++) {
			if (a[i] == 0) {
				continue;
			} else {
				nonzerocount++;
			}
		}
		// System.out.println(nonzerocount);
		String pattern[] = new String[nonzerocount];
		int patterncount = 0;
		for (int i = 1; i < a.length - 1; i++) {
			if (a[i] == 0) {
				continue;
			} else {
				String[] p = new String[a[i]];
				int j = 0;
				String temp = "";
				int pl = b[i - 1] - 1;
				int pr = pl + a[i];
				int length = pr - pl;
				if (length >= minlength)
					if (pl >= 0) {
						if (pr > tagarray.length) {
							pr = tagarray.length;
						}
						for (int pos = pl; pos < pr; pos++) {
							p[j] = tagarray[pos] + "|";
							temp += p[j];
							j++;

						}
						pattern[patterncount] = temp;
						patterncount++;
					} else {
						continue;
					}
			}
		}
		for (int i = 0; i < nonzerocount; i++)
			if (pattern[i] != null)
				System.out.println(pattern[i]);
		System.out.println();
		return pattern;
	}

	public void printPattern(String[] p) {
		for (int i = 0; i < p.length; i++) {
			if (p[i] != null) {
				p[i] = p[i].trim();
				p[i] = p[i].replace("|", " ");
				// System.out.println(p[i]);
				// ArrayPrinter.printArray(p[i].split("|"));
				// System.out.println(p[i].length());
				ArrayPrinter.printArray(TagMap.mapCodedAlphToTag(p[i]
						.split(" ")), false);
				System.out.println();
			}
		}
	}

	public String[][] extractByRegex(String regex, String filename,
			String XMLfilename) throws Exception {
		String[] filestring = TagMap.getOriginalStringFromFile(filename);
		String s = "";
		for (int i = 0; i < filestring.length; i++)
			s += filestring[i];
		System.out.println(s);
		StringBuffer sb = new StringBuffer();
		FileOutputStream fos = new FileOutputStream(new File(XMLfilename));
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			String temp = (m.group().trim().replaceAll("\\s+", "").replaceAll(
					"(<[^<^>]+>)+?", " ")).replace("(","_").replace(")","_");
			sb.append(temp);
			bw.write(temp);
			System.out.print(temp);
		}
		System.out.println();
		System.out.println(sb.toString());
		String temp = sb.toString().replaceAll("\\s+", " ");
		if (temp.charAt(0) == ' ')
			temp = temp.substring(1);
		String[] onedimension = temp.split(" ");
		int n = onedimension.length / 2;
		String[][] twodimension = new String[n][2];
		for (int i = 0; i < twodimension.length; i++) {
			twodimension[i][0] = onedimension[2 * i];
			twodimension[i][1] = onedimension[2 * i + 1];
		}
		fos.close();
		osw.close();
		return twodimension;
	}

	public static void main(String[] args) throws Exception {
		IBExtractor ibe = new IBExtractor("e:\\test\\600010.html",// "F:\\test_data\\yahoo\\600031.htm",
				"e:\\test\\600012.html", "e:\\test\\InfoBlock.txt");// "F:\\test_data\\yahoo\\600033.htm",
		// "f:\\InfoBlock.txt");
		String[] s = TagMap.getOriginalTagsFromFile("e:\\test\\InfoBlock.txt");
		ArrayPrinter.printArray(s);
		String[] mappedstring = TagMap.mapTagToCodedAlph(s);
		PatArray pa = new PatArray(mappedstring);
		ArrayPrinter.printArray(pa.getPatArray());
		System.out.println();
		// System.out.println(pa.a.length+" "+pa.getPatArray().length);
		pa.getAssitantArray();
		pa.getPrefixStartPosition();
		String[] p = pa.getPatterns(20); // ������Ҫpattern����С����
		String fs = StringOperator.getSringFromArray(mappedstring);
		// System.out.println(fs);
		ArrayList al = new ArrayList(3);
		for (int i = 0; i < p.length; i++) {
			if (p[i] == null)
				continue;
			p[i] = p[i].trim().replace("|", "");
			Evaluation e = new Evaluation(p[i], fs);
			if (e.getdivisor() == 0)
				continue;
			if (e.satisfyRLVC() && e.validStartsWith()) {// ͬʱ���㷧ֵ���ƺ���Ч��ͷ��ǩ}�����
				al.add(p[i]);
				System.out.println(p[i]);
			}
		}
		String[] bestpatterns = new String[al.size()];
		// ��ɸѡ���patterns��ѡ����̵���Ϊ����pattern
		int bestidx = 0;
		int len = ((String) al.get(0)).length();
		for (int i = 0; i < bestpatterns.length; i++) {
			bestpatterns[i] = (String) al.get(i);
			if (len > bestpatterns[i].length() && bestpatterns[i].length() >= 5) {// ��������ģʽ�ĳ��Ȳ���С��5��������򵥵����<a>text<b>text</a>
				len = bestpatterns[i].length();
				bestidx = i;
			}
			ArrayPrinter.printArray(TagMap.mapCodedAlphToTag(bestpatterns[i]
					.toCharArray()));
			System.out.println();
		}
		// String a = TagMap.mapCodedAlphToTag(bestpatterns)[0];
		// System.out.print(a);
		// pa.printPattern(p);
		System.out.println();
		// pa.getPatterns(3);
		ArrayPrinter.printArray(TagMap.mapCodedAlphToTag(bestpatterns[bestidx]
				.toCharArray()));
		String wrapper = StringOperator.getSringFromArray(TagMap
				.mapCodedAlphToTag(bestpatterns[bestidx].toCharArray()));
		wrapper = StringOperator.removeSelfRepeated(wrapper);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(wrapper);
		System.out.println();
		System.out.println();
		System.out.println();
		// ������ѵ�pattern�Ѿ��ҵ�(��Ҫ�ҵ�һ���9:���4�ж��ĸ�pattern��)

		wrapper = wrapper.replace("<TXT/>", "[^<^>]*?");

		pa.extractByRegex(wrapper, "e:\\test\\InfoBlock.txt",
				"e:\\test\\matched.txt");// "f:\\InfoBlock.txt");
		// ArrayPrinter.printArray(pa.getPatArray());
		// System.out.println(Integer.parseInt("0001"));
		// System.out.println(pa.getAssitantArray().toString());
	}
}