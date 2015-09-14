package com.wbuve.graph.handle;

import com.wbuve.graph.model.CommitMsg;

public interface IHandleOut {
	public void init();
	
	public boolean send(String string);
	
	public String CommitToString(CommitMsg cm);

	public void setOutFdname(String content);
}
