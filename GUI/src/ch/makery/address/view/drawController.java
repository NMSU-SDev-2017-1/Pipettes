package ch.makery.address.view;

import java.io.IOException;
import java.util.Arrays;

import ch.makery.address.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class drawController {

	private static String amount;
	private static String con;
	private static String units;
	private MainApp main;
	private static String[] procedure;

	ObservableList<String> unitList = FXCollections.observableArrayList("--Units--","mL",
			"micro");

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

	// String for listview
	public static String finalString;
	// To see if draw has been set so dispense can be opened
	public static boolean dopen;

	@FXML
	private void okButton() throws IOException {
		Window stage = okButton.getScene().getWindow();

		if ((conBox.getValue()).equals("--Containers--")) {
			main.showdConError();
		} else if ((unitBox.getValue()).equals("--Units--")) {
			main.showdUnitError();
		} else if (((unitAmount.getText()).isEmpty()) == true) {
			main.showTextError();
		} else {
			String am = unitAmount.getText();
			Boolean a = true;
			setOpen(a);
			setAmount(am);
			int dr = Integer.parseInt(am);
			dispenseController.setLeft(dr);
			stage.hide();
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

	public static String[] drawProcedure() throws Exception {
		if (isInputValid()) {
			procedure = new String[] { "Draw " + amount + " " + units
					+ " from " + con };
		}
		return procedure;
	}

	public static boolean isInputValid() throws Exception {
		if (amount == null || units == null || con == null)
			return false;
		else
			return true;
	}

	@FXML
	private void canButton() {
		Window stage = canButton.getScene().getWindow();
		stage.hide();
	}

	//public void setFinal(String a) {
	//	this.finalString = a;
	//}

	public void setAmount(String a) {
		this.amount = a;
	}
	/*
	public void setCon(String a) {
		con = a;
	}
	
	public void setUnits(String a) {
		units = a;
	}

	public String getFinal() {
		return this.finalString;
	}
	*/
	public static String getAmount() {
		return amount;
	}
	/*
	public static String getCon() {
		return con;
	}

	public static String getUnits1() {
		return units;
	}*/

	public void setOpen(Boolean a) {
		dopen = a;
	}

	public static Boolean getOpen() {
		return dopen;
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