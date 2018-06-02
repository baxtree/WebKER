package jackteng.pattree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluation {
	public static int[] startposition;

	public static String patalph;

	public static String filestring;

	public static int[] distance;
	
	public static int divisor;

	public Evaluation(String patalph, String filestring) {

		this.patalph = patalph;
		this.filestring = filestring;
//		System.out.println(patalph.length() + " " + filestring.length());
		Pattern p = Pattern.compile(patalph);
		Matcher m = p.matcher(filestring);
		int n = 0;
		while (m.find())
			n++;
		startposition = new int[n];
		m.reset();
		for (int i = 0; m.find(); i++) {
			startposition[i] = m.start();
//			System.out.println(startposition[i]);
		}
		distance = new int[startposition.length - 1];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = startposition[i + 1] - startposition[i];
		}
		divisor = distance.length;//除零判断
	}
	
	public int getdivisor(){
		return divisor;
	}

	public static double agv(int[] in) throws Exception{
		int result = 0;
		for (int i = 0; i < in.length; i++)
			result += in[i];
		return result / in.length;
	}

	public static double getRegularity() throws Exception{//大于0且越小越好

		double a = agv(distance);
		double temp = 0.0;
		for (int i = 0; i < distance.length; i++) {
			temp += Math.pow(distance[i] - a, 2);
		}
		double standarddeviation = Math.sqrt(temp / distance.length);
		double regularity = standarddeviation / a;
		return regularity;
	}

	public static double getLocality() throws Exception{//越大越好
		int up = (startposition.length * patalph.length());
		int down = (startposition[startposition.length - 1] - startposition[0] + patalph
				.length());
		return (double) up / (double) down;
	}

	public static double getVicinity() throws Exception{//大于1且小于2
		return patalph.length() / agv(distance);
	}

	public static double getCoverage() {
		return (double) (startposition[startposition.length - 1]
				+ patalph.length() - startposition[0])
				/ (double) filestring.length();
	}
	public boolean satisfyRLVC()throws Exception{//各个阀值的设定
		if(getRegularity()<0.5&&getLocality()>0.25&&getVicinity()>0.5)
			return true;
		else
			return false;
		
	}
	public boolean validStartsWith(){//以关闭标签或者<TXT/>开头的模式被认为是无效的
		char[] c = patalph.toCharArray();
		String[] s = TagMap.mapCodedAlphToTag(c);
		if(s[0].startsWith("</")||s[0].startsWith("<TXT/>")||s[0].startsWith("<DIV>"))
			return false;
		else
			return true;
	}

	public static void main(String[] args)throws Exception {
		Evaluation e = new Evaluation("bc", "abcfgabcggabc");
//		if(e.satisfyRLVC()) System.out.print("good");
//		else System.out.println("bad");
		if(e.validStartsWith()) System.out.print("good");
		else System.out.println("bad");
		
	}
}
