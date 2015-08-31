package rda.queue;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

public class MessageQueueException extends Exception{
    /**
    *
    */
    private static final Marker mqEventMarker = MarkerFactory.getMarker("Message Queue Event");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
        
    public MessageQueueException(String name) {
        super(name);
    }

    public void printEvent(){
        //イベント出力
        logger.printMQEvent(mqEventMarker, 
                "MQName_{} : ########## 負荷検知 ########## ", new String[]{this.toString()});
    }
}