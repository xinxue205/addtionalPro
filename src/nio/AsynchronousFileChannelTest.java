package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsynchronousFileChannelTest {
	public static void main1(String[] args) throws Exception {
		Path path = Paths.get("data/test-write.txt");
		if(!Files.exists(path)){
		    Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = 
		    AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		Future<Integer> operation = fileChannel.write(buffer, position);
		buffer.clear();

		while(!operation.isDone());

		System.out.println("Write done");
	}
	
	public static void main(String[] args) throws Exception {
		Path path = Paths.get("e:/test/test-write.txt");
		if(!Files.exists(path)){
		    Files.createFile(path);
		}
		AsynchronousFileChannel fileChannel = 
		    AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		buffer.put("test data".getBytes());
		buffer.flip();

		fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

		    @Override
		    public void completed(Integer result, ByteBuffer attachment) {
		        System.out.println("bytes written: " + result);
		        try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		    @Override
		    public void failed(Throwable exc, ByteBuffer attachment) {
		        System.out.println("Write failed");
		        exc.printStackTrace();
		    }
		});
	}
}
