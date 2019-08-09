package ipc;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

class WriteData {
    public void writeMethod(PipedWriter out){
        try {
            System.out.println("write:");
            for (int i = 0; i < 100; i++) {
                String data=""+i;
                out.write(data);
                System.out.print(data);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadData {
    public void readMethod(PipedReader in){
        try {
            System.out.println("read:");
            char[] byteArray = new char[20];
            int readLength=in.read(byteArray);
            while (readLength!=-1){
                String newData = new String(byteArray, 0, readLength);
                System.out.print(newData);
                readLength=in.read(byteArray);
            }
            System.out.println();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadWrite extends Thread {
    private WriteData write;
    private PipedWriter out;

    public ThreadWrite(WriteData write, PipedWriter out) {
        this.write = write;
        this.out = out;
    }

    @Override
    public void run() {
        write.writeMethod(out);
    }
}

class ThreadRead extends Thread{
    private ReadData read;
    private PipedReader in;

    public ThreadRead(ReadData read, PipedReader in) {
        this.read = read;
        this.in = in;
    }

    @Override
    public void run() {
        read.readMethod(in);
    }
}

public class PipeTest {
    public static void main(String[] args) {
        try {
            WriteData writeData = new WriteData();
            ReadData readData = new ReadData();

            PipedWriter pipedWriter = new PipedWriter();
            PipedReader pipedReader = new PipedReader();
            //通过这一行使得输入流和输出流联系起来
            pipedReader.connect(pipedWriter);

            ThreadWrite threadWrite = new ThreadWrite(writeData, pipedWriter);
            threadWrite.start();

            Thread.sleep(2000);

            ThreadRead threadRead = new ThreadRead(readData, pipedReader);
            threadRead.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}