package com.wbuve.graph.handle.imp;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.AbstractHandle;
import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;
import com.wbuve.graph.stat.StatListener;

@Component("accessFileHandle")
public class AccessFileHandle extends AbstractHandle{
	private final SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
	private final Logger logger = Logger.getLogger(AccessFileHandle.class);
	
	public List<CommitMsg> handle(Msg msg) {
		String tmp = msg.getMsg();
		long time = 0;
		Double t = null;
		String target = "";
		try {
			List<String> info = Lists.newArrayList(Splitter.on(' ').split(tmp));
			int count = info.size();
			if(count < 16){
				logger.info("error access log:" + tmp);
			}
			String timeStr = info.get(3) + " " + info.get(4);
			time = format.parse(timeStr.substring(1, timeStr.length() - 1)).getTime();

			String tStr = info.get(count - 2);
			t = Double.parseDouble(tStr.substring(2, tStr.length() - 2));

			target = info.get(6);
			int start = 1;
			int end = target.length();
			if (target.indexOf('?') >= 0) {
				end = target.indexOf('?');
			}
			int phpc = target.indexOf(".php");
			if (phpc >= 0 && phpc < end) {
				end = phpc;
			}
			target = target.substring(start, end);
			target = target.replace('/', '.');
			CommitMsg cm  = new CommitMsg(msg.getId());
			
			cm.setTarget(target);
			cm.setTime(time / 1000);
			cm.setOutExt(t);
			 
			List<CommitMsg> result = Lists.newArrayList();
			for(StatListener s : statListens){
				result.addAll(s.commit(cm));
			}

			return result;
		} catch (Exception e) {
            logger.info("error access log:" + tmp);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
