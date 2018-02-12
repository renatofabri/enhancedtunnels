package view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import controller.LogManager;
import model.Settings;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AppWindow extends Application {

	static LogManager log = new LogManager();
	
    public static void main(String[] args) {
    	log.info("Launching the application!");
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("EnhancedTunnels: a tunneling assistant");
        loadIcon(primaryStage);
        loadPuttyCheck();
        loadApp(primaryStage);
        primaryStage.show();
    }

	private void loadIcon(Stage stg) {
		Path path = Paths.get(new File("").getAbsolutePath(), "data");
        try {
        	String imageURL = new File(path.toString(), "tunnel.png").toURI().toURL().toExternalForm();
        	stg.getIcons().add(new Image(imageURL));
        }
        catch (Exception e) {
        	log.error("Couldn't load Icon");
        	log.error(e.getMessage());
        	e.printStackTrace();
        }
	}

	private void loadPuttyCheck() {
    	if (Settings.getInstance().isPuttyLocationSet())
    		return;

    	final Stage stg = new Stage();
    	loadIcon(stg);
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
		Scene appScene = new Scene(Browser.getInstance(),1024,600);
        primaryStage.setScene(appScene);
	}

    public void askPuttyLocation() {

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