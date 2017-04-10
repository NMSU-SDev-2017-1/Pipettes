package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import ch.makery.address.MainApp;

public class insideExistingProjectController {

	private MainApp main;
	private boolean okClicked = false;
	private Stage dialogStage;
	
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
