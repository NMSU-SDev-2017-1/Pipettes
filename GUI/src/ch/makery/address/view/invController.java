package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

public class invController {
	@FXML
	private Button okButton;
	@FXML
	private Label inv;
	
	@FXML
	private void okButton(){
		Window stage= okButton.getScene().getWindow();
		stage.hide();
	}
	@FXML
	private void initialize(){
		this.inv.setText("Cannot dispense "+dispenseController.getAmount()+ " ml from "+dispenseController.getLeft()+" ml that is leftover");
	}
}
