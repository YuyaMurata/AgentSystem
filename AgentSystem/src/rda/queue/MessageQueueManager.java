/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue;

import com.ibm.agent.exa.AgentKey;
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
    
    public static MessageQueueManager getInstance(){
        return manager;
    }
    
    public void initMessageQueue(Integer n){
        for(int i=0; i < n; i++)
            setMessageQueue(i);
    }
    
    public void setMessageQueue(int i){
        messageQueue.add(new ReciveMessageQueue("RMQ"+i));
    }
    
    public ReciveMessageQueue getMessageQueue(AgentKey key){
        int index = id.toMQN(key);
        return messageQueue.get(index);
    }
    
    public void startAll(){
        mqSS.storeMessageQueue(messageQueue);
        for(ReciveMessageQueue mq: messageQueue)
            mq.start();
    }
    
    public void closeAll(){
        for(ReciveMessageQueue mq: messageQueue)
            mq.stop();
    }
}
