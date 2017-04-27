package UI.view;

import java.io.IOException;

import UI.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class drawController {

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
	
	//For setter and getters
	public String amount;
	public String con;
	public String units;
	
	//String for listview
	public String finalString;
	//To see if draw has been set so dispense can be opened
	public boolean dopen;
	
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
		else if(((unitText.getText()).isEmpty())==true){
			Main.showTextError();
			System.out.println("Invalid entries");
		}
		else{
			MainController mC = new MainController();
			String container = (String) conBox.getValue();
			setCon(container);
			String am = unitText.getText();
			setAmount(am);
			String units = (String) unitBox.getValue();
			setUnits(units);
			dopen=true;
			drawProcedure();
			System.out.println(getFinal());
		    //mC.list();
			stage.hide();
		}
	}
	public void drawProcedure(){
		String con = getCon();
		String amount = getAmount();
		String units = getUnits();
		String drawFrom = "Draw from: ";
		String line = "\n";
		String space = " ";
		String a = drawFrom + con + line + amount + space + units;
		setFinal(a);
	}
	@FXML
	private void canButton(){
		Window stage= canButton.getScene().getWindow();
		stage.hide();
	}
	public void setFinal(String a){
		finalString = a; 
	}
	public void setAmount(String a){
		amount = a;
	}
	public void setCon(String a){
		con = a;
	}
	public void setUnits(String a){
		units = a;
	}
	public String getFinal(){
		return finalString;
	}
	public String getAmount(){
		return amount;
	}
	public String getCon(){
		return con;
	}
	public String getUnits(){
		return units;
	}
	@FXML
	private void initialize(){

		unitBox.setValue("--Units--");
		unitBox.setItems(unitList);
		
		conBox.setValue("--Containers--");
		conBox.setItems(conList);
	}
}
