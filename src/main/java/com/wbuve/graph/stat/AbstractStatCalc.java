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
		String tmp = msg.getTarget();
		if(this.prefix == null){
			prefix = "";
		}else {
			tmp = getPrefix() + "." + tmp;
		}
		
		if(this.postfix == null){
			postfix = "";
		}else {
			tmp = tmp + "." + postfix;
		}
		
		if(tmp.charAt(0) == '.'){
			System.out.println("##########" + tmp);
			System.out.println("##########" + prefix);
			System.out.println("##########" + postfix);
		}
		return  tmp;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
}
