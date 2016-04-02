/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import rda.queue.manager.MessageQueueManager;

/**
 *
 * @author 悠也
 */
public class MessageQueue extends MessageQueueProcess{
    private BlockingQueue<Object> queue;
    private String name;
    
    public MessageQueue(String name, Integer size){
        this.name = name;
        this.queue = new ArrayBlockingQueue<Object>(size);
        
        //Message Queue Length @RECORDS
        QueueObserver observe = new QueueObserver(name, queue);
        register(observe);
        
        //Supper
        super.setMessageQueue(this);
    }
    
    private void register(QueueObserver observe){
        MessageQueueManager.getInstance().add(observe);
    }
    
    public Object get(){
        try {
            return queue.take();
        } catch (InterruptedException ex) {
        }
        return null;
    }
    
    public void put(Object message){
        try {
            queue.put(message);
        } catch (InterruptedException ex) {
        }
    }
    
    //MessageQueue Process Overrides
    @Override
    public String getAgentID() {
        return name;
    }

    @Override
    public Boolean getRunnable() {
        return MessageQueueManager.getInstance().isRunnable();
    }
}