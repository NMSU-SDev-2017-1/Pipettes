package ch.makery.address.view;

import java.io.File;
import java.util.Arrays;

import ch.makery.address.view.exampleController;
import ch.makery.address.MainApp;
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

	private String name1, name2;
	
	private static String output;
	
	private static int[] device1 = {300, 200};
	private static int[] device2 = {400, 300};
	private static int[] device3 = {500, 400};

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
		System.out.println(output);
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
	
	public static int[] sendDevice() throws Exception {
		if(output == "device1")  {
			System.out.println("helo");
			return (device1);
		}
		else if(output == "device2")  {
			return (device2);

		}
		else if(output == "device3")  {
			return (device3);
		}
		return null;
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;

	}
}
