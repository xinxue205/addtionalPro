package GUI;

import java.awt.*;

public class CenterPanel {
	public static void main(String[] args) {
		new MyFrame(100,100,600,300,Color.red);
	}
}

class MyFrame extends Frame{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private Panel p;
	MyFrame(int x, int y, int w, int h, Color c){
		super("My centerPanel");
		setLayout(null);
		setBounds(x, y, w, h);
		setBackground(c);
		p = new Panel(null);
		p.setBounds(w/4, h/4, w/2, h/2);
		p.setBackground(Color.yellow);
		add(p);
		setVisible(true);
		
	}
}