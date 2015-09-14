package com.wbuve.graph.stat;

import com.wbuve.graph.model.CommitMsg;

public interface IStatCalc {
	public boolean isOk(CommitMsg msg);
	
	public String preInitCount(CommitMsg msg);
	
	public void initCount(Object ext, CommitMsg curResult);

	public void calcCount(Object ext, CommitMsg curResult);

	public void preSendCount(CommitMsg curResult);
	
	public void setPrefix(String prefix);
}
