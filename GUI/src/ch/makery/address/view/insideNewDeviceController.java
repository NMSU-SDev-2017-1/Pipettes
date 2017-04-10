package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ch.makery.address.MainApp;

public class insideNewDeviceController {
	
	private MainApp main;

	ObservableList<String> deviceList = FXCollections
			.observableArrayList("---------", "Circular", "Rectangular");

	@FXML
	private ComboBox deviceBox;
	
	@FXML
	private void initialize()  {
		deviceBox.setValue("---------");
		deviceBox.setItems(deviceList);
	}
}
