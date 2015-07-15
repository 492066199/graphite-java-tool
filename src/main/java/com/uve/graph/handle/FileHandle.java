package com.uve.graph.handle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.CommitMsg;
import com.uve.graph.model.Msg;
import com.uve.graph.output.IOutputTarget;
import com.uve.graph.output.OutputTargetFactory;

public abstract class FileHandle implements IGraphHandle{
	public String filename;
	public String id;  
	public final GraphLaunch graphLaunch;
	public File file;
	public FileReader fr;
	public BufferedReader br;
	public int failCount = 0;
	private final Map<String, CommitMsg> curResultMap = Maps.newHashMap();
	
	public String getId() {
		return id;
	}
	
	public FileHandle(String config, GraphLaunch graphLaunch) {
		this.filename = config;
		this.id = config;
 		this.graphLaunch = graphLaunch; 
	}
	
	public FileHandle() {
		this.filename = "FACTORY";
		this.id = "FACTORY";
		this.graphLaunch = null;
	}
	
	public void load(){
		file = new File(this.filename); 
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}
	
	public void readLog() {
		try {
			String tmp = br.readLine();
			if(tmp == null){
				if(failCount > 1000){
					failCount = 0;
					if(file.length() > 1073741824L){
						load();
					}else {
						return;
					}
				}else {
					failCount = failCount + 1;
				}
				return;
			}
			
			Msg msg = new Msg(this.id, tmp);
			this.graphLaunch.msgQueue.put(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public List<? extends IGraphHandle> setup(String[] strings,
			GraphLaunch graphLaunch) {
		List<IGraphHandle> handles = new LinkedList<IGraphHandle>();
		int count = strings.length;
		for(int i = 0; i < count; i++){
			handles.add(newInstance(strings[i], graphLaunch));
		}
		return handles;
	}
	
	public boolean sendMsgToTarget(CommitMsg msg) {
		OutputTargetFactory factory = OutputTargetFactory.getInstance();
		IOutputTarget target = factory.get(this.getOutputId());
		target.send(msg.getTarget() +" "+ msg.getCount() + " "+ msg.getTime());
		return true;
	}

	public boolean statistics(String ip, long time, String target, String Ext) {
		CommitMsg curResult = curResultMap.get(target);
		if(curResult == null){
			curResult = new CommitMsg(this.getId());
			curResult.setIp(ip);
			curResult.setTarget(target);
			curResult.setTime(time);
			curResult.setCount(1);
			curResultMap.put(target, curResult);
		}
		
		if(curResult.getTime() < time){
			try {
				this.graphLaunch.resultQueue.put(curResult);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			curResult = new CommitMsg(this.getId());
			curResult.setIp(ip);
			curResult.setTarget(target);
			curResult.setTime(time);
			curResult.setCount(1);
			curResultMap.put(target, curResult);
		}else {
			curResult.setCount(curResult.getCount() + 1);
		}
		return true;
	}
	
	
	public abstract IGraphHandle newInstance(String str, GraphLaunch graphLaunch);
}
