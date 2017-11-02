package frequentClass;

import java.io.File;
import java.io.IOException;

public class TestFile {
	public static void main(String[] args) {
		String fileName = "myfile.txt";
		String directory = "mydir1/mydir2";
		File f = new File(directory, fileName);
		if (f.exists()){
			System.out.println("file name:" + f.getAbsolutePath());
			System.out.println("file size:" + f.length());
		} else {
			f.getParentFile().mkdirs();
			
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
