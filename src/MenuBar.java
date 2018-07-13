import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuBar extends MotionPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel title = new JLabel("Timer ∞", SwingConstants.CENTER);

	public MenuBar(MouseAdapter ma, Window f) {
		super(f);
		int wi = Window.getWinWidth();
		int hght = Window.getWinHeight();
		
		JLabel menu = new JLabel();
		JLabel x = new JLabel();
		
		
		setPreferredSize(new Dimension(wi, (int)(hght/4.0)));
		
		menu.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/menu.png")).getImage().getScaledInstance(hght/5, hght/5, Image.SCALE_SMOOTH)));
		menu.setSize(new Dimension((int) (wi/5.0), (int) (hght/5.0)));
		menu.addMouseListener(ma);
		menu.setBorder(new EmptyBorder(0,(int) (wi *.0125f),0,0));
		
		x.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/x.png")).getImage().getScaledInstance(hght/6, hght/6, Image.SCALE_SMOOTH)));
		x.setMaximumSize(new Dimension((int) (wi/8), (int) (hght/8)));
		x.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Window.getInstance().dispose();
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e) {
				x.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				x.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		x.setBorder(new EmptyBorder(0,0,0,(int) (wi*.0125f)));
		
		title.setSize(new Dimension((int) (wi/3.0), (int) (hght/5.0)));
		title.setFont(new Font("Arial", Font.PLAIN, (int) (hght/8.0)));
		title.setForeground(Color.decode("#7c7e80"));
		
		setLayout(new BorderLayout());
		add(menu, BorderLayout.WEST);
		add(x, BorderLayout.EAST);
		add(title, BorderLayout.CENTER);
	}
	
	public void setTitle(String text) {
		title.setText(text);
	}
}
