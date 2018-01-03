package model;

import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import controller.OperationManager;
import wrapper.ServerList;

@XmlRootElement
@XmlType(propOrder={"id","displayName", "host", "port", "username", "password"})
public class Server {

	private static final int DEFAULT_PORT = 22;

	private long id;
	private String displayName;
	private String host;
	private int port;
	private String username;
	private String password;
	private long parentServer;

	private Server () {
		
	}

	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
		Server srvObj = (Server) obj;
		return this.id == srvObj.getId();
	}

	public String toJSON () {
		OperationManager om = new OperationManager();
		
		List<Tunnel> tnlLst = om.getTunnelsFromServer(this.id);
		
		String tunnelListJSON = "";
		
		if (!tnlLst.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			
			for (Tunnel tnl : tnlLst) {
				sb.append(tnl.toJSON());
				sb.append(",");
			}
			tunnelListJSON = sb.substring(0, sb.toString().length()-1);
		}
		
		String jsonFormat =     "{\"id\":" + this.getId() + ", "
				  + "\"displayName\": \"" + this.getDisplayName() + "\", "
				  + "\"username\": \"" + this.getUsername() + "\", "
				  + "\"host\": \"" + this.getHost() + "\", "
				  + "\"port\": " + this.getPort() + ", "
				  + "\"tunnels\": [ " + tunnelListJSON + " ]}";
		
		return jsonFormat;
	}

	public static class Builder {

		private long id;
		private String displayName;
		private String host;
		private int port;
		private String username;
		private String password;
		private long parentServer;

		public Builder(long id) {
			this.id = id;
		}

		public Builder () {
			
		}
		
		public Builder withName(String name) {
			this.displayName = name;
			return this;
		}

		public Builder withHost(String host) {
			this.host = host;
			return this;
		}

		public Builder withDefaultPort() {
			this.port = DEFAULT_PORT;
			return this;
		}
		public Builder withPort(int port) {
			this.port = port;
			return this;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Builder underServer(long id) {
			this.parentServer = id;
			return this;
		}

		public Server build() {
			Server server = new Server();
			
			if (this.id == 0) {
				this.id = ServerList.getInstance().getNextId();
			}
			server.id = this.id;
			server.displayName = this.displayName;
			server.host = this.host;
			server.port = this.port;
			server.username = this.username;
			server.password = this.password;
			server.parentServer = this.parentServer;
			return server;
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

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
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

		public long getParentServer() {
			return parentServer;
		}

		public void setParentServer(long parentServer) {
			this.parentServer = parentServer;
		}
	}

	public static final Comparator<Server> ID_COMPARATOR = new Comparator<Server>() {
		public int compare(Server srv1, Server srv2) {
			return Double.compare(srv1.getId(), srv2.getId());
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

	public String getHost() {
		return host;
	}

	@XmlElement
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	@XmlElement
	public void setPort(int port) {
		this.port = port;
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

	public long getParentServer() {
		return parentServer;
	}

	@XmlAttribute
	public void setParentServer(long parentServer) {
		this.parentServer = parentServer;
	}
}
