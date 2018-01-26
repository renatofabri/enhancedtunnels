package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import controller.FileManager;
import controller.LogManager;

@XmlRootElement
public class Settings {

	static LogManager log = new LogManager();
	private static Settings instance = null;

	// someday this will be loaded from file
	private int LOG_LEVEL = 1;
	private String PUTTY_LOCATION = "";
	
	public Settings() {
		
	}

	public static void updateInstance(Settings st) {
		if (st != null) {
			instance = st;
		} else {
			instance = new Settings();
		}
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		instance.refresh();
		return instance;
	}

	private void refresh() {
		FileManager.getInstance().retrieveSettings();
	}

	public void save () {
		log.info("Saving Settings to file");
		FileManager.getInstance().saveSettings(this);
	}

	public int getLogLevel() {
		return getLOG_LEVEL();
	}

	public int getLOG_LEVEL() {
		return LOG_LEVEL;
	}

	@XmlElement
	public void setLOG_LEVEL(int lOG_LEVEL) {
		LOG_LEVEL = lOG_LEVEL;
	}

	public String getPuttyLocation() {
		return getPUTTY_LOCATION();
	}

	public String getPUTTY_LOCATION() {
		return PUTTY_LOCATION;
	}

	@XmlElement
	public void setPUTTY_LOCATION(String pUTTY_LOCATION) {
		PUTTY_LOCATION = pUTTY_LOCATION;
	}

	public boolean isPuttyLocationSet() {
		return (getPUTTY_LOCATION() != null && getPUTTY_LOCATION().trim().length() > 0);
	}

	public void setAndSavePuttyLocation(String path) {
		setPUTTY_LOCATION(path);
		save();
	}
}
