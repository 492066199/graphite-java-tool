package com.uve.graph.output;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GraphTarget implements IOutputTarget {
	private boolean needRetry;
	private Socket socket;
	private PrintWriter pw;
	private int retryCount;
	
	public void load() {
		try {
			this.socket = new Socket("221.179.193.178", 33336);
			this.pw = new PrintWriter(socket.getOutputStream());
			needRetry = false;
			retryCount = 0;
		} catch (UnknownHostException e) {
			needRetry = true;
			e.printStackTrace();
		} catch (IOException e) {
			needRetry = true;
			e.printStackTrace();
		}
	}
	
	
	public GraphTarget() {
		load();
	}
	
	
	public boolean send(String string) {
		if(needRetry){
			if(this.retryCount > 10){
				load();
				return false;
			}else {
				retryCount++;
				return false;
			}
		}
		pw.println(string);
		return true;
	}
}
