package main.controllers;

import main.ClientController;
import requests.Message;
import requests.RequestType;

public class UserRequestController {
	public static boolean LogedIn = false;

	public static void sendUserLogIn(String userName,String password) {
		ClientController.getController().accept(new Message(RequestType.LOGIN_USER, userName,password));
	}

}
