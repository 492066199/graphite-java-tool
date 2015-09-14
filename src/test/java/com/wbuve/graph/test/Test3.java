package com.wbuve.graph.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class Test3 {
	public static SimpleDateFormat format = new SimpleDateFormat("dd/MMMMM/yyyy:HH:mm:ss z", Locale.ENGLISH);
	@Test
	public void test(){
		
		String q = "10.13.112.51|10.73.14.42 - weibo_v4@sina.com [28/Jul/2015:17:44:44 +0800] \"GET /uve/service/pc_right_account?source=2346240190&uid=1703575741&from=pchome&ip=10.73.13.71&page=1&count=20 HTTP/1.1\" - ^200^ 2864 \"-\" \"Weibo.com Swift framework HttpRequest class\" \"-\" ^\"0.081\"^ ^\"-\"^";
		System.out.println(q);
		List<String> s = Lists.newArrayList(Splitter.on(' ').split(q));
		for(String tmp : s){}
//			System.out.println(tmp);
		System.out.println(s.size());
		if(s.size() == 20){
			String time = s.get(3) + s.get(4);
			System.out.println(time);
			String target = s.get(6);
			System.out.println(target);
			String status = s.get(9);
			System.out.println(status);
			String use = s.get(18).replace('^', ' ').replace('\"', ' ').trim();
			System.out.println(use);
		}
	}
	
	public static void main(String[] args) throws IOException, ParseException{
		File file1 = new File("C:\\Users\\yangyang21\\Downloads\\tmp1");
		FileWriter fw = new FileWriter(file1);
		BufferedWriter wr = new BufferedWriter(fw);
		
		
		
		File file = new File("C:\\Users\\yangyang21\\Downloads\\tmp");
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int max = 0;
		int min = 100;
		int l16 = 0;
		int l20 = 0;
		
		while(true){
			String tmp = br.readLine();
			if(tmp == null){
				break;
			}
			List<String> info = Lists.newArrayList(Splitter.on(' ').split(tmp));
			int count = info.size();
			if(count > max)
				max = count;
			if(count < min)
				min = count;
			
			if(info.size() == 16 ){
//				System.out.println(tmp);
//				System.out.println(s.size());
				l16++;
			}
			
			if(info.size() == 20){
//				System.out.println(tmp);
//				System.out.println(s.size());
				l20++;
			}
			
			
			if(count == 44)
				System.out.println(tmp);
			
			long time;
			String ip = "";
			Double t;
			String target;
			
			if(count < 16){
			}
			
			String timeStr = info.get(3) + " " + info.get(4);
			time = format.parse(timeStr.substring(1, timeStr.length() - 1)).getTime();

			String tStr = info.get(count - 2);
			t = Double.parseDouble(tStr.substring(2, tStr.length() - 2));

			ip = "";

			target = info.get(6);
			int start = 1;
			int end = target.length();
			if (target.indexOf('?') >= 0) {
				end = target.indexOf('?');
			}
			int phpc = target.indexOf(".php");
			if (phpc >= 0) {
				end = phpc;
			}
			target = target.substring(start, end);
			target = target.replace('/', '.');
			
			target = target.replace('/', '.');
			//wr.write(t.toString() + "\n");
			//wr.write(time + "\n");
			wr.write(target + "\n");
		}
		System.out.println("max:" + max);
		System.out.println("min:" + min);
		System.out.println("l16:" + l16);
		System.out.println("l20:" + l20);
		wr.close();
	}
}
