package rda.agent.queue;

import rda.agent.template.AgentType;

public abstract class MessageQueueProcess extends Thread{
    abstract public Boolean getRunnable();
    
    abstract public Object get();
    abstract public void put(Object message) throws MessageQueueEvent;
    
    abstract public void setAgentType(AgentType type);
    abstract public AgentType getAgentType();
    
    @Override
    public void run(){
        AgentType agent = getAgentType();
        System.out.println(">AgentID::"+agent.getID());
        System.out.println(">> Start--MessageQueue of "+agent.getID()+" run message processing");
        while(getRunnable()){
            Object msgpack = get();
            
            if(msgpack != null)
                agent.sendMessage(msgpack);
            
        }
        System.out.println(">> Stop--MessageQueue of "+agent.getID()+" shutdown message processing");
    }
}