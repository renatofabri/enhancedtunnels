package controller.files;

import java.io.File;

import controller.files.base.FileBase;
import controller.files.base.FileRead;
import controller.files.base.FileWrite;
import model.Settings;
import wrapper.ServerList;

public class FileServer {

	public static final String FILE_NAME = "servers.properties";
	public static final String ROOT_PATH = Settings.getInstance().getDataLocation();
	private static final File FILE_OBJ = FileBase.getFile(ROOT_PATH, FILE_NAME);


	/**
	 * This method will retrieve all servers stored in file
	 * @return ServerList instance containing all servers 
	 */
	public static ServerList getServers() {

		ServerList serversInFile = (ServerList) FileRead.convertToClass(new ServerList(), FILE_OBJ);
		return serversInFile;
	}

	/**
	 * this method will save all servers in file
	 * @param serverList
	 */
	public static void saveServers(ServerList serverList) {
		FileWrite.convertToXML(serverList, FILE_OBJ);
	}
}
