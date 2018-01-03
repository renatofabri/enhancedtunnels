package test;

import model.Server;
import model.Tunnel;

public class Mock {

	public static Server createServer(long id) {
		Server server = new Server.Builder(id)
				.withName("Mock "+Long.toString(id))
				.withHost("mockhost")
				.withDefaultPort()
				.withUsername("mockusr")
				.withPassword("mockpass").build();
		return server;
	}

	public static Server createServerWithParent(long id, long parentId) {
		Server server = new Server.Builder(id)
				.withName("Mock "+Long.toString(id))
				.withHost("mockhost")
				.withDefaultPort()
				.withUsername("mockusr")
				.withPassword("mockpass")
				.underServer(parentId)
				.build();
		return server;
	}

	public static Tunnel createTunnel(long id, long serverId) {
		Tunnel tunnel = new Tunnel.Builder(id)
				.withName("Tunnel Mock "+Long.toString(id))
				.inPort(30000+(int)id)
				.toHost("mocktunnldhost")
				.toDefaultPort()
				.withDescription("Mocked tunnel "+Long.toString(id))
				.inServer(serverId)
				.build();
		return tunnel;
	}
}
