package com.wbuve.graph.handle;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wbuve.graph.stat.StatCenter;
import com.wbuve.graph.stat.StatListener;

public abstract class AbstractHandle implements IHandle{
	public final List<StatListener> statListens = Lists.newLinkedList();
	//last success!
	@Override
	public void addStat(String indexs) {
		List<String> ss = Lists.newArrayList(Splitter.on(',').split(indexs));
		for(String s : ss){
			this.statListens.add(StatCenter.INSTANCE.get(Integer.parseInt(s)));
		}
	}
}
