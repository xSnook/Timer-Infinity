import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class HomePanel extends JPanel {
	private int winWidth = Window.getWinWidth();
	private int winHeight = Window.getWinHeight();

	
	public HomePanel() {
		
		init();
	}
	
	private void init() {
		JPanel buttons = new JPanel();
		JPanel textBarPan = new JPanel();
		JLabel name = new JLabel("name:", JLabel.LEFT);
		JTextField nameTextBox = new JTextField();
		JButton timer = new JButton("Timer");
		
		setPreferredSize(new Dimension(winWidth, (int) (winHeight * .75f)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(Box.createHorizontalGlue());
		add(textBarPan);
		//textBarPan.setBackground(Color.cyan);
		textBarPan.setPreferredSize(new Dimension((int)(winWidth*.60f), (int)(winHeight* .75f)));
		textBarPan.setMaximumSize(new Dimension((int)(winWidth*.60f), (int)(winHeight* .75f)));
		textBarPan.setLayout(new BoxLayout(textBarPan, BoxLayout.Y_AXIS));
		textBarPan.add(Box.createVerticalGlue());
		textBarPan.add(name);
		textBarPan.add(nameTextBox);
		textBarPan.add(Box.createVerticalGlue());
		name.setAlignmentX(Component.LEFT_ALIGNMENT);
		name.setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/10.0)));
		name.setForeground(Color.decode("#7c7e80"));
		nameTextBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		//nameTextBox.setPreferredSize(new Dimension((int)(winWidth*.75f), (int)(winHeight* .75f * .25f)));
		nameTextBox.setMaximumSize(new Dimension((int)(winWidth*.55f), (int)(winHeight* .75f * .25f)));
		
		add(buttons);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(Box.createVerticalGlue());
		buttons.add(timer);
		timer.setAlignmentX(Component.RIGHT_ALIGNMENT);
		timer.setPreferredSize(new Dimension((int)(winWidth*.30f), (int) (winHeight * .75f * .25f)));
		timer.setMaximumSize(new Dimension((int)(winWidth*.30f), (int) (winHeight * .75f * .25f)));
		timer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!nameTextBox.getText().equals(""))
					Window.getInstance().setTitleText(nameTextBox.getText());
				Window.getInstance().homeToSetTimeTimerPanel();
			}
			
		});
		buttons.add(Box.createVerticalGlue());
		
		add(Box.createHorizontalGlue());
	}
}
