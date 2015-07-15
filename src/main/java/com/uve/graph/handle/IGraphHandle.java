package com.uve.graph.handle;

import java.util.List;

import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.CommitMsg;
import com.uve.graph.model.Msg;

public interface IGraphHandle{
	
	List<? extends IGraphHandle> setup(String[] strings, GraphLaunch graphLaunch);

	String getId();
	
	void handleMsg(Msg msg);

	void readLog();
	
	void load();

	boolean sendMsgToTarget(CommitMsg msg);

	int getPriority();
	
	public  int getOutputId();
}