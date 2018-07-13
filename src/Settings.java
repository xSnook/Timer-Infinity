
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Settings {
	private static Settings instance = null;
	
	private Settings() {
		String path = getPath();
		String filePath = path + "\\" + "preferences.txt";
		
		if(getFile(path)) {
			setPrefsOnOpen(filePath);
		} else {
			initPrefFile(filePath);
			setAlarm("AlarmSound=default");
		}
		
	}
	
	public static Settings getInstance() {
		if(instance == null)
			instance = new Settings();
		return instance;
	}
	
	public static String getPath() {
		String fileFolder = System.getenv("APPDATA") + "\\" + "Timer Infinity";
		String os = System.getProperty("os.name").toUpperCase();
		
		if (os.contains("WIN")) {
		    fileFolder = System.getenv("APPDATA") + "\\" + "Timer Infinity";
		}
		else if (os.contains("MAC")) {
		    fileFolder = System.getProperty("user.home") + "/Library/Application " + "Support"
		            + "/" + "Timer Infinity";
		}
		else if (os.contains("NUX")) {
		    fileFolder = System.getProperty("user.dir") + ".Timer Infinity";;
		}

		File directory = new File(fileFolder);

		if (!directory.exists()) {
		    directory.mkdir();
		}
		
		return fileFolder;
	}
	
	//Returns true if file exists, false if not and makes file
	private boolean getFile(String path) {
		File file = new File(path + "\\" + "preferences.txt");
		
		try {
			if(file.createNewFile()) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			System.out.println("Error: " + e + " when creating prefs file");
			System.exit(1);
		}
		return false;
	}
	
	private void initPrefFile(String path) {
		String nl = System.lineSeparator();
		String toWrite = "AlarmSound=default" + nl + "AlwaysOnTop=false" + nl + "NightMode=false"
					     + nl + "BackgroundColor=default" + nl + "ButtonColor=default" + nl + "TextColor=default";
		
		try {
			Files.write(Paths.get(path), toWrite.getBytes());
		} catch (IOException e) {
			
			System.out.println("Error: " + e + " when creating prefs file");
			System.exit(1);
		}
	}
	
	private void setPrefsOnOpen(String path) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner sc = new Scanner(new File(path));
			
			while(sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e + " when reading prefs file");
			System.exit(1);
		}
		if (lines.size() != 6)
			recreatePrefs(path);
		
		if(!setAlarm(lines.get(0)))
			recreatePrefs(path);
		if(!setAlwaysOnTop(lines.get(1)))
			recreatePrefs(path);
		if(!setNightMode(lines.get(2)))
			recreatePrefs(path);
		if(!setBackgroundColor(lines.get(3)))
			recreatePrefs(path);
		if(!setButtonColor(lines.get(4)))
			recreatePrefs(path);
		if(!setTextColor(lines.get(5)))
			recreatePrefs(path);
	}
	
	/* Returns true on success, false on failure. False indicates the preferences file
	 * has been tampered with or the alarm deleted/renamed, so the preferences file
	 * must be remade
	 */
	
	private boolean setAlarm(String line) {
		String[] temp = line.split("=");
		
		if (temp.length != 2) 
			return false;
		if(!temp[0].equalsIgnoreCase("AlarmSound")) 
			return false;
		if (!temp[1].equalsIgnoreCase("default")) {
			File file = new File(getPath() + "\\" + temp[1]);
			if (!file.exists()) {
				JOptionPane.showConfirmDialog(null, "Missing sound file specified in preferences.\n" +
						"Rebuilding preferences file...", 
						"File Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null);
				return false;
			}
		} 
		return SettingsPanel.getInstance().setAlarm(temp[1]);
	}
	
	private boolean setAlwaysOnTop(String line) {
		String[] temp = line.split("=");

		if (temp.length != 2) 
			return false;
		if(!temp[0].equalsIgnoreCase("AlwaysOnTop")) 
			return false;
		if(temp[1].equalsIgnoreCase("false") || temp[1].equals("true")) {
			SettingsPanel.getInstance().setAlwaysOnTop(Boolean.parseBoolean(temp[1]));
			return true;
		}
		return false;
	}
	
	private boolean setNightMode(String line) {
		String[] temp = line.split("=");

		if (temp.length != 2) 
			return false;
		if(!temp[0].equalsIgnoreCase("NightMode")) 
			return false;
		if(temp[1].equalsIgnoreCase("true")) {
			SettingsPanel.getInstance().setNightMode(Boolean.TRUE, true);
			return true;
		}
		if(temp[1].equalsIgnoreCase("false"))
			return true;
		return false;
	}
	
	private boolean setBackgroundColor(String line) {
		return setColor(line, "BackgroundColor");		
	}
	
	private boolean setButtonColor(String line) {
		return setColor(line, "ButtonColor");
	}
	
	private boolean setTextColor(String line) {
		return setColor(line, "TextColor");
	}
	
	private boolean setColor(String line, String descriptor) {
		String[] temp = line.split("=");

		if (temp.length != 2) 
			return false;
		if(!temp[0].equalsIgnoreCase(descriptor)) 
			return false;
		if(temp[1].equalsIgnoreCase("default"))
			return true;
		try {
			if(descriptor.equalsIgnoreCase("BackgroundColor"))
				SettingsPanel.getInstance().setBackgroundColor(new Color(Integer.parseInt(temp[1])));
			if(descriptor.equalsIgnoreCase("ButtonColor"))
				SettingsPanel.getInstance().setButtonColor(new Color(Integer.parseInt(temp[1])));
			if(descriptor.equalsIgnoreCase("TextColor"))
				SettingsPanel.getInstance().setTextColor(new Color(Integer.parseInt(temp[1])));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private void recreatePrefs(String path) {
		File file = new File(path);
		if(file.delete()) {
			getFile(getPath());
			initPrefFile(path);
		}
		else
			System.exit(1);
	}
}
