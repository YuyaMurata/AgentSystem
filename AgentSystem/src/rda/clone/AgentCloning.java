/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.clone;

import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;

/**
 *
 * @author 悠也
 */
public class AgentCloning {
    public static Boolean mode;
    private static IDManager id = AgentMessageQueueManager.getInstance().getIDManager();
    
    public static String cloning(String sourceID, Object originalState){
        if(mode) return "";
        
        String originalID = id.getOrigID(sourceID);
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        String cloneID = manager.createCloneAgent(originalID, originalState);
        manager.registerAgentID(originalID, cloneID);
        //((Window)msgpack).setDestID(agID);
        
        System.out.println(">> Agent Cloning New Copy From "+ originalID);
        
        return cloneID;
    }
    
    public static String delete(String deleteID){
        if(mode) return "";
        
        String originalID = id.getOrigID(deleteID);
        AgentMessageQueueManager manager = AgentMessageQueueManager.getInstance();
        
        manager.deleteAgentID(originalID, deleteID);
        System.out.println(">> Agent Cloning Delete "+ deleteID);
        
        return deleteID;
    }
    
    public static void setAutoMode(Integer auto){
        mode = auto != 1;
    }
}