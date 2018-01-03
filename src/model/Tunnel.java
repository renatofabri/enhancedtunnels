package model;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import wrapper.TunnelList;

@XmlRootElement
@XmlType(propOrder={"id","displayName", "localPort", "remoteHost", "remotePort", "description", "parentServer"})
public class Tunnel {

	public static final int DEFAULT_PORT = 22;

	private long id;
	private String displayName;
	private int localPort;
	private String remoteHost;
	private int remotePort;
	private String description;
	private long parentServer;

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
		String jsonFormat = "{\"id\": " + this.id + ", "
				+ "\"displayName\": \"" + this.displayName + "\", "
				+ "\"localPort\": " + this.localPort + ", "
				+ "\"remoteHost\": \"" + this.remoteHost + "\", "
				+ "\"remotePort\": " + this.remotePort + ", "
				+ "\"description\": \"" + this.description + "\", "
				+ "\"parentServer\": " + this.parentServer + "}";
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
}
