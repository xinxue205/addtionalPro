package test;

public class TestException {
	public static void main(String args[]) {
		int[] arr = {1,2,3};
		System.out.println(arr[2]);
		try {
			System.out.println(2/0);
		} catch (ArithmeticException ab) {
			System.out.println("System is maitance...");
			ab.getMessage();
		}
	}
} 