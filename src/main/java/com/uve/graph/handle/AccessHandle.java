package com.uve.graph.handle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uve.graph.main.GraphLaunch;
import com.uve.graph.model.Msg;



public class AccessHandle extends FileHandle{
	public final boolean FACTORY_SWITCH; 
	private final Pattern pattern = Pattern.compile("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s\"[A-Z]+\\s([\\w|\\d|/]+)[\\?]{0,1}");
	private final SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);;
	private final int outputId = 0;
	
	public AccessHandle(String config, GraphLaunch graphLaunch) {
		super(config, graphLaunch);
		FACTORY_SWITCH = false;
	}

	public AccessHandle() {
		FACTORY_SWITCH = true;
	}
	
	public void handleMsg(Msg msg) {
		String tmp = msg.getMsg();
		if(tmp == null)
			return;
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			String ip = matcher.group(1);
			String timeStr = matcher.group(3);
			String target = matcher.group(4);
			
			long time;
			try {
				time = format.parse(timeStr).getTime();
			} catch (ParseException e1) {
				e1.printStackTrace();
				return;
			}
			
			target = target.replaceAll("/", ".");
			target = "uve_monitor.access" + target;
			super.statistics(ip, time, target, null);
		}		
	}

	@Override
	public IGraphHandle newInstance(String str, GraphLaunch graphLaunch) {
		AccessHandle handle = new AccessHandle(str, graphLaunch);
		handle.load();
		return handle;
	}

	public int getPriority() {
		return 17;
	}


	public int getOutputId() {
		return outputId;
	}
}
