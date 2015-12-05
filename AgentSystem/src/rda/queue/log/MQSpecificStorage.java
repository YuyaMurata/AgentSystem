/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import rda.log.AgentSystemLogger;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage{
    public final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();

    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    private List<ReciveMessageQueue> mqArray = new ArrayList<>();
    private StringBuilder mqSizeFormat;
    public void storeMessageQueue(List<ReciveMessageQueue> mqArray){
        this.mqArray = mqArray;
        
        StringBuilder mqName = new StringBuilder("MQName");
        mqSizeFormat = new StringBuilder("MQL");
        for(ReciveMessageQueue mq : mqArray){
            mqSizeFormat.append(",{}");
            mqName.append(","+mq.name);
        }
        
        logger.printMQLength(logger.fieldMarker, mqName.toString(), null);
    }
    
    public void mqLogging(){
        if(mqArray == null) return;
        
        List<Integer> mqSize = new ArrayList<>();
        for(ReciveMessageQueue mq : mqArray)
            mqSize.add(mq.getSize());
        
        //Record MessageQueue Length
        logger.printMQLength(logger.dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
    }
}