package com.uve.graph.handle;

import java.text.SimpleDateFormat;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.Msg;


public class ErrorLogHandle extends FileHandle{
	public final boolean FACTORY_SWITCH; 
	private final SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	private final int outputId = 1;
	
	public ErrorLogHandle() {
		FACTORY_SWITCH = true;
	}
	
	
	public ErrorLogHandle(String config, GraphLaunch graphLaunch) {
		super(config, graphLaunch);
		FACTORY_SWITCH = false;
	}
	
	
	
	public void handleMsg(Msg msg) {	
		String ip = "";
		long time = 0;
		String target = "";		
		
		String tmp = msg.getMsg();
		
		if(tmp == null || tmp.indexOf("Connection timed out") < 0){
			return;
		}
		
		List<String> ps = Lists.newArrayList(Splitter.on(',').split(tmp));
		for(String s : ps){
			 if(s.indexOf('|') >= 0){
				 try{
                        String[] _s = s.split("\\|");
				        ip = _s[0]; 
				        String[] __s = _s[1].split(" ");
				        String date = __s[0] + " " + __s[1];
					    time= format.parse(date).getTime() / 1000;
    				} catch (Exception e) {
				    	//result.setTime(System.currentTimeMillis());
		    			e.printStackTrace();
                        return;
	    			}
			 }
			 
			 if(s.indexOf("request: \"") >= 0 && s.indexOf("subrequest: \"") < 0){
				 try {
					 int end = s.indexOf("HTTP/1.");
					 int start = s.indexOf("/");
					 int k = s.indexOf('?');
					 
					 if(k >= 0){
						 end = k;
					 }
					 
					 target = s.substring(start + 1, end).trim();
					 
					 target = target.replaceAll("/", ".");
					 
				 } catch (Exception e) {
						System.out.println("DEBUG:" + s);
				 }
			 }
		}
	        target = "uve_monitor.error." + target;	
		super.statistics(ip, time, target, null);
	}

	@Override
	public IGraphHandle newInstance(String str, GraphLaunch graphLaunch) {
		ErrorLogHandle handle = new ErrorLogHandle(str, graphLaunch);
		handle.load();
		return handle;
	}

	public int getPriority() {
		return 7;
	}


	public int getOutputId() {
		return outputId;
	}


	@Override
	public String getFilePrefix() {
		return "error";
	}
}
