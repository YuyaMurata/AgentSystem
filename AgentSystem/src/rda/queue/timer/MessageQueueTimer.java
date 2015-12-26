/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class MessageQueueTimer implements Runnable, SetProperty{
    private Boolean binaryTimer;
    private static final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    private static final MessageQueueTimer timer = new MessageQueueTimer();
    
    private MessageQueueTimer() {
        this.binaryTimer = false;
        ex.scheduleAtFixedRate(this, 0, QUEUE_WAIT, TimeUnit.MILLISECONDS);
    }
    
    public static MessageQueueTimer getInstance(){
        return timer;
    }
    
    @Override
    public void run() {
        binaryTimer = !binaryTimer;
    }
    
    public Boolean getTimer(){
        return binaryTimer;
    }
    
    public void close(){
        binaryTimer = false;
        ex.shutdownNow();
    }
}
