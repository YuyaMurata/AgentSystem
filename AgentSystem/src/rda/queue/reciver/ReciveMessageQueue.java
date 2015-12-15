package rda.queue.reciver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

import rda.property.SetProperty;
import rda.queue.event.MessageQueueEvent;

public class ReciveMessageQueue implements SetProperty{
    public final String name;
    private final BlockingQueue<Object> queue;
    private final ReciveMQProcess mqThread;
    private Boolean runnable;
    
    private static final Marker rMQMarker = MarkerFactory.getMarker("ReciveMessageQueue");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    public ReciveMessageQueue(String name) {
        this.name = name;
        
        this.queue = new ArrayBlockingQueue<>(QUEUE_LENGTH+1);
        this.mqThread = new ReciveMQProcess(this);
    }

    public void putMessage(Object msg) throws InterruptedException, MessageQueueEvent{
        //System.out.println("RECIVE::"+name+" PUT MESSAGE!! ["+queue.size()+"]");
        
        synchronized(this){
            if(!isRunning()) throw new IllegalStateException();
        }
        if(isFull())
            throw new MessageQueueEvent(name);
        
        //queue.put(msg);
    }
    
    public void event() throws MessageQueueEvent{
        throw new MessageQueueEvent(name);
    }
    
    public Boolean isFull(){
        return getSize() >= QUEUE_LENGTH;
    }
    
    public Boolean isEmpty(){
        return getSize() == 0;
    }

    public Object getMessage() throws InterruptedException{
        System.out.println("RECIVE::"+name+" GET MESSAGE!! ["+queue.size()+"]");
        
        if(!isRunning()) throw new IllegalStateException();
        
        return null;//queue.take();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    public Boolean isRunning(){
        return runnable;
    }
    
    public void start(){
        synchronized(this) { runnable = true; }
        mqThread.start();
        
        logger.print(rMQMarker, 
                "Recive Message Queue {} Start!!", 
                new String[]{name});
    }
    
    public void stop(){
        synchronized(this){ runnable = false; }
        mqThread.interrupt();
        
        logger.print(rMQMarker, 
                "Recive Message Queue {} Stop!!",
                new String[]{name});
    }
}