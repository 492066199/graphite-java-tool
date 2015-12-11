package com.wbuve.graph.handle.imp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.IHandleOut;
import com.wbuve.graph.model.CommitMsg;

@Component("graphTarget")
public class GraphTarget implements IHandleOut {
	private Socket socket;
	private OutputStream out;
	private String ip = null;
	private int port = 0; 
	
	public void load() {
		try {
			this.socket = new Socket(ip, port);
	        this.out = socket.getOutputStream();  
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		load();
	}

	@Override
	public String CommitToString(CommitMsg cm) {
		return cm.getTarget() + " " + cm.getCount() + " " + cm.getTime();
	}

	@Override
	public void setOutFdname(String content) {
		List<String> cs = Lists.newArrayList(Splitter.on(':').omitEmptyStrings().split(content));
		this.ip = cs.get(0);
		this.port = Integer.parseInt(cs.get(1));
		
	}
}
