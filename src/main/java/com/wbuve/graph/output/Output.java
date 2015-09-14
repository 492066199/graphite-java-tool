package com.wbuve.graph.output;

import java.util.List;

import com.wbuve.graph.handle.IGraphHandle;
import com.wbuve.graph.main.GraphHandleStart;
import com.wbuve.graph.model.CommitMsg;

public class Output implements Runnable {
	public void run() {
		while (true) {
			List<CommitMsg> cms = null;
			
			try {
				cms = GraphHandleStart.outPutQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(cms == null || cms.size() == 0){
				continue;
			}
			String id = cms.get(0).getId();
			IGraphHandle handle =  GraphHandleStart.maps.get(id);
			handle.handleOut(cms);
		}
	}
}
