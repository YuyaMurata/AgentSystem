/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.Map;

/**
 *
 * @author 悠也
 */
public class AgentMessageQueueManager {
    private static AgentMessageQueueManager manager = new AgentMessageQueueManager();
    
    //Singleton
    private void AgentMessageQueueManager(){}
    public static AgentMessageQueueManager getInstance(){
        return manager;
    }
    
    public void initAgentMessageQueueManager(Map agentMQParam){
        
    }
    
    
    public void createAgent(String namerule){
        
    }
    
    
}
