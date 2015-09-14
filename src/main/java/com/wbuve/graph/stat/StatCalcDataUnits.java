package com.wbuve.graph.stat;

import com.wbuve.graph.model.CommitMsg;

public class StatCalcDataUnits extends AbstractStatCalc{
	@Override
	public void initCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(0);	
	}
	@Override
	public void calcCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(curResult.getCount() + (Integer)Ext);
	}
	@Override
	public void preSendCount(CommitMsg curResult) {
		
	}

	@Override
	public String preInitCount(CommitMsg msg) {
		return null;
	}
}
