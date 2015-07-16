package com.uve.graph.util;


public class TimeUtil {
	private static final long BASE = 1428336000000L;
	private static final long DATE = 1000 * 60 * 60 * 24L;
	
	public static boolean isNotTheSameDay(long last){
		long count = (last - BASE) / DATE;
		long count1 = (System.currentTimeMillis() - BASE) / DATE;
        //System.out.println(count + ":" + count1);
		return count != count1;
	}
}
