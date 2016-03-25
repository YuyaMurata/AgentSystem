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
    abstract public MessageQueue getMessageQueue();
    abstract public Boolean getRunnable();
    abstract public void start();
    
    @Override
    public void run(){
        MessageQueue mq = getMessageQueue();
        UpdateRank agent = new UpdateRank(getAgentID());
        
        System.out.println(">> Start--MessageQueue of "+getAgentID()+" run message processing");
        while(getRunnable()){
            Object pack = mq.get();
            agent.sendUpdateMessage(((Window)pack).get());
        }
        System.out.println(">> Stop--MessageQueue of "+getAgentID()+" shutdown message processing");
    }
}