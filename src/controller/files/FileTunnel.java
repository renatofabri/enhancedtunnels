package controller.files;

import java.io.File;

import controller.files.base.FileBase;
import controller.files.base.FileRead;
import controller.files.base.FileWrite;
import model.Settings;
import wrapper.TunnelList;

public class FileTunnel {

	public static final String FILE_NAME = "tunnels.properties";
	public static final String ROOT_PATH = Settings.getInstance().getDataLocation();
	private static final File FILE_OBJ = FileBase.getFile(ROOT_PATH, FILE_NAME);


	/**
	 * This method will retrieve all servers stored in file
	 * @return ServerList instance containing all servers 
	 */
	public static TunnelList getTunnels() {

		TunnelList tunnelsInFile = (TunnelList) FileRead.convertToClass(new TunnelList(), FILE_OBJ);
		return tunnelsInFile;
	}

	/**
	 * this method will save all servers in file
	 * @param serverList
	 */
	public static void saveTunnels(TunnelList tunnelList) {
		FileWrite.convertToXML(tunnelList, FILE_OBJ);
	}
}
