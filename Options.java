public class Options{
	//public static final String[] options = {"allUpper", "allLower", "reverse", "capitalize", "reg"};
	private static final int length = 14;
	public static String allUpper(String s){
		return s.toUpperCase();
	}
	public static String allLower(String s){
		return s.toLowerCase();
	}
	public static String reverse(String s){
		char[] output = new char[s.length()];
		for(int i = 0; i < s.length(); i++){
			output[(s.length() - (1+i))] = s.charAt(i);
		} 
		return new String(output);
	}
	public static String capitalize(String s){
		return s.replace(s.charAt(0), (char)(s.charAt(0)-32));
	}
	public static String duplicate(String s){
		return s+s;
	}
	public static String reflect(String s){
		return s+reverse(s);
	}
	public static String deleteLast(String s){
		return s.substring(0, s.length()-1);
	}
	public static String deleteFirst(String s){
		return s.substring(1);
	}
	public static String toggle0(String s){
		char[] a = s.toCharArray();
		for(int i = 0; i < a.length; i += 2){
			if(a[i] >= 97)
				a[i] -= 32;
			else
				a[i] += 32;
		}
		return new String(a);
	}
	public static String toggle1(String s){
		char[] a = s.toCharArray();
		for(int i = 1; i < a.length; i += 2){
			if(a[i] >= 97)
				a[i] -= 32;
			else
				a[i] += 32;
		}
		return new String(a);
	}
	//these are going to be tricky to automate. 
	public static String prepend(String s, char c){
		return c + s;
	}
	public static String append(String s, char c){
		return s + c;
	}
	public static String nCapitalize(String s, char n){
		int length = s.length();
		char[] a = s.toCharArray();
		while(n < length){
			a[n++] -= 32;
		}
		return new String(a);
	}
	public static int length(){
		return length;
	}
	//public static void main(String[] args){
		//System.out.println(deleteFirst("Scott"));
		//System.out.println(duplicate("Scott"));
		//System.out.println(capitalize("scott"));
		//System.out.println(reflect("stringgnirts"));
		//System.out.println(nCapitalize("scott", 5));
		//System.out.println(toggle1("scottstephenss"));
	//}
}