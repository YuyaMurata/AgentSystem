/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import rda.agent.rank.updater.UpdateRank;
import rda.window.Window;

/**
 *
 * @author 悠也
 */
public abstract class MessageQueueProcess extends Thread{
    abstract public String getAgentID();
    abstract public Boolean getRunnable();
    private MessageQueue mq;
    
    public void setMessageQueue(MessageQueue mq){
        this.mq = mq;
    }
    
    @Override
    public void run(){
        System.out.println("AGN::"+getAgentID());
        
        UpdateRank agent = new UpdateRank(mq.getAgentID());
        
        System.out.println(">> Start--MessageQueue of "+mq.getAgentID()+" run message processing");
        while(getRunnable()){
            Object pack = mq.get();
            agent.sendUpdateMessage(((Window)pack).get());
        }
        System.out.println(">> Stop--MessageQueue of "+mq.getAgentID()+" shutdown message processing");
    }
}