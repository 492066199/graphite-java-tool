package com.wbuve.graph.stat;

import com.wbuve.graph.model.CommitMsg;

public abstract class AbstractStatCalc implements IStatCalc{
	private String prefix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public boolean isOk(CommitMsg msg){
		return true;
	}
	
	public String preInitCount(CommitMsg msg) {
		return getPrefix() + "." + msg.getTarget();
	}

}
