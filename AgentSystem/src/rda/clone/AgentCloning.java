/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.clone;

import java.util.Queue;
import rda.agent.queue.MessageQueue;
import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;

/**
 *
 * @author 悠也
 */
public class AgentCloning {
    public static Boolean mode;
    
    public static String cloning(String originalID, Queue queue){
        if(mode) return "";
        
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        //this.originalID = originalID;
        
        IDManager id = manager.getIDManager();
        String cloneID = id.genID();
        
        manager.createAgent(cloneID);
        id.regID(originalID, cloneID);
        //((Window)msgpack).setDestID(agID);
        
        MessageQueue agent = (MessageQueue) manager.getMQMap().get(cloneID);
        
        agent.setOriginalQueue(queue);
        
        return cloneID;
    }
    
    public static void setAutoMode(Integer auto){
        mode = auto != 1;
    }
}