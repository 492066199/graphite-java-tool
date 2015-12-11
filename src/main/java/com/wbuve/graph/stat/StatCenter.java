package com.wbuve.graph.stat;

import java.util.Map;

import com.google.common.collect.Maps;

public enum StatCenter {
	INSTANCE;
	private final Map<Integer, StatListener> statsmap = Maps.newHashMap();
	
	public void set(Integer index, StatListener s){
		statsmap.put(index, s);
	}
	
	public StatListener get(Integer index){
		return statsmap.get(index);
	}
}
