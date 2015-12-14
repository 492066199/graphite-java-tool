package com.wbuve.graph.handle;

import java.util.List;

import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;
import com.wbuve.graph.stat.StatListener;

public interface IHandle {
	
	public List<CommitMsg> handle(Msg m);
	
	public void init();

	public void addStat(String indexs);
	
	public List<StatListener> getStatListens();
}
