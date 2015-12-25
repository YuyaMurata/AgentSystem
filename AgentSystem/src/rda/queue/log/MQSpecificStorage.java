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
import rda.queue.manager.MessageQueueManager;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage{
    public final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private MessageQueueManager manager = MessageQueueManager.getInstance();
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    private Collection agIDList;
    private StringBuilder mqSizeFormat;
    public void storeMessageQueue(Collection agIDList){
        this.agIDList = agIDList;
        
        StringBuilder mqName = new StringBuilder("AgentID");
        mqSizeFormat = new StringBuilder("MQL");
        for(Object agID : agIDList){
            //Data 列の作成
            mqSizeFormat.append(",{}");
            
            //Field 列の作成
            mqName.append(",").append(((ReciveMessageQueue)agID).name);
        }
        
        logger.printMQLength(logger.fieldMarker, mqName.toString(), null);
    }
    
    public void mqLogging(){
        if(agIDList == null) return;
        
        List<Integer> mqSize = new ArrayList<>();
        for(Object agID : agIDList){
            mqSize.add(((ReciveMessageQueue)agID).getSize());
        }
        
        //Record MessageQueue Length
        logger.printMQLength(logger.dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
    }
}