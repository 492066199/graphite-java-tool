package com.wbuve.graph.handle;

public interface IHandleIn {
	public void init();
	
	public String readLog(); 
	
	public int getPriority();
	
	public void setFdName(String name);

	public void setPriority(int parseInt);
}
