package rda.queue.event;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    //private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private final String name;
    public MessageQueueEvent(String name) {
        super(name);
        this.name = name;
    }

    public void printEvent(){
        System.out.println(">MQEvents:"+name);
    }
}