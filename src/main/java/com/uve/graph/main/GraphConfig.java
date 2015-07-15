package com.uve.graph.main;

import java.util.HashMap;
import java.util.Map;

public class GraphConfig {
	public static final Map<String, String[]> handleStr = new HashMap<String, String[]>();
	
	static{
		handleStr.put("com.uve.graph.handle.ErrorLogHandle", new String[]{"D:\\error-2015-07-14_00023"});
		handleStr.put("com.uve.graph.handle.AccessHandle", new String[]{"D:\\api.uve.mobile.sina.cn_access-2015-07-15_00117"});
	};
	
}
