package com.wbuve.graph.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestRE {

public static void printMatched(String regex, String source) {
   Pattern p = Pattern.compile(regex);
   Matcher m = p.matcher(source);
   while (m.find()) {
    for (int i = 0; i <= m.groupCount(); i++) {
     System.out.println(m.group(i));
    }
   }
}

	public static void main(String[] arg) {
		JSONObject json = new JSONObject("{\"defefg\": 8877}");
		System.out.println(json.getInt("defefg"));
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	    ctx.scan("com.wbuve.graph.handle.imp");
	    ctx.scan("com.wbuve.graph.stat");
	    ctx.refresh();
	    
		Object o1 = ctx.getBean("statDelongTotal");
		Object o2 = ctx.getBean("statDelongTotal");
		System.out.println(o1 == o2);
	}
}