package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class insideNewProjectController {
	
	//private MainApp main;

	ObservableList<String> deviceList = FXCollections
			.observableArrayList("-------", "device1", "device2", "device3");

	@FXML
	private TextField projectField;
	@FXML
	private TextField locationField;
	@FXML
	private ComboBox deviceBox;
	
	@FXML
	private void initialize()  {
		deviceBox.setValue("-------");
		deviceBox.setItems(deviceList);
	}
	
	/*@FXML
	private void handleOk() throws Exception {
		main.showPersonOverview();
	}*/

}
