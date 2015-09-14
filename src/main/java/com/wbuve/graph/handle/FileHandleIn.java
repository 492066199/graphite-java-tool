package com.wbuve.graph.handle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public abstract class FileHandleIn implements IHandleIn{
	private final Logger logger = Logger.getLogger(FileHandleIn.class);
	private File dir;
	public File file;
	public FileReader fr;
	public BufferedReader br;
	public int failCount = 0;	
	
	public void load(String filename){
		if(filename == null)
			return;
        this.file = new File(filename); 
        logger.info(file);
        try {
			this.fr = new FileReader(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.br = new BufferedReader(this.fr);
	}
	
	public void init(){
		load(getFirstFileName());
	}
	
	public String readLog() {
		try {
			String tmp = br.readLine();
			if(tmp == null){
				if(this.failCount > 10000){
					this.failCount = 0;
					load(getNextFileName());
				}else {
					this.failCount = this.failCount + 1;
				}
				return null;
			}
			this.failCount = 0;    
			return tmp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract String getFirstFileName();
	
	public abstract String getNextFileName();

	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}
}
