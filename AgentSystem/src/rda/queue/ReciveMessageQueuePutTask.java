/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue;

/**
 *
 * @author kaeru
 */
public class ReciveMessageQueuePutTask implements Runnable{
    private final ReciveMessageQueue mq;
    private final Object msg; 
    
    public ReciveMessageQueuePutTask(ReciveMessageQueue mq, Object msg) {
        this.mq = mq;
        this.msg = msg;
    }
    
    public void event() throws MessageQueueException{
        throw new MessageQueueException(mq.name);
    }
    
    public synchronized void putWait(){
        try {
            wait(mq.QUEUE_WAIT);
        } catch (InterruptedException e) {
        }
    }
    
    @Override
    public void run() {
        while(mq.isFull()){
            try {
                event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();

                putWait();
            }
        }
        
        //mq.queue.offer(msg);
    }
    
}
