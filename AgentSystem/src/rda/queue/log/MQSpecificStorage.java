/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import rda.log.AgentSystemLogger;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage{
    public final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();
    
    private static final Marker dataMarker = MarkerFactory.getMarker("data");
    private static final Marker fieldMarker = MarkerFactory.getMarker("field");
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    private List<ReciveMessageQueue> mqArray = new ArrayList<>();
    private StringBuilder mqSizeFormat = new StringBuilder("MQL");
    public void storeMessageQueue(List<ReciveMessageQueue> mqArray){
        this.mqArray = mqArray;
        
        StringBuilder mqName = new StringBuilder("MQName");
        for(ReciveMessageQueue mq : mqArray){
            mqSizeFormat.append(",{}");
            mqName.append(","+mq.name);
        }
        
        logger.printMQLFile(fieldMarker, mqName.toString(), null);
    }
    
    public void mqLogging(){
        if(mqArray == null) return;
        
        List<Integer> mqSize = new ArrayList<>();
        for(ReciveMessageQueue mq : mqArray)
            mqSize.add(mq.getSize());
        
        //Record MessageQueue Length
        logger.printMQLFile(dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
    }
}