package model;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import wrapper.TunnelList;

@XmlRootElement
@XmlType(propOrder={"id","displayName", "localPort", "remoteHost", "remotePort", "description", "parentServer", "username", "password"})
public class Tunnel {

	public static final String ID = "id";
	public static final String DISPLAY_NAME = "display_name";
	public static final String LOCAL_PORT  = "local_port";
	public static final String REMOTE_HOST = "remote_host";
	public static final String REMOTE_PORT = "remote_port";
	public static final String DESCRIPTION = "description";
	public static final String PARENT_SERVER = "parent_server";
	public static final String LAUNCHABLE  = "launchable";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public static final int DEFAULT_PORT = 22;

	private long id;
	private String displayName;
	private int localPort;
	private String remoteHost;
	private int remotePort;
	private String description;
	private long parentServer;
	private boolean launchable;
	private String username;
	private String password;

	private Tunnel () {
		
	}
	
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
		Tunnel tnlObj = (Tunnel) obj;
		return this.id == tnlObj.getId();
	}

	public String toJSON() {
		String jsonFormat = "{\"" + ID + "\": " + this.id + ", "
				+ "\"" + DISPLAY_NAME + "\": \"" + this.displayName + "\", "
				+ "\"" + LOCAL_PORT + "\": " + this.localPort + ", "
				+ "\"" + REMOTE_HOST + "\": \"" + this.remoteHost + "\", "
				+ "\"" + REMOTE_PORT + "\": " + this.remotePort + ", "
				+ "\"" + DESCRIPTION + "\": \"" + this.description + "\", "
				+ "\"" + PARENT_SERVER + "\": " + this.parentServer + ", "
				+ "\"" + LAUNCHABLE + "\": \"" + this.isLaunchable() + "\", "
				+ "\"" + USERNAME + "\": \"" + this.getUsername() + "\", "
				+ "\"" + PASSWORD + "\": \"" + this.getPassword() + "\"}";
		return jsonFormat;
	}
	
	public static class Builder {
		
		private long id;
		private String displayName;
		private int localPort;
		private String remoteHost;
		private int remotePort;
		private String description;
		private long parentServer;
		private boolean launchable = false;
		private String username;
		private String password;

		public Builder(long id) {
			this.id = id;
		}

		public Builder () {
			
		}

		public Builder withName(String name) {
			this.displayName = name;
			return this;
		}
		
		public Builder inPort(int localPort) {
			this.localPort = localPort;
			return this;
		}
		
		public Builder toHost(String host) {
			this.remoteHost = host;
			return this;
		}

		public Builder toDefaultPort() {
			this.remotePort = DEFAULT_PORT;
			return this;
		}

		public Builder toPort (int port) {
			this.remotePort = port;
			return this;
		}

		public Builder withDescription (String description) {
			this.description = description;
			return this;
		}

		public Builder inServer (long id) {
			this.parentServer = id;
			return this;
		}

		public Builder launchable(boolean launchable) {
			this.launchable = launchable;
			return this;
		}

		public Builder loggedAs (String username) {
			this.username = username;
			return this;
		}

		public Builder withPassword (String password) {
			this.password = password;
			return this;
		}

		public Tunnel build () {
			Tunnel tunnel = new Tunnel();
			if (this.id == 0) {
				this.id = TunnelList.getInstance().getNextId();
			}
			tunnel.id = this.id;
			tunnel.displayName = this.displayName;
			tunnel.localPort = this.localPort;
			tunnel.remoteHost = this.remoteHost;
			tunnel.remotePort = this.remotePort;
			tunnel.description = this.description;

			tunnel.parentServer = this.parentServer;

			tunnel.launchable = this.launchable;
			tunnel.username = this.username;
			tunnel.password = this.password;

			return tunnel;
		}

		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public int getLocalPort() {
			return localPort;
		}
		public void setLocalPort(int localPort) {
			this.localPort = localPort;
		}
		public String getRemoteHost() {
			return remoteHost;
		}
		public void setRemoteHost(String remoteHost) {
			this.remoteHost = remoteHost;
		}
		public int getRemotePort() {
			return remotePort;
		}
		public void setRemotePort(int remotePort) {
			this.remotePort = remotePort;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public long getParentServer() {
			return parentServer;
		}
		public void setParentServer(long parentServer) {
			this.parentServer = parentServer;
		}

		public boolean isLaunchable() {
			return launchable;
		}

		public void setLaunchable(boolean isLaunchable) {
			this.launchable = isLaunchable;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static final Comparator<Tunnel> ID_COMPARATOR = new Comparator<Tunnel>() {
		public int compare(Tunnel tnl1, Tunnel tnl2) {
			return Double.compare(tnl1.getId(), tnl2.getId());
		}
	};

	public long getId() {
		return id;
	}

	@XmlAttribute
	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	@XmlAttribute
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getLocalPort() {
		return localPort;
	}

	@XmlAttribute
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	@XmlElement
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	@XmlElement
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public long getParentServer() {
		return parentServer;
	}

	@XmlAttribute
	public void setParentServer(long parentServer) {
		this.parentServer = parentServer;
	}

	public boolean isLaunchable() {
		return launchable;
	}

	@XmlAttribute
	public void setLaunchable(boolean isLaunchable) {
		this.launchable = isLaunchable;
	}

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
}
