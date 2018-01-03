package test;

import java.security.InvalidParameterException;

import controller.OperationManager;
import model.Server;
import model.Tunnel;

public class Tests {
	OperationManager om = new OperationManager();

	public boolean test_addNewServer() {

		Server s = Mock.createServer(5);
		om.addNewServer(s);
		return 1 == om.getServers().size();
	}

	public boolean test_removeServer() {
		om.removeServer(5);
		return 0 == om.getServers().size();
	}

	public boolean test_removeInvalidServer() {
		Server srv = Mock.createServer(5);
		om.addNewServer(srv);
		om.removeServer(6);
		//try to remove invalid id
		//certify that wrong id wasn't removed
		return 1 == om.getServers().size();
	}

	public boolean test_addDuplicatedServer() {
		long id = 5;
		Server srv = Mock.createServer(id);
		om.addNewServer(srv);
		Server srv2 = Mock.createServer(id);
		// should fail, leaving only 1 element in the list
		om.addNewServer(srv2);
		return 1 == om.getServers().size();
	}

	public boolean test_addNewTunnel() {
		Server srv = Mock.createServer((long) 5);
		om.addNewServer(srv);
		Tunnel tnl = Mock.createTunnel(5, 5);
		om.addNewTunnel(tnl);
		return 1 == om.getTunnels().size();
	}

	public boolean test_addTunnelInvalidServer() {
		Tunnel tnl = Mock.createTunnel(5, 18);
		try {
			om.addNewTunnel(tnl);
		} 
		catch (InvalidParameterException e) {
			return true;
		}
		return false;
	}

	public boolean test_removeTunnel() {
		Server srv = Mock.createServer(5);
		om.addNewServer(srv);

		int tunnelId = 5;
		Tunnel tnl = Mock.createTunnel(tunnelId, 5);
		om.addNewTunnel(tnl);
		
		om.removeTunnel(tunnelId);
		return 0 == om.getTunnels().size();
	}

	public boolean test_removeInvalidTunnel() {
		Server srv = Mock.createServer((long) 5);
		om.addNewServer(srv);
		Tunnel tnl = Mock.createTunnel(5, 5);
		om.addNewTunnel(tnl);
		//try to remove invalid id
		//certify that wrong id wasn't removed
		om.removeTunnel(6);
		return 1 == om.getTunnels().size();
	}

	public boolean test_addDuplicatedTunnel() {
		long parentId = 5;
		Server srv = Mock.createServer((long) parentId);
		om.addNewServer(srv);
		
		long  id= 5;
		Tunnel tnl = Mock.createTunnel(id, parentId);
		om.addNewTunnel(tnl);
		Tunnel tnl2 = Mock.createTunnel(id, parentId);
		om.addNewTunnel(tnl2);
		return 1 == om.getTunnels().size();
	}

	public boolean test_retrieveStrings() {
		Server s = Mock.createServer(5);
		om.addNewServer(s);
		Tunnel tnl = Mock.createTunnel(5, 5);
		om.addNewTunnel(tnl);
		Tunnel tnl2 = Mock.createTunnel(6, 5);
		om.addNewTunnel(tnl2);

		System.out.println(om.getStringForPuTTY(s));
		return true;
	}
}
