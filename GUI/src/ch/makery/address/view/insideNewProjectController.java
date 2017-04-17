package ch.makery.address.view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;

import ch.makery.address.MainApp;
import ch.makery.address.model.newProject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class insideNewProjectController {

	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;
	final FileChooser fileChooser = new FileChooser();
	private Desktop desktop = Desktop.getDesktop();
	private newProject name;

	ObservableList<String> deviceList = FXCollections.observableArrayList(
			"-------", "device1", "device2", "device3");

	@FXML
	private TextField projectName;
	@FXML
	private TextField projectLocation;
	@FXML
	private ComboBox deviceBox;
	@FXML
	private Label label = new Label();
	
	private String name1, name2;
	
	@FXML
	private void initialize() {
		deviceBox.setValue("-------");
		deviceBox.setItems(deviceList);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/*
	 * public void setProject() { this.name = name;
	 * projectName.setText(name.getProjectName()); }
	 */

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private String handleOk() throws Exception {
		name1 = projectName.getText();
		if (isInputValid()) {
			okClicked = true;
			dialogStage.close();
			main.showSecondView(name1);
		}
		return name1;
	}

	private boolean isInputValid() throws Exception{
		String errorMessage = "";
		if (projectName.getText() == null
				|| projectName.getText().length() == 0) {
			errorMessage += "No valid project name!\n";
			return false;
		}
		else  {
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
		//File defaultDirectory = new File("C:/");
		//chooser.setInitialDirectory(defaultDirectory);
		//File selectDirectory = chooser.showDialog(dialogStage);
		//if (file != null)  {
		name2 = file.getPath();
		label.setText(name2);
		System.out.println(name2);
		//}
		/*name2 = projectName.getText();
		if(isInputValid())  {
			System.out.println(name2);
			File file = fileChooser.showSaveDialog(dialogStage);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);
			if (file != null) {
				saveFile(name2, file);
			}
		}*/
	}

	private void saveFile(String name2, File file) throws Exception {
		FileWriter fileWriter = null;
            
        fileWriter = new FileWriter(file);
        fileWriter.write(name2);
        fileWriter.close();
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;

	}

}
