package rda.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import rda.property.SetPropertry;

public class ReciveMessageQueue extends SetPropertry{
	public String name;
	private ConcurrentLinkedQueue<Object> queue;
	private ReciveMQProcess thread;
	public ReciveMessageQueue(String name) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.queue = new ConcurrentLinkedQueue<Object>();

		this.thread = new ReciveMQProcess(name, this);
		thread.start();
	}

	public synchronized void putMessage(Object message) {
		while(queue.size() >= QUEUE_LENGTH){
			try {
				event();
			} catch (MessageQueueException e1) {
			// TODO 自動生成された catch ブロック
				e1.printEvent();

				try {
					wait(AGENT_WAIT);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
				}
			}
		}
		queue.offer(message);
		notify();
	}

	public void event() throws MessageQueueException{
		throw new MessageQueueException(name);
	}
	
	public Boolean check(){
		if(queue.size() > 0) return true;
		else return false;
	}

	public synchronized Object getMessage(){
		while(queue.size() == 0){
			try {
				//System.out.println(name+" Empty Wait!");
				wait();
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
			}
		}

		notify();
		return queue.poll();
	}

	public synchronized void close(){
		thread.stopRunning();
	}

	public Integer getSize(){
		return queue.size();
	}
}