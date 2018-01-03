package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import model.Server;
import model.Tunnel;
import controller.OperationManager;

public class Main {

	public static void main (String[] args) {
		Class<Tests> tests = Tests.class;
		Method[] allMethods = tests.getDeclaredMethods();
		System.out.println("------------------");
		for (Method m : allMethods) {
			try {
				preTest();
				System.out.println(String.format("Starting %s", m.toString()));
				boolean result = (boolean) m.invoke(new Tests());
				System.out.println(String.format("%s: %s", m.toString(), (result? "OK" : "Failed")));
				
				
				System.out.println("------------------");
				postTest();
				
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static void preTest() {}
	private static void postTest() {
		OperationManager om = new OperationManager();
		List<Server> srvLst = om.getServers();
		for (Server srv : srvLst) {
			om.removeServer(srv.getId());
		}
		
		List<Tunnel> tnlLst = om.getTunnels();
		for (Tunnel tnl : tnlLst) {
			om.removeTunnel(tnl.getId());
		}
	}
}
