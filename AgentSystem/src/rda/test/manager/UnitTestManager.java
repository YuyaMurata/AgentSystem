/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.manager;

import java.util.HashMap;
import java.util.Map;
import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class UnitTestManager extends TestParameter{
    private static UnitTestManager manager = new UnitTestManager();
    public static UnitTestManager getInstance(){
        return manager;
    }
    
    public void prepareManager(){
        AgentMessageQueueManager ag = AgentMessageQueueManager.getInstance();
        IDManager id = new IDManager("TEST#");
        
        Map agentMQParam = new HashMap();
        agentMQParam.put("QUEUE_LENGTH", QUEUE_LENGTH);
        
        ag.initAgentMessageQueueManager(agentMQParam);
        ag.setIDManager(id);
    }
}
