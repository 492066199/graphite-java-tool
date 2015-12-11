package com.wbuve.graph.handle.imp;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.AbstractHandle;
import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;

@Component("delongTcpHandle")
public class DelongTcpHandle extends AbstractHandle{
	private final Logger logger = Logger.getLogger(DelongTcpHandle.class);
	
	@Override
	public List<CommitMsg> handle(Msg m) {
		String tmp = m.getMsg();
		try {
			List<String> info = Lists.newArrayList(Splitter.on(' ').split(tmp));
			String target = info.get(0);
			if (target != null && !target.isEmpty()) {
				CommitMsg cm  = new CommitMsg(m.getId());
				cm.setTarget(target);
				cm.setOutExt(info);
				return statListens.get(0).commit(cm);
				
			}
			
		} catch (Exception e) {
			logger.info(tmp);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
