package com.wbuve.graph.stat;

import java.util.List;

import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.model.CommitMsg;

@Component("statDelongTotal")
public class StatDelongTotal extends AbstractStatCalc{
	
	public void initCount(Object Ext, CommitMsg curResult) {
		curResult.setCount((Integer)Ext);
	}

	public void calcCount(Object Ext, CommitMsg curResult) {
		curResult.setCount(curResult.getCount() + (Integer)Ext);
	}

	public List<CommitMsg> preSendCount(CommitMsg curResult) {
		return null;
	}

	@Override
	public boolean isOk(CommitMsg msg) {
		String target = msg.getTarget();
		if(target.indexOf("qps") > 0){
			@SuppressWarnings("unchecked")
			List<String> info = (List<String>) msg.getOutExt();
			if(info.size() != 3){
				return false;
			}
			msg.setTime(Long.parseLong(info.get(2)));
			msg.setOutExt(Integer.parseInt(info.get(1)));
			return true;
		}
		return false;
	}
}
