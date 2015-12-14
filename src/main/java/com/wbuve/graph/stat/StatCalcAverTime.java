package com.wbuve.graph.stat;

import java.util.List;

import com.google.common.collect.Lists;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.model.CommitMsg;

@Component("statCalcAverTime")
public class StatCalcAverTime extends AbstractStatCalc{
	private String postfixaver;
	private String postfixtotal;
	
	public String preInitCount(CommitMsg msg) {
		return getPrefix() + "." + msg.getTarget();
	}
	
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

	public List<CommitMsg> preSendCount(CommitMsg curResult) {
		List<CommitMsg> cs = Lists.newArrayList();
		
		CommitMsg cstotal = curResult.newCopy();
		cstotal.setTarget(cstotal.getTarget() + "." + postfixtotal);
		cs.add(cstotal);
		
		curResult.setCount(curResult.getCount() / curResult.getTotalNum());
		curResult.setTarget(curResult.getTarget() + "." + postfixaver);
		cs.add(curResult);
		return cs;
	}
	
	public void setPostfix(String postfix) {
		super.setPostfix(postfix);
		int index = postfix.indexOf(":");
		this.postfixaver = postfix.substring(0, index);
		this.postfixtotal = postfix.substring(index + 1, postfix.length());
		
	}
}
