package controller;

import java.util.List;

import wrapper.ServerList;
import wrapper.TunnelList;
import model.Server;
import model.Settings;
import model.Tunnel;

/**
 * Unfortunately, we need the terminal open :(
 * https://www.chiark.greenend.org.uk/~sgtatham/putty/wishlist/no-terminal-window.html
 */

public class OperationManager {
	
	static LogManager log = new LogManager();

	/**
	 * Connection string
	 * Format: username@host:port -pw password
	 * @param server
	 * @return
	 */
	public String getConnectionParam(Server server) {
		StringBuilder str = new StringBuilder();
		str.append(String.format("%s@%s -P %s -pw %s", server.getUsername(), 
													server.getHost(), 
													Integer.toString(server.getPort()),
													server.getPassword()));
		
		String connectionParam = str.toString();
		log.info("connection param: "+connectionParam);
		return connectionParam;
	}

	/**
	 * This method will return the ports as String to be used by PuTTY
	 * Fomat: -L localPort:RemoteHost:RemotePort
	 * @return
	 */
	public String getPortsParam(List<Tunnel> tunnelList) {
		StringBuilder str = new StringBuilder();
		for (Tunnel tunnel : tunnelList) {
			str.append(String.format("-L %s:%s:%s ", Integer.toString(tunnel.getLocalPort()), 
													tunnel.getRemoteHost(), 
													tunnel.getRemotePort()));
		}
		String portsParam = str.toString();
		log.info("ports: " + portsParam);
		return portsParam;
	}

	/** 
	 * Get full parameters to connect to a server
	 * @param server
	 * @return
	 */
	public String getStringForPuTTY(Server server) {
		String serverConn = getConnectionParam(server);
		String tunnels = getPortsParam(TunnelList.getInstance().getServerTunnels(server.getId()));
		return String.format("%s %s", serverConn, tunnels);
	}

	/**
	 * Adds a new server
	 * @param srv
	 */
	public void addNewServer(Server srv) {
		ServerList.getInstance().add(srv);
	}

	public void removeServer(long id) {
		ServerList.getInstance().remove(new Server.Builder(id).build());
		
	}

	public List<Server> getServers() {
		return ServerList.getInstance().getServerList();
	}

	public Server getServer(long id) {
		return ServerList.getInstance().load(id);
	}

	public List<Tunnel> getTunnelsFromServer(long id) {
		return TunnelList.getInstance().getServerTunnels(id);
	}

	/**
	 * Adds a new tunnel
	 * @param tnl
	 */
	public void addNewTunnel(Tunnel tnl) {
		TunnelList.getInstance().add(tnl);
	}
	
	public void removeTunnel(long id) {
		TunnelList.getInstance().remove(new Tunnel.Builder(id).build());
	}

	public List<Tunnel> getTunnels() {
		return TunnelList.getInstance().getTunnelList();
	}

	/**
	 * Accessing and executing commands in CMD
	 */
	public void execute(String param) {
		
		try {
			
			String command = "cmd /c cd " + Settings.getInstance().getPuttyLocation() + "&& start putty.exe " + param;
			log.info(command);
			Process p = Runtime.getRuntime().exec(command);
			log.info(String.format("Return code: %s", p.waitFor()));
			
		}
		catch(Exception e) {
			log.error(e.getStackTrace().toString());
			e.printStackTrace();
		}
	}

	/**
	 * Check if port is available; if not, launch required server first
	 */
	public void checkAndExecute(String param) {
		
	}

}
