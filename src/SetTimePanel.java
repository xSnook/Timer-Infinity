import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class SetTimePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int winWidth = Window.getWinWidth();
	private int winHeight = Window.getWinHeight();
	
	
	public SetTimePanel() {
		init();
	}
	
	private void init() {
		JPanel timePan = new JPanel();
		JPanel buttons = new JPanel();
		JLabel time = new JLabel("set time:");
		JPanel timeBox = new JPanel();
		JNumberTextField hh = new JNumberTextField(2);
		JNumberTextField mm = new JNumberTextField(2);
		JNumberTextField ss = new JNumberTextField(2);
		JNumberTextField[] timeFields = {hh, mm, ss};
		JLabel backButton = new JLabel();
		JButton begin = new JButton("Begin");
		
		setPreferredSize(new Dimension(winWidth, (int)(winHeight * .75f)));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus();
			}
		});
		
		add(Box.createHorizontalGlue());
		add(timePan);
		add(buttons);
		add(Box.createHorizontalGlue());
		
		timePan.setPreferredSize(new Dimension((int)(winWidth*.60f), (int)(winHeight* .75f)));
		timePan.setMaximumSize(new Dimension((int)(winWidth*.60f), (int)(winHeight* .75f)));
		timePan.setLayout(new BoxLayout(timePan, BoxLayout.Y_AXIS));
		timePan.add(Box.createVerticalGlue());
		timePan.add(time);
		timePan.add(timeBox);
		timePan.add(backButton);
		timePan.add(Box.createVerticalGlue());
		
		time.setAlignmentX(Component.LEFT_ALIGNMENT);
		time.setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/10.0)));
		time.setForeground(Color.decode("#7c7e80"));
		timeBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		timeBox.setPreferredSize(new Dimension((int)(winWidth*.55f), (int)(winHeight* .75f * .45f)));
		timeBox.setMaximumSize(new Dimension((int)(winWidth*.55f), (int)(winHeight* .75f * .45f)));
		//timeBox.setHorizontalAlignment(JTextField.CENTER);
		timeBox.setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/5.0)));
		timeBox.setForeground(Color.decode("#7c7e80"));
		backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		for(int i = 0; i < timeFields.length; i++) {
			timeFields[i].setText("00");
			timeFields[i].setFocusTraversalKeysEnabled(false);
			timeFields[i].setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/4.75)));
			timeFields[i].setForeground(Color.decode("#7c7e80"));
			timeFields[i].setPreferredSize(new Dimension((int) (winWidth*.55f*.25f), (int) (winHeight *.75f*.35f)));
			timeFields[i].setHorizontalAlignment(JTextField.CENTER);
			//timeFields[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
			final int j = i;
			timeFields[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					timeFields[j].selectAll();
				}
			});
			timeFields[i].addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					if (timeFields[j].getText().length() == 0) 
						timeFields[j].setText("00");	
					if (timeFields[j].getText().length() == 1) 
						timeFields[j].setText("0" + timeFields[j].getText());	
				}
				
			});
		}

		hh.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					mm.requestFocusInWindow();
					mm.selectAll();
				} 
			}
			@Override
			public void keyReleased(KeyEvent e) {

			}	
		});
		mm.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					ss.requestFocusInWindow();
					ss.selectAll();
				} 
			}
			@Override
			public void keyReleased(KeyEvent e) {

			}
			
		});
		ss.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
					begin.requestFocus();
				} 
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}	
		});
	

		timeBox.setLayout(new BoxLayout(timeBox, BoxLayout.X_AXIS));
		timeBox.add(hh);
		timeBox.add(new JLabel(":"));
		timeBox.getComponent(1).setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/4.75)));
		timeBox.getComponent(1).setForeground(Color.decode("#7c7e80"));
		timeBox.add(mm);
		timeBox.add(new JLabel(":"));
		timeBox.getComponent(3).setFont(new Font("Arial", Font.PLAIN, (int) (winHeight/4.75)));
		timeBox.getComponent(3).setForeground(Color.decode("#7c7e80"));
		timeBox.add(ss);
		
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(Box.createVerticalGlue());
		buttons.add(begin);
		buttons.add(Box.createVerticalGlue());
		
		begin.setPreferredSize(new Dimension((int)(winWidth*.30f), (int) (winHeight * .75f * .25f)));
		begin.setMaximumSize(new Dimension((int)(winWidth*.30f), (int) (winHeight * .75f * .25f)));
		begin.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
	}
}
