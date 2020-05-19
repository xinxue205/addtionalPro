
public class ChildName {
	
	static String[] yunmu = {"a","o","e","i","u","จน"
			,"ai","ei","ao","ou","ia","ie","ua","uo","จนe","iao","iou","uai","ue"
			,"an","ian","uan","en","จนn","ang","iang","uang","eng","ing","ueng","ong","iong"
	};
	
	static String[] shengmu = {"b","c","d","f","g","h","j","k","l","m","p","q","r","s","t","w","x","y","z"};
	
	public static void main(String[] args) {
		System.out.println(yunmu.length*shengmu.length);
		for (int i = 0; i < shengmu.length; i++) {
			String sheng = shengmu[i];
			for (int j = 0; j < yunmu.length; j++) {
				String yun = yunmu[j];
				System.out.print(sheng+yun+", ");
			}
			System.out.println("");
		}
	}
}
