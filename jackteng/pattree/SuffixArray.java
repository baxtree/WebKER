package jackteng.pattree;

import jackteng.util.KStrings;
import jackteng.util.StringArray;
import jackteng.util.TimeWatch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

class SuffixArrayComparator implements Comparator {
	char[] t;

	int i1, i2;

	SuffixArrayComparator(char[] a) {
		t = a;
	}

	public int compare(Object o1, Object o2) {
		i1 = ((Integer) o1).intValue();
		i2 = ((Integer) o2).intValue();
		if (i1 == i2) {
			return 0;
		}
		for (int i = 0; true; i++) {
			if (i1 + i >= t.length) {
				return -1;
			}
			if (i2 + i >= t.length) {
				return 1;
			}
			if (t[i1 + i] > t[i2 + i]) {
				return 1;
			}
			if (t[i1 + i] < t[i2 + i]) {
				return -1;
			}
		}
	}
}

/**
 * A text array with an index allowing to find sections of text in logarithmic
 * time.
 */
public class SuffixArray {

	String text; //输入的待建suffix array的文本

	int[] sa; //suffix array

	static char separator = ' '; //分隔符是空格

	static char asterisk = '*';  //*word* 用来表示模式

	TimeWatch t = new TimeWatch();

	/**
	 * 为一个词数组建立一个suffix array
	 * Creates a suffix array for an array of words.
	 */
	public int[] getSuffixArray(){
		return sa;
	}
	public SuffixArray(String[] s) {
		int i, j, l, len = 1;

		t.reset();

		for (i = 0; i < s.length; i++) {
			len += s[i].length() + 1;
		}

		char[] a = new char[len];
		Integer[] index = new Integer[len];
		sa = new int[len];

		l = 1;
		a[0] = separator;
		for (i = 0; i < s.length; i++) {
			for (j = 0; j < s[i].length(); j++) {
				a[l + j] = s[i].charAt(j);
			}
			a[l + j] = separator;
			l += s[i].length() + 1;
		}

		text = new String(a);

		Object txt[] = { new Integer(s.length), new Integer(len) };
//		System.out.println(MessageFormat.format(KStrings
//				.getString("SuffixArray_created_words_length"), txt));

		for (i = 0; i < len; i++) {
			index[i] = new Integer(i);
		}

		SuffixArrayComparator c = new SuffixArrayComparator(a);
		Arrays.sort(index, c);

		for (i = 0; i < len; i++) {
			sa[i] = index[i].intValue();
		}

//		t.display(KStrings.getString("SuffixArray_create_"));
	}

	/**
	 * 从过去建立的文档中读取一个suffix array
	 * Loads a suffix array from previously saved files.
	 * 
	 * @param basename 包含文本向量的文件的名字
	 *            name of the file containing the text array
	 * @param idxname  包含索引的文件的名字
	 *            name of the file containing the index
	 * @param encoding 字符编码方式
	 *            text encoding
	 */
	public SuffixArray(String basename, String idxname, String encoding) {
		int len;
		t.reset();
		try {
			DataInputStream d;
			d = new DataInputStream(new BufferedInputStream(
					new FileInputStream(idxname)));
			len = d.readInt();
			sa = new int[len];
			for (int i = 0; i < len; i++) {
				sa[i] = d.readInt();
			}
			d.close();
		} catch (IOException e) {
//			System.err.println(KStrings.getString("SuffixArray_")
//					+ e.getMessage());
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			BufferedReader d;
			d = new BufferedReader(new InputStreamReader(new FileInputStream(
					basename), encoding));
			text = d.readLine();
			d.close();
		} catch (IOException e) {
//			System.err.println(KStrings.getString("SuffixArray_")
//					+ e.getMessage());
			System.out.println(e.getStackTrace());
			return;
		}

		if (text.length() != len) {
			Object txt[] = { new Integer(text.length()), new Integer(len) };
//			System.err.println(MessageFormat.format(KStrings
//					.getString("SuffixArray_length_mismatch"), txt));
			
		}

		t.display(KStrings.getString("SuffixArray_load_"));
		Object txt1[] = { new Integer(len) };
//		System.out.println(MessageFormat.format(KStrings
//				.getString("SuffixArray_loaded"), txt1));
	}
	//将文本数组，索引，编码方式存入文件中保存
	public void save(String basename, String idxname, String encoding) {
		t.reset();
		try {
			DataOutputStream o;
			o = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(idxname)));
			o.writeInt(sa.length);
			for (int i = 0; i < sa.length; i++) {
				o.writeInt(sa[i]);
			}
			o.close();
		} catch (IOException e) {
//			System.err.println(KStrings.getString("SuffixArray_save_error_")
//					+ e.getMessage());
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			PrintStream o;
			o = new PrintStream(new FileOutputStream(basename), true, encoding);
			o.println(text);
			o.close();
		} catch (IOException e) {
//			System.err.println(KStrings.getString("SuffixArray_save_error_")
//					+ e.getMessage());
			System.out.println(e.getStackTrace());
		}
//		t.display(KStrings.getString("SuffixArray_save_"));
	}

	int compare(String s, int n) {
		for (int k = 0; k < s.length(); k++) {
			if (s.charAt(k) > text.charAt(sa[n] + k)) {
				return -1;
			}
			if (s.charAt(k) < text.charAt(sa[n] + k)) {
				return 1;
			}
		}
		return 0;
	}

	String getWord(int n) {
		int k, l;
		for (k = sa[n]; text.charAt(k) != separator; k--) {
			;
		}
		for (l = sa[n] + 1; text.charAt(l) != separator; l++) {
			;
		}
		return text.substring(k + 1, l);
	}

	public TreeSet matchesSimple(String s, int imax) {
		int len = sa.length - s.length();
		int i = 0, j = len;
		int m = 0, c = 1;

		while (j > i) {
			m = (i + j) / 2;
			c = compare(s, m);
			if (c == -1) {
				i = m + 1;
				continue;
			}
			if (c == 1) {
				j = m;
				continue;
			}
			break;
		}

		if (c != 0) {
			return null;
		}

		for (i = m; i > 0 && compare(s, i) == 0; i--) {
			;
		}
		for (j = m; j < len && compare(s, j) == 0; j++) {
			;
		}
		i++;

		if (j > i + imax) {
			j = i + imax;
		}

		TreeSet wynik = new TreeSet();
		for (m = i; m < j; m++) {
			wynik.add(getWord(m));
		}

		return wynik;
	}

	/**
	 * Wrapper for {@link #matchesSimple(String, int)}, removing asterisks from
	 * a query and adding separators if necessary.
	 * 
	 * @return words matching the pattern
	 */
	public TreeSet matchesPattern(String q, int imax) {
		if (q.length() < 1) {
			return null;
		}

		if (q.charAt(0) == asterisk) {
			q = q.substring(1);
		} else {
			q = separator + q;
		}

		if (q.length() == 0)
			return matchesSimple(q, imax);

		if (q.charAt(q.length() - 1) == asterisk) {
			q = q.substring(0, q.length() - 1);
		} else {
			q = q + separator;
		}

		return matchesSimple(q, imax);
	}

	public TreeSet matchesAny(String query, int imax) {
		String[] q;
		TreeSet rv, result = new TreeSet();

		t.reset();

		q = query.split(" ");

		for (int i = 0; i < q.length; i++) {
			rv = matchesPattern(q[i], imax);
			if (rv != null) {
				result.addAll(rv);
			}
			if (result.size() >= imax) {
				break;
			}
		}
//		t.display(KStrings.getString("SuffixArray_matches_"));
		return result;
	}

	/**
	 * Wrapper for matchesAny(). Returns result as a proper String array (but no
	 * more than imax words).
	 * 
	 * @param query
	 *            a list of patterns separated by spaces
	 * @param imax
	 *            maximum number of results allowed, -1 for no limit
	 * @return words matching any of the patterns
	 */
	public String[] matches(String query, int imax) {
		if (imax < 0) {
			imax = sa.length;
		}
		return (String[]) matchesAny(query, imax).toArray(new String[0]);
	}

	/**
	 * matches() with infinite number of results allowed.
	 * 
	 * @param query
	 *            a list of patterns separated by spaces
	 * @return words matching any of the patterns
	 */
	public String[] matches(String query) {
		return matches(query, sa.length);
	}
	public void printArray(String[] s){
		for(int i = 0; i < s.length; i++){
			System.out.print(s[i]+" ");
		}
	}
	public void printArray(int[] n){
		for(int i = 0; i < n.length; i++){
			System.out.print(n[i]+" ");
		}
	}
	public static void main(String[] args) {
		SuffixArray suffixArray;

		if (args.length > 0) {
			String[] data = StringArray.load(args[0]);
			if (data == null) {
				System.exit(1);
			}
			System.out.println(data);
			suffixArray = new SuffixArray(data);
			System.out.println(suffixArray.text);
			suffixArray.printArray(suffixArray.matches("*bad*", 3));//将含有bad的字串输出，不超过3个
			System.out.println();
			suffixArray.printArray(suffixArray.getSuffixArray());
		}
	}
}
