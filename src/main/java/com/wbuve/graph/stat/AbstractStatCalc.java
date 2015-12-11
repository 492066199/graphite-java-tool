package com.wbuve.graph.stat;

import com.wbuve.graph.model.CommitMsg;

public abstract class AbstractStatCalc implements IStatCalc{
	private String prefix;
	private String postfix;
	
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
		if(this.prefix == null){
			prefix = "";
		}
		if(this.postfix == null){
			postfix = "";
		}
		return getPrefix() + "." + msg.getTarget() + "." + postfix;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

}
