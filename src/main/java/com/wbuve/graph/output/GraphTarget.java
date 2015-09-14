package com.wbuve.graph.output;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.wbuve.graph.handle.IHandleOut;
import com.wbuve.graph.model.CommitMsg;

public class GraphTarget implements IHandleOut {
	private Socket socket;
	private OutputStream out;
	
	public void load() {
		try {
			this.socket = new Socket("10.77.96.122", 2003);
	        this.out = socket.getOutputStream();  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public GraphTarget() {
		load();
	}
	
	public boolean send(String string) {
		try {
			out.write((string + "\n").getBytes());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			load();
			return false;
		}
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String CommitToString(CommitMsg cm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOutFdname(String content) {
		// TODO Auto-generated method stub
		
	}
}
