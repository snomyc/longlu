package com.longlu.util.thread;

//任务类，具体要执行的操作  
public class Worker implements Runnable {
	private int id;

	public Worker(int id) {
		this.id = id;
	}

	public void run() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程：" + Thread.currentThread().getName() + " 执行任务"
				+ id);
	}
}