package com.wbuve.graph.model;

public class CommitMsg implements Cloneable{
	private String id;			 
	private long time;		 
	private String target;		 
	private Object outExt;
	
	private int count;			 
	private int totalNum;		 
	
	public CommitMsg(String id) {
		this.id = id;
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public Object getOutExt() {
		return outExt;
	}

	public void setOutExt(Object outExt) {
		this.outExt = outExt;
	}
	
	public CommitMsg newCopy(){
		try {
			return (CommitMsg)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
