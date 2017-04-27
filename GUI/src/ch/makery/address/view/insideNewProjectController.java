package ch.makery.address.view;

import java.io.File;

import ch.makery.address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class insideNewProjectController {

	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;

	ObservableList<String> deviceList = FXCollections.observableArrayList(
			"LulzBot Mini Mini 3D Printer", "MakerBot Replicator+",
			"MakerBot Replicator Desktop 3D Printer");

	@FXML
	private TextField projectName;
	@FXML
	private TextField projectLocation = new TextField();
	@FXML
	private ComboBox<String> deviceBox;
	@FXML
	private Label error;

	private String name1, name2;

	private static String output;

	private static int[] device1 = { 228, 228, 237 }; // (HWD)
	private static int[] device2 = { 248, 443, 290 }; // (HWD)
	private static int[] device3 = { 225, 297, 378 }; // (HWD)

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
	private String handleDevice() throws Exception {
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
			sendDevice();
		}
		return name1;
	}

	private boolean isInputValid() throws Exception {
		if (projectName.getText() == null
				|| projectName.getText().length() == 0
				|| projectLocation.getText() == null
				|| projectLocation.getText().length() == 0 || output == null) {
			error.setText("One of the Required Fields is Empty!");
			error.setTextFill(Color.RED);
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

	public static int[] sendDevice() throws Exception {
		if (output == "LulzBot Mini Mini 3D Printer") {
			return (device1);
		} else if (output == "MakerBot Replicator+") {
			return (device2);

		} else if (output == "MakerBot Replicator Desktop 3D Printer") {
			return (device3);
		}
		return null;
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;
	}
}
