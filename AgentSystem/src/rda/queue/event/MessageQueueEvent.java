package rda.queue.event;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.queue.manager.MessageQueueManager;

public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private final String name;
    public MessageQueueEvent(String name) {
        super(name);
        this.name = name;
    }

    public void printEvent(){
        String distAgent = manager.decompose(name);
        
        //イベント出力
        logger.printMQEvent(dataMarker, 
                "MQName_,{},{},: ########## Load Detecting ########## ",
                new String[]{name, distAgent});
    }
}