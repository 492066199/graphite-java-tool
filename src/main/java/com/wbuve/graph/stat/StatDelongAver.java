package com.wbuve.graph.stat;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wbuve.graph.model.CommitMsg;

@Scope("prototype")
@Component("statDelongAver")
public class StatDelongAver extends AbstractStatCalc{
	@Override
	public String preInitCount(CommitMsg msg) {
		@SuppressWarnings("unchecked")
		List<String> info = (List<String>) msg.getOutExt();
		msg.setTime(Long.parseLong(info.get(3)));
		String count = info.get(1);
		String sum = info.get(2);
		msg.setOutExt(sum + ":" + count);
		return msg.getTarget();
	}
	
	@Override
	public void initCount(Object Ext, CommitMsg curResult) {
		String info = (String)Ext;
		List<String> tmp = Lists.newArrayList(Splitter.on(':').split(info));
		double sum = Double.parseDouble(tmp.get(0));
		int total = Integer.parseInt(tmp.get(1));
		
		curResult.setCount((int)sum);
		curResult.setTotalNum(total);
	}
	
	@Override
	public void calcCount(Object Ext, CommitMsg curResult) {
		String info = (String)Ext;
		List<String> tmp = Lists.newArrayList(Splitter.on(':').split(info));
		double sum = Double.parseDouble(tmp.get(0));
		int total = Integer.parseInt(tmp.get(1));
		
		curResult.setCount(curResult.getCount() + (int)sum);
		curResult.setTotalNum(total + curResult.getTotalNum());

	}

	@Override
	public void preSendCount(CommitMsg curResult) {
		curResult.setCount(curResult.getCount() / curResult.getTotalNum());
	}


	@Override
	public boolean isOk(CommitMsg msg) {
		String target = msg.getTarget();
		if(target.indexOf("time") > 0){
			return true;
		}
		return false;
	}
}
