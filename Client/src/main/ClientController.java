package main;

import java.io.IOException;

import requests.Message;

public class ClientController {

	private static ClientController clientController;
	
	private GoNatureClient client;
	public static String HOST = "localhost";
	public static int PORT = 5555;
	
	/*
	 * variables to determine whether connected to the server/database or not
	 * */
	public static boolean connectedToServer = false;
	public static boolean fetchedData = false;
	
	private ClientController(String host, int port) {
		try {
			client = new GoNatureClient(host, port);
		} catch (IOException e) {
			System.out.println("[ClientController] - Error setting up connection");
			e.printStackTrace();
		}
	}
	
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	  public void accept(Message str) 
	  {
		  client.handleMessageFromClientUI(str);
	  }
	  
	  public GoNatureClient getClient() {
		  return client;
	  }
		public static ClientController getController() {
			if (clientController == null)
				clientController = new ClientController(ClientController.HOST, ClientController.PORT);
			return clientController;
				
		}

}
