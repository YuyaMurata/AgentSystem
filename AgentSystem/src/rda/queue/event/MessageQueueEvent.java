package rda.queue.event;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    //private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private final String name;
    private final Object message;
    public MessageQueueEvent(String name, Object message) {
        super(name);
        this.name = name;
        this.message = message;
    }

    public void printEvent(){
        System.out.println(">MQEvents:"+name+"-msg="+message);
    }
}