package view;

import java.util.List;

import controller.JsOperations;
import controller.OperationManager;
import model.Server;
import model.Tunnel;
import netscape.javascript.JSObject;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;

public class AppWindow extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("EnhancedTunnels: a tunneling assistant");
  

        Scene appScene = new Scene(new Browser(),1024,600);
        
        primaryStage.setScene(appScene);
        primaryStage.show();
    }
}

class Browser extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
 
	public Browser() {
		
//		getStyleClass().add("browser");
		
		final OperationManager om = new OperationManager();

		String url = Browser.class.getResource("/template/index.html").toExternalForm();
		
        webEngine.load(url);

        // check when page has loaded
        webEngine.getLoadWorker().stateProperty().addListener(
        		new ChangeListener<State>(){
        			
        			@Override
        			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {

        				if (newState == State.SUCCEEDED) {
        					
        					List<Server> srvLst = om.getServers();
        					
        					for (Server srv : srvLst) {
	        					webEngine.executeScript(
	        							"addServer('" + srv.toJSON() + "');"
	        					);
        					}
        					
        					// app
        					JSObject window = (JSObject) webEngine.executeScript("window");
        					window.setMember("app", new JsOperations());
        				}
        			}
        		}
        );
        
        JSObject window = (JSObject) webEngine.executeScript("window");
        window.setMember("app", new JsOperations());

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

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 1024;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 600;
    }
    
}