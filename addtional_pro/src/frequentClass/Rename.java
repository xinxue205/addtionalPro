package frequentClass;

import java.io.File;

public class Rename {
	public static void main(String[] args) {
		File f = new File("d:/A");
		File[] fl = f.listFiles();
		File nf = new File("d:/A/"+"DD");
		fl[1].renameTo(nf);
	}
}
