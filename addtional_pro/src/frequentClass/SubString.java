package frequentClass;

public class SubString {
	public static void main(String[] args) {
		String fullPath="D:/temp/ฮารว.JPG";
		String path = fullPath.substring(0,fullPath.lastIndexOf("/"));
		String fileName = fullPath.substring(fullPath.lastIndexOf("/")+1,fullPath.lastIndexOf("."));
		String fileType = fullPath.substring(fullPath.lastIndexOf(".")+1).toLowerCase();
		System.out.println(fileName.getBytes().length);
		System.out.println(path);
		System.out.println(fileName);
		System.out.println(fileType);
	}
}
