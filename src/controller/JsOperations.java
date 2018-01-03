package controller;

import model.Server;
import model.Tunnel;
import netscape.javascript.JSObject;


public class JsOperations {
	
	OperationManager om = new OperationManager();
	
	public void callFromJavascript(String msg) {
		System.out.println("callFromJavascript: " + msg);
    	}
  
    	public void saveServer(JSObject obj) {

    		Server srv = new Server.Builder()
    				.withName( (String)obj.getMember("serverName"))
				.withHost( (String)obj.getMember("serverHost"))
				.withPort(Integer.parseInt((String)obj.getMember("serverPort")))
				.withUsername( (String)obj.getMember("serverUsername"))
				.withPassword((String) obj.getMember("serverPassword"))
				.build();
		om.addNewServer(srv);
	}

	public void saveTunnel(JSObject obj) {
		
		Tunnel tnl = new Tunnel.Builder()
				.withName((String)obj.getMember("tunnelName"))
				.inPort(Integer.parseInt((String)obj.getMember("tunnelLocalPort")))
				.toHost((String)obj.getMember("tunnelRemoteHost"))
				.toPort(Integer.parseInt((String)obj.getMember("tunnelRemotePort")))
				.withDescription((String)obj.getMember("tunnelDescription"))
				.inServer(Long.parseLong((String)obj.getMember("tunnelParentServer")))
				.build();
		om.addNewTunnel(tnl);
	}

	public void deleteServer(String id) {
		Long srvId = Long.parseLong(id);
		om.removeServer(srvId);
	}
	
	public void deleteTunnel(String id) {
		Long tnlId = Long.parseLong(id);
		om.removeTunnel(tnlId);
	}

	public void launchServer(String id) {
		Long srvId = Long.parseLong(id);
		Server srv = om.getServer(srvId);
		om.execute(om.getStringForPuTTY(srv));
	}

}
