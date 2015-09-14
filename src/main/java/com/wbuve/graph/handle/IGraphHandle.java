package com.wbuve.graph.handle;

import java.util.List;

import com.wbuve.graph.model.CommitMsg;

public interface IGraphHandle {

	public int getPriority();
	
	public String getId();
	
	public boolean handleIn();

	public boolean handleOut(List<CommitMsg> cms);
	
	public boolean handleMsg();
}
