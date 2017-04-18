package ch.makery.address.view;

import java.awt.Desktop;
import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import ch.makery.address.MainApp;

public class insideExistingProjectController {

	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;
	private Desktop desktop = Desktop.getDesktop();
	
	@FXML
	private TextField existingProject = new TextField();
	
	private String name2;
	
	public void setDialogStage(Stage dialogStage)  {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked()  {
		return okClicked;
	}
	
	@FXML
	private void handleOk() throws Exception {
		if(isInputValid())  {
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel() throws Exception  {
		dialogStage.close();
	}
	
	public void handleBrowse() throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Browse");
		File file = fileChooser.showOpenDialog(dialogStage);
		if (file != null) {
			name2 = file.getPath();
			existingProject.setText(name2);
			openFile(file);
		}
	}
	
	private boolean isInputValid() throws Exception {
		String errorMessage = "";
		if (existingProject.getText().length() == 0
				|| existingProject.getText() == null) {
			return false;
		} else {
			return true;
		}
	}

	private void openFile(File file) throws Exception {
		desktop.open(file);
	}
}
