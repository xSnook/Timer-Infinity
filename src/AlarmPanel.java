import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AlarmPanel extends JPanel {
	private JPanel alarmPanel = new JPanel();
	private File selF = null;
	private JLabel info = new JLabel();
	private boolean setDefaultOkay = false;
	
	private static final long serialVersionUID = 1L;
	
	public AlarmPanel() {
		init();
	}
	
	private void init() {
		JPanel textPan = new JPanel();
		JPanel buttonsPan = new JPanel();
		JPanel padding = new TransparentPanel();
		JPanel bg = new TransparentPanel();
		JButton okay = new JButton("Okay");
		JButton browse = new JButton("Browse");
		JButton def = new JButton("Default");
		
		int bw = Window.borderWidth;
		
		
		
		setLayout(new GridLayout(1,1));
		setOpaque(false);
		//setPreferredSize(new Dimension(Window.getWinWidth(), Window.getWinHeight()));
		//alarmPanel.setBackground(Color.GRAY);
		alarmPanel.setBorder(BorderFactory.createMatteBorder(0, bw, bw, bw, Window.borderColor));
		alarmPanel.setPreferredSize(new Dimension((int)(Window.getWinWidth()/1.5), (int)(Window.getWinHeight()/2.0)));
		alarmPanel.setMaximumSize(new Dimension((int)(Window.getWinWidth()/1.5), (int)(Window.getWinHeight()/2.0)));
		alarmPanel.add(textPan);
		alarmPanel.add(buttonsPan);
		alarmPanel.setLayout(new BoxLayout(alarmPanel, BoxLayout.Y_AXIS));
		alarmPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				e.consume();
			}
		});
		
		textPan.add(Box.createVerticalGlue());
		textPan.add(info);
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		textPan.add(Box.createVerticalGlue());
		textPan.setLayout(new BoxLayout(textPan, BoxLayout.Y_AXIS));
		
		okay.setFocusable(false);
		okay.setPreferredSize(new Dimension((int)(Window.getWinWidth()/1.5/3.0) -1, (int)(Window.getWinHeight()/2.0/3.0)));
		okay.setMargin(new Insets(okay.getMargin().top,0,okay.getMargin().bottom,0));
		okay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okay();
			}
		});
		
		browse.setFocusable(false);
		browse.setPreferredSize(new Dimension((int)(Window.getWinWidth()/1.5/3.0) -1, (int)(Window.getWinHeight()/2.0/3.0)));
		browse.setMargin(new Insets(browse.getMargin().top,0,browse.getMargin().bottom,0));
		browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				browse();
			}	
		});
		
		def.setFocusable(false);
		def.setPreferredSize(new Dimension((int)(Window.getWinWidth()/1.5/3.0) -1, (int)(Window.getWinHeight()/2.0/3.0)));
		def.setMargin(new Insets(def.getMargin().top,0,def.getMargin().bottom,0));
		def.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultSound();
			}
		});
		
		buttonsPan.setLayout(new BoxLayout(buttonsPan, BoxLayout.X_AXIS));
		buttonsPan.add(Box.createHorizontalGlue());
		buttonsPan.add(okay);
		buttonsPan.add(browse);
		buttonsPan.add(def);
		buttonsPan.add(Box.createHorizontalGlue());
		//buttonsPan.add(Box.createHorizontalGlue());
		//buttonsPan.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		add(bg);
		bg.setBackground(new Color(255,255,255,0));
		bg.setLayout(new BoxLayout(bg, BoxLayout.Y_AXIS));
		bg.add(alarmPanel);
		//bg.add(padding);
		padding.setPreferredSize(new Dimension(Window.getWinWidth(), (int)(Window.getWinHeight()/2.0)));
		bg.setPreferredSize(new Dimension(Window.getWinWidth(), Window.getWinHeight()));
		bg.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Window.getInstance().startMenuThread("alarm");
				setDefaultOkay = false;
			}
		});
		
		if(!SettingsPanel.getInstance().getAlarmSoundName().equalsIgnoreCase("default"))
			trimAlarmText("Current file - " + SettingsPanel.getInstance().getAlarmSoundName());
		else info.setText("Current file - done.wav");
		
	}
	
	public void trimAlarmText(String name) {
		String trimmed = "";
		for (int i = 0; i < name.length(); i++) {
			trimmed += name.charAt(i);
			info.setText(trimmed);
			Window.getInstance().revalidate();
			Window.getInstance().repaint();
			if (info.getWidth() > (int) (alarmPanel.getWidth() * .90f)) {
				info.setText(trimmed.substring(0, i - 1) + "...");
				break;
			}
		}
	}
	
	private void okay() {
		String temp = "";
		if (!SettingsPanel.getInstance().getAlarmSoundName().equalsIgnoreCase("default"))	
			temp = SettingsPanel.getInstance().getAlarmSoundName();
		if(selF != null) {
			try {
				SettingsPanel.getInstance().closeStreams();
				Files.copy(selF.toPath(), 
						Paths.get(Settings.getPath(), selF.getName()), 
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(Window.getInstance(), "File failed to copy: " + e.getMessage(), 
						"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
				return;
			}
		
			if(SettingsPanel.getInstance().setAlarm(selF.getName())) {
				Window.getInstance().startMenuThread("alarm");
				selF = null;
				if(!temp.equalsIgnoreCase("")) {
					try {
						Files.delete(Paths.get(Settings.getPath(), temp));
					} catch (IOException e) {
						JOptionPane.showConfirmDialog(Window.getInstance(), "Failed to delete old file:\n" + e.getMessage(), 
							"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
						return;
					}
				}
			}
		} else {
			Window.getInstance().startMenuThread("alarm");
		}
		if(setDefaultOkay) {
			SettingsPanel.getInstance().closeStreams();	
			SettingsPanel.getInstance().setAlarm("default");
			if(!temp.equalsIgnoreCase("")) {
				try {
					Files.delete(Paths.get(Settings.getPath(), temp));
				} catch (IOException e) {
					JOptionPane.showConfirmDialog(Window.getInstance(), "Failed to delete old file:\n" + e.getMessage(), 
						"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
					return;
				}
			}
			setDefaultOkay = false;
		} 
	}
	
	
	private void browse() {
		JFileChooser jf = new JFileChooser();
		//Clip done  = AudioSystem.getClip();
		AudioInputStream inS = null;
		
		jf.setFileFilter(new FileNameExtensionFilter(".wav", "wav"));
		jf.setAcceptAllFileFilterUsed(false);
		int ret = jf.showOpenDialog(Window.getInstance());
		
		if (ret == JFileChooser.CANCEL_OPTION) return;

		try {
			inS = AudioSystem.getAudioInputStream(jf.getSelectedFile());
			if(((inS.getFrameLength()+0.0)/inS.getFormat().getFrameRate()) > 3) {
				JOptionPane.showConfirmDialog(Window.getInstance(), "File too long, choose a file"
						+ " under 3 seconds in length", 
						"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
				try {inS.close();} catch (IOException e) {}
				inS = null;
				return;
			}
		} catch (UnsupportedAudioFileException | IOException e) {
			JOptionPane.showConfirmDialog(Window.getInstance(), "File not found/supported.", 
					"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
			return;
			
		}
		try {inS.close();} catch (IOException e) {}
		setDefaultOkay = false;
		selF = jf.getSelectedFile();
		trimAlarmText("Current file - " + selF.getName());

		return;
	}
	
	private void defaultSound() {
		selF = null;
		setDefaultOkay = true;
		info.setText("Current file - done.wav");
	}
	
	public JPanel getAlarmPanel() {
		return alarmPanel;
	}
	
	private class TransparentPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		{
	        setOpaque(false);
	    }
	    public void paintComponent(Graphics g) {
	        g.setColor(getBackground());
	        Rectangle r = g.getClipBounds();
	        g.fillRect(r.x, r.y, r.width, r.height);
	        super.paintComponent(g);
	    }
	}
}
