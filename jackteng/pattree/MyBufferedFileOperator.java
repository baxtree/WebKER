package jackteng.pattree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MyBufferedFileOperator {
	private static BufferedReader br;
	private static BufferedWriter bw;
//	public MyUTF8FileWriter(String filename){
//		this.filename = filename;
//	}
	public static BufferedReader getBufferedReader(String filename, String encoding)throws Exception{
		br = null;
		FileInputStream fis = new FileInputStream(new File(filename));
		InputStreamReader isr = new InputStreamReader(fis, encoding);
		br = new BufferedReader(isr);
		fis.close();
		isr.close();
		return br;
	}
	
	public static BufferedWriter getBufferedWriter(String filename, String encoding)throws Exception{
		bw = null;
		FileOutputStream fos = new FileOutputStream(new File(filename));
		OutputStreamWriter osr = new OutputStreamWriter(fos, encoding);
		bw = new BufferedWriter(osr);
		fos.close();
		osr.close();
		return bw;
	}
}
