package ch.makery.address.errors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;


public class dconController {

	@FXML
	private Button okButton;
	
	@FXML
	private void okButton(){
		Window stage= okButton.getScene().getWindow();
		stage.hide();
	}
}
