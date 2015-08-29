package rda.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import rda.property.SetProperty;

public class ReciveMessageQueue extends SetProperty{
    public final String name;
    private final ConcurrentLinkedQueue<Object> queue;
    private final ReciveMQProcess thread;
    
    public ReciveMessageQueue(String name) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.name = name;
        this.queue = new ConcurrentLinkedQueue<>();
        this.thread = new ReciveMQProcess(name, this);
        thread.start();
    }

    public synchronized void putMessage(Object message) {
        while(isFull()){
            try {
                event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();

                try {
                    wait(AGENT_WAIT);
                } catch (InterruptedException e) {}
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

    public synchronized Object getMessage(){
        if(isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        notify();
        return queue.poll();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public synchronized void close(){
        thread.stopRunning();
    }
}