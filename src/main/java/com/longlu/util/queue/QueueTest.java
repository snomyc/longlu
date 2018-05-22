package com.longlu.util.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {

	public static void main(String[] args) throws Exception {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(100);
		ProductRunnable p = new ProductRunnable(queue);
		
		DealDataRunnable d = new DealDataRunnable(queue);
		//创建可缓存的线程池
		ExecutorService service = Executors.newFixedThreadPool(5);
		service.execute(p);
		service.execute(d);
		service.execute(d);
		service.execute(d);
		service.execute(d);
		
		//Thread.sleep(10 * 1000);
		//销毁线程池
		//service.shutdown();
	}
}
