package GUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class Text {
    public static void main(String[] agrs) {
    	JFrame frame = new JFrame("监控是否继续？");

    	JPanel jp11 = new JPanel();
    	JLabel label1=new JLabel("普通标签");
//    	JTextField txtfield1=new JTextField();    //创建文本框
//        txtfield1.setText("告警发生，监控是否继续？"); 
        jp11.add(label1);
        frame.add(jp11);
    	
		JPanel jp1 = new JPanel(new GridLayout(1, 3,20,10));//3行2列 水平间距20 垂直间距10
		//第一行
		JLabel jl1 = new JLabel("文字du:");
		jl1.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField jtf1 = new JTextField(10);
		jtf1.setText("文本框文字");
		jp1.add(jl1);
		jp1.add(jtf1);
		//第二行
		JLabel jl2 = new JLabel("文字:");
//		jl2.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextField jtf2 = new JTextField(10);
		jtf2.setText("文本框文字");
		jp1.add(jl2);
//		jp1.add(jtf2);
//		//第三行
//		JLabel jl3 = new JLabel("文字:");
////		jl3.setHorizontalAlignment(SwingConstants.RIGHT);
//		JTextField jtf3 = new JTextField(10);
//		jtf3.setText("文本框文字");
//		jp1.add(jl3);
//		jp1.add(jtf3);
		 
		frame.add(jp1);
		 
		frame.setLayout(new FlowLayout());//流式布局
		 
		frame.setTitle("Demo");
		frame.setSize(321,169);//大小
		frame.setLocationRelativeTo(null);//居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }
}