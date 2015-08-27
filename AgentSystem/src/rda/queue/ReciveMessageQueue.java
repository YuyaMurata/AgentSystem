package rda.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import rda.property.SetPropertry;

public class ReciveMessageQueue extends SetPropertry{
    public String name;
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
        while(getSize() >= QUEUE_LENGTH){
            try {
                event();
            } catch (MessageQueueException mqLimitEvent) {
                // TODO 自動生成された catch ブロック
                mqLimitEvent.printEvent();

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
	
    public Boolean check(){
        return getSize() > 0;
    }

    public synchronized Object getMessage(){
        if(!check()){
            try {
                //System.out.println(name+" Empty Wait!");
                wait();
            } catch (InterruptedException e) {}
        }

        notify();
        return queue.poll();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public synchronized Thread close(){
        return thread.stopRunning();
    }
}