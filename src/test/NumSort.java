package test;

public class NumSort {
	public static void main(String[] args) {
		int a[] = new int[args.length]; 
		for (int i=0; i<a.length; i++){
			a[i]= Integer.parseInt(args[i]);
		}
		int b[]={7,4,5,3,2,9};
		int c[]={7,4,5,3,2,9};
		print(a);
		sort1(a);
		System.out.print("Method 1£º");print(a);
		sort2(b);
		System.out.print("Method 2£º");print(b);
		sort3(c);
		System.out.print("Method 3£º");print(c);
	}
	
	private static void sort1(int a[]){
		for(int i=0; i<a.length; i++){
			int tem;
			for(int j=i+1; j<a.length; j++){
				if(a[i]>a[j]){
					tem = a[i];
					a[i] = a[j];
					a[j] = tem;
				}
			}
		}
	}
	
	private static void sort2(int a[]){
		int tem;
		for(int i=0; i<a.length; i++){
			int j=i;
			for(int k=j+1; k<a.length; k++){
				if(a[k]<a[j]){
					j = k;
				}
			}
			
			if(i != j){
				tem = a[i];
				a[i] = a[j];
				a[j] = tem;
			}
		}
	}
	
	private static void sort3(int a[]){
		for(int i=a.length-1; i>=0; i--){
			int tem;
			for(int j=0;j<=i-1;j++){
				if(a[i]<a[j]){
					tem = a[i];
					a[i]=a[j];
					a[j]=tem;
				}
			}
		}
	}
	
	private static void print(int a[]){
			for (int i=0; i<a.length; i++){
				System.out.print(a[i]+" ");
			}
			System.out.println();
		}
}
