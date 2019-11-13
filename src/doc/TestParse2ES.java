package doc;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;

public class TestParse2ES {
    public static void main(String args[]) throws Exception {
//    	System.out.println(download());
    	System.out.println(parseDocAndToEs());
	}
    
    private static boolean download() throws Exception {
    	String host = "192.168.16.9";
		int port =22;
		String user = "sinobest";
		String pass = "sinobest";
		String fileName = "化学.doc";
		String filePath = "化学科(1-1-6)";
		String localTmpPath = "D:/download/20191030";
		
		ChannelSftp channel = SFTPUtil.getChannel(host, port, user, pass);
		SFTPUtil.downloadFile(channel, filePath, fileName, localTmpPath);
		channel.disconnect();
		return true;
    }
    
    private static boolean parseDocAndToEs() throws Exception{
		String esAddress="192.168.11.121";
		int esPort=9300;
		String index="document";
		String type="_doc";
		
		String subjectCode = "1-1-4";
		String now = "2019-11-11 11:13";
		String filePath = "D:/download/20191030/";
		String fileName = "化学.doc";
		int operType = 0; //0-add, 1-update, 2-delete
		
		String esName="";
		String pipeLine = null;

		String fileFullName = filePath+fileName;
		ESRequestUtil request = new ESRequestUtil(esAddress, esPort, esName);
		request.connect();
		
		String content = DocUtil.getContentFromStream(new FileInputStream(new File(fileFullName)));
		Map<String, String> map = new HashMap();
		map.put("author", "sdi");
		map.put("content", content);
//		map.put("create_date", now);
		map.put("document_path_url", fileFullName);
//		map.put("id", "");
		map.put("modify_date", now);
//		map.put("sinobest_entry_es_time", now);
		map.put("subjectcode", subjectCode);
		map.put("title", fileName);
		map.put("type", fileName.substring(fileName.lastIndexOf(".")+1));
//		map.put("url", "");
		
		if(operType == 0) {
			System.out.println(request.addDocument(index, type, map, pipeLine, fileFullName));//新增
		} else if (operType == 1) {
			request.updateDocument(index, type, fileFullName, map, null);//修改
		} else if (operType == 2) {
			request.deleteDocumentById(index, type, fileFullName, null);//删除
		}
		request.disconnect();
		return true;
	}
}
