package UI;

import java.io.IOException;

import UI.view.drawController;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;

public class Main extends Application{
	private static Stage primaryStage;
	private static BorderPane mainLayout;
	
	public static boolean drawopen;
	@Override
	public void start(Stage primaryStage)throws Exception{
		drawopen=false;
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Project Name");
		showMainView();
		
	}
    
	public void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/Main.fxml"));
		mainLayout = loader.load();
		
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event){
				try {
					showExit();
				} catch (IOException e) {
					e.printStackTrace();
				}
				event.consume();
			}
		});
		
	}
	//////////////////////////////////////////////////////////////////////////
	//
	//	Exit check stage
	//
	//////////////////////////////////////////////////////////////////////////
	
	public static void showExit() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/exit.fxml"));
		BorderPane exit = loader.load();
	
		Stage exitStage = new Stage();
		exitStage.setTitle("Project Name");
		exitStage.initModality(Modality.WINDOW_MODAL);
		exitStage.initOwner(primaryStage);
		Scene exitScene = new Scene(exit);
		exitStage.setScene(exitScene);
		exitStage.showAndWait();
	} 
	//////////////////////////////////////////////////////////////////////////
	//
	//	Draw and Dispense
	//
	//////////////////////////////////////////////////////////////////////////
	
	public static void showDraw() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/Draw.fxml"));
		BorderPane drawBut = loader.load();
		
		Stage drawStage = new Stage();
		drawStage.setTitle("Project Name");
		drawStage.initModality(Modality.WINDOW_MODAL);
	    drawStage.initOwner(primaryStage);
		Scene drawScene = new Scene(drawBut);
		drawStage.setScene(drawScene);
		drawStage.showAndWait();
	}
	
	public static void showDispense() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/Dispense.fxml"));
		BorderPane dispense = loader.load();
		
		Stage drawStage = new Stage();
		drawStage.setTitle("Project Name");
		drawStage.initModality(Modality.WINDOW_MODAL);
	    drawStage.initOwner(primaryStage);
		Scene drawScene = new Scene(dispense);
		drawStage.setScene(drawScene);
		drawStage.showAndWait();
	}
	
	//////////////////////////////////////////////////////////////////////////
	//
	//	Error stages
	//
	//////////////////////////////////////////////////////////////////////////
	
	public static void showdConError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("errors/dCon.fxml"));
		BorderPane dCon = loader.load();
		
		Stage dConStage = new Stage();
		dConStage.setTitle("Project Name");
		dConStage.initModality(Modality.WINDOW_MODAL);
	    dConStage.initOwner(primaryStage);
		Scene dConScene = new Scene(dCon);
		dConStage.setScene(dConScene);
		dConStage.showAndWait();
	}
	public static void showdUnitError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("errors/dUnit.fxml"));
		BorderPane dUnit = loader.load();
		
		Stage dUnitStage = new Stage();
		dUnitStage.setTitle("Project Name");
		dUnitStage.initModality(Modality.WINDOW_MODAL);
	    dUnitStage.initOwner(primaryStage);
		Scene dUnitScene = new Scene(dUnit);
		dUnitStage.setScene(dUnitScene);
		dUnitStage.showAndWait();
	}
	public static void showdisConError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("errors/disCon.fxml"));
		BorderPane disCon = loader.load();
		
		Stage disConStage = new Stage();
		disConStage.setTitle("Project Name");
		disConStage.initModality(Modality.WINDOW_MODAL);
	    disConStage.initOwner(primaryStage);
		Scene disConScene = new Scene(disCon);
		disConStage.setScene(disConScene);
		disConStage.showAndWait();
	}
	public static void showdisUnitError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("errors/disUnit.fxml"));
		BorderPane disUnit = loader.load();
		
		Stage disUnitStage = new Stage();
		disUnitStage.setTitle("Project Name");
		disUnitStage.initModality(Modality.WINDOW_MODAL);
	    disUnitStage.initOwner(primaryStage);
		Scene dUnitScene = new Scene(disUnit);
		disUnitStage.setScene(dUnitScene);
		disUnitStage.showAndWait();
	}
	public static void showTextError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("errors/text.fxml"));
		BorderPane text = loader.load();
		
		Stage textStage = new Stage();
		textStage.setTitle("Project Name");
		textStage.initModality(Modality.WINDOW_MODAL);
		textStage.initOwner(primaryStage);
		Scene textScene = new Scene(text);
		textStage.setScene(textScene);
		textStage.showAndWait();
	}
	public static void showDisError() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/disError.fxml"));
		BorderPane disError = loader.load();
		
		Stage disErrorStage = new Stage();
		disErrorStage.setTitle("Project Name");
		disErrorStage.initModality(Modality.WINDOW_MODAL);
		disErrorStage.initOwner(primaryStage);
		Scene disErrorScene = new Scene(disError);
		disErrorStage.setScene(disErrorScene);
		disErrorStage.showAndWait();
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
}
