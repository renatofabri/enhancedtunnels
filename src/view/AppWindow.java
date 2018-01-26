package view;

import java.io.File;
import java.util.List;

import controller.JsOperations;
import controller.LogManager;
import controller.OperationManager;
import model.Server;
import model.Settings;
import netscape.javascript.JSObject;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;

public class AppWindow extends Application {

	static LogManager log = new LogManager();
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("EnhancedTunnels: a tunneling assistant");

        loadPuttyCheck();

        loadApp(primaryStage);
        primaryStage.show();
    }

	private void loadPuttyCheck() {
    	if (Settings.getInstance().isPuttyLocationSet())
    		return;

    	final Stage stg = new Stage();
    	stg.setTitle("May I have your attention please");
    	BorderPane base = new BorderPane();
    	
    	base.setPadding(new Insets(10, 10, 10, 10));

    	base.setTop(null);
    	base.setCenter(createCenterGrid(stg));
        base.setBottom(createFooterGrid(stg));

        Scene popup = new Scene(base);
		stg.setScene(popup);
        stg.setResizable(false);
        stg.setIconified(false);
        stg.showAndWait();
	}

	private GridPane createCenterGrid(final Stage stg) {
		GridPane grid = new GridPane();
    	grid.add(new Label("To begin with, please select your PuTTY executable by clicking in 'Select Path' button."), 
    			1, 1);
    	grid.add(new Label("You can skip this step by clicking in 'Skip', but the application won't work properly."), 
    			1, 2);
		return grid;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GridPane createFooterGrid(final Stage stg) {

		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER_RIGHT);

		Button skipPath = new Button("Skip");
		skipPath.setOnAction(new EventHandler (){
			@Override
			public void handle(Event arg0) {
				stg.close();
			}
		});

		grid.add(skipPath, 1, 1);
		GridPane.setMargin(skipPath, new Insets(0, 10, 0, 0));

		Button selectPath = new Button("Select Path");
		selectPath.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				stg.close();
				askPuttyLocation();
			}
		});

		grid.add(selectPath, 2, 1);
		GridPane.setMargin(selectPath, new Insets(0, 0, 0, 10));

		return grid;
	}

	private void loadApp(Stage primaryStage) {
		Scene appScene = new Scene(new Browser(),1024,600);
        primaryStage.setScene(appScene);
	}

    private void askPuttyLocation() {

    	Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File puttyExe = fileChooser.showOpenDialog(stage);
        if (puttyExe != null) {
        	log.info("Path informed: " + puttyExe.toString());
        	Settings.getInstance().setAndSavePuttyLocation(puttyExe.toString());
        }
        else {
        	log.error("PuTTY path not informed. Servers won't be launched.");
        }
    }

    private void configureFileChooser(final FileChooser fileChooser) {
    	fileChooser.setTitle("Select the PuTTY executable that will be used");
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PuTTY Executable", "*.exe")
        );
    }
}

class Browser extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();
	final OperationManager om = new OperationManager();
 
	public Browser() {
		
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

        					preLoadChecks();
        					loadServersInGUI();
        					loadJsApp();
        				}
        			}

					private void preLoadChecks() {
						if (!Settings.getInstance().isPuttyLocationSet()) {
							webEngine.executeScript(
									"alertModal('" + "PuTTY path was not informed. <br>Servers won&apos;t be launched." + "');"
							);
						}
					}

					private void loadJsApp() {
						JSObject window = (JSObject) webEngine.executeScript("window");
						window.setMember("app", new JsOperations());
					}

					private void loadServersInGUI() {
						List<Server> srvLst = om.getServers();

						for (Server srv : srvLst) {
							webEngine.executeScript(
									"addServer('" + srv.toJSON() + "');"
							);
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