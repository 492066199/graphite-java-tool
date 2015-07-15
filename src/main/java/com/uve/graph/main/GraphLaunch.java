package com.uve.graph.main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.uve.graph.handle.IGraphHandle;
import com.uve.graph.model.CommitMsg;
import com.uve.graph.model.Msg;
import com.uve.graph.output.ResultMsgOutput;

public class GraphLaunch implements Runnable{
	public final List<IGraphHandle> handles = new LinkedList<IGraphHandle>();
	public final Map<String, IGraphHandle> maps = new HashMap<String, IGraphHandle>();
	public final TreeMap<Integer, IGraphHandle> treeMaps = new TreeMap<Integer, IGraphHandle>();
	
	public final ResultMsgOutput resultMsgOutput;
	public final BlockingQueue<Msg>  msgQueue = new LinkedBlockingQueue<Msg>();
	public final BlockingQueue<CommitMsg> resultQueue = new LinkedBlockingQueue<CommitMsg>();
	public int sumPriority = 0;
	
	public GraphLaunch(ResultMsgOutput resultMsgOutput) {
		this.resultMsgOutput = resultMsgOutput;
	}

	public void init(){
		Set<Entry<String, String[]>> sets = GraphConfig.handleStr.entrySet();
		try {
			for (Iterator<Entry<String, String[]>> iterator = sets.iterator(); iterator.hasNext();) {
				Entry<String, String[]> entry = (Entry<String, String[]>) iterator.next();				
				@SuppressWarnings("unchecked")
				Class<IGraphHandle> handleClass = (Class<IGraphHandle>) Class.forName(entry.getKey());
				IGraphHandle handle = handleClass.newInstance();				
				handles.addAll(handle.setup(entry.getValue(), this));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		int tmpPriority = 0;
		for(IGraphHandle handle : handles){
			maps.put(handle.getId(), handle);
			tmpPriority = handle.getPriority();
			sumPriority = sumPriority + tmpPriority;
			treeMaps.put(sumPriority, handle);			
		}
	}

	private void startHandleMsg() {
		try {
			while (true) {
				Msg msg = msgQueue.take();
				IGraphHandle handle = maps.get(msg.getId());
				handle.handleMsg(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	public void run() {
		startHandleMsg();
	}
	
	public void schedule(){
		new Thread(this).start();
		new Thread(resultMsgOutput).start();
	}

	public void runReadLogFile() {
		Random r = new Random();
		while (true) {
			int p = r.nextInt(sumPriority);
			IGraphHandle handle = treeMaps.higherEntry(p).getValue();
			handle.readLog();
		}
	}
}
