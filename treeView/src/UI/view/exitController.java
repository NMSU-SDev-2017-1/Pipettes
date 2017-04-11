package UI.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class exitController {

	@FXML
	private Button yesButton;
	@FXML
	private Button noButton;
	
	@FXML
	private void noButton(){
		Window stage= noButton.getScene().getWindow();
		stage.hide();
	}
	@FXML 
	private void yesButton(){
		Platform.exit();
		System.exit(0);
	}
}
