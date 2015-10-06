package rda.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import rda.property.SetProperty;

public class ReciveMessageQueue implements SetProperty{
    public final String name;
    private final BlockingQueue<Object> queue;
    private final ReciveMQProcess thread;
    private final WindowController window;
    private Boolean runnable;
    //private final ExecutorService dataPushWaiting = Executors.newSingleThreadExecutor();
    
    public ReciveMessageQueue(String name, WindowController window) {
        this.name = name;
        this.runnable = true;
        
        this.window = window;
        
        this.queue = new ArrayBlockingQueue<>(QUEUE_LENGTH);
        this.thread = new ReciveMQProcess(this);
        thread.start();
    }

    public void putMessage(Object msg) throws InterruptedException{
        //dataPushWaiting.execute(new ReciveMessageQueuePutTask(this, msg));
        synchronized(this){
            if(!isRunning()) throw new IllegalStateException();
        }
        if(isFull()){
            try {
                event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();
            }
        }
        
        queue.put(msg);
    }
    
    public void event() throws MessageQueueException{
        throw new MessageQueueException(name);
    }
    
    public Boolean isFull(){
        return getSize() >= QUEUE_LENGTH;
    }
    
    public Boolean isEmpty(){
        return getSize() == 0;
    }

    public Object getMessage() throws InterruptedException{
        if(!isRunning()) throw new IllegalStateException();
        
        return queue.take();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public Boolean isRunning(){
        return runnable;
    }
    
    public void isFinish(){
        runnable = false;
        
        thread.interrupt();
    }
}