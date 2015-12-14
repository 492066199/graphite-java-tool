package com.wbuve.graph.stat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wbuve.graph.model.CommitMsg;

public class StatUtil implements IStatUtil{
	private final Logger logger = Logger.getLogger(StatUtil.class);
	private int statsecond = 150;
	private final Map<String, Map<Long , CommitMsg>> curResultMap = Maps.newHashMap();
	private final Map<String, Long> maxCurMap = Maps.newHashMap();
	private IStatCalc statCalc;

	public void setStatsecond(int statsecond) {
		this.statsecond = statsecond;
	}

	public void setStatCalc(IStatCalc statCalc) {
		this.statCalc = statCalc;
	}

	public List<CommitMsg> statistics(CommitMsg msg) {
		List<CommitMsg> resultCM = Lists.newArrayList();
		
		String target = statCalc.preInitCount(msg);	
		String id = msg.getId();
		long time = msg.getTime();
		Object Ext = msg.getOutExt();
		//never use msg below!!!
		
		Map<Long, CommitMsg> curCommitMsgMap = curResultMap.get(target);
		if(curCommitMsgMap == null){    
			logger.info(target + " init!");
			curCommitMsgMap = new HashMap<Long, CommitMsg>();
			curResultMap.put(target, curCommitMsgMap);
		}
		
		Long maxCur = maxCurMap.get(target);
		if(maxCur == null ){
			maxCurMap.put(target, time);
			maxCur = time;
		}
		long minCur = maxCur - statsecond;  
		
		if(time <= minCur){                 
			return null;
		}else if (time > minCur && time <= maxCur) { 
			CommitMsg curResult = curCommitMsgMap.get(time);
			if(curResult == null){
	 			curResult = new CommitMsg(id); 
				curResult.setId(id);
				curResult.setTarget(target);
				curResult.setTime(time);
				statCalc.initCount(Ext, curResult);
				curCommitMsgMap.put(time, curResult);
			}else {
				statCalc.calcCount(Ext, curResult);
			}
		}else if(time > maxCur){//
			if(time == Long.MAX_VALUE || time <= 0){
				logger.info("I have catch you:" + time);
				return null;
			}
			
			long newMinCur = time - statsecond;
			long newMaxCur = time;
			if(newMinCur - minCur > 100000000L){
				logger.info("time too long:" + time + "," + minCur + ',' + newMinCur);
				return null;
			}
			
			for(long i = minCur + 1; i <= newMinCur; i++){
				CommitMsg curResult = curCommitMsgMap.get(i);
				if(curResult != null){
				
					List<CommitMsg> cs = statCalc.preSendCount(curResult);
					if(cs == null){
						resultCM.add(curResult);
					}else {
						resultCM.addAll(cs);
					}
					
					curCommitMsgMap.remove(i);
				}
			}
			
			CommitMsg curResult1 = new CommitMsg(id);
			curResult1.setId(id);
			curResult1.setTarget(target);
			curResult1.setTime(time);
			statCalc.initCount(Ext, curResult1);
            curCommitMsgMap.put(time, curResult1);
      
			maxCurMap.put(target, newMaxCur);
		}
		
		return resultCM;
	}
	
	@Override
	public boolean isOk(CommitMsg msg) {
		return statCalc.isOk(msg);
	}
}
