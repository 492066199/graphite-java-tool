package com.wbuve.graph.handle.imp;

import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.IHandleOut;
import com.wbuve.graph.model.CommitMsg;

@Component("commonHandleOut")
public class CommonHandleOut implements IHandleOut{

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public boolean send(String string) {
		System.out.println(string);
		return true;
	}

	public String CommitToString(CommitMsg cm) {
		return cm.getTarget() + " " + cm.getCount() + " " + cm.getTime();
	}

	@Override
	public void setOutFdname(String content) {
		// TODO Auto-generated method stub
		
	}
	
}
