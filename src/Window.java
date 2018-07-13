import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame {
	private static final long serialVersionUID = 1L;
	private static Window instance = null;
	
	static double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	static double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static int winHeight = (int) (height * .115f);
	private static int winWidth = (int) (width * .125f);
	public static final int borderWidth = (int) (winWidth * .0042f) < 1 ? 1 : (int) (winWidth * .0042f);
	public static Color borderColor = new Color(153, 153, 153);
	
	private JLayeredPane lp = getLayeredPane();
	private JPanel content = new JPanel();
	private JPanel base = new JPanel();
	private JPanel settingsPanel = null;
	private AlarmPanel alarmPanel = null;
	private HomePanel homePanel = new HomePanel();
	private SetTimePanel setTimePanel = new SetTimePanel();
	private Settings settings = null;
	public MouseAdapter menuButtonListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if(!isTransitioning)
				startMenuThread("settings");
		}
		public void mouseEntered(MouseEvent e) {
			Window.getInstance().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		public void mouseExited(MouseEvent e) {
			Window.getInstance().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	};
	private MenuBar menuBar = new MenuBar(menuButtonListener, this);
	
	private boolean[] menuDisplaying = {false, false};
	private boolean isTransitioning = false;
	private int moveDist = 0;
	private int movePixels = winWidth <= 240 ? 5 : 10;
	private static JPanel[] backgroundsToChange;
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Window.getInstance().initSettings();
	
		Window.getInstance().setVisible(true);
	}
	
	private Window() {
		init();
	} 
	
	public static int getWinHeight() {
		return winHeight;
	}
	
	public static int getWinWidth() {
		return winWidth;
	}
	
	private void initSettings() {
		settings = Settings.getInstance();
		settingsPanel = SettingsPanel.getInstance();
		alarmPanel = new AlarmPanel();
		
		lp.add(settingsPanel, JLayeredPane.MODAL_LAYER);
		lp.add(alarmPanel, JLayeredPane.POPUP_LAYER);
		
		settingsPanel.setLocation(settingsPanel.getX()-winWidth, settingsPanel.getY());
		alarmPanel.setLocation(alarmPanel.getX(), alarmPanel.getY()-winHeight);
		
		alarmPanel.setSize(new Dimension(winWidth, winHeight));
		backgroundsToChange = new JPanel[] {settingsPanel, alarmPanel.getAlarmPanel(), content, base, menuBar, homePanel, setTimePanel};
	}
	
	private void init() {	
		setTitle("Timer ∞");
		setSize(winWidth, winHeight);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);

		content.add(homePanel);
		
		
		base.setLayout(new BorderLayout());
		base.add(menuBar, BorderLayout.NORTH);
		base.add(content, BorderLayout.SOUTH);
		base.setSize(new Dimension(winWidth, winHeight));
		base.setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, borderColor));

		lp.add(base, JLayeredPane.DEFAULT_LAYER);
	}
	
	public void homeToSetTimeTimerPanel() {
		content.remove(0);
		content.add(setTimePanel);
		
		repaint();
		revalidate();
	}
	
	public void setTitleText(String text) {
		//setTitle(text);
		menuBar.setTitle(text);
		repaint();
	}
	
	public void startMenuThread(String menuToMove) {
		new MoveMenu(menuToMove).start();
	}
	
	public static Window getInstance() {
		if (instance == null) 
			instance = new Window();	
		return instance;
	}
	private void moveIt(String menuToMove) {
		switch(menuToMove) {
		case "settings":
			if (menuDisplaying[0])
				settingsPanel.setLocation(settingsPanel.getX() - movePixels, settingsPanel.getY());
			else
				settingsPanel.setLocation(settingsPanel.getX() + movePixels, settingsPanel.getY());
			break;
		case "alarm":
			if (menuDisplaying[1])
				alarmPanel.setLocation(alarmPanel.getX(), alarmPanel.getY() - movePixels);
			else
				alarmPanel.setLocation(alarmPanel.getX(), alarmPanel.getY() + movePixels);
			break;
		}

		moveDist += movePixels;
		switch(menuToMove) {
		case "settings":
			if (moveDist >= winWidth)
				moveDist = winWidth;
			break;
		case "alarm":
			if (moveDist >= winHeight)
				moveDist = winHeight;
			break;
		}
		
		
	}
	class MoveMenu extends Thread {
		String menu;
		public MoveMenu(String menuToMove) {
			menu = menuToMove;
		}

		@Override
		public void run() {
			isTransitioning = true;
			int md = menu.equals("settings") ? winWidth : winHeight;
			movePixels = menu.equals("settings") ? winWidth/48 : winHeight/31;
			
			Timer t = new Timer(10, null);
			t.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == t && moveDist != md) {
						moveIt(menu);
					} else {
						t.stop();
						moveDist = 0;
						switch(menu) {
						case "settings":
							menuDisplaying[0] = !menuDisplaying[0];
							if (!menuDisplaying[0])
								SettingsPanel.getInstance().exitSettingsMenu();
							break;
						case "alarm":
							menuDisplaying[1] = !menuDisplaying[1];
							if (!menuDisplaying[1])
								SettingsPanel.getInstance().exitSettingsMenu();
								String temp = SettingsPanel.getInstance().getAlarmSoundName();
								alarmPanel.trimAlarmText(temp.equalsIgnoreCase("default") ? "Current file - done.wav": "Current file - " + temp);
							break;
						}
						isTransitioning = false;	
					}
					Window.getInstance().repaint();
				}
			});
			t.start();
		}
	}

}
