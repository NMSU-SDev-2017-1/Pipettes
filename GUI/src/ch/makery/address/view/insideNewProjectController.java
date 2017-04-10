package ch.makery.address.view;

import java.awt.Desktop;
import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class insideNewProjectController {

	private boolean okClicked = false;
	private Stage dialogStage;
	final FileChooser fileChooser = new FileChooser();
	private Desktop desktop = Desktop.getDesktop();

	ObservableList<String> deviceList = FXCollections.observableArrayList(
			"-------", "device1", "device2", "device3");

	@FXML
	private TextField projectName;
	@FXML
	private TextField projectLocation;
	@FXML
	private ComboBox deviceBox;

	@FXML
	private void initialize() {
		deviceBox.setValue("-------");
		deviceBox.setItems(deviceList);
	}

	public void handleBrowse() throws Exception {
		File file = fileChooser.showOpenDialog(dialogStage);
		if (file != null) {
			openFile(file);
		}

	}

	private void openFile(File file) throws Exception {
		desktop.open(file);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setProject() {

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() throws Exception {
		okClicked = true;
		dialogStage.close();
	}

	@FXML
	private void handleCancel() throws Exception {
		dialogStage.close();
	}

}
