package jackteng.pattree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IBExtractor {// ÌáÈ¡file1µÄinfoblock²¢´æÈëÎÄ¼þÖÐ
	private String ibs = "";

	private String ibf = "";

	public IBExtractor(String file1, String file2, String file3)
			throws Exception {
		ibf = file3;
		ibs = getInformationBlock(file1, file2);
		saveInfoBlock(ibs, file3);
	}

	public String getIBFileName() {
		return ibf;
	}

	public String trainInformationBlock(String filename1, String filename2)
			throws Exception {// ï¿½È½ï¿½}ï¿½ï¿½ï¿½ï¿½Ò³ï¿½Ä²ï¿½ï¿½ì£¬ï¿½ï¿½Í¬ï¿½Ä²ï¿½ï¿½ï¿½ï¿½ï¿½Îªï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
		StringBuffer sb = new StringBuffer();
		FileInputStream fis1 = new FileInputStream(new File(filename1));
		FileInputStream fis2 = new FileInputStream(new File(filename2));
		InputStreamReader isr1 = new InputStreamReader(fis1, "UTF-8");
		InputStreamReader isr2 = new InputStreamReader(fis2, "UTF-8");
		BufferedReader br1 = new BufferedReader(isr1);
		BufferedReader br2 = new BufferedReader(isr2);
		String s1;
		String s2;
		while ((s1 = br1.readLine()) != null && (s2 = br2.readLine()) != null) {// ï¿½ï¿½Ò»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ð¶ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ÖµÖ®ï¿½ï¿½ï¿½Ã¿Õ¸ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ü´ï¿½ï¿½ï¿½
			if (s1.equals(s2)) {
				// System.out.println(s1+" "+s2);
				sb.append("s"); // same
			} else {
				// System.out.println(s1+" "+s2);
				sb.append("d"); // different
			}
		}
		fis1.close();
		fis2.close();
		isr1.close();
		isr2.close();
		br1.close();
		br2.close();
		String s = sb.toString();
		Pattern p = Pattern.compile("s{5,}"); // ÈÏÎªÁ¬Ðø³öÏÖnÐÐÏàÍ¬µÄÐÅÏ¢¿éÊÇ²»ÖØÒªµÄ
		Matcher m = p.matcher(s);
		// int count = 0;

		while (m.find()) {
			StringBuffer sub = new StringBuffer();
			// System.out.println(m.group());
			int n = m.group().length();
			for (int i = 0; i < n; i++) {
				sub.append("o");
			}
			System.out.println(sub.toString());
			String regex = "s{" + n + "}";
			s = s.replaceAll(regex, sub.toString());
			// count++;
		}
		// System.out.println(count);
		System.out.println(s);
		// System.out.println(sb.toString());
		return s;

	}

	public String getInformationBlock(String filename1, String filename2)
			throws Exception {
		char[] ibchararray = trainInformationBlock(filename1, filename2)
				.toCharArray();
		System.out.println(String.valueOf(ibchararray));
		StringBuffer sb = new StringBuffer();
		FileInputStream fis = new FileInputStream(new File(filename1));
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader in = new BufferedReader(isr);
		String b = "";
		int i = 0;
		while ((b = in.readLine()) != null && i < ibchararray.length) {
			if (ibchararray[i] == 'o') {
				i++;
				continue;
			} else {
				sb.append(b);
				sb.append("\n");
				i++;
			}

		}
		isr.close();
		fis.close();
		in.close();
		return sb.toString().trim();

	}

	public void saveInfoBlock(String s, String filename) throws Exception {// infoblockÎÄ¼þÀï´æµÄÊÇfilename1ÎÄ¼þµÄinfoblock
		FileOutputStream fos = new FileOutputStream(new File(filename));
		OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(s);
		bw.close();
		fos.close();
		osw.close();
		bw.close();
	}

	public static void main(String[] args) throws Exception {
		IBExtractor ibe = new IBExtractor("e:\\600030.html", "e:\\600031.html",
				"e:\\ibo.txt");
		System.out.print(ibe.getIBFileName());
	}

}
