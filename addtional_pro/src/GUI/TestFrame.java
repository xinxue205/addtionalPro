package GUI;

import java.awt.*;

public class TestFrame {
	public static void main(String[] args) {
		Frame f = new Frame("My first Test");
		f.setLocation(100,100);
		f.setSize(300, 300);
		f.setBackground(Color.BLUE);
		f.setVisible(true);
		f.setResizable(false);
	}
}
