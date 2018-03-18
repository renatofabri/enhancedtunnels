package controller;

import view.ApplicationWindow;
import view.Browser;
import model.Server;
import model.Tunnel;
import netscape.javascript.JSObject;


public class JsOperations {

	static LogManager log = new LogManager();
	OperationManager om = new OperationManager();
	
	public void callFromJavascript(String msg) {
		log.info("callFromJavascript: " + msg);
	}

	public void logInfo(String msg) {
		log.info("JS info: " + msg);
	}

	public void logError(String msg) {
		log.error("JS error: " + msg);
	}

	public void resetPuttyPath() {
		log.debug("JsOperations:resetPuttyPath()");
		ApplicationWindow aw = new ApplicationWindow();
		aw.askPuttyLocation();
		Browser.getInstance().refreshAbout();
	}
  
	public void saveServer(JSObject obj) {
		log.debug("JsOperations:saveServer(obj)");
		Server.Builder builder = null;
		String id = (String) obj.getMember(Server.ID);

		if (id != null && id.matches("[-+]?\\d*\\.?\\d+"))
			builder = new Server.Builder(Long.parseLong(id));
		else
			builder = new Server.Builder();

		Server srv = builder
				.withName( (String)obj.getMember(Server.DISPLAY_NAME))
				.withHost( (String)obj.getMember(Server.HOST))
				.withPort(Integer.parseInt((String)obj.getMember(Server.PORT)))
				.withUsername( (String)obj.getMember(Server.USERNAME))
				.withPassword((String) obj.getMember(Server.PASSWORD))
					.build();
		om.addNewServer(srv);
	}

	public void saveTunnel(JSObject obj) {
		log.debug("JsOperations:saveTunnel()");
		Tunnel.Builder builder = null;
		String tunnelId = (String) obj.getMember(Tunnel.ID);

		if (tunnelId != null && tunnelId.matches("[-+]?\\d*\\.?\\d+"))
			builder = new Tunnel.Builder(Long.parseLong(tunnelId));
		else
			builder = new Tunnel.Builder();

		
		Tunnel tnl = builder
				.withName((String)obj.getMember(Tunnel.DISPLAY_NAME))
				.inPort(Integer.parseInt((String)obj.getMember(Tunnel.LOCAL_PORT)))
				.toHost((String)obj.getMember(Tunnel.REMOTE_HOST))
				.toPort(Integer.parseInt((String)obj.getMember(Tunnel.REMOTE_PORT)))
				.withDescription((String)obj.getMember(Tunnel.DESCRIPTION))
				.inServer(Long.parseLong((String)obj.getMember(Tunnel.PARENT_SERVER)))
				.launchable(Boolean.parseBoolean((String) obj.getMember(Tunnel.LAUNCHABLE)))
				.loggedAs((String) obj.getMember(Tunnel.USERNAME))
				.withPassword((String) obj.getMember(Tunnel.PASSWORD))
				.build();
		om.addNewTunnel(tnl);
	}

	public void deleteServer(String id) {
		log.debug("JsOperations:deleteServer("+id+")");
		Long srvId = Long.parseLong(id);
		om.removeServer(srvId);
	}
	
	public void deleteTunnel(String id) {
		log.debug("JsOperations:deleteTunnel("+id+")");
		Long tnlId = Long.parseLong(id);
		om.removeTunnel(tnlId);
	}

	public void launchServer(String id) {
		log.debug("JsOperations:launchServer("+id+")");
		Long srvId = Long.parseLong(id);
		Server srv = om.getServer(srvId);
		om.execute(om.getStringForPuTTY(srv));
	}

	public void launchTunnel(String id) {
		log.debug("JsOperations:launchTunnel("+id+")");
		Long tnlId = Long.parseLong(id);
		Tunnel tnl = om.getLaunchableTunnel(tnlId);
		if (tnl != null) {
			om.execute(om.getConnectionParam(tnl));
		}
		else {
			log.fatal("Cannot launch since tunnel is not launchable!");
		}
	}
}
