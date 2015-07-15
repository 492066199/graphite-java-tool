package com.uve.graph.main;

import com.uve.graph.output.ResultMsgOutput;

public class GraphMain {
	public static void main(String[] args) {
		ResultMsgOutput resultMsgOutput = new ResultMsgOutput();
		GraphLaunch graphLaunch = new GraphLaunch(resultMsgOutput);
		resultMsgOutput.setGraphLauch(graphLaunch);
		
		graphLaunch.init();
		
		graphLaunch.schedule();
		
		graphLaunch.runReadLogFile();
	}
}
