package com.wbuve.graph.annotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ComponentScanCtx {
	private Set<Class<?>> clazzs = Sets.newHashSet();  
	private HashMap<String, Class<?>> maps = Maps.newHashMap();
	private ClassLoader loader = Thread.currentThread().getContextClassLoader();
	private final Logger logger = Logger.getLogger(ComponentScanCtx.class);
	
	public void scan(String packageName) {
		findFileClass(packageName);
		filterComponent();
		logger.info("create bean ctx success:" + packageName);
	}
	
	private void filterComponent(){
		for(Class<?> clazz : clazzs){
			Component c = clazz.getAnnotation(Component.class);
		    System.out.println(clazz.getName() + "---->");
			if(c != null){
				maps.put(c.value(), clazz);
				System.out.println(clazz.getName() + "---->" + c.value());
			}
		}
	}   
	
    private void findFileClass(String packName){
        String packageDirName =packName.replace('.', '/');
        try {
        	Enumeration<URL> dirs = loader.getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    getFileClass(packName,filePath);
                }else if("jar".equals(protocol)){
                    JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile(); 
                    getJarClass(jarFile, packageDirName);
                    logger.info("create bean in package path:" + packageDirName);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    
	private void getFileClass(String packName, String filePath) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}

		File[] dirFiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				boolean acceptDir = file.isDirectory();
				boolean acceptClass = file.getName().endsWith(".class");
				return acceptDir || acceptClass;
			}
		});

		for (File file : dirFiles) {
			String fileName = file.getName();
			if (file.isDirectory()) {
				getFileClass(packName + "." + fileName, file.getAbsolutePath());
			} else {
				int dotIndex = fileName.indexOf('.');
				int dolIndex = fileName.indexOf('$');
				int index = dotIndex;
				if (dolIndex != -1) {
					index = dolIndex;
				}
				String className = fileName.substring(0, index);
				className = packName + "." + className; 
				try {
					Class<?> clazz = loader.loadClass(className);
					clazzs.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getJarClass(JarFile jarFile, String filepath)
			throws IOException {
		List<JarEntry> jarEntryList = Lists.newArrayList();
		Enumeration<JarEntry> enumes = jarFile.entries();
		while (enumes.hasMoreElements()) {
			JarEntry entry = (JarEntry) enumes.nextElement();
			String name = entry.getName();
			if (name.startsWith(filepath) && name.endsWith(".class")) {
				jarEntryList.add(entry);
			}
		}
		for (JarEntry entry : jarEntryList) {
			String fileName = entry.getName();
			String className = fileName.replace('/', '.');
			int dotIndex = fileName.indexOf('.');
			int dolIndex = fileName.indexOf('$');
			int index = dotIndex;
			if (dolIndex != -1) {
				index = dolIndex;
			}
			className = className.substring(0, index);
			try {
				clazzs.add(loader.loadClass(className));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object getBean(String value) {
		Class<?> c = maps.get(value);
		Object o;
		try {
			o = Class.forName(c.getName());
			return o;
		} catch (ClassNotFoundException e) {
			logger.error("can't create object: " + value);
			e.printStackTrace();
		}
		return null;
	}
}
