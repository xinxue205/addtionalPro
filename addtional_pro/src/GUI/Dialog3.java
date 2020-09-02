package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dialog3 {
	boolean continueMonitor = true;
	JFrame frame;
	
	public Dialog3() {
		frame = new JFrame("继续监控");
		JPanel jp11 = new JPanel();
    	JLabel label1=new JLabel("发生告警了，请确认监控是否继续？");
    	jp11.add(label1);
    	frame.add(jp11);
    	
		JPanel jp=new JPanel(new GridLayout(1, 3, 20, 10)); 
		JButton b1 = new JButton("是");
		JButton b2 = new JButton("否");
		JButton b3 = new JButton("关闭");
		b1.setMnemonic('T');
		b2.setMnemonic('I');
		b3.setMnemonic('C');
		frame.getRootPane().setDefaultButton(b1);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueMonitor = true;
				frame.setVisible(false);
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueMonitor = false;
				frame.setVisible(false);
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		//contentPane.add(b1);
		jp.add(b1);
		jp.add(b2);
		jp.add(b3);
		frame.add(jp);
        frame.setBounds(300,200,400,120);
		frame.setLayout(new FlowLayout());//流式布局
		frame.setVisible(true);
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
		
		new Thread() {
			public void run() {
				while(true) {
					System.out.println("continueMonitor : "+continueMonitor);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					if(!frame.isVisible()) {
						frame.setVisible(true);
//					}
				}
			};
		}.start();
	}

	public static void main(String[] args) {
		new Dialog3();
	}
}