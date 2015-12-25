/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import rda.queue.id.IDToMQN;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.agent.user.creator.CreateUserAgent;
import rda.queue.log.MQSpecificStorage;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author 悠也
 */
public class MessageQueueManager {
    private static MessageQueueManager manager = new MessageQueueManager();
    private Map<String, ReciveMessageQueue> mqMap = new ConcurrentHashMap();
    private IDToMQN id = IDToMQN.getInstance();
    
    private static Boolean running = true;
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
        
        //Init ID
        id.init();
        
        //Reserve Agents
        if(reserve == 1) reserve(m);
    }
    
    private Boolean create(String agID){
        //Checking Exists Agent
        if(mqMap.get(agID) != null) return false;
        
        //Create Agent
        CreateUserAgent agent = new CreateUserAgent();
        agent.create(agID);
        
        //Setting MessageQueue
        ReciveMessageQueue mq = new ReciveMessageQueue(agID);
        mq.start();
        
        //MQ registe MQManager
        mqMap.put(agID, mq);
        
        //MQ Length Logging
        registerMQSS();
        
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
        
        if(flg == true || !running) return "";
        
        String cid = id.createID(); 
        
        if(create(cid)) System.out.println("Create Agents ("+pid+"->"+cid+")");
        else System.out.println("Take Reserve Agents ("+pid+"->"+cid+")");
            
        id.addDistAgent(pid, cid);
        
        return cid;
    }
     
    private void registerMQSS(){
        MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
        mqSS.storeMessageQueue(mqMap.values());
    }
    
    private Boolean limit(){
        return mqMap.size() > 1000;
    }
    
    public Integer getLength(Object agID){
        return mqMap.get((String) agID).getSize();
    }
    
    public Boolean getState(String agID){
        return mqMap.get(agID).isFull();
    }
    
    public void stopAll(){
        running = false;
        for(String key : mqMap.keySet()){
            mqMap.get(key).stop();
            mqMap.remove(key);
        }
    }
}