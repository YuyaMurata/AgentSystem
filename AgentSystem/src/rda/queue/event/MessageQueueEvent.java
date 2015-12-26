package rda.queue.event;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.queue.manager.MessageQueueManager;

public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private final String name;
    public MessageQueueEvent(String name) {
        super(name);
        this.name = name;
    }

    public void printEvent(){
        manager.event(name);
    }
}