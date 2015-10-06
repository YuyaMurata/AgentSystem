package old;

import rda.queue.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import rda.property.SetProperty;

public class ReciveMessageQueue implements SetProperty{
    public final String name;
    public final ConcurrentLinkedQueue<Object> queue;
    private final ReciveMQProcess thread;
    private final WindowController window;
    private Boolean runnable;
    //private final ExecutorService dataPushWaiting = Executors.newSingleThreadExecutor();
    
    public ReciveMessageQueue(String name, WindowController window) {
        this.name = name;
        this.runnable = true;
        
        this.window = window;
        
        this.queue = new ConcurrentLinkedQueue<>();
        this.thread = new ReciveMQProcess(this);
        thread.start();
    }
    
    public synchronized void putWait(){
        try {
            wait(QUEUE_WAIT);
        } catch (InterruptedException e) {
        }
    }

    public synchronized void putMessage(Object msg){
        //dataPushWaiting.execute(new ReciveMessageQueuePutTask(this, msg));
        if(!isRunning()) throw new IllegalStateException();
        while(isFull()){
            try {
                event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();

                putWait();
            }
        }
        
        queue.offer(msg);
        
        notifyAll();
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
        if(!isRunning()) throw new IllegalStateException();

        if(isEmpty()) wait();
        
        notifyAll();
        return queue.poll();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public Boolean isRunning(){
        return runnable;
    }
    
    public synchronized void isFinish(){
        notifyAll();
        runnable = false;
        
        thread.interrupt();
    }
}