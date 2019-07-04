package jpg;
  
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
  
public class Test {
	 /**
	 * ��תͼƬΪָ���Ƕ�
	 * 
	 * @param bufferedimage
	 *   Ŀ��ͼ��
	 * @param degree
	 *   ��ת�Ƕ�
	 * @return
	 */
	 public static BufferedImage rotateImage(final BufferedImage bufferedimage,
			 final int degree) {
		 int w = bufferedimage.getWidth();
		 int h = bufferedimage.getHeight();
		 int type = bufferedimage.getColorModel().getTransparency();
		 BufferedImage img;
		 Graphics2D graphics2d;
		 (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
		 .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		 graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		 graphics2d.drawImage(bufferedimage, 0, 0, null);
		 graphics2d.dispose();
		 return img;
	 }
	  
	 /**
	 * ���ͼ��Ϊָ����С
	 * 
	 * @param bufferedimage
	 *   Ŀ��ͼ��
	 * @param w
	 *   ��
	 * @param h
	 *   ��
	 * @return
	 */
	 public static BufferedImage resizeImage(final BufferedImage bufferedimage,
			 final int w, final int h) {
		 int type = bufferedimage.getColorModel().getTransparency();
		 BufferedImage img;
		 Graphics2D graphics2d;
		 (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
		 .setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		 graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0,
		 bufferedimage.getWidth(), bufferedimage.getHeight(), null);
		 graphics2d.dispose();
		 return img;
	 }
	  
	 /**
	 * ˮƽ��תͼ��
	 * 
	 * @param bufferedimage
	 *   Ŀ��ͼ��
	 * @return
	 */
	 public static BufferedImage flipImage(final BufferedImage bufferedimage) {
		 int w = bufferedimage.getWidth();
		 int h = bufferedimage.getHeight();
		 BufferedImage img;
		 Graphics2D graphics2d;
		 (graphics2d = (img = new BufferedImage(w, h, bufferedimage
		 .getColorModel().getTransparency())).createGraphics())
		 .drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);
		 graphics2d.dispose();
		 return img;
	 }
	 
	 public static void main(String[] args) throws Exception {
		 String path = "E:\\Camera\\�½��ļ���\\";
		 File f = new File(path);
		 String[] str = f.list();
		 for (int i = 0; i < str.length; i++) {
//			 System.out.println(path+File.separator+str[i]);
			 File file = new File(path+File.separator+str[i]);
			 BufferedImage bufferedimage = ImageIO.read(file);
			 ImageIO.write(flipImage(bufferedimage), "jpg", file);
		}
	}
}