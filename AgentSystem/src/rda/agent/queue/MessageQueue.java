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
public class MessageQueue {
    private BlockingQueue<Object> queue;
    
    public MessageQueue(String name, Integer size){
        this.queue = new ArrayBlockingQueue<Object>(size);
        QueueObserver observe = new QueueObserver(name, queue);
    }
    
    private void register(QueueObserver observe){
        MessageQueueManager manager = MessageQueueManager.getInstance();
        manager.add(observe);
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
}