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
import rda.queue.manager.MessageQueueManager;

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
    
    private List agIDList;
    private StringBuilder mqSizeFormat;
    public void storeMessageQueue(List agIDList){
        this.agIDList = agIDList;
        
        StringBuilder mqName = new StringBuilder("AgentID");
        mqSizeFormat = new StringBuilder("MQL");
        for(Object agID : agIDList){
            //Data 列の作成
            mqSizeFormat.append(",{}");
            
            //Field 列の作成
            mqName.append(",").append(manager.getLength(agID));
        }
        
        logger.printMQLength(logger.fieldMarker, mqName.toString(), null);
    }
    
    public void mqLogging(){
        if(agIDList == null) return;
        
        List<Integer> mqSize = new ArrayList<>();
        for(Object agID : agIDList)
            mqSize.add(manager.getLength(agID));
        
        //Record MessageQueue Length
        logger.printMQLength(logger.dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
    }
}