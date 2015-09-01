package rda.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rda.property.SetProperty;

public class ReciveMessageQueue implements SetProperty{
    public final String name;
    public final ConcurrentLinkedQueue<Object> queue;
    private final ReciveMQProcess thread;
    private final WindowController window;
    private final ExecutorService dataPushWaiting = Executors.newSingleThreadExecutor();
    
    public ReciveMessageQueue(String name, WindowController window) {
        this.name = name;
        this.window = window;
        
        this.queue = new ConcurrentLinkedQueue<>();
        this.thread = new ReciveMQProcess(this);
        thread.start();
    }

    public synchronized void putMessage(Object message){
        dataPushWaiting.execute(new ReciveMessageQueuePutTask(this, message));
        
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
        if(isEmpty()) 
            try {
                wait();
            } catch (InterruptedException e) {
            }

        notifyAll();
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
        while(!thread.isFinish())
            try {
                wait(10);
            } catch (InterruptedException ex) {
            }
    }
}