package com.longlu.util.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author yangcan 
 * 处理更新数据 及 消费者
 */
public class DealDataRunnable implements Runnable {

	private BlockingQueue<String> queue;

	public DealDataRunnable(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	public void run() {
		boolean isRunning = true;
		try {
			while (isRunning) {
				System.out.println(Thread.currentThread().getName()+"正从队列获取数据...");
				String data = queue.poll(2, TimeUnit.SECONDS);
				//String data = queue.poll();
				if (null != data) {
					System.out.println(Thread.currentThread().getName()+"正在处理更新SQL脚本：" + data);
					Thread.sleep(1000);
				} else {
					// 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
					isRunning = false;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			System.out.println(Thread.currentThread().getName()+"退出处理更新SQL脚本线程！");
		}
	}

}
