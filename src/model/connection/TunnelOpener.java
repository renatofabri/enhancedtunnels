package model.connection;

import model.Server;
import model.Tunnel;

public class TunnelOpener {

	/**
	 * This method will open all tunnel ports of the given server
	 * @param server
	 * @return Connection containing SSH Connection
	 */
	public static Connection openServerTunnels(Server server) {

		Connection conn = Shell.createConnection(server);
//		Shell.connect(conn);
		for (Tunnel tnl : server.getTunnels()) {
			Shell.setConnectionTunnel(conn, tnl);
		}
		return conn;
	}
}
