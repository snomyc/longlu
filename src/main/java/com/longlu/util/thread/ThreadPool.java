package com.longlu.util.thread;

import java.util.LinkedList;

public class ThreadPool {  
    // 线程池大小  
    private final int nThreads;  
    // 线程池工作者(具体线程)  
    private final PoolWorker[] threads;  
    // 任务队列  
    private final LinkedList<Runnable> queue;  
    public ThreadPool(int nThreads) {  
        // 初始线程池，并启动线程池里面的线程  
        this.nThreads = nThreads;  
        queue = new LinkedList<Runnable>();  
        threads = new PoolWorker[nThreads];  
        for (int i = 0; i < nThreads; i++) {  
            threads[i] = new PoolWorker();  
            threads[i].start();  
        }  
    }  
    // 提交工作任务，实际将任务放入队列，并通知线程进行消费  
    public void execute(Runnable r) {  
        synchronized (queue) {  
            queue.addLast(r);  
            queue.notify();  
        }  
    }  
  
    private class PoolWorker extends Thread {  
        public void run() {  
            Runnable r;  
            // 循环取出任务队列里的任务进行消费，如果没有任务，就等待任务到来。  
            while (true) {  
                synchronized (queue) {  
                    while (queue.isEmpty()) {  
                        try {  
                            queue.wait();  
                        } catch (InterruptedException ignored) {  
                        }  
                    }
                    r = queue.removeFirst();  
                }  
                try {  
                    r.run();  
                } catch (RuntimeException e) {  
                }  
            }  
        }  
    }  
}  
