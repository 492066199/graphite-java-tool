package com.wbuve.graph.handle;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public abstract class TcpServerIn implements IHandleIn{
	private final Logger logger = Logger.getLogger(TcpServerIn.class);
	private String fdName;
	private int priority;
	
	private InetSocketAddress address;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	
	public void setFdName(String name){
		this.fdName = name;
	}
		
	public void load() {
		try {
			List<String> tmp = Lists.newArrayList(Splitter.on(':').omitEmptyStrings().split(this.fdName));
			String addr = tmp.get(0);
			int port = Integer.parseInt(tmp.get(1));
			this.selector = Selector.open();
			this.serverSocketChannel = ServerSocketChannel.open();
			this.address = new InetSocketAddress(addr, port);
			this.serverSocketChannel.socket().bind(this.address);
			this.serverSocketChannel.configureBlocking(false);
			this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			logger.info("Create Socket Server OK!");
		} catch (Exception e) {
			logger.info("Create Socket Server ERROR!");
			e.printStackTrace();
		}
	}
	
	public String readLog() {
		try {
			if (this.selector.selectNow() > 0) {
				Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey selectionKey = it.next();
					if (selectionKey.isAcceptable()) {
						serverSocketChannel = ((ServerSocketChannel) selectionKey.channel());
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						
						logger.info("Connected: " + socketChannel.socket().getRemoteSocketAddress());
					} else if (selectionKey.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
						return readLogFromTcpIn(socketChannel);
					}
					it.remove(); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public abstract String readLogFromTcpIn(SocketChannel socketChannel);

}
