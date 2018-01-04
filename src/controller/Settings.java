package controller;

public class Settings {

	private static Settings instance = null;

	// someday this will be loaded from file
	private static final int LOG_LEVEL = 1;
	private static final String PUTTY_LOCATION = "C:\\eclipse";
	
	private Settings() {
		
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
