package UI.view;

import java.io.IOException;
import UI.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class MainController {

	@FXML
	private TreeView<String> treeView;
	@FXML 
	private ListView listView;
	@FXML
	private Button okButton;
	@FXML
	private Button canButton;
	
	
	@FXML
	private void drawButton() throws IOException{
		Main.showDraw();
	}
	@FXML
	private void dispenseButton() throws IOException{
		Main.showDispense();
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
		
		TreeItem<String> child2_1 = new TreeItem<>("Child2.1");
		TreeItem<String> child2_2 = new TreeItem<>("Child2.2");
		TreeItem<String> child2_3 = new TreeItem<>("Child3.3");
		
		child2.getChildren().addAll(child2_1,child2_2,child2_3);
		
		TreeItem<String> child3_1 = new TreeItem<>("Child3.1");
		TreeItem<String> child3_2 = new TreeItem<>("Child3.2");
		TreeItem<String> child3_3 = new TreeItem<>("Child3.3");
		
		child3.getChildren().addAll(child3_1,child3_2,child3_3);
		
		treeView.setRoot(root);
	}
}
