/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import java.util.List;
import java.util.Map;
import rda.manager.AgentMessageQueueManager;

/**
 *
 * @author kaeru
 */
public class AgentLogPrint {
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    public static void printMessageQueueLog(Map length){
        List list = (List)length.get("Field");
        logger.printMQLength(logger.fieldMarker, "MessageQueue", list.toArray(new Object[list.size()]));
        
        list = (List)length.get("Data");
        logger.printMQLength(logger.dataMarker, "MessageQueue", list.toArray(new Object[list.size()]));
    }
    
    public static void printAgentTransaction(Map transaction){
        List list = (List)transaction.get("Field");
        logger.printMQLength(logger.fieldMarker, "Agent", list.toArray(new Object[list.size()]));
        
        list = (List)transaction.get("Data");
        logger.printMQLength(logger.dataMarker, "Agent", list.toArray(new Object[list.size()]));
    }
    
    public static void printMessageLatency(Map latency){
        List list = (List)latency.get("Field");
        logger.printMQLength(logger.fieldMarker, "Message", list.toArray(new Object[list.size()]));
        
        list = (List)latency.get("Data");
        logger.printMQLength(logger.dataMarker, "Message", list.toArray(new Object[list.size()]));
    }
    
    public static void printAgentLoad(String origin, String dest, String clone){
        Integer num = AgentMessageQueueManager.getInstance().getNumAgents();
        logger.printMQEvent(logger.dataMarker, "Event", new Object[]{origin, dest, clone, num});
    }
    
    public static void printPropertySettings(String str, Map map){
        for(Object key : map.keySet())
            logger.print(logger.titleMarker, str, new Object[]{key+"="+map.get(key)});
    }
    
    public static void printResults(String str, Map map){
        for(Object key : map.keySet())
            logger.print(logger.titleMarker, str, new Object[]{key+"="+map.get(key)});
    }
    
    public static void printTestcaseData(){
        
    }
}
