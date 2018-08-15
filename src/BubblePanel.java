import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import java.awt.Dimension;

public class BubblePanel extends JPanel
{
	private static final String CHOOSE_BUBBLE_COLOR = "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0446\u0432\u0435\u0442 \u043A\u0438\u0441\u0442\u0438";
	private static final String CHOOSE_BACK_COLOR = "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0446\u0432\u0435\u0442 \u0444\u043E\u043D\u0430";
	private static final String BUTTON_PAUSE = "\u0421\u0442\u043E\u043F";
	private static final String BUTTON_START = "\u0421\u0442\u0430\u0440\u0442";
	private static final String BUTTON_CLEAR = "\u0421\u0442\u0435\u0440\u0435\u0442\u044C";
	private static final String LABEL_BACKGROUND = "\u0426\u0432\u0435\u0442 \u0444\u043E\u043D\u0430:";
	private static final String LABEL_FOREGROUND = "\u0426\u0432\u0435\u0442 \u043A\u0438\u0441\u0442\u0438:";
	private static final String LABEL_ANIMATION_SPEED = "\u0421\u043A\u043E\u0440\u043E\u0441\u0442\u044C \u0430\u043D\u0438\u043C\u0430\u0446\u0438\u0438:";
	
	Random rand = new Random();
	ArrayList<Bubble> bubbleList;
	int size = 25;
	Timer timer;
	int delay = 33;
	JSlider slider;
	private JTextField txtBackground;
	private JTextField txtBubbleColor;
	
	private Color bubbleColor;
	
	public BubblePanel()
	{
		setMinimumSize(new Dimension(0, 0));
		timer = new Timer(delay, new BubbleListener());
		bubbleList = new ArrayList<>();
		setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JButton btnPause = new JButton(BUTTON_PAUSE);
		btnPause.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JButton btn = (JButton)e.getSource();
				if (btn.getText().equals(BUTTON_PAUSE))
				{
					timer.stop();
					btn.setText(BUTTON_START);
				}
				else
				{
					timer.start();
					btn.setText(BUTTON_PAUSE);
				}
			}
		});
		
		JLabel lblBackgroundColor = new JLabel(LABEL_BACKGROUND);
		panel.add(lblBackgroundColor);
		
		txtBackground = new JTextField();
		txtBackground.setText("   ");
		panel.add(txtBackground);
		txtBackground.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				Color currentColor = txtBackground.getBackground();
				Color selectedColor = JColorChooser.showDialog(null, CHOOSE_BACK_COLOR, currentColor);
				
				if (selectedColor != null && !selectedColor.equals(currentColor))
				{
					setBackground(selectedColor);
					txtBackground.setBackground(selectedColor);
				}
			}
		});
		txtBackground.setEditable(false);
		txtBackground.setColumns(2);
		txtBackground.setBackground(getBackground());
		
		JLabel lblBubbleColor = new JLabel(LABEL_FOREGROUND);
		panel.add(lblBubbleColor);
		
		txtBubbleColor = new JTextField();
		txtBubbleColor.setEnabled(false);
		txtBubbleColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int buttonIndex = e.getButton();
				
				if (buttonIndex == MouseEvent.BUTTON1)
				{
					Color selectedColor = JColorChooser.showDialog(null, CHOOSE_BUBBLE_COLOR, bubbleColor);
					
					if (selectedColor != null && !selectedColor.equals(bubbleColor))
					{
						bubbleColor = selectedColor;
						
						if (!txtBubbleColor.isEnabled())
							txtBubbleColor.setEnabled(true);
						
						txtBubbleColor.setBackground(selectedColor);
					}					
				}
				else if (buttonIndex == MouseEvent.BUTTON3)
				{
					if (txtBubbleColor.isEnabled())
					{
						bubbleColor = null;
						txtBubbleColor.setBackground(panel.getBackground());
						txtBubbleColor.setEnabled(false);
					}
				}
			}
		});
		txtBubbleColor.setEditable(false);
		panel.add(txtBubbleColor);
		txtBubbleColor.setText("  ");
		txtBubbleColor.setColumns(2);
		
		JLabel lblAnimationSpeed = new JLabel(LABEL_ANIMATION_SPEED);
		panel.add(lblAnimationSpeed);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg0)
			{
				int speed = slider.getValue() + 1;
				int delay = 1000 / speed;
				timer.setDelay(delay);
			}
		});
		slider.setValue(30);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(30);
		slider.setMinorTickSpacing(5);
		slider.setMaximum(120);
		panel.add(slider);
		panel.add(btnPause);
		
		JButton btnClear = new JButton(BUTTON_CLEAR);
		btnClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				bubbleList = new ArrayList<>();
				
				repaint();
			}
		});
		panel.add(btnClear);
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		//testBubbles();
		
		timer.start();
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
			
			bubbleList.add(new Bubble(x, y, size, bubbleColor));
		}
		
		repaint();
	}
	
	private class BubbleListener extends MouseAdapter implements ActionListener
	{

		@Override
		public void mouseDragged(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size, bubbleColor));
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			bubbleList.add(new Bubble(e.getX(), e.getY(), size, bubbleColor));
			repaint();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			size += e.getUnitsToScroll();
			
			if (size < 3)
				size = 3;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (Bubble b : bubbleList)
				b.update();
			
			repaint();
		}		
	}
	
	private class Bubble
	{
		private int x;
		private int y;
		private int size;
		private Color color;
		private final int MAX_SPEED = 5;
		private int xSpeed, ySpeed;
		
		public Bubble(int newX, int newY, int newSize, Color color)
		{
			x = newX;
			y = newY;
			//x = (newX / newSize) * newSize + newSize / 2;
			//y = (newY / newSize) * newSize + newSize / 2;
			size = newSize;
			
			if (color != null)
				this.color = color;
			else
				this.color = new Color(rand.nextInt(256),
						rand.nextInt(256),
						rand.nextInt(256),
						rand.nextInt(256));
			
			xSpeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
			ySpeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
			
			if (xSpeed == 0 && ySpeed == 0)
			{
				int var = rand.nextInt(2);
				
				if (var == 1)
					ySpeed = 1;
				else
					xSpeed = 1;
			}
		}
		
		public void update()
		{
			x += xSpeed;
			y += ySpeed;
			
			if (x - size/2 <= 0 || x + size/2 >= getWidth())
				xSpeed = - xSpeed;
			if (y - size/2 <= 0 || y + size/2 >= getHeight())
				ySpeed = - ySpeed;
		}

		public void draw(Graphics canvas)
		{
			canvas.setColor(color);
			canvas.fillOval(x - size/2, y - size/2, size, size);
			//canvas.fillRect(x - size/2, y - size/2, size, size);
		}
	}

}
