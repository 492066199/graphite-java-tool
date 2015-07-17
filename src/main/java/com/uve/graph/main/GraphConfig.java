package com.uve.graph.main;

import java.util.HashMap;
import java.util.Map;

public class GraphConfig {
	public static final Map<String, String[]> handleStr = new HashMap<String, String[]>();
	
	static{
		handleStr.put("com.uve.graph.handle.ErrorLogHandle", new String[]{"/data0/scribe_log/api.uve.mobile.sina.cn/error"});
		handleStr.put("com.uve.graph.handle.AccessHandle", new String[]{"/data0/scribe_log/api.uve.mobile.sina.cn/access"});
	};
	
}
