package server;

public abstract class ServerThread implements Runnable {

	protected Thread mThread = null;
	protected long mInterval = 1000;
	protected boolean mSentinel = true;
	
	public ServerThread() {
		if (mThread != null) return;
		mThread = new Thread(this);
	}
	
	@Override
	public void run() {
		setAction();
	}
	
	public void start() {
		mSentinel = true;
		mThread.start();
	}
	
	public void end() {
		mSentinel = false;
	}
	
	protected void setAction() {
		
	}
}
