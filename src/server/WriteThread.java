package server;

import java.util.Scanner;

public class WriteThread extends ServerThread {

	public WriteThread() {
		super();
		mInterval = 4000;
	}
	
	public void setAction() {
//		new Scanner(System.in).next();
		System.out.print("\nMASTER: Great!!!\n\n");
	}
}
