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

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage{
    public final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();
    
    private static final Marker mqSSMarker = MarkerFactory.getMarker("MessageQueueSSLength");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    public void mqLengthLogging(){
        StringBuilder sb = new StringBuilder();
        Object[] mapStr = new Object[map.size()];
        int i= 0;
        
        for(String key : map.keySet()){
            mapStr[i++] = map.get(key);
            sb.append("{} ");
        }
        
        //Record MessageQueue Length
        logger.print(mqSSMarker, sb.toString(), mapStr);
    }
    
    /**
    public static void main(String args[]){
        MQSpecificStorage mq = MQSpecificStorage.getInstance();
        
        for(int i=0; i < 10; i++)
            mq.map.put("key."+i, i);
        
        mq.mqLengthLogging();
    }
    * */
}