package rda.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import rda.property.SetProperty;

public class ReciveMessageQueue extends SetProperty{
    public final String name;
    private final ConcurrentLinkedQueue<Object> queue;
    private final ReciveMQProcess thread;
    private final WindowController window;
    
    public ReciveMessageQueue(String name, WindowController window) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.name = name;
        this.window = window;
        
        this.queue = new ConcurrentLinkedQueue<>();
        this.thread = new ReciveMQProcess(name, this);
        thread.start();
    }

    public synchronized void putMessage(Object message) throws InterruptedException {
        while(isFull()){
            try {
                event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();

                wait(AGENT_WAIT);
            }
        }
        
        queue.offer(message);
        notify();
    }

    public void event() throws MessageQueueException{
        throw new MessageQueueException(name);
    }
	
    public Boolean isEmpty(){
        return getSize() == 0;
    }
    
    public Boolean isFull(){
        return getSize() > QUEUE_LENGTH;
    }

    public synchronized Object getMessage() throws InterruptedException{
        if(isEmpty()) wait();

        notify();
        return queue.poll();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public Boolean isRunning(){
        return window.running;
    }
    
    public synchronized void isFinish(){
        notify();
    }
}