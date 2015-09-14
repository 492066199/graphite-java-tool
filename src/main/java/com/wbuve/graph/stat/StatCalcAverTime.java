package com.wbuve.graph.stat;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wbuve.graph.model.CommitMsg;

@Scope("prototype")
@Component("statCalcAverTime")
public class StatCalcAverTime extends AbstractStatCalc{
	
	public void initCount(Object ext, CommitMsg curResult) {
		int timeInit = (int)((Double)ext * 1000);
		curResult.setCount(timeInit);          
		curResult.setTotalNum(1);            
	}

	public void calcCount(Object Ext, CommitMsg curResult) {
		int timeTmp = curResult.getCount() + (int)((Double)Ext * 1000);
		curResult.setCount(timeTmp);
		curResult.setTotalNum(curResult.getTotalNum() + 1);
	}

	public void preSendCount(CommitMsg curResult) {
		curResult.setCount(curResult.getCount() / curResult.getTotalNum());
	}
}
