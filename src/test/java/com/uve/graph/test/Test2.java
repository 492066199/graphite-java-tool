package com.uve.graph.test;

import com.uve.graph.util.TimeUtil;


public class Test2 {
	private static final long t = 1436976000000L - 1000 * 60 * 60 * 24 * 100;
	private static final long t1 = (1436976000000L - 1000 * 60 * 60 * 24 * 100L);
	
	public static void main(String[] args) {
		System.out.println(TimeUtil.isNotTheSameDay(System.currentTimeMillis()  - 1000 * 60 * 60 * 18));
//		System.out.println(1436976000000L - 1000 * 60 * 60 * 24 * 100L);
//		System.out.println(t);
//		System.out.println(t1);
	}
}
