package practise50;

public class Practise2 {
	public static void main(String[] args) {
		for (int i = 101; i <= 120; i+=2) {
			boolean f = true;
			for(int j=2;j<Math.sqrt(i);j++){
				if(i%j==0){
					f = false;
					break;
				}
			}
			if (f){
				System.out.println(i);
			}
			
			
		}
		
	}
}
