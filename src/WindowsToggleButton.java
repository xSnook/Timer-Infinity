import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSliderUI;

public class WindowsToggleButton extends JSlider {

	private static final long serialVersionUID = 1L;
	private boolean isTransitioning = false;
	private boolean isOn = false;
	//private static boolean firstClickComplete = false;
	private boolean paintBG = false;
	private boolean isFinished = false;
	private Color thumbColor = Color.BLACK;
	private Color trackColor = Color.decode("#0079d7");
	private int winHeight = 0;
	private int winWidth = 0;
	private int trackWidth = 0;
	private int trackHeight = 0;
	private int stroke = 0;
	private int corners = 0;
	private MouseAdapter m = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (!isTransitioning)
				new Animate().start();
		}
	};
	public MouseAdapter getMA() {
		return m;
	}
	public WindowsToggleButton(int winWidth, int winHeight) {
		super(0,100);
		this.winHeight = winHeight;
		this.winWidth = winWidth;
		trackHeight = (int)(winHeight * 0.065f);
		trackWidth = (int) (winWidth * 0.0725f);
		stroke = (int)(winHeight/124f);
		corners = (int)(winHeight/15f);
		
		init();
	}
	
	private void init() {
		setUI(new WindowsToggleButtonSliderUI(this));
		setFocusable(false);
		setValue(0);
		setEnabled(false);
		addMouseListener(m);
		
		setPreferredSize(new Dimension((int) (winWidth * 1/2.0 * 1/4.3), (int)(winHeight * 3/4.0 * 1/5.0)));
		setMaximumSize(new Dimension((int) (winWidth * 1/2.0 * 1/4.3), (int)(winHeight * 3/4.0 * 1/5.0)));
		//setBorder(new EmptyBorder(0,0,0,-5));
		
	}
	
	private class WindowsToggleButtonSliderUI extends BasicSliderUI {
	    
		public WindowsToggleButtonSliderUI(JSlider slider) {
			super(slider);
		}
		


	    @Override
	    protected Dimension getThumbSize() {
	        return new Dimension(trackHeight/2, trackHeight/2);
	    }

	    @Override
	    public void paintTrack(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        //Stroke old = g2d.getStroke();
	        g2d.setStroke(new BasicStroke(stroke));
	       
	        g2d.setPaint(Color.BLACK);
	        //g2d.setStroke(new BasicStroke(2));
	        g2d.drawRoundRect(trackRect.x, (int)(trackRect.y/1.25f), trackWidth, trackHeight, corners, corners);
	        if (paintBG) {
	        	g2d.setPaint(trackColor);
	        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
		                RenderingHints.VALUE_ANTIALIAS_OFF);
	        	g2d.drawRoundRect(trackRect.x, (int)(trackRect.y/1.25f), trackWidth, trackHeight, corners, corners);
	        	g2d.fillRoundRect(trackRect.x, (int)(trackRect.y/1.25f), trackWidth, trackHeight, corners, corners);
	        }
	        //g2d.drawRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, 20, 25);
	        //g2d.setStroke(old);
	    }

	    @Override
	    public void paintThumb(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
	        //        RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
	                RenderingHints.VALUE_ANTIALIAS_OFF);
	        //g2d.setStroke(new BasicStroke(stroke));
	        g2d.setPaint(thumbColor);
	        g2d.drawOval(thumbRect.x +4, thumbRect.y, trackHeight/2, trackHeight/2);
	        g2d.fillOval(thumbRect.x +4, thumbRect.y, trackHeight/2, trackHeight/2);

	    }
	}
	
	private Color brighten(Color color, double fraction) {
		 int red = (int) Math.round(Math.min(255, color.getRed() + 255 * fraction));
	        int green = (int) Math.round(Math.min(255, color.getGreen() + 255 * fraction));
	        int blue = (int) Math.round(Math.min(255, color.getBlue() + 255 * fraction));

	        int alpha = color.getAlpha();

	        return new Color(red, green, blue, alpha);
	}
	
	private void moveIt() {
		if(isOn) {
			setValue(getValue()-4);
			if (getValue() % 4 == 0)  thumbColor = thumbColor.darker();
			paintBG = false;
		} else {
			setValue(getValue()+4);
			if (getValue() % 4 == 0)  thumbColor = brighten(thumbColor, .16d);
			paintBG = true;
		}
		if(getValue() == 72 || getValue() == 0) isFinished = true;
		repaint();
	}
	class Animate extends Thread {
		@Override
		public void run() {
			
			//firstClickComplete = true;
			isTransitioning = true;
			
			Timer t = new Timer(10, null);
			t.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == t && !isFinished) {
						moveIt();
					} else {
						t.stop();
						isFinished = false;
						isOn = !isOn;
						isTransitioning = false;	
					}
				}
				
			});
			t.start();
		}
	}
	

}
