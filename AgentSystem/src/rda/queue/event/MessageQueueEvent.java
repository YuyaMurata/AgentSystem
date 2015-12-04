package rda.queue.event;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;

public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private final String name;
    public MessageQueueEvent(String name) {
        super(name);
        this.name = name;
    }

    public void printEvent(){
        //イベント出力
        logger.printMQEvent(dataMarker, 
                "MQName_,{}, : ########## 負荷検知 ########## ", new String[]{name});
    }
}