package com.uve.graph.model;

public class Msg {
	private String id;
	private String msg;
	public Msg(String id, String tmp) {
		this.msg = tmp;
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}	
}
