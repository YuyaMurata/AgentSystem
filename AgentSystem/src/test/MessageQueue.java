package test;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
	private static final int QUEUE_SIZE = 10;
	private static final long QUEUE_WAIT = 100;

	public String name;
	private ConcurrentLinkedQueue<Object> queue;
	private MQThread thread;
	public MessageQueue(String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.queue = new ConcurrentLinkedQueue<Object>();

		this.thread = new MQThread(name, this);
		thread.start();
	}

	public synchronized void putMessage(String mes){
		while(queue.size() >= QUEUE_SIZE) {
			try {
				System.out.println(name+": full wait!");
				wait();
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
			}
		};

		System.out.println(name+": put message!");
		queue.offer(mes);
		notify();
	}

	public synchronized Object get(){
		while(queue.size() == 0) {
			try {
				System.out.println(name+": empty wait!");
				wait();
			} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			}
		};

		notify();
		return queue.poll();
	}
	
	public void close(){
		thread.stopRunning();
	}
	
}
