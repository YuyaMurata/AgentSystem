package rda.queue.event;

import rda.queue.obj.MessageObject;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    //private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private final String name;
    private final MessageObject message;
    public MessageQueueEvent(String name, Object message) {
        super(name);
        this.name = name;
        this.message = (MessageObject)message;
        printEvent();
    }

    public void printEvent(){
        System.out.println(">MQEvents:"+name+"-msg="+message.toString());
    }
}