package UI.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class dispenseController {

	ObservableList<String> unitList = FXCollections.observableArrayList(
			"Units","mL", "µl");
	
	@FXML 
    private ComboBox unitBox;
	@FXML
	private TextField unitText;
	@FXML
	private Button okButton;
	@FXML
	private Button canButton;
	
	@FXML
	private void okButton(){
		Window stage= okButton.getScene().getWindow();
		stage.hide();
	}
	@FXML
	private void canButton(){
		Window stage= canButton.getScene().getWindow();
		stage.hide();
	}
	@FXML
	private void initialize(){

		unitBox.setValue("Units");
		unitBox.setItems(unitList);
	}
}
