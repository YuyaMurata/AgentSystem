/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import java.util.List;
import rda.queue.id.IDToMQN;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.agent.user.creator.CreateRankAgent;
import rda.log.AgentLogSchedule;
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
    
    private Integer mode, reserve, max;

    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n, Integer mode, Integer reserve, Integer m){
        this.mode = mode;
        this.reserve = reserve;
        this.max = 1000;
        
        //Agent Create
        for(int i=0; i < n; i++){
            String agID = id.createID();
            create(agID);
        }
        
        //MQ Length Logging
        mqSS.storeMessageQueue(mqMap.values());
        
        //Init ID
        id.init();
        
        //Reserve Agents
        if(reserve == 1) {
            reserve(m);
            //this.max = m;
        }
        
    }
    
    private AgentLogSchedule aglog;
    public void initLogger(Long delay, Long period, Long timeout){
        // AgentLog Scheduler Initialise
        aglog = new AgentLogSchedule(delay, period, timeout);
    }
    
    private Boolean create(String agID){
        //Checking Exists Agent
        if(mqMap.get(agID) != null) return false;
        
        //Create Agent
        CreateRankAgent agent = new CreateRankAgent();
        agent.create(agID);
        
        //Setting MessageQueue
        ReciveMessageQueue mq = new ReciveMessageQueue(agID);
        mq.start();
        
        //MQ registe MQManager
        mqMap.put(agID, mq);
        
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
    
    private Boolean limit(){
        return mqMap.size() >= max;
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
    
    private static Boolean runnable =true;
    public Boolean isRunnable(){
        return runnable;
    }
    
    public void startAgentLog(){
        aglog.start();
    }
    
    public void stopAgentLog(){
        aglog.stop();
    }
    
    public void stopAll(){
        runnable = false;
        
        for(ReciveMessageQueue mq : mqMap.values())
            mq.stop();
        
        for(ReciveMessageQueue mq : mqMap.values())
            mq.syncstop();
    }
    
    //Logger用にMQの監視オブジェクトを登録
    List<Object> qsList;
    public void add(Object observe){
       qsList.add(observe);
    }
}