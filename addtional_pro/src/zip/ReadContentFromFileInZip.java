package zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class ReadContentFromFileInZip {
	public static void main(String[] args) throws Exception {
		List versions = new ArrayList();
		versions.add("1.1");
		versions.add("1.2");
		versions.add("1.3");
		versions.add("1.4");
		
		String currVersion="1.4";
		int fromIndex = 0;
		for (int i = 0; i < versions.size(); i++) {
			String curr = (String) versions.get(i);
			if(curr.equals(currVersion)) {
				fromIndex = i; break;
			}
		}
		List<String> subList = versions.subList(fromIndex+1, versions.size());  //获取子列表
		System.out.println(subList);
		
//		File file = new File("test.zip");
//		ZipFile zipFile = new ZipFile(file);
//		InputStream zipInputStream = zipFile.getInputStream(zipFile.getEntry("test.txt"));
//		Stream<String> result = new BufferedReader(new InputStreamReader(zipInputStream))
//				.lines();
//		List<String> result1 = result.collect(Collectors.toList());
//		System.out.println(result1);
	}
}
