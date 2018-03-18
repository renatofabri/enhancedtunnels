package controller.files;

import java.io.File;
import java.nio.file.Paths;

import controller.files.base.FileBase;
import controller.files.base.FileRead;
import controller.files.base.FileWrite;
import model.Settings;

public class FileSettings {

	public static final String FILE_NAME = "settings.properties";
	public static final String ROOT_PATH = Paths.get(new File("").getAbsolutePath(), "data").toString();
	private static final File FILE_OBJ = FileBase.getFile(ROOT_PATH, FILE_NAME);


	/**
	 * This method will retrieve Settings stored in file
	 * @return Settings 
	 */
	public static Settings getSettings() {
		Settings settings = (Settings) FileRead.convertToClass(new Settings(), FILE_OBJ);
		return settings;
	}

	/**
	 * this method will save all servers in file
	 * @param serverList
	 */
	public static void saveSettings(Settings settings) {
		FileWrite.convertToXML(settings, FILE_OBJ);
	}
}
