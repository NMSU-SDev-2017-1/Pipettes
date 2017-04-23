package UI.view;

import java.io.IOException;
import java.util.Arrays;

import UI.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Window;

public class MainController {
	
	//ObservableSet<String> items = FXCollections.observableSet();
	ObservableList<String> items = FXCollections.observableArrayList("Hello", "Bye");
	@FXML
	public TreeView<String> treeView;
	@FXML 
	public ListView<String> listView;
	//public static ListView newList = listView;
	@FXML
	private Button okButton;
	@FXML
	private Button canButton;
	
	//public static String[] procedure;
	public void list(){
		//System.out.println("You made it");
		//listView.getItems().add("byee");
	}
	@FXML
	private void drawButton() throws IOException{
		Main.showDraw();
	}
	@FXML
	private void dispenseButton() throws IOException{
		drawController dC = new drawController();
		if((dC.dopen)==false){
			Main.showDisError();
		}
		else{
			Main.showDispense();
		}
	}
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
	
	public void initialize(){
		TreeItem<String> root = new TreeItem<>("Root");
		
		TreeItem<String> child1 = new TreeItem<>("Child1");
		TreeItem<String> child2 = new TreeItem<>("Child2");
		TreeItem<String> child3 = new TreeItem<>("Child3");
		
		root.getChildren().addAll(child1,child2,child3);
		
		TreeItem<String> child1_1 = new TreeItem<>("Child1.1");
		TreeItem<String> child1_2 = new TreeItem<>("Child1.2");
		TreeItem<String> child1_3 = new TreeItem<>("Child1.3");
		
		child1.getChildren().addAll(child1_1,child1_2,child1_3);
		
		listView.setEditable(true);
		listView.setCellFactory(TextFieldListCell.forListView());
		listView.setItems(items);
		treeView.setRoot(root);
		
	}
}
