package ch.makery.address.view;

import ch.makery.address.view.insideNewProjectController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

public class exampleController {

	private String output;

	ObservableList<String> containersList = FXCollections.observableArrayList(
			"\t container1", "\t container2", "\t container3", "\t container4",
			"\t container5", "\t container6", "\t container7", "\t container8",
			"\t container9", "\t container10");

	@FXML
	private ComboBox<String> containerBox;
	@FXML
	private Canvas myCanvas;
	
	//int device[] = insideNewProjectController.sendDevice();

	@FXML
	public void initialize() throws Exception {
		int[] deviceUse = insideNewProjectController.sendDevice();
		containerBox.setValue("\t   Containers");
		containerBox.setItems(containersList);
		handleWorkArea(deviceUse);
	}

	@FXML
	private void handleContainer() throws Exception {
		output = containerBox.getSelectionModel().getSelectedItem();
		if (output == "\t container1") {
		}
	}
	
	public void handleWorkArea(int[] deviceUse) throws Exception {
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		drawWorkArea(gc, deviceUse);
		return;
	}

	public void drawWorkArea(GraphicsContext gc, int[] deviceUse) {
		gc.setLineWidth(4);
		gc.strokeRect(0, 100, deviceUse[0], deviceUse[1]);
	}
}
