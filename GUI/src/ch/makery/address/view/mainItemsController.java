package ch.makery.address.view;

import java.io.IOException;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;

public class mainItemsController {
	
	private MainApp main;
	
	@FXML
	private void goNew() throws Exception  {
		main.showNewScene();
	}
	
	@FXML
	private void goExist() throws Exception  {
		main.showNewScene1();
	}
	
	@FXML
	private void goDevice() throws Exception  {
		main.showNewScene2();
	}
}
