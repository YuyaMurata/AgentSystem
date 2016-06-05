/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.clone;

import java.util.Queue;
import rda.agent.queue.MessageQueue;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author 悠也
 */
public class AgentCloning {
    public static Boolean mode;
    
    public static String cloning(String originalID, Queue queue){
        if(mode) return "";
        
        System.out.println(">> Agent Cloning New Copy From "+ originalID);
        
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        MessageQueue agent = (MessageQueue) manager.createAgent();
        manager.registerAgentID(originalID, agent.getID());
        //((Window)msgpack).setDestID(agID);
        
        agent.setOriginalQueue(originalID, queue);
        
        return agent.getID();
    }
    
    public static void delete(String originalID, String cloneID){
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        manager.deleteAgentID(originalID, cloneID);
        System.out.println(">> Agent Cloning Delete "+ cloneID);
    }
    
    public static void setAutoMode(Integer auto){
        mode = auto != 1;
    }
}