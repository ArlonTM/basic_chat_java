package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnHandler implements Runnable {

	private boolean mSentinel = true;
	private ServerSocket mServer = null;
	private Thread mThread = null;
	private ConnInterface mConnInterface = null;
	private List<Socket> mClients = new ArrayList<Socket>();
	
	public ConnHandler(ConnInterface connInterface) {
		if (mThread != null) return;
		mThread = new Thread(this);
		mConnInterface = connInterface;
	}
	
	public void start() {
		if (mThread == null) return;
		if (mThread.isAlive()) return;
		mSentinel = true;
		mThread.start();
	}
	
	@Override
	public void run() {
		mServer = null;
		
		try {
			mServer = new ServerSocket(1000);
			mConnInterface.serverOpened(mServer);
		} catch (IOException e) {
			mConnInterface.error(ConnException.SERVER_FAILED_TO_OPEN);
			return;
		}
		
		while(mSentinel) {
			try {
				addClient(mServer.accept());
			} catch (IOException e) {
				mConnInterface.error(ConnException.CLIENT_FAILED_TO_ADD);
			}
		}

		mConnInterface.serverClosed();
	}
	
	public void addClient(Socket client) {
		mClients.add(client);
		mConnInterface.clientJoined(client);
		updateClients();
	}
	
	public void dropAllClients() {
		for (Socket client : mClients) {
			dropClient(client);
		}
	}
	
	public void dropClient(Socket client) {
		try {
			client.close();
		} catch (IOException e) {
		}
		mClients.remove(client);
		mConnInterface.clientLeft(client);
		updateClients();
	}
	
	private void updateClients() {
		mSentinel = !mClients.isEmpty();
		
		if (!mSentinel) {
			try {
				mServer.close();
			} catch (IOException e) {
			}
		}
	}
	
	public void end() {
		mSentinel = false;
		mThread = null;
	}
	
	public interface ConnInterface {
		public void serverOpened(ServerSocket server);
		public void serverClosed();
		public void clientJoined(Socket client);
		public void clientLeft(Socket client);
		public void error(ConnException exception);
		public void done();
	}
	
	public enum ConnException {
		
		SERVER_FAILED_TO_OPEN(
			"SERVER FAILED TO OPEN"
		),
		
		CLIENT_FAILED_TO_ADD(
			"FAILED TO ADD CLIENT"
		);
		
		private String mMessage = "";
		
		private ConnException(String message) {
			mMessage = message;
		}
		
		private String getMessage() {
			return mMessage;
		}
	}
}
