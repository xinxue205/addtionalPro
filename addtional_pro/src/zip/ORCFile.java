package zip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

public class ORCFile {
	 
	public static void main(String[] args) throws Exception {
		//����ORC���ݽṹ������ṹ
//		CREATE TABLE lxw_orc1 (
//		 field1 STRING,
//		 field2 STRING,
//		 field3 STRING
//		) stored AS orc;
		TypeDescription schema = TypeDescription.createStruct()
				.addField("field1", TypeDescription.createString())
		        .addField("field2", TypeDescription.createString())
		        .addField("field3", TypeDescription.createString());
		//���ORC�ļ����ؾ���·��
		String lxw_orc1_file = "D:\\test\\lxw_orc1_file.orc";
		Configuration conf = new Configuration();
		FileSystem.getLocal(conf);
		Writer writer = OrcFile.createWriter(new Path(lxw_orc1_file),
				OrcFile.writerOptions(conf)
		          .setSchema(schema)
		          .stripeSize(67108864)
		          .bufferSize(131072)
		          .blockSize(134217728)
		          .compress(CompressionKind.ZLIB)
		          .version(OrcFile.Version.V_0_12));
		//Ҫд�������
		String[] contents = new String[]{"1,a,aa","2,b,bb","3,c,cc","4,d,dd"};
		
		VectorizedRowBatch batch = schema.createRowBatch();
		for(String content : contents) {
			int rowCount = batch.size++;
			String[] logs = content.split(",", -1);
			for(int i=0; i<logs.length; i++) {
				((BytesColumnVector) batch.cols[i]).setVal(rowCount, logs[i].getBytes());
				//batch full
				if (batch.size == batch.getMaxSize()) {
				    writer.addRowBatch(batch);
				    batch.reset();
				}
			}
		}
		writer.addRowBatch(batch);
		writer.close();
		
	}
 
}
