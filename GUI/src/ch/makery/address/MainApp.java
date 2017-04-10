package ch.makery.address;

import java.io.IOException;

import ch.makery.address.view.insideExistingProjectController;
import ch.makery.address.view.insideNewDeviceController;
import ch.makery.address.view.insideNewProjectController;
import ch.makery.address.view.mainItemsController;
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
    public void showPersonOverview() throws Exception{
    	// Load person overview.
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();

        // Set person overview into the center of root layout.
        rootLayout.setCenter(personOverview);
        
        mainItemsController controller = loader.getController();
        controller.setMainApp(this);
    }
    
    
    public static boolean showNewScene() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/createNewProject.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage dialogStage = new Stage();
    	dialogStage.setTitle("Creating New Project");
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	dialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	dialogStage.setScene(scene);
    	
    	insideNewProjectController controller = loader.getController();
    	controller.setDialogStage(dialogStage);   	
    	
    	dialogStage.showAndWait();
    	return controller.isOkClicked();
    }
    
    public static boolean showNewScene1() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/existingProject.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage dialogStage = new Stage();
    	dialogStage.setTitle("Import Existing Project");
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	dialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	dialogStage.setScene(scene);
    	
    	insideExistingProjectController controller = loader.getController();
    	controller.setDialogStage(dialogStage);   	
    	
    	dialogStage.showAndWait();
    	return controller.isOkClicked();  	
    }
    
    public static boolean showNewScene2() throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/newDevice.fxml"));
    	AnchorPane createProject = loader.load();
    	
    	Stage dialogStage = new Stage();
    	dialogStage.setTitle("Create a New Device");
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	dialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	dialogStage.setScene(scene);
    	
    	insideNewDeviceController controller = loader.getController();
    	controller.setDialogStage(dialogStage);   	
    	
    	dialogStage.showAndWait();
    	return controller.isOkClicked();
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
