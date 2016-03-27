/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.queue.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kaeru
 */
public class MessageQueueTimer implements Runnable{
    private Boolean binaryTimer;
    private static final MessageQueueTimer timer = new MessageQueueTimer();
    
    private final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
    {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"MessageQueueTimer");
        }
    });
    
    private Long period = 100L;
    public void setTImerPriod(Long period){
        this.period = period;
    }
    
    private MessageQueueTimer() {
        this.binaryTimer = false;
        
        ex.scheduleAtFixedRate(this, 0, period, TimeUnit.MILLISECONDS);
    }
    
    public static MessageQueueTimer getInstance(){
        return timer;
    }
    
    @Override
    public void run() {
        binaryTimer = !binaryTimer;
        //System.out.println("******** Thread Print ********");
        //for(Thread t : Thread.getAllStackTraces().keySet()){
        //    if(t.getName().contains("R#")) continue;
        //    System.out.println("Thread Stats::"+t.getName()+"="+t.getState()+" num="+Thread.getAllStackTraces().size());
        //}
    }
    
    public Boolean getTimer(){
        return binaryTimer;
    }
    
    public void close(){
        binaryTimer = false;
        ex.shutdownNow();
    }
}