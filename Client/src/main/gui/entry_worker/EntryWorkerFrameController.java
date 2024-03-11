package main.gui.entry_worker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.SceneController;

/**
 * This class serves as the controller for the EntryWorkerFrame.fxml file.
 * It manages the actions and events related to the Entry Worker's interface.
*/

public class EntryWorkerFrameController extends Application{
	
	 @FXML
	 private Button checkAvailablePlacesBtn;
	 
	 @FXML
	 private Button viewBillBtn;
	 
	
	 public static void main(String args[]) {
		 launch(args);
	 }
	 
	 
	/**
    * @param primaryStage the primary stage for the application
	* @throws Exception if an error occurs during initialization
    */
	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneController sceneController = new SceneController();
		sceneController.changeScene("GoNature - Entry Worker", primaryStage,
							"/main/gui/entry_worker/EntryWorkerFrame.fxml");
	}
	
	
	
	/***
	 * This method responsible for hiding EntryWorker window
	 * after clicking on checkAvailablePlaces button and
	 * new window is opened for the Available Places that the 
	 * EntryWorker can check.
	 * @param event
	 */
	public void checkAvailablePlaces(ActionEvent event) {
		((Node)event.getSource()).getScene().getWindow().hide(); //hide EntryWorker window
		AvailablePlacesFrameController availablePlaceFrame = new AvailablePlacesFrameController();
		try {
			availablePlaceFrame.start(new Stage());;
		}
		catch (Exception ex) {
		System.out.println("[availablePlaceFrameController] - Error starting availablePlaceFrame");
		ex.printStackTrace();
		}
	}
	
	
	/***
	 * This method responsible for hiding EntryWorker window
	 * after clicking on View Bill button and
	 * new window is opened to check the Bill of the visitor 
	 * that the EntryWorker can check.
	 * @param event
	 */
	public void viewBill(ActionEvent event) {
		
		((Node)event.getSource()).getScene().getWindow().hide(); //hide EntryWorker window
		BillCakFrameController BillCakFrame = new BillCakFrameController();
		try {
			BillCakFrame.start(new Stage());
		}
		catch (Exception ex) {
		System.out.println("[BillCakFrameController] - Error starting BillCakFrame");
		ex.printStackTrace();
		}
	}

}
