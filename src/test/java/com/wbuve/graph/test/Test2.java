package com.wbuve.graph.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;



public class Test2 {
	@Test
	public void test() {
//		System.out.println(TimeUtil.isNotTheSameDay(System.currentTimeMillis()  - 1000 * 60 * 60 * 18));
//		System.out.println(1436976000000L - 1000 * 60 * 60 * 24 * 100L);
//		System.out.println(t);
//		System.out.println(t1);
		
		String tmp = "10.13.112.49|2015/07/15 11:43:12 [error] 45243#0: *60931816371 upstream timed out (110: Connection timed out) while reading response header from upstream, client: 172.16.35.220, server: api.uve.mobile.sina.cn, request: \"GET /uve/service/single_page?uid=1523799630&mid=3864803263014233&isRecom=-1&from=1053093010&v_p=21&ip=120.209.176.183&wm=3333_2001&appid=6&source=3439264077&ouid=3972798555 HTTP/1.1\", subrequest: \"/sm_bo_page/wbpage_recommend.php\", upstream: \"http://10.13.57.14:80/interface/wbpage_recommend/wbpage_recommend.php?last_feed_elapsed=-1&is_uve_whitelist=0&lasttime=1436931792174&uid=1523799630&area_code=34&from=1053093010&platform=iphone&carrier_code=CMCC&appid=&ip=120.209.176.183&v_p=21&unread_status=-1&mid=3864803263014233&city_code=3401&source=3439264077&wm=3333_2001&isRecom=-1&imei=&nettype=5&ouid=3972798555&is_loadmore=0&incremental=1&posid=pos523947af92d30&platform=iphone\", host: \"api.uve.mobile.sina.cn\"";
		Pattern pattern = Pattern.compile("([\\d|\\.]+)\\|([\\d|\\s|:|/]+)\\[error\\]\\s[\\d|#|:|\\*|\\w|\\s]+\\(110:\\sConnection\\stimed\\sout\\)[^,]+,[^,]+,[^,]+,[^,]+,"
				+ "\\ssubrequest:\\s" + "\"([^\"]+)\",");
		System.out.println("([\\d|\\.]+)\\|([\\d|\\s|:|/]+)[error]\\s[\\d|#|:|\\*|\\w]+\\(110:\\sConnection\\stimed\\sout\\)[^,]+,[^,]+,[^,]+,[^,]+,\\ssubrequest:\\s\"([^[\"|,]]+)\",");
		Matcher matcher = pattern.matcher(tmp);
		if(matcher.find()){
			String ip = matcher.group(1);
			String timeStr = matcher.group(2);
			String target = matcher.group(3);
//			target = target.replaceAll("/", ".");
//			target = "uve_monitor.access" + target;
			System.out.println("#" + ip + "#");
			System.out.println("#" + timeStr + "#");
			System.out.println("#" + target + "#");

		}		
		
		System.out.println(tmp);
	}
	
	public void test1() throws IOException{
		 File file = new File("d:/tmp"); 
		 FileReader fr = null;
			try {
				fr = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		BufferedReader br = new BufferedReader(fr);
		SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
		Pattern pattern = Pattern.compile("([[0-9]|\\.]+)\\|([[0-9]|\\.]+)\\s-\\s-\\s\\[([\\w|\\d|/|:|\\s|\\+]+)\\]\\s\"[A-Z]+\\s([\\w|\\d|/]+)[\\?]{0,1}");
		String tmp = null;
		while((tmp = br.readLine()) != null){
			Matcher matcher = pattern.matcher(tmp);
			if(matcher.find()){
				String ip = matcher.group(1);
				String timeStr = matcher.group(3);
				String target = matcher.group(4);
				
				long time;
				try {
					time = format.parse(timeStr).getTime();
				} catch (ParseException e1) {
					e1.printStackTrace();
					return;
				}
				System.out.println(time / 1000);
			}		
		}
	}
	
}
