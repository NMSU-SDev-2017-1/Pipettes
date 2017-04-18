package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class exampleController {
	
	private String output;

	ObservableList<String> containersList = FXCollections.observableArrayList(
			"\t     container1", "\t     container2", "\t     container3",
			"\t     container4", "\t     container5", "\t     container6",
			"\t     container7", "\t     container8", "\t     container9",
			"\t     container10");

	@FXML
	private ComboBox<String> containerBox;


	@FXML
	private void initialize() {
		containerBox.setValue("\t     Containers");
		containerBox.setItems(containersList);
	}
	
	@FXML
	private void handleContainer() throws Exception  {
		output = containerBox.getSelectionModel().getSelectedItem();
		System.out.println(output);
		if(output == "\t     container1") {
			Circle c1 = new Circle();
			c1.setCenterX(50.0);
			c1.setCenterY(125.0);
			c1.setRadius(30.0);
			c1.setFill(Color.BLACK);
			//c1.setStrokeWidth(3);
			System.out.println("hi");
		}
	}

}
