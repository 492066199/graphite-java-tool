package com.wbuve.graph.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import com.wbuve.graph.handle.IGraphHandle;
import com.wbuve.graph.model.CommitMsg;
import com.wbuve.graph.output.Output;
import com.wbuve.graph.xml.XmlParser;

public class GraphHandleStart {
	private final Logger logger = Logger.getLogger(GraphHandleStart.class); 
	public static LinkedBlockingQueue<List<CommitMsg>> outPutQueue = new LinkedBlockingQueue<List<CommitMsg>>(250);
	public static List<? extends IGraphHandle> handles = null;
	public static final Map<String, IGraphHandle> maps = new HashMap<String, IGraphHandle>();
	public static final TreeMap<Integer, IGraphHandle> treeMaps = new TreeMap<Integer, IGraphHandle>();
	public static int sumPriority = 0; 

	public void init() throws ParserConfigurationException, SAXException, IOException{
        logger.info("begin init handle...");	
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("handle.xml"); 
        SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.scan("com.wbuve.graph.handle.imp");
	    ctx.scan("com.wbuve.graph.stat");
	    ctx.refresh();
	    
		XmlParser parse = new XmlParser(ctx);
		parser.parse(input, parse);
		
		handles = parse.getHandles();
		
		int tmpPriority = 0;
        logger.info("load handle num:" + handles.size());		
		for(IGraphHandle handle : handles){
			maps.put(handle.getId(), handle);
			tmpPriority = handle.getPriority();
			sumPriority = sumPriority + tmpPriority;
			treeMaps.put(sumPriority, handle);			
		}
	}
	
	public void schedule(){
		for(final IGraphHandle handle : handles){
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						handle.handleMsg();
					}
				}
			}).start();
		}
		new Thread(new Output()).start();
	}

	public void runReadLogFile() {
		Random r = new Random();      			
		while (true) {
			int p = r.nextInt(sumPriority);		
			IGraphHandle handle = treeMaps.higherEntry(p).getValue();
			handle.handleIn();
		}
	}
}
