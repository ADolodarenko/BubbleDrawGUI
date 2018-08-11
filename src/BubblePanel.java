import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.*;

public class BubblePanel extends JPanel
{
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	
	public BubblePanel()
	{
		bubbleList = new ArrayList<>();
		setBackground(Color.BLACK);
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		//testBubbles();
	}
	
	@Override
	public void paintComponent(Graphics canvas)
	{
		super.paintComponent(canvas);
		
		for (Bubble b : bubbleList)
			b.draw(canvas);
	}
	
	public void testBubbles()
	{
		for (int i = 0; i < 100; i++)
		{
			int x = rand.nextInt(600);
			int y = rand.nextInt(400);
			int size = rand.nextInt(50);
			
			bubbleList.add(new Bubble(x, y, size));
		}
		
		repaint();
	}
	
	private class BubbleListener extends MouseAdapter
	{

		@Override
		public void mouseDragged(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			size += e.getUnitsToScroll();
			
			if (size < 3)
				size = 3;
		}		
	}
	
	private class Bubble
	{
		private int x;
		private int y;
		private int size;
		private Color color;
		
		public Bubble(int newX, int newY, int newSize)
		{
			x = newX;
			y = newY;
			//x = (newX / newSize) * newSize + newSize / 2;
			//y = (newY / newSize) * newSize + newSize / 2;
			size = newSize;
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));
		}
		
		public void draw(Graphics canvas)
		{
			canvas.setColor(color);
			canvas.fillOval(x - size/2, y - size/2, size, size);
			//canvas.fillRect(x - size/2, y - size/2, size, size);
		}
	}

}
