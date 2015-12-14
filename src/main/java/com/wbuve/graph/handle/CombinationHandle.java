package com.wbuve.graph.handle;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.wbuve.graph.main.GraphHandleStart;
import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.model.Msg;
import com.wbuve.graph.stat.StatListener;

public class CombinationHandle implements IGraphHandle{
	private String id;

	private IHandleIn in;
	
	private IHandleOut out;
	
	private IHandle handle;
	
	private LinkedBlockingQueue<Msg> msgQueue;	
	
	public boolean handleIn() {
		String tmp = in.readLog();
		if(tmp == null){
			return false;
		}else{
			Msg m = new Msg(this.id, tmp);
			try {
				this.msgQueue.put(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	
	public boolean handleMsg(){
		try {
			Msg m = this.msgQueue.take();
			List<CommitMsg> cms = this.handle.handle(m);
			if(cms == null || cms.size() == 0){
				return false;
			}
			GraphHandleStart.outPutQueue.put(cms);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public boolean handleOut(List<CommitMsg> cms) {
		
		List<CommitMsg> result = Lists.newArrayList();
		List<StatListener> statListens =  this.handle.getStatListens();
		for(CommitMsg cm : cms){
			for(StatListener s :statListens){
				result.addAll(s.commit(cm));
			}
		}

		if(result == null || result.size() == 0){
			return false;
		}
		
		for(CommitMsg c : result){
			String tmp = this.out.CommitToString(c);
			out.send(tmp);
		}
		
		return true;
	}
	

	public int getPriority() {
		return this.in.getPriority();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IHandleIn getIn() {
		return in;
	}

	public void setIn(IHandleIn in) {
		this.in = in;
	}

	public IHandleOut getOut() {
		return out;
	}

	public void setOut(IHandleOut out) {
		this.out = out;
	}

	public IHandle getHandle() {
		return handle;
	}

	public void setHandle(IHandle handle) {
		this.handle = handle;
	}

	public LinkedBlockingQueue<Msg> getMsgQueue() {
		return msgQueue;
	}

	public void setMsgQueue(LinkedBlockingQueue<Msg> msgQueue) {
		this.msgQueue = msgQueue;
	}
}
