package com.uve.graph.handle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.CommitMsg;
import com.uve.graph.model.Msg;
import com.uve.graph.output.IOutputTarget;
import com.uve.graph.output.OutputTargetFactory;
import com.uve.graph.util.TimeUtil;

public abstract class FileHandle implements IGraphHandle{
	private final Logger logger = Logger.getLogger(FileHandle.class);
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private final String dirname;
	private final String id;  
	private final GraphLaunch graphLaunch;
	private final Map<String, CommitMsg> curResultMap = Maps.newHashMap();
	
	public int num;
	public File file;
	
	public long lasttime;
	public FileReader fr;
	public BufferedReader br;
	public int failCount = 0;
	
	
	public String getId() {
		return id;
	}
	
	public FileHandle(String config, GraphLaunch graphLaunch) {
		this.dirname = config;
		this.id = config;
 		this.graphLaunch = graphLaunch; 
	}
	
	public FileHandle() {
		this.dirname = "FACTORY";
		this.id = "FACTORY";
		this.graphLaunch = null;
	}
	
	public void load(){
        File dir  = new File(dirname);
        String[] filelist = dir.list();
        String filename = null;
        int max = 0;
        for (int i = 0; i < filelist.length; i++) {
        	 String[] ss = filelist[i].split("_");
        	 int count = ss.length - 1;
        	 String tmp = ss[count];
        	 if(tmp.charAt(0) <= 9 && tmp.charAt(0) >= 0){
        		 int num = Integer.parseInt(tmp);
        		 if(max < num){
        			 max = num;
        			 filename = filelist[i];
        		 }
        	 }
        }
        
        logger.info("load first file:" + filename);
        
        this.file = new File(filename); 
		try {
			this.fr = new FileReader(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.br = new BufferedReader(this.fr);
		lasttime = System.currentTimeMillis();
		this.num = max;
	}
	
	public void readLog() {
		try {
			String tmp = br.readLine();
			if(tmp == null){
				if(this.failCount > 10000){
					this.failCount = 0;
					tryLoadNewFile();
				}else {
					this.failCount = this.failCount + 1;
				}
				return;
			}
		
			this.failCount = 0;    
			Msg msg = new Msg(this.id, tmp);
			this.graphLaunch.msgQueue.put(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private boolean tryLoadNewFile() {
		String filePrefix = getFilePrefix();
		String time = format.format(new Date());;
		int num = 0; 
		if(!TimeUtil.isNotTheSameDay(System.currentTimeMillis())){ //不在同一天
			num = this.num + 1; 
		}
		String numStr = String.format("%05d", num);
		File tmp = new File(filePrefix + "-" + time + "_" + numStr);
		if (tmp.exists()) {
			try {
				this.file = tmp;
				this.fr = new FileReader(this.file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			this.br = new BufferedReader(this.fr);
			this.lasttime = System.currentTimeMillis();
			this.num = num;
			logger.info("load reload file:" + file.getName());
			return true;
		} else {
			return false;
		}
	}

	public abstract String getFilePrefix();

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
