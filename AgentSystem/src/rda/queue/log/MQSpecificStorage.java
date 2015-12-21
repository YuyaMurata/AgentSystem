/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.log;

import java.util.ArrayList;
import java.util.Collection;
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
    
    private Collection<ReciveMessageQueue> mqALL;
    private StringBuilder mqSizeFormat;
    public void storeMessageQueue(Collection<ReciveMessageQueue> mqALL){
        this.mqALL = mqALL;
        
        StringBuilder mqName = new StringBuilder("AgentID");
        mqSizeFormat = new StringBuilder("MQL");
        for(ReciveMessageQueue mq : mqALL){
            //Data 列の作成
            mqSizeFormat.append(",{}");
            
            //Field 列の作成
            mqName.append(",").append(mq.name);
        }
        
        logger.printMQLength(logger.fieldMarker, mqName.toString(), null);
    }
    
    public void mqLogging(){
        if(mqALL == null) return;
        
        List<Integer> mqSize = new ArrayList<>();
        for(ReciveMessageQueue mq : mqALL)
            mqSize.add(mq.getSize());
        
        //Record MessageQueue Length
        logger.printMQLength(logger.dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
    }
}