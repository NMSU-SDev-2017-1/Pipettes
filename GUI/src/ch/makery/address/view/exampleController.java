package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class exampleController {

	private String output;
	private MainApp main;
	private Stage stage;


	//ObservableList<String> procedures = FXCollections.observableArrayList(
		//	"hello", "hi");
	// public static final ObservableList data = FXCollections
	// .observableArrayList();

	ObservableList<String> containersList = FXCollections.observableArrayList(
			"\t container1", "\t container2", "\t container3", "\t container4",
			"\t container5", "\t container6", "\t container7", "\t container8",
			"\t container9", "\t container10");

	@FXML
	private ComboBox<String> containerBox;
	@FXML
	private Canvas myCanvas;
	//@FXML
	//private ListView<String> listView;

	@FXML
	private void initialize() {
		containerBox.setValue("\t   Containers");
		containerBox.setItems(containersList);
	}

	/*private void listProcedures() throws Exception {
		// final ListView listView = new ListView(procedures);
		// data.addAll("hi", "hello");
		listView.setItems(procedures);
		procedures.add("hello");
	}*/

	/*public void start(Stage primaryStage) {
		primaryStage.setTitle("Drawing Operations Test");
		Group root = new Group();
		Canvas canvas = new Canvas(300, 250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawWorkArea(gc);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}*/

	@FXML
	private void handleContainer() throws Exception {
		output = containerBox.getSelectionModel().getSelectedItem();
		System.out.println(output);
		if (output == "\t container1") {
			System.out.println("hi");
			// listProcedures();
			handleWorkArea();
			Rectangle c1 = new Rectangle(0, 0, 50, 50);
			/*
			 * c1.setCenterX(200.0); 
			 * c1.setCenterY(200.0); 
			 * c1.setRadius(30.0);
			 */
			c1.setFill(Color.BLACK);
			c1.setStrokeWidth(3);
			// root.getChildren().add(c1);
		}
	}
	
	private void handleWorkArea() throws Exception {
		Group root = new Group();
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		drawWorkArea(gc);
		stage = main.getStage();
		root.getChildren().add(myCanvas);
		stage.setScene(new Scene(root));
		stage.show();
	}

	public void drawWorkArea(GraphicsContext gc) {
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
		gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;

	}

}
