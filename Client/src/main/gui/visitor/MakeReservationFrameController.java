package main.gui.visitor;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.ClientController;
import main.ClientUI;
import main.controllers.VisitorRequestController;
import main.gui.MainFrameController;
import utilities.SceneController;


/**
 * This class represents the controller for the frame where visitors can make reservations.
 * It allows visitors to input reservation details and submit them for processing.
 */
public class MakeReservationFrameController implements Initializable{
 
	@FXML
	private  ComboBox<String> orderType;
	@FXML
	private  TextField numOfVisitorsField;
	@FXML 
	private  DatePicker dateField;
	@FXML
	private  TextField hourField;
	@FXML
	private  TextField minuteField;
	@FXML
	private  ComboBox<String> parkNameField;
	@FXML
	private  TextField phoneField;
	@FXML
	private  TextField emailField;
	@FXML
	private Button bookBtn;
	@FXML
	private Label msgLabel;
	@FXML
	private  CheckBox payedCheckBox;
	@FXML
	private Label payLabel;
	public static Order o;
	String str;
	LocalDate date;
	LocalDate today = LocalDate.now();
	public static boolean hasSpace = false;
	
	
	 /**
	 * 
     * @param arg0 The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setOrderTypeComboBox();
		setParkNameFieldComboBox();
		
		if (o != null) {
			orderType.setValue(o.getOrderType());
			numOfVisitorsField.setText((o.getNumOfVisitors())+ "");
			LocalDate specificDate = LocalDate.of(o.getYear(),o.getMonth(),o.getDay()); // Year, Month, Day
			dateField.setValue(specificDate);
			hourField.setText(o.getHour());
			minuteField.setText(o.getMinute());
			parkNameField.setValue(o.getParkName());
			phoneField.setText(o.getPhone());
			emailField.setText(o.getEmail());
		}else {
			o = new Order();
			orderType.getSelectionModel().select("Private");
			numOfVisitorsField.setText("1");
		}
		payLabel.setVisible(false);
	    payedCheckBox.setVisible(false);
		orderType.setOnAction(event -> {
		    String selectedType = orderType.getValue();
		    if (selectedType != null && selectedType.equals("Organized Group")) {
		        payLabel.setVisible(true);
		        payedCheckBox.setVisible(true);
		    } else {
		        payLabel.setVisible(false);
		        payedCheckBox.setVisible(false);
		    }
		});	
		
	}
	
	
	
	 /**
     * Handles the action of making a reservation after
     * clicking on Make Reservation.
     * @param e The action event.
     * @throws Exception if an error occurs during reservation making.
     */
	public void makeReservation(ActionEvent e) throws Exception{
		if(isValid()){
			loadData();
			VisitorRequestController.sendReservation(o);
			if (VisitorRequestController.finishedMakingReservation) {
				if (hasSpace) {
					o=null;
					ClientController.connectedVisitor.setFoundInDB(true);
					SceneController.switchFrame("GoNature",e,new MainFrameController());
				}else {
					DeclinedReservationOptions.setOrder(o);
					SceneController scene = new SceneController();
					scene.setPane(ClientUI.contentPane, "/main/gui/visitor/DeclinedReservationFrame.fxml");
				}
			}
			else {
				System.out.println("[MakeReservationFrameController] - did not finished Making Reservation");		
			}	
		}
		VisitorRequestController.finishedMakingReservation = false;
		hasSpace = false;
	}

	
	 /**
     * Validates the input fields for making a reservation.
     * @return true if all input fields are valid, otherwise false.
     */
	private boolean isValid() {
		//null input
		if(orderType.getValue()==null || numOfVisitorsField.getText().length() <= 0 || dateField.getValue()==null || hourField.getText().length() <= 0 || minuteField.getText().length()<= 0 || phoneField.getText().length() <= 0 || emailField.getText().length() <= 0 || parkNameField.getValue()==null) {
        	displayError("Please enter all fields");
			return false;}
		
		//test to check if the organized group reservation is done by an instructor
		if("Organized Group".equals(orderType.getValue()) && !(ClientController.connectedVisitor.isInstructor())){
			displayError("Please enter legal type");
			return false;
		}
		//illegal input
		try {
			int numVisitors = Integer.parseInt(numOfVisitorsField.getText());
			if(15 < numVisitors || numVisitors <= 0  || ("Organized Group".equals(orderType.getValue()) && numVisitors == 1)) {
			displayError("Please enter a valid number of visitors");
			return false;}
		}catch(Exception e) {
			displayError("Please enter a valid number of visitors");
		    return false;
		}
		date = dateField.getValue();
		if(date != null && date.isBefore(today)) {
			displayError("Please enter a legal date");
			return false;}
		String hourPattern = "^([01]?[0-9]|2[0-3])$";
	    String text1 = hourField.getText();
	    // Check if the text matches the hour pattern
	    if(!(Pattern.matches(hourPattern, text1))) {
	    	displayError("Please enter a legal hour");
			return false;}
	    String minutesPattern = "^[0-5][0-9]$";
        String text2 = minuteField.getText();
        // Check if the text matches the minutes pattern
        if(!(Pattern.matches(minutesPattern, text2))) {
        	displayError("Please enter a legal minutes");
			return false;}
        // The park working hours are 8:00 - 22:00
        try {
	        int hour = Integer.parseInt(hourField.getText());
	        int minute = Integer.parseInt(minuteField.getText());
	        if (hour < 8 || hour > 22 || (hour == 22 && minute > 0)) {
	            displayError("Please enter a time between 8:00 and 22:00");
	            return false;
	        }
	    } catch (NumberFormatException e) {
	        displayError("Please enter valid hour and minute values");
	        return false;
	    }
        String phonePattern = "^05\\d{8}$";
        String text3 = phoneField.getText();
        // Check if the text matches the phone number pattern
        if(!(Pattern.matches(phonePattern, text3))) {
        	displayError("Please enter a legal phone number");
			return false;}
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String text4 = emailField.getText();
        // Check if the text matches the email address pattern
        if(!(Pattern.matches(emailPattern, text4))) {
        	displayError("Please enter a legal email");
			return false;}
		return true;
    
	}
	
	
	/**
    * Loads the data from the input fields into the Order Object.
    */
	private void loadData() {

		o.setOrderType(orderType.getValue());
		o.setNumOfVisitors(numOfVisitorsField.getText());		 
		date = dateField.getValue();
		str = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		o.setDate(str);
		o.setHour(hourField.getText());
		o.setMinute(minuteField.getText());
		o.setParkName(parkNameField.getValue());
		o.setphone(phoneField.getText());
		o.setEmail(emailField.getText());
		if(orderType.getValue().equals("Organized Group")) o.setIsPayed(payedCheckBox.isSelected());
		else o.setIsPayed(false);
		if(ClientController.connectedVisitor == null) {
			o.setInvitedInAdvance(false);
		}
		else {
			o.setInvitedInAdvance(true);
			o.setVisitorID(ClientController.connectedVisitor.getId());
		}//WHAT ABOUT IF THE ENTRY WORKER DID THE RESERVATION WHAT ID SHOULD I ENTER ?? (make another gui to enter id before switching to make reservation)
		o.setIsConfirmed(false);
	}
	
	
	/**
    * Sets up the options for the park name field combo box.
    */
	private void setParkNameFieldComboBox() {
		ArrayList<String> al = new ArrayList<String>();	

		for (String pName : ClientController.getParks().keySet())
			al.add(pName);
		
		ObservableList<String> list = FXCollections.observableArrayList(al);
		parkNameField.setItems(list);
	}
	
	
	/**
    * Sets up the options for the order type combo box.
    */
	private void setOrderTypeComboBox() {
	    ArrayList<String> al = new ArrayList<String>();	
		al.add("Private");
		al.add("Organized Group");

		ObservableList<String> list = FXCollections.observableArrayList(al);
		orderType.setItems(list);
	}


	/**
    * Displays an error message.
    * @param txt The error message to display.
    */
	public void displayError(String txt) {
		msgLabel.setText(txt);
		msgLabel.setVisible(true);
	}
}