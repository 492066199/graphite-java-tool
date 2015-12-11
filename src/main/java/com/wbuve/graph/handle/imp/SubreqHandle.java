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

	
@Component("subreqHandle")
public class SubreqHandle extends AbstractHandle{
	private final SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
	private final Logger logger = Logger.getLogger(SubreqHandle.class); 
	
	@Override
	public List<CommitMsg> handle(Msg msg) {
		String tmp = msg.getMsg();
        long time;
        Double t;
        String target;

        try{
            List<String> info = Lists.newArrayList(Splitter.on(' ').split(tmp));
            int count = info.size();
            if (count < 8){
                logger.info("error subreq log" + tmp);
            }
            String tmpTime = info.get(0) + " " + info.get(1);
            int start = tmpTime.indexOf("[");
            int end = tmpTime.indexOf("]");
            if (start > end){
                logger.info("loction error!!");
            }
            tmpTime = tmpTime.substring(start + 1, end);
            time = format.parse(tmpTime).getTime();

            t = Double.parseDouble(info.get(4));
            
            
            target = info.get(2);
            end = target.length();
            int phpc = target.indexOf(".php");
            if (phpc > 0 && phpc < end){
                end = phpc;
            }
            target = target.substring(0, end);
			target = target.replaceAll("/", ".");

			CommitMsg cmg = new CommitMsg(msg.getId());
		
			cmg.setTarget(target);
			cmg.setTime(time / 1000);
			cmg.setOutExt(t);
			
			List<CommitMsg> result = Lists.newArrayList();
			for(StatListener s : statListens){
				result.addAll(s.commit(cmg));
			}

			return result;			
		}catch (Exception e){
            logger.info(tmp);
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public void init() {
		
	}
}
