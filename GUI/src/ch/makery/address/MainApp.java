package ch.makery.address;

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

	public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pipette GUI - Welcome Page");

        //initRootLayout();

        showPersonOverview();
        
    }

    /**
     * Initializes the root layout.
     */
    /*public void initRootLayout() throws Exception  {
    	// Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }*/

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() throws Exception{
    	// Load person overview.
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/personOverview.fxml"));
        BorderPane personOverview = loader.load();
        Scene scene = new Scene(personOverview);
        primaryStage.setScene(scene);
        primaryStage.show();
        
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
    
    public static void showSecondView(String name1) throws Exception  {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainApp.class.getResource("view/example.fxml"));
    	BorderPane createProject = loader.load();
    	
    	primaryStage.hide();
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle(name1);
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.show();
    }
    
    public Stage getStage()  {
    	return this.primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
