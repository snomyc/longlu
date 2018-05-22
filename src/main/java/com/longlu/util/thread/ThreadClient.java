package com.longlu.util.thread;

public class ThreadClient {
	public static void main(String[] args) {
		ThreadPool queue = new ThreadPool(10);
		// 提交工作任务。
		queue.execute(new Worker(1));
		queue.execute(new Worker(2));
		queue.execute(new Worker(3));
	}
}
