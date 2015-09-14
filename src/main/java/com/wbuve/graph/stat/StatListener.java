package com.wbuve.graph.stat;

import java.util.List;

import com.google.common.collect.Lists;
import com.wbuve.graph.model.CommitMsg;

public class StatListener {
	private List<IStatUtil> ls = Lists.newLinkedList();
	
	public void add(IStatUtil stat){
		ls.add(stat);
	}
	
	public List<CommitMsg> commit(CommitMsg msg){
		List<CommitMsg> cms = Lists.newLinkedList();
		List<CommitMsg> tmp;
		for(IStatUtil stat: ls){
			if(stat.isOk(msg)){
				tmp = stat.statistics(msg);
				if(tmp != null){
					cms.addAll(tmp);
				}
			}
		}
		return cms;
	}
}
