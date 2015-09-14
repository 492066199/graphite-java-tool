package com.wbuve.graph.stat;

import java.util.List;

import com.wbuve.graph.model.CommitMsg;

public interface IStatUtil {
	public void setStatCalc(IStatCalc statCalc);
	
	List<CommitMsg> statistics(CommitMsg msg);
	
	public boolean isOk(CommitMsg msg);

	public void setStatsecond(int parseInt);
}
