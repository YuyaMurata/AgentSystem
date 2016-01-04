/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import rda.queue.id.IDToMQN;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.log.AgentSystemLogger;
import rda.queue.log.MQSpecificStorage;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author 悠也
 */
public class MessageQueueManager {
    private static final MessageQueueManager manager = new MessageQueueManager();
    private static final MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
    private Map<String, ReciveMessageQueue> mqMap = new ConcurrentHashMap();
    private static IDToMQN id = IDToMQN.getInstance();
    
    private static AgentSystemLogger logger = AgentSystemLogger.getInstance();
    
    private Integer mode, reserve;

    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n, Integer mode, Integer reserve, Integer m){
        this.mode = mode;
        this.reserve = reserve;
        
        //Agent Create
        for(int i=0; i < n; i++){
            String agID = id.createID();
            create(agID);
        }
        
        //MQ Length Logging
        //registerMQSS();
        
        //Init ID
        id.init();
        
        //Reserve Agents
        if(reserve == 1) reserve(m);
    }
    
    private Boolean create(String agID){
        //Checking Exists Agent
        if(mqMap.get(agID) != null) return false;
        
        //Create Agent
        //CreateUserAgent agent = new CreateUserAgent();
        //agent.create(agID);
        
        //Setting MessageQueue
        ReciveMessageQueue mq = new ReciveMessageQueue(agID);
        mq.start();
        
        //MQ registe MQManager
        synchronized(mqMap){
            mqMap.put(agID, mq);
        }
        
        return true;
    }
    
    public void reserve(int n){
        //Reserve Agent Create
        for(int i=0; i < n; i++){
            String agID = id.reserveID(i);
            create(agID);
        }
    }
    
    public ReciveMessageQueue getMessageQueue(String uid){
        //Select MQ
        String agID = id.ageToAGID(uid);
        
        return mqMap.get(agID);
    }
    
    private Boolean flg = false;
    public String decompose(String pid){
        //Autonomy Mode
        if(mode == 0 || limit()) flg = true;
        
        if(flg == true) return "";
        
        String cid = id.createID(); 
        
        if(create(cid)) logger.print(null, "Create Agents ({}->{})",new Object[]{pid,cid});
        else logger.print(null, "Take Reserve Agents ({}->{})",new Object[]{pid,cid});
            
        id.addDistAgent(pid, cid);
        
        return cid;
    }
     
    //private void registerMQSS(){
    //    mqSS.storeMessageQueue(mqMap.values());
    //}
    
    private Boolean limit(){
        return mqMap.size() > 1000;
    }
    
    public void event(String name){
        
        String distAgent = manager.decompose(name);
        
        //イベント出力
        logger.printMQEvent(logger.dataMarker, 
                "MQName_,{},{},: ########## Load Detecting ########## ",
                new String[]{name, distAgent});
    }
    
    public Boolean getState(String agID){
        return mqMap.get(agID).isFull();
    }
    
    private Boolean running = true;
    public Boolean state(){
        return running;
    }
    
    public void stopAll(){
        running = false;
        for(ReciveMessageQueue mq : mqMap.values())
            mq.stop();
    }
}