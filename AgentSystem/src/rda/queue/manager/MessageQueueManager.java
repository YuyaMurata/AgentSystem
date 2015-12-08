/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import java.text.DecimalFormat;
import rda.queue.id.IDToMQN;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import rda.agent.user.CreateUserAgent;
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
    
    private HashMap<Object, Integer> decompositionMap = new HashMap<>();

    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n){
        String digit = "";
        for(int i=0; i < n.toString().length(); i++)
            digit = digit + "0";
        
        DecimalFormat dformat = new DecimalFormat(digit);
        for(int i=0; i < n; i++)
            create("R#"+dformat.format(i));
    }
    
    private Boolean create(String agID){
        //Agent (and Register ID)
        if(id.toSID(agID) > -1) return false;
        
        CreateUserAgent agent = new CreateUserAgent();
        agent.create(agID);
        
        //MessageQueue
        String mqName = "RMQ"+id.toSID(agID);
        setMessageQueue(new ReciveMessageQueue(mqName));
        id.setMQName(mqName);
        
        //Init Decomposition
        decompositionMap.put(id.toKey(agID), 0);
        
        return true;
    }
    
    private void setMessageQueue(ReciveMessageQueue mq){
        messageQueue.add(mq);
    }
    
    public ReciveMessageQueue getMessageQueue(String uid){
        int sid = id.toSID(uid);
        return messageQueue.get(sid);
    }
    
    public void decompose(String mqName){
        if(limit()) {
            System.err.println("Decompose Limit Error !");
            return ;
        }
        
        decompositionMap.put(id.toKey(mqName), decompositionMap.get(id.toKey(mqName))+1);
        String agID = id.toAGID(mqName)+"-"+decompositionMap.get(id.toKey(mqName));
        if(create(agID)){
            start(id.toSID(agID));
            //System.out.println(id.toString());
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
