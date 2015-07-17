package com.uve.graph.test;

import java.io.File;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.uve.graph.handle.FileHandle;

public class Test1 {
	public Test1(int x, int y) {
		// TODO Auto-generated constructor stub
	}
	
	//"正则转移字符是//"
	
	public static void main(String[] args) {
		String tmp = "10.13.2.40|172.16.89.234 - - [15/Jul/2015:14:08:39 +0800] \"GET /uve/service/profile?uve_target=3rd&target=profile&uid=3888072243&from=1053095010&host_uid=1285846970&page=1&lang=zh_CN&ip=39.176.10.202 HTTP/1.1\" - ^200^ 84 \"-\" \"-\" \"-\" ^\"0.005\"^ ^\"-\"^";		
		Pattern pattern = Pattern.compile("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s\"[A-Z]+\\s([\\w|\\d|/]+)[\\?]{0,1}");
		//System.out.println("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]");
//		Pattern pattern = Pattern.compile("([A-Z]{3,4})");
//		String tmp = "(GET)";  
		//System.out.println(tmp);
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			System.out.println("#" + matcher.group(1) + "#");
			System.out.println("#" + matcher.group(2) + "#");
			System.out.println("#" + matcher.group(3) + "#");
			System.out.println("#" + matcher.group(4) + "#");
		}
		
		String k = "15/Jul/2015:14:08:39 +0800";
		SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
		try {
			Date date = format.parse(k);
			System.out.println(date.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s = "api.uve.mobile.sina.cn_access_current";
		String[] t = s.split("_");
		System.out.println(t.length);
		
		//System.out.println(k1);
		System.out.println("fafff".charAt(0));
		
		SimpleDateFormat format123 = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format123.format(new Date(System.currentTimeMillis())));
		System.out.println(String.format("%05d", 99));
		File s111 = new File("def");
		System.out.println(s111.exists());
	}
}
