package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Settings {

	private static Settings instance = null;

	// someday this will be loaded from file
	private int LOG_LEVEL = 1;
	private String PUTTY_LOCATION = "C:\\eclipse";
	
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

}
