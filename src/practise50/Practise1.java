package practise50;

public class Practise1 {
	public static void main(String[] args) {
		int[] n = new int[6];//ÔÂÊı
		int i = 0;
		n[0]=1;
		n[1]=1;
		for (i=2;i<=n.length-1;i++){
			n[i]=n[i-2]+n[i-1];
		}
		System.out.println(n[n.length-1]);
	}
	
	
}


