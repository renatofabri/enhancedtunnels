package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import wrapper.ServerList;
import wrapper.TunnelList;

public class XmlManager {
	
	

	private static XmlManager instance = null;

	public static final Path ROOT_FOLDER = Paths.get(new File("").getAbsolutePath(), "data");
	public static final String FILE_SERVER = "servers.properties";
	public static final String FILE_TUNNELS = "tunnels.properties";


	private XmlManager() {
		
	}

	public static XmlManager getInstance() {
		if (instance == null) {
			instance = new XmlManager();
		}
		return instance;
	}


	private File getServerFile() {
		return getFile(FILE_SERVER);
	}

	private File getTunnelFile() {
		return getFile(FILE_TUNNELS);
	}

	private File getFile(String filename) {
		File file = null;
		try {
			file = new File(ROOT_FOLDER.toString());
			
			if (Files.notExists(ROOT_FOLDER)) file.mkdirs();
			
			file = new File(ROOT_FOLDER.toString(), filename);
			if (!(file.exists()&& !file.isDirectory())) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public void retrieveServers() {
		ServerList.updateInstance((ServerList) convertToClass(new ServerList(), getServerFile()));
	}
	public void saveServers(ServerList serverList) {
		convertToXML(serverList, getServerFile());
	}

	public void retrieveTunnels() {
		TunnelList.updateInstance((TunnelList) convertToClass(new TunnelList(), getTunnelFile()));
	}

	public void saveTunnels(TunnelList tunnelList) {
		convertToXML(tunnelList, getTunnelFile());
	}



	public static void convertToXML(Object object, File file) {

		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			marshaller.marshal(object, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static Object convertToClass(Object object, File file) {

		if (isFileEmpty(file)) {
			return null;
		}

		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (Object) unmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isFileEmpty(File file) {
		boolean isEmpty = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));     
			if (br.readLine() == null) {
			    isEmpty = true;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isEmpty;
	}
}
