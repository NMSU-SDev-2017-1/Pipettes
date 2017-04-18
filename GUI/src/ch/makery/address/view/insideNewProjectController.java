package ch.makery.address.view;

import java.io.File;
import ch.makery.address.MainApp;
import ch.makery.address.view.exampleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class insideNewProjectController {

	private MainApp main;
	private exampleController example;
	private boolean okClicked = false;
	private Stage dialogStage;

	ObservableList<String> deviceList = FXCollections.observableArrayList(
			"device1", "device2", "device3");

	@FXML
	private TextField projectName;
	@FXML
	private TextField projectLocation = new TextField();
	@FXML
	private ComboBox <String> deviceBox;
	@FXML

	private String name1, name2, output;
	
	private int[] workArea;

	@FXML
	private void initialize() {
		deviceBox.setValue("--------");
		deviceBox.setItems(deviceList);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private String handleDevice() throws Exception  {
		output = deviceBox.getSelectionModel().getSelectedItem();
		return output;
	}

	@FXML
	private String handleOk() throws Exception {
		name1 = projectName.getText();
		if (isInputValid()) {
			okClicked = true;
			dialogStage.close();
			main.showSecondView(name1);
			//example.drawWorkArea(workArea);
		}
		return name1;
	}

	private boolean isInputValid() throws Exception {
		String errorMessage = "";
		if (projectName.getText() == null
				|| projectName.getText().length() == 0
				|| projectLocation.getText() == null
				|| projectLocation.getText().length() == 0
				|| output == null) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	private void handleCancel() throws Exception {
		dialogStage.close();
	}

	@FXML
	public void handleBrowse() throws Exception {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Browse");
		File file = chooser.showDialog(dialogStage);
		if (file != null) {
			name2 = file.getPath();
			projectLocation.setText(name2);
		}
	}
	
	private int[] sendDevice() throws Exception {
		workArea = new int[] {300, 350};
		return workArea;
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;

	}

}
