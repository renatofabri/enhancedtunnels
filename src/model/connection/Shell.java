package model.connection;

import com.jcraft.jsch.*;

public class Shell {
	
	final private int DEFAULT_TIMEOUT = 30000; 

	public Session createSession(String username, String host, int port, String password) {
		Session session = null;

		try {

			JSch jsch = new JSch();

			session = jsch.getSession(username, host, port);
			session.setPassword(password);

			session.setUserInfo(new User());

//			session.connect(DEFAULT_TIMEOUT);

			Channel channel = session.openChannel("shell");
			channel.setInputStream(System.in);
			channel.setOutputStream(System.out);

//			channel.connect(DEFAULT_TIMEOUT);

		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return session;
	}

	public Session setSessionTunnel(Session session, int lport, String host, int rport) {

		try {
			session.setPortForwardingL(lport, host, rport);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return session;
	}

	public boolean connectSession(Session session) {

		try {
			session.connect(DEFAULT_TIMEOUT);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return false;
	}

}
