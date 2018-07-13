import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int winWidth = Window.getWinWidth();
	private int winHeight = Window.getWinHeight();
	
	
	public TimerPanel() {
		init();
	}
	
	private void init() {
		JPanel timePanel = new JPanel();
		JPanel clockPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		JLabel time = new JLabel("time:");
		JLabel timeRemaining = new JLabel();
		JLabel backButton = new JLabel();
		JButton pause = new JButton("Pause");
		JButton reset = new JButton("Reset");
		CircularTimer p1 = new CircularTimer();
		CircularTimer p2 = new CircularTimer();
		
		
		setPreferredSize(new Dimension(winWidth, (int)(winHeight * .75f)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		p1.setForeground(Color.decode("#123456"));
		p2.setForeground(Color.decode("#123456"));
		
		add(Box.createHorizontalGlue());
		add(timePanel);
		add(clockPanel);
		add(buttonsPanel);
		add(Box.createHorizontalGlue());
	}
	
	
}
