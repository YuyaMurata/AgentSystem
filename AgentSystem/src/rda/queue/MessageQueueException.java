package rda.queue;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

public class MessageQueueException extends Exception{
    /**
    *
    */
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private final String name;
    public MessageQueueException(String name) {
        super(name);
        this.name = name;
    }

    public void printEvent(){
        //イベント出力
        logger.printMQEvent(dataMarker, 
                "MQName_,{}, : ########## 負荷検知 ########## ", new String[]{name});
    }
}