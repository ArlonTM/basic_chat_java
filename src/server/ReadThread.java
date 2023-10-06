package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends ServerThread {

	private ConnHandler mConnHandler = null;
	private MsgInterface mMsgInterface = null;
	private Socket mClient = null;
	
	public ReadThread(ConnHandler connHandler, MsgInterface msgInterface, Socket client) {
		super();
		mConnHandler = connHandler;
		mMsgInterface = msgInterface;
		mClient = client;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
			String message = "";
			
			do {
				message = in.readLine();
				mMsgInterface.messageReceived(mClient, message);
			} while((message == null) || (!message.equalsIgnoreCase("DONE")));
			
			mConnHandler.dropClient(mClient);
			in.close();
		} catch (IOException e) {
		}
	}
	
	public interface MsgInterface {
		public void messageReceived(Socket client, String message);
	}
}
