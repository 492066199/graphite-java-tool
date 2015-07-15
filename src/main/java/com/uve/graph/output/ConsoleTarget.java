package com.uve.graph.output;

public class ConsoleTarget implements IOutputTarget {
	
	public boolean send(String string) {
		System.out.println(string);
		return true;
	}
}
