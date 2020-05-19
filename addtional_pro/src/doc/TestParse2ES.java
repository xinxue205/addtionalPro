package doc;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

import com.jcraft.jsch.ChannelSftp;

public class TestParse2ES {
    public static void main(String args[]) throws Exception {
    	FileInputStream inputStream = new FileInputStream(new File("D:/download/车票实名制核查问题排查.docx"));
    	BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
		Metadata matadata=new Metadata();
		new AutoDetectParser().parse(inputStream, textHandler, matadata, new ParseContext());
		inputStream.close();
		System.out.println(matadata.get("Author"));
    	for (String name : matadata.names()) {                                   //查看解析出的文档的元信息
            System.out.println(name + ":" + matadata.get(name));
        }
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
		List list = new ArrayList();
		SFTPUtil.getFileList(channel, list, "体育科(1-2-10)");
//		SFTPUtil.downloadFile(channel, filePath, fileName, localTmpPath);
		channel.getSession().disconnect();
		return true;
    }
    
    private static boolean parseDocAndToEs() throws Exception{
		String esAddress="192.168.11.121";
		int esPort=9300;
		String index="document";
		String type="_doc";
		
		String subjectCode = "1-1-4";
		String now = "2019-11-11 11:13";
		String filePath = "D:\\download\\20190618\\";
		String fileName = "历史.docx";
		int operType = 0; //0-add, 1-update, 2-delete
		
		String esName="";
		String pipeLine = null;

		String fileFullName = filePath+fileName;
		ESRequestUtil request = new ESRequestUtil(esAddress, esPort, esName);
		request.connect();
		
		String content = DocUtil.getContentFromStream(new FileInputStream(new File(fileFullName)));
		System.out.println(content);
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
