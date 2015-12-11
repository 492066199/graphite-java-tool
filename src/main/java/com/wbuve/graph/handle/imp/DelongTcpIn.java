package com.wbuve.graph.handle.imp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wbuve.graph.annotation.Component;
import com.wbuve.graph.handle.TcpServerIn;

@Component("delongTcpIn")
public class DelongTcpIn extends TcpServerIn{
	private final Logger logger = Logger.getLogger(DelongTcpIn.class);
	private final Map<String, String> tailMap = Maps.newHashMap();
	
	@Override
	public String readLogFromTcpIn(SocketChannel socketChannel) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);		
		try {
			String addr = socketChannel.socket().getRemoteSocketAddress().toString();
			String tail = this.tailMap.get(addr); 
			
			if(tail == null){
				tail = "";
			}
			int c = 0;
			while ((c = socketChannel.read(buffer)) > 0) { 
				buffer.flip();
				byte[] dst = new byte[buffer.limit()];
				buffer.get(dst);

				String tmp = new String(dst);
				
				List<String> tmps= Lists.newArrayList(Splitter.on('\n').split(tmp));
				int count = tmps.size();
				for(int i = 0; i < count; i++){
					if(i == 0){
						return tail + tmps.get(0);
					}else if(i < count - 1){
						return tmps.get(i);
					}else {
						tail = tmps.get(i);
					}
				}
			}
			
			if(c == -1){
				socketChannel.close();
				logger.info("remote close the connect:" + addr);
			}
				
			this.tailMap.put(addr, tail);
		} catch (IOException e) {
			logger.info("read data error");
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public void init() {
		load();
	}
}
