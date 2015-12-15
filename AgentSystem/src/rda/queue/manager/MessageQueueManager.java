/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import java.text.DecimalFormat;
import rda.queue.id.IDToMQN;
import java.util.ArrayList;
import java.util.List;
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
    
    private Integer mode;

    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n, Integer mode){
        String digit = "";
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        this.mode = mode;
        
        DecimalFormat dformat = new DecimalFormat(digit);
        for(int i=0; i < n; i++)
            create("R#"+dformat.format(i));
        
        //Init ID
        id.init();
    }
    
    private Boolean create(String agID){
        //Checking Exists Agent
        if(id.toSID(agID) > -1) return false;
        
        //CreateUserAgent agent = new CreateUserAgent();
        //agent.create(agID);
        id.setID(agID);
        
        //MessageQueue
        setMessageQueue(new ReciveMessageQueue(id.agIDToMQN(agID)));
        
        return true;
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
        if(limit() && (flg == false)) {
            System.err.println("Decompose Limit Error !");
            flg = true;
        }
        if((mode == 0) || (flg == true)) return;
        
        String agID = id.getDecomposeID(mqName);
        
        if(create(agID)){
            start(id.toSID(agID));
            id.addDistributedAgent(mqName, agID);
        }
    }
    
    public void startAll(){
        mqSS.storeMessageQueue(messageQueue);
        for(ReciveMessageQueue mq : messageQueue)
            mq.start();
    }
    
    public void start(int sid){
        mqSS.storeMessageQueue(messageQueue);
        messageQueue.get(sid).start();
    }
    
    private Boolean limit(){
        return messageQueue.size() > 1000;
    }
    
    public void stopAll(){
        for(ReciveMessageQueue mq: messageQueue)
            mq.stop();
    }
}