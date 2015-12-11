package com.wbuve.graph.handle.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.AbstractHandle;
import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;

@Component("errorFileHandle")
public class ErrorFileHandle extends AbstractHandle{
	private final Logger logger = Logger.getLogger(ErrorFileHandle.class);
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Pattern pattern = Pattern.compile("([\\d|\\.]+)\\|([\\d|\\s|:|/]+)\\[error\\]\\s[\\d|#|:|\\*|\\w|\\s]+\\(110:\\sConnection\\stimed\\sout\\)[^,]+,[^,]+,[^,]+,[^,]+,"
			+ "\\ssubrequest:\\s" + "\"([^\"]+)\",");
	
	@Override
	public List<CommitMsg> handle(Msg msg) {
		String tmp = msg.getMsg();
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			String ip = matcher.group(1);
			String timeStr = matcher.group(2);
			String target = matcher.group(3);
			
			logger.debug("ip:" + ip);
			logger.debug("time:" + timeStr);
			logger.debug("target:" + target);
			
			long time;
			try {
				time = format.parse(timeStr).getTime();
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
			
			target = target.replaceAll("\\.php", "");
			target = target.replaceAll("/", ".");
			target = target.substring(1, target.length());
			CommitMsg cm = new CommitMsg(msg.getId());
			cm.setTarget(target);
			cm.setOutExt(null);
			cm.setTime(time/1000);
			
			return statListens.get(0).commit(cm);
		}else{
            //logger.info(tmp);
        }        
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
}
