package com.wbuve.graph.stat;

import java.util.List;

import com.google.common.collect.Lists;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.model.CommitMsg;

@Component("statDelongAver")
public class StatDelongAver extends AbstractStatCalc{
	private String postfixaver;
	private String postfixtotal;
	
	public String preInitCount(CommitMsg msg) {
		return msg.getTarget();
	}
	
	@Override
	public void initCount(Object Ext, CommitMsg curResult) {
		@SuppressWarnings("unchecked")
		List<String> tmp = (List<String>)Ext;
		double sum = Double.parseDouble(tmp.get(2));
		int total = Integer.parseInt(tmp.get(1));
		curResult.setCount((int)sum);
		curResult.setTotalNum(total);
	}
	
	@Override
	public void calcCount(Object Ext, CommitMsg curResult) {
		@SuppressWarnings("unchecked")
		List<String> tmp = (List<String>)Ext;
		double sum = Double.parseDouble(tmp.get(2));
		int total = Integer.parseInt(tmp.get(1));
		
		curResult.setCount(curResult.getCount() + (int)sum);
		curResult.setTotalNum(total + curResult.getTotalNum());

	}

	@Override
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


	@Override
	public boolean isOk(CommitMsg msg) {
		String target = msg.getTarget();
		if(target.indexOf("time") > 0){
			@SuppressWarnings("unchecked")
			List<String> info = (List<String>) msg.getOutExt();
			if(info.size() != 4){
				return false;
			}
			msg.setTime(Long.parseLong(info.get(3)));
			return true;
		}
		return false;
	}
	
	public void setPostfix(String postfix) {
		super.setPostfix(postfix);
		int index = postfix.indexOf(":");
		this.postfixaver = postfix.substring(0, index);
		this.postfixtotal = postfix.substring(index + 1, postfix.length());
		
	}
}
