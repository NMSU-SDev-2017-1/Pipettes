package ch.makery.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static Stage primaryStage;
    private static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pipette GUI - Welcome Page");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public static void showPersonOverview() throws Exception{
    	// Load person overview.
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();

        // Set person overview into the center of root layout.
        rootLayout.setCenter(personOverview);
    }
    
    public static void showNewScene() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/createNewProject.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle("Creating New Project");
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.showAndWait();
    }
    
    public static void showNewScene1() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/existingProject.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle("Import Existing Project");
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.showAndWait();
    }
    
    public static void showNewScene2() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/newDevice.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle("Create a New Device");
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.showAndWait();
    }
    
    /*public static void showSecondView() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/newDevice.fxml"));
    	BorderPane createProject = loader.load();
    	
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle("Create a New Device");
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.showAndWait();
    }*/

	public static void main(String[] args) {
		launch(args);
	}
}
