package UI;

import java.io.IOException;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
public class Main extends Application{
	private static Stage primaryStage;
	private static BorderPane mainLayout;
	
	@Override
	public void start(Stage primaryStage)throws Exception{
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
	
	public static void main(String[] args){
		launch(args);
	}
	
}
