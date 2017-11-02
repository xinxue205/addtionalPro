package base64pic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  

import javax.imageio.ImageIO;
  
import sun.misc.BASE64Decoder;  
import sun.misc.BASE64Encoder;  
  
public class Test64Bit {  
public static void main(String[] args) {  
	// ���Դ�Base64����ת��ΪͼƬ�ļ�  
	  
	  String strImg = "�����64λ����";  
	GenerateImage(strImg, "D:\\wangyc.jpg");  
	  
	// ���Դ�ͼƬ�ļ�ת��ΪBase64����  
	System.out.println(GetImageStr("d:\\wangyc.jpg"));  
	  
	  
	}  
	  
	public static String GetImageStr(String imgFilePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
		byte[] data = null;  
		  
		// ��ȡͼƬ�ֽ�����  
		try {  
			InputStream in = new FileInputStream(imgFilePath);  
			data = new byte[in.available()];  
			in.read(data);  
			in.close();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		  
		// ���ֽ�����Base64����  
		BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);// ����Base64��������ֽ������ַ���  
	}  
	  
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// ���ֽ������ַ�������Base64���벢����ͼƬ  
		if (imgStr == null) // ͼ������Ϊ��  
			return false;  
		BASE64Decoder decoder = new BASE64Decoder();  
		try {  
			// Base64����  
			byte[] bytes = decoder.decodeBuffer(imgStr);  
			for (int i = 0; i < bytes.length; ++i) {  
				if (bytes[i] < 0) {// �����쳣����  
				bytes[i] += 256;  
				}  
			}  
			// ����jpegͼƬ  
			OutputStream out = new FileOutputStream(imgFilePath);  
			out.write(bytes);  
			out.flush();  
			out.close();  
			return true;  
		} catch (Exception e) {  
			return false;  
		}  
	}  
	
	public static String img2base(String imgFilePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
		byte[] data = null;  
		
		  
		// ��ȡͼƬ�ֽ�����  
		try {  
			BufferedImage image = ImageIO.read(new File(""));
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        ImageIO.write( image, "png", os );
	        data = os.toByteArray();
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		  
		// ���ֽ�����Base64����  
		BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);// ����Base64��������ֽ������ַ���  
	}
	
} 
