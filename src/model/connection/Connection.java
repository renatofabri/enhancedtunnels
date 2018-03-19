package model.connection;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

public class Connection {

	private Session session;
	private Channel channel;

	public Connection (Session session, Channel channel) {
		this.session = session;
		this.channel = channel;
	}

	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}
