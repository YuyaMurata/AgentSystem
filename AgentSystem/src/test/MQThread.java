package test;


public class MQThread extends Thread{
	private boolean running = true;
	
	public String name;
	private MessageQueue queue;
	public MQThread(String name, MessageQueue queue) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.queue = queue;
	}

	public void run(){
		while(running){
			String mes = (String) queue.get();
			System.out.println(name+":"+mes);
		}
		
		System.out.println(name+": Stop Thread!");
	}
	
	public void stopRunning(){
		running = false;
	}
}
