import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WindowsCheckBox extends JCheckBox {

	private static final long serialVersionUID = 1L;
	private final int ss = (int)(Window.getWinHeight() * .113f); //scaledSize
	
	public WindowsCheckBox() {
		
		int padding = (int) (Window.getWinHeight() * .05d);
		
		setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/checkbox_unchecked.png"))
				.getImage().getScaledInstance(ss, ss, Image.SCALE_SMOOTH)));
		
		setSelectedIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/checkbox_checked.png"))
				.getImage().getScaledInstance(ss, ss, Image.SCALE_SMOOTH)));
		
		setRolloverSelectedIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/checkbox_checked_hover.png"))
				.getImage().getScaledInstance(ss, ss, Image.SCALE_SMOOTH)));
		
		setRolloverIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/checkbox_unchecked_hover.png"))
				.getImage().getScaledInstance(ss, ss, Image.SCALE_SMOOTH)));
		
		setBorder(new EmptyBorder(0,padding,0,padding));
	}

}
