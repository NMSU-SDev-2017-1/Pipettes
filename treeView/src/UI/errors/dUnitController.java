package UI.errors;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class dUnitController {
	@FXML
	private Button okButton;
	
	@FXML
	private void okButton(){
		Window stage= okButton.getScene().getWindow();
		stage.hide();
	}
}