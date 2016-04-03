/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import rda.agent.rank.updater.UpdateRank;
import rda.agent.template.AgentType;
import rda.window.Window;

/**
 *
 * @author 悠也
 */
public abstract class MessageQueueProcess extends Thread{
    abstract public Boolean getRunnable();
    
    abstract public Object get();
    abstract public void put(Object message);
    
    abstract public void setAgentType(AgentType type);
    abstract public AgentType getAgentType();
    
    @Override
    public void run(){
        AgentType agent = getAgentType();
        System.out.println(">AgentID::"+agent.getID());
        System.out.println(">> Start--MessageQueue of "+agent.getID()+" run message processing");
        while(getRunnable()){
            Object pack = get();
            
            if(pack != null)
                agent.sendMessage(((Window)pack).get());
        }
        System.out.println(">> Stop--MessageQueue of "+agent.getID()+" shutdown message processing");
    }
}