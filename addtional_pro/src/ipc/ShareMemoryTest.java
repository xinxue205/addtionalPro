package ipc;


import java.io.File;  
import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.nio.ByteBuffer;  
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel;  
import java.nio.channels.FileLock;  
import java.util.Properties;  
  
/** 
 * �����ڴ������ 
 * @author hx 
 * 
 */  
public class ShareMemoryTest {  
  
    int flen = 41779264;                    //���ٹ����ڴ��С  
    int fsize = 0;                          //�ļ���ʵ�ʴ�С  
    String shareFileName;                   //�����ڴ��ļ���  
    String sharePath;                       //�����ڴ�·��  
    MappedByteBuffer mapBuf = null;         //���干���ڴ滺����  
    FileChannel fc = null;                  //������Ӧ���ļ�ͨ��  
    FileLock fl = null;                     //�����ļ����������ı�ǡ�        
    Properties p = null;  
    RandomAccessFile RAFile = null;         //����һ�������ȡ�ļ�����  
  
    /** 
     *  
     * @param sp    �����ڴ��ļ�·�� 
     * @param sf    �����ڴ��ļ��� 
     */  
    public ShareMemoryTest(String sp, String sf) {  
        if (sp.length() != 0) {  
        	File file = new File(sp);
        	if(!file.exists()) {
        		file.mkdir();
        	}
//            FileUtil.CreateDir(sp);  
            this.sharePath = sp + File.separator;  
        } else {  
            this.sharePath = sp;  
        }  
        this.shareFileName = sf;  
  
        try {  
            // ���һ��ֻ���������ȡ�ļ�����   "rw" ���Ա��ȡ��д�롣������ļ��в����ڣ����Դ������ļ���    
            RAFile = new RandomAccessFile(this.sharePath + this.shareFileName + ".sm", "rw");  
            //��ȡ��Ӧ���ļ�ͨ��  
            fc = RAFile.getChannel();  
            //��ȡʵ���ļ��Ĵ�С  
            fsize = (int) fc.size();  
            if (fsize < flen) {  
                byte bb[] = new byte[flen - fsize];  
                //�����ֽڻ�����  
                ByteBuffer bf = ByteBuffer.wrap(bb);  
                bf.clear();  
                //���ô�ͨ�����ļ�λ�á�   
                fc.position(fsize);  
                //���ֽ����дӸ����Ļ�����д���ͨ����  
                fc.write(bf);  
                fc.force(false);  
  
                fsize = flen;  
            }  
            //����ͨ�����ļ�����ֱ��ӳ�䵽�ڴ��С�  
            mapBuf = fc.map(FileChannel.MapMode.READ_WRITE, 0, fsize);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     *  
     * @param ps        ��������ʼ��λ�ã�����Ϊ�Ǹ��� 
     * @param len       ��������Ĵ�С������Ϊ�Ǹ��� 
     * @param buff      д������� 
     * @return 
     */  
    public synchronized int write(int ps, int len, byte[] buff) {  
        if (ps >= fsize || ps + len >= fsize) {  
            return 0;  
        }  
        //�����ļ����������ı�ǡ�  
        FileLock fl = null;  
        try {  
            //��ȡ��ͨ�����ļ����������ϵ�������   
            fl = fc.lock(ps, len, false);  
            if (fl != null) {  
  
                mapBuf.position(ps);  
                ByteBuffer bf1 = ByteBuffer.wrap(buff);  
                mapBuf.put(bf1);  
                //�ͷŴ�������  
                fl.release();  
  
                return len;  
            }  
        } catch (Exception e) {  
            if (fl != null) {  
                try {  
                    fl.release();  
                } catch (IOException e1) {  
                    System.out.println(e1.toString());  
                }  
            }  
            return 0;  
        }  
  
        return 0;  
    }  
      
    /** 
     *  
     * @param ps        ��������ʼ��λ�ã�����Ϊ�Ǹ��� 
     * @param len       ��������Ĵ�С������Ϊ�Ǹ��� 
     * @param buff      Ҫȡ������ 
     * @return 
     */  
    public synchronized int read(int ps, int len, byte[] buff) {  
        if (ps >= fsize) {  
            return 0;  
        }  
        //�����ļ����������ı�ǡ�  
        FileLock fl = null;  
        try {  
            fl = fc.lock(ps, len, false);  
            if (fl != null) {  
                //System.out.println( "ps="+ps );  
                mapBuf.position(ps);  
                if (mapBuf.remaining() < len) {  
                    len = mapBuf.remaining();  
                }  
  
                if (len > 0) {  
                    mapBuf.get(buff, 0, len);  
                }  
  
                fl.release();  
  
                return len;  
            }  
        } catch (Exception e) {  
            if (fl != null) {  
                try {  
                    fl.release();  
                } catch (IOException e1) {  
                    System.out.println(e1.toString());  
                }  
            }  
            return 0;  
        }  
  
        return 0;  
    }  
      
    /** 
     * ��ɣ��ر���ز��� 
     */  
    protected void finalize() throws Throwable {  
        if (fc != null) {  
            try {  
                fc.close();  
            } catch (IOException e) {  
                System.out.println(e.toString());  
            }  
            fc = null;  
        }  
  
        if (RAFile != null) {  
            try {  
                RAFile.close();  
            } catch (IOException e) {  
                System.out.println(e.toString());  
            }  
            RAFile = null;  
        }  
        mapBuf = null;  
    }  
      
    /** 
     * �رչ����ڴ���� 
     */  
    public synchronized void closeSMFile() {  
        if (fc != null) {  
            try {  
                fc.close();  
            } catch (IOException e) {  
                System.out.println(e.toString());  
            }  
            fc = null;  
        }  
  
        if (RAFile != null) {  
            try {  
                RAFile.close();  
            } catch (IOException e) {  
                System.out.println(e.toString());  
            }  
            RAFile = null;  
        }  
        mapBuf = null;  
    }  
      
    /** 
     *  ����˳� 
     * @return  true-�ɹ���false-ʧ�� 
     */  
    public synchronized boolean checkToExit() {  
        byte bb[] = new byte[1];  
  
        if (read(1, 1, bb) > 0) {  
            if (bb[0] == 1) {  
                return true;  
  
            }  
        }  
  
        return false;  
    }  
  
    /** 
     * ��λ�˳� 
     */  
    public synchronized void resetExit() {  
        byte bb[] = new byte[1];  
  
        bb[0] = 0;  
        write(1, 1, bb);  
  
    }  
  
    /** 
     * �˳� 
     */  
    public synchronized void toExit() {  
        byte bb[] = new byte[1];  
  
        bb[0] = 1;  
        write(1, 1, bb);  
  
    }  
      
    public static void main(String arsg[]) throws Exception{  
        ShareMemoryTest sm = new ShareMemoryTest("E://test","test");  
        String str = "���Ĳ���";  
        sm.write(40, 20, str.getBytes("UTF-8"));  
        byte[] b = new byte[20];  
        sm.read(40, 20, b);  
        System.out.println(new String(b,"UTF-8"));  
    }  
} 