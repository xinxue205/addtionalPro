package java8;

import java.util.HashMap;


public class MethodRefTest3 {
	@FunctionalInterface
    public interface Processor{
		boolean process(String str1, String str2) ;
    }
	
	 public static String contackStr(String str1, String str2) {
		return str1+str2;
	 }
	 
	 public static boolean findStr(String target, String source) {
		 return source.indexOf(target)>0;
	 }
	 
	 
	public static void main(String[] args) {
		HashMap<Integer, Processor> processorMap = new HashMap<>();
		processorMap.put(1, MethodRefTest3::findStr);
		boolean s = processorMap.get(1).process("111", "22211");
		System.out.println(s);
	}
}
