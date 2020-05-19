package test;
public class CountQuit {
	public static void main(String[] args) {
		KidCircle kc = new KidCircle(500);
		System.out.println(kc.hashCode());
		int countNum = 0;
		Kid k = kc.first;
		while(kc.count>1){
			countNum ++;
			if(countNum == 3){
				countNum = 0;
				kc.delete(k);
			}
			k = k.right;
		}
		System.out.println(k.id);
	}
}

class Kid{
	int id;
	Kid left;
	Kid right;
}

class KidCircle{
	int count = 0;
	Kid first, last;
	KidCircle(int n){
		for(int i=0; i<n; i++){
			add();
		}
	}
	
	void add(){
		Kid k = new Kid();
		k.id = count;
		if(count <=0 ){
			first = k;
			last = k;
			k.left = k;
			k.right = k;
		} else {
			last.right = k;
			k.left = last;
			k.right = first;
			first.left = k;
			last = k;					
		}
		count ++;
	}
	
	void delete(Kid k){
		if(count <= 0) {
			System.out.println("Wrong operation");
		} else if (count ==1){  first = last = null;
		} else {
			k.left.right = k.right;
			k.right.left = k.left;
			if(k == first){
				first = k.right;
			} else if(k == last){
				last = k.left;
			}
		}
		count --;
		
	}
}



/*public class CountQuit {
	public static void main(String[] args) {
		boolean[] arr = new boolean[500];
		for(int i=0; i<arr.length; i++){
			arr[i]=true;
		}
		
		int leftCount=arr.length;
		int countNum = 0;
		int index = 0;
		
		while(leftCount>1){
			if(arr[index] == true){
				countNum++;
				if (countNum == 3){
					countNum=0;
					arr[index]=false;
					leftCount--;
				}
			}
			index++;
			if (index == arr.length){
				index=0;
			}
		}
		for(int i=0; i<arr.length; i++){
			if(arr[i]==true){
			System.out.println(i);
			}
		}
	}

}*/
