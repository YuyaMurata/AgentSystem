package rda.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

import rda.property.SetProperty;

public class ReciveMessageQueue implements SetProperty{
    public final String name;
    private final BlockingQueue<Object> queue;
    private final ReciveMQProcess mqThread;
    private Boolean runnable;
    
    private static final Marker rMQMarker = MarkerFactory.getMarker("ReciveMessageQueue");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private static final MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
    
    //private final ExecutorService dataPushWaiting = Executors.newSingleThreadExecutor();
    
    public ReciveMessageQueue(String name) {
        this.name = name;
        
        this.queue = new ArrayBlockingQueue<>(QUEUE_LENGTH);
        this.mqThread = new ReciveMQProcess(this);
    }

    public void putMessage(Object msg) throws InterruptedException{
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
    
    public void start(){
        synchronized(this) { runnable = true; }
        mqThread.start();
        
        logger.print(rMQMarker, 
                "********** Recive Message Queue {} Start!! ********** ", 
                new String[]{name});
    }
    
    public void stop(){
        synchronized(this){ runnable = false; }
        mqThread.interrupt();
        
        logger.print(rMQMarker, 
                "********** Recive Message Queue {} Stop!! ********** ",
                new String[]{name});
    }
    
    public void log(){
        mqSS.map.put(name, getSize());
    }
}