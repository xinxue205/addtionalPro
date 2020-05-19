package IO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Duration;
import java.time.Instant;

/**
 * Channel�Ƕ�I/O�����ķ�װ��FileChannel�����ByteBuffer������д�����ݻ��浽�ڴ��У�Ȼ��������/����ķ�ʽread/write��ʡȥ�˷���������ʱ���ظ��м���������ݴ��ļ�ʱ�����������Ч�ʣ���Stream��byte���鷽ʽ��ʲô���𣿾������ԣ�Ч���ϼ���������
 * @author Administrator
 *
 */
public class ChannelStreamTest {

    public static void main(String[] args) {
        // 4GB������
        File sourceFile = new File("d://dd.iso");
        File targetFile = new File("d://ee.iso");
        targetFile.deleteOnExit();
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // stream��ʽ
        ChannelStreamTest.copyFileByStream(sourceFile, targetFile);

        // channel��ʽ
//        FileChannelTest.copyFileByFileChannel(sourceFile, targetFile);
    }

    /**
     * channel��ʽ
     *
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFileByFileChannel(File sourceFile, File targetFile) {
        Instant begin = Instant.now();

        RandomAccessFile randomAccessSourceFile;
        RandomAccessFile randomAccessTargetFile;
        try {
            // ����RandomAccessFile�����ڻ�ȡFileChannel
            randomAccessSourceFile = new RandomAccessFile(sourceFile, "r");
            randomAccessTargetFile = new RandomAccessFile(targetFile, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        FileChannel sourceFileChannel = randomAccessSourceFile.getChannel();
        FileChannel targetFileChannel = randomAccessTargetFile.getChannel();

        // ����1MB�Ļ���ռ�
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        try {
            while (sourceFileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                targetFileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sourceFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                targetFileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("total spent " + Duration.between(begin, Instant.now()).toMillis());
    }

    /**
     * stream��ʽ
     *
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFileByStream(File sourceFile, File targetFile) {
        Instant begin = Instant.now();

        FileInputStream fis;
        FileOutputStream fos;
        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        // ʹ��byte�����ȡ��ʽ������1MB����
        byte[] readed = new byte[1024 * 1024];
        try {
            while (fis.read(readed) != -1) {
                fos.write(readed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("total spent " + Duration.between(begin, Instant.now()).toMillis());
    }
}
