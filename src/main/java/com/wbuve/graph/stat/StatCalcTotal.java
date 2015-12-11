package com.wbuve.graph.stat;


import java.util.List;

import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.model.CommitMsg;

@Component("statCalcTotal")
public class StatCalcTotal extends AbstractStatCalc{

	public void initCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(1);
	}

	public void calcCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(curResult.getCount() + 1);
	}

	public List<CommitMsg> preSendCount(CommitMsg curResult) {
		return null;
	}
}
