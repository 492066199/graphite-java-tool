package com.wbuve.graph.xml;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.collect.Lists;
import com.wbuve.graph.handle.CombinationHandle;
import com.wbuve.graph.handle.IHandle;
import com.wbuve.graph.handle.IHandleIn;
import com.wbuve.graph.handle.IHandleOut;
import com.wbuve.graph.model.Msg;
import com.wbuve.graph.stat.IStatCalc;
import com.wbuve.graph.stat.IStatUtil;
import com.wbuve.graph.stat.StatListener;
import com.wbuve.graph.stat.StatUtil;

public class XmlParser extends DefaultHandler{
	
	private List<CombinationHandle> handles = null;
	private CombinationHandle handle = null;
	private String preTag = null;
	private IStatCalc curStat = null;
	private StatListener curStats = null;
	private final AnnotationConfigApplicationContext ctx;
	
	public XmlParser(AnnotationConfigApplicationContext ctx) {
		this.ctx = ctx;
	}

	public List<CombinationHandle> getHandles(){
		for(CombinationHandle tmp : handles){
			tmp.getIn().init();
			tmp.getHandle().init();
			tmp.getOut().init();
		}
		return handles;
	}
	
	@Override
	public void startDocument() throws SAXException {
		handles = Lists.newLinkedList();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		qName = qName.trim();
		switch (qName) {
		case "graphhandle":
			handle = new CombinationHandle();
			handle.setId(attributes.getValue("id"));
			break;
		case "in":
			IHandleIn in = (IHandleIn) ctx.getBean(attributes.getValue("class"));;
			handle.setIn(in);
			break;
		case "handle":
			IHandle h = (IHandle) ctx.getBean(attributes.getValue("class"));
			handle.setHandle(h);
			break;
		case "out":
			IHandleOut out = (IHandleOut) ctx.getBean(attributes.getValue("class"));
			handle.setOut(out);
			break;
		case "stat":
			curStat = (IStatCalc) ctx.getBean(attributes.getValue("class"));
			String prefix = attributes.getValue("prefix");
			if(prefix != null){
				curStat.setPrefix(prefix);
			}
			break;
		case "stats":
			curStats = new StatListener();
			Integer index  = Integer.parseInt(attributes.getValue("index"));
			handle.getHandle().addStat(index, curStats);
			break;
		default:
			break;
		}
		preTag = qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		qName = qName.trim();
		switch (qName) {
		case "graphhandle":
			handles.add(handle);
			handle = null;
			break;
		case "stat":
			curStat = null;
			break;
		case "stats":
			curStats = null;
		default:
			break;
		}
		preTag = null;
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			content = content.trim();
			switch (preTag) {
			case "name":
				handle.getIn().setFdName(content);
				break;
			case "priority":
				handle.getIn().setPriority(Integer.parseInt(content));
				break;
			case "stat":
				IStatUtil su = new StatUtil();
				su.setStatsecond(Integer.parseInt(content));
				su.setStatCalc(curStat);
				curStats.add(su);
				break;
			case "out":
				handle.getOut().setOutFdname(content);
				break;
			case "count":
				handle.setMsgQueue(new LinkedBlockingQueue<Msg>(Integer.parseInt(content)));
				break;
			default:
				break;
			}
		}
	}
}
