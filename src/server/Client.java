package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	public Client() {
		Socket client = null;
		
		try {
			client = new Socket("localhost", 1000);
		} catch (IOException e) {
			return;
		}

		Scanner scanner = new Scanner(System.in);
		
		try {
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			String message = "";
			
			do {
				message = scanner.nextLine();
				out.println(message);
			} while((message == null) || (!message.equalsIgnoreCase("DONE")));

			out.close();
		} catch (IOException e) {
		}
		
		scanner.close();
		
		try {
			client.close();
		} catch (IOException e) {
		}
	}
}