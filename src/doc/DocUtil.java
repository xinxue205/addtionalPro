package doc;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;

public class DocUtil {
	public static String getContentFromStream(InputStream inputStream) throws Exception {
		BodyContentHandler textHandler=new BodyContentHandler(new WriteOutContentHandler(1024*1024*1024));
		Metadata matadata=new Metadata();
		new AutoDetectParser().parse(inputStream, textHandler, matadata, new ParseContext());
		inputStream.close();
		return textHandler.toString();
	}
}
