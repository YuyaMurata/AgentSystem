package rda.queue.reciver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

import rda.queue.event.MessageQueueEvent;
import rda.queue.manager.MessageQueueManager;

public class ReciveMessageQueue{
    public final String name;
    private final BlockingQueue<Object> queue;
    private final ReciveMQProcess mqThread;
    //private Boolean runnable;
    private Integer size;
    
    private static final Marker rMQMarker = MarkerFactory.getMarker("ReciveMessageQueue");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    public ReciveMessageQueue(String name, Integer size) {
        this.name = name;
        this.size = size;
        
        this.queue = new ArrayBlockingQueue<>(size);
        this.mqThread = new ReciveMQProcess(this);
    }

    public void putMessage(Object msg) throws InterruptedException, MessageQueueEvent{
        //System.out.println("RECIVE::"+name+" PUT MESSAGE!! ["+queue.size()+"]");
        if(!isRunning()) throw new InterruptedException();
        
        if(isFull())
            throw new MessageQueueEvent(name);
        
        queue.put(msg);
    }
    
    public void event() throws MessageQueueEvent{
        throw new MessageQueueEvent(name);
    }
    
    public Boolean isFull(){
        return getSize() >= size;
    }
    
    public Boolean isEmpty(){
        return getSize() == 0;
    }

    public Object getMessage() throws InterruptedException{
        //System.out.println("RECIVE::"+name+" GET MESSAGE!! ["+queue.size()+"]");
        
        if(!isRunning()) throw new InterruptedException();
        
        return queue.take();
    }

    public Integer getSize(){
        return queue.size();
    }
        
    private static final MessageQueueManager manager = MessageQueueManager.getInstance();
    public Boolean isRunning(){
        return manager.isRunnable();
    }
    
    public void start(){
        //synchronized(this) { runnable = true; }
        mqThread.start();
        
        logger.print(rMQMarker, 
                "Recive Message Queue {} Start!!", 
                new String[]{name});
    }
    
    public void syncstop(){
        if(mqThread.isAlive())
        try {
            stop();
            mqThread.join();
        } catch (InterruptedException ex) {
        }
        
        logger.print(rMQMarker, 
                "Recive Message Queue {} Stop!!",
                new String[]{name});
    }
    
    public void stop(){
        //synchronized(this){ runnable = false; }
        mqThread.interrupt();
    }
}