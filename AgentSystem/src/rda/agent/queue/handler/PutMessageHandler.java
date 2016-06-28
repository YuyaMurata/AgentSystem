/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rda.Aggregateagent;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.agent.queue.PutMessage;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author kaeru
 */
public class PutMessageHandler extends MessageHandler{
    private static AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        PutMessage putMsg = (PutMessage)msg;
        
        Aggregateagent agent = (Aggregateagent)getEntity();
        TxID tx = getTx();
        String id = agent.getAgentID(tx);
        
        //Get MessageQueue
        MessageQueue mq = (MessageQueue)manager.getMQMap().get(id);
        
        //Put Message
        try{
            mq.put(putMsg.msgPack);
        } catch (MessageQueueEvent mqev) {
            mqev.printEvent();
        }
        
        return id + " : success put messages";
    } 
}
