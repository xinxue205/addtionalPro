package test;
import java.util.ArrayList;


public class GongYueShu {
	public static void main(String[] args) {
		int a = 256;
		int b = 128;
		System.out.println(qiuShu1(a,b));
		System.out.println(qiuShu2(a,b));
	}	
		static int qiuShu1(int a, int b){
			int max=0;
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			for (int i = 2; i <= a/2; i++) {
				if (a%i==0){
					list1.add(i);
				}
			}
			for (int i=1; i<=list1.size()-1;i++){
				if (b%(list1.get(i))==0){
					max=list1.get(i);
				}
			}
			return max;
		}
		
		static int qiuShu2(int a, int b) {
			int max=0;
			int k= (a>b?b:a);
			for (int i = 2; i <= k/2; i++) {
				if (a%i==0 & b%i==0){max=i; }
			}
			return max;
		}
	
	

}


