import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;

public class BubblePanel extends JPanel
{
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	
	public BubblePanel()
	{
		bubbleList = new ArrayList<>();
		setBackground(Color.BLACK);
		
		testBubbles();
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
			size = newSize;
			color = new Color(rand.nextInt(256),
					rand.nextInt(256),
					rand.nextInt(256));
		}
		
		public void draw(Graphics canvas)
		{
			canvas.setColor(color);
			canvas.fillOval(x - size/2, y - size/2, size, size);
		}
	}

}
