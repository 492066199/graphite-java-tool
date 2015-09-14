package com.wbuve.graph.stat;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wbuve.graph.model.CommitMsg;

@Scope("prototype")
@Component("statCalcTotal")
public class StatCalcTotal extends AbstractStatCalc{

	public void initCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(1);
	}

	public void calcCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(curResult.getCount() + 1);
	}

	public void preSendCount(CommitMsg curResult) {
	}
}
