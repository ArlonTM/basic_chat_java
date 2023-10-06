package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import server.ConnHandler.ConnException;
import server.ConnHandler.ConnInterface;
import server.ReadThread.MsgInterface;

public class Master {
	
	private ServerSocket mServer = null;
	private ReadThread mReadThread = null;
	private ConnHandler mConnHandler = null;
	
	private ConnInterface mInterface = new ConnInterface() {
		
		@Override
		public void serverOpened(ServerSocket server) {
			mServer = server;
		}

		@Override
		public void serverClosed() {
			mServer = null;
			restart();
		}

		@Override
		public void clientJoined(Socket client) {
			System.out.println("CLIENT JOINED! - " + client.getPort());
			mReadThread = new ReadThread(mConnHandler, mMsgInterface, client);
			mReadThread.start();
		}

		@Override
		public void clientLeft(Socket client) {
		}

		@Override
		public void error(ConnException exception) {
			if (!exception.equals(ConnException.SERVER_FAILED_TO_OPEN)) {
				
			}
		}

		@Override
		public void done() {
			try {
				mServer.close();
			} catch (IOException e) {
			}
		}
	};
	
	private MsgInterface mMsgInterface = (Socket client, String message) -> {
		System.out.println(String.format("CLIENT-%d: %s", client.getPort(), message));
	};
	
	public Master() {
		start();
	}
	
	private void start() {
		mConnHandler = new ConnHandler(mInterface);
		mConnHandler.start();
	}
	
	private void restart() {
		System.out.println("The server was closed!");
		System.out.println("To restart and listen for connection, type '1'!");
		
		Scanner scanner = new Scanner(System.in);
		
		if (scanner.nextInt() == 1) {
			start();
		}
		
		scanner.close();
	}
	
	public void end() {
		mConnHandler.end();
	}
}