package zip;

import net.jpountz.lz4.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by beini on 2017/10/30.
 * lz4:https://github.com/lz4/lz4-java
 */
public class LZ4Util {
	static String file ="test.lz4";
	
	public static void compress() throws Exception {
		File file1 = new File("D:\\test\\worker45\\logs\\25\\131-25-worker45.log");
		byte[]  bb = compressedByte(File2byte(file1));
		System.out.println(bb);
		createFile(bb, file);
	}
	
	public static void main(String[] args) throws Exception {
		
		String s = "samba(windows登录报用户密码错，重新smbpasswd -a centos)";
		byte[]  b = s.getBytes();
		byte[]  bb = compressedByte(b);
//		createFile(bb, file);
		
//		compress();
//		File file1 = new File(file);
//		byte[]  b = File2byte(file1);
//		System.out.println(b);
		byte[]  bbb = decompressorByte(bb, b.length);
		System.out.println(new String(bbb));
//		createFile(bb, "test.log");
	}

    /**
     * @param srcByte 原始数据
     * @return 压缩后的数据
     */
    public static byte[] compressedByte(byte[] srcByte) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4Compressor compressor = factory.fastCompressor();
        return compressor.compress(srcByte);
    }

    /**
     * @param compressorByte 压缩后的数据
     * @param srcLength      压缩前的数据长度
     * @return
     */
    public static byte[] decompressorByte(byte[] compressorByte, int srcLength) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        return decompressor.decompress(compressorByte, srcLength);
    }

    /**
     * @param srcByte
     * @param blockSize 一次压缩的大小 取值范围 64 字节-32M之间
     * @return
     * @throws IOException
     */
    public static byte[] lz4Compress(byte[] srcByte, int blockSize) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, blockSize, compressor);
        compressedOutput.write(srcByte);
        compressedOutput.close();
        return byteOutput.toByteArray();
    }

    /**
     * @param compressorByte
     * @param blockSize      一次压缩的大小 取值范围 64 字节-32M之间
     * @return
     * @throws IOException
     */
    public static byte[] lz4Decompress(byte[] compressorByte, int blockSize) throws IOException {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(blockSize);
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(compressorByte), decompresser);
        int count;
        byte[] buffer = new byte[blockSize];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        return baos.toByteArray();
    }

    /**
     * File  to byte[]
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] returnFileByte(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
        channel.read(byteBuffer);
        return byteBuffer.array();
    }

    /**
     * createFile
     *
     * @param fileByte
     * @param filePath
     */
    public static void createFile(byte[] fileByte, String filePath) {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath);
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(fileByte);
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            if (bufferedOutputStream != null) {
//                try {
//                	bufferedOutputStream.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//            if (fileOutputStream != null) {
//                try {
//                	fileOutputStream.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
        }
    }
    
    /**
     * File2byte
     * @param tradeFile
     * @return
     */
    public static byte[] File2byte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }
}