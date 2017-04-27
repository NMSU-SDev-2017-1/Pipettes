package ch.makery.address.view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class disErrorController {
	@FXML
	private Button okButton;
	
	@FXML
	private void okButton(){
		Window stage= okButton.getScene().getWindow();
		stage.hide();
	}
}
