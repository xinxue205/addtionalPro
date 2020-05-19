package test;

public class TestArgs {
	public static void main(String args[]) {
		if(args.length<3) {
			System.out.println("Uses java Test \"n1\" \"op\" \"n2\"")	;
			System.exit(0);
		}	
		int d1 = Integer.parseInt(args[0]);
		int d2 = Integer.parseInt(args[2]);
		int d = 0;
		if(args[1].equals("+")) d = d1 + d2;
		else if(args[1].equals("-")) d = d1 - d2;
		else if(args[1].equals("x")) d = d1 * d2;
		else if(args[1].equals("/")) d = d1 / d2;
		else {
			System.out.println("Error operation!");
			System.exit(-1);
		}
		System.out.println(d);
	}
}