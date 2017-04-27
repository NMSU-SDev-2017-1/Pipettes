package ch.makery.address;

import java.io.IOException;

import ch.makery.address.view.insideExistingProjectController;
import ch.makery.address.view.insideNewDeviceController;
import ch.makery.address.view.insideNewProjectController;
import ch.makery.address.view.mainItemsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    	
    	primaryStage.close();
    	Stage addDialogStage = new Stage();
    	addDialogStage.setTitle(name1);
    	addDialogStage.initModality(Modality.WINDOW_MODAL);
    	addDialogStage.initOwner(primaryStage);
    	Scene scene = new Scene(createProject);
    	addDialogStage.setScene(scene);
    	addDialogStage.show();
    }
    
    public static void showDraw() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Draw.fxml"));
		BorderPane drawBut = loader.load();
		
		Stage drawStage = new Stage();
		drawStage.setTitle("Draw");
		drawStage.initModality(Modality.WINDOW_MODAL);
	    drawStage.initOwner(primaryStage);
		Scene drawScene = new Scene(drawBut);
		drawStage.setScene(drawScene);
		drawStage.showAndWait();
	}
    
    public static void showDispense() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Dispense.fxml"));
		BorderPane dispense = loader.load();
		
		Stage drawStage = new Stage();
		drawStage.setTitle("Dispense");
		drawStage.initModality(Modality.WINDOW_MODAL);
	    drawStage.initOwner(primaryStage);
		Scene drawScene = new Scene(dispense);
		drawStage.setScene(drawScene);
		drawStage.showAndWait();
	}
    
    public static void showdConError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/dCon.fxml"));
		BorderPane dCon = loader.load();
		
		Stage dConStage = new Stage();
		dConStage.setTitle("Error");
		dConStage.initModality(Modality.WINDOW_MODAL);
	    dConStage.initOwner(primaryStage);
		Scene dConScene = new Scene(dCon);
		dConStage.setScene(dConScene);
		dConStage.showAndWait();
	}
	public static void showdUnitError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/dUnit.fxml"));
		BorderPane dUnit = loader.load();
		
		Stage dUnitStage = new Stage();
		dUnitStage.setTitle("Error");
		dUnitStage.initModality(Modality.WINDOW_MODAL);
	    dUnitStage.initOwner(primaryStage);
		Scene dUnitScene = new Scene(dUnit);
		dUnitStage.setScene(dUnitScene);
		dUnitStage.showAndWait();
	}
	public static void showdisConError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/disCon.fxml"));
		BorderPane disCon = loader.load();
		
		Stage disConStage = new Stage();
		disConStage.setTitle("Error");
		disConStage.initModality(Modality.WINDOW_MODAL);
	    disConStage.initOwner(primaryStage);
		Scene disConScene = new Scene(disCon);
		disConStage.setScene(disConScene);
		disConStage.showAndWait();
	}
	public static void showdisUnitError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/disUnit.fxml"));
		BorderPane disUnit = loader.load();
		
		Stage disUnitStage = new Stage();
		disUnitStage.setTitle("Error");
		disUnitStage.initModality(Modality.WINDOW_MODAL);
	    disUnitStage.initOwner(primaryStage);
		Scene dUnitScene = new Scene(disUnit);
		disUnitStage.setScene(dUnitScene);
		disUnitStage.showAndWait();
	}
	public static void showTextError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/text.fxml"));
		BorderPane text = loader.load();
		
		Stage textStage = new Stage();
		textStage.setTitle("Error");
		textStage.initModality(Modality.WINDOW_MODAL);
		textStage.initOwner(primaryStage);
		Scene textScene = new Scene(text);
		textStage.setScene(textScene);
		textStage.showAndWait();
	}
	public static void showDisError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("errors/disError.fxml"));
		BorderPane disError = loader.load();
		
		Stage disErrorStage = new Stage();
		disErrorStage.setTitle("Error");
		disErrorStage.initModality(Modality.WINDOW_MODAL);
		disErrorStage.initOwner(primaryStage);
		Scene disErrorScene = new Scene(disError);
		disErrorStage.setScene(disErrorScene);
		disErrorStage.showAndWait();
	}
	public static void showInvError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/invError.fxml"));
		BorderPane invError = loader.load();
		
		Stage invErrorStage = new Stage();
		invErrorStage.setTitle("Error");
		invErrorStage.initModality(Modality.WINDOW_MODAL);
		invErrorStage.initOwner(primaryStage);
		Scene invErrorScene = new Scene(invError);
		invErrorStage.setScene(invErrorScene);
		invErrorStage.showAndWait();
	}
    
    public Stage getStage()  {
    	return this.primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
