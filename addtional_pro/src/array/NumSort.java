package array;

public class NumSort {
	public static void main(String args[]) {
		int b[]={1,2,3,4};
		int[] a = new int[args.length];
		for (int i=0;i<args.length;i++) {
			a[i] = Integer.parseInt(args[i]);
		}
		print(a);
		selectionSort(a);
		print(a);
	}
	
	private static void selectionSort(int[] a) {
		int j, tem;
		for(int i=0;i<a.length;i++) {
			j = i;
			for(int k=j+1;k<a.length;k++) {
				if (a[k]<a[j]) j=k;
			}
			
			if (i != j) {
				tem = a[i];
				a[i] = a[j];
				a[j] = tem;
			}
		}
	}
	
	/*private static void selectionSort(int[] a) {
		for(int i=0;i<a.length;i++) {
			for(int j=i+1;j<a.length;j++) {
				if(a[j]<a[i]){
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;					
				}
			}	
		}	
	}*/
	
	private static void print(int[] a) {
		for(int i=0;i<a.length;i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
}
