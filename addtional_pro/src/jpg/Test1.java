package jpg;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
 
import javax.swing.*;
 
public class Test1 extends JPanel
{
	JFrame frame;
	Image image = new ImageIcon("hand2.jpg").getImage();
	public void paint( Graphics g )
	{
		
		g.drawImage(image, 0, 0,null);	//֮ǰ��ͼƬ
		Image m = ImgRotate.imageMisro(image, ImgRotate.Left_Right_Reverse);
		g.drawImage(m, 200, 200, null);	//ˮƽ��ת��ͼƬ
		
		Image mm = ImgRotate.rotateImage(m, 90);	//����ֻ����90,180,270
		g.drawImage(mm, 250, 0, null);
		
 
	}
	public Test1()
	{
		frame = new JFrame();
		
		frame.add(this);
		
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String[] args) 
	{
		
		new Test1();
		
	}
	
 
}