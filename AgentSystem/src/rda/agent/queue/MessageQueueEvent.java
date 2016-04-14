package rda.agent.queue;

import rda.window.Window;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private final String name;
    private final Window msgpack;
    //private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    public MessageQueueEvent(String name, Object msgpack) {
        super(name);
        this.name = name;
        this.msgpack = (Window)msgpack;
    }

    public void printEvent(){
        //logger.print(logger.dataMarker, name, new Object[]{">MQEvents:",name,"-msg=",message.toString()});
        System.out.println(">MQEvents:"+name+"-msg="+msgpack.toString());
    }
}