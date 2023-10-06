import java.util.Scanner;
import server.Client;
import server.Master;

public class Entry {

	public static void main(String[] args) {
		System.out.println("Choose between the following options:");
		System.out.println("1. Server");
		System.out.println("2. Client");
		Scanner scanner = new Scanner(System.in);
		int input = scanner.nextInt();
		
		if (input == 1) {
			startServer();
			scanner.close();
		} else {
			startClient();
		}
	}
	
	private static void startServer() {
		System.out.println("SERVER!!!");
		new Master();
	}
	
	private static void startClient() {
		System.out.println("CLIENT!!!");
		new Client();
	}
}
