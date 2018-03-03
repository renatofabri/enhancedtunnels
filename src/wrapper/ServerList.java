package wrapper;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import controller.LogManager;
import controller.FileManager;
import model.Server;
import model.Tunnel;

/**
 * Singleton class
 * @author RENATOS
 *
 */
@XmlRootElement(name = "Servers")
public class ServerList {
	
	static LogManager log = new LogManager();

	private static ServerList instance = null;
	private List<Server> serverList;

	public ServerList() {
		this.serverList = new ArrayList<Server>();
	}

	public static void updateInstance(ServerList newInstance) {
		if (newInstance != null) {
			instance = newInstance;
		} else {
			instance = new ServerList();
		}
	}

	public static ServerList getInstance() {
		if (instance == null) {
			instance = new ServerList();
		}
		instance.refresh(); 
		return instance;
	}

	public static boolean exists(long id) {
		for (Server srv : instance.getServerList()) {
			if (id == srv.getId())
				return true;
		}
		return false;
	}

	/**
	 * This method refreshes the data in this Singleton
	 */
	private void refresh() {
		FileManager.getInstance().retrieveServers(); 
	}

	public void add (Server server) {
		log.info("Processing server with id " + server.getId() + "...");
		if (server.getParentServer() != 0 && !ServerList.exists(server.getParentServer())) {
			throw new InvalidParameterException("Parent Server ID informed is not valid");
		}
		if (serverList.contains(server)) {
			log.info("Server " + server.getId() + " already exists! Handling as update...");
			serverList.remove(server);
		}
		serverList.add(server);
		this.save();
	}

	/**
	 * Save a Server instance to the list
	 * @param server
	 */
	public void save () {
		log.info("Saving to file");
		FileManager.getInstance().saveServers(this);
	}

	/**
	 * Return a Server instance of the given ID
	 * @param id
	 * @return Server or Null if not found in list
	 */
	public Server load (long id) {
		for (Server srv : getServerList()) {
			if (srv.getId() == id)
				return srv;
		}
		return null;
	}

	/**
	 * Remove a Server instance from the list
	 * @param server
	 */
	public void remove (Server server) {
		log.info("Removing server with id " + server.getId());
		List<Tunnel> tnlLst = TunnelList.getInstance().getServerTunnels(server.getId());
		if (!tnlLst.isEmpty()) {
			log.info("Removing tunnels existent in Server("+ server.getId()+")...");
			for (Tunnel tnl : tnlLst) {
				TunnelList.getInstance().remove(tnl);
			}
			log.info("Done!");
		}
		serverList.remove(server);
		this.save();
	}
	
	public List<Server> getServerList() {
		return serverList;
	}

	@XmlElement(name = "Server")
	public void setServerList(List<Server> serverList) {
		this.serverList = serverList;
	}

	public long getNextId() {
		Collection<Server> col = serverList;
		if (col.isEmpty()) {
			return 1;
		}
		Server highest = Collections.max(col, Server.ID_COMPARATOR);
		return highest.getId()+1;
	}
}
