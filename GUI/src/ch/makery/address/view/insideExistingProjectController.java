package ch.makery.address.view;

import java.awt.Desktop;
import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ch.makery.address.MainApp;

public class insideExistingProjectController {

	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;
	final FileChooser fileChooser = new FileChooser();
	private Desktop desktop = Desktop.getDesktop();
	
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
		//main.showSecondView();
	}
	
	@FXML
	private void handleCancel() throws Exception  {
		dialogStage.close();
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
}
