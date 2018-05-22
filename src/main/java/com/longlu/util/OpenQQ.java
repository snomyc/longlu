package com.longlu.util;

import java.io.IOException;

public class OpenQQ {
	public void openQQ(String qqpath, String filepath) {
		Runtime r = Runtime.getRuntime();
		try {
			r.exec(qqpath);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		}
	}

	public static void main(String[] args) throws Exception {
		//OpenQQ t = new OpenQQ();
		//t.openQQ("E:\\apache-tomcat-server\\bin\\startup.bat", null);
		//t.openQQ("E:/qq.jar", null);
		Runtime r = Runtime.getRuntime();
		String[] ss = {"cmd.exe D:/cd memcached/memcached.exe -d start","cd memcached","memcached.exe -d start"};
		r.exec("D:/p/h cd memcached/p/h memcached.exe -d start/p/h");
	}
}