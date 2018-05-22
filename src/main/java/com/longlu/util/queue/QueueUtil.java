package com.longlu.util.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueUtil {

	private static BlockingQueue<String> queue = new LinkedBlockingQueue<String>(5000);

	public static BlockingQueue<String> getQueue() {
		return queue;
	}

	public static void setQueue(BlockingQueue<String> queue) {
		QueueUtil.queue = queue;
	}
}
