package view;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import model.Server;
import model.Settings;
import netscape.javascript.JSObject;
import controller.JsOperations;
import controller.LogManager;
import controller.OperationManager;

public class Browser extends Region {

	static LogManager log = new LogManager();

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	final OperationManager om = new OperationManager();
	static Browser instance = null;

	public static Browser getInstance() {
		if (instance == null)
			instance = new Browser();
		return instance;
	}
 
	private Browser() {
		
//		getStyleClass().add("browser");
//		browser.setContextMenuEnabled(false);
		
		String url = Browser.class.getResource("/template/index.html").toExternalForm();
		
        webEngine.load(url);

        // check when page has loaded
        webEngine.getLoadWorker().stateProperty().addListener(
        		new ChangeListener<State>(){
        			@Override
        			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

        				if (newState == State.SUCCEEDED) {
        					try {
        						preLoad();
        						load();
        					}
        					catch (Exception e) {
        						log.fatal("Unfortunately, s**t happens! " + e.toString());
        					}
        				}
        			}
        		});
        
        // handle alert() from JS
        webEngine.setOnAlert(
        		new EventHandler<WebEvent<String>>() {

					@Override
					public void handle(WebEvent<String> event) {
						System.out.println(event.getData());
					}
        			
        		}
        );
        
        getChildren().add(browser);
    }

	private void preLoad() {
		if (!Settings.getInstance().isPuttyLocationSet()) {
//			webEngine.executeScript("alertModal('" + "PuTTY path was not informed. <br>Servers won&apos;t be launched." + "');");
		}
	}

	private void load() {
		loadJsApp();
		loadAbout();
		loadServersInGUI();
	}

	private void loadAbout() {
		webEngine.executeScript("addPathToPutty('" + Settings.getInstance().getPuttyLocation().replace("\\", "\\\\") + "');");
		webEngine.executeScript("addLogLevel('" + Settings.getInstance().getLogLevel() + "');");
		webEngine.executeScript("addLogPath('" + Settings.getInstance().getLogPath().replace("\\", "\\\\") + "');");
	}

	public void refreshAbout() {
		loadAbout();
	}

	private void loadJsApp() {
		JSObject window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", new JsOperations());
	}

	private void loadServersInGUI() {
		List<Server> srvLst = om.getServers();

		if (srvLst == null)
			return;

		for (Server srv : srvLst) {
			webEngine.executeScript(
					"addServer('" + srv.toJSON() + "');"
			);
		}
	}


    @Override 
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override 
    protected double computePrefWidth(double height) {
        return 1024;
    }
 
    @Override 
    protected double computePrefHeight(double width) {
        return 600;
    }
    
}