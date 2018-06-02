package jackteng.pattree;

public class ArrayPrinter {
	public static void printArray(String[] s){
		for(int i = 0; i < s.length; i++){
			System.out.print(s[i]+" ");
		}
	}
	public static void printArray(int[] n){
		for(int i = 0; i < n.length; i++){
			System.out.print(n[i]+" ");
		}
	}
	public static void printArray(String[] s, boolean withspace){
		if(withspace == true)
			for(int i = 0; i < s.length; i++){
				System.out.print(s[i]+" ");
			}
		else{
			for(int i = 0; i < s.length; i++){
				System.out.print(s[i]);
			}
		}
	}
	
	public static void printArray(int[] s, boolean withspace){
		if(withspace == true)
			for(int i = 0; i < s.length; i++){
				System.out.print(s[i]+" ");
			}
		else{
			for(int i = 0; i < s.length; i++){
				System.out.print(s[i]);
			}
		}
	}

}
