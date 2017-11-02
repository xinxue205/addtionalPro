package frequentClass;

public class ArrayParse {
	public static void main(String[] args) {
		Double[][] d;
		String s = "1,2;3,4,5;6,7,8";
		String[] sFirst = s.split(";");
		d = new Double[sFirst.length][];
		for (int i=0; i<sFirst.length; i++){
			String[] sSecond = sFirst[i].split(",");
			d[i] = new Double[sSecond.length];
			for (int j=0; j<sSecond.length; j++){
				double ds = Double.parseDouble(sSecond[j]);
				d[i][j] = ds;
			}
		}
		for(int i=0; i<d.length; i++){
			for (int j=0; j<d[i].length; j++){
				System.out.print(d[i][j]+ " ");
			}
			System.out.println();
		}
	}

}
