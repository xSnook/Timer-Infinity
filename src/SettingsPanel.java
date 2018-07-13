
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.*;



public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static SettingsPanel instance;
	public static Clip done;
	public static FileInputStream fis;;
	public static BufferedInputStream bis;
	public static AudioInputStream inputStream;
	public static File soundFile;
	private WindowsCheckBox aotBox = new WindowsCheckBox();

	
	private int winWidth = Window.getWinWidth();


	
	//AlarmSound, AlwaysOnTop, NightMode, BackgroundColor, ButtonColor, TextColor
	private String[] writeOutOnClose = {"default", "false", "false", "default", "default", "default"};
	
	private SettingsPanel() {
		init();
	}
	
	private void init() {
		MotionPanel titleBar = new MotionPanel(Window.getInstance());
		JPanel settingsAreaPanel = new JPanel();
		JPanel AOT = new JPanel();
		JPanel AS = new JPanel();
		JPanel NM = new JPanel();
		JPanel FC = new JPanel();
		JPanel BaC = new JPanel();
		JPanel BuC = new JPanel();
		JPanel Dfs = new JPanel();
		//JPanel col1 = new JPanel();
		JPanel[] settingsPanels = {AOT,AS,NM,FC,BaC,BuC,Dfs};
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JLabel menu = new JLabel();
		int winHeight = Window.getWinHeight();
		int itemPadding = (int) (winHeight * 0.05d);
		int winWidth = Window.getWinWidth();
		int borderWidth = Window.getInstance().borderWidth;
		WindowsToggleButton wtb = new WindowsToggleButton(winWidth, winHeight);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (JPanel p :settingsPanels) {
			p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));		
		}
		//Dfs.setLayout(new FlowLayout());
		setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, new Color(153, 153, 153)));
		//AOT.setLayout(new BoxLayout(AOT, BoxLayout.X_AXIS));
		
		titleBar.setPreferredSize(new Dimension(winWidth, (int) (winHeight/4)));
		titleBar.setLayout(new BorderLayout());
		//titleBar.setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, 0, borderWidth, new Color(153, 153, 153)));
		
		AOT.add(aotBox);
		aotBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        if(aotBox.isSelected())
		        	setAlwaysOnTop(true);
		        else 
		        	setAlwaysOnTop(false);       	
			} 
		});
		aotBox.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				aotBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				aotBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		//((AbstractButton) AOT.getComponent(0)).setSelected(Boolean.getBoolean(writeOutOnClose[1]));
	
		AOT.add(new JLabel("Always on top", SwingConstants.LEFT));
		
		AOT.getComponent(1).addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!aotBox.isSelected())
					setAlwaysOnTop(true);
				else
					setAlwaysOnTop(false);
			}
			public void mouseEntered(MouseEvent e) {
				AOT.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				AOT.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		FC.add(new JLabel("Font color", SwingConstants.LEFT));
		AS.add(new JLabel());
		((JLabel) AS.getComponent(0)).setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/Note_small.png"))
				.getImage().getScaledInstance((int)(winHeight*.113f), (int)(winHeight * .178f), Image.SCALE_SMOOTH)));
		
		((JLabel) AS.getComponent(0)).setBorder(new EmptyBorder(0,itemPadding,0,itemPadding +1));
		//AS.setBackground(Color.BLUE);
		AS.add(new JLabel("Alarm Sound", SwingConstants.LEFT));
		
		AS.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Window.getInstance().startMenuThread("alarm");
			}
		});
		BaC.add(new JLabel("Background color"));
		//NM.setLayout(new GridBagLayout());
		//c.gridx = 0;
		//c.gridy = 0;
		//c.fill = GridBagConstraints.VERTICAL;
		//c.anchor = GridBagConstraints.WEST;
		//c.weightx =0.0001f;
		NM.add(wtb);
		//NM.setBackground(Color.GREEN);
		wtb.setBorder(new EmptyBorder(0, 0, 0, itemPadding));
		//wtb.setPreferredSize(new Dimension((int)(winWidth * 0.115f), (int)(winHeight * 0.15f)));
		//c.insets = new Insets(0,-3,0,0);
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 500000000;
		//c.gridx =1;
		//((WindowsToggleButton) NM.getComponent(0)).setBorder(new EmptyBorder(0,0,0,-8));
		NM.add(new JLabel("Night Mode", SwingConstants.LEFT));
		NM.getComponent(1).addMouseListener(wtb.getMA());
		//((JLabel) NM.getComponent(1)).setBorder(new EmptyBorder(0,0,0,-3));
		BuC.add(new JLabel("Button Color"));
		Dfs.add(new JButton("Defaults"));
		//Dfs.getComponent(0).setFont(new Font("Arial", Font.PLAIN, 8));
		Dfs.getComponent(0).setFocusable(false);
		

		
		//settingsAreaPanel.setBorder(BorderFactory.createMatteBorder(0, borderWidth, borderWidth, borderWidth, new Color(153, 153, 153)));
		settingsAreaPanel.setPreferredSize(new Dimension(winWidth, (int) (winHeight * 3/4)));
		//settingsAreaPanel.add(col1);
		settingsAreaPanel.setLayout(gb);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		settingsAreaPanel.add(AOT, c);
		c.gridx = 1;
		settingsAreaPanel.add(FC, c);
		c.gridx = 0;
		c.gridy = 1;
		settingsAreaPanel.add(AS, c);
		c.gridx = 1;
		settingsAreaPanel.add(BaC, c);
		c.gridx = 0;
		c.gridy = 2;
		settingsAreaPanel.add(NM, c);
		c.gridx = 1;
		settingsAreaPanel.add(BuC, c);
		c.gridx = 0;
		c.gridy = 3;
		//c.fill = GridBagConstraints.BOTH;
		JPanel slidTestPanel = new JPanel();
		//WindowsToggleButton slidTest = new WindowsToggleButton();
		//slidTest.setPreferredSize(new Dimension((int)(winWidth/4f), (int)(winHeight *3/16f)));
		//slidTest.setFocusable(false);
		//slidTestPanel.add(new JLabel("Transparancy"));
		
		//slidTestPanel.add(slidTest);
		//settingsAreaPanel.add(Box.createVerticalGlue(), c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		settingsAreaPanel.add(Dfs, c);

		//settingsAreaPanel.setBackground(Color.BLUE);
		
		menu.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/menu.png")).getImage().getScaledInstance(winHeight/5, winHeight/5, Image.SCALE_SMOOTH)));
		menu.setSize(new Dimension((int) (winHeight/5), (int) (winHeight/5)));
		menu.addMouseListener(Window.getInstance().menuButtonListener);
		menu.setBorder(new EmptyBorder(0,0,0,(int) (winWidth*.0125f)));
		//menu.setForeground(Color.BLACK);
		
		setSize(new Dimension(winWidth, winHeight));
		JLabel titleText = new JLabel("Settings");
		titleText.setFont(new Font("Arial", Font.BOLD, (int) (winHeight/8)));
		titleText.setForeground(Color.decode("#7c7e80"));
		titleText.setBorder(new EmptyBorder(0,(int) (winWidth*.0125f),0,0));
		//titleBar.add(new JButton("Test"));
		titleBar.add(titleText, BorderLayout.WEST);
		titleBar.add(menu, BorderLayout.EAST);
		
		add(titleBar);
		add(settingsAreaPanel);
		//titleBar.setBackground(Color.BLACK);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});
		repaint();
		//revalidate();
		
		//System.out.println(this.getSize());
	}
	
	public static SettingsPanel getInstance() {
		if(instance == null)
			instance = new SettingsPanel();
		return instance;
	}
	
	
	public boolean setAlarm(String name) {
		if (soundFile != null) soundFile = null;
		if (name.equalsIgnoreCase("default")) {
			try {
				done = AudioSystem.getClip();
				inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/done.wav"));
				done.open(inputStream);
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				System.out.println("Default sound not found... somehow...");
				System.exit(1);
			}		
		} else {
			try {
				done = AudioSystem.getClip();
				
				fis = new FileInputStream(new File(Settings.getPath() + "\\" + name));
				bis = new BufferedInputStream(fis);
				inputStream = AudioSystem.getAudioInputStream(bis);
				done.open(inputStream);
				done.start();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				JOptionPane.showMessageDialog(Window.getInstance(), "Error: \"" + e + "\" when attempting to set new alarm sound.\n"
						  + " File type may be unsupported.", "Alarm Error"
						  , JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		writeOutOnClose[0] = name;
		return true;
	}
	
	public void closeStreams() {
			try {
				if(done != null) done.close();
				if(inputStream != null) inputStream.close();
				if(bis != null) bis.close();
				if(fis != null) fis.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(Window.getInstance(), "Error: \"" + e + "\" when attempting to set new alarm sound.\n"
						  + " File type may be unsupported.", "Alarm Error"
						  , JOptionPane.ERROR_MESSAGE);
			}
	}
	
	public void setAlwaysOnTop(boolean isOnTop) {
		//System.out.println(isOnTop);
		aotBox.setSelected(isOnTop);
		Window.getInstance().setAlwaysOnTop(isOnTop);
		writeOutOnClose[1] = String.valueOf(isOnTop);
	}
	
	public void setNightMode(boolean isNightMode, boolean calledBySettings) {
		int selection = -1;
		if((!writeOutOnClose[3].equalsIgnoreCase("default") || !writeOutOnClose[4].equalsIgnoreCase("default") 
				|| !writeOutOnClose[5].equalsIgnoreCase("default")) && !calledBySettings) {
			selection = JOptionPane.showConfirmDialog(Window.getInstance(), "This option will change all background/button/text colors."
					+ " Are you sure you want to continue?");
		}
		if(selection == JOptionPane.YES_OPTION || selection == -1) {
			//make necessary changes
			writeOutOnClose[2] = "true";
			writeOutOnClose[3] = "default";
			writeOutOnClose[4] = "default";
			writeOutOnClose[5] = "default";
			return;
		}
		
		writeOutOnClose[2] = String.valueOf(isNightMode);
	}
	
	private void setColors(Container parent, Color co, String compToSet) {
		for(Component c : parent.getComponents()) {
	        if(c instanceof Container) {
	            if(compToSet.equals("JPanel") && c instanceof JPanel) 
	                c.setBackground(co);
	            if(compToSet.equals("JButton") && c instanceof JButton)
	            	c.setBackground(co);
	            if(compToSet.equals("JLabel") && c instanceof JLabel)
	            	c.setForeground(co);

	            setColors((Container)c, co, compToSet);
	        }
	    }
	}
	
	/* Sets every panel's BG Color to the given parameter
	 * and changes the value to write to the prefs file 
	 */
	public void setBackgroundColor(Color c) {
		setColors(Window.getInstance(), c, "JPanel");
		
		writeOutOnClose[3] = Integer.toString(c.getRGB());
	}
	

	
	public void setButtonColor(Color c) {
		setColors(Window.getInstance(), c, "JButton");
		
		writeOutOnClose[4] = Integer.toString(c.getRGB());
	}
	

	
	public void setTextColor(Color c) {
		setColors(Window.getInstance(), c, "JLabel");
		
		writeOutOnClose[5] = Integer.toString(c.getRGB());
	}
	
	public void exitSettingsMenu() {	
		String prefs = Settings.getPath() + "\\" + "preferences.txt";
		String nl = System.lineSeparator();
		String toWrite = "AlarmSound=" + writeOutOnClose[0] + nl + "AlwaysOnTop=" + writeOutOnClose[1] + nl + "NightMode=" 
						 + writeOutOnClose[2] + nl + "BackgroundColor=" + writeOutOnClose[3] + nl + "ButtonColor=" 
						 + writeOutOnClose[4] + nl + "TextColor=" + writeOutOnClose[5];
		
		try {
			Files.write(Paths.get(prefs), toWrite.getBytes());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Window.getInstance(), "Error: \"" + e + "\" when attempting to write preferences file."
										  + " Reopen and then close the menu to reattempt to save your changes.", "Write Error"
										  , JOptionPane.ERROR_MESSAGE);
		}
	
	}
	public String getAlarmSoundName() {
		return writeOutOnClose[0];
	}
}
