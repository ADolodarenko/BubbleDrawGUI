import java.awt.Dimension;

import javax.swing.JFrame;

public class BubbleDrawGUI extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Alex's BubbleDraw GUI App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BubblePanel());
		frame.setSize(new Dimension(800, 600));
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setVisible(true);
	}

}
