package com.uve.graph.output;

import java.util.Map;

import com.google.common.collect.Maps;

public class OutputTargetFactory {
	private static volatile OutputTargetFactory outputTargetFactory;
	private final Map<Integer, IOutputTarget>  maps = Maps.newHashMap();  
	
	private OutputTargetFactory(){
		
	}
	public static OutputTargetFactory getInstance() {
		if(outputTargetFactory == null){
			synchronized (OutputTargetFactory.class) {
				if(outputTargetFactory == null){
					OutputTargetFactory tmp = new OutputTargetFactory();
					tmp.maps.put(0, new ConsoleTarget());
					tmp.maps.put(1, new GraphTarget() );
					outputTargetFactory = tmp;
				}
			}
		}
		return outputTargetFactory;
	}

	public IOutputTarget get(int outputId) {
		return maps.get(outputId);
	}
}
