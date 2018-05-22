package com.longlu.util.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangcan
 * 创建需要更新的数据 及 生产者
 */
public class ProductRunnable implements Runnable{
	
	private BlockingQueue<String> queue;
	
	private static AtomicInteger count = new AtomicInteger();
	
	public ProductRunnable(BlockingQueue<String> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		while(true) {
			try {
				queue.offer("update game set begin_draw_issue = ? where game_id = "+count.incrementAndGet());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
