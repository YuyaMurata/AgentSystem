/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import rda.queue.id.IDToMQN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import rda.queue.log.MQSpecificStorage;
import rda.queue.reciver.ReciveMessageQueue;

/**
 *
 * @author 悠也
 */
public class MessageQueueManager {
    private static MessageQueueManager manager = new MessageQueueManager();
    private List<ReciveMessageQueue> messageQueue = new ArrayList<>();
    private IDToMQN id = IDToMQN.getInstance();
    private MQSpecificStorage mqSS = MQSpecificStorage.getInstance();
    
    private Integer mode, reserve;

    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n, Integer mode, Integer reserve){
        String digit = "";
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        this.mode = mode;
        this.reserve = reserve;
        
        DecimalFormat dformat = new DecimalFormat(digit);
        for(int i=0; i < n; i++){
            String agID = "R#"+dformat.format(i);
            create(agID);
            start(agID);
        }
        
        //Init ID
        id.init();
        
        reserve(10);
        
        registerMQSS();
    }
    
    private Boolean create(String agID){
        //Checking Exists Agent
        if(id.toSID(agID) > -1) return false;
        
        //Setting ID
        id.setID(agID);
        
        //Setting MessageQueue
        setMessageQueue(new ReciveMessageQueue(id.agIDToMQN(agID)));
        
        return true;
    }
    
    private HashMap reservMap = new HashMap();
    private Queue reserveQueue = new ArrayDeque();
    private void reserve(Integer n){
        if(reserve == 0) return;
        
        String digit = "";
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        DecimalFormat dformat = new DecimalFormat(digit);
        for(int i=0; i < n; i++){
            String agID = "RV#"+dformat.format(i);
            create(agID);
            start(agID);
            reserveQueue.add(agID);
        }  
    }
    
    private void setMessageQueue(ReciveMessageQueue mq){
        messageQueue.add(mq);
    }
    
    public ReciveMessageQueue getMessageQueue(String uid){
        int sid = id.ageToSID(uid);
        return messageQueue.get(sid);
    }
    
    private Boolean flg = false;
    public void decompose(String mqName){
        //Reserve Mode
        if((reserve == 1) && (reserveQueue.size() == 0)) flg = true;
        //Autonomy Mode
        if(mode == 0 || limit()) flg = true;
        
        if(flg == true) return;
        
        String agID = id.getDecomposeID(mqName);
        System.out.println("Decompose::"+agID);
        
        switch(reserve){
            case 0 :
                if(create(agID)){
                    start(id.agIDToMQN(agID));
                    id.addDistributedAgent(agID.split("-")[0], agID);
                };
            case 1 :
                String rvAGID = (String) reserveQueue.poll();
                
                if(agID.contains("RV#")) agID = (String)reservMap.get(agID.split("-")[0]);
                else reservMap.put(rvAGID, agID.split("-")[0]);
                id.addDistributedAgent(agID.split("-")[0], rvAGID);
        }
    }
    
    public void start(String agID){
        messageQueue.get(id.toSID(agID)).start();
    }
    
    private void registerMQSS(){
        mqSS.storeMessageQueue(messageQueue);
    }
    
    private Boolean limit(){
        return messageQueue.size() > 1000;
    }
    
    public void stopAll(){
        for(ReciveMessageQueue mq: messageQueue)
            mq.stop();
    }
}