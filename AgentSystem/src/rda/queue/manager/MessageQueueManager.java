/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.manager;

import rda.queue.id.IDToMQN;
import com.ibm.agent.exa.AgentKey;
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
        for(int i=0; i < n; i++)
            create("U#00"+i);
    }
    
    private Boolean create(String agID){
        //Agent (and Register ID)
        if(id.toMQN(agID) > -1) return false;
        CreateUserAgent agent = new CreateUserAgent();
        agent.create(agID);
        
        //MessageQueue
        String mqName = "RMQ"+id.toMQN(agID);
        setMessageQueue(new ReciveMessageQueue(mqName));
        id.setMQName(mqName);
        
        //Init Decomposition
        decompositionMap.put(id.toKey(agID), 0);
        
        return true;
    }
    
    private void setMessageQueue(ReciveMessageQueue mq){
        messageQueue.add(mq);
    }
    
    public ReciveMessageQueue getMessageQueue(AgentKey key){
        int sid = id.toMQN(key);
        return messageQueue.get(sid);
    }
    
    public void decompose(String mqName){
        decompositionMap.put(id.toKey(mqName), 1);
        if(create(id.toID(mqName)+"-"+decompositionMap.get(id.toKey(mqName))))
            start(id.toMQN(mqName));
    }
    
    public void startAll(){
        mqSS.storeMessageQueue(messageQueue);
        for(ReciveMessageQueue mq : messageQueue)
            mq.start();
    }
    
    public void start(int sid){
        //mqSS.storeMessageQueue(messageQueue);
        messageQueue.get(sid).start();
    }
    
    public void closeAll(){
        for(ReciveMessageQueue mq: messageQueue)
            mq.stop();
    }
}
