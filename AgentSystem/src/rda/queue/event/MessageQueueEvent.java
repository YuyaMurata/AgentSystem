package rda.queue.event;

import rda.log.AgentSystemLogger;
import rda.queue.obj.MessageObject;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private final String name;
    private final MessageObject message;
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    public MessageQueueEvent(String name, Object message) {
        super(name);
        this.name = name;
        this.message = (MessageObject)message;
    }

    public void printEvent(){
        System.out.println(">MQEvents:"+name+"-msg="+message.toString());
    }
}