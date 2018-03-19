package model.connection;

import model.Server;
import model.Tunnel;

import com.jcraft.jsch.*;

public class Shell {
	
	private static final int DEFAULT_TIMEOUT = 30000; 

	/**
	 * Responsible for creating a connection, by defining session, user and channel. 
	 * @param username
	 * @param host
	 * @param port
	 * @param password
	 * @return
	 */
	public Connection createConnection(Server server) {
		Connection connection = null;

		try {

			JSch jsch = new JSch();

			Session session = jsch.getSession(server.getUsername(), 
											  server.getHost(), 
											  server.getPort());
			session.setPassword(server.getPassword());

			session.setUserInfo(new User());

//			session.connect(DEFAULT_TIMEOUT);

			Channel channel = session.openChannel("shell");
			channel.setInputStream(System.in);
			channel.setOutputStream(System.out);

			connection = new Connection(session, channel);

//			channel.connect(DEFAULT_TIMEOUT);

		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return connection;
	}

	public Connection setConnectionTunnel(Connection conn, Tunnel tunnel) {

		try {
			conn.getSession().setPortForwardingL(tunnel.getLocalPort(), 
												 tunnel.getRemoteHost(), 
												 tunnel.getRemotePort());
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return conn;
	}

	public boolean connect(Connection conn) {

		try {
			conn.getSession().connect(DEFAULT_TIMEOUT);

			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}

}
