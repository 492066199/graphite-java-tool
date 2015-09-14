package com.wbuve.graph.handle;

import java.util.Map;

import com.google.common.collect.Maps;
import com.wbuve.graph.stat.StatListener;

public abstract class AbstractHandle implements IHandle{
	public final Map<Integer ,StatListener> statListens = Maps.newHashMap();
	
	@Override
	public void addStat(Integer index, StatListener stats) {
		this.statListens.put(index, stats);
	}
}
