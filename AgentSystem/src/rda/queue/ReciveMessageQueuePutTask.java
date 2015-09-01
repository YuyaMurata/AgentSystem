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
    
    @Override
    public void run() {
        while(mq.isFull()){
            try {
                mq.event();
            } catch (MessageQueueException mqEvent) {
                mqEvent.printEvent();

                try {
                    wait(mq.QUEUE_WAIT);
                } catch (InterruptedException e) {
                }
            }
        }
        
        mq.queue.offer(msg);
        mq.notifyAll();
    }
    
}
