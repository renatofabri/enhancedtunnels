package model;

import java.io.File;
import java.nio.file.Paths;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import controller.LogManager;
import controller.files.FileSettings;

@XmlRootElement
public class Settings {

	static LogManager log = new LogManager();
	private static Settings instance = null;

	private int LOG_LEVEL;
	private String PUTTY_LOCATION;
	private String DATA_LOCATION;
	
	public Settings() {
		System.out.println("Settings()");
		this.LOG_LEVEL = 1;
		this.PUTTY_LOCATION = "";
		this.DATA_LOCATION = Paths.get(new File("").getAbsolutePath(), "data").toString();
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = FileSettings.getSettings();
		}
		return instance;
	}

	public void save () {
		log.info("Saving Settings to file");
		FileSettings.saveSettings(this);
	}

	public int getLogLevel() {
		return getLOG_LEVEL();
	}

	protected int getLOG_LEVEL() {
		return LOG_LEVEL;
	}

	@XmlElement
	protected void setLOG_LEVEL(int lOG_LEVEL) {
		LOG_LEVEL = lOG_LEVEL;
	}

	public String getPuttyLocation() {
		return getPUTTY_LOCATION();
	}

	protected String getPUTTY_LOCATION() {
		return PUTTY_LOCATION;
	}

	@XmlElement
	protected void setPUTTY_LOCATION(String pUTTY_LOCATION) {
		PUTTY_LOCATION = pUTTY_LOCATION;
	}

	public String getDataLocation() {
		return getDATA_LOCATION();
	}

	public String getDATA_LOCATION() {
		return DATA_LOCATION;
	}

	@XmlElement
	public void setDATA_LOCATION(String dATA_LOCATION) {
		DATA_LOCATION = dATA_LOCATION;
	}

	public boolean isPuttyLocationSet() {
		return (getPUTTY_LOCATION() != null && getPUTTY_LOCATION().trim().length() > 0);
	}

	public void setAndSavePuttyLocation(String path) {
		setPUTTY_LOCATION(path);
		save();
	}

	public String getLogPath() {
		return LogManager.ROOT_FOLDER.toString();
	}
}
