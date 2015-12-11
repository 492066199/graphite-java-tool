package com.wbuve.graph.handle;

import java.util.List;

import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;

public interface IHandle {
	
	public List<CommitMsg> handle(Msg m);
	
	public void init();

	public void addStat(String indexs);
}
