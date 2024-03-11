package main.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Order;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.ClientController;
import main.ClientUI;
import utilities.SceneController;


public class ClientFrameController extends Application implements Initializable{
	
	@FXML
	private Pane dataPane;
	@FXML
	private ComboBox<String> ordersBox;
	@FXML
	private TextField parkNameField;
	@FXML
	private TextField timeField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField numOfVisitorsField;
	@FXML
	private TextField telephoneField;
	@FXML
	private Label errorLabel;
	@FXML
	private Label msgLabel;
	
	
	private void updateOrderBox()
	{
		ArrayList<String[]> data = ClientUI.clientController.getClient().getOrders();
		ArrayList<String> ordernums = new ArrayList<>();
		for (String[] row : data) {
			ordernums.add(row[1]);
		}
		
		ObservableList<String> list = FXCollections.observableArrayList(ordernums);
		ordersBox.setItems(list);
	}
	
	//a function to check if there was any error communicating with the server
	public boolean errorOccured() {
		if (!ClientController.connectedToServer) {
			errorLabel.setText("Error Connecting to the server");
			return true;
		}else if (!ClientController.fetchedData) {
			errorLabel.setText("Error fetching data from the database");
			return true;
		}
		return false;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (errorOccured()) 
			errorLabel.setVisible(true);
		else						
			updateOrderBox();//read the function that update the orderBox
	}

	
	/**
	* @param primaryStage the primary stage for the application
	* @throws Exception if an error occurs during initialization
	*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneController sceneController = new SceneController();
		sceneController.changeScene("GoNature Client - demo", primaryStage,
							               "/main/gui/ClientFrame.fxml");
	}
	
	
	public void showDataClick(ActionEvent e) throws Exception{
		if (ordersBox.getValue() != null) {
			String id = ordersBox.getValue();
			String[] order = ClientUI.clientController.getClient().getOrderById(id);
			if (order != null) {
				parkNameField.setText(order[0]);
				timeField.setText(order[2]);
				numOfVisitorsField.setText(order[3]);
				telephoneField.setText(order[4]);
				emailField.setText(order[5]);
				dataPane.setVisible(true);
			}
		}
	}
	
	
	public void updateData(ActionEvent e) throws Exception{
		Order o = new Order(parkNameField.getText(), ordersBox.getValue(), timeField.getText(),
				numOfVisitorsField.getText(), telephoneField.getText(), emailField.getText());
		ArrayList<String> data = o.getArray();
		//data.add(0, RequestType.UPDATE.getRequestId() + "");
		//ClientUI.clientController.accept(data);
		ArrayList<String> update = new ArrayList<>();
		//update.add(RequestType.REQUEST_DATA.getRequestId() + "");
		//ClientUI.clientController.accept(update);
		updateOrderBox();
		
		if (ClientController.connectedToServer) {
			msgLabel.setText("Succesfully updated data.");
			msgLabel.setLayoutX(90);
			
		}else {
			msgLabel.setText("Error updating data.");
			msgLabel.setLayoutX(105);
		}
		msgLabel.setVisible(true);
	}
	
	
	public void exitClient(ActionEvent e) {
		System.out.println("[ClientFrameController] - Exiting Client");
		ClientUI.clientController.getClient().quit();
		System.exit(0);
	}
	
	
	
	

}
