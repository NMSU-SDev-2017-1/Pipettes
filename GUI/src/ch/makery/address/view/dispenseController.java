package ch.makery.address.view;

import java.io.IOException;

import ch.makery.address.MainApp;
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

	private static String amount;
	private static String con;
	private static String units;
	private MainApp main;
	private static String[] procedure;
	public static int leftover;

	ObservableList<String> unitList = FXCollections.observableArrayList("--Units--","mL",
			"µl");

	ObservableList<String> conList = FXCollections.observableArrayList(
			"--Containers--","Container1", "Container2");

	@FXML
	private ComboBox<String> unitBox;
	@FXML
	private ComboBox<String> conBox;
	@FXML
	private TextField unitAmount;
	@FXML
	private Button okButton;
	@FXML
	private Button canButton;

	public static boolean err;
	@FXML
	private void okButton() throws Exception {
		Window stage = okButton.getScene().getWindow();
		if ((conBox.getValue()).equals("--Containers--")) {
			main.showdConError();
			System.out.println("Invalid entries");
		} else if ((unitBox.getValue()).equals("--Units--")) {
			main.showdUnitError();
			System.out.println("Invalid entries");
		} else if (((unitAmount.getText()).isEmpty()) == true) {// ==true){
			main.showTextError();
			System.out.println("Invalid entries");
		} else {
			String a = unitAmount.getText();
			setAmount(a);
			compute(a);
			if(err==true){
				main.showInvError();
			}
			else{
			stage.hide();
			}
		}
	}

	@FXML
	private String getContainer() throws Exception {
		con = conBox.getSelectionModel().getSelectedItem();
		return con;
	}

	@FXML
	private String getUnits() throws Exception {
		units = unitBox.getSelectionModel().getSelectedItem();
		return units;
	}

	public static String[] dispenseProcedure() throws Exception {
		if (isInputValid()) {
			procedure = new String[] { "Dispense " + amount + " " + units
					+ " into " + con };
		}
		return procedure;
	}

	public static boolean isInputValid() throws Exception {
		if (amount == null || units == null || con == null)
			return false;
		else
			return true;
	}

	public void compute(String a) {
		int dispAmount = Integer.parseInt(a);
		int drawn = Integer.parseInt(drawController.getAmount());
		if(leftover<dispAmount){
			err=true;
		}
		else{
			int left = leftover - dispAmount;
			setLeft(left);
		}
	}
	public static void setLeft(int a){
		leftover = a;
	}
	public void setAmount(String a) {
		amount = a;
	}
	public static int getLeft(){
		return leftover;
	}
	public static String getAmount() {
		return amount;
	}

	@FXML
	private void canButton() {
		Window stage = canButton.getScene().getWindow();
		stage.hide();
	}

	@FXML
	private void textHandle(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			System.out.println(unitAmount.getText());
		}
	}

	@FXML
	private void initialize() {

		unitBox.setValue("--Units--");
		unitBox.setItems(unitList);

		conBox.setValue("--Containers--");
		conBox.setItems(conList);
	}

	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;
	}
}
