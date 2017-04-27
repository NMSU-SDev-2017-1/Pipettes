package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class insideNewDeviceController {
	
	private boolean okClicked = false;
	private Stage dialogStage;
	private String lengthDim;
	private String widthDim;

	ObservableList<String> deviceList = FXCollections
			.observableArrayList("Circular", "Rectangular");

	@FXML
	private ComboBox<String> deviceBox;
	@FXML
	private TextField lengthArea;
	@FXML
	private TextField widthArea;
	@FXML
	private TextField heightArea;
	
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
		lengthDim = lengthArea.getText();
		widthDim = widthArea.getText();
		System.out.println(lengthDim);
		System.out.println(widthDim);
		okClicked = true;
		dialogStage.close();
	}
	
	@FXML
	private void handleCancel() throws Exception  {
		dialogStage.close();
	}
	
}
