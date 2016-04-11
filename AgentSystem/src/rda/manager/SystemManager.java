/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.HashMap;
import java.util.Map;
import rda.agent.client.AgentConnection;
import rda.property.SetProperty;
import rda.stream.DataStream;

/**
 *
 * @author 悠也
 */
public class SystemManager implements SetProperty{
    private static SystemManager manager = new SystemManager();
    private SystemManager(){}
    
    public static SystemManager getInstance(){
        return manager;
    }
    
    public void launchSystem(){
        System.out.println(">>Launch System");
        
        agentSettings(NAME_RULE, NUMBER_OF_RANK_AGENTS, preAgentMap(), POOLSIZE);
        dataSettings(NUMBER_OF_USERS, preDataMap(), preProfMap());
        loggerSettings();
        streamSettings(preStreamMap());
    }
    
    public void shutdownSystem(){
        AgentMessageQueueManager agManager = AgentMessageQueueManager.getInstance();
        agManager.doShutdown();
        
        System.out.println(">>Shutdown System...");
    }
    
    private void agentSettings(String rule, Integer numberOfAgents, Map agentParam, Integer poolsize){
        AgentConnection agconn = AgentConnection.getInstance();
        agconn.setPoolSize(poolsize);
        
        AgentMessageQueueManager agManager = AgentMessageQueueManager.getInstance();
        IDManager idManager = new IDManager(rule);
        
        agManager.initAgentMessageQueueManager(agentParam);
        agManager.setIDManager(idManager);
        agManager.createNumberOfAgents(numberOfAgents);
        
        System.out.println(">>> Finished Set Agents & IDs");
    }
    
    private void dataSettings(Integer numberOfUsers, Map dataParam, Map profParam){
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.initTestCase(dataParam, profParam);
        
        System.out.println(">>> Finished Set TestCase");
    }
    
    private void loggerSettings(){
        LoggerManager.getInstance().initLoggerManager();
    }
    
    private DataStream stream;
    private void streamSettings(Map streamMap){
        this.stream = new DataStream(streamMap);
    }
    
    public DataStream dataStream(){
        return stream;
    }
    
    private Map preAgentMap(){
        Map map = new HashMap();
        map.put("QUEUE_LENGTH", QUEUE_LENGTH);
        map.put("QUEUE_WAIT", QUEUE_WAIT);
        map.put("AGENT_WAIT", AGENT_WAIT);
        return map;
    }
    
    private Map preProfMap(){
        Map map = new HashMap();
        map.put("AMOUNT_USER", NUMBER_OF_USERS);
        map.put("MODE", DATA_MODE_PROFILE);
        map.put("SEED", PROF_SEED);
        return map;
    }
    
    private Map preDataMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", TIME_PERIOD); 
        map.put("DATA_VOLUME", DATA_VOLUME); 
        map.put("AMOUNT_USER", NUMBER_OF_USERS); 
        map.put("AGENT_DEFAULT_VALUE", AGENT_DEFAULT_VALUE);
        map.put("SELECT_TYPE", DATA_SELECT_TYPE);
        map.put("MODE", DATA_MODE_GENERATE);
        map.put("SEED", DATA_SEED);
        return map;
    }
    
    private Map preStreamMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", TIME_PERIOD);
        map.put("TIME_WAIT", TIME_WAIT);
        map.put("WINDOW_SIZE", WINDOW_SIZE);
        return map;
    }
}