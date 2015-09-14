package com.wbuve.graph.handle.imp;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wbuve.graph.handle.FileHandleIn;
import com.wbuve.graph.util.TimeUtil;

@Scope("prototype")
@Component("commonFileHandleIn")
public class CommonFileHandleIn extends FileHandleIn{
	private final Logger logger = Logger.getLogger(CommonFileHandleIn.class); 
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private String dirname;
	private final String suffix = "00000";
	private String filePrefix;
	private int priority;
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setFdName(String name) {
		this.dirname = name;
	}

	@Override
	public String getFirstFileName() {
		File dir  = new File(dirname);
        logger.info("load dir:" + dirname);
        String[] filelist = dir.list();			
        logger.info("dir file num:" + filelist.length);
        String filename = null;
        int max = 0;
        String tmpDate = format.format(new Date());
        logger.info("file date:" + tmpDate);
        
		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].indexOf(tmpDate) > 0) {			
				String[] ss = filelist[i].split("_");		
				int count = ss.length - 1;					
				String tmp = ss[count];						
				if (tmp.charAt(0) <= '9' && tmp.charAt(0) >= '0') {
					int num = Integer.parseInt(tmp);		
					if (max <= num) {			
						max = num;
						filename = filelist[i];
					}
				}
			}
		}
    
        logger.info("load first file:" + filename);	
        
        this.filePrefix = filename.substring(0, filename.length() - "-2015-07-17_00135".length());		
        logger.info("file Prefix:" + this.filePrefix);
        
		return this.dirname + "/" + filename;
	}

	@Override
	public String getNextFileName() {
		String tmpFileName = this.file.getName(); 
		tmpFileName = tmpFileName.substring(filePrefix.length() + 1, tmpFileName.length());
		String tmpData[] = tmpFileName.split("_"); 													
		long lasttime = 0;
		try {
			lasttime = format.parse(tmpData[0]).getTime(); 
		} catch (ParseException e1) {
			lasttime = System.currentTimeMillis();
			e1.printStackTrace();
		}

		int num = Integer.parseInt(tmpData[1]);
		num = num + 1;
		String numStr = String.format("%05d", num);
		String nextFileName = dirname + "/" + this.filePrefix + "-" + tmpData[0] + "_" + numStr;
		File tmp = new File(nextFileName);
		if (!tmp.exists()) {
			long nexttime = lasttime + TimeUtil.DATE;
			nextFileName = dirname + "/" + this.filePrefix + "-" + format.format(new Date(nexttime)) + "_" + suffix;
			tmp = new File(nextFileName);
			if (!tmp.exists()) {
				return null;
			}
		}
		return nextFileName;
	}


}
