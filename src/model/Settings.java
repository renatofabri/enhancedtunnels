package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {

	private static Settings instance = null;

	// someday this will be loaded from file
	private final int LOG_LEVEL = 1;
	private final String PUTTY_LOCATION = "C:\\eclipse";
	
	private Settings() {
		
	}

	public static void updateInstance(Settings st) {
		instance = st;
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public int getLogLevel() {
		return LOG_LEVEL;
	}

	public String getPuttyLocation() {
		return PUTTY_LOCATION;
	}
}
