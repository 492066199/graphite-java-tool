package com.wbuve.graph.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

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
	}
}