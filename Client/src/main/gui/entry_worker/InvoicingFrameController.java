package main.gui.entry_worker;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.ClientController;
import main.controllers.UserRequestController;

public class InvoicingFrameController implements Initializable {
	
	@FXML
	private Label reservationId;
	@FXML
	private Label msgLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reservationId.setText(MonitoringFrameControlleroring.id);
		reservationId.setVisible(true);
		UserRequestController.sendShowBill(reservationId.getText());
		if (ClientController.showBill!=null) 
		{
			displayMSG(MonitoringFrameControlleroring.price + "");
		}
		else {//if null
				System.out.println("[BillCakFrameController] - did no bill");
				displayMSG("reservation Not Found");
			 }
		}
	
	/*
	public void showBill(ActionEvent e){
		if (reservatiomId.getText().length() <= 0){
			displayMSG("Please enter the  Bill Id");
			return;
		}
		try {
			Integer.parseInt(reservatiomId.getText());
			displayMSG("reservationId has to be Number");
		}catch(Exception ex) {
			displayMSG("Invalid id was inputted");
			return;
		}
		
		UserRequestController.sendShowBill(reservatiomId.getText());
		msgLabel.setVisible(false);
		billLabel.setVisible(false);
		if (ClientController.showBill!=null) 
		{
			displayMSG(ClientController.showBill.returnPrice()+"");
		}
		else {//if null
				System.out.println("[BillCakFrameController] - did no bill");
				displayMSG("reservation Not Found");
			 }
		}*/
	
	public void displayMSG(String txt) {
		msgLabel.setText(txt);
		msgLabel.setVisible(true);
	}



}

