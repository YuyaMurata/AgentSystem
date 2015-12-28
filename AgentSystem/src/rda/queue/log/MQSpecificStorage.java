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
import rda.queue.id.IDToMQN;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author kaeru
 */
public class MQSpecificStorage{
    public final ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
    private static final MQSpecificStorage mqSS = new MQSpecificStorage();
    private static final AgentSystemLogger logger = AgentSystemLogger.getInstance();
    private static IDToMQN id = IDToMQN.getInstance();
    private static Boolean running = true;
    
    private MQSpecificStorage(){   
    }
    
    public static MQSpecificStorage getInstance(){
        return mqSS;
    }
    
    private Collection agValues;
    public void storeMessageQueue(Collection agValues){
        this.agValues = agValues;
    }
    
    public void mqLogging() throws InterruptedException{
        try{
        if(!running) throw new InterruptedException();
        
        StringBuilder mqName = new StringBuilder("AgentID");
        StringBuilder mqSizeFormat = new StringBuilder("MQL");
        List<Integer> mqSize = new ArrayList<>();
        for(Object ag : agValues){
            //Data 列の作成
            mqSizeFormat.append(",{}");
            
            //Field 列の作成
            mqName.append(",").append(((ReciveMessageQueue)ag).name);
            
            mqSize.add(((ReciveMessageQueue)ag).getSize());
        }
        
        System.out.println("MQSS TESTPRINT:"+mqName);
        
        //Record MessageQueue Length
        logger.printMQLength(logger.fieldMarker, mqName.toString(), null);
        logger.printMQLength(logger.dataMarker, mqSizeFormat.toString(), 
                mqSize.toArray(new Integer[mqSize.size()]));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void close(){
        running = false;
    }
}