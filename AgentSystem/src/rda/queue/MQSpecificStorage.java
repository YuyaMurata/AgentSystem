/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue;

import java.util.concurrent.ConcurrentSkipListMap;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage implements SetProperty{
    private static final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();
    
    private static final Marker mqSSMarker = MarkerFactory.getMarker("MessageQueueSSLength");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    public static void setMQSSMap(String mqName, Integer mqLength){
        map.put(mqName, mqLength);
    }
    
    public static void mqLengthLogging(){
        StringBuilder sb = new StringBuilder();
        Object[] mapStr = new String[map.size()];
        int i= -1;
        
        for(String key : map.keySet()){
            mapStr[i++] = map.get(key);
            sb.append("{} ");
        }
        
        logger.printMQLFile(mqSSMarker, sb.toString(), mapStr);
    }
}