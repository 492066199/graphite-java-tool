package com.wbuve.graph.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class Test1 {
	
	@Test
	public void testAccessHandle() {
		String tmp = "10.13.2.40|weibo_v4@sina.com - - [15/Jul/2015:14:08:39 +0800] \"GET /uve/service/profile?uve_target=3rd&target=profile&uid=3888072243&from=1053095010&host_uid=1285846970&page=1&lang=zh_CN&ip=39.176.10.202 HTTP/1.1\" - ^200^ 84 \"-\" \"-\" \"-\" ^\"0.005\"^ ^\"-\"^";		
		Pattern pattern = Pattern.compile("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s\"[A-Z]+\\s([\\w|\\d|/]+)[\\?]{0,1}");
		//System.out.println("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]");
//		Pattern pattern = Pattern.compile("([A-Z]{3,4})");
//		String tmp = "(GET)";  
//		System.out.println(tmp);
		Matcher matcher = pattern.matcher(tmp);
	
		if(matcher.find()){
			System.out.println("#" + matcher.group(0) +"#");
			System.out.println("# ip: " + matcher.group(1) + "#");
			System.out.println("# timeStr: " + matcher.group(2) + "#");
			System.out.println("# group(3): " + matcher.group(3) + "#");
			System.out.println("# group(4): " + matcher.group(4) + "#");
			Double t = Double.parseDouble(matcher.group(5));
			System.out.println("# Test: " + t + "#");
		}
	}
	public void test1234() {
		String tmp = "10.13.112.49|2015/07/30 11:16:07 [error] 22120#0: *1125355432 upstream timed out (110: Connection timed out) while reading response header from upstream, client: 10.75.13.106, server: api.uve.mobile.sina.cn, request: \"GET /uve/service/profile?target=profile&uid=1788600742&host_uid=2395605211&from=pc&page=1&ip=14.157.79.201 HTTP/1.1\", subrequest: \"/render/v4/gateway.php\", upstream: \"fastcgi://127.0.0.1:9000\", host: \"api.uve.mobile.sina.cn\"";		
		Pattern pattern = Pattern.compile("^([^\\[]+).*, subrequest: \"([^\\\"]+)");
		//System.out.println("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]");
//		Pattern pattern = Pattern.compile("([A-Z]{3,4})");
//		String tmp = "(GET)";  
		System.out.println(pattern);
		System.out.println(tmp);
		Matcher matcher = pattern.matcher(tmp);
	
		if(matcher.find()){
			System.out.println("#" + matcher.group(1) +"#");
			System.out.println("#" + matcher.group(2) + "#");
		}
		
//		String k = "15/Jul/2015:14:08:39 +0800";
//		SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
//		try {
//			Date date = format.parse(k);
//			System.out.println(date.toString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("=========");
//		String s = "api.uve.mobile.sina.cn_access_current";
//		String[] t = s.split("_");
//		System.out.println(t.length);
//		
//		System.out.println("=======");
//		//System.out.println(k1);
//		System.out.println("fafff".charAt(1));
//		
//		SimpleDateFormat format123 = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(format123.format(new Date(System.currentTimeMillis())));
//		System.out.println(String.format("%05d", 99));
//		File s111 = new File("def");
//		System.out.println(s111.exists());
	}
	
/*	@Test
	public void test123(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long lasttime;
		try {
			lasttime = format.parse("2015-07-12").getTime();
			System.out.println(lasttime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	//@Test
	public void testSubreqHandle() {
		String tmp_subreq = "10.13.112.50|[23/Jul/2015:09:47:01 +0800] /sm_bo_page/wbpage_recommend.php 504 0.101 [10.13.57.14:80] [0.101] [504]";
		Pattern pattern2 = 	Pattern.compile("([[0-9]|\\.]+)\\|\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s([\\w|/|\\.]+)\\s([\\d]+)\\s([\\d|\\.]+)\\s\\[([\\d|:|\\.]+)\\]\\s\\[([\\d|\\.]+)\\]\\s\\[([\\d]+)\\]");
  //  	Pattern pattern2 = Pattern.compile("([[0-9]|\\.]+)\\|\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s([\\w|\\d|/]+)");
//		Pattern pattern2_time = Pattern.compile("\\[((\\d|\\.)+)\\]\\s\\[(\\d)+\\]$");
//		Pattern pattern2 = Pattern.compile("([[0-9]|\\.]+)\\|\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s([\\w|\\d|/]+)");
		Matcher matcher2 = pattern2.matcher(tmp_subreq);
//Matcher matcher2_time = pattern2_time.matcher(tmp_subreq);
		
		/*if(matcher2_time.find()){
			System.out.println("#" + matcher2_time.group(0) + "$");		 //get the whole pattern_match_string   chaoqiang  2015-07-23
			System.out.println("#" + matcher2_time.group(1) + "$");      //get the time value which we needed!  chaoqiang  2015-07-23
			System.out.println("#" + matcher2_time.group(2) + "$");
			System.out.println("#" + matcher2_time.group(3) + "$");
		}else{
			System.out.println("output time error!");
		}*/
		if(matcher2.find()){
			System.out.println("Start output...");
			System.out.println("#" + matcher2.group(0) +"#");
//			System.out.println("#" + matcher2_time.group(0) +"#");
			System.out.println("#" + matcher2.group(1) + "#");
			System.out.println("#" + matcher2.group(2) + "#");
			System.out.println("#" + matcher2.group(3) + "#");
			System.out.println("#" + matcher2.group(4) + "#");
			System.out.println("# group(5): " + matcher2.group(5) + "#");
			System.out.println("# group(5): " + Double.parseDouble(matcher2.group(5)) + "#");
			System.out.println("#" + matcher2.group(6) + "#");
			System.out.println("#" + matcher2.group(7) + "#");
			System.out.println("#" + matcher2.group(8) + "#");
		}else{	
		System.out.println("Error!");
		}
	}

	//@Test
	public void testStatsHandle() {
		String tmp_StatsHandle1 = "111.13.53.232|reqtime:1438013295|uid:2623693303|platform:iphone|version:5.3.0|from:1053093010|uvev:v1/20141023|refreshid:|loadmore:0|feedtype:hot_topic_feed|source:135|source:135:error:12204|dataunits:0";
		String tmp_StatsHandle2 = "111.13.53.232|reqtime:1438013295|uid:1779977311|platform:iphone|version:5.3.0|from:1053093010|uvev:v6/20150122|refreshid:2598655806284207136|reqid:14380132952211779977311581|loadmore:1|feedtype:main|mode:incr|unread_status:26|available_pos:2|source:130|source:130:error:12204|_first_alloc:1|_second_rand:362|_third_rand:191|source:131|source:22|source:131:error:12204|source:22:error:2202|pos:15:to:131|pos:3:to:131|dataunits:0";
		Pattern pattern1 = Pattern.compile("([[0-9]|\\.]+)\\|[a-z]+:(\\d+)");
		Pattern pattern2 = Pattern.compile("dataunits:([0-9]\\d?)");
		Pattern pattern3 = Pattern.compile("source:");
		//Pattern pattern2 = 	Pattern.compile("([[0-9]|\\.]+)\\|\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s([\\w|/|\\.]+)\\s([\\d]+)\\s([\\d|\\.]+)\\s\\[([\\d|:|\\.]+)\\]\\s\\[([\\d|\\.]+)\\]\\s\\[([\\d]+)\\]");
		Matcher matcher1 = pattern1.matcher(tmp_StatsHandle2);
		Matcher matcher2 = pattern2.matcher(tmp_StatsHandle2);
		if(matcher1.find()){
			System.out.println("Start output...");
			System.out.println("# All match string:  " + matcher1.group(0) + "#");
			System.out.println("# ip: " + matcher1.group(1) + "#");
			System.out.println("# reqtime: " + matcher1.group(2) + "#");
		}else{	
		System.out.println("Error!");
		}
		System.out.println("=================");
		if(matcher2.find()){
			System.out.println("# All match string:  " + matcher2.group(0) + "#");
			System.out.println("# dataUnits: " + matcher2.group(1) + "#");
		}else{	
		System.out.println("Error!");
		}	
	}
}
