package com.uve.graph.output;

import com.uve.graph.handle.IGraphHandle;
import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.CommitMsg;

public class ResultMsgOutput implements Runnable {
	private volatile GraphLaunch graphLauch;
	
	public void setGraphLauch(GraphLaunch graphLaunch) {
		this.graphLauch = graphLaunch;
	}
	
	public void run() {
		try {
			while (true) {
				CommitMsg msg = graphLauch.resultQueue.take();
				IGraphHandle handle =  this.graphLauch.maps.get(msg.getId());
				handle.sendMsgToTarget(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
