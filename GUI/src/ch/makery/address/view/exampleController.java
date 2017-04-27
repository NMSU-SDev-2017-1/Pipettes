package ch.makery.address.view;

import java.io.IOException;


//import java.util.Arrays;
import ch.makery.address.MainApp;
import ch.makery.address.view.insideNewProjectController;
import ch.makery.address.view.drawController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

//import javafx.scene.input.MouseEvent;

public class exampleController {

	private MainApp main;
	private String output;
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;

	ObservableList<String> containersList = FXCollections.observableArrayList(
			"50mL\tCentrifugeTube", "9mL\t\tTestTube", "150mL\tBeaker",
			"50mL\tFlask", "\t container5", "\t container6", "\t container7",
			"\t container8", "\t container9", "\t container10");

	ObservableList<String> items = FXCollections.observableArrayList();
	ObservableList<String> items1 = FXCollections.observableArrayList();


	@FXML
	private ComboBox<String> containerBox;
	@FXML
	private Button draw;
	@FXML
	private Button dispense;
	@FXML
	private Canvas myCanvas;
	@FXML
	ListView<String> list = new ListView<>();

	// (Capacity(mL), Diameter(mm), Height(mm))
	private static double[] centrifugeTube = { 50.0, 33.0, 115.0 };
	private static double[] testTube = { 9.0, 13.0, 100.0 };
	private static double[] beaker = { 150.0, 60.0, 85.0 };
	private static double[] flask = { 50.0, 101.0, 176.0 };

	private void convert() throws Exception {
		centrifugeTube[1] = (double) (centrifugeTube[1] * 1.5);
		testTube[1] = (double) (testTube[1] * 1.5);
		beaker[1] = (double) (beaker[1] * 1.5);
		flask[1] = (double) (flask[1] * 1.5);

		return;
	}

	@FXML
	public void initialize() throws Exception {
		convert();
		int[] deviceUse = insideNewProjectController.sendDevice();
		containerBox.setValue("\t     Containers");
		containerBox.setItems(containersList);
		handleWorkArea(deviceUse);
	}

	@FXML
	private void drawButton() throws Exception {
		MainApp.showDraw();
		if(drawController.isInputValid() == true)  {
			String[] drawProcedure = drawController.drawProcedure();
			items.addAll(drawProcedure);
			list.setItems(items);
		}
		//list.getItems().clear();
	}

	@FXML
	private void dispenseButton() throws Exception {
		//drawController dC = new drawController();
		MainApp.showDispense();
		if(dispenseController.isInputValid() == true)  {
			String[] dispenseProcedure = drawController.drawProcedure();
			items1.addAll(dispenseProcedure);
			list.setItems(items1);
		}
	}

	@FXML
	private void handleContainer() throws Exception {
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		output = containerBox.getSelectionModel().getSelectedItem();
		if (!(output == null)) {
			drawContainers(gc, output);
			// myCanvas.setOnMousePressed(canvasOnMousePressedEventHandler);
			// myCanvas.setOnMouseDragged(canvasOnMouseDraggedEventHandler);
		}
		return;
	}

	/*
	 * EventHandler<MouseEvent> canvasOnMousePressedEventHandler = new
	 * EventHandler<MouseEvent>() {
	 * 
	 * @Override public void handle(MouseEvent mouseEvent) { orgSceneX =
	 * mouseEvent.getSceneX(); orgSceneY = mouseEvent.getSceneY(); orgTranslateX
	 * = ((Canvas) (mouseEvent.getSource())).getTranslateX(); orgTranslateY =
	 * ((Canvas) (mouseEvent.getSource())).getTranslateY(); } };
	 * 
	 * EventHandler<MouseEvent> canvasOnMouseDraggedEventHandler = new
	 * EventHandler<MouseEvent>() {
	 * 
	 * @Override public void handle(MouseEvent mouseEvent) { double offsetX =
	 * mouseEvent.getSceneX() - orgSceneX; double offsetY =
	 * mouseEvent.getSceneY() - orgSceneY; double newTranslateX = orgTranslateX
	 * + offsetX; double newTranslateY = orgTranslateY + offsetY;
	 * 
	 * ((Canvas) (mouseEvent.getSource())).setTranslateX(newTranslateX);
	 * ((Canvas) (mouseEvent.getSource())).setTranslateY(newTranslateY); } };
	 */

	public void handleWorkArea(int[] deviceUse) throws Exception {
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		drawWorkArea(gc, deviceUse);
		return;
	}

	public void drawWorkArea(GraphicsContext gc, int[] deviceUse) {
		gc.setLineWidth(4);
		gc.strokeRect(0, 100, deviceUse[1], deviceUse[2]);
	}

	public void drawContainers(GraphicsContext gc, String output)
			throws Exception {
		if (output == containersList.get(0)) {
			gc.strokeOval(0, 100, centrifugeTube[1] / 2, centrifugeTube[1] / 2);
			gc.fillOval(0, 100, centrifugeTube[1] / 2, centrifugeTube[1] / 2);
		}
		if (output == containersList.get(1)) {
			gc.strokeOval(30, 100, testTube[1] / 2, testTube[1] / 2);
			gc.fillOval(30, 100, testTube[1] / 2, testTube[1] / 2);
		}
		if (output == containersList.get(2)) {
			gc.strokeOval(40, 100, beaker[1] / 2, beaker[1] / 2);
			gc.fillOval(40, 100, beaker[1] / 2, beaker[1] / 2);
		}
		if (output == containersList.get(3)) {
			gc.strokeOval(100, 100, flask[1] / 2, flask[1] / 2);
			gc.fillOval(100, 100, flask[1] / 2, flask[1] / 2);
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;
	}
}
