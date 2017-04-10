package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ch.makery.address.MainApp;

public class insideNewDeviceController {
	
	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;

	ObservableList<String> deviceList = FXCollections
			.observableArrayList("---------", "Circular", "Rectangular");

	@FXML
	private ComboBox deviceBox;
	
	@FXML
	private void initialize()  {
		deviceBox.setValue("---------");
		deviceBox.setItems(deviceList);
	}
	
	public void setDialogStage(Stage dialogStage)  {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked()  {
		return okClicked;
	}
	
	@FXML
	private void handleOk() throws Exception {
		okClicked = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleCancel() throws Exception  {
		dialogStage.close();
	}
	
}
