package UI.view;

import java.io.IOException;

import UI.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class dispenseController {

	ObservableList<String> unitList = FXCollections.observableArrayList(
			"--Units--","mL", "µl");
	
	ObservableList<String> conList = FXCollections.observableArrayList(
			"--Containers--","Container1", "Container2");
	
	@FXML 
    private ComboBox unitBox;
	@FXML
	private ComboBox conBox;
	@FXML
	private TextField unitText;
	@FXML
	private Button okButton;
	@FXML
	private Button canButton;
	
	public String amount;
	public int leftover;
	@FXML
	private void okButton() throws IOException{
		Window stage= okButton.getScene().getWindow();
		if((conBox.getValue()).equals("--Containers--")){
			Main.showdConError();
			System.out.println("Invalid entries");
		}
		else if((unitBox.getValue()).equals("--Units--")){
			Main.showdUnitError();
			System.out.println("Invalid entries");
		}
		else if((unitText.getText()).isEmpty()){//==true){
			Main.showTextError();
			System.out.println("Invalid entries");
		}
		else{
		System.out.println("Dispense to: "+conBox.getValue());
		System.out.print(unitText.getText());
		System.out.println("  "+unitBox.getValue());
		amount = unitText.getText();
		setAmount(amount);
		compute(amount);
		
		System.out.println("Leftover amount: " +leftover+" "+unitBox.getValue());
		stage.hide();
		}
	}
	public int compute(String a){
		drawController dC = new drawController();
		int dispAmount = Integer.parseInt(a);
		int drawn = Integer.parseInt(dC.getAmount());
		leftover = drawn-dispAmount;
		return leftover;
	}
	public void setAmount(String a){
		amount = a;
	}
	
	public String getAmount(){
		return amount;
	}
	@FXML
	private void canButton(){
		Window stage= canButton.getScene().getWindow();
		stage.hide();
	}
	@FXML 
	private void textHandle(KeyEvent e){
		if(e.getCode()==KeyCode.ENTER){
		System.out.println(unitText.getText());
		}
	}
	@FXML
	private void initialize(){

		unitBox.setValue("--Units--");
		unitBox.setItems(unitList);
		
		conBox.setValue("--Containers--");
		conBox.setItems(conList);
	}
}
