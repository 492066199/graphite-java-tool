package com.wbuve.graph.main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GraphMain {
	public static void main(String[] args) {
		GraphHandleStart ghs = new GraphHandleStart();
		try {
			ghs.init();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ghs.schedule();
		ghs.runReadLogFile();
	}
}
