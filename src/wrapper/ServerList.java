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
		log.debug("ServerList()");
		this.serverList = new ArrayList<Server>();
	}

	public static void updateInstance(ServerList newInstance) {
		log.debug("ServerList:updateInstance()");
		if (newInstance != null) {
			instance = newInstance;
		} else {
			instance = new ServerList();
		}
	}

	public static ServerList getInstance() {
		log.debug("ServerList:getInstance()");
		if (instance == null) {
			instance = new ServerList();
		}
		instance.refresh(); 
		return instance;
	}

	public static boolean exists(long id) {
		log.debug("ServerList:exists()");
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
		log.debug("ServerList:refresh()");
		FileManager.getInstance().retrieveServers(); 
	}

	public void add (Server server) {
		log.debug("ServerList:add(server)");
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
		log.debug("ServerList:save()");
		log.info("Saving to file");
		FileManager.getInstance().saveServers(this);
	}

	/**
	 * Return a Server instance of the given ID
	 * @param id
	 * @return Server or Null if not found in list
	 */
	public Server load (long id) {
		log.debug("ServerList:load("+id+")");
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
		log.debug("ServerList:remove(server)");
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
		log.debug("ServerList:getServerList()");
		return serverList;
	}

	@XmlElement(name = "Server")
	public void setServerList(List<Server> serverList) {
		log.debug("ServerList:setServerList(serverList)");
		this.serverList = serverList;
	}

	public long getNextId() {
		log.debug("ServerList:getNextId()");
		Collection<Server> col = serverList;
		if (col.isEmpty()) {
			return 1;
		}
		Server highest = Collections.max(col, Server.ID_COMPARATOR);
		long nextId = highest.getId()+1;
		log.debug("Btw, the NextId is " + nextId);
		return nextId;
	}
}
